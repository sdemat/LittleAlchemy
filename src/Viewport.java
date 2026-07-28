import javax.swing.JPanel;
import java.awt.*;

public class Viewport extends JPanel {
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = createG2D(g);
        Main.draw(g2d);
    }

    private Graphics2D createG2D(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        rh.put(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        return g2d;
    }
}
