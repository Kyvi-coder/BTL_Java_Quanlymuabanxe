package com.carmanagement.dao;

import com.carmanagement.entity.Employee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class employeeDAO {

    public List<Employee> getAllEmployees()
    {
        return getALLEmployees();
    }
// lấy toàn bộ danh sách nhân viên

    public List<Employee> getALLEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM Employee";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                employeeList.add(mapEmployee(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeList;
    }
    // tìm nhân viên theo id
    public Employee getEmployeeById(String idEmployee) {
        String query = "SELECT * FROM Employee WHERE id_employee = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, idEmployee);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapEmployee(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // tìm nhân viên theo chức vụ
    public List<Employee> getEmployeesByPosition(String position) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE position_employee = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, position);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
// thêm nhân viên
    public Boolean insertEmployee(Employee e) {
        String query = "INSERT INTO Employee(id_employee, name_employee, phone_employee, position_employee, hiredate_employee, status_employee) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, e.getId_employee());
            ps.setString(2, e.getName_employee());
            ps.setString(3, e.getPhone_employee());
            ps.setString(4, e.getPosition_employee());
            LocalDate date = e.getHiredate_employee() != null ? e.getHiredate_employee() : LocalDate.now();
            ps.setDate(5, Date.valueOf(date));
            ps.setString(6, e.getStatus_employee());
            return ps.executeUpdate() > 0;
        } catch (SQLException c) {
            c.printStackTrace();
        }
        return false;
    }
// thay đổi thông tin nhân viên
    public Boolean updateEmployee(Employee e) {
        String query = "UPDATE Employee " +
                "SET name_employee = ?, phone_employee = ?, position_employee = ?, status_employee = ? " +
                "WHERE id_employee = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, e.getName_employee());
            ps.setString(2, e.getPhone_employee());
            ps.setString(3, e.getPosition_employee());
            ps.setString(4, e.getStatus_employee());
            ps.setString(5, e.getId_employee());
            return ps.executeUpdate() > 0;
        } catch (SQLException c) {
            c.printStackTrace();
        }
        return false;
    }

    public List<Employee> getEmployees(String name) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE id_employee LIKE ? OR name_employee LIKE ? OR phone_employee LIKE ? OR position_employee LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            String keyword = "%" + name + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            ps.setString(4, keyword);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<Employee> getEmployeeByStatusActive() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE status_employee = 'active'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public List<Employee> getEmployeeByStatusInactive() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE status_employee = 'inactive'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                employees.add(mapEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private Employee mapEmployee(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setId_employee(rs.getString("id_employee"));
        e.setName_employee(rs.getString("name_employee"));
        e.setPhone_employee(rs.getString("phone_employee"));
        e.setPosition_employee(rs.getString("position_employee"));
        Date hireDate = rs.getDate("hiredate_employee");
        if (hireDate != null) {
            e.setHiredate_employee(hireDate.toLocalDate());
        }
        e.setStatus_employee(rs.getString("status_employee"));
        return e;
    }
}
