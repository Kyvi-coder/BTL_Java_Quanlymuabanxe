package com.carmanagement.service;
import com.carmanagement.database.DBConnection;
import java.sql.*;
public class InventoryService {
    // ===== LẤY TẤT CẢ XE =====
    public ResultSet getAllCars() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM product";
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== TÌM KIẾM =====
    public ResultSet searchCar(String keyword) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM product WHERE vin LIKE ? OR brand LIKE ? OR product_id LIKE ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");

            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== SẮP XẾP =====
    public ResultSet sortByBrand() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM product ORDER BY brand";
            return conn.prepareStatement(sql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet sortByPriceAsc() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM product ORDER BY price ASC";
            return conn.prepareStatement(sql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet sortByPriceDesc() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM product ORDER BY price DESC";
            return conn.prepareStatement(sql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== THÊM XE =====
    public boolean insertCar(String vin, String name, String brand, String color,
                             double price, String importDate, int quantity) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO product(vin, product_name, brand, color, price, import_date, quantity) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, vin);
            ps.setString(2, name);
            ps.setString(3, brand);
            ps.setString(4, color);
            ps.setDouble(5, price);
            ps.setString(6, importDate);
            ps.setInt(7, quantity);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== XÓA XE HẾT =====
    public void deleteOutOfStock() {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "DELETE FROM product WHERE quantity <= 0";
            conn.prepareStatement(sql).executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

