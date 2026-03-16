package com.carmanagement.gui;
import javax.swing.*;
import java.awt.*;
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;
    public BackgroundPanel() {
//        backgroundImage = new ImageIcon(
//                getClass().getResource("/images/bg.jpg")
//        ).getImage();
        backgroundImage = new ImageIcon("src/main/resources/images/bg.jpg").getImage();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
