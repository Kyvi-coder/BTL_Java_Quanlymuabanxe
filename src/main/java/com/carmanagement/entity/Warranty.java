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
    private LocalDate start_date_warranty;
    private LocalDate end_date_warranty;
    private String VIN;

    public Warranty(String id_warranty, LocalDate start_date_warranty, LocalDate end_date_warranty, String VIN) {
        this.id_warranty = id_warranty;

        this.start_date_warranty = start_date_warranty;
        this.end_date_warranty = end_date_warranty;
        this.VIN = VIN;
    }

    public String getId_warranty() {
        return id_warranty;
    }



    public LocalDate getStart_date_warranty() {
        return start_date_warranty;
    }

    public LocalDate getEnd_date_warranty() {
        return end_date_warranty;
    }

    public String getVIN() {
        return VIN;
    }

    public void setId_warranty(String id_warranty) {
        this.id_warranty = id_warranty;
    }



    public void setStart_date_warranty(LocalDate start_date_warranty) {
        this.start_date_warranty = start_date_warranty;
    }

    public void setEnd_date_warranty(LocalDate end_date_warranty) {
        this.end_date_warranty = end_date_warranty;
    }

    public void setVIN(String status_warranty) {
        this.VIN = VIN;
    }
    
}
