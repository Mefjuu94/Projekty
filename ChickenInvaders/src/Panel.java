import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.*;
import java.util.Timer;

public class Panel extends JPanel {

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int WIDTH = 800;
    int HEIGHT = 800;
    boolean fullScreenMode = false;
    boolean resizeMode = false;

    int scorePoints = 10;
    int coinsMoney = 100;

    Hero hero = new Hero();
    Enemy enemy;
    String name = "";
    boolean death = false;
    RandomGameBonus gameBonus = new RandomGameBonus();
    boolean bonusActivated = false;
    boolean speedIdUp = false;
    LocalDateTime czas = LocalDateTime.now();
    int czasNano;

    int countTurn = 0;

    int enemiesNumber = 3; //(max 16x na szerokość ekranu)
    List<Enemy> enemies = new ArrayList<>();
    List<EnemyBullets> bosseBullets = new ArrayList<>();
    int iloscNaOS = 3;


    InfoPanel info = new InfoPanel();
    JButton start = new JButton(new ImageIcon("src/ICONS/StartGame.png"));
    JButton exit = new JButton(new ImageIcon("src/ICONS/ExitGame.png"));
    JButton load = new JButton(new ImageIcon("src/ICONS/LoadGame.png"));
    JButton save = new JButton(new ImageIcon("src/ICONS/saveGame.png"));
    JButton goToShop = new JButton("Go to Shop>>>");


    ImageIcon background = new ImageIcon("src/Backgrounds/level1Recznie.jpg");
    ImageIcon backgroundSpeed = new ImageIcon("src/Backgrounds/BackgroundSpace1.gif");
    int movebackgroundY = -1600;
    int allEnemiesKilled = 60;
    int allShootsBulletsnumber = 0;
    int bulletsMissed = allShootsBulletsnumber - allEnemiesKilled;

    Boos boos = new Boos(this);
    Obstacle obstacle = new Obstacle(this);
    Help firstaid;
    List<Coins> coinsQuantity = new ArrayList<>();

    TalentPoints talentPoints = new TalentPoints(this, obstacle.obstacleActive, obstacle, hero, enemy);
    int health = 99 + talentPoints.healthPoints;
    MENU menu = new MENU(talentPoints, hero, this, save, load, exit, goToShop);
    boolean[][] reflectOfBullet = new boolean[350][obstacle.getQuantity()];


    Panel panelItself;
    JFrame ramka;

