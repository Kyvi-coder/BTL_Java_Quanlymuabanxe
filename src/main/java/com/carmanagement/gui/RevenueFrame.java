package com.carmanagement.gui;
import javax.swing.*;
import java.awt.*;

public class RevenueFrame extends JFrame {

    JLabel lblCarsSold;
    JLabel lblRevenue;

    public RevenueFrame(){

        setTitle("Doanh số");
        setSize(400,300);

        setLayout(new GridLayout(2,1));

        lblCarsSold = new JLabel("Số xe bán trong tháng: 0");
        lblRevenue = new JLabel("Doanh thu tháng: 0 VNĐ");

        add(lblCarsSold);
        add(lblRevenue);
    }
}
