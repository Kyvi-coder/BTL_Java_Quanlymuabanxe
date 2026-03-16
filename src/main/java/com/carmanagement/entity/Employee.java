/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmanagement.entity;

/**
 *
 * @author hoait
 */
import java.time.LocalDate;
public class Employee {


    private String id_employee;
    private String name_employee;
    private double phone_employee;
    private String position_employee;
    private LocalDate hiredate_employee;
    public Employee(String id_employee, String name_employee, double phone_employee, String position_employee, LocalDate hiredate_employee) {
        this.id_employee = id_employee;
        this.name_employee = name_employee;
        this.phone_employee = phone_employee;
        this.position_employee = position_employee;
        this.hiredate_employee = hiredate_employee;
    }
        public String getId_employee() {
        return id_employee;
    }

    public String getName_employee() {
        return name_employee;
    }

    public double getPhone_employee() {
        return phone_employee;
    }

    public String getPosition_employee() {
        return position_employee;
    }

    public LocalDate getHiredate_employee() {
        return hiredate_employee;
    }

    public void setId_employee(String id_employee) {
        this.id_employee = id_employee;
    }

    public void setName_employee(String name_employee) {
        this.name_employee = name_employee;
    }

    public void setPhone_employee(double phone_employee) {
        this.phone_employee = phone_employee;
    }

    public void setPosition_employee(String position_employee) {
        this.position_employee = position_employee;
    }

    public void setHiredate_employee(LocalDate hiredate_employee) {
        this.hiredate_employee = hiredate_employee;
    }
}
