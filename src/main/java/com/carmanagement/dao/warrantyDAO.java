package com.carmanagement.dao;

import com.carmanagement.entity.Warranty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class warrantyDAO {
    // danh sách xe còn bảo hành

    public List<Warranty> getWarrantyOnDate() {
        List<Warranty> warrantyList = new ArrayList<>();
        String sql = "SELECT * FROM Warranty\n" +
                "WHERE end_date >= CURDATE()";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                Warranty w = new Warranty();
                w.setId_warranty(rs.getString("id_warranty"));
                w.setVIN(rs.getString("VIN"));
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                w.setStart_date_warranty(start_date);
                w.setEnd_date_warranty(end_date);
                warrantyList.add(w);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return warrantyList;
    }

    // danh sách xe hết bảo hành

    public List<Warranty> getWarrantyOff(){
        List<Warranty> warrantyList = new ArrayList<>();
        String sql = "SELECT * FROM Warranty\n" +
                "WHERE end_date < CURDATE()";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                Warranty w = new Warranty();
                w.setId_warranty(rs.getString("id_warranty"));
                w.setVIN(rs.getString("VIN"));
                LocalDate start_date = rs.getDate("start_date").toLocalDate();
                LocalDate end_date = rs.getDate("end_date").toLocalDate();
                w.setStart_date_warranty(start_date);
                w.setEnd_date_warranty(end_date);
                warrantyList.add(w);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return warrantyList;
    }

    // xem bảo hành theo xe

    public List<Warranty> getWarrantyByVIN(String vin) {
        List<Warranty> list = new ArrayList<>();

        String sql = "SELECT w.id_warranty, p.name_product, v.VIN, w.start_date, w.end_date " +
                "FROM Warranty w " +
                "JOIN Vehicle v ON w.VIN = v.VIN " +
                "JOIN Product p ON v.id_product = p.id_product " +
                "WHERE v.VIN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vin);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Warranty w = new Warranty();
                w.setId_warranty(rs.getString("id_warranty"));
                w.setVIN(rs.getString("VIN"));

                LocalDate start = rs.getDate("start_date").toLocalDate();
                LocalDate end = rs.getDate("end_date").toLocalDate();

                 w.setStart_date_warranty(start);
                 w.setEnd_date_warranty(end);

                list.add(w);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // xem bảo hành theo khách hàng

    public List<Warranty> getWarrantyByCustomer(String idCustomer) {
        List<Warranty> list = new ArrayList<>();

        String sql = "SELECT c.name_customer, p.name_product, v.VIN, w.start_date, w.end_date " +
                "FROM Warranty w " +
                "JOIN Vehicle v ON w.VIN = v.VIN " +
                "JOIN Product p ON v.id_product = p.id_product " +
                "JOIN Invoice_Detail d ON v.VIN = d.VIN " +
                "JOIN Invoice i ON d.id_invoice = i.id_invoice " +
                "JOIN Customer c ON i.id_customer = c.id_customer " +
                "WHERE c.id_customer = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idCustomer);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Warranty w = new Warranty();

                w.setVIN(rs.getString("VIN"));

                LocalDate start = rs.getDate("start_date").toLocalDate();
                LocalDate end = rs.getDate("end_date").toLocalDate();

                 w.setStart_date_warranty(start);
                 w.setEnd_date_warranty(end);

                list.add(w);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // check trùng bảo hành

    public boolean isWarrantyExists(String vin) {
        String sql = "SELECT 1 FROM Warranty WHERE VIN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vin);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // thêm bảo hành

    public boolean addWarranty(String idWarranty, String vin) {
        String sql = "INSERT INTO Warranty(" +
                "id_warranty, VIN, start_date, end_date) " +
                "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 2 YEAR))";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idWarranty);
            ps.setString(2, vin);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
