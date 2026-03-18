package com.carmanagement.service;
import com.carmanagement.database.DBConnection;
import java.sql.*;
import java.util.Vector;
public class CustomerService {
    // Lấy danh sách khách hàng
    public ResultSet getAllCustomers() {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT customer_name, phone, contract_id, warranty_id, car_name FROM invoice";

            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // Tìm kiếm
    public ResultSet searchCustomer(String keyword) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT customer_name, phone, contract_id, warranty_id, car_name " +
                    "FROM invoice WHERE customer_name LIKE ? OR phone LIKE ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // Cập nhật khách hàng
    public boolean updateCustomer(String name, String phone, String contractID) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "UPDATE invoice SET customer_name = ?, phone = ? WHERE contract_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, contractID);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // Xóa khách theo contract_id
    public boolean deleteCustomer(String contractID) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "DELETE FROM invoice WHERE contract_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, contractID);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

