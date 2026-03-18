package com.carmanagement.gui;
import com.carmanagement.service.RevenueService;
import javax.swing.*;
import java.awt.*;
import java.time.Year;
public class RevenuePanel extends JPanel {
    JComboBox<Integer> cbMonth, cbYear;
    JLabel lblCars, lblRevenue;
    RevenueService service = new RevenueService();
    public RevenuePanel() {
        setLayout(new BorderLayout(10,10));
        add(topPanel(), BorderLayout.NORTH);
        add(centerPanel(), BorderLayout.CENTER);
    }

    // ===== TOP =====
    private JPanel topPanel() {
        JPanel p = new JPanel();

        cbMonth = new JComboBox<>();
        for(int i=1;i<=12;i++) cbMonth.addItem(i);

        cbYear = new JComboBox<>();
        int currentYear = Year.now().getValue();
        for(int i=currentYear-5;i<=currentYear;i++) cbYear.addItem(i);

        JButton btnLoad = new JButton("Thống kê");

        btnLoad.addActionListener(e -> loadData());

        p.add(new JLabel("Tháng:"));
        p.add(cbMonth);
        p.add(new JLabel("Năm:"));
        p.add(cbYear);
        p.add(btnLoad);

        return p;
    }

    // ===== CENTER =====
    private JPanel centerPanel() {
        JPanel p = new JPanel(new GridLayout(2,1,10,10));

        lblCars = new JLabel("Số xe bán: 0", JLabel.CENTER);
        lblRevenue = new JLabel("Doanh thu: 0 VNĐ", JLabel.CENTER);

        lblCars.setFont(new Font("Arial", Font.BOLD, 20));
        lblRevenue.setFont(new Font("Arial", Font.BOLD, 20));

        p.add(lblCars);
        p.add(lblRevenue);

        return p;
    }

    // ===== LOAD =====
    private void loadData() {

        int month = (int) cbMonth.getSelectedItem();
        int year = (int) cbYear.getSelectedItem();

        int cars = service.getTotalCarsSold(month, year);
        double revenue = service.getTotalRevenue(month, year);

        lblCars.setText("Số xe bán: " + cars);
        lblRevenue.setText("Doanh thu: " + String.format("%,.0f VNĐ", revenue));
    }
}
