import javax.swing.*;
import java.awt.*;

public class Enemy {

    Enemy enemy;
    Enemy[] enemies;

    int enemySize = 48; //px
    int enemyMiddle = 24;

    ImageIcon enemyIcon = new ImageIcon("C:\\Users\\mateu\\OneDrive\\Pulpit\\Projekty\\SpaceInvaders\\src\\enemy.png");
    int x, y;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;


    }

    public void paint(Graphics2D g2d, JPanel panel) {

        this.enemyIcon.paintIcon(panel, g2d, this.x, this.y);

    }

    }
