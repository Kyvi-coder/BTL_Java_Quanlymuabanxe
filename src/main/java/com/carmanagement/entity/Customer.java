package com.carmanagement.entity;

public class Customer {
    private String id_customer;
    private String name_customer;
    private int phone_customer;
    private String address_customer;
    private String email_customer;
    public  Customer(String id_customer, String name_customer, int phone_customer, String address_customer, String email_customer) {
        this.id_customer = id_customer;
        this.name_customer = name_customer;
        this.phone_customer = phone_customer;
        this.address_customer = address_customer;
        this.email_customer = email_customer;
    }
    public String getId_customer() {
        return id_customer;
    }
    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }
    public String getName_customer() {
        return name_customer;
    }
    public void setName_customer(String name_customer) {
        this.name_customer = name_customer;
    }
    public int getPhone_customer() {
        return phone_customer;
    }
    public void setPhone_customer(int phone_customer) {
        this.phone_customer = phone_customer;
    }
    public String getAddress_customer() {
        return address_customer;
    }
    public void setAddress_customer(String address_customer) {
        this.address_customer = address_customer;
    }
    public String getEmail_customer() {
        return email_customer;
    }
    public void setEmail_customer(String email_customer) {
        this.email_customer = email_customer;
    }
}
