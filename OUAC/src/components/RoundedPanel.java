package components;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class RoundedPanel extends JPanel {
    private int radius = 67;
    private Shape shape;
    private boolean hoverEnabled = false;
    private Color hoverBackground;
    private Color normalBackground;
    private final java.awt.event.MouseAdapter hoverMouseAdapter = new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            if (hoverEnabled && hoverBackground != null) {
                normalBackground = getBackground();
                setBackground(hoverBackground);
                repaint();
            }
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            if (hoverEnabled && normalBackground != null) {
                setBackground(normalBackground);
                repaint();
            }
        }
    };
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
            }
        });
        addMouseListener(hoverMouseAdapter);
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
    public boolean isHoverEnabled() {
        return hoverEnabled;
    }

    public void setHoverEnabled(boolean hoverEnabled) {
        boolean old = this.hoverEnabled;
        this.hoverEnabled = hoverEnabled;
        updateHoverListeners();
        firePropertyChange("hoverEnabled", old, hoverEnabled);
    }

    public Color getHoverBackground() {
        return hoverBackground;
    }

    public void setHoverBackground(Color hoverBackground) {
        Color old = this.hoverBackground;
        this.hoverBackground = hoverBackground;
        firePropertyChange("hoverBackground", old, hoverBackground);
    }

    @Override
    protected void addImpl(Component comp, Object constraints, int index) {
        super.addImpl(comp, constraints, index);
        if (hoverEnabled) {
            comp.addMouseListener(hoverMouseAdapter);
        }
    }

    private void updateHoverListeners() {
        for (Component child : getComponents()) {
            child.removeMouseListener(hoverMouseAdapter);
            if (hoverEnabled) {
                child.addMouseListener(hoverMouseAdapter);
            }
        }
    }

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