package com.carmanagement.dao;

import com.carmanagement.entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class accountDAO {
    public Account getAccountByCredentials(String username, String password) {
        String sql = "SELECT id_account, username, password, role, id_employee " +
                "FROM Account WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId_account(rs.getString("id_account"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setRole(rs.getString("role"));
                account.setId_employee(rs.getString("id_employee"));
                return account;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
