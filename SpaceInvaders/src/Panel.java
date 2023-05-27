import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel {
    final int WIDTH = 500;
    final int HEIGHT = 500;
    public int x = 250;
    public int y = 450;
    Hero hero = new Hero();
    ImageIcon heroImage;
    Bullet[] bullets = new Bullet[999999];
    boolean[] activeBullet = new boolean[999999];
    int counter = 0;

    public Panel() {
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.hero);
        this.setFocusable(true);
        this.heroImage = new ImageIcon("C:\\Users\\mateu\\OneDrive\\Pulpit\\Projekty\\SpaceInvaders\\src\\hero1.png");
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        this.heroImage.paintIcon(this, g2d, this.x, this.y);
        g2d.setColor(Color.WHITE);
        if (this.activeBullet[this.hero.shootCounter]) {
            g2d.fillOval(this.bullets[this.hero.shootCounter].xShoot, this.bullets[this.hero.shootCounter].yShoot, 5, 5);
        }

        for(int i = 0; i < this.hero.shootCounter; ++i) {
            if (this.activeBullet[i]) {
                g2d.fillOval(this.bullets[i].xShoot, this.bullets[i].yShoot, 5, 5);
            }
        }

    }

    public void move() {
        this.updateMove();
        this.strzal();
        this.repaint();
        this.sprawdzenie();
    }

    public void updateMove() {
        if (this.hero.right) {
            this.x += 3;
        } else if (this.hero.left) {
            this.x -= 3;
        }

    }

    public void strzal() {
        if (this.hero.shoot) {
            Bullet bullet = new Bullet(this.x, this.y);
            this.bullets[this.hero.shootCounter] = bullet;
            System.out.println(this.hero.shootCounter + " pocisk stworzony");
            this.activeBullet[this.hero.shootCounter] = true;
        }

        this.BulletCoordinates();
    }

    public void BulletCoordinates() {
        for(int i = 1; i <= this.hero.shootCounter; ++i) {
            this.bullets[i].yShoot -= 5;
            if (this.bullets[i].yShoot < -10) {
                this.bullets[i].xShoot = -99999;
                this.bullets[i].yShoot = -99999;
                this.activeBullet[i] = false;
            }
        }

    }

    public void sprawdzenie() {
        for(int i = 1; i <= this.hero.shootCounter; ++i) {
            if (!this.activeBullet[i] && this.counter < i) {
                System.out.println("" + i + " kulka jest poza ekrtanem");
                ++this.counter;
            }
        }

    }

    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }
}
