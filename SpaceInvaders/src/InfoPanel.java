import javax.swing.*;
import java.awt.*;

public class InfoPanel {


    public void paintInfopanel(Graphics2D g2d, int enemiesnumber, int health,int bulletcounter){

        g2d.setColor(Color.gray);
        g2d.fillRect(0,0,800,100);

        g2d.setColor(Color.black);

        g2d.drawString("Ilość żcyia " + health,20,20);
        g2d.drawString("pozsotało " + enemiesnumber + " przeciwników",20,40);
        g2d.drawString("Wystrzelono " + bulletcounter + " pocisków",20,60);



    }

    public InfoPanel(){

    }

}
