package com.carmanagement.gui;
import javax.swing.*;
import java.awt.*;
import com.carmanagement.service.SaleService;
public class SaleFrame extends JFrame {

    JTextField txtCustomerName;
    JTextField txtPhone;
    JTextField txtAddress;
    JComboBox genderBox;
    JComboBox customerTypeBox;

    JTextField txtCarName;
    JTextField txtBrand;
    JTextField txtQuantity;

    JComboBox warrantyBox;

    JTextField txtEmployeeName;
    JTextField txtEmployeePhone;
    JTextField txtPosition;

    JComboBox paymentMethod;

    public SaleFrame(){

        setTitle("Bán xe");
        setSize(800,600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0,2,10,10));

        panel.add(new JLabel("Tên khách hàng"));
        txtCustomerName = new JTextField();
        panel.add(txtCustomerName);

        panel.add(new JLabel("SĐT"));
        txtPhone = new JTextField();
        panel.add(txtPhone);

        panel.add(new JLabel("Địa chỉ"));
        txtAddress = new JTextField();
        panel.add(txtAddress);

        panel.add(new JLabel("Giới tính"));
        genderBox = new JComboBox(new String[]{"Nam","Nữ"});
        panel.add(genderBox);

        panel.add(new JLabel("Loại khách"));
        customerTypeBox = new JComboBox(new String[]{"Khách mới","Khách cũ"});
        panel.add(customerTypeBox);

        panel.add(new JLabel("Tên xe"));
        txtCarName = new JTextField();
        panel.add(txtCarName);

        panel.add(new JLabel("Hãng xe"));
        txtBrand = new JTextField();
        panel.add(txtBrand);

        panel.add(new JLabel("Số lượng"));
        txtQuantity = new JTextField();
        panel.add(txtQuantity);

        panel.add(new JLabel("Gói bảo hành"));
        warrantyBox = new JComboBox(new String[]{"1 năm","2 năm","3 năm"});
        panel.add(warrantyBox);

        panel.add(new JLabel("Tên nhân viên"));
        txtEmployeeName = new JTextField();
        panel.add(txtEmployeeName);

        panel.add(new JLabel("Chức vụ"));
        txtPosition = new JTextField();
        panel.add(txtPosition);

        panel.add(new JLabel("SĐT nhân viên"));
        txtEmployeePhone = new JTextField();
        panel.add(txtEmployeePhone);

        panel.add(new JLabel("Phương thức thanh toán"));
        paymentMethod = new JComboBox(new String[]{
                "Tiền mặt",
                "Thẻ",
                "Trả góp"
        });

        panel.add(paymentMethod);

        JButton btnCreateContract = new JButton("Tạo hợp đồng");

        btnCreateContract.addActionListener(e -> createContract());

        panel.add(btnCreateContract);

        add(new JScrollPane(panel));
    }

    private void createContract() {
        String name = txtCustomerName.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String car = txtCarName.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        String payment = paymentMethod.getSelectedItem().toString();

        SaleService service = new SaleService();

        boolean result = service.createContract(
                name, phone, address, car, quantity, payment
        );

        if (result) {

            String contractID = "HD" + System.currentTimeMillis();

            JOptionPane.showMessageDialog(this,
                    "Thanh toán thành công\nMã hợp đồng: " + contractID);

        } else {

            JOptionPane.showMessageDialog(this, "Lỗi tạo hợp đồng");

        }
    }
}
