package com.carmanagement.service;
import com.carmanagement.database.DBConnection;
import java.sql.*;
public class EmployeeService {
    // ===== LẤY DANH SÁCH =====
    public ResultSet getAllEmployees() {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM employee";
            return conn.prepareStatement(sql).executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== TÌM KIẾM =====
    public ResultSet searchEmployee(String keyword) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM employee WHERE name LIKE ? OR phone LIKE ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===== THÊM =====
    public boolean insertEmployee(String name, String dob, String address,
                                  String phone, String position,
                                  String startDate, String status) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO employee(name, dob, address, phone, position, start_date, status) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, dob);
            ps.setString(3, address);
            ps.setString(4, phone);
            ps.setString(5, position);
            ps.setString(6, startDate);
            ps.setString(7, status);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ===== XÓA =====
    public boolean deleteEmployee(int id) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "DELETE FROM employee WHERE employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
