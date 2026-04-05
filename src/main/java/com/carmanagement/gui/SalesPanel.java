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

        JLabel title = new JLabel("BAN XE", JLabel.CENTER);
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
        panel.setBorder(BorderFactory.createTitledBorder("Thong tin khach hang"));

        txtName = new JTextField();
        txtPhone = new JTextField();
        txtAddress = new JTextField();

        cbGender = new JComboBox<>(new String[]{"Nam", "Nu"});
        cbCustomerType = new JComboBox<>(new String[]{"Khach moi", "Khach cu"});

        panel.add(new JLabel("Ten:"));
        panel.add(txtName);
        panel.add(new JLabel("SDT:"));
        panel.add(txtPhone);
        panel.add(new JLabel("Dia chi:"));
        panel.add(txtAddress);
        panel.add(new JLabel("Gioi tinh:"));
        panel.add(cbGender);
        panel.add(new JLabel("Loai KH:"));
        panel.add(cbCustomerType);
        panel.add(new JLabel("Ngay mua:"));
        panel.add(new JLabel("Tu dong"));

        return panel;
    }

    private JPanel createCarPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Thong tin xe"));

        txtCarName = new JTextField();
        txtBrand = new JTextField();
        txtQuantity = new JTextField();

        cbWarranty = new JComboBox<>(new String[]{
                "6 thang", "1 nam", "2 nam"
        });

        panel.add(new JLabel("Ten xe:"));
        panel.add(txtCarName);
        panel.add(new JLabel("Hang:"));
        panel.add(txtBrand);
        panel.add(new JLabel("So luong:"));
        panel.add(txtQuantity);
        panel.add(new JLabel("Bao hanh:"));
        panel.add(cbWarranty);

        return panel;
    }

    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Nhan vien"));

        txtEmpName = new JTextField();
        txtPosition = new JTextField();
        txtEmpPhone = new JTextField();

        txtEmpName.setEditable(false);
        txtPosition.setEditable(false);
        txtEmpPhone.setEditable(false);

        refreshCurrentEmployee();

        panel.add(new JLabel("Ten NV:"));
        panel.add(txtEmpName);
        panel.add(new JLabel("Chuc vu:"));
        panel.add(txtPosition);
        panel.add(new JLabel("SDT:"));
        panel.add(txtEmpPhone);

        return panel;
    }

    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(2, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Thanh toan"));

        cbPayment = new JComboBox<>(new String[]{
                "Tien mat", "The", "Tra gop"
        });

        form.add(new JLabel("Phuong thuc:"));
        form.add(cbPayment);

        JButton btnCreate = new JButton("Tao hop dong");
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
