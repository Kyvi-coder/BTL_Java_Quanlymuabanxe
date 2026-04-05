package com.carmanagement.gui;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel() {
        URL resource = getClass().getResource("/images/bg.jpg");
        if (resource != null) {
            backgroundImage = new ImageIcon(resource).getImage();
            return;
        }
        backgroundImage = new ImageIcon(Objects.requireNonNullElse(
                "src/main/resources/images/bg.jpg",
                ""
        )).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        GradientPaint overlay = new GradientPaint(
                0, 0, new Color(7, 18, 32, 180),
                getWidth(), getHeight(), new Color(20, 60, 82, 130)
        );
        g2.setPaint(overlay);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}
