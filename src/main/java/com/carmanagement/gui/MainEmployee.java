package com.carmanagement.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class MainEmployee extends JFrame{
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainEmployee(String username){
        this.setTitle("Employee Car Managerment System");
        this.setSize(1200,700);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.add(this.createTopBar(username), "North");
        this.add(this.createSidebar(), "West");
        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(this.cardLayout);
        this.contentPanel.add(new DashboardPanel(), "dashboard");
        this.contentPanel.add(new SalesPanel(), "sales");
        this.contentPanel.add(new CustomerPanel(), "customer");
        this.contentPanel.add(new InventoryPanel(), "inventory");
        this.contentPanel.add(new WarrantyPanel(), "warranty");
        this.add(this.contentPanel, "Center");
        this.setVisible(true);
    }
    private JPanel createTopBar(String username) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(100, 60));
        panel.setBackground(new Color(60, 120, 200));
        JLabel title = new JLabel("HỆ THỐNG QUẢN LÝ MUA BÁN XE", 0);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", 1, 20));
        JLabel user = new JLabel("Welcome, " + username + "   ");
        user.setForeground(Color.WHITE);
        panel.add(title, "Center");
        panel.add(user, "East");
        return panel;
    }
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBackground(new Color(30, 50, 80));
        sidebar.setLayout(new GridLayout(8, 1));
        ImageIcon icon = new ImageIcon("src/main/resources/images/logo.png");
        Image img = icon.getImage().getScaledInstance(120, 70, 4);
        JLabel logo = new JLabel(new ImageIcon(img));
        logo.setHorizontalAlignment(0);
        sidebar.add(logo);
        sidebar.add(this.createMenuButton("Trang chủ", "dashboard"));
        sidebar.add(this.createMenuButton("Bán xe", "sales"));
        sidebar.add(this.createMenuButton("Khách hàng", "customer"));
        sidebar.add(this.createMenuButton("Kho xe", "inventory"));
        sidebar.add(this.createMenuButton("Bảo hành", "warranty"));
        return sidebar;
    }
    private JButton createMenuButton(String text, String panelName) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(30, 50, 80));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBorder(null);
        btn.addActionListener((e) -> this.cardLayout.show(this.contentPanel, panelName));
        return btn;
    }
}