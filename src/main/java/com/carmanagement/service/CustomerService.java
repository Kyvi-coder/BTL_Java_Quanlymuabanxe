package com.carmanagement.service;

import com.carmanagement.dao.DBConnection;
import com.carmanagement.dao.customerDAO;
import com.carmanagement.entity.Customer;
import com.carmanagement.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CustomerService {
    private final customerDAO dao = new customerDAO();
    private final AuditLogService auditLogService = new AuditLogService();

    public List<Customer> getAllCustomers() {
        return dao.getAllCustomers();
    }

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

    public boolean updateCustomer(String name, String phone, String address, String id, Employee actor) {
        if (name.isEmpty() || phone.isEmpty()) {
            System.out.println("Thieu du lieu");
            return false;
        }

        Customer c = new Customer();
        c.setId_customer(id);
        c.setName_customer(name);
        c.setPhone_customer(phone);
        c.setAddress_customer(address);

        boolean updated = dao.updateCustomer(c);
        if (updated) {
            auditLogService.log(
                    actor,
                    "UPDATE_CUSTOMER",
                    "Customer",
                    id,
                    "Cap nhat khach hang: " + name + " - " + phone + " - " + address
            );
        }
        return updated;
    }

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
