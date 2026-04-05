package com.carmanagement.dao;

import com.carmanagement.entity.Warranty;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class warrantyDAO {
    public List<Warranty> getAllWarranty() {
        List<Warranty> warranties = new ArrayList<>();
        String sql = "SELECT w.id_warranty, " +
                "COALESCE(c.name_customer, '') AS customer_name, " +
                "COALESCE(p.name_product, '') AS car_name, " +
                "w.VIN, w.start_date, w.end_date " +
                "FROM Warranty w " +
                "JOIN Vehicle v ON w.VIN = v.VIN " +
                "JOIN Product p ON v.id_product = p.id_product " +
                "LEFT JOIN Invoice_Detail d ON v.VIN = d.VIN " +
                "LEFT JOIN Invoice i ON d.id_invoice = i.id_invoice " +
                "LEFT JOIN Customer c ON i.id_customer = c.id_customer";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                warranties.add(mapWarranty(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warranties;
    }

    public Warranty getWarrantyInfoByVIN(String vin) {
        String sql = "SELECT COALESCE(c.name_customer, '') AS customer_name, " +
                "COALESCE(p.name_product, '') AS car_name, " +
                "v.VIN " +
                "FROM Vehicle v " +
                "JOIN Product p ON v.id_product = p.id_product " +
                "LEFT JOIN Invoice_Detail d ON v.VIN = d.VIN " +
                "LEFT JOIN Invoice i ON d.id_invoice = i.id_invoice " +
                "LEFT JOIN Customer c ON i.id_customer = c.id_customer " +
                "WHERE v.VIN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Warranty warranty = new Warranty();
                warranty.setCustomer_name(rs.getString("customer_name"));
                warranty.setCar_name(rs.getString("car_name"));
                warranty.setVIN(rs.getString("VIN"));
                return warranty;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertWarranty(Warranty warranty) {
        String sql = "INSERT INTO Warranty(id_warranty, VIN, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, warranty.getId_warranty());
            ps.setString(2, warranty.getVIN());
            ps.setDate(3, Date.valueOf(warranty.getStart_date_warranty()));
            ps.setDate(4, Date.valueOf(warranty.getEnd_date_warranty()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteExpiredWarranty() {
        String sql = "DELETE FROM Warranty WHERE end_date < DATE_SUB(CURDATE(), INTERVAL 2 MONTH)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Warranty mapWarranty(ResultSet rs) throws SQLException {
        Warranty warranty = new Warranty();
        warranty.setId_warranty(rs.getString("id_warranty"));
        warranty.setCustomer_name(rs.getString("customer_name"));
        warranty.setCar_name(rs.getString("car_name"));
        warranty.setVIN(rs.getString("VIN"));
        Date startDate = rs.getDate("start_date");
        Date endDate = rs.getDate("end_date");
        if (startDate != null) {
            warranty.setStart_date_warranty(startDate.toLocalDate());
        }
        if (endDate != null) {
            warranty.setEnd_date_warranty(endDate.toLocalDate());
        }
        return warranty;
    }
}
