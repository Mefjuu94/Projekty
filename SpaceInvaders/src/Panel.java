import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import java.util.Iterator;

public class Panel extends JPanel {
    final int WIDTH = 800;
    final int HEIGHT = 800;

    Hero hero = new Hero();

    int enemiesNumber = 40; //(max 16x na szerokość ekranu)
    List<Enemy> enemies = new ArrayList<>();


    public Panel() {

        for (int i = 0; i < enemiesNumber; i++) {
            enemies.add(new Enemy(i % 16 * 50, (int) Math.floor((i / 16)) * 50));
        }

        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.hero);
        this.setFocusable(true);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        this.hero.paint(g2d, this);   // rysuje również  pocisk

        // rysuj przeciwnika

        for (Enemy i : enemies) {  //eneny to jest "i"
            i.paint(g2d, this);
        }

    }


    public void move() {

        this.hero.updateMove();
        this.kolizje();
        this.repaint();

        //this.sprawdzenie();
    }


    public void kolizje() {

        Iterator<Bullet> i = hero.bullets.iterator();
        while (i.hasNext()) {

            Bullet bullet = i.next(); // must be called before you can call i.remove()

            Iterator<Enemy> e = enemies.iterator();
            while (e.hasNext()) {

                Enemy enemy = e.next(); // must be called before you can call i.remove()
                if (bullet.yShoot <= enemy.y + 48 && bullet.yShoot > enemy.y && bullet.xShoot <= enemy.x + 48 && bullet.xShoot > enemy.x) {
                    e.remove();
                    i.remove();
                    System.out.println("pozostało pzzeciwników " + enemies.size());

                }
            }
        }

    }


    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }
}
