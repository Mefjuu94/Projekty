import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BallColor {

    Color[] fourColors = {Color.RED,Color.GREEN,Color.BLUE,Color.BLACK};
    List<Color> rowColors = new ArrayList<>();
    List<Color> grayC = new ArrayList<>();
    int x,y;
    int rowCounter = 0;



    ArrayList<Color> colors = new ArrayList<>();
    {
        colors.addAll(Arrays.asList(fourColors));
    }
    ImageIcon upArrow = new ImageIcon("src/up-arrow.png");

    public BallColor(){
    }

    public void PaintNewBall(Color c,Graphics2D g2d){




    }


    public void paint(Graphics2D g2d,JPanel panel,int clickCounter,Color c,List<Color> randomcolors,List<Color> rowColors, int i, int j){

        g2d.setColor(c);
        challenge(g2d,c,i,j);
        setActiveArrow(panel,g2d,clickCounter);
        paintBall(c,g2d,clickCounter);
    }

    public void paintBall(Color c,Graphics2D g2d,int clickCounter){
        y = 550;
        x = 220 - 60;
        g2d.setColor(c);
        if (clickCounter > 0) {
            x = x + (60 * clickCounter);

            if (x >= 460) {
                rowCounter++;
                x = x - (240 * rowCounter);
                y = y - (80 * rowCounter);
            }

            g2d.fillOval(x, y, 40, 40);
        }

    }


    public void challenge(Graphics2D g2d, Color c, int j, int i){ //sloty do wypełnienia
        y = 550;
        x = 220;
        g2d.setColor(Color.black);
        g2d.drawString("sloty do wypełnienia/ zgadnięcia", 200,250);

        for ( j = 0; j < 5; j++) {

            for ( i = 0; i < fourColors.length; i++) {
                g2d.setColor(Color.DARK_GRAY);
                if (i ==1 && j == 3){
                    g2d.setColor(Color.PINK);
                }

                    g2d.fillOval(x, y, 40, 40);
                    x += 60;

            }
            x = 220;
            y -= 80;
        }



    }



    public void setActiveArrow(JPanel panel,Graphics2D g2d, int clickCounter) {
        int xActive = 225;
        int yActive = 590;
        int licznik = 0;


        if (clickCounter > 0) {
            xActive = xActive + (60 * clickCounter);
            licznik = clickCounter / 4;
            if (licznik == 5) {
                licznik = 0;
            }
        }
        System.out.println(licznik);


        if (xActive >= 460) {
            xActive = xActive - (240 * licznik);
            yActive = yActive - (80 * licznik);
        }

        System.out.println(xActive);

        upArrow.paintIcon(panel, g2d, xActive, yActive);


    }



}
