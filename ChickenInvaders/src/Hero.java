import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
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
    ImageIcon bigShootIcon = new ImageIcon("src/ICONS/big.png");

    List<Bullet> bullets = new ArrayList<>();
    int bulletCounter = 0;

    int allBulletsShoots = 0;
    boolean trippeShoots = false;
    boolean bigShoot = true;

    ArrayList<Integer> bigShotsIndex = new ArrayList<>();
    {
        for (int i = 0; i < 99999; i++) {
            if (i%5 == 0){
                bigShotsIndex.add(i);
            }
        }
    }

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
    public void paint(Graphics2D g2d, JPanel panel, boolean trippeShoots, boolean bigShoot) {

        this.trippeShoots = trippeShoots;
        this.bigShoot = bigShoot;

        this.heroIcon.paintIcon(panel, g2d, this.x, this.y);
        this.paintBullets(g2d,panel);

    }


    public void paintBullets(Graphics2D g2d, JPanel panel) {

        g2d.setColor(Color.WHITE);




        for (int i = 0; i < bullets.size(); i++) {


            g2d.fillOval(bullets.get(i).xShoot, bullets.get(i).yShoot, 6, 6);
            bullets.get(i).yShoot -= 8;
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

        if (bigShoot){
            bullets.add(new Bullet(x + 20, y )); // twórz pocisk)=
            bulletCounter++;
            allBulletsShoots++;
        }else {
            bullets.add(new Bullet(x + 20, y)); // twórz pocisk)=
            bulletCounter++;
            allBulletsShoots++;
        }

        if (trippeShoots){
            bullets.add(new Bullet(x, y + 20)); // twórz pocisk)=
            bulletCounter++;
            allBulletsShoots++;
            bullets.add(new Bullet(x + 40, y + 20)); // twórz pocisk)=
            bulletCounter++;
            allBulletsShoots++;
        }



    }


    public void removeBullets() {

        //generyk
        //        Iterator<Bullet> i = bullets.iterator();
//        while (i.hasNext()) {
//
//            Bullet bullet = i.next();
//            if (bullet.yShoot < -5) {
//                i.remove();
//                System.out.println("usunieto " + bullets.size());
//            }
//        }

        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).yShoot < -5) {
                //bullets.remove(i);
                bullets.get(i).yShoot = -209;
                bullets.get(i).xShoot = 1000;
                //System.out.println("usunieto " + bullets.size());
            }
        }

    }

}