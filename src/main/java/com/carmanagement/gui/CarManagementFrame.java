package com.carmanagement.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
public class CarManagementFrame extends JFrame {
    public CarManagementFrame(){
        setTitle("Car Management");
        setSize(700,400);
        setLocationRelativeTo(null);
        String[] columns = {"ID","Car Name","Brand","Price"};
        DefaultTableModel model = new DefaultTableModel(columns,0);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JPanel top = new JPanel();
        top.add(btnAdd);
        top.add(btnDelete);
        add(top,BorderLayout.NORTH);
        add(scroll,BorderLayout.CENTER);
    }
}

