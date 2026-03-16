/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmanagement.entity;

/**
 *
 * @author hoait
 */
public class Invoice_Detail {
    private String id_detail;
    private String id_product;
    private float price_detail;
    private String name_product;
    private double stock_quantity_product;

    public Invoice_Detail(String id_detail, String id_product, float price_detail, String name_product, double stock_quantity_product) {
        this.id_detail = id_detail;
        this.id_product = id_product;
        this.price_detail = price_detail;
        this.name_product = name_product;
        this.stock_quantity_product = stock_quantity_product;
    }

    public String getId_detail() {
        return id_detail;
    }

    public String getId_product() {
        return id_product;
    }

    public float getPrice_detail() {
        return price_detail;
    }

    public String getName_product() {
        return name_product;
    }

    public double getStock_quantity_product() {
        return stock_quantity_product;
    }

    public void setId_detail(String id_detail) {
        this.id_detail = id_detail;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public void setPrice_detail(float price_detail) {
        this.price_detail = price_detail;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public void setStock_quantity_product(double stock_quantity_product) {
        this.stock_quantity_product = stock_quantity_product;
    }
    
}
