package com.carmanagement.gui;

import com.carmanagement.entity.Customer;
import com.carmanagement.entity.Employee;
import com.carmanagement.service.CustomerService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.util.List;

public class CustomerPanel extends JPanel {
    JTable table;
    DefaultTableModel model;
    JTextField txtSearch, txtName, txtPhone, txtAddress;
    CustomerService service = new CustomerService();
    private final Employee currentEmployee;

    public CustomerPanel() {
        this(null);
    }

    public CustomerPanel(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        loadData();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel right = new JPanel();
        txtSearch = new JTextField(20);
        JButton btnRefresh = new JButton("Refresh");

        btnRefresh.addActionListener(e -> loadData());

        // Tim kiem realtime theo ten hoac so dien thoai.
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void changedUpdate(DocumentEvent e) { search(); }
        });

        right.add(new JLabel("Tìm Kiếm:"));
        right.add(txtSearch);
        right.add(btnRefresh);

        panel.add(title, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] cols = {"Tên KH", "SĐT", "Mã KH", "Địa chỉ"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> fillForm());
        return new JScrollPane(table);
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new GridLayout(2,1,10,10));

        JPanel form = new JPanel();
        txtName = new JTextField(12);
        txtPhone = new JTextField(12);
        txtAddress = new JTextField(16);

        form.add(new JLabel("Tên KH:"));
        form.add(txtName);
        form.add(new JLabel("SĐT:"));
        form.add(txtPhone);
        form.add(new JLabel("Địa chỉ:"));
        form.add(txtAddress);

        JPanel buttons = new JPanel();
        JButton btnUpdate = new JButton("Cập nhật");
        btnUpdate.addActionListener(e -> updateCustomer());
        buttons.add(btnUpdate);

        panel.add(form);
        panel.add(buttons);
        return panel;
    }

    private void loadData() {
        // Reset bang truoc khi nap lai de tranh bi lap dong.
        model.setRowCount(0);
        List<Customer> list = service.getAllCustomers();

        for (Customer c : list) {
            model.addRow(new Object[]{
                    c.getName_customer(),
                    c.getPhone_customer(),
                    c.getId_customer(),
                    c.getAddress_customer()
            });
        }
    }

    public void refreshData() {
        loadData();
    }

    private void search() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        try {
            model.setRowCount(0);
            ResultSet rs = service.searchCustomer(keyword);
            if (rs == null) {
                return;
            }

            // Search hien tai tra ve thong tin tu bang Customer.
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("name_customer"),
                        rs.getString("phone_customer"),
                        rs.getString("id_customer"),
                        rs.getString("address_customer")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtName.setText(model.getValueAt(row, 0).toString());
            txtPhone.setText(model.getValueAt(row, 1).toString());
            txtAddress.setText(model.getValueAt(row, 3).toString());
        }
    }

    private void updateCustomer() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chon khach!");
            return;
        }

        String customerID = model.getValueAt(row, 2).toString();
        boolean ok = service.updateCustomer(
                txtName.getText(),
                txtPhone.getText(),
                txtAddress.getText(),
                customerID,
                currentEmployee
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Cap nhat thanh cong");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Loi");
        }
    }
}
