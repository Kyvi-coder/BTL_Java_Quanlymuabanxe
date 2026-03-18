package com.carmanagement.gui;
import com.carmanagement.service.WarrantyService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.Random;

public class WarrantyPanel extends JPanel {
    JTable table;
    DefaultTableModel model;
    JTextField txtCustomer, txtCar, txtVIN, txtStart, txtEnd;
    WarrantyService service = new WarrantyService();

    public WarrantyPanel() {
        setLayout(new BorderLayout(10,10));

        add(titlePanel(), BorderLayout.NORTH);
        add(tablePanel(), BorderLayout.CENTER);
        add(formPanel(), BorderLayout.SOUTH);

        loadData();
    }

    // ===== TITLE =====
    private JPanel titlePanel() {
        JPanel p = new JPanel();
        JLabel title = new JLabel("QUẢN LÝ BẢO HÀNH");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        p.add(title);
        return p;
    }

    // ===== TABLE =====
    private JScrollPane tablePanel() {

        String[] cols = {"Mã BH", "Khách", "Xe", "VIN", "Ngày tạo", "Ngày hết hạn"};
        model = new DefaultTableModel(cols,0);

        table = new JTable(model);
        table.setRowHeight(25);

        return new JScrollPane(table);
    }

    // ===== FORM =====
    private JPanel formPanel() {
        JPanel p = new JPanel(new GridLayout(2,5,5,5));

        txtCustomer = new JTextField();
        txtCar = new JTextField();
        txtVIN = new JTextField();
        txtStart = new JTextField();
        txtEnd = new JTextField();

        JButton btnAdd = new JButton("Thêm bảo hành");

        btnAdd.addActionListener(e -> addWarranty());

        p.add(new JLabel("Khách"));
        p.add(txtCustomer);

        p.add(new JLabel("Xe"));
        p.add(txtCar);

        p.add(new JLabel("VIN"));
        p.add(txtVIN);

        p.add(new JLabel("Ngày bắt đầu"));
        p.add(txtStart);

        p.add(new JLabel("Ngày hết hạn"));
        p.add(txtEnd);

        p.add(btnAdd);

        return p;
    }

    // ===== LOAD =====
    private void loadData() {
        service.deleteExpiredWarranty(); // auto xóa

        try {
            model.setRowCount(0);

            ResultSet rs = service.getAllWarranty();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("warranty_id"),
                        rs.getString("customer_name"),
                        rs.getString("car_name"),
                        rs.getString("vin"),
                        rs.getString("start_date"),
                        rs.getString("end_date")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== ADD =====
    private void addWarranty() {

        String id = "BH" + new Random().nextInt(100000);

        boolean ok = service.insertWarranty(
                id,
                txtCustomer.getText(),
                txtCar.getText(),
                txtVIN.getText(),
                txtStart.getText(),
                txtEnd.getText()
        );

        if(ok){
            JOptionPane.showMessageDialog(this,"✔ Thêm bảo hành thành công");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this,"❌ Lỗi");
        }
    }
}

