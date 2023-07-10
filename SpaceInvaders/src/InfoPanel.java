import javax.swing.*;
import java.awt.*;

public class InfoPanel {

    ImageIcon healthIcon = new ImageIcon("src/ICONS/heart25.png");
    ImageIcon bulletIcon = new ImageIcon("src/ICONS/bullet.png");
    ImageIcon enemyIcon = new ImageIcon("src/ICONS/enemyInfo.png");


    public void paintInfopanel(Graphics2D g2d, int enemiesnumber, int health,int bulletcounter, JPanel panel){

        g2d.setColor(Color.gray);
        g2d.fillRect(0,0,800,70);

        g2d.setColor(Color.black);
        Font statsFont = new Font("Arial",Font.BOLD,18);
        g2d.setFont(statsFont);

        healthIcon.paintIcon(panel,g2d,8,5);
        g2d.drawString("   " + health,0,50);

        enemyIcon.paintIcon(panel,g2d,400,5);
        g2d.drawString("  " + enemiesnumber ,400,50);

        bulletIcon.paintIcon(panel,g2d,760,0);
        g2d.drawString("    " + bulletcounter,760,50);



    }

    public InfoPanel(){

    }

}
