package com.carmanagement.gui;

import com.carmanagement.entity.Employee;
import com.carmanagement.service.LoginService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    JTextField txtUser;
    JPasswordField txtPass;

    public LoginFrame() {
        setTitle("Car Management System");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        com.carmanagement.gui.BackgroundPanel panel = new com.carmanagement.gui.BackgroundPanel();
        panel.setLayout(new GridBagLayout());
        JPanel form = new JPanel();
        form.setLayout(new GridLayout(4, 2, 10, 10));
        form.setBackground(new Color(255, 255, 255, 200));
        JLabel title = new JLabel("LOGIN", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        txtUser = new JTextField();
        txtPass = new JPasswordField();
        JButton btnLogin = new JButton("Login");
        JButton btnReset = new JButton("Reset");
        form.add(new JLabel("Username"));
        form.add(txtUser);
        form.add(new JLabel("Password"));
        form.add(txtPass);
        form.add(btnLogin);
        form.add(btnReset);
        panel.add(form);
        add(panel);
        btnLogin.addActionListener(e -> login());
        btnReset.addActionListener(e -> {
            txtUser.setText("");
            txtPass.setText("");
        });
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
}
