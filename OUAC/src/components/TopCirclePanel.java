package components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import javax.swing.JPanel;

public class TopCirclePanel extends JPanel {

    private static final int DEFAULT_RADIUS = 40;
    private int radius;

    public TopCirclePanel() {
        this(DEFAULT_RADIUS);
    }

    public TopCirclePanel(int radius) {
        this.radius = radius;
        setOpaque(false);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
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

        int w = getWidth();
        int h = getHeight();
        int r = Math.min(radius, Math.min(w, h));

        Path2D shape = new Path2D.Double();
        shape.moveTo(0, h);
        shape.lineTo(0, r);
        shape.quadTo(0, 0, r, 0);
        shape.lineTo(w - r, 0);
        shape.quadTo(w, 0, w, r);
        shape.lineTo(w, h);
        shape.closePath();

        g2.setColor(getBackground());
        g2.fill(shape);
        g2.dispose();
    }
}
