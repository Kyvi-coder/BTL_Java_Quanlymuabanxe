package com.carmanagement.dao;

import com.carmanagement.entity.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class employeeDAO {

    // lấy danh sách nhân viên
    public List<Employee> getALLEmployees(){
        List<Employee> employeeList=new ArrayList<Employee>();
        String query="select * from employee";
        try(Connection conn =DBConnection.getConnection(); PreparedStatement ps=conn.prepareStatement(query); ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                Employee e = new Employee();
                e.setId_employee(rs.getString("id_employee"));
                e.setName_employee(rs.getString("name_employee"));
                e.setPhone_employee(rs.getString("phone_employee"));
                e.setPosition_employee(rs.getString("position_employee"));
                LocalDate hiredate_employee= rs.getDate("hiredate_employee").toLocalDate();
                e.setHiredate_employee(hiredate_employee);
                e.setStatus_employee(rs.getString("status_employee"));
                employeeList.add(e);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return employeeList;
    }

    // lấy danh sách nhân viên theo từng chức vụ

    public List<Employee> getEmployeesByPosition(String position){
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee\n" +
                "WHERE position_employee = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps=conn.prepareStatement(query)){
            ps.setString(1,position);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Employee e = new Employee();
                e.setId_employee(rs.getString("id_employee"));
                e.setName_employee(rs.getString("name_employee"));
                e.setPhone_employee(rs.getString("phone_employee"));
                e.setPosition_employee(rs.getString("position_employee"));
                LocalDate hiredate_employee= rs.getDate("hiredate_employee").toLocalDate();
                e.setHiredate_employee(hiredate_employee);
                e.setStatus_employee(rs.getString("status_employee"));
                employees.add(e);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employees;
    }
    // thêm nhân viên

    public Boolean insertEmployee(Employee e){
        String query = "INSERT INTO Employee(id_employee,name_employee, phone_employee, position_employee, hiredate_employee, status_employee) VALUES(?,?,?,?,?,?)";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps=conn.prepareStatement(query)){
            ps.setString(1,e.getId_employee());
            ps.setString(2,e.getName_employee());
            ps.setString(3,e.getPhone_employee());
            ps.setString(4,e.getPosition_employee());
            LocalDate date = LocalDate.now();
            ps.setDate(5,Date.valueOf(date));
            ps.setString(6, e.getStatus_employee());
            return ps.executeUpdate()>0;
        }catch (SQLException c){
            c.printStackTrace();
        }
        return false;
    }

    // thay đổi thông tin nhân viên

    public Boolean updateEmployee(Employee e){
        String query = "UPDATE Employee\n" +
                "SET name_employee = ?, phone_employee = ?, position_employee = ?\n" +
                "WHERE id_employee = ?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps=conn.prepareStatement(query)){
            ps.setString(1,e.getName_employee());
            ps.setString(2,e.getPhone_employee());
            ps.setString(3,e.getPosition_employee());
            return ps.executeUpdate()>0;

        }catch (SQLException c){
            c.printStackTrace();
        }
        return false;
    }

    // tìm nhân viên theo tên

    public List<Employee> getEmployeesByName(String name){
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM Employee WHERE name_employee = ?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps=conn.prepareStatement(query)){
            ps.setString(1,"%"+name+"%");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                Employee e = new Employee();
                e.setId_employee(rs.getString("id_employee"));
                e.setName_employee(rs.getString("name_employee"));
                e.setPhone_employee(rs.getString("phone_employee"));
                e.setPosition_employee(rs.getString("position_employee"));
                LocalDate hiredate_employee= rs.getDate("hiredate_employee").toLocalDate();
                e.setHiredate_employee(hiredate_employee);
                e.setStatus_employee(rs.getString("status_employee"));
                employees.add(e);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return employees;
    }

    // tìm kếm nhân viên theo số điện thoại
    public Employee getEmployeeByPhone(String phone){
        Employee e = new Employee();
        String query = "SELECT * FROM Employee WHERE phone_employee = ?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps=conn.prepareStatement(query)){
            ps.setString(1,phone);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                e.setId_employee(rs.getString("id_employee"));
                e.setName_employee(rs.getString("name_employee"));
                e.setPhone_employee(rs.getString("phone_employee"));
                e.setPosition_employee(rs.getString("position_employee"));
                LocalDate hiredate_employee= rs.getDate("hiredate_employee").toLocalDate();
                e.setHiredate_employee(hiredate_employee);
                e.setStatus_employee(rs.getString("status_employee"));
                return e;
            }
        }catch (SQLException c){
            c.printStackTrace();
        }
        return null;
    }

    // lấy danh sách nhân viên đang làm

    public List<Employee> getEmployeeByStatusActive(){
        List<Employee> employees = new ArrayList<>();
        String query = "  SELECT *\n" +
                "FROM Employee\n" +
                "WHERE status_employee = 'active'";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps=conn.prepareStatement(query); ResultSet rs=ps.executeQuery()){
            while(rs.next()){
                Employee e = new Employee();
                e.setId_employee(rs.getString("id_employee"));
                e.setName_employee(rs.getString("name_employee"));
                e.setPhone_employee(rs.getString("phone_employee"));
                e.setPosition_employee(rs.getString("position_employee"));
                LocalDate hiredate_employee= rs.getDate("hiredate_employee").toLocalDate();
                e.setHiredate_employee(hiredate_employee);
                e.setStatus_employee(rs.getString("status_employee"));
                employees.add(e);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employees;
    }


    // lấy danh sách nhân viên đã nghỉ làm
    public List<Employee> getEmployeeByStatusInactive(){
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT *\n" +
                "FROM Employee\n" +
                "WHERE status_employee = 'inactive'";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps=conn.prepareStatement(query); ResultSet rs=ps.executeQuery();){
            while(rs.next()){
                Employee e = new Employee();
                e.setId_employee(rs.getString("id_employee"));
                e.setName_employee(rs.getString("name_employee"));
                e.setPhone_employee(rs.getString("phone_employee"));
                e.setPosition_employee(rs.getString("position_employee"));
                LocalDate hiredate_employee= rs.getDate("hiredate_employee").toLocalDate();
                e.setHiredate_employee(hiredate_employee);
                e.setStatus_employee(rs.getString("status_employee"));
                employees.add(e);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  employees;
    }
}
