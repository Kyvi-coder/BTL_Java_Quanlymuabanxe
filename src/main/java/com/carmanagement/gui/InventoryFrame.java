package com.carmanagement.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventoryFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    JTextField txtSearch;

    public InventoryFrame(){

        setTitle("Kho xe");
        setSize(700,500);

        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
                "ID",
                "Tên xe",
                "Hãng",
                "VIN",
                "Số lượng"
        });

        table = new JTable(model);

        JPanel top = new JPanel();

        txtSearch = new JTextField(15);

        JButton btnSearch = new JButton("Tìm");
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");

        top.add(txtSearch);
        top.add(btnSearch);
        top.add(btnAdd);
        top.add(btnUpdate);
        top.add(btnDelete);

        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);
    }
}