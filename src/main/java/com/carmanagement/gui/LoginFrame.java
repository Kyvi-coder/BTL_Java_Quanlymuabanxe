package com.carmanagement.gui;
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

            LoginService service = new LoginService();

            String role = service.login(user, pass);

            if (role == null) {

                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu");

            } else if (role.equals("admin")) {

                JOptionPane.showMessageDialog(this, "Đăng nhập quản lý thành công");

                new HomeFrame(role).setVisible(true);
                this.dispose();

            } else if (role.equals("employee")) {

                JOptionPane.showMessageDialog(this, "Đăng nhập nhân viên thành công");

                new HomeFrame(role).setVisible(true);
                this.dispose();
            }
        }
    }
