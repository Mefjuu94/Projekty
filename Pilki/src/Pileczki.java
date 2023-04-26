import javax.swing.*;
import java.awt.*;
import java.util.Random;

class Panel extends JPanel{



    Random los = new Random();


    int[] pileczki = new int[2];
    int wielkosc = los.nextInt(150);
    int x = los.nextInt(getWidth()) - wielkosc;
    int y = los.nextInt(getHeight()) - wielkosc;

    int SzybX = los.nextInt(10);
    int SzybY = los.nextInt(10);
    Color color = new Color(los.nextInt(256),los.nextInt(256),los.nextInt(256));

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < pileczki.length ; i++) {
            g.setColor(color);
            g.fillOval(x,y,wielkosc,wielkosc);
        }

    }



    public Dimension getPreferredSize(){
        return new Dimension(500,500);
    }

}





public class Pileczki {
    public static void main(String[] args) {

        JFrame ramka = new JFrame("Piłeczki");
        Panel panel = new Panel();

        ramka.add(panel);
        ramka.setVisible(true);
        ramka.pack();
        ramka.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }
}
