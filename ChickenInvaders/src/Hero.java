import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

public class Hero implements KeyListener {
    public boolean left;
    public boolean right;
    public boolean shoot;

    List<Integer> listToRight = new ArrayList<>();

    Panel panel;

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getAmmo() {
        return ammo;
    }

    public int ammo;

    public int x = 250;
    public int y = 750;

    public ImageIcon heroIcon = new ImageIcon("src/ICONS/hero1.png"); // zrobić ścieżkę


    ImageIcon bigShootIcon = new ImageIcon("src/ICONS/big.png");

    public int getBullets() {
        return bullets.size();
    }

    List<Bullet> bullets = new ArrayList<>();
    int bulletCounter = 0;
    //List<Integer> setTurnOfBullet = new ArrayList<>();

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

        y = panel.getHeight() -50;

        this.trippeShoots = trippeShoots;
        this.bigShoot = bigShoot;
        this.panel = (Panel) panel;

        this.heroIcon.paintIcon(panel, g2d, this.x, this.y);
        this.paintBullets(g2d);

    }


    public void paintBullets(Graphics2D g2d) {

        g2d.setColor(Color.WHITE);


        for (int i = 0; i < bullets.size(); i++) {
            g2d.fillOval(bullets.get(i).xShoot, bullets.get(i).yShoot, 6, 6);
            bullets.get(i).xShoot += bullets.get(i).xDegree;
            bullets.get(i).yShoot -= bullets.get(i).turn;
        }

    }


    public void updateMove() {

        if (right && x < panel.getWidth() - 48) {
            x += 8;
        } else if (left && x > 0) {
            x -= 8;
        }


    }


    public void strzal() {

        if (bigShoot){
            if (ammo >0) {
                bullets.add(new Bullet(x + 20, y, panel.obstacle.getQuantity(), 0)); // twórz pocisk)=
                ammo = ammo - 1;
                bulletCounter++;
                allBulletsShoots++;
            }

        }else {
            if (panel.bonusActivated && panel.gameBonus.chooseBonus() == 3 && !trippeShoots){
                if (ammo > 0) {
                    bullets.add(new Bullet(x + 40, y + 20, panel.obstacle.getQuantity(), 8));
                    ammo = ammo - 1;
                    bulletCounter++;
                    allBulletsShoots++;
                }
                if (ammo > 0) {
                    bullets.add(new Bullet(x, y + 20, panel.obstacle.getQuantity(), -8));
                    ammo = ammo - 1;
                    bulletCounter++;
                    allBulletsShoots++;
                }
            }else {
                if (ammo > 0) {
                    bullets.add(new Bullet(x + 20, y, panel.obstacle.getQuantity(), 0)); // twórz pocisk)=
                    ammo = ammo - 1;
                    bulletCounter++;
                    allBulletsShoots++;
                }
            }

            //setTurnOfBullet.add(8);
        }

        if (trippeShoots){
            if (panel.bonusActivated && panel.gameBonus.chooseBonus() == 3){
                if (ammo > 0) {
                    bullets.add(new Bullet(x, y + 20, panel.obstacle.getQuantity(), -8));
                    ammo = ammo - 1;
                    bulletCounter++;
                    allBulletsShoots++;
                }
            }else {
                if (ammo > 0) {
                    bullets.add(new Bullet(x, y + 20, panel.obstacle.getQuantity(), 0)); // twórz pocisk)=
                    ammo = ammo - 1;
                    bulletCounter++;
                    allBulletsShoots++;
                }
            }

//            setTurnOfBullet.add(8);

            if (panel.bonusActivated && panel.gameBonus.chooseBonus() == 3){
                if (ammo > 0) {
                    bullets.add(new Bullet(x + 40, y + 20, panel.obstacle.getQuantity(), 8));
                    ammo = ammo - 1;
                    bulletCounter++;
                    allBulletsShoots++;
                }
            }else {
                if (ammo > 0) {
                    bullets.add(new Bullet(x + 40, y + 20, panel.obstacle.getQuantity(), 0)); // twórz pocisk)=
                    ammo = ammo - 1;
                    bulletCounter++;
                    allBulletsShoots++;
                }
            }

            //setTurnOfBullet.add(8);
        }

        System.out.println("Ammo left : " + ammo);

    }


    public void removeBullets() {


        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).yShoot < -5 || bullets.get(i).yShoot > panel.getHeight()) {

                bullets.remove(bullets.get(i));
                //setTurnOfBullet.remove(setTurnOfBullet.get(i));

            }
        }

    }
    public void removeBullet(int i) {
        bullets.remove(i);
        //setTurnOfBullet.remove(i);
    }

}