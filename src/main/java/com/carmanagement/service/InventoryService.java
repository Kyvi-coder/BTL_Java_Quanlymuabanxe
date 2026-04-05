package com.carmanagement.service;

import com.carmanagement.dao.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InventoryService {
    // Doc danh sach xe bang query join Product va Vehicle.
    public ResultSet getAllCars() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT v.VIN, p.id_product, p.name_product, p.brand_product, " +
                    "p.color_product, p.price_product, p.production_year_product, " +
                    "CASE " +
                    "WHEN d.VIN IS NOT NULL THEN 'Da ban' " +
                    "ELSE p.status_product " +
                    "END AS status_display " +
                    "FROM Vehicle v " +
                    "JOIN Product p ON v.id_product = p.id_product " +
                    "LEFT JOIN Invoice_Detail d ON v.VIN = d.VIN";
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet searchCar(String keyword) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT v.VIN, p.id_product, p.name_product, p.brand_product, " +
                    "p.color_product, p.price_product, p.production_year_product, " +
                    "CASE " +
                    "WHEN d.VIN IS NOT NULL THEN 'Da ban' " +
                    "ELSE p.status_product " +
                    "END AS status_display " +
                    "FROM Vehicle v " +
                    "JOIN Product p ON v.id_product = p.id_product " +
                    "LEFT JOIN Invoice_Detail d ON v.VIN = d.VIN " +
                    "WHERE v.VIN LIKE ? OR p.brand_product LIKE ? OR p.id_product LIKE ? OR p.name_product LIKE ? OR " +
                    "CASE WHEN d.VIN IS NOT NULL THEN 'Da ban' ELSE p.status_product END LIKE ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ps.setString(4, "%" + keyword + "%");
            ps.setString(5, "%" + keyword + "%");
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet sortByBrand() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT v.VIN, p.id_product, p.name_product, p.brand_product, " +
                    "p.color_product, p.price_product, p.production_year_product, " +
                    "CASE " +
                    "WHEN d.VIN IS NOT NULL THEN 'Da ban' " +
                    "ELSE p.status_product " +
                    "END AS status_display " +
                    "FROM Vehicle v " +
                    "JOIN Product p ON v.id_product = p.id_product " +
                    "LEFT JOIN Invoice_Detail d ON v.VIN = d.VIN " +
                    "ORDER BY p.brand_product";
            return conn.prepareStatement(sql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet sortByPriceAsc() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT v.VIN, p.id_product, p.name_product, p.brand_product, " +
                    "p.color_product, p.price_product, p.production_year_product, " +
                    "CASE " +
                    "WHEN d.VIN IS NOT NULL THEN 'Da ban' " +
                    "ELSE p.status_product " +
                    "END AS status_display " +
                    "FROM Vehicle v " +
                    "JOIN Product p ON v.id_product = p.id_product " +
                    "LEFT JOIN Invoice_Detail d ON v.VIN = d.VIN " +
                    "ORDER BY p.price_product ASC";
            return conn.prepareStatement(sql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet sortByPriceDesc() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT v.VIN, p.id_product, p.name_product, p.brand_product, " +
                    "p.color_product, p.price_product, p.production_year_product, " +
                    "CASE " +
                    "WHEN d.VIN IS NOT NULL THEN 'Da ban' " +
                    "ELSE p.status_product " +
                    "END AS status_display " +
                    "FROM Vehicle v " +
                    "JOIN Product p ON v.id_product = p.id_product " +
                    "LEFT JOIN Invoice_Detail d ON v.VIN = d.VIN " +
                    "ORDER BY p.price_product DESC";
            return conn.prepareStatement(sql).executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Them mot Product moi va mot Vehicle gan voi Product do.
    public boolean insertCar(String vin, String name, String brand, String color,
                             int price, int productionYear, String status) {
        try {
            Connection conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String productId = "P" + System.currentTimeMillis();
            String sqlProduct = "INSERT INTO Product(id_product, name_product, brand_product, color_product, price_product, production_year_product, status_product) VALUES (?,?,?,?,?,?,?)";
            String sqlVehicle = "INSERT INTO Vehicle(VIN, id_product) VALUES (?,?)";

            PreparedStatement psProduct = conn.prepareStatement(sqlProduct);
            psProduct.setString(1, productId);
            psProduct.setString(2, name);
            psProduct.setString(3, brand);
            psProduct.setString(4, color);
            psProduct.setInt(5, price);
            psProduct.setInt(6, productionYear);
            psProduct.setString(7, status);
            psProduct.executeUpdate();

            PreparedStatement psVehicle = conn.prepareStatement(sqlVehicle);
            psVehicle.setString(1, vin);
            psVehicle.setString(2, productId);
            psVehicle.executeUpdate();

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
