package com.carmanagement.gui;
import javax.swing.*;
import java.awt.*;
public class HomeFrame extends JFrame {
    private String role;

    JButton btnSale;
    JButton btnCustomer;
    JButton btnInventory;
    JButton btnWarranty;
    JButton btnEmployee;
    JButton btnRevenue;

    public HomeFrame(String role){

        this.role = role;

        setTitle("Car Management System");
        setSize(1200,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI(){

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,2,20,20));

        btnSale = new JButton("Bán Xe");
        btnCustomer = new JButton("Khách Hàng");
        btnInventory = new JButton("Kho Xe");
        btnWarranty = new JButton("Bảo Hành");
        btnEmployee = new JButton("Nhân Viên");
        btnRevenue = new JButton("Doanh Số");

        mainPanel.add(btnSale);
        mainPanel.add(btnCustomer);
        mainPanel.add(btnInventory);
        mainPanel.add(btnWarranty);

        if(role.equals("admin")){
            mainPanel.add(btnEmployee);
            mainPanel.add(btnRevenue);
        }

        add(mainPanel,BorderLayout.CENTER);

        btnSale.addActionListener(e -> new SaleFrame().setVisible(true));
        btnCustomer.addActionListener(e -> new CustomerFrame().setVisible(true));
        btnInventory.addActionListener(e -> new InventoryFrame().setVisible(true));
        btnWarranty.addActionListener(e -> new WarrantyFrame().setVisible(true));

        btnEmployee.addActionListener(e -> new EmployeeFrame().setVisible(true));
        btnRevenue.addActionListener(e -> new RevenueFrame().setVisible(true));
    }
}

