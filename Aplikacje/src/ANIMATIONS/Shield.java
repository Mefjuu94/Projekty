package ANIMATIONS;

import javax.swing.*;
import java.awt.*;

public class Shield extends JPanel{
    public static int los;

    public static int los(){
        los = (int) (Math.random() * 7);
        return los;
    }

    public static int kol;
    public int ilosc = Integer.parseInt(JOptionPane.showInputDialog("Podaj ilość okręgów tarczy", 0));




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //tarcza

        int wielkosc = getHeight();

        if (getHeight() < getWidth()){
            wielkosc = getHeight() / 2;
        }
        else if (getHeight() > getWidth()) {
            wielkosc = getWidth()/2;
        }

        int pomoc = wielkosc/ilosc;

        int wielkoscx = getWidth()/2;
        int wielkoscy = getHeight()/2;

        int x = getWidth()/2 - (wielkosc / 2);
        int y = getHeight()/2 - (wielkosc / 2);

        g.drawRect(x,y,wielkosc,wielkosc);


        for (int i = 0; i < ilosc ; i++) {

            g.setColor(new Color((int)(Math.random() * 255),(int)(Math.random() * 255),(int)(Math.random() * 255)));


            if (getWidth()>getHeight()) {
                g.fillOval(x, y, wielkoscy, wielkoscy);
            } else if (getWidth() < getHeight()) {
                g.fillOval(x, y , wielkoscx, wielkoscx);
            } else if (getWidth()==getHeight()) {
                g.fillOval(x + (wielkosc / 4), y + (wielkosc / 4) , wielkoscx, wielkoscx);

            }

            wielkoscx = wielkoscx - pomoc;
            wielkoscy = wielkoscy - pomoc;
            x = x + pomoc/2;
            y = y + pomoc/2;

        }

    }
}
