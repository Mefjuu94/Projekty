import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class Dzialo extends JPanel implements MouseListener, MouseMotionListener {

    int Width = 1000;
    int Height = 500;

    //proca

    int prawa = Width/2 + 100;
    int lewa = Width/2 - 100;
    int procay = Height-200;

    //rysowana kulka
    boolean rysKulke = false;
    int wielkosc = 30;
    double x;
    int y;
    //dodatkowe upgrade!!
    int dodatkowe = 2;
    int[] xD = new int[dodatkowe];
    int[] yD = new int [dodatkowe];

    //cieciwa

    int ciecProcaLewa = lewa;
    int ciecProcaPrawa = prawa;
    int koniecCieciwyPr = (int) (x + wielkosc/2);
    int koniecCieciwyLw = (int) (x - wielkosc/2);
    int odlegosc;
    int srodekProcy = Width/2;
    int koniecy = procay;
    boolean rysCieciwe = true;

    int startx;
    double szybkoscPoruszaniaY;
    double szybkoscPoruszaniaX;


    //ilsoc celow
    int cele = 8;
    int targetSize = 40;

    int[] xTarget = new int[cele];
    int[] yTarget = new int[cele];
    // TRAFIONE
    int celeTrafione;
    boolean upgrade = false;
    boolean strzelaj = false;
    boolean dragging = false;

    ///kostrukltor\
    public Dzialo(){
        addMouseListener(this);
        addMouseMotionListener(this);
    }



    Random rand= new Random();

    {
        for (int i = 0; i < cele; i++) {
            xTarget[i] = rand.nextInt(Width - targetSize);
        }
        for (int i = 0; i < cele; i++) {
            yTarget[i] = rand.nextInt(procay - targetSize);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));


        g2d.drawLine(Width/2,Height-10,Width/2,Height-120);
        g2d.drawLine(Width/2,Height-120,lewa,procay);
        g2d.drawLine(Width/2,Height-120,prawa,procay);


        if (rysKulke) {
            g2d.fillOval((int) (x - wielkosc / 2), y - wielkosc / 2, wielkosc, wielkosc);
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(Color.blue);
            if (y <= procay) {
                g2d.drawLine(ciecProcaLewa, procay, ciecProcaPrawa, koniecy);
                rysCieciwe = false;
            }
            if (rysCieciwe) {
                g2d.drawLine(ciecProcaLewa, procay, (int) (x - wielkosc / 2), y);
                g2d.drawLine(ciecProcaPrawa, procay, (int) (x + wielkosc / 2), y);
//                g2d.drawLine(ciecProcaLewa, procay, (int) (x), y+84);
//                g2d.drawLine(ciecProcaPrawa, procay, (int) (x + 9), y+84);
            }
        }

        for (int i = 0; i < cele; i++) {
            g2d.setStroke(new BasicStroke(3));
            g2d.setColor(Color.gray);
            g2d.fillOval(xTarget[i],yTarget[i],targetSize,targetSize);
        }

        if (upgrade && strzelaj){
            g2d.setColor(Color.blue);
            for (int i = 0; i < 2 ; i++) {
                xD[i] = (int) x;
                yD[i] = y;
                g2d.fillOval(xD[i] - wielkosc /2,yD[i] - wielkosc/2,wielkosc,wielkosc);
            }
        }

    }


    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;

    }

    public void Trafienie(){
        //odl euklidesowa

        for (int i = 0; i < cele; i++){
            int odleglosc;
            odleglosc = (int) Math.sqrt(Math.pow(((x - (wielkosc/2)) - xTarget[i]), 2) + Math.pow((y-(wielkosc/2) - yTarget[i]), 2));
//            System.out.println(odleglosc + " kulka : " + i );
            if (odleglosc < 35) {
                System.out.println("Trafiłeś!!!!!!!!!!!!!!");
                xTarget[i] = -151150; // Jak trafiles to wyrzuc poza ekran
                yTarget[i] = 151150;
                celeTrafione +=1;
                System.out.println("cele trafione = " + celeTrafione);
            }
        }

        if (celeTrafione >= 3){
            upgrade = true;
            System.out.println(" Możesz Wystrzelić UPGRADE");
        }

    }



    public void strzal() {
        addMouseListener(this);

        y -= szybkoscPoruszaniaY;

        //oblicz kąt ( z jaką predkoscia x bedzie sie przemieszcał

        if (startx > Width/2){
            x =  x - szybkoscPoruszaniaX;
        }else {
            x = x + szybkoscPoruszaniaX;
        }

       //poruszanie się dodatkowych piłek


        for (int i = 0; i < dodatkowe; i++) {
            xD[0] += 1;
            xD[1] -= 1;
        }

        //sprawdz czy trafiłeś
        Trafienie();

        //jak wypadnie poza ekran pilka nie leci dalej
        if (x < -150 && y < -150){
            x = -200;
            y= -200;
            szybkoscPoruszaniaX = 0;
            szybkoscPoruszaniaY = 0;
        }

        repaint();
    }


    @Override
    public Dimension getPreferredSize() {

        return new Dimension(Width,Height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        rysKulke = true;
        rysCieciwe = true;
        x = e.getX();
        y = e.getY();
        dragging = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragging = false;
        x = e.getX();
        y = e.getY();

        startx = e.getX() ;

        if (x < srodekProcy) {
            odlegosc = srodekProcy - startx;
        }else {
            odlegosc = startx - srodekProcy;
        }

        szybkoscPoruszaniaY = ((y - (wielkosc/2)) - procay)/10;
        szybkoscPoruszaniaX = odlegosc /10;

//        System.out.println("x = " + x);
//        System.out.println("y = " + y);
//        System.out.println("odleglosc = " + odlegosc);
//        System.out.println("szyb poruszania Y" + szybkoscPoruszaniaY);
//        strzal();

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        x = e.getX();
        y = e.getY();

        System.out.println(x);
        System.out.println(y);


        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("MOVED!!");

    }
}

//dowaolna zmiana w kodzie


class Myframe extends JFrame implements KeyListener {

    Dzialo dzialo;
    public Myframe(Dzialo dzialo) {
        this.dzialo = dzialo;
        addKeyListener(this);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.dzialo.setUpgrade(true);
            System.out.println(" SPACJA! ");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}




public class DzialoUpgrade {
    public static void main(String[] args) throws InterruptedException {


        Dzialo dzialo = new Dzialo();
        Myframe obwod = new Myframe(dzialo);
        obwod.getContentPane().add(dzialo);

        obwod.setVisible(true);
        obwod.add(dzialo);
        obwod.pack();
        obwod.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        while (true) {
            dzialo.strzal();
            Thread.sleep(50);
        }
    }

}
