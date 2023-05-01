import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class Dzialo extends JPanel implements MouseListener, MouseMotionListener {

    int Width = 1000;
    int Height = 500;

    // proca

    int prawa = Width / 2 + 100;
    int lewa = Width / 2 - 100;
    int procay = Height - 200;

    // rysowana kulka
    boolean rysKulke = false;
    int wielkosc = 30;
    double x;
    int y;
    // dodatkowe upgrade!!
    int dodatkowe = 2;
    int startXD = (int) x;
    int[] xD = new int[dodatkowe];
    int[] yD = new int[dodatkowe];

    // cieciwa

    int ciecProcaLewa = lewa;
    int ciecProcaPrawa = prawa;
    // int koniecCieciwyPr = (int) (x + wielkosc/2);
    // int koniecCieciwyLw = (int) (x - wielkosc/2);
    int odlegosc;
    int srodekProcy = Width / 2;
    int koniecy = procay;
    boolean rysCieciwe = true;

    int startx;
    double szybkoscPoruszaniaY;
    double szybkoscPoruszaniaX;

    // nasze cele
    Ball balls[];

    // TRAFIONE
    int celeTrafione;
    boolean upgrade = false;
    boolean strzelaj = false;
    boolean dragging = false;
    int count = 0; // info o upgrade
    boolean afterShoot = false;

    /// kostruktor
    public Dzialo(int ilosCeli) {
        addMouseListener(this);
        addMouseMotionListener(this);
        this.stworzCele(ilosCeli);
    }

    private void stworzCele(int ilosCeli) {
        Random rand = new Random();
        this.balls = new Ball[ilosCeli];
        for (int i = 0; i < ilosCeli; i++) {
            int ballSize = 40;
            this.balls[i] = new Ball(ballSize, rand.nextInt(Width - ballSize), rand.nextInt(procay - ballSize));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));

        g2d.drawLine(Width / 2, Height - 10, Width / 2, Height - 120);
        g2d.drawLine(Width / 2, Height - 120, lewa, procay);
        g2d.drawLine(Width / 2, Height - 120, prawa, procay);

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
                // g2d.drawLine(ciecProcaLewa, procay, (int) (x), y+84);
                // g2d.drawLine(ciecProcaPrawa, procay, (int) (x + 9), y+84);
            }
        }

        for (int i = 0; i < this.balls.length; i++) {
            this.balls[i].paint(g2d);
        }

        // jeśli spełni warunek 3 zdobytych piłek oraz naciśnięta spacja to:
        // -wystrzeli 2 niebieskie piłki z tej pierwotnej
        if (upgrade) {
            // System.out.println("wszedłem w upgrade"); //info dziala
            g2d.setColor(Color.blue);
            if (upgrade && strzelaj) {
                // System.out.println("Wszedłem w strzał - niebieskie pilki"); //info dziala
                for (int i = 0; i < 2; i++) {
                    yD[i] = y;
                    g2d.fillOval(xD[i] - wielkosc / 2, yD[i] - wielkosc / 2, wielkosc, wielkosc);
                }
                afterShoot = true;
            }
        }

    }

    // konstruktory -> do instancji do odwołania się w key listenerze
    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;

    }

    public void setStrzal(boolean strzelaj) {
        this.strzelaj = strzelaj;
    }

    public void Trafienie() {
        for (int i = 0; i < this.balls.length; i++) {
            boolean trafienie1 = this.balls[i].trafienie(wielkosc, (int) x, y);
            boolean trafienie2 = this.balls[i].trafienie(wielkosc, (int) xD[0], yD[0]);
            boolean trafienie3 = this.balls[i].trafienie(wielkosc, (int) xD[1], yD[1]);
            if (trafienie1 || trafienie2 || trafienie3) {
                this.celeTrafione++;
            }
            System.out.println("cele trafione = " + celeTrafione + " / " + this.balls.length);
        }

        // jak Trafione zostanie 1/3 WSZYSTKICH celów to odblokowuje UPGRADE
        if (celeTrafione >= (this.balls.length / 3)) {
            upgrade = true;
            if (count < 1) {
                System.out.println(" Możesz Wystrzelić UPGRADE");
                System.out.println("upgrade = " + upgrade);
                count = count + 1;
            }
        }

    }

    public void strzal() {
        addMouseListener(this);

        y -= szybkoscPoruszaniaY;

        // oblicz kąt ( z jaką predkoscia x bedzie sie przemieszcał

        if (startx > Width / 2) {
            x = x - szybkoscPoruszaniaX;
        } else {
            x = x + szybkoscPoruszaniaX;
        }

        // poruszanie się dodatkowych piłek

        if (strzelaj && !afterShoot) {
            xD[0] = (int) x;
            xD[1] = (int) x;
        }
        if (afterShoot) {
            xD[0] += 5;
            xD[1] -= 5;
        }

        // sprawdz czy trafiłeś
        Trafienie();

        // jak wypadnie poza ekran pilka nie leci dalej, lokuje sie w "x", "y" -200
        if (x < -150 && y < -150) {
            x = -200;
            y = -200;
            szybkoscPoruszaniaX = 0;
            szybkoscPoruszaniaY = 0;
        }

        repaint();

        if (celeTrafione == this.balls.length) {
            System.out.println("Gratulacje, trafiłeś wszystkie Cele!");
            // pytanie czy chce zagrać jescze raz?
            int dialogButton = JOptionPane.showConfirmDialog(null,
                    "Czy chcesz zagrać jeszcze raz?", "WARNING", JOptionPane.YES_NO_OPTION);
            // YES =0 NO = 1
            if (dialogButton == 1) {
                System.exit(0);
            } else if (dialogButton == 0) {
                // tu ma być wywołanie na nowo!!!!
                System.exit(0);
            }
        }

    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(Width, Height);
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

        strzelaj = false;
        upgrade = false;

        startx = e.getX();

        if (x < srodekProcy) {
            odlegosc = srodekProcy - startx;
        } else {
            odlegosc = startx - srodekProcy;
        }

        szybkoscPoruszaniaY = ((y - (wielkosc / 2)) - procay) / 10;
        szybkoscPoruszaniaX = odlegosc / 10;

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

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // System.out.println("MOVED!!"); //sprawdza czy działa event listener
    }
}

