import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import javax.swing.JPanel;

public class Panel extends JPanel {
    final int WIDTH = 800;
    final int HEIGHT = 800;

    Hero hero = new Hero();

    int enemiesNumber = 11; //(max 16x na szerokość ekranu)
    List<Enemy> enemies = new ArrayList<>();
    int health = 5;

    InfoPanel info = new InfoPanel();


    public Panel() {

        int[] randomArray = generateRandomArray(3,0,15);
        for (int i = 0; i < enemiesNumber; i++) {
            if (i % 3 == 0){
                randomArray = generateRandomArray(3,0,15);
            }
            enemies.add(new Enemy(randomArray[i % 3] * 50, (int) Math.floor((i / 3)) * 50));

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

        //infopanel
        this.info.paintInfopanel(g2d,enemiesNumber,health,hero.bulletCounter);

    }


    public void move() {

        this.hero.updateMove();
        this.kolizje();
        for (Enemy i : enemies) {  //eneny to jest "i"
            i.update();
        }
        this.checkEnemyY();
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
                    break;
                }
            }
        }

    }

    public void checkEnemyY(){

        for (Enemy e: enemies) {
            if (e.y > 780){
                e.y = -50; // wyjdzie jeszcze raz zza ekranu
                health --;
                System.out.println("przeciwniik ma punkt");
            }
        }

        if (health <= 0){
            //koniec gry

        }

    }

    public static int[] generateRandomArray(int size, int min, int max) {
        if (max - min + 1 < size) {
            throw new IllegalArgumentException("Cannot generate unique array. Range is too small.");
        }

        int[] randomArray = new int[size];
        Set<Integer> uniqueSet = new HashSet<>();
        Random random = new Random();

        while (uniqueSet.size() < size) {
            int randomNum = random.nextInt(max - min + 1) + min;
            uniqueSet.add(randomNum);
        }

        int index = 0;
        for (int number : uniqueSet) {
            randomArray[index++] = number;
        }

        return randomArray;
    }


    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }
}
