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
public class Warranty {
    private String id_warranty;
    private String id_product;
    private LocalDate start_date_warranty;
    private LocalDate end_date_warranty;
    private String status_warranty;

    public Warranty(String id_warranty, String id_product, LocalDate start_date_warranty, LocalDate end_date_warranty, String status_warranty) {
        this.id_warranty = id_warranty;
        this.id_product = id_product;
        this.start_date_warranty = start_date_warranty;
        this.end_date_warranty = end_date_warranty;
        this.status_warranty = status_warranty;
    }

    public String getId_warranty() {
        return id_warranty;
    }

    public String getId_product() {
        return id_product;
    }

    public LocalDate getStart_date_warranty() {
        return start_date_warranty;
    }

    public LocalDate getEnd_date_warranty() {
        return end_date_warranty;
    }

    public String getStatus_warranty() {
        return status_warranty;
    }

    public void setId_warranty(String id_warranty) {
        this.id_warranty = id_warranty;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public void setStart_date_warranty(LocalDate start_date_warranty) {
        this.start_date_warranty = start_date_warranty;
    }

    public void setEnd_date_warranty(LocalDate end_date_warranty) {
        this.end_date_warranty = end_date_warranty;
    }

    public void setStatus_warranty(String status_warranty) {
        this.status_warranty = status_warranty;
    }
    
}