class Ball {
    int x;
    int y;
    int size;

    public Ball(int size, int x, int y) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void paint(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.gray);
        g2d.fillOval(x, y, size, size);
    }

    // sprawdz czy kulka o pozycji x,y i wielkosci size trafila kulke
    public boolean trafienie(int size, int x, int y) {
        // odl euklidesowa
        int odleglosc = (int) Math.sqrt(
                Math.pow(((x - (size / 2)) - this.x), 2) + Math.pow((y - (size / 2) - this.y), 2));
        if (odleglosc < 35) {
            this.x = -151150; // Jak trafiles to wyrzuc poza ekran
            this.y = 151150;
            return true;
        }
        return false;
    }
}

class Myframe extends JFrame implements KeyListener {

    Dzialo dzialo; // Klasa Dzialo o nazwie dzialo

    public Myframe(Dzialo dzialo) {
        this.dzialo = dzialo; // konstruktor dla Myframe to dzialo (stworzone wyzej) przypisane do dzialo (z
                              // Jpanela)
        addKeyListener(this); // dodany do tego keyListener

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.dzialo.setStrzal(true); // po naciśnieciu na SPACJE
            // bool "strzal = true" i wystrzeli klulkę

            // zbieranie współrzędnych pierwotnej piłki
            for (int i = 0; i < 2; i++) {
                this.dzialo.xD[i] = (int) dzialo.x;
                this.dzialo.yD[i] = dzialo.y;
            }

            System.out.println(" SPACJA! ");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

public class DzialoUpgrade {
    public static void main(String[] args) throws InterruptedException {

        int ilosCeli = Integer.parseInt(JOptionPane.showInputDialog("Ilość celi"));

        Dzialo dzialo = new Dzialo(ilosCeli);
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
