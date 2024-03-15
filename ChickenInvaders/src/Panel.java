import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class Panel extends JPanel {

    final int WIDTH = 800;
    final int HEIGHT = 800;

    int scorePoints = 10;
    int coinsMoney = 100;

    Hero hero = new Hero();
    Enemy enemy;
    String name = "";
    boolean death = false;

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
    int movebackgroundY = -1600;
    int allEnemiesKilled = 0;
    int allShootsBulletsnumber = 0;
    int bulletsMissed = allShootsBulletsnumber - allEnemiesKilled;

    Boos boos = new Boos();
    Obstacle obstacle = new Obstacle();
    Help firstaid = new Help();
    List<Coins> coinsQuantity = new ArrayList<>();

    TalentPoints talentPoints = new TalentPoints(this, obstacle.obstacleActive, obstacle,hero,enemy);
    int health = 99 + talentPoints.healthPoints;
    MENU menu = new MENU(talentPoints, hero, this, save, load, exit,goToShop);
    boolean[][] reflectOfBullet = new boolean[350][obstacle.getQuantity()];



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
        this.add(goToShop);
        goToShop.setVisible(false);

    }


    enum STATE {
        MENU,
        GAME,
        Pause,
        TalentPoints,
    }

    public static STATE State = STATE.MENU; /// zmienic na GAME zeby grac STATE.STATE.TalentPoints

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

            int alotOfEnemiesGap = enemiesNumber / 25;
            System.out.println(enemiesNumber);
            System.out.println("level: " + talentPoints.LEVEL + " * enenmies number : " + enemiesNumber);

            for (int i = 0; i < enemiesNumber; i++) {
                if (i % iloscNaOS == 0) {
                    randomArray = generateRandomArray(iloscNaOS, 0, 15);
                }

                enemies.add(new Enemy(randomArray[i % iloscNaOS] * 50, (int) Math.floor((i / iloscNaOS)) * (-alotOfEnemiesGap * 50),talentPoints));
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

                enemies.add(new Enemy(randomArray[i % iloscNaOS] * 50, (int) Math.floor((i / iloscNaOS)) * (-alotOfEnemiesGap * 50),talentPoints));
            }
        }

        if (talentPoints.LEVEL > 0) {
            obstacle = new Obstacle();
            quantityOfObstacle();
            obstacle.obstacleActive = true;
        }
        
       // System.out.println(enemiesNumber + " ilość przeciwników co do lvlu");
       // System.out.println(enemies.size() + " wielkość tablicy która powinna być taka sama co ilość przeciwników");
    }

    private void howManyEnemiesInLine() {

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


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (State == STATE.MENU) {
            menu.Draw(g2d, start, exit);
        } else if (State == STATE.GAME) {
            this.removeAll(); // < czyści żeby zostały wsyztskie potrzebne info
            talentPoints.setButtons(false); // ukrywa buttony z talentów

            load.setVisible(false);
            save.setVisible(false);
            exit.setVisible(false);
            goToShop.setVisible(false);

            background.paintIcon(this, g2d, 0, movebackgroundY);
            movebackgroundY = movebackgroundY + 1;
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
                boos.paint(g2d, this);

                for (EnemyBullets e : bosseBullets) {  //eneny to jest "i"
                    e.paint(g2d, this);
                }

            } else {                 // jak boss jest aktywny nie usuwaj bulletów, bo automatycznie usuwa gdy chickenów małych jest 0 i wychodzi do menu
                if (enemies.size() < 1) {
                    obstacle.obstacleActive = false;
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
            for (Coins c: coinsQuantity) {
                c.paint(g2d,this);
            }


            //infopanel
            assert boos != null;
            if (!boos.ActiveBoss) {
                this.info.paintInfopanel(g2d, enemies.size(), health, hero.bulletCounter, firstaid.firstAidleft, coinsMoney,this);
            } else {
                this.info.paintInfopanel(g2d, boos.bossHP, health, hero.bulletCounter, firstaid.firstAidleft, coinsMoney,this); //< info zamiast kurczków to hp bosa
            }

        } else if (State == STATE.Pause) {

            menu.pause(g2d);

        } else if (State == STATE.TalentPoints) {

            hero.bullets.removeAll(hero.bullets);
            talentPoints.paintTalentTree(g2d, this);
            hero.bulletCounter = 0; // wyzeropwanie licznika pocisków wystrzerlonych PODCZAS SESJI
            firstaid = new Help();
        }


    }


    public void move() {

        if (State == STATE.GAME) {

            this.hero.updateMove();

            if (boos != null && boos.ActiveBoss) {
                this.boos.updateMove();
                checkBoss();
                kolizjeBoss();
                checkEnemyShoot();
                if (obstacle.obstacleActive) {
                    obstacleCollision();
                }
            } else {
                this.kolizje();
                for (Enemy i : enemies) {  //eneny to jest "i"
                    i.update(talentPoints,this);
                    if (enemies.size() <= 0) {
                        obstacle.obstacleActive = false;
                    }
                    if (obstacle.obstacleActive) {
                        obstacleCollision();
                    }
                }
                this.checkEnemyY();

                kolizjeCoins();

                for (Coins c: coinsQuantity) {
                    c.update(c);
                }
            }


        }
        this.repaint();
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


                    coinsQuantity.add(new Coins(enemies.get(j).x,enemies.get(j).y));
                    enemies.remove(j);
                    System.out.println("dodano " + j + " do coins");

                    allEnemiesKilled++;
                    hero.bullets.get(i).yShoot = -209;
                    hero.bullets.get(i).xShoot = -1000;
                    System.out.println("pozostało pzzeciwników " + enemies.size());
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

            firstaid.helpActive = false;
            bulletsMissed = hero.bulletCounter - enemiesNumber;
            System.out.println(bulletsMissed + "<< ilosc nietarfionych");

            scorePoints = scorePoints + talentPoints.calcualteStats(enemiesNumber, health, hero.bulletCounter); // pocli9cz se punkty po grze
            talentPoints.LEVEL++;
            System.out.println("promote to level: " + talentPoints.LEVEL);
            talentPoints.tabbedPane.setVisible(true);
            talentPoints.turnOnOffShopButtons(true);
            talentPoints.AddPanel();
            talentPoints.setButtons(true);
            talentPoints.turnOnOffShopButtons(true);
            talentPoints.firstLoad = true;
            State = STATE.TalentPoints;   /// zmień na okno punktów talentów do wydania punktów
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
            if (firstaid.y[i] > 800) {
                firstaid.goDown[i] = false;
                firstaid.x[i] = -20;
                firstaid.y[i] = -20;
            }

        }

    }

    public void kolizjeCoins(){
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

            if (hero.bullets.get(i).yShoot > 0 && hero.bullets.get(i).yShoot < 800) {

                calc++;
                for (int j = 0; j < obstacle.quantity; j++) {

                    if (hero.bullets.get(i).yShoot <= obstacle.y[j] && hero.bullets.get(i).yShoot + 7 > obstacle.y[j] - 7 &&
                            hero.bullets.get(i).xShoot > obstacle.x[j] - 5 && hero.bullets.get(i).xShoot <= obstacle.x[j] + obstacle.obstacleWidth[j]
                            && !reflectOfBullet[i][j]) {

                        System.out.println("zawróć! " + i);

                        if (!talentPoints.antiReflectBullet) {
                            hero.setTurnOfBullet.set(i, hero.setTurnOfBullet.get(i) * (-1)); // odbija sie

                            makeArrayToCheckReflect();
                            reflectOfBullet[i][j] = true;

                        } else {
                            hero.bullets.get(i).yShoot = -209;
                            hero.bullets.get(i).xShoot = -1000;
                            makeArrayToCheckReflect();
                            reflectOfBullet[i][j] = true;

                        }

                    }

                }

                if (hero.bullets.get(i).xShoot >= hero.x + 10 && hero.bullets.get(i).xShoot + 5 <= hero.x + 48 &&
                        hero.bullets.get(i).yShoot > hero.y && hero.bullets.get(i).yShoot < hero.y + 48 && hero.setTurnOfBullet.get(i) == -8) {

                    System.out.println("sam się ustrzeliłeś! :O");
                    System.out.println(countTurn);
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

        //obstacle.setQuantity(12);

        makeArrayToCheckReflect();
        System.out.println(obstacle.getQuantity() + " << ilość kładek");

    }

    public void makeArrayToCheckReflect(){
        reflectOfBullet = new boolean[350][obstacle.getQuantity()];
    }

    public void checkEnemyY() {

        for (Enemy e : enemies) {
            if (e.y > 780 ) {
                e.y = -50; // wyjdzie jeszcze raz zza ekranu
                health--;
                System.out.println("przeciwniik ma punkt");
            }
        }

        coinsQuantity.removeIf(c -> c.y > 780);
        checkHealth();
    }

    public void checkEnemyShoot() {

        for (int b = 0; b < boos.bullets.size(); b++) {
            if (boos.bullets.get(b).yShoot > 780) {
                //boos.bullets.remove(b);
                boos.bullets.get(b).yShoot = -209;
                boos.bullets.get(b).xShoot = -1000;
                health = health - 1;
                System.out.println("przeciwniik ma punkt");
            }
        }
        checkHealth();
    }

    private void checkHealth() {
        if (health <= 0) {
            ImageIcon die = new ImageIcon("src/ICONS/skull.png");
            JOptionPane.showMessageDialog(this, "Straciłeś wszystkie życia!", "Koniec Gry", 0, die);
            resetStatsAfterDeath();
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

    public int enemySkin(){

        if (talentPoints.chickenEnemySkin3bool){
            return 3;
        }


        return 0;
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
        return new Dimension(WIDTH, HEIGHT);
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