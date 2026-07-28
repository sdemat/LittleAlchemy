import java.awt.*;

public class Particle {

    double velox ;
    double veloy;
    double x;
    double y;
    Color color;
    boolean hasGrav;
    int fadeSpeed;

    public Particle(double _x, double _y, double _velox, double _veloy, Color _color, boolean _hasGrav, int _fadeSpeed) {
        x = _x;
        y = _y;
        velox = _velox;
        veloy = _veloy;
        color = _color;
        hasGrav = _hasGrav;
        fadeSpeed = _fadeSpeed;
    }

    public static void spawnParticles(int x, int y, int amt, boolean _hasgrav, int _fades) {
        for (int b = 0; b < amt; b++) {
            Main.particles.add(new Particle(x, y, (Math.random() * (Math.random() * 30)) - (Math.random() * 30), (Math.random() * (Math.random() * 20)) - (Math.random() * 20), new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255)), _hasgrav, _fades));
        }
    }

    public static void spawnParticles(int x, int y, int amt, boolean _hasgrav, int _fades, Color color) {
        for (int b = 0; b < amt; b++) {
            Main.particles.add(new Particle(x, y, (Math.random() * (Math.random() * 30)) - (Math.random() * 30), (Math.random() * (Math.random() * 20)) - (Math.random() * 20), color, _hasgrav, _fades));
        }
    }

}
