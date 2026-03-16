package com.carmanagement.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class HomePanel extends JPanel {
    private JLabel imageLabel;
    private String[] images = {
            "src/images/vios.jpg",
            "src/images/toyota.jpg",
            "src/images/audi.png",
            "src/images/honda.jpg"
    };
    private int index = 0;
    public HomePanel() {
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(350,600));
        leftPanel.setBackground(Color.WHITE);
        ImageIcon logo = new ImageIcon("src/images/logo.png");
        JLabel logoLabel = new JLabel(logo);
        leftPanel.add(logoLabel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        setImage();
        rightPanel.add(imageLabel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        startSlide();
    }

    private void setImage(){

        ImageIcon icon = new ImageIcon(images[index]);
        Image img = icon.getImage().getScaledInstance(700,450,Image.SCALE_SMOOTH);

        imageLabel.setIcon(new ImageIcon(img));
    }

    private void startSlide(){

        Timer timer = new Timer(3000,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){

                index++;

                if(index >= images.length){
                    index = 0;
                }

                setImage();
            }
        });

        timer.start();
    }
}
