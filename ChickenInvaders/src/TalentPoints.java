import javax.swing.*;
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

    int LEVEL = 4;
    public int score = 0;

    JButton returnToMenu = new JButton("<<-return to menu");

    boolean firstLoad = true;
    JButton nextLevelButton = new JButton();

    Panel panel;
    Obstacle obstacle;
    boolean obstacleActive;

    ImageIcon antiReflectBulletIcon = new ImageIcon("src/ICONS/reflect.png");
    JButton antiReflectButton = new JButton(antiReflectBulletIcon);
    boolean antiReflectBullet = true;
    int antiReflectPoints = 1;


    TalentPoints(Panel panel, boolean obstacleActove, Obstacle obstacle) {

        this.panel = panel;
        this.obstacle = obstacle;
        this.obstacleActive = obstacleActove;

        additionalHealth.addActionListener(this);
        trippleShoot.addActionListener(this);
        returnToMenu.addActionListener(this);
        nextLevelButton.addActionListener(this);
        bigShoot.addActionListener(this);

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

        g2d.setColor(Color.white);
        Font statsFont = new Font("Arial", Font.BOLD, 22);
        g2d.setFont(statsFont);

        Font levelFont = new Font("Arial", Font.BOLD, 18);


        additionalHealth.setBounds(350, 20, 80, 80);
        additionalHealth.setBackground(Color.black);
        panel.add(additionalHealth);
        g2d.drawString(" health points: " + healthPoints + " / 5", 300, 120);


        trippleShoot.setBounds(350, 150, 80, 80);
        trippleShoot.setBackground(Color.black);
        panel.add(trippleShoot);
        g2d.drawString("Tripple shoot points :" + trippleShootsPoints + " / 10", 250, 250);


        bigShoot.setBounds(350, 280, 80, 80);
        bigShoot.setBackground(Color.black);
        panel.add(bigShoot);
        g2d.drawString("BIG shoot points :" + bigShootsPoints + " / 1", 250, 390);

        antiReflectButton.setBounds(350, 410, 80, 80);
        antiReflectButton.setBackground(Color.black);
        panel.add(antiReflectButton);
        g2d.drawString("anti reflection :" + antiReflectPoints + " / 1", 250, 520);


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


        setButtons(true);
        if (firstLoad) {
            CheckIfTalentCanBeActive();
            firstLoad = false;
        }
    }

    public void setButtons(boolean tf) {

        additionalHealth.setVisible(tf);
        trippleShoot.setVisible(tf);
        nextLevelButton.setVisible(tf);
        bigShoot.setVisible(tf);
        returnToMenu.setVisible(tf);
        antiReflectButton.setVisible(tf);

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

        if (e.getSource() == bigShoot) {
            if (bigShootsPoints < 1 && score > 0) {
                bigShootsPoints++;
                score--;
            }
            updateByTalents();
        }

        if (e.getSource() == antiReflectButton) {
            if (antiReflectPoints < 1 && score > 0) {
                antiReflectPoints++;
                score--;
            }
            updateByTalents();
        }


        if (e.getSource() == nextLevelButton) {
            System.out.println("next LEVEL!");
            updateByTalents();
            System.out.println(tripleShoots);
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
