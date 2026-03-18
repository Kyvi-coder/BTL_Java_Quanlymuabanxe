package com.carmanagement.gui;
import javax.swing.*;
import java.awt.*;
public class DashboardPanel extends JPanel {
    private JLabel slideLabel;
    private int index = 0;
    String[] images = {
            "src/main/resources/images/audi.png",
            "src/main/resources/images/bmw.jpg",
            "src/main/resources/images/lexus.jpg",
            "src/main/resources/images/vin.jpg"
    };
    public DashboardPanel(){
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        add(createTitle(),BorderLayout.NORTH);
        add(createSlideShow(),BorderLayout.CENTER);
        add(createGallery(),BorderLayout.SOUTH);
        startSlideShow();
    }
    private JPanel createTitle(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(100,60));
        JLabel title = new JLabel("TRANG CHỦ");
        title.setFont(new Font("Arial",Font.BOLD,28));
        title.setForeground(new Color(40,90,200));
        panel.add(title);
        return panel;
    }
    private JPanel createSlideShow(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        slideLabel = new JLabel();
        slideLabel.setHorizontalAlignment(SwingConstants.CENTER);
        slideLabel.setPreferredSize(new Dimension(900,400));
        panel.add(slideLabel,BorderLayout.CENTER);
        return panel;
    }
    private JPanel createGallery(){
        JPanel gallery = new JPanel();
        gallery.setBackground(Color.WHITE);
        gallery.setLayout(new GridLayout(1,3,20,10));
        gallery.setBorder(BorderFactory.createEmptyBorder(10,40,20,40));
        gallery.add(createCarImage("src/main/resources/images/merc.jpg"));
        gallery.add(createCarImage("src/main/resources/images/toyota.jpg"));
        gallery.add(createCarImage("src/main/resources/images/honda.jpg"));
        return gallery;
    }
    private JLabel createCarImage(String path){
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(260,160,Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(img));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    private void startSlideShow(){
        Timer timer = new Timer(3000,e -> {
            ImageIcon icon = new ImageIcon(images[index]);
            Image img = icon.getImage().getScaledInstance(850,400,Image.SCALE_SMOOTH);
            slideLabel.setIcon(new ImageIcon(img));
            index++;
            if(index >= images.length){
                index = 0;
            }
        });
        timer.start();
    }
}

