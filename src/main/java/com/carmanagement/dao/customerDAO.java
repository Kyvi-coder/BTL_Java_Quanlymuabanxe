package com.carmanagement.dao;

import com.carmanagement.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class customerDAO {

    // hàm lấy danh sách của khách hàng

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                Customer c = new Customer(); // biến lưu tạm thời để add vào list
                c.setId_customer(rs.getString("id_customer"));
                c.setName_customer(rs.getString("name_customer"));
                c.setEmail_customer(rs.getString("email_customer"));
                c.setAddress_customer(rs.getString("address_customer"));
                c.setPhone_customer(rs.getString("phone_customer"));
                customers.add(c); // thêm user vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // hàm thêm khách hàng

    public Boolean insertCustomer(Customer c) {
        String query = "INSERT INTO Customer(id_customer, name_customer, phone_customer, adress_customer, email_customer) VALUES (?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
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

    // danh sách khách hàng còn bảo hành

    public List<Customer> getCustomerWithValidWarranty() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT DISTINCT  \n" +
                "c.name_customer,\n" +
                "p.name_product,\n" +
                "v.VIN,\n" +
                "w.end_date\n" +
                "FROM Customer c \n" +
                "JOIN Invoice i ON c.id_customer = i.id_customer\n" +
                "JOIN Invoice_Detail d ON i.id_invoice = d.id_invoice \n" +
                "JOIN Vehicle v ON d.VIN = v.VIN \n" +
                "JOIN Product p ON v.id_product = p.id_product\n" +
                "JOIN Warranty w ON v.VIN = w.VIN \n" +
                "WHERE w.end_date > CURDATE()";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
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

    // hàm tìm kiếm khách hàng theo số điện thoại
    public Customer getCustomerByPhone(String phone_customer) {
        Customer c = new Customer();
        String query = "SELECT * FROM customer WHERE id_customer = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phone_customer);
            ResultSet rs = stmt.executeQuery(phone_customer);
            if (rs.next()) {
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

    // hàm tìm kiếm khách hàng theo tên
    public  List<Customer> getCustomerByName(String name_customer) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customer WHERE name_customer LIKE ?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" +name_customer+ "%");
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
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // hàm thay đổi thông tin khách hàng
    public Boolean updateCustomer(Customer c) {
        String query = "UPDATE Customer \n" +
                "SET name_customer = ?, phone_customer = ?, adress_customer = ?\n" +
                "WHERE id_customer = ?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, c.getName_customer());
            stmt.setString(2, c.getPhone_customer());
            stmt.setString(3, c.getAddress_customer());
            stmt.setString(4, c.getAddress_customer());
            return stmt.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // hàm xem lịch sử mua hàng
}