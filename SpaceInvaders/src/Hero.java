import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.swing.*;

public class Hero implements KeyListener {
    public boolean left;
    public boolean right;
    public boolean shoot;

    public int x = 250;
    public int y = 750;

    public ImageIcon heroIcon = new ImageIcon("src/ICONS/hero1.png"); // zrobić ścieżkę

    List<Bullet> bullets = new ArrayList<>();
    int bulletCounter = 0;


    public Hero() {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 37) {
            this.left = true;
        }

        if (code == 39) {
            this.right = true;
        }

        if (code == 32) {
            strzal();
        }
        if (code == 27) {
            if (Panel.State == Panel.STATE.GAME){
                Panel.State = Panel.STATE.Pause;
        } else {
                Panel.State = Panel.STATE.GAME;
            }
        }

    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 37) {
            this.left = false;
        }

        if (code == 39) {
            this.right = false;
        }

        if (code == 32) {
            this.shoot = false;
        }


    }




    ////rysowanie
    public void paint(Graphics2D g2d, JPanel panel) {

        this.heroIcon.paintIcon(panel, g2d, this.x, this.y);
        this.paintBullets(g2d);

    }


    public void paintBullets(Graphics2D g2d) {

        g2d.setColor(Color.WHITE);

        for (Bullet i : bullets) {
            g2d.fillOval(i.xShoot, i.yShoot, 6, 6);
            i.yShoot -= 8;
        }
    }


    public void updateMove() {

        if (right && x < 800 - 48) {
            x += 8;
        } else if (left && x > 0) {
            x -= 8;
        }

        removeBullets();

    }


    public void strzal() {

        bullets.add(new Bullet(x + 20, y)); // twórz pocisk)=

        bulletCounter++;

    }


    public void removeBullets() {

        //generyk
        Iterator<Bullet> i = bullets.iterator();
        while (i.hasNext()) {

            Bullet bullet = i.next();
            if (bullet.yShoot < -5) {
                i.remove();
                System.out.println("usunieto " + bullets.size());
            }
        }
    }


}