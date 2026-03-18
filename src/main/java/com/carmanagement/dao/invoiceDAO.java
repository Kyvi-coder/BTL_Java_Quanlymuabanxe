package com.carmanagement.dao;

import com.carmanagement.entity.Invoice;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class invoiceDAO {
    // lây hóa đơn

    public List<Invoice> selectAll() {
        List<Invoice> list = new ArrayList<>();
        String  sql = "select * from invoice";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                Invoice invoice = new Invoice();
                invoice.setId_invoice(rs.getString("id_invoice"));
                invoice.setId_customer(rs.getString("id_customer"));
                invoice.setId_employee(rs.getString("id_employee"));
                invoice.setTotal_amount_invoice(rs.getInt("total_amount_invoice"));
                LocalDateTime date_invoice = rs.getTimestamp("date_invoice").toLocalDateTime();
                invoice.setDate_invoice(date_invoice);
                invoice.setPayment(rs.getString("payment_method"));
                list.add(invoice);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    // hàm check VIN đã bán chưa

    public boolean isVinSold(Connection conn, String vin) {
        String sql = "SELECT 1 FROM Invoice_Detail WHERE VIN = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vin);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // có → đã bán
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // tạo hóa đơn
    public boolean addInvoiceFull(Invoice invoice, List<String> listVIN) {
        String insertInvoice = "INSERT INTO Invoice(" +
                "id_invoice, date_invoice, total_amount_invoice, payment_method, id_customer, id_employee) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        String insertDetail = "INSERT INTO Invoice_Detail(id_invoice, VIN, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); //  bắt đầu transaction

            // 1. thêm invoice
            PreparedStatement psInvoice = conn.prepareStatement(insertInvoice);
            psInvoice.setString(1, invoice.getId_invoice());
            psInvoice.setTimestamp(2, Timestamp.valueOf(invoice.getDate_invoice()));
            psInvoice.setInt(3, invoice.getTotal_amount_invoice());
            psInvoice.setString(4, invoice.getPayment());
            psInvoice.setString(5, invoice.getId_customer());
            psInvoice.setString(6, invoice.getId_employee());

            psInvoice.executeUpdate();

            // 2. thêm từng VIN vào Invoice_Detail
            PreparedStatement psDetail = conn.prepareStatement(insertDetail);

            for (String vin : listVIN) {

                // check VIN đã bán chưa
                if (isVinSold(conn, vin)) {
                    conn.rollback();
                    return false;
                }

                psDetail.setString(1, invoice.getId_invoice());
                psDetail.setString(2, vin);
                psDetail.setInt(3, 1); // mỗi xe = 1

                psDetail.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // chi tiết hóa đơn

    // tìm hóa đơn theo khách hàng, nhaan viên
    // tính tổng tiền hóa đơn
}
