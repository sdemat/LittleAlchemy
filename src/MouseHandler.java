import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MouseHandler implements MouseListener, MouseWheelListener, MouseMotionListener {
    public static int mouseX;
    public static int mouseY;
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (Item i : Main.discovered) {
            if (e.getX() > i.getBounds().x && e.getX() < i.getBounds().x + i.getBounds().width && e.getY() > i.getBounds().y && e.getY() < i.getBounds().y + i.getBounds().height) {
                Item newItem = new Item(i.name);
                Main.spawned.add(newItem);
                Main.grabbed = newItem;
                Particle.spawnParticles((int)(mouseX), (int)(mouseY), 20, false, 10, Color.DARK_GRAY);
            }
        }
        for (int b = Main.spawned.size() - 1; b >= 0; b--) {
            Item i = Main.spawned.get(b);
            if (i != null) {
                if (e.getX() > i.getBounds().x && e.getX() < i.getBounds().x + i.getBounds().width && e.getY() > i.getBounds().y && e.getY() < i.getBounds().y + i.getBounds().height) {
                    Main.grabbed = i;
                    break;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (Main.grabbed != null) {
            if (Main.grabbed.getX() > 800 || Main.grabbed.getX() + Main.grabbed.getWidth() > 800) {
                Main.remove.add(Main.grabbed);
                Main.grabbed = null;
                return;
            }
            for (Item i : Main.spawned) {
                if (i != Main.grabbed) {
                    if (Math.max(i.getX(), Main.grabbed.getX()) < Math.min(i.getX() + i.getWidth(), Main.grabbed.getX() + Main.grabbed.getWidth())) {
                        if (Math.max(i.getY(), Main.grabbed.getY()) < Math.min(i.getY() + i.getHeight(), Main.grabbed.getY() + Main.grabbed.getHeight())) {
                            Item craft = Main.craftItems(Main.grabbed, i);
                            if (craft != null) {
                                Main.remove.add(Main.grabbed);
                                Main.remove.add(i);
                                Main.addThese.add(craft);
                            }
                            break;
                        }
                    }
                }
            }
            Main.grabbed = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX();
        mouseY = mouseEvent.getY();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = mouseEvent.getX();
        mouseY = mouseEvent.getY();
    }
}
