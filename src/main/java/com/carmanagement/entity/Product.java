/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.carmanagement.entity;

/**
 *
 * @author hoait
 */
public class Product {
    private String id_product;
    private String name_product;
    private String brand_product;
    private Integer price_product;
    private String color_product;
    private int production_year_product;
    private String status_product;

    public Product(String id_product, String name_product, String brand_product, Integer price_product, String color_product,int production_year_product, String status_product) {
        this.id_product = id_product;
        this.name_product = name_product;
        this.brand_product = brand_product;
        this.price_product = price_product;
        this.color_product = color_product;
        this.production_year_product = production_year_product;
        this.status_product = status_product;
    }

    public Product() {}
    public String getId_product() {
        return id_product;
    }

    public String getName_product() {
        return name_product;
    }

    public String getBrand_product() {
        return brand_product;
    }

    public Integer getPrice_product() {
        return price_product;
    }

    public String getColor_product() {
        return color_product;
    }




    public int getProduction_year_product() {
        return production_year_product;
    }

    public String getStatus_product() {
        return status_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public void setBrand_product(String brand_product) {
        this.brand_product = brand_product;
    }

    public void setPrice_product(Integer pirce_product) {
        this.price_product = pirce_product;
    }

    public void setColor_product(String color_product) {
        this.color_product = color_product;
    }



    public void setProduction_year_product(int production_year_product) {
        this.production_year_product = production_year_product;
    }

    public void setStatus_product(String status_product) {
        this.status_product = status_product;
    }
}
