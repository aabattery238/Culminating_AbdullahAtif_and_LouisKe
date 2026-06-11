package components;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class RoundedPanel extends JPanel {
    // Default radius for rounded corners
    private final int radius = 67;
    private Shape shape;

    @Override
    // Paint the panel with rounded corners
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Paint rounded background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        Shape oldClip = g2.getClip();
        // Set the clipping area to a rounded rectangle
        RoundRectangle2D round = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
        g2.clip(round);
        super.paintComponent(g2);
        g2.setClip(oldClip);
        g2.dispose();
    }

    @Override
    // Check if a point is within the rounded shape of the panel
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
        }
        return shape.contains(x, y);
    }
}