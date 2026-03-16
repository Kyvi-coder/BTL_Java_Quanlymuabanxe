package com.carmanagement.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmployeeFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    public EmployeeFrame(){

        setTitle("Quản lý nhân viên");
        setSize(900,500);

        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
                "Tên",
                "Ngày sinh",
                "CCCD",
                "SĐT",
                "Chức vụ",
                "Ngày vào làm",
                "Số hợp đồng",
                "Trạng thái",
                "Ngày nghỉ"
        });

        table = new JTable(model);

        JPanel top = new JPanel();

        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");

        top.add(btnAdd);
        top.add(btnUpdate);
        top.add(btnDelete);

        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);
    }
}

