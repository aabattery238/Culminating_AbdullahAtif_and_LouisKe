package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class RoundedTextField extends JTextField {
    private static final int DEFAULT_RADIUS = 18;

    public RoundedTextField() {
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, DEFAULT_RADIUS, DEFAULT_RADIUS);
        g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), DEFAULT_RADIUS, DEFAULT_RADIUS));
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(214, 214, 214));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, DEFAULT_RADIUS, DEFAULT_RADIUS);
        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension preferredSize = super.getPreferredSize();
        preferredSize.height = Math.max(preferredSize.height, 32);
        return preferredSize;
    }
}