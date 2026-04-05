package com.carmanagement.service;

import com.carmanagement.dao.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaleService {

    public SaleResult createFullOrder(
            String contractID,
            String employeeID,
            String customerName,
            String phone,
            String address,
            String carName,
            String brand,
            int quantity,
            String paymentMethod,
            String warrantyOption
    ) {
        Connection conn = null;

        try {
            if (quantity <= 0) {
                throw new IllegalArgumentException("So luong xe phai lon hon 0.");
            }

            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            ProductInfo product = getProductForSale(conn, carName, brand);
            List<String> availableVins = getAvailableVins(conn, product.idProduct(), quantity);

            if (availableVins.size() < quantity) {
                if (availableVins.isEmpty()) {
                    throw new IllegalStateException("Xe nay da het hang.");
                }
                throw new IllegalStateException("Chi con " + availableVins.size() + " xe san sang de ban.");
            }

            String customerID = getOrCreateCustomer(conn, customerName, phone, address);
            int totalAmount = product.price() * quantity;
            insertInvoice(conn, contractID, customerID, employeeID, paymentMethod, totalAmount);
            insertInvoiceDetails(conn, contractID, availableVins);

            List<String> warrantyIds = createWarranties(conn, availableVins, warrantyOption);

            int remainingStock = countAvailableVins(conn, product.idProduct());
            updateProductStatus(conn, product.idProduct(), remainingStock == 0 ? "Da ban" : "Chua ban");

            conn.commit();
            return new SaleResult(contractID, customerID, availableVins, warrantyIds, totalAmount, remainingStock);
        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception rollbackException) {
                rollbackException.printStackTrace();
            }

            if (e instanceof IllegalArgumentException) {
                throw (IllegalArgumentException) e;
            }
            if (e instanceof IllegalStateException) {
                throw (IllegalStateException) e;
            }
            throw new RuntimeException("Khong the tao giao dich ban xe.", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ignored) {
            }
        }
    }

    private String getOrCreateCustomer(Connection conn, String name, String phone, String address) throws Exception {
        String checkSql = "SELECT id_customer FROM Customer WHERE phone_customer = ?";
        try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String customerID = rs.getString("id_customer");
                updateCustomer(conn, customerID, name, phone, address);
                return customerID;
            }
        }

        String customerID = generateId("CUS", 0);
        String insertSql = "INSERT INTO Customer(id_customer, name_customer, phone_customer, address_customer) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setString(1, customerID);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.setString(4, address);
            ps.executeUpdate();
        }
        return customerID;
    }

    private void updateCustomer(Connection conn, String customerID, String name, String phone, String address) throws Exception {
        String sql = "UPDATE Customer SET name_customer = ?, phone_customer = ?, address_customer = ? WHERE id_customer = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, customerID);
            ps.executeUpdate();
        }
    }

    private ProductInfo getProductForSale(Connection conn, String carName, String brand) throws Exception {
        String sql = "SELECT id_product, price_product, status_product " +
                "FROM Product " +
                "WHERE LOWER(name_product) = LOWER(?) AND LOWER(brand_product) = LOWER(?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, carName.trim());
            ps.setString(2, brand.trim());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new IllegalStateException("Khong tim thay xe phu hop trong kho.");
            }

            String status = rs.getString("status_product");
            if (status != null && status.toLowerCase().contains("bao")) {
                throw new IllegalStateException("Xe nay dang bao tri, khong the ban.");
            }

            return new ProductInfo(
                    rs.getString("id_product"),
                    rs.getInt("price_product")
            );
        }
    }

    private List<String> getAvailableVins(Connection conn, String productId, int quantity) throws Exception {
        List<String> vins = new ArrayList<>();
        String sql = "SELECT v.VIN " +
                "FROM Vehicle v " +
                "LEFT JOIN Invoice_Detail d ON v.VIN = d.VIN " +
                "WHERE v.id_product = ? AND d.VIN IS NULL " +
                "ORDER BY v.VIN";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next() && vins.size() < quantity) {
                vins.add(rs.getString("VIN"));
            }
        }
        return vins;
    }

    private int countAvailableVins(Connection conn, String productId) throws Exception {
        String sql = "SELECT COUNT(*) AS total " +
                "FROM Vehicle v " +
                "LEFT JOIN Invoice_Detail d ON v.VIN = d.VIN " +
                "WHERE v.id_product = ? AND d.VIN IS NULL";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    private void insertInvoice(Connection conn, String contractID, String customerID, String employeeID, String payment, int totalAmount) throws Exception {
        String sql = "INSERT INTO Invoice(id_invoice, date_invoice, total_amount_invoice, payment_method, id_customer, id_employee) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, contractID);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(3, totalAmount);
            ps.setString(4, payment);
            ps.setString(5, customerID);
            ps.setString(6, employeeID);
            ps.executeUpdate();
        }
    }

    private void insertInvoiceDetails(Connection conn, String contractID, List<String> vins) throws Exception {
        String sql = "INSERT INTO Invoice_Detail(id_invoice, VIN, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String vin : vins) {
                ps.setString(1, contractID);
                ps.setString(2, vin);
                ps.setInt(3, 1);
                ps.executeUpdate();
            }
        }
    }

    private List<String> createWarranties(Connection conn, List<String> vins, String warrantyOption) throws Exception {
        List<String> ids = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = calculateWarrantyEndDate(startDate, warrantyOption);
        String sql = "INSERT INTO Warranty(id_warranty, VIN, start_date, end_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String vin : vins) {
                String id = generateId("BH", ids.size());
                ps.setString(1, id);
                ps.setString(2, vin);
                ps.setDate(3, Date.valueOf(startDate));
                ps.setDate(4, Date.valueOf(endDate));
                ps.executeUpdate();
                ids.add(id);
            }
        }
        return ids;
    }

    private LocalDate calculateWarrantyEndDate(LocalDate startDate, String warrantyOption) {
        if (warrantyOption == null) {
            return startDate.plusMonths(6);
        }

        if (warrantyOption.contains("2")) {
            return startDate.plusYears(2);
        }
        if (warrantyOption.contains("1")) {
            return startDate.plusYears(1);
        }
        return startDate.plusMonths(6);
    }

    private void updateProductStatus(Connection conn, String productId, String status) throws Exception {
        String sql = "UPDATE Product SET status_product = ? WHERE id_product = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, productId);
            ps.executeUpdate();
        }
    }

    private String generateId(String prefix, int suffix) {
        String seed = String.valueOf(System.currentTimeMillis()) + suffix;
        int maxLength = 15 - prefix.length();
        if (seed.length() > maxLength) {
            seed = seed.substring(seed.length() - maxLength);
        }
        return prefix + seed;
    }

    private record ProductInfo(String idProduct, int price) {
    }

    public record SaleResult(
            String invoiceId,
            String customerId,
            List<String> soldVins,
            List<String> warrantyIds,
            int totalAmount,
            int remainingStock
    ) {
    }
}
