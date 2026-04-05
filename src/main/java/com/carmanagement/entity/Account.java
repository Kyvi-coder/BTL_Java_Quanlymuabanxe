package com.carmanagement.entity;

public class Account {
    private String id_account;
    private String username;
    private String password;
    private String role;
    private String id_employee;

    public Account() {
    }

    public Account(String id_account, String username, String password, String role, String id_employee) {
        this.id_account = id_account;
        this.username = username;
        this.password = password;
        this.role = role;
        this.id_employee = id_employee;
    }

    public String getId_account() {
        return id_account;
    }

    public void setId_account(String id_account) {
        this.id_account = id_account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId_employee() {
        return id_employee;
    }

    public void setId_employee(String id_employee) {
        this.id_employee = id_employee;
    }
}