    public Panel(JFrame ramka) {
        this.ramka = ramka;


        panelItself = this;

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
        this.add(goToShop);
        goToShop.setVisible(false);

        ramka.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if (ramka.getWidth() > 1000) {
                    resizeMode = true;
                } if (fullScreenMode || ramka.getWidth() < 1000){
                    resizeMode = false;
                }
                System.out.println(resizeMode);
                if (State == STATE.TalentPoints && resizeMode){
                    talentPoints.AddPanel(fullScreenMode, resizeMode);
                }else if (State == STATE.TalentPoints){
                    talentPoints.AddPanel(fullScreenMode, resizeMode);
                }
                //TODO ogarnąć zeby enemies były na całości długości ekrannu
                //CreateEnemiesAgainResizeMode();
                firstaid = new Help(ramka.getWidth());
            }
        });

    }


    enum STATE {
        MENU,
        GAME,
        Pause,
        TalentPoints,
    }

    public static STATE State = STATE.TalentPoints; /// zmienic na GAME zeby grac STATE.STATE.TalentPoints


    private void howManyEnemiesInLine() {


        if (ramka.getWidth() > 0){
            iloscNaOS = ramka.getWidth()/160;
            System.out.println("ramka większa");
        }else {

            if (talentPoints.LEVEL >= 3) {
                iloscNaOS = 4;
            }

            if (talentPoints.LEVEL >= 6) {
                iloscNaOS = 6;
            }
            if (talentPoints.LEVEL >= 9) {
                iloscNaOS = 8;
            }
        }

    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (State == STATE.MENU) {
            menu.Draw(g2d, start, exit);
        } else if (State == STATE.GAME) {
            turnOffButtonsAndPanelsToPlay(false);


            background.paintIcon(this, g2d, 0, movebackgroundY);
            if (fullScreenMode || resizeMode){
                int backgroundX = ramka.getWidth() / 820;
                int xBackground = 0;
                for (int i = 0; i < backgroundX +1 ; i++) {
                    background.paintIcon(this,g2d,xBackground,movebackgroundY);
                    xBackground+=820;
                }
            }
            if (speedIdUp){
                movebackgroundY = movebackgroundY + 3;
            }else {
                movebackgroundY = movebackgroundY + 1;
            }
            if (movebackgroundY == 0) {
                movebackgroundY = -1600;
            }


            this.hero.paint(g2d, this, talentPoints.tripleShoots, talentPoints.bigShootEnable);   // rysuje również  pocisk

            // rysuj przeciwnika
            for (Enemy i : enemies) {  //eneny to jest "i"
                i.paint(g2d, this);
            }


            //rysuj bossa wraz z pociskiem!
            if (boos != null && boos.ActiveBoss) {
                boos.paint(g2d);

                for (EnemyBullets e : bosseBullets) {  //eneny to jest "i"
                    e.paint(g2d);
                }

            } else {                 // jak boss jest aktywny nie usuwaj bulletów, bo automatycznie usuwa gdy chickenów małych jest 0 i wychodzi do menu
                if (enemies.size() < 1) {
                    obstacle.obstacleActive = false;
                    bonusActivated = false;
                    coinsQuantity.removeAll(coinsQuantity);
                    hero.bullets.removeAll(hero.bullets);
                    CreateEnemiesAgain();
                }
            }

            // rysuj kładkę!
            if (obstacle.obstacleActive) {
                obstacle.paintObstacle(g2d);
            }

            if (firstaid.helpActive) {
                firstaid.odpalPomoc(g2d, this);
                firstAidKolizje();
            }

            //rysuj pieniążek!
            for (Coins c : coinsQuantity) {
                c.paint(g2d, this);
            }

            //rysuj ewentualny Bonus
            gameBonus.LosowanieBonusu(g2d, this);
            bonusKolizja();


            //TODO zrobić bo nie oblciza dobrze czasu przy +55 sek
            if (bonusActivated) {
                System.out.println("int: " + czasNano);
                czas = LocalDateTime.now();
                int sekundy = czas.getSecond();
                System.out.println("localdataTime: " + czas.getSecond());
                if (czasNano > 55) {
                    czasNano = czasNano - 60;
                }


                if (czas.getSecond() > czasNano + 5) {
                    for (Enemy value : enemies) {
                        value.enemySpeed = 2;
                    }
                    bonusActivated = false;
                    speedIdUp = false;
                }
            }


            // infopanel
            assert boos != null;
            if (!boos.ActiveBoss) {
                this.info.paintInfopanel(g2d, enemies.size(), health, hero.bulletCounter, firstaid.firstAidleft, coinsMoney, this);
            } else {
                this.info.paintInfopanel(g2d, boos.bossHP, health, hero.bulletCounter, firstaid.firstAidleft, coinsMoney, this); //< info zamiast kurczków to hp bosa
            }

        } else if (State == STATE.Pause) {

            menu.pause(g2d);

        } else if (State == STATE.TalentPoints) {

            hero.bullets.removeAll(hero.bullets);
            talentPoints.paintTalentTree(g2d, this);
            talentPoints.buttonsAndScore.setVisible(true);
            hero.bulletCounter = 0; // wyzeropwanie licznika pocisków wystrzerlonych PODCZAS SESJI
            firstaid = new Help(WIDTH);
        }


    }


    public void move() {

        if (State == STATE.GAME) {

            hero.updateMove();

            if (boos != null && boos.ActiveBoss) {
                boos.updateMove();
                checkBoss();
                kolizjeBoss();
                checkEnemyShoot();
                if (obstacle.obstacleActive) {
                    obstacleCollision();
                }
            } else {
                kolizje();
                for (Enemy i : enemies) {  //eneny to jest "i"
                    i.update(talentPoints, panelItself);
                    if (enemies.size() <= 0) {
                        obstacle.obstacleActive = false;
                    }
                    if (obstacle.obstacleActive) {
                        obstacleCollision();
                    }
                }
                checkEnemyY();

                kolizjeCoins();

                for (Coins c : coinsQuantity) {
                    c.update(c);
                }
            }


        }
        this.repaint();
    }

    private void turnOffButtonsAndPanelsToPlay(boolean tf){
        talentPoints.setButtons(tf); // ukrywa buttony z talentów
        talentPoints.buttonsAndScore.setVisible(tf);
        talentPoints.FullscreenMode.setVisible(tf);
        talentPoints.tabbedPane.setVisible(tf);

        load.setVisible(tf);
        save.setVisible(tf);
        exit.setVisible(tf);
        goToShop.setVisible(tf);
        menu.setFullscreen.setVisible(tf);
    }


    public void kolizje() {

        for (int i = 0; i < hero.bullets.size(); i++) {


            for (int j = 0; j < enemies.size(); j++) {


                // kurczaki w strachu na chwilę przed eksterminacją zmieniając ikonkę
                if (hero.bullets.get(i).yShoot <= enemies.get(j).y + 48 + 30 && hero.bullets.get(i).yShoot > enemies.get(j).y &&
                        hero.bullets.get(i).xShoot <= enemies.get(j).x + 48 + 30 && hero.bullets.get(i).xShoot > enemies.get(j).x - 30) {
                    enemies.get(j).enemyIcon = new ImageIcon("src/ICONS/shootChickenOO.png");
                }

                if (hero.bullets.get(i).yShoot <= enemies.get(j).y + 48 && hero.bullets.get(i).yShoot > enemies.get(j).y &&
                        hero.bullets.get(i).xShoot <= enemies.get(j).x + 48 && hero.bullets.get(i).xShoot > enemies.get(j).x) {


                    coinsQuantity.add(new Coins(enemies.get(j).x, enemies.get(j).y));
                    enemies.remove(j);
                   // System.out.println("dodano " + j + " do coins");

                    allEnemiesKilled++;
                    hero.bullets.get(i).yShoot = -209;
                    hero.bullets.get(i).xShoot = -1000;
                   // System.out.println("pozostało pzzeciwników " + enemies.size());
                    break;
                }
            }

            if (hero.bullets.get(i).yShoot < 0) {
                bulletsMissed++;
            }


        }
        if (enemies.size() == 0) {    // jak liczba przeciwników dojdzie do 0 to pojawi się MENU/talentPoints

            allShootsBulletsnumber = hero.allBulletsShoots;
            firstaid.firstAidQuantity += 1;
            obstacle.quantity += 1;

            hero.bullets.removeAll(hero.bullets);
            //hero.setTurnOfBullet.removeAll(hero.setTurnOfBullet);

            firstaid.helpActive = false;
            bulletsMissed = hero.bulletCounter - enemiesNumber;
            System.out.println(bulletsMissed + "<< ilosc nietarfionych");

            scorePoints = scorePoints + talentPoints.calcualteStats(enemiesNumber, hero.bulletCounter); // pocli9cz se punkty po grze
            talentPoints.LEVEL++;
            System.out.println("promote to level: " + talentPoints.LEVEL);
            talentPoints.tabbedPane.setVisible(true);
            talentPoints.turnOnOffShopButtons(true);
            talentPoints.AddPanel(fullScreenMode, resizeMode);
            talentPoints.setButtons(true);
            talentPoints.turnOnOffShopButtons(true);
            talentPoints.firstLoad = true;
            State = STATE.TalentPoints;   /// zmień na okno punktów talentów do wydania punktów
        }

    }

    private void bonusKolizja() {

        if (gameBonus.y + 80 >= hero.y && gameBonus.y < hero.y + 48 && gameBonus.x + 30 < hero.x + 48 && gameBonus.x + 10 > hero.x) {
            System.out.println("Aktywacja bonusu!");
            gameBonus.y = -5900;
            gameBonus.x = -5900;
            if (gameBonus.chooseBonus() == 0) {
                for (int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).enemySpeed = 5;
                    speedIdUp = true;
                }

            }
            if (gameBonus.chooseBonus() == 1) {
                for (int i = 0; i < enemies.size(); i++) {
                    enemies.get(i).enemySpeed = 1;
                }

            }
            bonusActivated = true;
            czas = LocalDateTime.now();
            czasNano = LocalDateTime.now().getSecond();
        }

    }


    private void firstAidKolizje() {

        for (int i = 0; i < firstaid.firstAidQuantity; i++) {

            if (firstaid.y[i] + 40 >= hero.y && firstaid.y[i] < hero.y + 48 && firstaid.x[i] + 30 < hero.x + 48 && firstaid.x[i] + 10 > hero.x) {
                System.out.println("dostałem apteczkę!");
                health = health + 1;
                firstaid.goDown[i] = false;
                firstaid.x[i] = -20;
                firstaid.y[i] = -20;
            }
            //przenieś i przestań ruszać
            if (firstaid.y[i] > getHeight()) {
                firstaid.goDown[i] = false;
                firstaid.x[i] = -20;
                firstaid.y[i] = -20;
            }

        }

    }

    public void kolizjeCoins() {
        for (int i = 0; i < coinsQuantity.size(); i++) {

            if (coinsQuantity.get(i).y + 40 >= hero.y && coinsQuantity.get(i).y < hero.y + 48 && coinsQuantity.get(i).x + 30 < hero.x + 48 && coinsQuantity.get(i).x + 10 > hero.x) {
                System.out.println("dostałem KASE!");
                // coins
                coinsMoney++;
                coinsQuantity.remove(i);
            }
        }

    }

    public void kolizjeBoss() {

        // wywołanie iteratora na klasie Hero (liczenie pocisków)
        for (Bullet bullet : hero.bullets) {

            if (bullet.yShoot <= boos.y + 150 && bullet.yShoot > boos.y && bullet.xShoot <= boos.x + 120 && bullet.xShoot > boos.x - 20) {
                boos.bossHP--;
                //i.remove();
                bullet.yShoot = -209;
                bullet.xShoot = -1000;
                System.out.println(boos.bossHP + " << pozostało hp");
            }

            for (int i = 0; i < boos.bullets.size(); i++) {


                if (bullet.yShoot <= boos.bullets.get(i).yShoot + 30 && bullet.yShoot > boos.bullets.get(i).yShoot && bullet.xShoot <= boos.bullets.get(i).xShoot + 30 && bullet.xShoot > boos.bullets.get(i).xShoot) {
                    System.out.println("trafione");
                    boos.bullets.remove(i);
                    bullet.yShoot = -209;
                    bullet.xShoot = -1000;
                    break;
                }


            }

            if (bullet.yShoot < 0) {
                bulletsMissed++;
            }
        }
        if (boos.bossHP <= 0) {    // jak boss będzie miał 0 HP

            allShootsBulletsnumber = hero.allBulletsShoots;

            bulletsMissed = hero.bulletCounter - enemiesNumber;
            System.out.println(bulletsMissed + "<< ilosc nietarfionych");

            firstaid.firstAidQuantity += 1;
            firstaid.helpActive = false;

            scorePoints = scorePoints + 5; // dej 5 pkt po zabiciu bosa
            talentPoints.LEVEL++;
            System.out.println("promote to level: " + talentPoints.LEVEL);
            talentPoints.firstLoad = true;
            boos.ActiveBoss = false;
            boos.bossCounter++;
            State = STATE.TalentPoints;   /// zmień na okno punktów talentów do wydania punktów

        }
    }

    private void obstacleCollision() {
        int calc = 0;
        for (int i = 0; i < hero.bullets.size(); i++) {

            if (hero.bullets.get(i).yShoot > 0 && hero.bullets.get(i).yShoot < getHeight()) {

                calc++;
                for (int j = 0; j < obstacle.quantity; j++) {

                    if (hero.bullets.get(i).yShoot <= obstacle.y[j] && hero.bullets.get(i).yShoot + 7 > obstacle.y[j] - 7 &&
                            hero.bullets.get(i).xShoot > obstacle.x[j] - 5 && hero.bullets.get(i).xShoot <= obstacle.x[j] + obstacle.obstacleWidth[j]
                            && !hero.bullets.get(i).obstacles[j]) { // tu ejst błąd

                        //System.out.println("zawróć! " + i);

                        if (!talentPoints.antiReflectBullet) {
                            //hero.setTurnOfBullet.set(i, hero.setTurnOfBullet.get(i) * (-1)); // odbija sie
                            hero.bullets.get(i).turn *= -1;

                            makeArrayToCheckReflect(i);
                            hero.bullets.get(i).obstacles[j] = true;
                            //reflectOfBullet[i][j] = true;

                        } else {
                            hero.bullets.get(i).yShoot = -209;
                            hero.bullets.get(i).xShoot = -1000;
                            makeArrayToCheckReflect(i);
                            reflectOfBullet[i][j] = true;

                        }

                    }

                }

                if (hero.bullets.get(i).xShoot >= hero.x  && hero.bullets.get(i).xShoot + 5 <= hero.x + 48 &&
                        hero.bullets.get(i).yShoot > hero.y + 15 && hero.bullets.get(i).yShoot < hero.y + 48  && hero.bullets.get(i).turn == -8) {
                    System.out.println("index danej kulki: " + i);
                    hero.bullets.get(i).yShoot = -209;
                    hero.bullets.get(i).xShoot = -1000;

                    hero.removeBullet(i);
                    health--;
                }
            }

            hero.removeBullets();
        }


//        System.out.println(hero.bullets.size() + " ilość bulletów w danej chwili");
//        System.out.println(calc + " długość boolien (pocisków) również w planszy");
//        System.out.println("=============");
    }

    private void quantityOfObstacle() {

        if (talentPoints.LEVEL > 1 && talentPoints.LEVEL < 6) {
            obstacle.setQuantity(talentPoints.LEVEL);
        }
        if (talentPoints.LEVEL > 6) {
            obstacle.setQuantity(5);
        }
        if (talentPoints.LEVEL == 8) {
            obstacle.quantity = 2;
        }
        if (talentPoints.LEVEL > 8) {
            obstacle.quantity = 7;
        }

        obstacle.setQuantity(12);

        //makeArrayToCheckReflect(i);
        System.out.println(obstacle.getQuantity() + " << ilość kładek");

    }

    public void makeArrayToCheckReflect(int i) {
        //reflectOfBullet = new boolean[350][obstacle.getQuantity()];
        Arrays.fill(hero.bullets.get(i).obstacles, false);
    }

    public void checkEnemyY() {

        for (Enemy e : enemies) {
            if (e.y > getHeight()) {
                e.y = -50; // wyjdzie jeszcze raz zza ekranu
                health--;
                System.out.println("-1 życia");
            }
        }

        coinsQuantity.removeIf(c -> c.y > getHeight());
        checkHealth();
    }

    public void checkEnemyShoot() {

        for (int b = 0; b < boos.bullets.size(); b++) {
            if (boos.bullets.get(b).yShoot > getHeight()) {
                //boos.bullets.remove(b);
                boos.bullets.get(b).yShoot = -209;
                boos.bullets.get(b).xShoot = -1000;
                health = health - 1;
                System.out.println("przeciwniik ma punkt - 1 życia");
            }
        }
        checkHealth();
    }

    private void checkHealth() {
        if (health <= 0) {
            ImageIcon die = new ImageIcon("src/ICONS/skull.png");
            JOptionPane.showMessageDialog(this, "Straciłeś wszystkie życia!", "Koniec Gry", 0, die);
            resetStatsAfterDeath();
            bosseBullets.retainAll(bosseBullets);
            hero.bullets.removeAll(hero.bullets);
            coinsQuantity.removeAll(coinsQuantity);
            death = true;
            obstacle.obstacleActive = false;
            State = STATE.MENU;
            //koniec gry
        }
    }

    private void resetStatsAfterDeath() {
        health = 5;
        talentPoints.LEVEL = 1;
        scorePoints = 0;
        talentPoints.score = 0;
        allEnemiesKilled = 0;
        enemiesNumber = 3;

        talentPoints.healthPoints = 0;
        talentPoints.addHealth = false;
        talentPoints.additionalHealth.setEnabled(false);

        talentPoints.tripleShoots = false;
        talentPoints.trippleShootsPoints = 0;
        talentPoints.trippleShoot.setEnabled(false);

        talentPoints.bigShootEnable = false;
        talentPoints.bigShootsPoints = 0;
        talentPoints.bigShoot.setEnabled(false);

        hero.bulletCounter = 0;

        bulletsMissed = 0;
        allShootsBulletsnumber = 0;

        firstaid.helpActive = false;

        if (enemies.size() < 1) {
            hero.bullets.removeAll(hero.bullets);
        }

        if (boos != null) {
            boos.ActiveBoss = false;
            boos.bullets.removeAll(bosseBullets);
            boos.bullets.removeAll(boos.bullets);
        }

    }

    public void checkBoss() {

        checkHealth();
    }

    public void makeButtonsMenuVisible(boolean tf) {
        load.setVisible(tf);
        save.setVisible(tf);
        exit.setVisible(tf);
        goToShop.setVisible(tf);
    }

    public void CreateEnemiesAgain() {

        if (death) {
            enemiesNumber = 3;
            death = false;
        }

        enemiesNumber = (talentPoints.LEVEL * enemiesNumber) - (talentPoints.LEVEL * enemiesNumber / 3);
        if (talentPoints.LEVEL > 4) {
            enemiesNumber = (talentPoints.LEVEL * 10);
        }

        if (talentPoints.LEVEL % 8 != 0) {
            howManyEnemiesInLine();
            int[] randomArray = generateRandomArray(5, 0, 15);  //3,0,15
            // size ramka.getwidth/160                        max ramka.getWidth/80

            int alotOfEnemiesGap = enemiesNumber / 25;
            System.out.println(enemiesNumber);
            System.out.println("level: " + talentPoints.LEVEL + " * enenmies number : " + enemiesNumber);

            for (int i = 0; i < enemiesNumber; i++) {
                if (i % iloscNaOS == 0) {
                    randomArray = generateRandomArray(iloscNaOS, 0, 15);
                }

                enemies.add(new Enemy(randomArray[i % iloscNaOS] * 50, (int) Math.floor((i / iloscNaOS)) * (-alotOfEnemiesGap * 50), talentPoints));
            }
        } else {
            boos.ActiveBoss = true;
        }

        if (enemies.size() <= 1) {
            howManyEnemiesInLine();
            int[] randomArray = generateRandomArray(5, 0, 15);  //3,0,15

            int alotOfEnemiesGap = enemiesNumber / 25;
            System.out.println(enemiesNumber);
            System.out.println("level: " + talentPoints.LEVEL + " * enenmies number : " + enemiesNumber);

            for (int i = 0; i < enemiesNumber; i++) {
                if (i % iloscNaOS == 0) {
                    randomArray = generateRandomArray(iloscNaOS, 0, 15);
                }

                enemies.add(new Enemy(randomArray[i % iloscNaOS] * 50, (int) Math.floor((i / iloscNaOS)) * (-alotOfEnemiesGap * 50), talentPoints));
            }
        }

        if (talentPoints.LEVEL > 0) {
            obstacle = new Obstacle(this);
            quantityOfObstacle();
            obstacle.obstacleActive = true;
        }

        firstaid = new Help(WIDTH);

        // System.out.println(enemiesNumber + " ilość przeciwników co do lvlu");
        // System.out.println(enemies.size() + " wielkość tablicy która powinna być taka sama co ilość przeciwników");
    }

    public void CreateEnemiesAgainResizeMode() {

        if (death) {
            enemiesNumber = 3;
            death = false;
        }

        enemiesNumber = (talentPoints.LEVEL * enemiesNumber) - (talentPoints.LEVEL * enemiesNumber / 3);
        if (talentPoints.LEVEL > 4) {
            enemiesNumber = (talentPoints.LEVEL * 10);
        }

        if (talentPoints.LEVEL % 8 != 0) {
            howManyEnemiesInLine();
            int[] randomArray = generateRandomArray(5, 0, 15);  //3,0,15
            // size ramka.getwidth/160                        max ramka.getWidth/80

            int alotOfEnemiesGap = enemiesNumber / 25;
            System.out.println(enemiesNumber);
            System.out.println("level: " + talentPoints.LEVEL + " * enenmies number : " + enemiesNumber);

            for (int i = 0; i < enemiesNumber; i++) {
                if (i % iloscNaOS == 0) {
                    randomArray = generateRandomArray(5, 0, 15);
                }

                enemies.add(new Enemy(randomArray[i % iloscNaOS] * 50, (int) Math.floor((i / iloscNaOS)) * (-alotOfEnemiesGap * 50), talentPoints));
            }
        } else {
            boos.ActiveBoss = true;
        }

        if (enemies.size() <= 1) {
            howManyEnemiesInLine();
            int[] randomArray = generateRandomArray(5, 0, 15);  //3,0,15

            int alotOfEnemiesGap = enemiesNumber / 25;
            System.out.println(enemiesNumber);
            System.out.println("level: " + talentPoints.LEVEL + " * enenmies number : " + enemiesNumber);

            for (int i = 0; i < enemiesNumber; i++) {
                if (i % iloscNaOS == 0) {
                    randomArray = generateRandomArray(iloscNaOS, 0, 15);
                }

                enemies.add(new Enemy(randomArray[i % iloscNaOS] * 50, (int) Math.floor((i)) * (-alotOfEnemiesGap * 50), talentPoints));
            }
        }

        if (talentPoints.LEVEL > 0) {
            obstacle = new Obstacle(this);
            quantityOfObstacle();
            obstacle.obstacleActive = true;
        }

        firstaid = new Help(WIDTH);

        // System.out.println(enemiesNumber + " ilość przeciwników co do lvlu");
        // System.out.println(enemies.size() + " wielkość tablicy która powinna być taka sama co ilość przeciwników");
    }


    public static int[] generateRandomArray(int size, int min, int max) {

        if (max - min + 1 < size) {
            throw new IllegalArgumentException("Cannot generate unique array. Range is too small.");
        }

        int[] randomArray = new int[size];
        Set<Integer> uniqueSet = new HashSet<>();
        Random random = new Random();

        //Tworzy tablicę o rozmiarze size, która będzie przechowywać wygenerowane losowe liczby.
        //Tworzy zbiór (Set) uniqueSet, który będzie przechowywał unikalne liczby.
        //Używamy Set do zapewnienia, że nie będzie duplikatów w naszych wygenerowanych liczbach.

        while (uniqueSet.size() < size) {
            int randomNum = random.nextInt(max - min + 1) + min;
            uniqueSet.add(randomNum);
        }
        //Pętla while wykonuje się, dopóki rozmiar uniqueSet (ilość unikalnych liczb w zbiorze) jest mniejszy niż size (żądana liczba unikalnych liczb w tablicy).
        //Generuje losową liczbę (randomNum) w zakresie od min do max (włącznie) za pomocą random.nextInt(max - min + 1) + min.
        //Następnie dodaje tę losową liczbę do uniqueSet.

        int index = 0;
        for (int number : uniqueSet) {
            randomArray[index++] = number;
        }

        //Przechodzi przez każdą unikalną liczbę w uniqueSet za pomocą pętli for-each.
        //Przypisuje każdą z tych unikalnych liczb do odpowiedniego indeksu w tablicy randomArray.

        return randomArray;
    }


    //metoda generateRandomArray generuje tablicę randomArray o podanej
    // wielkości size, zawierającą losowe, unikalne liczby z
    // zakresu od min do max (włącznie). Jeśli próbujemy wygenerować
    // więcej unikalnych liczb, niż jest możliwe z danego zakresu, metoda wyrzuci
    // wyjątek. W przypadku przykładu z 3, 0, 15 oznacza to, że chcemy
    // wygenerować tablicę 3 unikalnych liczb z zakresu od 0 do 15 (włącznie).


    public Dimension getPreferredSize() {
        if (!fullScreenMode) {
            return new Dimension(WIDTH, HEIGHT);
        }else
            return new Dimension(screenSize.width,screenSize.height);
    }
}


//***************** założenia *******************************
// klasy: bullet która dziedziczy po HERO
// EnemyBullet która dziedziczy po Boss

// klasa hero "produkuje" strzały (bullets) dodając do swojej tablicy za każdym strzałem jedną (przy odblokwoanym talencie 3 bullety)
// bullety są malowane poprzez przekazanie Jpanel do metody malującej w Klasach głównych (HERO i BOSS)

// Przyciski również są malowane, ustawiane/ukrywane w tych klasach

// w głównej klasie panel w większosci powinny znajodwać się metody do: kolizji, generowania ilości przeciwników, zarządzaniem pubnktami

// Klasa Talent points rozszerza możliwości bohatera ( więcej życia, potrójny strzał...) poprzez odblokowanie dzięki punktom i
// zmiennym boolien które sa przekazywane do klasy Hero.

// zapis oprócz funkcji wyboru z listy wcześniej zapisanego użytkownika wczytuje również punkty i talenty odblokowane.