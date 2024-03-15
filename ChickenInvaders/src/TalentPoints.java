import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class TalentPoints implements ActionListener {

    Hero hero;
    Enemy enemy;

    ImageIcon healthIcon = new ImageIcon("src/ICONS/heart.png");
    JButton additionalHealth = new JButton(healthIcon);
    boolean addHealth = false;
    int healthPoints = 0;
    JLabel healthPointsLabel = new JLabel(" health: " + healthPoints + " / 5");

    ImageIcon shoot3x = new ImageIcon("src/ICONS/shoot3x.png");
    JButton trippleShoot = new JButton(shoot3x);
    boolean tripleShoots = false;
    int trippleShootsPoints = 0;
    JLabel trippleShootshPointsLabel = new JLabel("Tripple shoot: " + trippleShootsPoints + " / 10");

    ImageIcon biggerShootIcon = new ImageIcon("src/ICONS/BigSHoot.png");
    JButton bigShoot = new JButton(biggerShootIcon);
    boolean bigShootEnable = false;
    int bigShootsPoints = 0;
    JLabel BigShootPointsLabel = new JLabel("BIG shoot: " + bigShootsPoints + " / 1");

    int LEVEL = 1; // default 1
    public int score = 10;

    JPanel buttonsAndScore = new JPanel();
    JButton returnToMenu = new JButton("<<-return to menu");
    JButton nextLevelButton = new JButton();


    boolean firstLoad = true;

    Panel panel;
    Obstacle obstacle;
    boolean obstacleActive;

    ImageIcon antiReflectBulletIcon = new ImageIcon("src/ICONS/reflect.png");
    JButton antiReflectButton = new JButton(antiReflectBulletIcon);
    boolean antiReflectBullet = false;
    int antiReflectPoints = 0;
    JLabel antiReflectPointsLabel = new JLabel("anti reflection: " + antiReflectPoints + " / 1");

    JTabbedPane tabbedPane = new JTabbedPane();

    JPanel talents = new JPanel();
    JPanel skins = new JPanel();

    JPanel enemySkins = new JPanel();
    JPanel shipSkins = new JPanel();

    ImageIcon chickenEnemySkin1 = new ImageIcon("src/ICONS/chickenEnemySkin1Icon.png");
    JButton chickenEnemySkin_1 = new JButton(chickenEnemySkin1);
    int eSkin1Points = 0;
    JLabel eSkin1Label = new JLabel();
    boolean chickenEnemySkin1bool = false;

    ImageIcon chickenEnemySkin2 = new ImageIcon("src/ICONS/chickenEnemySkin2Icon.png");
    JButton chickenEnemySkin_2 = new JButton(chickenEnemySkin2);
    int eSkin2Points = 0;
    JLabel eSkin2Label = new JLabel();
    boolean  chickenEnemySkin2bool = false;

    ImageIcon chickenEnemySkin3 = new ImageIcon("src/ICONS/chickenEnemySkin3Icon.png");
    JButton chickenEnemySkin_3 = new JButton(chickenEnemySkin3);
    int eSkin3Points = 0;
    JLabel eSkin3Label = new JLabel();
    boolean chickenEnemySkin3bool = false;

    ImageIcon spaceShipSkin1 = new ImageIcon("src/ICONS/spaceShipSkinIcoon1.png");
    JButton spaceShipSkin_1 = new JButton(spaceShipSkin1);
    int shipSkin1Points = 0;
    JLabel shipSkin1Label = new JLabel();
    boolean spaceShipSkin1bool = false;

    ImageIcon cartoonShipSkin1 = new ImageIcon("src/ICONS/cartoonShipIcon.png");
    JButton cartoonShipSkin = new JButton(cartoonShipSkin1);
    int shipSkin2Points = 0;
    JLabel shipSkin2Label = new JLabel();
    boolean cartoonShipSkin1bool = false;


    TalentPoints(Panel panel, boolean obstacleActove, Obstacle obstacle, Hero hero, Enemy enemy) {


        this.panel = panel;
        this.obstacle = obstacle;
        this.obstacleActive = obstacleActove;
        this.hero = hero;
        this.enemy = enemy;

        if (Panel.State == Panel.STATE.TalentPoints) {
            panel.setLayout(new BorderLayout());
            AddPanel();
            setButtons(true);
            turnOnOffShopButtons(true);
            CheckIfTalentCanBeActive();
            updateByTalents();
            panel.repaint();
        }


        additionalHealth.addActionListener(this);
        trippleShoot.addActionListener(this);
        returnToMenu.addActionListener(this);
        nextLevelButton.addActionListener(this);
        bigShoot.addActionListener(this);
        antiReflectButton.addActionListener(this);


        chickenEnemySkin_1.addActionListener(this);
        chickenEnemySkin_2.addActionListener(this);
        chickenEnemySkin_3.addActionListener(this);
        spaceShipSkin_1.addActionListener(this);
        cartoonShipSkin.addActionListener(this);


    }

    public void AddPanel() {

        panel.setLayout(new BorderLayout());
        panel.add(tabbedPane, BorderLayout.WEST);
        panel.add(buttonsAndScore,BorderLayout.SOUTH);

        tabbedPane.setBackground(Color.LIGHT_GRAY);
        tabbedPane.setPreferredSize(new Dimension(550,200));
        tabbedPane.addChangeListener(e -> System.out.println(tabbedPane.getSelectedIndex()));

        tabbedPane.add("Talent", talents);

        talents.setBackground(Color.black);
        talents.setPreferredSize(new Dimension(200, 400));
        talents.setLayout(null);

        Font statsFont = new Font("Arial", Font.BOLD, 22);

        talents.add(additionalHealth);
        talents.add(healthPointsLabel);
        talents.add(bigShoot);
        talents.add(BigShootPointsLabel);
        talents.add(trippleShoot);
        talents.add(trippleShootshPointsLabel);
        talents.add(antiReflectButton);
        talents.add(antiReflectPointsLabel);

        additionalHealth.setBounds(60, 20, 80, 80);
        additionalHealth.setBackground(Color.black);
        healthPointsLabel.setForeground(Color.WHITE);
        healthPointsLabel.setFont(statsFont);

        healthPointsLabel.setBounds(160, 20, 180, 80);
        healthPointsLabel.setBackground(Color.black);

        trippleShoot.setBounds(60, 150, 80, 80);
        trippleShoot.setBackground(Color.black);
        trippleShootshPointsLabel.setForeground(Color.WHITE);
        trippleShootshPointsLabel.setFont(statsFont);

        trippleShootshPointsLabel.setBounds(160, 150, 280, 80);
        trippleShootshPointsLabel.setBackground(Color.black);

        bigShoot.setBounds(60, 280, 80, 80);
        bigShoot.setBackground(Color.black);
        BigShootPointsLabel.setForeground(Color.WHITE);
        BigShootPointsLabel.setFont(statsFont);

        BigShootPointsLabel.setBounds(160, 280, 280, 80);
        BigShootPointsLabel.setBackground(Color.black);

        antiReflectButton.setBounds(60, 410, 80, 80);
        antiReflectButton.setBackground(Color.black);
        antiReflectPointsLabel.setForeground(Color.WHITE);
        antiReflectPointsLabel.setFont(statsFont);

        antiReflectPointsLabel.setBounds(160, 410, 1280, 80);
        antiReflectPointsLabel.setBackground(Color.black);

        skins.setLayout(new GridLayout());
        tabbedPane.add("Shop", skins);

        skins.add(enemySkins);
        enemySkins.setLayout(new GridLayout(3, 2));

        enemySkins.add(chickenEnemySkin_1);
        enemySkins.add(eSkin1Label);
        enemySkins.add(chickenEnemySkin_2);
        enemySkins.add(eSkin2Label);
        enemySkins.add(chickenEnemySkin_3);
        enemySkins.add(eSkin3Label);
//
        skins.add(shipSkins);
//
        shipSkins.add(spaceShipSkin_1);
        shipSkins.add(shipSkin1Label);
        shipSkins.add(cartoonShipSkin);
        shipSkins.add(shipSkin2Label);

        Border borderEnemy = new TitledBorder("Enemy Skins");
        Border borderShip = new TitledBorder("Ship Skins");

        enemySkins.setBorder(borderEnemy);
        enemySkins.setBackground(Color.GRAY);

        shipSkins.setBorder(borderShip);
        shipSkins.setBackground(Color.GRAY);

        buttonsAndScore.setLayout(new BorderLayout());
        buttonsAndScore.add(returnToMenu,BorderLayout.LINE_START);
        returnToMenu.setBackground(new Color(204, 30, 41));
        nextLevelButton.setText("Go level " + LEVEL);
        nextLevelButton.setToolTipText("GO AHEAD!");
        buttonsAndScore.add(nextLevelButton,BorderLayout.LINE_END);
        buttonsAndScore.setBackground(Color.black);
        buttonsAndScore.setPreferredSize(new Dimension(800,50));

        returnToMenu.setVisible(true);
        returnToMenu.setBounds(20, 700, 200, 40);

        nextLevelButton.setBounds(550, 700, 200, 40);
        nextLevelButton.setBackground(new Color(204, 30, 41));

        tabbedPane.setVisible(true);

        turnOnOffShopButtons(true);
        setButtons(true);
    }


    public int calcualteStats(int enemiesnumber, int health, int bulletcounter) {

        if (bulletcounter == 0) {
            score = (enemiesnumber * 100);
        } else {
            score = (enemiesnumber * 100) / bulletcounter / 10;
        }

        return score;
    }

    public void paintTalentTree(Graphics2D g2d, Panel mainPanel) {


        g2d.setColor(Color.white);
        Font statsFont = new Font("Arial", Font.BOLD, 22);
        g2d.setFont(statsFont);

        Font levelFont = new Font("Arial", Font.BOLD, 18);

        g2d.drawString(" health: " + healthPoints + " / 5", 3, 120);

        g2d.drawString("Tripple shoot:" + trippleShootsPoints + " / 10", 5, 250);

        g2d.drawString("BIG shoot:" + bigShootsPoints + " / 1", 5, 390);

        g2d.drawString("anti reflection :" + antiReflectPoints + " / 1", 5, 520);


        g2d.setColor(Color.ORANGE);


        g2d.drawRect(580, 150, 300, 380);

        g2d.drawString("All bullets counter :", 600, 180);
        g2d.drawString(String.valueOf(mainPanel.allShootsBulletsnumber), 600, 210);

        g2d.drawString("Enemies Killed :", 600, 280);
        g2d.drawString(String.valueOf(mainPanel.allEnemiesKilled), 600, 310);

        g2d.drawString("Bullets missed :", 600, 380);
        g2d.drawString(String.valueOf(mainPanel.bulletsMissed), 600, 410);

        double ratio = 0;
        String ratioString = "";
        if (mainPanel.allEnemiesKilled > 0 && mainPanel.allShootsBulletsnumber > 0) {
            ratio = (double) mainPanel.allEnemiesKilled / mainPanel.allShootsBulletsnumber;
            DecimalFormat df = new DecimalFormat("#.##");
            ratioString = df.format(ratio);
        }
        g2d.drawString("ratio: ", 600, 480);
        g2d.drawString(ratioString, 600, 510);


        g2d.drawString("Score points :", 300, 730);
        g2d.drawString(String.valueOf(score), 350, 760);

        g2d.drawString(" " + panel.coinsMoney, 600, 50);
        ImageIcon coinIcon = new ImageIcon("src/ICONS/smallCoin-unscreen.gif");
        coinIcon.paintIcon(panel,g2d,600,50);

        if (firstLoad) {
            CheckIfTalentCanBeActive();
            tabbedPane.setVisible(true);
            turnOnOffShopButtons(true);
            AddPanel();
            setButtons(true);
            turnOnOffShopButtons(true);
            firstLoad = false;
            CheckIfTalentCanBeActive();
        }


    }

    public void setButtons(boolean tf) {

        additionalHealth.setVisible(tf);
        trippleShoot.setVisible(tf);
        nextLevelButton.setVisible(tf);
        bigShoot.setVisible(tf);
        returnToMenu.setVisible(tf);
        antiReflectButton.setVisible(tf);
        CheckIfTalentCanBeActive();

    }

    private void CheckIfTalentCanBeActive() {

        //additionalHealth
        if (panel.allEnemiesKilled < 10) {
            additionalHealth.setToolTipText("to unlock, kill at least 10 chickens!");
            additionalHealth.setEnabled(false);
        } else {
            additionalHealth.setToolTipText("add 1 health per 1 point");
            additionalHealth.setEnabled(true);
        }

        //additionalBullets 3x
        if (panel.allEnemiesKilled < 50) {
            trippleShoot.setToolTipText("to unlock, kill at least 50 chickens!");
            trippleShoot.setEnabled(false);
            tripleShoots = false;
        } else {
            trippleShoot.setToolTipText("makes you 3x better");
            trippleShoot.setEnabled(true);
        }

        //big shoot
        if (panel.allEnemiesKilled < 100) {
            bigShoot.setToolTipText("to unlock, kill at least 100 chickens!");
            bigShoot.setEnabled(false);
        } else {
            bigShootEnable = true;
            bigShoot.setToolTipText("One big Shoot for pleasure");

            bigShoot.setEnabled(true);
        }

        if (panel.allEnemiesKilled < 150) {
            antiReflectButton.setToolTipText("to unlock, kill at least 150 chickens!");
            antiReflectButton.setEnabled(false);
        } else {
            antiReflectButton.setToolTipText("no reflection from obstacles");

            antiReflectButton.setEnabled(true);
        }

        if (shipSkin2Points == 15 && chickenEnemySkin2bool) {
            cartoonShipSkin.setBackground(new Color(0, 154, 0));
        }

        if (shipSkin1Points == 15 && chickenEnemySkin1bool) {
            spaceShipSkin_1.setBackground(new Color(0, 154, 0));
        }

        if (eSkin3Points == 15 && chickenEnemySkin3bool) {
            chickenEnemySkin_3.setBackground(new Color(0, 154, 0));
        }

        if (eSkin2Points == 15 && chickenEnemySkin2bool) {
            chickenEnemySkin_2.setBackground(new Color(0, 154, 0));
        }

        if (eSkin1Points == 15 && chickenEnemySkin1bool) {
            chickenEnemySkin_1.setBackground(new Color(0, 154, 0));
        }


    }

    private void updateByTalents() {

        if (healthPoints == 5) {
            additionalHealth.setBorder(BorderFactory.createLineBorder(new Color(0, 154, 0)));
            additionalHealth.setToolTipText("all health points enabled");
        }
        System.out.println(healthPoints + " =health +");

        if (trippleShootsPoints == 10) {
            tripleShoots = true;
            trippleShoot.setBorder(BorderFactory.createLineBorder(new Color(0, 154, 0)));
            trippleShoot.setToolTipText("shoot x3 enabled");
        }
        System.out.println(tripleShoots + " =shoot x3");


        if (bigShootsPoints == 10) {
            bigShoot.setBorder(BorderFactory.createLineBorder(new Color(0, 154, 0)));
            bigShoot.setToolTipText("Big Shoot enabled");
        }

        if (antiReflectPoints == 5) {
            antiReflectButton.setBorder(BorderFactory.createLineBorder(new Color(0, 154, 0)));
            antiReflectButton.setToolTipText("Anti Reflect Shoot enabled");
        }

    }


    public void turnOnOffShopButtons(boolean tf) {

        Font statsFont = new Font("Arial", Font.BOLD, 22);

        chickenEnemySkin_1.setVisible(tf);
        chickenEnemySkin_1.setBackground(Color.black);
        chickenEnemySkin_1.setBounds(60, 100, 60, 60);
        eSkin1Label.setVisible(tf);
        eSkin1Label.setBackground(Color.black);
        eSkin1Label.setBounds(150, 100, 60, 60);
        eSkin1Label.setFont(statsFont);
        eSkin1Label.setForeground(Color.WHITE);
        eSkin1Label.setText("points: " + eSkin1Points + "/15");

        chickenEnemySkin_2.setVisible(tf);
        chickenEnemySkin_2.setBackground(Color.black);
        chickenEnemySkin_2.setBounds(60, 200, 60, 60);
        eSkin2Label.setVisible(tf);
        eSkin2Label.setBackground(Color.black);
        eSkin2Label.setFont(statsFont);
        eSkin2Label.setForeground(Color.WHITE);
        eSkin2Label.setBounds(150, 200, 60, 60);
        eSkin2Label.setText("points: " + eSkin2Points + "/15");

        chickenEnemySkin_3.setVisible(tf);
        chickenEnemySkin_3.setBackground(Color.black);
        chickenEnemySkin_3.setBounds(60, 300, 60, 60);
        eSkin3Label.setVisible(tf);
        eSkin3Label.setBackground(Color.black);
        eSkin3Label.setFont(statsFont);
        eSkin3Label.setForeground(Color.WHITE);
        eSkin3Label.setBounds(150, 300, 60, 60);
        eSkin3Label.setText("points: " + eSkin3Points + "/15");

        spaceShipSkin_1.setVisible(tf);
        spaceShipSkin_1.setBackground(Color.black);
        chickenEnemySkin_1.setBounds(60, 400, 60, 60);
        shipSkin1Label.setVisible(tf);
        shipSkin1Label.setBackground(Color.black);
        shipSkin1Label.setFont(statsFont);
        shipSkin1Label.setForeground(Color.WHITE);
        shipSkin1Label.setBounds(150, 400, 60, 60);
        shipSkin1Label.setText("points: " + shipSkin1Points + "/15");

        cartoonShipSkin.setVisible(tf);
        cartoonShipSkin.setBackground(Color.black);
        cartoonShipSkin.setBounds(60, 500, 60, 60);
        shipSkin2Label.setVisible(tf);
        shipSkin2Label.setBackground(Color.black);
        shipSkin2Label.setFont(statsFont);
        shipSkin2Label.setForeground(Color.WHITE);
        shipSkin2Label.setBounds(150, 500, 60, 60);
        shipSkin2Label.setText("points: " + shipSkin2Points + "/15");


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == additionalHealth && score > 0) {
            if (healthPoints < 5) {
                healthPoints += 1;
                panel.health = 5 + healthPoints;
                healthPointsLabel.setText(" health: " + healthPoints + " / 5");
                score--;
            }
            updateByTalents();
            System.out.println("klkikam");
        }

        if (e.getSource() == trippleShoot && trippleShoot.isEnabled()) {
            if (trippleShootsPoints < 10 && score > 0) {
                trippleShootsPoints += 1;
                trippleShootshPointsLabel.setText("Tripple shoot: " + trippleShootsPoints + " / 10");
                score--;
            }
            updateByTalents();
        }

        if (e.getSource() == bigShoot) { //TODO OGARNĄC TEN BIGsHOT
            if (bigShootsPoints < 1 && score > 0) {
                bigShootsPoints++;
                BigShootPointsLabel.setText("BIG shoot: " + bigShootsPoints + " / 1");
                score--;
            }
            updateByTalents();
        }

        if (e.getSource() == antiReflectButton) {
            if (antiReflectPoints < 5 && score > 0) {
                antiReflectPoints++;
                antiReflectPointsLabel.setText("anti reflection: " + antiReflectPoints + " / 1");
                score--;
            }
            updateByTalents();
        }


        if (e.getSource() == chickenEnemySkin_1) {
            if (eSkin1Points < 15 && panel.coinsMoney > 0) {
                eSkin1Points++;
                eSkin1Label.setText("points: " + eSkin1Points + "/15");
                panel.coinsMoney--;
            }

            if (eSkin1Points == 15 && !chickenEnemySkin1bool){
                chickenEnemySkin_1.setBackground(new Color(0, 154, 0));
                chickenEnemySkin_3.setBackground(Color.black);
                chickenEnemySkin_2.setBackground(Color.black);

                chickenEnemySkin3bool = false;
                chickenEnemySkin2bool = false;
                chickenEnemySkin1bool = true;
            }

        }

        if (e.getSource() == chickenEnemySkin_2) {
            if (eSkin2Points < 15 && panel.coinsMoney > 0) {
                eSkin2Points++;
                eSkin2Label.setText("points: " + eSkin2Points + "/15");
                panel.coinsMoney--;
            }

            if (eSkin2Points == 15 && !chickenEnemySkin2bool){
                chickenEnemySkin_2.setBackground(new Color(0, 154, 0));
                chickenEnemySkin_3.setBackground(Color.black);
                chickenEnemySkin_1.setBackground(Color.black);

                chickenEnemySkin3bool = false;
                chickenEnemySkin2bool = true;
                chickenEnemySkin1bool = false;
            }

        }

        if (e.getSource() == chickenEnemySkin_3) {
            if (eSkin3Points < 15 && panel.coinsMoney > 0) {
                eSkin3Points++;
                System.out.println(eSkin3Points);
                eSkin3Label.setText("points: " + eSkin3Points + "/15");
                panel.coinsMoney--;
            }

            if (eSkin3Points == 15 && !chickenEnemySkin3bool){
                chickenEnemySkin_3.setBackground(new Color(0, 154, 0));
                chickenEnemySkin_2.setBackground(Color.black);
                chickenEnemySkin_1.setBackground(Color.black);

                chickenEnemySkin3bool = true;
                chickenEnemySkin2bool = false;
                chickenEnemySkin1bool = false;
            }

        }

        if (e.getSource() == spaceShipSkin_1) {
            if (shipSkin1Points < 15 && panel.coinsMoney > 0) {
                shipSkin1Points++;
                shipSkin1Label.setText("points: " + shipSkin1Points + "/15");
                panel.coinsMoney--;
            }


            if (shipSkin1Points == 15 && !cartoonShipSkin1bool){
                spaceShipSkin_1.setBackground(new Color(0, 154, 0));
                cartoonShipSkin.setBackground(Color.black);
                hero.heroIcon = new ImageIcon("src/ICONS/spaceShipSkin1.png");
                cartoonShipSkin1bool = true;
                chickenEnemySkin2bool = false;
            }

        }

        if (e.getSource() == cartoonShipSkin) {
            if (shipSkin2Points < 15 && panel.coinsMoney > 0) {
                shipSkin2Points++;
                shipSkin2Label.setText("points: " + shipSkin2Points + "/15");
                panel.coinsMoney--;
            }

            if (shipSkin2Points == 15 && !chickenEnemySkin2bool){
                cartoonShipSkin.setBackground(new Color(0, 154, 0));
                spaceShipSkin_1.setBackground(Color.black);
                hero.heroIcon = new ImageIcon("src/ICONS/cartoonShip.png");
                chickenEnemySkin2bool = true;
                cartoonShipSkin1bool = false;
            }

        }


        if (e.getSource() == nextLevelButton) {
            panel.makeArrayToCheckReflect();
            System.out.println("next LEVEL!");
            updateByTalents();
            System.out.println(healthPoints + " << health points " + addHealth);
            System.out.println(trippleShootsPoints + " << tripple shots points " + tripleShoots);
            System.out.println(bigShootsPoints + " << bigShooot points " + bigShootEnable);
            System.out.println(antiReflectPoints + " << Anti reflect points " + antiReflectBullet);
            obstacleActive = true;
            //tutaj zapis
            tabbedPane.setVisible(false);
            panel.setLayout(new FlowLayout());
            Panel.State = Panel.STATE.GAME;
        }

        if (e.getSource() == returnToMenu) {
            updateByTalents();
            System.out.println("return to Main menu");
            setButtons(false);
            turnOnOffShopButtons(false);
            tabbedPane.setVisible(false);
            enemySkins.setVisible(false);
            shipSkins.setVisible(false);

            System.out.println();

            panel.setLayout(new FlowLayout());
            Panel.State = Panel.STATE.MENU;
        }

    }
}
