package com.carmanagement.gui;
import com.carmanagement.entity.Employee;
import javax.swing.*;
import java.awt.*;
public class MainFrame extends JFrame {
    private Employee currentUser;
    private JButton btnEmployee;
    private JButton btnProduct;
    private JButton btnInvoice;
    public MainFrame(Employee emp) {
        this.currentUser = emp;
        setTitle("Car Management System");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        btnEmployee = new JButton("Manage Employee");
        btnProduct = new JButton("Manage Product");
        btnInvoice = new JButton("Invoice");
        panel.add(btnEmployee);
        panel.add(btnProduct);
        panel.add(btnInvoice);
        add(panel);
        checkRole();
    }
    // PHÂN QUYỀN
    private void checkRole() {
        if (currentUser.getPosition_employee().equalsIgnoreCase("EMPLOYEE")) {
            btnEmployee.setEnabled(false); // nhân viên không quản lý nhân viên
        }
    }
}