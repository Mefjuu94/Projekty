import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class TalentPoints implements ActionListener {

    Hero hero;

    ImageIcon healthIcon = new ImageIcon("src/ICONS/heart.png");
    JButton additionalHealth = new JButton(healthIcon);
    boolean addHealth = false;
    int healthPoints = 0;

    ImageIcon shoot3x = new ImageIcon("src/ICONS/shoot3x.png");
    JButton trippleShoot = new JButton(shoot3x);
    boolean tripleShoots = false;
    int trippleShootsPoints = 0;

    ImageIcon biggerShootIcon = new ImageIcon("src/ICONS/BigSHoot.png");
    JButton bigShoot = new JButton(biggerShootIcon);
    boolean bigShootEnable = false;
    int bigShootsPoints = 0;

    int LEVEL = 6; // default 1
    public int score = 0;

    JButton returnToMenu = new JButton("<<-return to menu");
    JButton goToShop = new JButton("*goToShop*");

    boolean firstLoad = true;
    JButton nextLevelButton = new JButton();

    Panel panel;
    Obstacle obstacle;
    boolean obstacleActive;

    ImageIcon antiReflectBulletIcon = new ImageIcon("src/ICONS/reflect.png");
    JButton antiReflectButton = new JButton(antiReflectBulletIcon);
    boolean antiReflectBullet = false;
    int antiReflectPoints = 0;

    JPanel skins = new JPanel();
    JPanel enemySkins = new JPanel();
    JPanel shipSkins = new JPanel();

    ImageIcon chickenEnemySkin1 = new ImageIcon("src/ICONS/chickenEnemySkin1Icon.png");
    JButton chickenEnemySkin_1 = new JButton(chickenEnemySkin1);
    int eSkin1Points = 0;
    JLabel eSkin1Label = new JLabel();

    ImageIcon chickenEnemySkin2 = new ImageIcon("src/ICONS/chickenEnemySkin2Icon.png");
    JButton chickenEnemySkin_2 = new JButton(chickenEnemySkin2);
    int eSkin2Points = 0;
    JLabel eSkin2Label = new JLabel();

    ImageIcon chickenEnemySkin3 = new ImageIcon("src/ICONS/chickenEnemySkin3Icon.png");
    JButton chickenEnemySkin_3 = new JButton(chickenEnemySkin3);
    int eSkin3Points = 0;
    JLabel eSkin3Label = new JLabel();

    ImageIcon spaceShipSkin1 = new ImageIcon("src/ICONS/spaceShipSkinIcoon1.png");
    JButton spaceShipSkin_1 = new JButton(spaceShipSkin1);
    int shipSkin1Points = 0;
    JLabel shipSkin1Label = new JLabel();

    ImageIcon cartoonShipSkin1 = new ImageIcon("src/ICONS/cartoonShipIcon.png");
    JButton cartoonShipSkin = new JButton(cartoonShipSkin1);
    int shipSkin2Points = 0;
    JLabel shipSkin2Label = new JLabel();


    TalentPoints(Panel panel, boolean obstacleActove, Obstacle obstacle) {

        this.panel = panel;
        this.obstacle = obstacle;
        this.obstacleActive = obstacleActove;

        additionalHealth.addActionListener(this);
        trippleShoot.addActionListener(this);
        returnToMenu.addActionListener(this);
        nextLevelButton.addActionListener(this);
        bigShoot.addActionListener(this);
        antiReflectButton.addActionListener(this);

        goToShop.addActionListener(this);

        chickenEnemySkin_1.addActionListener(this);
        chickenEnemySkin_2.addActionListener(this);
        chickenEnemySkin_3.addActionListener(this);
        spaceShipSkin_1.addActionListener(this);
        cartoonShipSkin.addActionListener(this);


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

    public void paintTalentTree(Graphics2D g2d, JPanel panel, Panel mainPanel) {

        if (Panel.State == Panel.STATE.TalentPoints) {
            g2d.setColor(Color.white);
            Font statsFont = new Font("Arial", Font.BOLD, 22);
            g2d.setFont(statsFont);

            Font levelFont = new Font("Arial", Font.BOLD, 18);


            additionalHealth.setBounds(60, 20, 80, 80);
            additionalHealth.setBackground(Color.black);
            panel.add(additionalHealth);
            g2d.drawString(" health: " + healthPoints + " / 5", 3, 120);


            trippleShoot.setBounds(60, 150, 80, 80);
            trippleShoot.setBackground(Color.black);
            panel.add(trippleShoot);
            g2d.drawString("Tripple shoot:" + trippleShootsPoints + " / 10", 5, 250);


            bigShoot.setBounds(60, 280, 80, 80);
            bigShoot.setBackground(Color.black);
            panel.add(bigShoot);
            g2d.drawString("BIG shoot:" + bigShootsPoints + " / 1", 5, 390);

            antiReflectButton.setBounds(60, 410, 80, 80);
            antiReflectButton.setBackground(Color.black);
            panel.add(antiReflectButton);
            g2d.drawString("anti reflection :" + antiReflectPoints + " / 1", 5, 520);


            goToShop.setVisible(true);
            goToShop.setBounds(20, 600, 200, 40);
            goToShop.setBackground(new Color(204, 30, 41));
            goToShop.setFont(levelFont);
            panel.add(goToShop);


            returnToMenu.setVisible(true);
            returnToMenu.setBounds(20, 700, 200, 40);
            returnToMenu.setBackground(new Color(204, 30, 41));
            returnToMenu.setFont(levelFont);
            panel.add(returnToMenu);


            nextLevelButton.setToolTipText("GO AHEAD!");
            nextLevelButton.setBounds(550, 700, 200, 40);
            nextLevelButton.setBackground(new Color(204, 30, 41));
            nextLevelButton.setFont(levelFont);
            nextLevelButton.setText("Next Level: " + LEVEL);
            panel.add(nextLevelButton);

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

            enemySkins.add(chickenEnemySkin_1);
            enemySkins.add(eSkin1Label);
            enemySkins.add(chickenEnemySkin_2);
            enemySkins.add(eSkin2Label);
            enemySkins.add(chickenEnemySkin_3);
            enemySkins.add(eSkin3Label);
            shipSkins.add(spaceShipSkin_1);
            shipSkins.add(shipSkin1Label);
            shipSkins.add(cartoonShipSkin);
            shipSkins.add(shipSkin2Label);
            turnOnOffShopButtons(false);

            setButtons(true);
            if (firstLoad) {
                CheckIfTalentCanBeActive();
                firstLoad = false;
            }
        } else if (Panel.State == Panel.STATE.SHOP) {
            turnOnOffShopButtons(true);

        }


    }

    public void setButtons(boolean tf) {

        additionalHealth.setVisible(tf);
        trippleShoot.setVisible(tf);
        nextLevelButton.setVisible(tf);
        bigShoot.setVisible(tf);
        returnToMenu.setVisible(tf);
        antiReflectButton.setVisible(tf);
        goToShop.setVisible(tf);

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

        chickenEnemySkin_1.setVisible(tf);
        chickenEnemySkin_1.setBackground(Color.black);
        chickenEnemySkin_1.setBounds(60, 100, 60, 60);
        eSkin1Label.setVisible(tf);
        eSkin1Label.setBackground(Color.black);
        eSkin1Label.setBounds(150, 100, 60, 60);
        eSkin1Label.setText("points: " + eSkin1Points);

        chickenEnemySkin_2.setVisible(tf);
        chickenEnemySkin_2.setBackground(Color.black);
        chickenEnemySkin_2.setBounds(60, 200, 60, 60);
        eSkin2Label.setVisible(tf);
        eSkin2Label.setBackground(Color.black);
        eSkin2Label.setBounds(150, 200, 60, 60);
        eSkin2Label.setText("points: " + eSkin2Points);

        chickenEnemySkin_3.setVisible(tf);
        chickenEnemySkin_3.setBackground(Color.black);
        chickenEnemySkin_3.setBounds(60, 300, 60, 60);
        eSkin3Label.setVisible(tf);
        eSkin3Label.setBackground(Color.black);
        eSkin3Label.setBounds(150, 300, 60, 60);
        eSkin3Label.setText("points: " + eSkin3Points);

        spaceShipSkin_1.setVisible(tf);
        spaceShipSkin_1.setBackground(Color.black);
        chickenEnemySkin_1.setBounds(60, 400, 60, 60);
        shipSkin1Label.setVisible(tf);
        shipSkin1Label.setBackground(Color.black);
        shipSkin1Label.setBounds(150, 400, 60, 60);
        shipSkin1Label.setText("points: " + shipSkin1Points + "/15");

        cartoonShipSkin.setVisible(tf);
        cartoonShipSkin.setBackground(Color.black);
        cartoonShipSkin.setBounds(60, 500, 60, 60);
        shipSkin2Label.setVisible(tf);
        shipSkin2Label.setBackground(Color.black);
        shipSkin2Label.setBounds(150, 500, 60, 60);
        shipSkin2Label.setText("points: " + shipSkin2Points + "/15");


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == additionalHealth && score > 0) {
            if (healthPoints < 5) {
                healthPoints += 1;
                panel.health = 5 + healthPoints;
                score--;
            }
            updateByTalents();
        }

        if (e.getSource() == trippleShoot && trippleShoot.isEnabled()) {
            if (trippleShootsPoints < 10 && score > 0) {
                trippleShootsPoints += 1;
                score--;
            }
            updateByTalents();
        }

        if (e.getSource() == bigShoot) { //TODO OGARNĄC TEN BIGsHOT
            if (bigShootsPoints < 1 && score > 0) {
                bigShootsPoints++;
                score--;
            }
            updateByTalents();
        }

        if (e.getSource() == antiReflectButton) {
            if (antiReflectPoints < 5 && score > 0) {
                antiReflectPoints++;
                score--;
            }
            updateByTalents();
        }

        /// skiny doi kupienia //TODO UZUPEŁNIĆ

        if (e.getSource() == chickenEnemySkin_1) {
            if (eSkin1Points < 5 && panel.coinsMoney > 0) {
                eSkin1Points++;
                eSkin1Label.setText("points: " + eSkin1Points);
                panel.coinsMoney--;
            }

        }

        if (e.getSource() == chickenEnemySkin_2) {
            if (eSkin2Points < 5 && panel.coinsMoney > 0) {
                eSkin2Points++;
                eSkin2Label.setText("points: " + eSkin2Points);
                panel.coinsMoney--;
            }

        }

        if (e.getSource() == chickenEnemySkin_3) {
            if (eSkin3Points < 5 && panel.coinsMoney > 0) {
                eSkin3Points++;
                System.out.println(eSkin3Points);
                eSkin3Label.setText("points: " + eSkin3Points);
                panel.coinsMoney--;
            }

        }

        if (e.getSource() == spaceShipSkin_1) {
            if (shipSkin1Points < 15 && panel.coinsMoney > 0) {
                shipSkin1Points++;
                shipSkin1Label.setText("points: " + shipSkin1Points + "/15");
                panel.coinsMoney--;
            }

        }

        if (e.getSource() == cartoonShipSkin) {
            if (shipSkin2Points < 15 && panel.coinsMoney > 0) {
                shipSkin2Points++;
                shipSkin2Label.setText("points: " + shipSkin2Points + "/15");
                panel.coinsMoney--;
            }

        }


        if (e.getSource() == goToShop) {
            if (Panel.State == Panel.STATE.TalentPoints) {

                Panel.State = Panel.STATE.SHOP;

                setButtons(false);

                goToShop.setVisible(true);
                goToShop.setText("back to Talent Points");

                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(skins);

                skins.setLayout(new GridLayout());

                skins.setBackground(Color.black);

                skins.add(enemySkins);
                skins.add(shipSkins);


                Border borderEnemy = new TitledBorder("Enemy Skins");
                Border borderShip = new TitledBorder("Ship Skins");


                enemySkins.setLayout(new GridLayout(3,2));
                enemySkins.setBorder(borderEnemy);
                enemySkins.setBounds(0, 100, 200, 400);
                enemySkins.setBackground(Color.GRAY);


                shipSkins.setLayout(new BoxLayout(this.shipSkins, BoxLayout.Y_AXIS));
                shipSkins.setBorder(borderShip);
                shipSkins.setBounds(230, 100, 200, 400);
                shipSkins.setBackground(Color.GRAY);


                turnOnOffShopButtons(true);

            } else {
                Panel.State = Panel.STATE.TalentPoints;
                panel.remove(skins);
                goToShop.setText("go to Shop");
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
            Panel.State = Panel.STATE.GAME;
        }

        if (e.getSource() == returnToMenu) {
            updateByTalents();
            System.out.println("return to Main menu");
            setButtons(false);
            Panel.State = Panel.STATE.MENU;
        }

    }
}
