package com.carmanagement.service;

import com.carmanagement.dao.invoiceDAO;
import com.carmanagement.entity.Invoice;

import java.util.List;

public class RevenueService {
    private final invoiceDAO invoiceDAO = new invoiceDAO();

    public double getTotalRevenue(int month, int year) {
        return invoiceDAO.getTotalRevenueByMonthYear(month, year);
    }

    public int getTotalCarsSold(int month, int year) {
        return invoiceDAO.getTotalCarsSoldByMonthYear(month, year);
    }

    public List<Invoice> getInvoicesByMonthYear(int month, int year) {
        return invoiceDAO.getInvoicesByMonthYear(month, year);
    }
}
