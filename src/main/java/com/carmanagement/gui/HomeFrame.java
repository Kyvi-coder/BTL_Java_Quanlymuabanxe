package com.carmanagement.gui;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

    JLabel sliderLabel;

    String[] images = {
            "src/main/resources/images/audi.png",
            "src/main/resources/images/Merc.jpg",
            "src/main/resources/images/toyota.jpg",
            "src/main/resources/images/Vin.jpg"
    };

    int index = 0;

    public HomeFrame(String role){

        setTitle("Car Management System");
        setSize(1200,750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createTopBar(role),BorderLayout.NORTH);
        add(createSidebar(),BorderLayout.WEST);
        add(createMainContent(),BorderLayout.CENTER);

        startSlideShow();

        setVisible(true);
    }

    // TOP BAR
    private JPanel createTopBar(String role){

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50,110,200));
        panel.setPreferredSize(new Dimension(100,60));

        JLabel title = new JLabel("HỆ THỐNG QUẢN LÝ MUA BÁN XE",SwingConstants.CENTER);
        title.setFont(new Font("Arial",Font.BOLD,22));
        title.setForeground(Color.WHITE);

        JLabel user = new JLabel("Welcome, " + role + "   ");
        user.setForeground(Color.WHITE);

        panel.add(title,BorderLayout.CENTER);
        panel.add(user,BorderLayout.EAST);

        return panel;
    }

    // SIDEBAR
    private JPanel createSidebar(){

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200,0));
        sidebar.setBackground(new Color(30,50,80));
        sidebar.setLayout(new GridLayout(8,1,5,10));

        JLabel logo = new JLabel();
        ImageIcon icon = new ImageIcon("src/main/resources/images/logo.png");
        Image img = icon.getImage().getScaledInstance(160,80,Image.SCALE_SMOOTH);
        logo.setIcon(new ImageIcon(img));
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        sidebar.add(logo);

        sidebar.add(createMenuButton("Trang chủ"));
        sidebar.add(createMenuButton("Bán xe"));
        sidebar.add(createMenuButton("Khách hàng"));
        sidebar.add(createMenuButton("Kho xe"));
        sidebar.add(createMenuButton("Bảo hành"));
        sidebar.add(createMenuButton("Nhân viên"));
        sidebar.add(createMenuButton("Doanh số"));

        return sidebar;
    }

    private JButton createMenuButton(String text){

        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(30,50,80));
        btn.setForeground(Color.WHITE);
        btn.setBorder(null);
        btn.setFont(new Font("Arial",Font.BOLD,14));
        btn.addActionListener(e -> handleMenu(text));
        return btn;
    }
    private void handleMenu(String menu){

        switch(menu){

            case "Bán xe":
                new SalesFrame().setVisible(true);
                break;

            case "Khách hàng":
                new CustomerFrame().setVisible(true);
                break;

            case "Kho xe":
                new InventoryFrame().setVisible(true);
                break;

            case "Bảo hành":
                new WarrantyFrame().setVisible(true);
                break;

            case "Nhân viên":
                new EmployeeFrame().setVisible(true);
                break;

            case "Doanh số":
                new RevenueFrame().setVisible(true);
                break;
        }
    }

    // MAIN CONTENT
    private JPanel createMainContent(){

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        sliderLabel = new JLabel();
        sliderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sliderLabel.setPreferredSize(new Dimension(800,400));

        panel.add(sliderLabel,BorderLayout.CENTER);

        panel.add(createCarGallery(),BorderLayout.SOUTH);

        return panel;
    }

    // CAR GALLERY
    private JPanel createCarGallery(){

        JPanel gallery = new JPanel(new GridLayout(1,3,10,10));
        gallery.setPreferredSize(new Dimension(800,200));

        gallery.add(createImageLabel("src/main/resources/images/Merc.jpg"));
        gallery.add(createImageLabel("src/main/resources/images/Vin.jpg"));
        gallery.add(createImageLabel("src/main/resources/images/toyota.jpg"));

        return gallery;
    }

    private JLabel createImageLabel(String path){

        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(300,180,Image.SCALE_SMOOTH);

        return new JLabel(new ImageIcon(img));
    }

    // SLIDESHOW
    private void startSlideShow(){

        Timer timer = new Timer(3000, e -> {

            ImageIcon icon = new ImageIcon(images[index]);
            Image img = icon.getImage().getScaledInstance(800,400,Image.SCALE_SMOOTH);

            sliderLabel.setIcon(new ImageIcon(img));

            index++;
            if(index >= images.length){
                index = 0;
            }

        });

        timer.start();
    }
}