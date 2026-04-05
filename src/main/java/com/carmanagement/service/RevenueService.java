package com.carmanagement.service;
import com.carmanagement.dao.DBConnection;
import java.sql.*;
public class RevenueService {

    // ===== TỔNG DOANH THU =====
    public double getTotalRevenue(int month, int year) {
        double total = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT SUM(price * quantity) AS total " +
                    "FROM invoice WHERE MONTH(created_at)=? AND YEAR(created_at)=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, year);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    // ===== SỐ XE ĐÃ BÁN =====
    public int getTotalCarsSold(int month, int year) {
        int total = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT SUM(quantity) AS total FROM invoice " +
                    "WHERE MONTH(created_at)=? AND YEAR(created_at)=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, year);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}

