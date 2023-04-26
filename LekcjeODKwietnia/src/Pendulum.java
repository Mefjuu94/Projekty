import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;

class Kurwa extends JPanel implements MouseListener {
    final int width = 1000;
    final int height = 1000;
    int srodekEkranu = width/2;

    double x;
    double y;
    int ciezarek = 30;
    int promien = ciezarek/2;
    double predkoscPoruszania = 0;
    double StartX;
    double EndX ;
    double mocBujania = 0.7;


    int liczba;
    int odleglosc;


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g ;
        g2d.setStroke(new BasicStroke(3));
        g.drawLine(width/2,0, (int) (x), (int) (y ));
        g.fillOval((int) x - promien, (int) y - promien,ciezarek,ciezarek);

    }

    public double OblOdl(){

        if (odleglosc !=srodekEkranu) {
            if (liczba != odleglosc) {
                if (x < srodekEkranu && predkoscPoruszania > 0) {
                    y += 1;
                } else if (x < srodekEkranu && predkoscPoruszania < 0) {
                    y -= 1;
                } else if (x > srodekEkranu && predkoscPoruszania > 0 ) {
                    y -= 1;
                } else if (x > srodekEkranu && predkoscPoruszania < 0 ) {  //jak leci w lewo to ma też na dół
                    y+= 1;
                }
            }
        }
        return y;
    }

    public void ruszaj(){
        addMouseListener(this);

        x += predkoscPoruszania;
        if (x >= srodekEkranu +2 && EndX - 2 <= srodekEkranu ||
                x <= srodekEkranu + 2 && EndX + 2 >= srodekEkranu){
            x+=0;
            y+=0;
            if (EndX - 1 <= srodekEkranu && StartX + 1 >= srodekEkranu){
                predkoscPoruszania = 0;
            }
        }
        liczba = (int) Math.sqrt(Math.pow((500 - x), 2) + Math.pow((0 - y), 2)); // odl euklidesowa
        OblOdl();
        liczba = (int) Math.sqrt(Math.pow((500 - x), 2) + Math.pow((0 - y), 2)); // odl euklidesowa
        OblOdl(); // powtórzone dla ładniejszej korekcji

            //odl euklidesowa zawsze taka sama


        // odbij
        double helpVar = srodekEkranu - StartX; // zmienna pomocnicza

        if (x >= EndX){
            predkoscPoruszania *= -1;
            // zmniejsz bujanie
            System.out.println(helpVar);
            StartX = srodekEkranu - (helpVar* mocBujania);
            System.out.println(StartX + " Startx");
        } else if (x <= StartX) {
            predkoscPoruszania *= -1; // EndX automatycznie zostanie obl w nastepnym przejsciu
            EndX = srodekEkranu + (helpVar* mocBujania);
            System.out.println(EndX + " endX");
        }




        repaint();


    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        StartX = x;


        liczba = (int) Math.sqrt(Math.pow(((width/2) - x), 2) + Math.pow((0 - y), 2)); // odl euklidesowa
        odleglosc = liczba;
        if (x > srodekEkranu){
            predkoscPoruszania = -2;
            EndX = x;
            StartX = srodekEkranu - (x - srodekEkranu);
        }else {
            predkoscPoruszania = 2;
            EndX = srodekEkranu + (srodekEkranu - StartX);
        }
        ruszaj();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

public class Pendulum {
    public static void main(String[] args) throws InterruptedException {

        JFrame ramka = new JFrame();
        Kurwa Panel = new Kurwa();

        ramka.add(Panel);
        ramka.setVisible(true);
        ramka.pack();
        ramka.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        while (true){
            Panel.ruszaj();
            Thread.sleep(10);
        }

    }
}
