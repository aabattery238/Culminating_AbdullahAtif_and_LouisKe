package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class CircleDesign extends JPanel {

    public CircleDesign() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(getBackground() != null ? getBackground() : Color.WHITE);

        int diameter = Math.min(getWidth(), getHeight());
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        g2.fillOval(x, y, diameter, diameter);
        g2.dispose();
    }
}
