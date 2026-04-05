package com.carmanagement.service;

import com.carmanagement.dao.employeeDAO;
import com.carmanagement.entity.Employee;

import java.time.LocalDate;
import java.util.List;

public class EmployeeService {
    private final employeeDAO employeeDao = new employeeDAO();

    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    public List<Employee> searchEmployee(String keyword) {
        return employeeDao.getEmployees(keyword);
    }

    public boolean insertEmployee(String name, String phone, String position,
                                  String status) {
        Employee employee = new Employee();
        employee.setId_employee("EMP" + System.currentTimeMillis());
        employee.setName_employee(name);
        employee.setPhone_employee(phone);
        employee.setPosition_employee(position);
        employee.setHiredate_employee(LocalDate.now());
        employee.setStatus_employee(status);
        return employeeDao.insertEmployee(employee);
    }

    public boolean updateEmployee(String id, String name, String phone, String position, String status) {
        Employee employee = new Employee();
        employee.setId_employee(id);
        employee.setName_employee(name);
        employee.setPhone_employee(phone);
        employee.setPosition_employee(position);
        employee.setStatus_employee(status);
        return employeeDao.updateEmployee(employee);
    }
}
