package com.carmanagement.gui;

import com.carmanagement.entity.Employee;
import com.carmanagement.service.SaleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SalesPanel extends JPanel {

    JTextField txtName, txtPhone, txtAddress;
    JComboBox<String> cbGender, cbCustomerType;

    JTextField txtCarName, txtBrand, txtQuantity;
    JComboBox<String> cbWarranty;

    JTextField txtEmpName, txtPosition, txtEmpPhone;

    JComboBox<String> cbPayment;
    private final Employee currentEmployee;

    public SalesPanel(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("BÁN XE", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(10, 0, 10, 0));

        add(title, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        mainPanel.add(createLeftPanel());
        mainPanel.add(createRightPanel());

        add(mainPanel, BorderLayout.CENTER);
    }

    public void refreshCurrentEmployee() {
        if (txtEmpName == null || txtPosition == null || txtEmpPhone == null) {
            return;
        }

        if (currentEmployee != null) {
            txtEmpName.setText(currentEmployee.getName_employee());
            txtPosition.setText(currentEmployee.getPosition_employee());
            txtEmpPhone.setText(currentEmployee.getPhone_employee());
        } else {
            txtEmpName.setText("");
            txtPosition.setText("");
            txtEmpPhone.setText("");
        }
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.add(createCustomerPanel());
        panel.add(createCarPanel());
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.add(createEmployeePanel());
        panel.add(createPaymentPanel());
        return panel;
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        txtName = new JTextField();
        txtPhone = new JTextField();
        txtAddress = new JTextField();

        cbGender = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cbCustomerType = new JComboBox<>(new String[]{"Khách mới", "Khách cũ"});

        panel.add(new JLabel("Tên:"));
        panel.add(txtName);
        panel.add(new JLabel("SĐT:"));
        panel.add(txtPhone);
        panel.add(new JLabel("Địa chỉ:"));
        panel.add(txtAddress);
        panel.add(new JLabel("Giới tính:"));
        panel.add(cbGender);
        panel.add(new JLabel("Loại KH:"));
        panel.add(cbCustomerType);
        panel.add(new JLabel("Ngày mua:"));
        panel.add(new JLabel("Tự động"));

        return panel;
    }

    private JPanel createCarPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin xe"));

        txtCarName = new JTextField();
        txtBrand = new JTextField();
        txtQuantity = new JTextField();

        cbWarranty = new JComboBox<>(new String[]{
                "6 tháng", "1 năm", "2 năm"
        });

        panel.add(new JLabel("Tên xe:"));
        panel.add(txtCarName);
        panel.add(new JLabel("Hãng:"));
        panel.add(txtBrand);
        panel.add(new JLabel("Số lượng:"));
        panel.add(txtQuantity);
        panel.add(new JLabel("Bảo hành:"));
        panel.add(cbWarranty);

        return panel;
    }

    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Nhân viên"));

        txtEmpName = new JTextField();
        txtPosition = new JTextField();
        txtEmpPhone = new JTextField();

        txtEmpName.setEditable(false);
        txtPosition.setEditable(false);
        txtEmpPhone.setEditable(false);

        refreshCurrentEmployee();

        panel.add(new JLabel("Tên NV:"));
        panel.add(txtEmpName);
        panel.add(new JLabel("Chức vụ:"));
        panel.add(txtPosition);
        panel.add(new JLabel("SĐT:"));
        panel.add(txtEmpPhone);

        return panel;
    }

    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 2, 5, 5));
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

    private void createOrder() {
        if (txtName.getText().isEmpty() || txtPhone.getText().isEmpty()
                || txtAddress.getText().isEmpty() || txtCarName.getText().isEmpty()
                || txtBrand.getText().isEmpty() || txtQuantity.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin!");
            return;
        }

        try {
            int quantity = Integer.parseInt(txtQuantity.getText().trim());
            String contractID = "HD" + System.currentTimeMillis();

            SaleService service = new SaleService();
            SaleService.SaleResult result = service.createFullOrder(
                    contractID,
                    currentEmployee != null ? currentEmployee.getId_employee() : null,
                    currentEmployee,
                    txtName.getText().trim(),
                    txtPhone.getText().trim(),
                    txtAddress.getText().trim(),
                    txtCarName.getText().trim(),
                    txtBrand.getText().trim(),
                    quantity,
                    cbPayment.getSelectedItem().toString(),
                    cbWarranty.getSelectedItem().toString()
            );

            JOptionPane.showMessageDialog(this,
                    "Tao hop dong thanh cong!\n" +
                            "Ma HD: " + result.invoiceId() +
                            "\nKhach hang: " + result.customerId() +
                            "\nVIN da ban: " + String.join(", ", result.soldVins()) +
                            "\nBao hanh: " + String.join(", ", result.warrantyIds()) +
                            "\nTong tien: " + result.totalAmount() +
                            "\nSo xe con lai: " + result.remainingStock()
            );

            clearForm();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "So luong phai la so hop le!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Loi khi tao hop dong!");
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        txtCarName.setText("");
        txtBrand.setText("");
        txtQuantity.setText("");
        cbWarranty.setSelectedIndex(0);
        cbPayment.setSelectedIndex(0);
    }
}
