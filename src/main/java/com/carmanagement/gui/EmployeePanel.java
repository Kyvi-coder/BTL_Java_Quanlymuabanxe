package com.carmanagement.gui;
import com.carmanagement.service.EmployeeService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
public class EmployeePanel extends JPanel {

    JTable table;
    DefaultTableModel model;

    JTextField txtSearch;

    JTextField txtName, txtDob, txtAddress, txtPhone, txtPosition, txtStart;
    JComboBox<String> cbStatus;

    EmployeeService service = new EmployeeService();

    public EmployeePanel() {
        setLayout(new BorderLayout(10,10));

        add(topPanel(), BorderLayout.NORTH);
        add(tablePanel(), BorderLayout.CENTER);
        add(formPanel(), BorderLayout.SOUTH);

        loadData();
    }

    // ===== TOP =====
    private JPanel topPanel() {
        JPanel p = new JPanel();

        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Tìm");

        btnSearch.addActionListener(e -> search());

        p.add(new JLabel("Tìm:"));
        p.add(txtSearch);
        p.add(btnSearch);

        return p;
    }

    // ===== TABLE =====
    private JScrollPane tablePanel() {

        String[] cols = {"ID","Tên","Ngày sinh","Quê quán","SĐT","Chức vụ","Ngày vào","Trạng thái"};

        model = new DefaultTableModel(cols,0);
        table = new JTable(model);
        table.setRowHeight(25);

        return new JScrollPane(table);
    }

    // ===== FORM =====
    private JPanel formPanel() {

        JPanel p = new JPanel(new GridLayout(3,6,5,5));

        txtName = new JTextField();
        txtDob = new JTextField();
        txtAddress = new JTextField();
        txtPhone = new JTextField();
        txtPosition = new JTextField();
        txtStart = new JTextField();

        cbStatus = new JComboBox<>(new String[]{"Đang làm","Đã nghỉ"});

        JButton btnAdd = new JButton("Thêm");
        JButton btnDelete = new JButton("Xóa");

        btnAdd.addActionListener(e -> addEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());

        p.add(new JLabel("Tên")); p.add(txtName);
        p.add(new JLabel("Ngày sinh")); p.add(txtDob);
        p.add(new JLabel("Quê quán")); p.add(txtAddress);

        p.add(new JLabel("SĐT")); p.add(txtPhone);
        p.add(new JLabel("Chức vụ")); p.add(txtPosition);
        p.add(new JLabel("Ngày vào")); p.add(txtStart);

        p.add(new JLabel("Trạng thái")); p.add(cbStatus);
        p.add(btnAdd);
        p.add(btnDelete);

        return p;
    }

    // ===== LOAD =====
    private void loadData() {
        try {
            model.setRowCount(0);

            ResultSet rs = service.getAllEmployees();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("dob"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("position"),
                        rs.getString("start_date"),
                        rs.getString("status")
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

            ResultSet rs = service.searchEmployee(txtSearch.getText());

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getString("dob"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("position"),
                        rs.getString("start_date"),
                        rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== ADD =====
    private void addEmployee() {

        boolean ok = service.insertEmployee(
                txtName.getText(),
                txtDob.getText(),
                txtAddress.getText(),
                txtPhone.getText(),
                txtPosition.getText(),
                txtStart.getText(),
                cbStatus.getSelectedItem().toString()
        );

        if(ok){
            JOptionPane.showMessageDialog(this,"✔ Thêm thành công");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this,"❌ Lỗi");
        }
    }

    // ===== DELETE =====
    private void deleteEmployee() {

        int row = table.getSelectedRow();

        if(row < 0){
            JOptionPane.showMessageDialog(this,"Chọn nhân viên!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        boolean ok = service.deleteEmployee(id);

        if(ok){
            JOptionPane.showMessageDialog(this,"✔ Đã xóa");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this,"❌ Lỗi");
        }
    }
}

