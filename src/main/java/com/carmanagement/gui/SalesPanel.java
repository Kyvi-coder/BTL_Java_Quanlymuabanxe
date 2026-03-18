package com.carmanagement.gui;

import com.carmanagement.service.SaleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

public class SalesPanel extends JPanel {

    // ===== CUSTOMER =====
    JTextField txtName, txtPhone, txtAddress;
    JComboBox<String> cbGender, cbCustomerType;

    // ===== CAR =====
    JTextField txtCarName, txtBrand, txtQuantity;
    JComboBox<String> cbWarranty;

    // ===== EMPLOYEE =====
    JTextField txtEmpName, txtPosition, txtEmpPhone;

    // ===== PAYMENT =====
    JComboBox<String> cbPayment;

    public SalesPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("BÁN XE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(10,0,10,0));

        add(title, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        mainPanel.add(createLeftPanel());
        mainPanel.add(createRightPanel());

        add(mainPanel, BorderLayout.CENTER);
    }

    // ================= LEFT =================
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new GridLayout(2,1,10,10));

        panel.add(createCustomerPanel());
        panel.add(createCarPanel());

        return panel;
    }

    // ================= RIGHT =================
    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new GridLayout(2,1,10,10));

        panel.add(createEmployeePanel());
        panel.add(createPaymentPanel());

        return panel;
    }

    // ================= CUSTOMER =================
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new GridLayout(6,2,5,5));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        txtName = new JTextField();
        txtPhone = new JTextField();
        txtAddress = new JTextField();

        cbGender = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cbCustomerType = new JComboBox<>(new String[]{"Khách mới", "Khách cũ"});

        panel.add(new JLabel("Tên:")); panel.add(txtName);
        panel.add(new JLabel("SĐT:")); panel.add(txtPhone);
        panel.add(new JLabel("Địa chỉ:")); panel.add(txtAddress);
        panel.add(new JLabel("Giới tính:")); panel.add(cbGender);
        panel.add(new JLabel("Loại KH:")); panel.add(cbCustomerType);

        panel.add(new JLabel("Ngày mua:"));
        panel.add(new JLabel("Tự động")); // auto

        return panel;
    }

    // ================= CAR =================
    private JPanel createCarPanel() {
        JPanel panel = new JPanel(new GridLayout(5,2,5,5));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin xe"));

        txtCarName = new JTextField();
        txtBrand = new JTextField();
        txtQuantity = new JTextField();

        cbWarranty = new JComboBox<>(new String[]{
                "6 tháng", "1 năm", "2 năm"
        });

        panel.add(new JLabel("Tên xe:")); panel.add(txtCarName);
        panel.add(new JLabel("Hãng:")); panel.add(txtBrand);
        panel.add(new JLabel("Số lượng:")); panel.add(txtQuantity);
        panel.add(new JLabel("Bảo hành:")); panel.add(cbWarranty);

        return panel;
    }

    // ================= EMPLOYEE =================
    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new GridLayout(4,2,5,5));
        panel.setBorder(BorderFactory.createTitledBorder("Nhân viên"));

        txtEmpName = new JTextField();
        txtPosition = new JTextField();
        txtEmpPhone = new JTextField();

        panel.add(new JLabel("Tên NV:")); panel.add(txtEmpName);
        panel.add(new JLabel("Chức vụ:")); panel.add(txtPosition);
        panel.add(new JLabel("SĐT:")); panel.add(txtEmpPhone);

        return panel;
    }

    // ================= PAYMENT =================
    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2,2,5,5));
        form.setBorder(BorderFactory.createTitledBorder("Thanh toán"));

        cbPayment = new JComboBox<>(new String[]{
                "Tiền mặt", "Thẻ", "Trả góp"
        });

        form.add(new JLabel("Phương thức:"));
        form.add(cbPayment);

        JButton btnCreate = new JButton("Tạo hợp đồng");

        btnCreate.addActionListener(e -> createOrder());

        panel.add(form, BorderLayout.CENTER);
        panel.add(btnCreate, BorderLayout.SOUTH);

        return panel;
    }

    // ================= LOGIC =================
    private void createOrder() {

        // Validate đơn giản
        if(txtName.getText().isEmpty() || txtPhone.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        String contractID = "HD" + System.currentTimeMillis();
        String warrantyID = "BH" + new Random().nextInt(100000);

        try {
            SaleService service = new SaleService();

            service.createFullOrder(
                    contractID,
                    warrantyID,
                    txtName.getText(),
                    txtPhone.getText(),
                    txtAddress.getText(),
                    txtCarName.getText(),
                    cbPayment.getSelectedItem().toString()
            );

            JOptionPane.showMessageDialog(this,
                    "✅ Tạo hợp đồng thành công!\n"
                            + "Mã HĐ: " + contractID
                            + "\nMã BH: " + warrantyID
            );

            clearForm();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi tạo hợp đồng!");
        }
    }

    private void clearForm(){
        txtName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtCarName.setText("");
        txtBrand.setText("");
        txtQuantity.setText("");
        txtEmpName.setText("");
        txtPosition.setText("");
        txtEmpPhone.setText("");
    }
}