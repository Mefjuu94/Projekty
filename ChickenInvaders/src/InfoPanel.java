import javax.swing.*;
import java.awt.*;

public class InfoPanel {

    ImageIcon healthIcon = new ImageIcon("src/ICONS/heart25.png");
    ImageIcon bulletIcon = new ImageIcon("src/ICONS/bullet.png");
    ImageIcon enemyIcon = new ImageIcon("src/ICONS/enemy30.png");
    ImageIcon firstAidKitIcon = new ImageIcon("src/ICONS/firstAid30.png");


    public void paintInfopanel(Graphics2D g2d, int enemiesnumber, int health,int bulletcounter, int healthkit, JPanel panel){

        g2d.setColor(Color.gray);
        g2d.fillRect(0,0,800,70);

        g2d.setColor(Color.black);
        Font statsFont = new Font("Arial",Font.BOLD,18);
        g2d.setFont(statsFont);

        healthIcon.paintIcon(panel,g2d,8,5);
        g2d.drawString("   " + health,0,50);

        firstAidKitIcon.paintIcon(panel,g2d,50,0);
        g2d.drawString("   " + healthkit ,45,50);

        enemyIcon.paintIcon(panel,g2d,400,5);
        g2d.drawString("  " + enemiesnumber ,400,50);

        bulletIcon.paintIcon(panel,g2d,740,0);
        g2d.drawString("    " + bulletcounter,700,50);



    }

    public InfoPanel(){

    }

}
