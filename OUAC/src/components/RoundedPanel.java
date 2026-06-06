package components;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class RoundedPanel extends JPanel {
    private int radius = 67;
    private Shape shape;
    private boolean autoRadius = false;
    private static final int DEFAULT_RADIUS = 67;

    public RoundedPanel() {
        this(DEFAULT_RADIUS);
    }

    public RoundedPanel(int radius) {
        this.radius = radius;
        init();
    }

    private void init() {
        setOpaque(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                shape = null;
                if (autoRadius) repaint();
            }
        });
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        int old = this.radius;
        this.radius = radius;
        this.shape = null;
        revalidate();
        repaint();
        firePropertyChange("radius", old, radius);
    }

    public boolean isAutoRadius() {
        return autoRadius;
    }

    public void setAutoRadius(boolean autoRadius) {
        boolean old = this.autoRadius;
        this.autoRadius = autoRadius;
        this.shape = null;
        revalidate();
        repaint();
        firePropertyChange("autoRadius", old, autoRadius);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int r = autoRadius ? Math.min(getWidth(), getHeight()) / 2 : radius;
        // paint rounded background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), r, r);
        // clip to rounded shape so children paint within it
        Shape oldClip = g2.getClip();
        RoundRectangle2D round = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), r, r);
        g2.clip(round);
        super.paintComponent(g2);
        g2.setClip(oldClip);
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        int r = autoRadius ? Math.min(getWidth(), getHeight()) / 2 : radius;
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), r, r);
        }
        return shape.contains(x, y);
    }
}