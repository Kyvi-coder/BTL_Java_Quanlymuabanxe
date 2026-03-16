package com.carmanagement.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class WarrantyFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    public WarrantyFrame(){

        setTitle("Bảo hành");
        setSize(700,500);

        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
                "Mã bảo hành",
                "Ngày lập",
                "Ngày hết hạn"
        });

        table = new JTable(model);

        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");

        JPanel top = new JPanel();

        top.add(btnAdd);
        top.add(btnUpdate);
        top.add(btnDelete);

        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);
    }
}
