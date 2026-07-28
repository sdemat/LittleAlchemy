import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class Main implements ActionListener {

    static JFrame frame;
    static Viewport viewport;
    final int WINDOW_WIDTH = 1200;
    static final int WINDOW_HEIGHT = 900;
    static int boxWidth = 200;
    static int boxHeight = 50;
    static Font itemFont = new Font("Arial", Font.BOLD, 20);
    final static Color background_color = new Color(201, 201, 199);
    final static Color secondary_color = new Color(191, 191, 191);
    final static Color accent_color = new Color(165, 165, 165);
    static HashMap<String, Item> allItems = new HashMap();
    static ArrayList<Item> discovered = new ArrayList<>();
    static ArrayList<Item> spawned = new ArrayList<>();
    static ArrayList<Item> remove = new ArrayList<>();
    static ArrayList<Item> addThese = new ArrayList<>();
    static Item grabbed;
    static ArrayList<Particle> particles = new ArrayList<>();
    MouseHandler mh = new MouseHandler();
    int fps = 240;
    int count = 0;
    int interval = 1000/fps;
    Timer  timer;

    public Main() {
        createItems();
        createAndShowGUI();
        timer = new Timer((int) interval, this);
        timer.start();
    }

    public void createItems() {
        allItems.put("Air", new Item("Air"));
        allItems.put("Water", new Item("Water"));
        allItems.put("Earth", new Item("Earth"));
        allItems.put("Fire", new Item("Fire"));
        discovered.addAll(allItems.values());
        allItems.put("Mud", new Item("Mud", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Water"), allItems.get("Earth") });}}));
        allItems.put("Brick", new Item("Brick", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Mud"), allItems.get("Air") });}}));
        allItems.put("Steam", new Item("Steam", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Water"), allItems.get("Fire") });}}));
        allItems.put("Dust", new Item("Dust", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Earth"), allItems.get("Air") });}}));
        allItems.put("Stone", new Item("Stone", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Earth"), allItems.get("Fire") });}}));
        allItems.put("Cloud", new Item("Cloud", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Water"), allItems.get("Air") });}}));
        allItems.put("Energy", new Item("Energy", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Fire"), allItems.get("Air") });}}));
        allItems.put("Life", new Item("Life", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Water"), allItems.get("Energy") });}}));
        allItems.put("Plant", new Item("Plant", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Earth"), allItems.get("Life") });}}));
        allItems.put("Tree", new Item("Tree", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Plant"), allItems.get("Air") });}}));
        allItems.put("Human", new Item("Human", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Life"), allItems.get("Air") });}}));
        allItems.put("Animal", new Item("Animal", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Life"), allItems.get("Earth") });}}));
        allItems.put("Bird", new Item("Bird", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Animal"), allItems.get("Air") });}}));
        allItems.put("Wood", new Item("Wood", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Tree"), allItems.get("Earth") });}}));
        allItems.put("House", new Item("House", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Brick"), allItems.get("Wood") });}}));
        allItems.put("Forest", new Item("Forest", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("Tree"), allItems.get("Tree") });}}));
        allItems.put("City", new Item("City", new ArrayList<Item[]>() {{add(new Item[] { allItems.get("House"), allItems.get("Human") });}}));
    }

    public static Item craftItems(Item i1, Item i2) {
        for (Item cI : allItems.values()) {
            if (cI.recipe.size() > 0) {
                for (Item[] recipe : cI.recipe) {
                    if (recipe[0].name == i1.name && recipe[1].name == i2.name) {
                        Item newItem = new Item(cI.name);
                        newItem.setBounds(i1.getBounds());
                        if (!discovered.contains(cI)) {
                            Particle.spawnParticles(newItem.getX() + newItem.getWidth() / 2, newItem.getY() + newItem.getHeight() / 2, 999, true, 0);
                            discovered.add(cI);
                        }
                        return newItem;
                    }
                    if (recipe[0].name == i2.name && recipe[1].name == i1.name ) {
                        Item newItem = new Item(cI.name);
                        newItem.setBounds(i1.getBounds());
                        if (!discovered.contains(cI)) {
                            Particle.spawnParticles(newItem.getX() + newItem.getWidth() / 2, newItem.getY() + newItem.getHeight() / 2, 999, true, 0);
                            discovered.add(cI);
                        }
                        return newItem;
                    }
                }
            }
        }
        return null;
    }

    public void update() {
        count++;
        if (grabbed != null) {
            Rectangle newBounds = new Rectangle(mh.mouseX - boxWidth / 2, mh.mouseY - boxHeight / 2, boxWidth, boxHeight);
            if (grabbed.getBounds() != newBounds) {
                if (count % (int)((Math.random() * 2) + 3) == 0) {
                    for (int i = 10; i < boxWidth - 10; i += Math.random() * 50) {
                        particles.add(new Particle(newBounds.x + i, newBounds.y + boxHeight, (int) (Math.random() * 2) - 0.5, (int) (Math.random() * 2) - 0.5, Color.darkGray, true, 10));
                    }
                }
            }
            grabbed.setBounds(newBounds);

        }
        spawned.addAll(addThese);
        addThese.clear();
        for (Item i : remove) {
            spawned.remove(i);
        }
        remove.clear();
        ArrayList<Particle> removethese = new ArrayList<>();
        for (Particle p : particles) {
            p.x += (p.velox);
            p.y += (p.veloy);
            if (p.hasGrav) {
                p.veloy += 0.2;
            }
            if (p.color.getAlpha() - p.fadeSpeed < 0) {
                p.color = new Color(p.color.getRed(), p.color.getBlue(), p.color.getGreen(), 0);
            } else {
                p.color = new Color(p.color.getRed(), p.color.getBlue(), p.color.getGreen(), p.color.getAlpha() - p.fadeSpeed);
            }
            if (p.x < 0 || p.x > WINDOW_WIDTH || p.y > WINDOW_HEIGHT || p.y < 0 || p.color.getAlpha() == 0) {
                removethese.add(p);
            }
        }
        for (Particle p : removethese) {
            particles.remove(p);
        }
        if (count == 240) {
            count = 0;
        }
    }

    public static void draw(Graphics2D g) {
        g.setColor(secondary_color);
        g.fillRect(800, 0, 400, 900);
        g.setColor(accent_color);
        g.drawRect(800, 0, 400, 900);

        for (int i = 0; i < discovered.size(); i++) {
            Item item = discovered.get(i);
            item.setBounds(900, 50 + (i * 50) + (i * 10), boxWidth, boxHeight);
            g.setColor(secondary_color);
            g.fillRoundRect(item.getBounds().x, item.getBounds().y, item.getBounds().width, item.getBounds().height, 20, 20);
            g.setColor(accent_color);
            g.drawRoundRect(item.getBounds().x, item.getBounds().y, item.getBounds().width, item.getBounds().height, 20, 20);
            g.setFont(itemFont);
            FontMetrics fm = g.getFontMetrics();
            g.setColor(Color.BLACK);
            g.drawString(item.name, item.getX() + (item.getWidth() - fm.stringWidth(item.name)) / 2, item.getY() + (item.getHeight() - fm.getHeight()) / 2 + fm.getAscent());
        }
        for (int i = 0; i < spawned.size(); i++) {
            Item item = spawned.get(i);
            if (item != null) {
                g.setColor(secondary_color);
                g.fillRoundRect(item.getBounds().x, item.getBounds().y, item.getBounds().width, item.getBounds().height, 20, 20);
                g.setColor(accent_color);
                g.drawRoundRect(item.getBounds().x, item.getBounds().y, item.getBounds().width, item.getBounds().height, 20, 20);
                g.setFont(itemFont);
                FontMetrics fm = g.getFontMetrics();
                g.setColor(Color.BLACK);
                g.drawString(item.name, item.getX() + (item.getWidth() - fm.stringWidth(item.name)) / 2, item.getY() + (item.getHeight() - fm.getHeight()) / 2 + fm.getAscent());
            }
        }
        for (Particle p : particles) {
            g.setColor(p.color);
            g.fillRect((int)p.x, (int)p.y, 2, 2);
        }
        g.setFont(new Font("Times New Roman", Font.BOLD, 40));
        String s = discovered.size() + "/" + allItems.size();
        g.setColor(accent_color);
        g.drawString(s, 800 - (g.getFontMetrics().stringWidth(s)), WINDOW_HEIGHT);
    }

    public void createAndShowGUI() {
        frame = new JFrame("Little Alchemy");
        viewport = new Viewport();
        viewport.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        viewport.setLayout(null);
        viewport.setBackground(background_color);
        viewport.addMouseListener(mh);
        viewport.addMouseMotionListener(mh);
        viewport.addMouseWheelListener(mh);
        frame.setContentPane(viewport);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        Main m = new Main();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        update();
        viewport.repaint();
    }
}