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
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public class HomeFrame extends JFrame {
    JPanel contentPanel;
    CardLayout cardLayout;
    private final Map<String, JPanel> loadedPanels = new HashMap<>();
    private final Employee currentEmployee;
    private JLabel userLabel;

    public HomeFrame(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
        setTitle("Car Management System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createTopBar(currentEmployee.getName_employee()), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        add(contentPanel, BorderLayout.CENTER);

        showPanel("dashboard");
        setVisible(true);
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

        userLabel = new JLabel("Welcome, " + username);
        userLabel.setForeground(Color.WHITE);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> logout());
        rightPanel.add(userLabel);
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
        sidebar.add(createMenuButton("Nhân Viên", "employee"));
        sidebar.add(createMenuButton("Doanh Số", "revenue"));
        sidebar.add(createMenuButton("Lịch Sử", "audit"));

        return sidebar;
    }

    private JButton createMenuButton(String text, String panelName) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(30, 50, 80));
        btn.setForeground(Color.WHITE);
        btn.setBorder(null);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.addActionListener(e -> showPanel(panelName));
        return btn;
    }

    private void showPanel(String panelName) {
        if (!loadedPanels.containsKey(panelName)) {
            JPanel panel = createPanel(panelName);
            loadedPanels.put(panelName, panel);
            contentPanel.add(panel, panelName);
        }
        refreshPanelIfNeeded(loadedPanels.get(panelName));
        cardLayout.show(contentPanel, panelName);
    }

    private JPanel createPanel(String panelName) {
        try {
            return switch (panelName) {
                case "dashboard" -> new DashboardPanel();
                case "sales" -> new SalesPanel(currentEmployee);
                case "customer" -> new CustomerPanel(currentEmployee);
                case "inventory" -> new InventoryPanel();
                case "warranty" -> new WarrantyPanel();
                case "employee" -> new EmployeePanel(currentEmployee, this::refreshCurrentEmployeeViews);
                case "revenue" -> new RevenuePanel();
                case "audit" -> new AuditLogPanel();
                default -> createErrorPanel("Panel khong ton tai: " + panelName);
            };
        } catch (Exception ex) {
            ex.printStackTrace();
            return createErrorPanel("Khong the tai muc nay. Kiem tra lai query/database.");
        }
    }

    private JPanel createErrorPanel(String message) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(message);
        label.setForeground(Color.RED);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        panel.add(label);
        return panel;
    }

    private void refreshCurrentEmployeeViews() {
        if (userLabel != null) {
            userLabel.setText("Welcome, " + currentEmployee.getName_employee());
        }

        JPanel salesPanel = loadedPanels.get("sales");
        if (salesPanel instanceof SalesPanel panel) {
            panel.refreshCurrentEmployee();
            panel.revalidate();
            panel.repaint();
        }
    }

    private void refreshPanelIfNeeded(JPanel panel) {
        if (panel instanceof CustomerPanel customerPanel) {
            customerPanel.refreshData();
            return;
        }
        if (panel instanceof InventoryPanel inventoryPanel) {
            inventoryPanel.refreshData();
            return;
        }
        if (panel instanceof WarrantyPanel warrantyPanel) {
            warrantyPanel.refreshData();
            return;
        }
        if (panel instanceof AuditLogPanel auditLogPanel) {
            auditLogPanel.refreshData();
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
