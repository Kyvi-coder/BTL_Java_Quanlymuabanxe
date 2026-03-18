package com.carmanagement.gui;
import com.carmanagement.service.CustomerService;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
public class CustomerPanel extends JPanel {
    JTable table;
    DefaultTableModel model;
    JTextField txtSearch, txtName, txtPhone;
    CustomerService service = new CustomerService();
    public CustomerPanel() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        loadData();
    }

    // ===== TOP PANEL =====
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("QUẢN LÝ KHÁCH HÀNG");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        JPanel right = new JPanel();
        txtSearch = new JTextField(20);
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(e -> loadData());
        // 🔥 SEARCH REALTIME
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void changedUpdate(DocumentEvent e) { search(); }
        });
        right.add(new JLabel("Tìm:"));
        right.add(txtSearch);
        right.add(btnRefresh);
        panel.add(title, BorderLayout.WEST);
        panel.add(right, BorderLayout.EAST);
        return panel;
    }
    // ===== TABLE =====
    private JScrollPane createTablePanel() {
        String[] cols = {"Tên KH", "SĐT", "Mã HĐ", "Mã BH", "Xe"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> fillForm());
        return new JScrollPane(table);
    }
    // ===== BOTTOM PANEL =====
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new GridLayout(2,1,10,10));
        // FORM
        JPanel form = new JPanel();
        txtName = new JTextField(15);
        txtPhone = new JTextField(15);
        form.add(new JLabel("Tên KH:"));
        form.add(txtName);
        form.add(new JLabel("SĐT:"));
        form.add(txtPhone);
        // BUTTON
        JPanel buttons = new JPanel();
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        btnUpdate.addActionListener(e -> updateCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        panel.add(form);
        panel.add(buttons);
        return panel;
    }
    // ===== LOAD =====
    private void loadData() {
        try {
            model.setRowCount(0);
            ResultSet rs = service.getAllCustomers();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("customer_name"),
                        rs.getString("phone"),
                        rs.getString("contract_id"),
                        rs.getString("warranty_id"),
                        rs.getString("car_name")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== SEARCH =====
    private void search() {
        try {
            model.setRowCount(0);
            ResultSet rs = service.searchCustomer(txtSearch.getText());

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("customer_name"),
                        rs.getString("phone"),
                        rs.getString("contract_id"),
                        rs.getString("warranty_id"),
                        rs.getString("car_name")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== FILL =====
    private void fillForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtName.setText(model.getValueAt(row, 0).toString());
            txtPhone.setText(model.getValueAt(row, 1).toString());
        }
    }

    // ===== UPDATE =====
    private void updateCustomer() {
        int row = table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn khách!");
            return;
        }

        String contractID = model.getValueAt(row, 2).toString();

        boolean ok = service.updateCustomer(
                txtName.getText(),
                txtPhone.getText(),
                contractID
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "✔ Cập nhật thành công");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Lỗi");
        }
    }

    // ===== DELETE =====
    private void deleteCustomer() {
        int row = table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn khách cần xóa!");
            return;
        }

        String contractID = model.getValueAt(row, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Xóa khách này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = service.deleteCustomer(contractID);

            if (ok) {
                JOptionPane.showMessageDialog(this, "✔ Đã xóa");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Lỗi");
            }
        }
    }
}

