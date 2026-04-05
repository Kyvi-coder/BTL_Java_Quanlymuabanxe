package com.carmanagement.dao;

import com.carmanagement.entity.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class invoiceDAO {
    public List<Invoice> selectAll() {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM Invoice";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean isVinSold(Connection conn, String vin) {
        String sql = "SELECT 1 FROM Invoice_Detail WHERE VIN = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vin);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addInvoiceFull(Invoice invoice, List<String> listVIN) {
        String insertInvoice = """
                INSERT INTO Invoice(
                    id_invoice, date_invoice, total_amount_invoice, payment_method, id_customer, id_employee
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        String insertDetail = "INSERT INTO Invoice_Detail(id_invoice, VIN, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psInvoice = conn.prepareStatement(insertInvoice);
                 PreparedStatement psDetail = conn.prepareStatement(insertDetail)) {

                psInvoice.setString(1, invoice.getId_invoice());
                psInvoice.setTimestamp(2, Timestamp.valueOf(invoice.getDate_invoice()));
                psInvoice.setInt(3, invoice.getTotal_amount_invoice());
                psInvoice.setString(4, invoice.getPayment());
                psInvoice.setString(5, invoice.getId_customer());
                psInvoice.setString(6, invoice.getId_employee());
                psInvoice.executeUpdate();

                for (String vin : listVIN) {
                    if (isVinSold(conn, vin)) {
                        conn.rollback();
                        return false;
                    }

                    psDetail.setString(1, invoice.getId_invoice());
                    psDetail.setString(2, vin);
                    psDetail.setInt(3, 1);
                    psDetail.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getTotalRevenueByMonthYear(int month, int year) {
        String sql = """
                SELECT COALESCE(SUM(total_amount_invoice), 0) AS total
                FROM Invoice
                WHERE MONTH(date_invoice) = ? AND YEAR(date_invoice) = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalCarsSoldByMonthYear(int month, int year) {
        String sql = """
                SELECT COALESCE(SUM(d.quantity), 0) AS total
                FROM Invoice i
                JOIN Invoice_Detail d ON i.id_invoice = d.id_invoice
                WHERE MONTH(i.date_invoice) = ? AND YEAR(i.date_invoice) = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Invoice> getInvoicesByMonthYear(int month, int year) {
        List<Invoice> list = new ArrayList<>();
        String sql = """
                SELECT id_invoice, id_customer, id_employee, date_invoice, total_amount_invoice, payment_method
                FROM Invoice
                WHERE MONTH(date_invoice) = ? AND YEAR(date_invoice) = ?
                ORDER BY date_invoice DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            ps.setInt(2, year);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapInvoice(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Invoice mapInvoice(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId_invoice(rs.getString("id_invoice"));
        invoice.setId_customer(rs.getString("id_customer"));
        invoice.setId_employee(rs.getString("id_employee"));

        Timestamp timestamp = rs.getTimestamp("date_invoice");
        LocalDateTime dateInvoice = timestamp != null ? timestamp.toLocalDateTime() : null;
        invoice.setDate_invoice(dateInvoice);

        invoice.setTotal_amount_invoice(rs.getInt("total_amount_invoice"));
        invoice.setPayment(rs.getString("payment_method"));
        return invoice;
    }
}
