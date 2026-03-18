package com.carmanagement.service;
import com.carmanagement.database.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;

public class SaleService {

    // ===== HÀM CHÍNH =====
    public void createFullOrder (
            String contractID,
            String warrantyID,
            String customerName,
            String phone,
            String address,
            String carName,
            String paymentMethod
    ) {

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // transaction

            // 1. LƯU KHÁCH HÀNG
            int customerID = getOrCreateCustomer(conn, customerName, phone, address);

            // 2. LƯU HỢP ĐỒNG
            int invoiceID = insertInvoice(conn, contractID, customerID, paymentMethod);

            // 3. LƯU CHI TIẾT XE (đơn giản)
            insertInvoiceDetail(conn, invoiceID, carName);

            // 4. LƯU BẢO HÀNH
            insertWarranty(conn, warrantyID, carName);

            conn.commit();

            System.out.println("✔ Lưu thành công!");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if(conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // ===== CUSTOMER =====
    private int getOrCreateCustomer(Connection conn, String name, String phone, String address) throws Exception {

        String checkSql = "SELECT CustomerID FROM Customer WHERE Phone = ?";
        PreparedStatement ps = conn.prepareStatement(checkSql);
        ps.setString(1, phone);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            return rs.getInt("CustomerID");
        }

        // nếu chưa có thì insert
        String insertSql = "INSERT INTO Customer(Name, Phone, Address) VALUES (?, ?, ?)";
        PreparedStatement ps2 = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

        ps2.setString(1, name);
        ps2.setString(2, phone);
        ps2.setString(3, address);

        ps2.executeUpdate();

        ResultSet rs2 = ps2.getGeneratedKeys();
        if(rs2.next()){
            return rs2.getInt(1);
        }

        return -1;
    }

    // ===== INVOICE =====
    private int insertInvoice(Connection conn, String contractID, int customerID, String payment) throws Exception {

        String sql = "INSERT INTO Invoice(ContractCode, CustomerID, PaymentMethod, Date) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, contractID);
        ps.setInt(2, customerID);
        ps.setString(3, payment);
        ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()){
            return rs.getInt(1);
        }

        return -1;
    }

    // ===== INVOICE DETAIL =====
    private void insertInvoiceDetail(Connection conn, int invoiceID, String carName) throws Exception {

        String sql = "INSERT INTO InvoiceDetail(InvoiceID, ProductName) VALUES (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, invoiceID);
        ps.setString(2, carName);

        ps.executeUpdate();
    }

    // ===== WARRANTY =====
    private void insertWarranty(Connection conn, String warrantyID, String carName) throws Exception {

        String sql = "INSERT INTO Warranty(WarrantyCode, ProductName, StartDate, EndDate) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        LocalDateTime now = LocalDateTime.now();

        ps.setString(1, warrantyID);
        ps.setString(2, carName);
        ps.setTimestamp(3, Timestamp.valueOf(now));
        ps.setTimestamp(4, Timestamp.valueOf(now.plusYears(2))); // bảo hành 2 năm

        ps.executeUpdate();
    }
}

