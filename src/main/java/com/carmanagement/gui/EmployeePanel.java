package com.carmanagement.gui;

import com.carmanagement.service.EmployeeService;
import com.carmanagement.entity.Employee;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeePanel extends JPanel {

    JTable table;
    DefaultTableModel model;

    JTextField txtSearch;
    JTextField txtName, txtPhone, txtPosition;
    JComboBox<String> cbStatus;

    EmployeeService service = new EmployeeService();
    private final Employee currentEmployee;
    private final Runnable onCurrentEmployeeUpdated;

    public EmployeePanel() {
        this(null, null);
    }

    public EmployeePanel(Employee currentEmployee, Runnable onCurrentEmployeeUpdated) {
        this.currentEmployee = currentEmployee;
        this.onCurrentEmployeeUpdated = onCurrentEmployeeUpdated;
        setLayout(new BorderLayout(10, 10));

        add(topPanel(), BorderLayout.NORTH);
        add(tablePanel(), BorderLayout.CENTER);
        add(formPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel topPanel() {
        JPanel p = new JPanel();

        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Tìm");
        btnSearch.addActionListener(e -> search());

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { search(); }
            public void removeUpdate(DocumentEvent e) { search(); }
            public void changedUpdate(DocumentEvent e) { search(); }
        });

        p.add(new JLabel("Tìm:"));
        p.add(txtSearch);
        p.add(btnSearch);
        return p;
    }

    private JScrollPane tablePanel() {
        String[] cols = {"ID", "Tên", "SĐT", "Chức vụ", "Ngày vào", "Trạng thái"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.getSelectionModel().addListSelectionListener(this::fillFormFromSelectedRow);
        return new JScrollPane(table);
    }

    private JPanel formPanel() {
        JPanel p = new JPanel(new GridLayout(3, 4, 5, 5));

        txtName = new JTextField();
        txtPhone = new JTextField();
        txtPosition = new JTextField();
        cbStatus = new JComboBox<>(new String[]{"active", "inactive"});

        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Cập nhật");
        btnAdd.addActionListener(e -> addEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());

        p.add(new JLabel("Tên"));
        p.add(txtName);
        p.add(new JLabel("SĐT"));
        p.add(txtPhone);
        p.add(new JLabel("Chức vụ"));
        p.add(txtPosition);
        p.add(new JLabel("Trạng thái"));
        p.add(cbStatus);
        p.add(btnAdd);
        p.add(btnUpdate);

        return p;
    }

    private void loadData() {
        try {
            model.setRowCount(0);
            loadRows(service.getAllEmployees());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void search() {
        try {
            model.setRowCount(0);
            String keyword = txtSearch.getText().trim();
            List<Employee> employees = keyword.isEmpty()
                    ? service.getAllEmployees()
                    : service.searchEmployee(keyword);
            loadRows(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRows(List<Employee> employees) {
        if (employees == null) {
            return;
        }

        for (Employee employee : employees) {
            model.addRow(new Object[]{
                    employee.getId_employee(),
                    employee.getName_employee(),
                    employee.getPhone_employee(),
                    employee.getPosition_employee(),
                    employee.getHiredate_employee(),
                    employee.getStatus_employee()
            });
        }
    }

    private void fillFormFromSelectedRow(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            return;
        }

        int row = table.getSelectedRow();
        if (row < 0) {
            return;
        }

        txtName.setText(model.getValueAt(row, 1).toString());
        txtPhone.setText(model.getValueAt(row, 2).toString());
        txtPosition.setText(model.getValueAt(row, 3).toString());
        cbStatus.setSelectedItem(model.getValueAt(row, 5).toString());
    }

    private void addEmployee() {
        boolean ok = service.insertEmployee(
                txtName.getText().trim(),
                txtPhone.getText().trim(),
                txtPosition.getText().trim(),
                cbStatus.getSelectedItem().toString(),
                currentEmployee
        );

        if (ok) {
            JOptionPane.showMessageDialog(this, "Them thanh cong");
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Loi");
        }
    }

    private void updateEmployee() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chon nhan vien de cap nhat!");
            return;
        }

        String id = model.getValueAt(row, 0).toString();
        boolean ok = service.updateEmployee(
                id,
                txtName.getText().trim(),
                txtPhone.getText().trim(),
                txtPosition.getText().trim(),
                cbStatus.getSelectedItem().toString(),
                currentEmployee
        );

        if (ok) {
            syncCurrentEmployeeIfNeeded(id);
            JOptionPane.showMessageDialog(this, "Cap nhat thanh cong");
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Loi");
        }
    }

    private void syncCurrentEmployeeIfNeeded(String updatedId) {
        if (currentEmployee == null || updatedId == null) {
            return;
        }

        if (!updatedId.equals(currentEmployee.getId_employee())) {
            return;
        }

        currentEmployee.setName_employee(txtName.getText().trim());
        currentEmployee.setPhone_employee(txtPhone.getText().trim());
        currentEmployee.setPosition_employee(txtPosition.getText().trim());
        currentEmployee.setStatus_employee(cbStatus.getSelectedItem().toString());

        if (onCurrentEmployeeUpdated != null) {
            onCurrentEmployeeUpdated.run();
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtPhone.setText("");
        txtPosition.setText("");
        cbStatus.setSelectedItem("active");
        table.clearSelection();
    }
}
