package com.carmanagement.dao;

import com.carmanagement.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class customerDAO {
// lấy danh sách khách hàng
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setId_customer(rs.getString("id_customer"));
                c.setName_customer(rs.getString("name_customer"));
                c.setEmail_customer(rs.getString("email_customer"));
                c.setAddress_customer(rs.getString("address_customer"));
                c.setPhone_customer(rs.getString("phone_customer"));
                customers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
// thêm khách hàng
    public Boolean insertCustomer(Customer c) {
        String query = "INSERT INTO Customer(id_customer, name_customer, phone_customer, address_customer, email_customer) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, c.getId_customer());
            stmt.setString(2, c.getName_customer());
            stmt.setString(3, c.getPhone_customer());
            stmt.setString(4, c.getAddress_customer());
            stmt.setString(5, c.getEmail_customer());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
// tìm khách hàng theo số điện thoại
    public Customer getCustomerByPhone(String phoneCustomer) {
        String query = "SELECT * FROM Customer WHERE phone_customer = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phoneCustomer);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setId_customer(rs.getString("id_customer"));
                c.setName_customer(rs.getString("name_customer"));
                c.setEmail_customer(rs.getString("email_customer"));
                c.setAddress_customer(rs.getString("address_customer"));
                c.setPhone_customer(rs.getString("phone_customer"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // tìm khách hàng theo tên
    public List<Customer> getCustomerByName(String nameCustomer) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer WHERE name_customer LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + nameCustomer + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setId_customer(rs.getString("id_customer"));
                c.setName_customer(rs.getString("name_customer"));
                c.setEmail_customer(rs.getString("email_customer"));
                c.setAddress_customer(rs.getString("address_customer"));
                c.setPhone_customer(rs.getString("phone_customer"));
                customers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
// sửa thông tin của khách hàng, chỉ đuợc sửa số điện thoại và tên
    public Boolean updateCustomer(Customer c) {
        String query = "UPDATE Customer SET name_customer = ?, phone_customer = ?, address_customer = ? WHERE id_customer = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, c.getName_customer());
            stmt.setString(2, c.getPhone_customer());
            stmt.setString(3, c.getAddress_customer());
            stmt.setString(4, c.getId_customer());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
