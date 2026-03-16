package com.carmanagement.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    JTextField txtSearchName;
    JTextField txtSearchPhone;

    public CustomerFrame(){

        setTitle("Quản lý khách hàng");
        setSize(700,500);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Tên","SĐT","Địa chỉ","Hợp đồng"
        });

        table = new JTable(model);

        JPanel top = new JPanel();

        txtSearchName = new JTextField(10);
        txtSearchPhone = new JTextField(10);

        JButton btnSearch = new JButton("Tìm");

        top.add(new JLabel("Tên"));
        top.add(txtSearchName);

        top.add(new JLabel("SĐT"));
        top.add(txtSearchPhone);

        top.add(btnSearch);

        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);
    }
}
