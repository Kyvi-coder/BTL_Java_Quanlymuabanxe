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
    private String id_invoice;
    private String VIN;
    private double stock_quantity_product;

    public Invoice_Detail( String id_invoice,String VIN, double stock_quantity_product) {
        this.id_invoice = id_invoice;
        this.VIN = VIN;
        this.stock_quantity_product = stock_quantity_product;
    }
    public Invoice_Detail() {}
    public String getId_invoice() {
        return id_invoice;
    }
    public void setId_invoice(String id_invoice) {
        this.id_invoice = id_invoice;
    }
    public String getVIN() {
        return VIN;
    }
    public void setVIN(String VIN) {
        this.VIN = VIN;
    }


    public double getStock_quantity_product() {
        return stock_quantity_product;
    }



    public void setStock_quantity_product(double stock_quantity_product) {
        this.stock_quantity_product = stock_quantity_product;
    }
    
}
