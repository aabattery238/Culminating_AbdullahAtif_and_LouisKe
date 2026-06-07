package components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.Image;
import javax.swing.JPanel;

public class CircleDesign extends JPanel {

    private Image image;

    public CircleDesign() {
        setOpaque(false);
        // Default image (change path as needed)
        image = ImageImporter.getImage("/images/profile.png");
    }

    public void setImage(String path) {
        image = ImageImporter.getImage(path);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        Ellipse2D circle = new Ellipse2D.Double(0, 0, w, h);
        g2.setClip(circle);

        g2.drawImage(image, 0, 0, w, h, this);

        g2.dispose();
    }
}
