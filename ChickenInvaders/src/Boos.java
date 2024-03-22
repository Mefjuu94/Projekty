import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Boos {

    int x = 200, y = 150;
    int xShoot;
    int yShoot;

    Bullet[] bossBullets;
    int boosSpeed = 3;

    int bossCounter = 1;
    ImageIcon bossIcon = new ImageIcon("src/ICONS/BOSSlvl8.png");
    ImageIcon bossBulletsIcon = new ImageIcon("src/ICONS/BossBulletEgg.png");
    boolean ActiveBoss = false;
    int bossHP = 100;
    Panel panel;

    List<EnemyBullets> bullets = new ArrayList<>();

    Boos(Panel panel){
        this.panel = panel;
    }

    public void paint(Graphics2D g2d) {

        if (bossCounter == 1) {
            this.bossIcon.paintIcon(panel, g2d, this.x, this.y);
        }

        Random rand = new Random();
        if ( rand.nextInt(30) == 10){
            System.out.println("uwaga jajo!!!");
            bullets.add(new EnemyBullets(this.x+ 50,this.y + 150,panel));
        }

        for (int i = 0; i < bullets.size(); i++) {
            this.bossBulletsIcon.paintIcon(panel,g2d,bullets.get(i).xShoot,bullets.get(i).yShoot);
            bullets.get(i).yShoot += 2;
        }

    }


    public void updateMove() {

        if (x > panel.ramka.getWidth()-150 || x <= 2){
            boosSpeed = boosSpeed * -1;
        }
        x += boosSpeed;

        removeBullets();

    }

    public void removeBullets() {

        //generyk
        Iterator<EnemyBullets> i = bullets.iterator();
        while (i.hasNext()) {

            EnemyBullets bullet = i.next();
            if (bullet.yShoot > panel.ramka.getHeight()) {
                i.remove();
                System.out.println("usunieto " + bullets.size());
            }
        }
    }

}
