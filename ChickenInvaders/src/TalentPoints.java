import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
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

    int LEVEL = 6; // default 1
    public int score = 10;

    JPanel buttonsAndScore = new JPanel();
    JButton returnToMenu = new JButton("<<-return to menu");
    JButton nextLevelButton = new JButton();
    JPanel FullscreenMode = new JPanel();

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
    JPanel centerInfoPanel = new JPanel();

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

    JLabel allshots;
    JLabel allShotsCount;
    JLabel enemiesKilled;
    JLabel enemiesKilledCount;
    JLabel bulletsMissed;
    JLabel bulletsMissedCount;
    JLabel ratioLabel;
    JLabel ratioStringLabel;
    JLabel scorePoints;
    JLabel valueOfScore;
    JLabel coinsmoney;
    JLabel coinsmoneyImage;
    ImageIcon coinIcon = new ImageIcon("src/ICONS/smallCoin-unscreen.gif");


    TalentPoints(Panel panel, boolean obstacleActove, Obstacle obstacle, Hero hero, Enemy enemy) {


        this.panel = panel;
        this.obstacle = obstacle;
        this.obstacleActive = obstacleActove;
        this.hero = hero;
        this.enemy = enemy;

        if (Panel.State == Panel.STATE.TalentPoints) {
            panel.setLayout(new BorderLayout());
            AddPanel(panel.fullScreenMode,panel.resizeMode);
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

    public void AddPanel(boolean fullScreen, boolean resizeMode) {

        removePanels();
        panel.setLayout(new BorderLayout());

        if (!fullScreen) {
            removePanels();
            panel.add(tabbedPane, BorderLayout.WEST);
            panel.add(buttonsAndScore, BorderLayout.SOUTH);

            tabbedPane.setBackground(Color.LIGHT_GRAY);
            tabbedPane.setPreferredSize(new Dimension(550, 200));
            tabbedPane.addChangeListener(e -> System.out.print(""));

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
            buttonsAndScore.add(returnToMenu, BorderLayout.LINE_START);
            returnToMenu.setBackground(new Color(204, 30, 41));
            nextLevelButton.setText("Go level " + LEVEL);
            nextLevelButton.setToolTipText("GO AHEAD!");
            buttonsAndScore.add(nextLevelButton, BorderLayout.LINE_END);
            buttonsAndScore.setBackground(Color.black);
            buttonsAndScore.setPreferredSize(new Dimension(800, 50));

            returnToMenu.setVisible(true);
            returnToMenu.setBounds(20, 700, 200, 40);

            nextLevelButton.setBounds(550, 700, 200, 40);
            nextLevelButton.setBackground(new Color(204, 30, 41));

            tabbedPane.setVisible(true);
            tabbedPane.setSelectedIndex(0);

            panel.add(centerInfoPanel,BorderLayout.CENTER);
            centerInfoPanel.setBackground(Color.BLACK);
            Border centerInfoBorder = new LineBorder(Color.WHITE);
            centerInfoPanel.setBorder(centerInfoBorder);
            centerInfoPanel.setLayout(new BoxLayout(centerInfoPanel,BoxLayout.Y_AXIS));

            allshots = new JLabel("All bullets counter :");
            allshots.setBounds(0,180,200,40);
            allshots.setFont(statsFont);
            allshots.setForeground(Color.ORANGE);
            allshots.setAlignmentX(Component.CENTER_ALIGNMENT);
            allShotsCount = new JLabel(panel.allShootsBulletsnumber + "");
            allShotsCount.setBounds(0,210,200,40);
            allShotsCount.setFont(statsFont);
            allShotsCount.setForeground(Color.ORANGE);
            allShotsCount.setAlignmentX(Component.CENTER_ALIGNMENT);

            centerInfoPanel.add(allshots);
            centerInfoPanel.add(allShotsCount);

            enemiesKilled = new JLabel("Enemies Killed :");
            enemiesKilled.setFont(statsFont);
            enemiesKilled.setBounds(0,280,200,40);
            enemiesKilled.setForeground(Color.ORANGE);
            enemiesKilled.setAlignmentX(Component.CENTER_ALIGNMENT);
            enemiesKilledCount = new JLabel(panel.allEnemiesKilled + "");
            enemiesKilledCount.setFont(statsFont);
            enemiesKilledCount.setBounds(0,310,200,40);
            enemiesKilledCount.setForeground(Color.ORANGE);
            enemiesKilledCount.setAlignmentX(Component.CENTER_ALIGNMENT);

            centerInfoPanel.add(enemiesKilled);
            centerInfoPanel.add(enemiesKilledCount);

            bulletsMissed = new JLabel("Bullets missed :");
            bulletsMissed.setBounds(0,380,200,40);
            bulletsMissed.setFont(statsFont);
            bulletsMissed.setForeground(Color.ORANGE);
            bulletsMissed.setAlignmentX(Component.CENTER_ALIGNMENT);
            bulletsMissedCount = new JLabel(panel.bulletsMissed + "");
            bulletsMissedCount.setBounds(0,410,200,40);
            bulletsMissedCount.setFont(statsFont);
            bulletsMissedCount.setForeground(Color.ORANGE);
            bulletsMissedCount.setAlignmentX(Component.CENTER_ALIGNMENT);

            centerInfoPanel.add(bulletsMissed);
            centerInfoPanel.add(bulletsMissedCount);


            double ratio = 0;
            String ratioString = "";
            if (panel.allEnemiesKilled > 0 && panel.allShootsBulletsnumber > 0) {
                ratio = (double) panel.allEnemiesKilled / panel.allShootsBulletsnumber;
                DecimalFormat df = new DecimalFormat("#.##");
                ratioString = df.format(ratio);
            }

            ratioLabel = new JLabel("ratio: ");
            ratioLabel.setBounds(0,380,200,40);
            ratioLabel.setFont(statsFont);
            ratioLabel.setForeground(Color.ORANGE);
            ratioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            ratioStringLabel = new JLabel(ratioString);
            ratioStringLabel.setBounds(0,410,200,40);
            ratioStringLabel.setFont(statsFont);
            ratioStringLabel.setForeground(Color.ORANGE);
            ratioStringLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            centerInfoPanel.add(ratioLabel);
            centerInfoPanel.add(ratioStringLabel);


            scorePoints = new JLabel("Score points :");
            scorePoints.setBounds(0,600,200,40);
            scorePoints.setFont(statsFont);
            scorePoints.setForeground(Color.ORANGE);
            scorePoints.setAlignmentX(Component.CENTER_ALIGNMENT);
            valueOfScore = new JLabel(String.valueOf(score));
            valueOfScore.setBounds(0,600,200,40);
            valueOfScore.setFont(statsFont);
            valueOfScore.setForeground(Color.ORANGE);
            valueOfScore.setAlignmentX(Component.CENTER_ALIGNMENT);


            coinsmoney = new JLabel(panel.coinsMoney + "");
            coinsmoney.setBounds(0,600,200,40);
            coinsmoney.setFont(statsFont);
            coinsmoney.setForeground(Color.ORANGE);
            coinsmoney.setAlignmentX(Component.CENTER_ALIGNMENT);


            coinsmoneyImage = new JLabel(coinIcon);
            coinsmoneyImage.setBounds(0,600,200,40);
            coinsmoneyImage.setFont(statsFont);
            coinsmoneyImage.setForeground(Color.ORANGE);
            coinsmoneyImage.setAlignmentX(Component.CENTER_ALIGNMENT);

            centerInfoPanel.add(coinsmoney);
            centerInfoPanel.add(coinsmoneyImage);
            centerInfoPanel.add(scorePoints);
            centerInfoPanel.add(valueOfScore);

            turnOnOffShopButtons(true);
            setButtons(true);
        } else {
            removePanels();
            FullscreenOrResizableMode();
        }

        if (resizeMode){
            removePanels();
            FullscreenOrResizableMode();
        }

    }

    private void FullscreenOrResizableMode(){
        panel.setLayout(new BorderLayout());

        panel.add(talents, BorderLayout.WEST);
        panel.add(centerInfoPanel,BorderLayout.CENTER);
        panel.add(skins,BorderLayout.EAST);
        panel.add(buttonsAndScore, BorderLayout.SOUTH);

        talents.setBackground(Color.black);
        Border talentsBorder = new LineBorder(Color.WHITE);
        talents.setBorder(talentsBorder);
        talents.setPreferredSize(new Dimension(450, 400));
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
        //center inno panel

        // centerInfoPanel.setLayout();
        Border centerBorder = new LineBorder(Color.WHITE);
        BoxLayout centerLayout = new BoxLayout(centerInfoPanel,BoxLayout.Y_AXIS);
        centerInfoPanel.setLayout(centerLayout);
        centerInfoPanel.setBorder(centerBorder);
        centerInfoPanel.setBackground(Color.black);
        centerInfoPanel.setPreferredSize(new Dimension(200,400));

        allshots = new JLabel("All bullets counter :");
        allshots.setBounds(0,180,200,40);
        allshots.setFont(statsFont);
        allshots.setForeground(Color.ORANGE);
        allshots.setAlignmentX(Component.CENTER_ALIGNMENT);
        allShotsCount = new JLabel(panel.allShootsBulletsnumber + "");
        allShotsCount.setBounds(0,210,200,40);
        allShotsCount.setFont(statsFont);
        allShotsCount.setForeground(Color.ORANGE);
        allShotsCount.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerInfoPanel.add(allshots);
        centerInfoPanel.add(allShotsCount);

        enemiesKilled = new JLabel("Enemies Killed :");
        enemiesKilled.setFont(statsFont);
        enemiesKilled.setBounds(0,280,200,40);
        enemiesKilled.setForeground(Color.ORANGE);
        enemiesKilled.setAlignmentX(Component.CENTER_ALIGNMENT);
        enemiesKilledCount = new JLabel(panel.allEnemiesKilled + "");
        enemiesKilledCount.setFont(statsFont);
        enemiesKilledCount.setBounds(0,310,200,40);
        enemiesKilledCount.setForeground(Color.ORANGE);
        enemiesKilledCount.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerInfoPanel.add(enemiesKilled);
        centerInfoPanel.add(enemiesKilledCount);

        bulletsMissed = new JLabel("Bullets missed :");
        bulletsMissed.setBounds(0,380,200,40);
        bulletsMissed.setFont(statsFont);
        bulletsMissed.setForeground(Color.ORANGE);
        bulletsMissed.setAlignmentX(Component.CENTER_ALIGNMENT);
        bulletsMissedCount = new JLabel(panel.bulletsMissed + "");
        bulletsMissedCount.setBounds(0,410,200,40);
        bulletsMissedCount.setFont(statsFont);
        bulletsMissedCount.setForeground(Color.ORANGE);
        bulletsMissedCount.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerInfoPanel.add(bulletsMissed);
        centerInfoPanel.add(bulletsMissedCount);


        double ratio = 0;
        String ratioString = "";
        if (panel.allEnemiesKilled > 0 && panel.allShootsBulletsnumber > 0) {
            ratio = (double) panel.allEnemiesKilled / panel.allShootsBulletsnumber;
            DecimalFormat df = new DecimalFormat("#.##");
            ratioString = df.format(ratio);
        }

        ratioLabel = new JLabel("ratio: ");
        ratioLabel.setBounds(0,380,200,40);
        ratioLabel.setFont(statsFont);
        ratioLabel.setForeground(Color.ORANGE);
        ratioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ratioStringLabel = new JLabel(ratioString);
        ratioStringLabel.setBounds(0,410,200,40);
        ratioStringLabel.setFont(statsFont);
        ratioStringLabel.setForeground(Color.ORANGE);
        ratioStringLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerInfoPanel.add(ratioLabel);
        centerInfoPanel.add(ratioStringLabel);


        scorePoints = new JLabel("Score points :");
        scorePoints.setBounds(0,600,200,40);
        scorePoints.setFont(statsFont);
        scorePoints.setForeground(Color.ORANGE);
        scorePoints.setAlignmentX(Component.CENTER_ALIGNMENT);
        valueOfScore = new JLabel(String.valueOf(score));
        valueOfScore.setBounds(0,600,200,40);
        valueOfScore.setFont(statsFont);
        valueOfScore.setForeground(Color.ORANGE);
        valueOfScore.setAlignmentX(Component.CENTER_ALIGNMENT);


        coinsmoney = new JLabel(panel.coinsMoney + "");
        coinsmoney.setBounds(0,600,200,40);
        coinsmoney.setFont(statsFont);
        coinsmoney.setForeground(Color.ORANGE);
        coinsmoney.setAlignmentX(Component.CENTER_ALIGNMENT);


        coinsmoneyImage = new JLabel(coinIcon);
        coinsmoneyImage.setBounds(0,600,200,40);
        coinsmoneyImage.setFont(statsFont);
        coinsmoneyImage.setForeground(Color.ORANGE);
        coinsmoneyImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerInfoPanel.add(coinsmoney);
        centerInfoPanel.add(coinsmoneyImage);
        centerInfoPanel.add(scorePoints);
        centerInfoPanel.add(valueOfScore);

        ////////////////////////////////
        skins.setLayout(new GridLayout());

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
        Border skinsBorder = new LineBorder(Color.WHITE);;
        skins.setBorder(skinsBorder);
        if (panel.ramka.getWidth() < 1500){
            skins.setPreferredSize(new Dimension(300,400));
            System.out.println(panel.ramka.getWidth());
        }else {
            skins.setPreferredSize(new Dimension(800, 400));
        }
        shipSkins.setLayout(new GridLayout(2, 2));
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
        buttonsAndScore.add(returnToMenu, BorderLayout.LINE_START);
        returnToMenu.setBackground(new Color(204, 30, 41));
        nextLevelButton.setText("Go level " + LEVEL);
        nextLevelButton.setToolTipText("GO AHEAD!");
        buttonsAndScore.add(nextLevelButton, BorderLayout.LINE_END);
        buttonsAndScore.setBackground(Color.black);
        buttonsAndScore.setPreferredSize(new Dimension(800, 50));

        returnToMenu.setVisible(true);
        returnToMenu.setBounds(20, 700, 200, 40);

        nextLevelButton.setBounds(550, 700, 200, 40);
        nextLevelButton.setBackground(new Color(204, 30, 41));

        tabbedPane.setVisible(true);

        turnOnOffShopButtons(true);
        setButtons(true);

    }


    public int calcualteStats(int enemiesnumber, int bulletcounter) {

        if (bulletcounter == 0) {
            score = (enemiesnumber * 100);
        } else {
            score = (enemiesnumber * 100) / bulletcounter / 10;
        }

        return score;
    }

    public void paintTalentTree(Graphics2D g2d, Panel mainPanel) {


        if (firstLoad) {
            CheckIfTalentCanBeActive();
            tabbedPane.setVisible(true);
            turnOnOffShopButtons(true);
            AddPanel(panel.fullScreenMode, panel.resizeMode);
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

        talents.setVisible(tf);
        centerInfoPanel.setVisible(tf);
        skins.setVisible(tf);
        enemySkins.setVisible(tf);
        buttonsAndScore.setVisible(tf);
    }

    public void removePanels(){
        panel.remove(tabbedPane);
        panel.remove(talents);
        panel.remove(centerInfoPanel);
        panel.remove(skins);
        panel.remove(enemySkins);
        panel.remove(buttonsAndScore);

        if (allshots != null) {
            centerInfoPanel.remove(allshots);
            centerInfoPanel.remove(allShotsCount);
            centerInfoPanel.remove(enemiesKilled);
            centerInfoPanel.remove(enemiesKilledCount);
            centerInfoPanel.remove(bulletsMissed);
            centerInfoPanel.remove(bulletsMissedCount);
            centerInfoPanel.remove(ratioLabel);
            centerInfoPanel.remove(ratioStringLabel);
            centerInfoPanel.remove(scorePoints);
            centerInfoPanel.remove(valueOfScore);
            centerInfoPanel.remove(coinsmoney);
            centerInfoPanel.remove(coinsmoneyImage);
        }
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

        //TODO dokończyć aktualizacje napisów labelów z pkt

        if (e.getSource() == spaceShipSkin_1) {
            if (shipSkin1Points < 15 && panel.coinsMoney > 0) {
                shipSkin1Points++;
                shipSkin1Label.setText("points: " + shipSkin1Points + "/15");
                panel.coinsMoney--;
                coinsmoney.setText(String.valueOf(panel.coinsMoney));
            }


            if (shipSkin1Points == 15 && !cartoonShipSkin1bool){
                spaceShipSkin_1.setBackground(new Color(0, 154, 0));
                cartoonShipSkin.setBackground(Color.black);
                hero.heroIcon = new ImageIcon("src/ICONS/spaceShipSkin1.png");
                spaceShipSkin1bool = true;
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
            updateByTalents();

            obstacleActive = true;
            //TODO automatyczny zapis
            tabbedPane.setVisible(false);
            talents.setVisible(false);
            skins.setVisible(false);
            centerInfoPanel.setVisible(false);


            setButtons(false);
            turnOnOffShopButtons(false);
            buttonsAndScore.setVisible(false);
            removePanels();

            obstacle.obstacleActive = true;
            panel.gameBonus = new RandomGameBonus();
            panel.firstaid = new Help(panel.ramka.getWidth());
            hero.x = panel.ramka.getWidth()/2-50;
            panel.setLayout(new FlowLayout());
            Panel.State = Panel.STATE.GAME;
        }

        if (e.getSource() == returnToMenu) {
            updateByTalents();
            System.out.println("return to Main menu");
            setButtons(false);
            turnOnOffShopButtons(false);
            tabbedPane.setVisible(false);
            talents.setVisible(false);
            skins.setVisible(false);
            centerInfoPanel.setVisible(false);
            removePanels();
//
            panel.makeButtonsMenuVisible(false);
            panel.setLayout(new FlowLayout());
            Panel.State = Panel.STATE.MENU;
        }

    }
}
