package com.carmanagement.service;
import com.carmanagement.database.DBConnection;
import java.sql.*;
public class WarrantyService {
    // ===== LẤY DANH SÁCH =====
    public ResultSet getAllWarranty() {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM warranty";
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // ===== THÊM BẢO HÀNH =====
    public boolean insertWarranty(String id, String customer, String car, String vin,
                                  String startDate, String endDate) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO warranty(warranty_id, customer_name, car_name, vin, start_date, end_date) VALUES (?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, customer);
            ps.setString(3, car);
            ps.setString(4, vin);
            ps.setString(5, startDate);
            ps.setString(6, endDate);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // ===== XÓA BẢO HÀNH HẾT HẠN 2 THÁNG =====
    public void deleteExpiredWarranty() {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "DELETE FROM warranty WHERE end_date < DATE_SUB(NOW(), INTERVAL 2 MONTH)";

            conn.prepareStatement(sql).executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

