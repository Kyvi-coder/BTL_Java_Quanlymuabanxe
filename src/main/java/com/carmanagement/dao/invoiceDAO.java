package com.carmanagement.dao;

import com.carmanagement.entity.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                invoice.setPayment(rs.getString("payment"));
                list.add(invoice);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    // chi tiết hóa đơn



    // tạo hóa đơn
    // thêm chi tiết hóa đơn
    // tìm hóa đơn theo khách hàng, nhaan viên
    // tính tổng tiền hóa đơn
}
