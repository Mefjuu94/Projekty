import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Panel extends JPanel implements MouseMotionListener, MouseListener {

    int WIDTH = 800;
    int Height = 500;

    double slonceX = 400;
    double slonceY = 0;

    int radius = 20;    // Promień koła
    double x;
    double y;           // Współrzędne koła


    double nowyX;
    double a, b, c;  // a = wys w Y, b = x, c = przeciwprostokątna

    double wychyleniePlus, wychylenieMinus, wyhylenie, startX, PrzyspKAT;


    boolean liczXY = false;


    boolean anime = false;
    boolean drage = false;
    boolean liczDrag = false;

    double angle;   // Kąt (w radianach
    double speed; // Prędkość ruchu koła (zmiana kąta w jednostce czasu)


    Panel() {
        addMouseMotionListener(this);
        addMouseListener(this);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 800, 800);


        if (!anime) {
            g2d.setColor(Color.white);
            g2d.drawLine((int) slonceX, (int) slonceY, (int) (x + radius), (int) (y + radius));

            g2d.setColor(Color.blue);
            g.fillOval((int) x, (int) y, radius * 2, radius * 2);
        } else {

            g2d.setColor(Color.white);
            g2d.drawLine((int) slonceX, (int) slonceY, (int) (x + radius), (int) (y + radius));

            g2d.setColor(Color.blue);
            g.fillOval((int) x, (int) y, radius * 2, radius * 2);

            math();
        }

        if (drage) {
            liczDrag();
        }


        repaint();
    }

    public void metoda() {

    }

    public void math() {

        if (liczXY) {
            liczXY();
            liczXY = false;
        }
        ///kat
        anime = true;
        // Aktualizacja kąta, aby koło poruszało się po okręgu
        angle += speed;
        // Obliczenie nowych współrzędnych koła na podstawie kąta

        // pierwsza współrzędna to punkt zerowy ( zaczepienie)
        // druga to okrąg po którym się porusza!!


        x = (int) ((slonceX - radius) + c * Math.sin(angle));
        y = (int) c * Math.cos(angle);

        // po przeciągnięciu liczy metodą "liczDrag" przeciwprostokątną -> która jest okręcgiem po którym ma
        //sie poruszać (druga zmienna) * cos z kąta


        //tutaj ma być zmiena kierunku - odbicie
        odbicie();

        repaint();

    }

    public void liczXY() {

        if (x > 380) {          // leć w prawo albo lewo w zależności po której stronie jest
            speed = -0.001;
        } else {
            speed = 0.001;
        }
    }

    public void liczDrag() {

        if (nowyX > 380) {
            a = y;
            b = x - 380;
            c = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
            angle = b / c;
        } else {
            a = y;
            b = 380 - x;
            c = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
            angle = b / c;
        }

        if (x > 380) {
            wyhylenie = b * (-1) + 20;
        } else {
            wyhylenie = b - 20;
        }


        if (angle > 0){
            wychyleniePlus = angle;
            wychylenieMinus = angle * (-1);
        }else {
            wychyleniePlus = angle * (-1);
            wychylenieMinus = angle;
        }


        angle *= -1; // zmień znak kątą bo zaczyna od złej strony!!!

        System.out.println(Math.toDegrees(angle));
    }

    public void odbicie() {
        if (nowyX > 380) {
            b = x - 380;
        } else {
            b = 380 - x;
        }


        if (x > 380){
            if (angle > wychyleniePlus){
                speed *= -1;
                wychylenieMinus *= -0.95;
            }
        }

        if (x < 380){
            if (angle < wychylenieMinus){
                speed *= -1;
                wychyleniePlus *= -0.95;
            }
        }



    }


    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, Height);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        anime = false;
        System.out.println("dragged");

        x = e.getX() - radius;
        y = e.getY() - radius;
        if (x > 380) {
            startX = x;
        }
        liczDrag();

        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        drage = false;
        anime = true;
        liczXY = true;
        repaint();


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
