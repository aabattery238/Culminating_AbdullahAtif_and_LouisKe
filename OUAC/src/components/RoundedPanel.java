package components;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class RoundedPanel extends JPanel {
    // Default radius for rounded corners
    private int radius = 67;
    private Shape shape;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // paint rounded background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        // clip to rounded shape so children paint within it
        Shape oldClip = g2.getClip();
        RoundRectangle2D round = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
        g2.clip(round);
        super.paintComponent(g2);
        g2.setClip(oldClip);
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
        }
        return shape.contains(x, y);
    }
}