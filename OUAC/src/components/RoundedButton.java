package components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;

public class RoundedButton extends JButton {
    private static final int DEFAULT_RADIUS = 30;
    private final java.awt.Color defaultBackground = new java.awt.Color(88, 123, 127);
    private final java.awt.Color hoverBackground = new java.awt.Color(57, 62, 65);
    private boolean hoverInstalled = false;

    public RoundedButton() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setMargin(new java.awt.Insets(8, 16, 8, 16));
        setBackground(defaultBackground);
        installHover();
    }

    private void installHover() {
        if (hoverInstalled) {
            return;
        }
        hoverInstalled = true;
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(hoverBackground);
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(defaultBackground);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getModel().isArmed() ? getBackground().darker() : getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, DEFAULT_RADIUS, DEFAULT_RADIUS);
        g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), DEFAULT_RADIUS, DEFAULT_RADIUS));
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground().darker());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, DEFAULT_RADIUS, DEFAULT_RADIUS);
        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension preferredSize = super.getPreferredSize();
        preferredSize.height = Math.max(preferredSize.height, 33);
        return preferredSize;
    }
}