package com.carmanagement.gui;
import javax.swing.*;
import java.awt.*;
public class DashboardFrame extends JFrame {
    public DashboardFrame(){
        setTitle("Car Management Dashboard");
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JButton btnCar = new JButton("Manage Cars");
        JButton btnCustomer = new JButton("Customers");
        JButton btnLogout = new JButton("Logout");
        panel.add(btnCar);
        panel.add(btnCustomer);
        panel.add(btnLogout);
        add(panel);
        btnCar.addActionListener(e->{
            new CarManagementFrame().setVisible(true);
        });
        btnLogout.addActionListener(e->{
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
}
