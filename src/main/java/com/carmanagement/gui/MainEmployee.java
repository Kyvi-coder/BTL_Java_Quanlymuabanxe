package com.carmanagement.gui;

import com.carmanagement.entity.Employee;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

public class MainEmployee extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private final SalesPanel salesPanel;
    private final CustomerPanel customerPanel;
    private final InventoryPanel inventoryPanel;
    private final WarrantyPanel warrantyPanel;
    private final AuditLogPanel auditLogPanel;

    public MainEmployee(Employee currentEmployee) {
        setTitle("Employee Car Management System");
        setSize(1200, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(createTopBar(currentEmployee != null ? currentEmployee.getName_employee() : ""), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        salesPanel = new SalesPanel(currentEmployee);
        customerPanel = new CustomerPanel(currentEmployee);
        inventoryPanel = new InventoryPanel();
        warrantyPanel = new WarrantyPanel();
        auditLogPanel = new AuditLogPanel();

        contentPanel.add(new DashboardPanel(), "dashboard");
        contentPanel.add(salesPanel, "sales");
        contentPanel.add(customerPanel, "customer");
        contentPanel.add(inventoryPanel, "inventory");
        contentPanel.add(warrantyPanel, "warranty");
        contentPanel.add(auditLogPanel, "audit");

        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public MainEmployee(String username) {
        this(createEmployeeFromUsername(username));
    }

    private static Employee createEmployeeFromUsername(String username) {
        Employee employee = new Employee();
        employee.setName_employee(username);
        return employee;
    }

    private JPanel createTopBar(String username) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(100, 60));
        panel.setBackground(new Color(60, 120, 200));

        JLabel title = new JLabel("HỆ THỐNG QUẢN LÝ MUA BÁN XE", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.setOpaque(false);

        JLabel user = new JLabel("Welcome, " + username);
        user.setForeground(Color.WHITE);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> logout());

        rightPanel.add(user);
        rightPanel.add(btnLogout);

        panel.add(title, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);
        return panel;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBackground(new Color(30, 50, 80));
        sidebar.setLayout(new GridLayout(9, 1));

        ImageIcon icon = new ImageIcon("src/main/resources/images/logo.png");
        Image img = icon.getImage().getScaledInstance(120, 70, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(img));
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        sidebar.add(logo);
        sidebar.add(createMenuButton("Trang Chủ", "dashboard"));
        sidebar.add(createMenuButton("Bán Xe", "sales"));
        sidebar.add(createMenuButton("Khách Hàng", "customer"));
        sidebar.add(createMenuButton("Kho Xe", "inventory"));
        sidebar.add(createMenuButton("Bảo Hành", "warranty"));
        sidebar.add(createMenuButton("Lịch Sử", "audit"));
        return sidebar;
    }

    private JButton createMenuButton(String text, String panelName) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(30, 50, 80));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorder(null);
        btn.addActionListener(e -> {
            refreshPanelIfNeeded(panelName);
            cardLayout.show(contentPanel, panelName);
        });
        return btn;
    }

    private void refreshPanelIfNeeded(String panelName) {
        switch (panelName) {
            case "customer" -> customerPanel.refreshData();
            case "inventory" -> inventoryPanel.refreshData();
            case "warranty" -> warrantyPanel.refreshData();
            case "audit" -> auditLogPanel.refreshData();
            default -> {
            }
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "You're sure?",
                "Xac nhan",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}
