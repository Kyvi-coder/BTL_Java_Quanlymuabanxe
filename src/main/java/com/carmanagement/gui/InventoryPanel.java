package com.carmanagement.gui;

import com.carmanagement.service.InventoryService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class InventoryPanel extends JPanel {
    JTable table;
    DefaultTableModel model;
    JTextField txtSearch;
    JTextField txtVIN, txtName, txtBrand, txtColor, txtPrice, txtYear;
    JComboBox<String> cbStatus;

    InventoryService service = new InventoryService();

    public InventoryPanel() {
        setLayout(new BorderLayout(10, 10));

        add(topPanel(), BorderLayout.NORTH);
        add(tablePanel(), BorderLayout.CENTER);
        add(bottomPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel topPanel() {
        JPanel p = new JPanel();

        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Tìm");
        JButton btnSortBrand = new JButton("Sort hãng");
        JButton btnSortAsc = new JButton("Giá tăng");
        JButton btnSortDesc = new JButton("Giá giảm");

        btnSearch.addActionListener(e -> search());
        btnSortBrand.addActionListener(e -> loadTable(service.sortByBrand()));
        btnSortAsc.addActionListener(e -> loadTable(service.sortByPriceAsc()));
        btnSortDesc.addActionListener(e -> loadTable(service.sortByPriceDesc()));

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void changedUpdate(DocumentEvent e) { search(); }
        });

        p.add(new JLabel("Tìm kiếm:"));
        p.add(txtSearch);
        p.add(btnSearch);
        p.add(btnSortBrand);
        p.add(btnSortAsc);
        p.add(btnSortDesc);

        return p;
    }

    private JScrollPane tablePanel() {
        String[] cols = {"VIN", "Mã sản phẩm", "Tên xe", "Hãng", "Mẫu", "Giá", "Năm sản xuất", "Trạng thái"};

        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        return new JScrollPane(table);
    }

    private JPanel bottomPanel() {
        JPanel wrapper = new JPanel(new BorderLayout(10, 10));
        wrapper.setBorder(BorderFactory.createTitledBorder("Thêm xe mới"));

        JPanel form = new JPanel(new GridLayout(4, 4, 10, 10));

        txtVIN = new JTextField();
        txtName = new JTextField();
        txtBrand = new JTextField();
        txtColor = new JTextField();
        txtPrice = new JTextField();
        txtYear = new JTextField();
        cbStatus = new JComboBox<>(new String[]{"Chưa bán", "Đã bán", "Bảo trì"});

        JButton btnAdd = new JButton("Thêm xe");
        btnAdd.addActionListener(e -> addCar());

        form.add(new JLabel("VIN"));
        form.add(txtVIN);
        form.add(new JLabel("Tên xe"));
        form.add(txtName);
        form.add(new JLabel("Hãng"));
        form.add(txtBrand);
        form.add(new JLabel("Mẫu"));
        form.add(txtColor);
        form.add(new JLabel("Giá"));
        form.add(txtPrice);
        form.add(new JLabel("Năm sản xuất"));
        form.add(txtYear);
        form.add(new JLabel("Trạng thái"));
        form.add(cbStatus);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.add(btnAdd);

        wrapper.add(form, BorderLayout.CENTER);
        wrapper.add(actions, BorderLayout.SOUTH);
        return wrapper;
    }

    private void loadData() {
        loadTable(service.getAllCars());
    }

    public void refreshData() {
        loadData();
    }

    private void loadTable(ResultSet rs) {
        try {
            model.setRowCount(0);
            if (rs == null) {
                return;
            }

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("VIN"),
                        rs.getString("id_product"),
                        rs.getString("name_product"),
                        rs.getString("brand_product"),
                        rs.getString("color_product"),
                        rs.getInt("price_product"),
                        rs.getInt("production_year_product"),
                        rs.getString("status_display")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void search() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        loadTable(service.searchCar(keyword));
    }

    private void addCar() {
        boolean ok = service.insertCar(
                txtVIN.getText().trim(),
                txtName.getText().trim(),
                txtBrand.getText().trim(),
                txtColor.getText().trim(),
                Integer.parseInt(txtPrice.getText().trim()),
                Integer.parseInt(txtYear.getText().trim()),
                cbStatus.getSelectedItem().toString()
        );
        if (ok) {
            JOptionPane.showMessageDialog(this, "Them xe thanh cong");
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Loi");
        }
    }

    private void clearForm() {
        txtVIN.setText("");
        txtName.setText("");
        txtBrand.setText("");
        txtColor.setText("");
        txtPrice.setText("");
        txtYear.setText("");
        cbStatus.setSelectedItem("Chưa bán");
    }
}
