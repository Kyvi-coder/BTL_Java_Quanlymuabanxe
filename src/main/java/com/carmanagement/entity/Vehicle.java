package com.carmanagement.entity;

public class Vehicle extends Product {
    private String VIN;

    public Vehicle(String id_product, String name_product, String brand_product, Integer price_product, String color_product, int prodution_year_product, String VIN) {
        super(id_product, name_product, brand_product, price_product, color_product,  prodution_year_product);
        this.VIN = VIN;
    }
    public String getVIN() {
        return VIN;
    }
    public void setVIN(String VIN) {
        this.VIN = VIN;
    }
}
