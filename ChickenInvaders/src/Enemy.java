import javax.swing.*;
import java.awt.*;

public class Enemy {

    Enemy enemy;
    Enemy[] enemies;

    int enemySize = 48; //px
    int enemyMiddle = 24;

    int enemySpeed = 1;

    ImageIcon enemyIcon = new ImageIcon("src/ICONS/enemy48.png");
    int x, y;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics2D g2d, JPanel panel) {

        this.enemyIcon.paintIcon(panel, g2d, this.x, this.y);

    }

    public void update(TalentPoints talentPoints){
        if (talentPoints.LEVEL > 5){
            enemySpeed = 2;
        }
        //y = y + enemySpeed;

    }

    }
