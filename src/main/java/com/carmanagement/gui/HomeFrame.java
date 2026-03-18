package com.carmanagement.gui;
import javax.swing.*;
import java.awt.*;
public class HomeFrame extends JFrame {
    JPanel contentPanel;
    CardLayout cardLayout;
    public HomeFrame(String username){
        setTitle("Car Management System");
        setSize(1200,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createTopBar(username),BorderLayout.NORTH);
        add(createSidebar(),BorderLayout.WEST);
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(new DashboardPanel(),"dashboard");
        contentPanel.add(new SalesPanel(),"sales");
        contentPanel.add(new CustomerPanel(),"customer");
        contentPanel.add(new InventoryPanel(),"inventory");
        contentPanel.add(new WarrantyPanel(),"warranty");
        contentPanel.add(new EmployeePanel(),"employee");
        contentPanel.add(new RevenuePanel(),"revenue");

        add(contentPanel,BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createTopBar(String username){

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(100,60));
        panel.setBackground(new Color(60,120,200));

        JLabel title = new JLabel("HỆ THỐNG QUẢN LÝ MUA BÁN XE",SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial",Font.BOLD,20));

        JLabel user = new JLabel("Welcome, "+username+"   ");
        user.setForeground(Color.WHITE);

        panel.add(title,BorderLayout.CENTER);
        panel.add(user,BorderLayout.EAST);

        return panel;
    }
    private JPanel createSidebar(){

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(200,0));
        sidebar.setBackground(new Color(30,50,80));
        sidebar.setLayout(new GridLayout(8,1));

        // LOGO
        ImageIcon icon = new ImageIcon("src/main/resources/images/logo.png");
        Image img = icon.getImage().getScaledInstance(120,70,Image.SCALE_SMOOTH);

        JLabel logo = new JLabel(new ImageIcon(img));
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        sidebar.add(logo);

        // MENU
        sidebar.add(createMenuButton("Trang chủ","dashboard"));
        sidebar.add(createMenuButton("Bán xe","sales"));
        sidebar.add(createMenuButton("Khách hàng","customer"));
        sidebar.add(createMenuButton("Kho xe","inventory"));
        sidebar.add(createMenuButton("Bảo hành","warranty"));
        sidebar.add(createMenuButton("Nhân viên","employee"));
        sidebar.add(createMenuButton("Doanh số","revenue"));

        return sidebar;
    }


    private JButton createMenuButton(String text,String panelName){

        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(30,50,80));
        btn.setForeground(Color.WHITE);
        btn.setBorder(null);
        btn.setFont(new Font("Arial",Font.BOLD,14));

        btn.addActionListener(e -> cardLayout.show(contentPanel,panelName));

        return btn;
    }
}

