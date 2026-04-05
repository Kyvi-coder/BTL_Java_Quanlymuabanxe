package com.carmanagement.gui;

import com.carmanagement.entity.Employee;
import com.carmanagement.service.LoginService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class LoginFrame extends JFrame {
    private static final Color ACCENT = new Color(201, 138, 45);
    private static final Color SURFACE = new Color(248, 246, 241);
    private static final Color TEXT_PRIMARY = new Color(28, 37, 45);
    private static final Color TEXT_MUTED = new Color(103, 110, 117);

    JTextField txtUser;
    JPasswordField txtPass;

    public LoginFrame() {
        setTitle("Car Management System");
        setSize(980, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BackgroundPanel panel = new BackgroundPanel();
        panel.setLayout(new GridBagLayout());

        JPanel card = new RoundedPanel(30);
        card.setOpaque(false);
        card.setLayout(new GridLayout(1, 2));
        card.setPreferredSize(new Dimension(860, 470));

        JPanel brandPanel = buildBrandPanel();
        JPanel formPanel = buildFormPanel();
        card.add(brandPanel);
        card.add(formPanel);

        panel.add(card);
        add(panel);
    }

    private JPanel buildBrandPanel() {
        JPanel brandPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint paint = new GradientPaint(
                        0, 0, new Color(10, 29, 46, 235),
                        getWidth(), getHeight(), new Color(18, 68, 93, 225)
                );
                g2.setPaint(paint);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        brandPanel.setOpaque(false);
        brandPanel.setLayout(new BorderLayout());
        brandPanel.setBorder(new EmptyBorder(38, 38, 38, 38));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel eyebrow = new JLabel("CAR DEALERSHIP PLATFORM");
        eyebrow.setForeground(new Color(223, 228, 233));
        eyebrow.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JLabel title = new JLabel("<html>Manage sales,<br>inventory and staff<br>in one place.</html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 34));

        JLabel desc = new JLabel("<html>Đăng nhập để truy cập hệ thống quản lý mua bán xe với quy trình làm việc rõ ràng.</html>");
        desc.setForeground(new Color(225, 229, 233));
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JLabel logoLabel = new JLabel(loadScaledIcon("/images/logo.png", 120, 120));
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(logoLabel);
        content.add(Box.createVerticalStrut(18));
        content.add(eyebrow);
        content.add(Box.createVerticalStrut(16));
        content.add(title);
        content.add(Box.createVerticalStrut(20));
        content.add(desc);
        content.add(Box.createVerticalGlue());

        JLabel footer = new JLabel("Secure access for managers and employees");
        footer.setForeground(new Color(205, 214, 220));
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        brandPanel.add(content, BorderLayout.CENTER);
        brandPanel.add(footer, BorderLayout.SOUTH);
        return brandPanel;
    }

    private JPanel buildFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setBackground(SURFACE);
        formPanel.setBorder(new EmptyBorder(54, 52, 54, 52));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Login");
        title.setForeground(TEXT_PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));

        JLabel subtitle = new JLabel("Nhập tài khoản được cấp để tiếp tục");
        subtitle.setForeground(TEXT_MUTED);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        txtUser = new JTextField();
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUser.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(214, 217, 220), 1),
                new EmptyBorder(12, 14, 12, 14)
        ));
        txtUser.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

        txtPass = new JPasswordField();
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(214, 217, 220), 1),
                new EmptyBorder(12, 14, 12, 14)
        ));
        txtPass.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));

        JButton btnLogin = new JButton("Login");
        stylePrimaryButton(btnLogin);
        JButton btnReset = new JButton("Reset");
        styleSecondaryButton(btnReset);

        btnLogin.addActionListener(e -> login());
        btnReset.addActionListener(e -> {
            txtUser.setText("");
            txtPass.setText("");
        });

        formPanel.add(title);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(subtitle);
        formPanel.add(Box.createVerticalStrut(34));
        formPanel.add(createFieldBlock("Username", txtUser));
        formPanel.add(Box.createVerticalStrut(18));
        formPanel.add(createFieldBlock("Password", txtPass));
        formPanel.add(Box.createVerticalStrut(30));
        formPanel.add(createButtonRow(btnLogin, btnReset));
        formPanel.add(Box.createVerticalStrut(18));


        return formPanel;
    }

    private JPanel createFieldBlock(String labelText, JComponent field) {
        JPanel block = new JPanel();
        block.setOpaque(false);
        block.setLayout(new BoxLayout(block, BoxLayout.Y_AXIS));
        block.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setForeground(TEXT_PRIMARY);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        block.add(label);
        block.add(Box.createVerticalStrut(8));
        block.add(field);
        return block;
    }

    private JPanel createButtonRow(JButton btnLogin, JButton btnReset) {
        JPanel row = new JPanel(new GridLayout(1, 2, 12, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(btnLogin);
        row.add(btnReset);
        return row;
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(ACCENT);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(new Color(233, 235, 237));
        button.setForeground(TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private ImageIcon loadScaledIcon(String resourcePath, int width, int height) {
        URL resource = getClass().getResource(resourcePath);
        if (resource == null) {
            return null;
        }
        Image image = new ImageIcon(resource).getImage();
        return new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    private void login() {
        String user = txtUser.getText();
        String pass = new String(txtPass.getPassword());

        if (user.equals("admin") && pass.equals("123")) {
            Employee employee = new Employee();
            employee.setId_employee("ADMIN");
            employee.setName_employee("admin");
            employee.setPhone_employee("");
            employee.setPosition_employee("Admin");
            employee.setStatus_employee("active");
            JOptionPane.showMessageDialog(this, "Login Success (Test Account)");
            openMainScreen(employee);
            this.dispose();
            return;
        }

        LoginService service = new LoginService();
        Employee employee = service.getEmployeeByAccount(user, pass);
        if (employee != null) {
            JOptionPane.showMessageDialog(this, "Login Success");
            openMainScreen(employee);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Wrong username or password");
        }
    }

    private void openMainScreen(Employee employee) {
        String role = employee.getPosition_employee();
        if (role == null) {
            new MainEmployee(employee).setVisible(true);
            return;
        }

        if (role.equalsIgnoreCase("manager") || role.equalsIgnoreCase("admin")) {
            new HomeFrame(employee).setVisible(true);
            return;
        }

        if (role.equalsIgnoreCase("staff") || role.equalsIgnoreCase("employee")) {
            new MainEmployee(employee).setVisible(true);
            return;
        }

        new MainEmployee(employee).setVisible(true);
    }

    private static class RoundedPanel extends JPanel {
        private final int radius;

        private RoundedPanel(int radius) {
            this.radius = radius;
            setBorder(new EmptyBorder(0, 0, 0, 0));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(11, 18, 24, 42));
            g2.fillRoundRect(10, 12, getWidth() - 20, getHeight() - 14, radius, radius);
            g2.setColor(new Color(255, 255, 255, 245));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
