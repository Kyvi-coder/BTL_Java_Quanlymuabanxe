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
public class Invoice {
    private String id_invoice;
    private String id_customer;
    private String id_employee;
    private LocalDate date_invoice;
    private double total_amount_invoice;
    Payment payment;

    public Invoice(String id_invoice, String id_customer, String id_employee, LocalDate date_invoice, double total_amount_invoice, Payment payment) {
        this.id_invoice = id_invoice;
        this.id_customer = id_customer;
        this.id_employee = id_employee;

        this.date_invoice = date_invoice;
        this.total_amount_invoice = total_amount_invoice;
        this.payment = payment;
    }
    public Invoice() {}

    public String getId_invoice() {
        return id_invoice;
    }

    public String getId_customer() {
        return id_customer;
    }

    public String getId_employee() {
        return id_employee;
    }


    public LocalDate getDate_invoice() {
        return date_invoice;
    }

    public double getTotal_amount_invoice() {
        return total_amount_invoice;
    }

    public void setId_invoice(String id_invoice) {
        this.id_invoice = id_invoice;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public void setId_employee(String id_employee) {
        this.id_employee = id_employee;
    }


    public void setDate_invoice(LocalDate date_invoice) {
        this.date_invoice = date_invoice;
    }

    public Payment getPayment() {
        return payment;
    }
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setTotal_amount_invoice(double total_amount_invoice) {
        this.total_amount_invoice = total_amount_invoice;
    }
    
}
