package com.carmanagement.gui;
import com.carmanagement.service.InventoryService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
public class InventoryPanel extends JPanel {
    JTable table;
    DefaultTableModel model;
    JTextField txtSearch;
    // form thêm xe
    JTextField txtVIN, txtName, txtBrand, txtColor, txtPrice, txtDate, txtQty;

    InventoryService service = new InventoryService();

    public InventoryPanel() {
        setLayout(new BorderLayout(10,10));

        add(topPanel(), BorderLayout.NORTH);
        add(tablePanel(), BorderLayout.CENTER);
        add(bottomPanel(), BorderLayout.SOUTH);

        loadData();
    }

    // ===== TOP =====
    private JPanel topPanel() {
        JPanel p = new JPanel();

        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Tìm");
        JButton btnSortBrand = new JButton("Sort hãng");
        JButton btnSortAsc = new JButton("Giá ↑");
        JButton btnSortDesc = new JButton("Giá ↓");

        btnSearch.addActionListener(e -> search());
        btnSortBrand.addActionListener(e -> load(service.sortByBrand()));
        btnSortAsc.addActionListener(e -> load(service.sortByPriceAsc()));
        btnSortDesc.addActionListener(e -> load(service.sortByPriceDesc()));

        p.add(new JLabel("Tìm:"));
        p.add(txtSearch);
        p.add(btnSearch);
        p.add(btnSortBrand);
        p.add(btnSortAsc);
        p.add(btnSortDesc);

        return p;
    }

    // ===== TABLE =====
    private JScrollPane tablePanel() {
        String[] cols = {"VIN","Tên","Hãng","ID","Màu","Giá","Ngày nhập","Số lượng"};

        model = new DefaultTableModel(cols,0);
        table = new JTable(model);

        return new JScrollPane(table);
    }

    // ===== BOTTOM =====
    private JPanel bottomPanel() {
        JPanel p = new JPanel(new GridLayout(3,6,5,5));

        txtVIN = new JTextField();
        txtName = new JTextField();
        txtBrand = new JTextField();
        txtColor = new JTextField();
        txtPrice = new JTextField();
        txtDate = new JTextField();
        txtQty = new JTextField();

        JButton btnAdd = new JButton("Thêm xe");

        btnAdd.addActionListener(e -> addCar());

        p.add(new JLabel("VIN")); p.add(txtVIN);
        p.add(new JLabel("Tên")); p.add(txtName);
        p.add(new JLabel("Hãng")); p.add(txtBrand);

        p.add(new JLabel("Màu")); p.add(txtColor);
        p.add(new JLabel("Giá")); p.add(txtPrice);
        p.add(new JLabel("Ngày")); p.add(txtDate);

        p.add(new JLabel("Số lượng")); p.add(txtQty);
        p.add(btnAdd);

        return p;
    }

    // ===== LOAD =====
    private void loadData() {
        load(service.getAllCars());
    }

    private void load(ResultSet rs) {
        try {
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("vin"),
                        rs.getString("product_name"),
                        rs.getString("brand"),
                        rs.getInt("product_id"),
                        rs.getString("color"),
                        rs.getDouble("price"),
                        rs.getString("import_date"),
                        rs.getInt("quantity")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ===== SEARCH =====
    private void search() {
        load(service.searchCar(txtSearch.getText()));
    }
    // ===== ADD =====
    private void addCar() {
        boolean ok = service.insertCar(
                txtVIN.getText(),
                txtName.getText(),
                txtBrand.getText(),
                txtColor.getText(),
                Double.parseDouble(txtPrice.getText()),
                txtDate.getText(),
                Integer.parseInt(txtQty.getText())
        );
        if(ok){
            JOptionPane.showMessageDialog(this,"✔ Thêm xe thành công");
            service.deleteOutOfStock();
            loadData();
        } else {
            JOptionPane.showMessageDialog(this,"❌ Lỗi");
        }
    }
}

