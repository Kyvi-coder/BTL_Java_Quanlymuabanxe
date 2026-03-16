package com.carmanagement.entity;

import java.time.LocalDate;

public class Payment {
    private String id_payment;
    private String id_invoice;
    private String payment_method;
    private LocalDate date_payment;
    private Integer amount;
    public Payment(String id_payment, String id_invoice, String payment_method, LocalDate date_payment, Integer amount) {
        this.id_payment = id_payment;
        this.id_invoice = id_invoice;
        this.payment_method = payment_method;
        this.date_payment = date_payment;
        this.amount = amount;
    }

    public Payment() {}
    public String getId_payment() {
        return id_payment;
    }
    public void setId_payment(String id_payment) {
        this.id_payment = id_payment;
    }
    public String getId_invoice() {
        return id_invoice;
    }
    public void setId_invoice(String id_invoice) {
        this.id_invoice = id_invoice;
    }
    public String getPayment_method() {
        return payment_method;
    }
    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
    public LocalDate getDate_payment() {
        return date_payment;
    }
    public void setDate_payment(LocalDate date_payment) {
        this.date_payment = date_payment;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
