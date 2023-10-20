import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import javax.swing.*;

public class Panel extends JPanel {
    final int WIDTH = 800;
    final int HEIGHT = 800;

    Hero hero = new Hero();

    int enemiesNumber = 5; //(max 16x na szerokość ekranu)
    List<Enemy> enemies = new ArrayList<>();
    int health = 5;

    InfoPanel info = new InfoPanel();
    JButton start =new JButton(new ImageIcon("src/ICONS/StartGame.png"));
    JButton exit =new JButton(new ImageIcon("src/ICONS/ExitGame.png"));
    JButton load =new JButton(new ImageIcon("src/ICONS/LoadGame.png"));
    JButton save =new JButton(new ImageIcon("src/ICONS/saveGame.png"));

    ImageIcon background = new ImageIcon("src/Backgrounds/level1Recznie.jpg");
    int movebackgroundY = -1600;
    MENU menu = new MENU();


    public Panel() {

        CreateEnemiesAgain();

        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(this.hero);
        this.setFocusable(true);


        this.add(start);
        start.setVisible(false);
        this.add(exit);
        exit.setVisible(false);
        this.add(load);
        load.setVisible(false);
        this.add(save);
        save.setVisible(false);

    }


    enum STATE{
        MENU,
        GAME,
        Pause,

    }

    public void CreateEnemiesAgain(){
        int[] randomArray = generateRandomArray(3,0,15);
        for (int i = 0; i < enemiesNumber; i++) {
            if (i % 3 == 0){
                randomArray = generateRandomArray(3,0,15);
            }
            enemies.add(new Enemy(randomArray[i % 3] * 50, (int) Math.floor((i / 3)) * 50));

        }
    }

    public static STATE State = STATE.MENU; /// zmienic na GAME zebyh grac

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (State == STATE.MENU){
            menu.Draw(g2d,this,start,exit,load,save);
            repaint();
        }else if (State == STATE.GAME){
            load.setVisible(false);
            save.setVisible(false);
            exit.setVisible(false);

            background.paintIcon(this,g2d,0,movebackgroundY);
            movebackgroundY = movebackgroundY + 1;

            this.hero.paint(g2d, this);   // rysuje również  pocisk

            // rysuj przeciwnika

            for (Enemy i : enemies) {  //eneny to jest "i"
                i.paint(g2d, this);
            }

            if (enemies.size() < 1){
                hero.bullets.removeAll(hero.bullets);
                CreateEnemiesAgain();
            }

            //infopanel
            this.info.paintInfopanel(g2d, enemies.size(), health, hero.bulletCounter,this);

        } else if (State == STATE.Pause) {

            this.info.paintInfopanel(g2d, enemies.size(), health, hero.bulletCounter,this);
            background.paintIcon(this,g2d,0,movebackgroundY);
            this.hero.paint(g2d, this);   // rysuje również  pocisk
            for (Enemy i : enemies) {  //eneny to jest "i"
                i.paint(g2d, this);
            }


            menu.pause(g2d,this,exit,load,save);
        }


    }


    public void move() {

        if (State == STATE.GAME) {

            this.hero.updateMove();
            this.kolizje();
            for (Enemy i : enemies) {  //eneny to jest "i"
                i.update();
            }
            this.checkEnemyY();

        }
        this.repaint();
        //this.sprawdzenie();
    }


    public void kolizje() {

        Iterator<Bullet> i = hero.bullets.iterator(); // wywołanie iteratora na klasie Hero (liczenie pocisków)
        while (i.hasNext()) {

            Bullet bullet = i.next();

            Iterator<Enemy> e = enemies.iterator(); // iterator nazwany "e" -> teraz kazdy enemies to "e"
            while (e.hasNext()) {

                Enemy enemy = e.next();
                if (bullet.yShoot <= enemy.y + 48 && bullet.yShoot > enemy.y && bullet.xShoot <= enemy.x + 48 && bullet.xShoot > enemy.x) {
                    e.remove();
                    i.remove();
                    System.out.println("pozostało pzzeciwników " + enemies.size());
                    break;
                }
            }
        }
        if (enemies.size() == 0){    // jak liczba przeciwników dojdzie do 0 to pojawi się MENU
            State = STATE.MENU;   /// ale liczba przeciwniiów == 0 nadal więc gra nie odpali na nowo.
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
            ImageIcon death = new ImageIcon("src/ICONS/skull.png");
            JOptionPane.showMessageDialog(null,"Straciłeś wszystkie życia!","Koniec Gry",0,death);
            State = STATE.MENU;
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
