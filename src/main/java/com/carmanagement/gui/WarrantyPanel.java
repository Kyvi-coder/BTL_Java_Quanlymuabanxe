package com.carmanagement.gui;

import com.carmanagement.entity.Warranty;
import com.carmanagement.service.WarrantyService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class WarrantyPanel extends JPanel {
    JTable table;
    DefaultTableModel model;
    JTextField txtCustomer, txtCar, txtVIN, txtStart, txtEnd;
    WarrantyService service = new WarrantyService();

    public WarrantyPanel() {
        setLayout(new BorderLayout(10, 10));

        add(titlePanel(), BorderLayout.NORTH);
        add(tablePanel(), BorderLayout.CENTER);
        add(formPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel titlePanel() {
        JPanel p = new JPanel();
        JLabel title = new JLabel("QUẢN LÝ BẢO HÀNH");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        p.add(title);
        return p;
    }

    private JScrollPane tablePanel() {
        String[] cols = {"Mã BH", "Khách", "Xe", "VIN", "Ngày tạo", "Ngày hết hạn"};
        model = new DefaultTableModel(cols, 0);

        table = new JTable(model);
        table.setRowHeight(25);

        return new JScrollPane(table);
    }

    private JPanel formPanel() {
        JPanel p = new JPanel(new BorderLayout(12, 12));
        p.setBorder(new EmptyBorder(12, 12, 12, 12));

        txtCustomer = new JTextField();
        txtCustomer.setEditable(false);
        txtCar = new JTextField();
        txtCar.setEditable(false);
        txtVIN = new JTextField();
        txtStart = new JTextField();
        txtEnd = new JTextField();

        txtVIN.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { fillInfoByVIN(); }
            public void removeUpdate(DocumentEvent e) { fillInfoByVIN(); }
            public void changedUpdate(DocumentEvent e) { fillInfoByVIN(); }
        });

        JButton btnAdd = new JButton("Thêm bảo hành");
        btnAdd.addActionListener(e -> addWarranty());

        JPanel fields = new JPanel(new GridLayout(1, 5, 12, 0));
        fields.add(createFieldPanel("Khách", txtCustomer));
        fields.add(createFieldPanel("Xe", txtCar));
        fields.add(createFieldPanel("VIN", txtVIN));
        fields.add(createFieldPanel("Ngày bắt đầu", txtStart));
        fields.add(createFieldPanel("Ngày hết hạn", txtEnd));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actionPanel.add(btnAdd);

        p.add(fields, BorderLayout.CENTER);
        p.add(actionPanel, BorderLayout.SOUTH);

        return p;
    }

    private JPanel createFieldPanel(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void loadData() {
        service.deleteExpiredWarranty();
        model.setRowCount(0);
        loadRows(service.getAllWarranty());
    }

    public void refreshData() {
        loadData();
    }

    private void loadRows(List<Warranty> warranties) {
        if (warranties == null) {
            return;
        }

        for (Warranty warranty : warranties) {
            model.addRow(new Object[]{
                    warranty.getId_warranty(),
                    warranty.getCustomer_name(),
                    warranty.getCar_name(),
                    warranty.getVIN(),
                    warranty.getStart_date_warranty(),
                    warranty.getEnd_date_warranty()
            });
        }
    }

    private void fillInfoByVIN() {
        String vin = txtVIN.getText().trim();
        if (vin.isEmpty()) {
            txtCustomer.setText("");
            txtCar.setText("");
            return;
        }

        Warranty warranty = service.getWarrantyInfoByVIN(vin);
        if (warranty == null) {
            txtCustomer.setText("");
            txtCar.setText("");
            return;
        }

        txtCustomer.setText(warranty.getCustomer_name());
        txtCar.setText(warranty.getCar_name());
    }

    private void addWarranty() {
        String id = "W" + System.currentTimeMillis();
        boolean ok = service.insertWarranty(
                id,
                txtVIN.getText().trim(),
                txtStart.getText().trim(),
                txtEnd.getText().trim()
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Them bao hanh thanh cong");
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Loi");
        }
    }

    private void clearForm() {
        txtCustomer.setText("");
        txtCar.setText("");
        txtVIN.setText("");
        txtStart.setText("");
        txtEnd.setText("");
    }
}
