import javax.swing.*;
import java.awt.*;

public class InfoPanel {

    ImageIcon healthIcon = new ImageIcon("src/ICONS/heart25.png");
    ImageIcon bulletIcon = new ImageIcon("src/ICONS/bullet.png");
    ImageIcon enemyIcon = new ImageIcon("src/ICONS/enemy30.png");
    ImageIcon firstAidKitIcon = new ImageIcon("src/ICONS/firstAid30.png");
    ImageIcon coinIcon = new ImageIcon("src/ICONS/smallCoin-unscreen.gif");


    public void paintInfopanel(Graphics2D g2d, int enemiesnumber, int health,int bulletcounter, int healthkit,int coinsQantity , Panel panel){

        g2d.setColor(Color.gray);
        g2d.fillRect(0,0,panel.getWidth(),70);

        g2d.setColor(Color.black);
        Font statsFont = new Font("Arial",Font.BOLD,18);
        g2d.setFont(statsFont);

        healthIcon.paintIcon(panel,g2d,8,5);
        g2d.drawString("   " + health,0,50);

        firstAidKitIcon.paintIcon(panel,g2d,50,0);
        g2d.drawString("   " + healthkit ,45,50);

        enemyIcon.paintIcon(panel,g2d,panel.getWidth()/2,5);
        g2d.drawString("  " + enemiesnumber ,panel.getWidth()/2,50);

        coinIcon.paintIcon(panel,g2d,panel.getWidth()/2+100,0);
        g2d.drawString("  " + coinsQantity ,panel.getWidth()/2+105,50);

        bulletIcon.paintIcon(panel,g2d,panel.getWidth()-60,0);
        g2d.drawString("    " + bulletcounter,panel.getWidth()-100,50);



    }

    public InfoPanel(){

    }

}
