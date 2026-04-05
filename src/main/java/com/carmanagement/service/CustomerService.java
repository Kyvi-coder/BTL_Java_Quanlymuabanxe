package com.carmanagement.service;
import com.carmanagement.dao.DBConnection;
import com.carmanagement.entity.Customer;
import com.carmanagement.dao.customerDAO;
import java.sql.*;
import java.util.List;
import java.util.Vector;
public class CustomerService {
    private customerDAO dao = new customerDAO();
    // Lấy danh sách khách hàng
    public List<Customer> getAllCustomers() {
        List<Customer> list = dao.getAllCustomers();

        return list;
    }
    // Tìm kiếm
    public ResultSet searchCustomer(String keyword) {
        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT id_customer, name_customer, phone_customer, address_customer " +
                    "FROM Customer WHERE name_customer LIKE ? OR phone_customer LIKE ?";

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
    public boolean updateCustomer(String name, String phone, String address, String id) {

        // validate đơn giản
        if(name.isEmpty() || phone.isEmpty()){
            System.out.println("Thiếu dữ liệu");
            return false;
        }

        Customer c = new Customer();
        c.setId_customer(id);
        c.setName_customer(name);
        c.setPhone_customer(phone);
        c.setAddress_customer(address);
        return dao.updateCustomer(c);
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

