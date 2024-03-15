import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Enemy {

    Enemy enemy;
    Enemy[] enemies;

    int enemySize = 48; //px
    int enemyMiddle = 24;

    int enemySpeed = 1;
    int[] ensmiesSpeed;


    ImageIcon enemyIcon = new ImageIcon("src/ICONS/enemy48.png");

    ImageIcon skin3 = new ImageIcon("src/ICONS/chickenEnemySkin3.png");

    int x, y;

    TalentPoints talentPoints;

    public Enemy(int x, int y, TalentPoints talentPoints) {
        this.x = x;
        this.y = y;
        this.talentPoints = talentPoints;
    }


    public void paint(Graphics2D g2d, JPanel panel) {

        if (talentPoints.chickenEnemySkin3bool){
            enemyIcon = new ImageIcon("src/ICONS/chickenEnemySkin3.png");
        }
        if (talentPoints.chickenEnemySkin2bool){
            enemyIcon = new ImageIcon("src/ICONS/chickenEnemySkin2.png");
        }
        if (talentPoints.chickenEnemySkin1bool){
            enemyIcon = new ImageIcon("src/ICONS/chickenEnemySkin1.png");
        }

        this.enemyIcon.paintIcon(panel, g2d, this.x, this.y);

    }

    public void update(TalentPoints talentPoints, Panel panel) {

        ensmiesSpeed = new int[panel.enemiesNumber];

        if (talentPoints.LEVEL > 5) {
            enemySpeed = 2;
        }
        y = y + enemySpeed;

        ///dopisać ruchy w lewo i prawo

    }

    }
