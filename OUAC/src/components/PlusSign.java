package components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PlusSign extends JPanel {

    private int thickness = -1; // -1 = auto-scale

    public PlusSign() {
        setOpaque(false);
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
        repaint();
    }

    public void useAutoThickness() {
        this.thickness = -1;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(getBackground());

        int w = getWidth();
        int h = getHeight();

        int actualThickness;

        if (thickness > 0) {
            actualThickness = thickness;
        } else {
            actualThickness = Math.max(1, Math.min(w, h) / 8);
        }

        int verticalX = (w - actualThickness) / 2;
        int horizontalY = (h - actualThickness) / 2;

        int arc = Math.max(1, actualThickness / 2);

        g2.fillRoundRect(verticalX, 0, actualThickness, h, arc, arc);
        g2.fillRoundRect(0, horizontalY, w, actualThickness, arc, arc);

        g2.dispose();
    }
}
