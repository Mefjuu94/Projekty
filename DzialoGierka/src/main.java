import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.Random;

class Dzialo extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    int Width = 1200;
    int Height = 800;

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
    int[] xD = new int[dodatkowe];
    int[] yD = new int[dodatkowe];

    // cieciwa

    int ciecProcaLewa = lewa;
    int ciecProcaPrawa = prawa;

    int odlegosc;
    int srodekProcy = Width / 2;
    int koniecy = procay;
    boolean rysCieciwe = true;

    int startx;
    double szybkoscPoruszaniaY;
    double szybkoscPoruszaniaX;

    // nasze cele
    Ball balls[];
    int iloscCeli;

    // TRAFIONE
    int celeTrafione;
    boolean upgrade = false;
    boolean strzelaj = false;
    boolean dragging = false;
    int count = 0; // info o upgrade
    boolean afterShoot = false;

    //gracze,wynik czas
    int gracze;
    int ktoryGraczGra;
    Player gracz[];

    long startTime = System.currentTimeMillis();
    int czasWsek;

    boolean updateScore = true;

    ScoreBoard scoreBoard;
    JButton przyciskOK;
    JButton noweCele;
    boolean IleNowychCeli = false;
    boolean przycisk = false;


    public Dzialo(int ilosCeli) throws FileNotFoundException {
        addMouseListener(this);
        addMouseMotionListener(this);
        setLayout(null);

        przyciskOK = new JButton("Kliklnij aby\n zmienić gracza");
        noweCele = new JButton("Ilość celi");
        noweCele.addActionListener(this);
        noweCele.setVisible(true);
        noweCele.setBounds(Width-150,Height-100,100,50);
        noweCele.setFocusable(false); // nie reguje na klawaiture
        add(noweCele);
        if (IleNowychCeli){
            odPoczatku();
            IleNowychCeli = false;
        }

        this.stworzCele(ilosCeli);
        this.iloscCeli = ilosCeli;
        this.scoreBoard = new ScoreBoard();
        this.scoreBoard.Height = Height;
        this.scoreBoard.Width = Width;
    }

    public int IloscGraczy() {
        gracze = Integer.parseInt(JOptionPane.showInputDialog("Ilość GRACZY"));
        System.out.println(startTime);
        ktoryGraczGra = 1;
        this.scoreBoard.ktoryGraczGra = ktoryGraczGra;
        this.scoreBoard.ileGraczy = gracze;
        return gracze;
    }
    //Tworzenie graczy
    public void Players() {
        gracz = new Player[gracze + 1]; // gracze liczeni od 1 !!!!!
        for (int i = 1; i < gracze + 1; i++) {
            String name = JOptionPane.showInputDialog("Imię Gracza " + i);
            gracz[i] = new Player(name);
            System.out.println(gracz[i].name);
        }
        this.scoreBoard.setPlayers(gracz);
    }

    ///////////////////////////////////////////////////////////////////

    public void odPoczatku() throws FileNotFoundException {
        this.scoreBoard.WczytajNajlepszyWynik();
        this.scoreBoard.ktoryGraczGra = ktoryGraczGra;

        this.stworzCele(iloscCeli);
        startTime = System.currentTimeMillis();
        x = -800; // wywal piłkę zeby po wpisaniu nie leciala dalej
        y = 50;
        xD[0] = -500; // wyzeruj współrzędne piłek dodatkowych zeby nie leciały dalej
        xD[1] = -500;
        celeTrafione = 0; //wyzeruj cele trafione
        szybkoscPoruszaniaX = 0;
        szybkoscPoruszaniaY = 0;
    }

    private void stworzCele(int ilosCeli) {
        Random rand = new Random();
        this.balls = new Ball[ilosCeli];
        for (int i = 0; i < ilosCeli; i++) {
            int ballSize = 40;
            this.balls[i] = new Ball(ballSize, rand.nextInt(Width - ballSize), rand.nextInt(procay - ballSize));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));

        g2d.setColor(new Color(192,192,192));
        g2d.fillRect(0,0,Width,Height);

        g2d.setColor(Color.black);
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

        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.drawString("cele Trafione = " + celeTrafione + "/ " + this.balls.length, Width - 400, Height - 100);

        if (upgrade) {
            g2d.drawString("UPGRADE AKTYWNY!! ", Width - 250, Height - 20);
        }

        //Wyświetlenie graczy - klasa tablica!!
        this.scoreBoard.paint(g2d);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // konstruktory -> do instancji do odwołania się w key listenerze
    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;

    }

    public void setStrzal(boolean strzelaj) {
        this.strzelaj = strzelaj;
    }

    public void SprawdzCele() throws FileNotFoundException {
        if (this.celeTrafione == this.balls.length) {
            System.out.println("Gratulacje, trafiłeś wszystkie Cele!");
            ObliczCzas();
            System.out.println("czas w sek " + czasWsek);
            if (updateScore) {
                updateScore();
            }
            //jaki wynik

            // wczytaj jeszscze raz piłeczki!!

            if (ktoryGraczGra  == gracze) {

                // pytanie czy chce zagrać jescze raz?
                int dialogButton = JOptionPane.showConfirmDialog(null,
                        "Czy chcesz zagrać jeszcze raz?", "WARNING", JOptionPane.YES_NO_OPTION);
                // YES =0 NO = 1
                if (dialogButton == 1) {
                    System.exit(0);
                } else if (dialogButton == 0) {
                    // tu ma być wywołanie na nowo!!!!
                    ktoryGraczGra = 1;
                    this.scoreBoard.ktoryGraczGra = ktoryGraczGra;
                    this.iloscCeli = Integer.parseInt(JOptionPane.showInputDialog("Ilość celi"));
                    this.odPoczatku();
                }
            } else {
                add(przyciskOK);
                przyciskOK.setVisible(true);
                przyciskOK.setBounds(Width/2-100,Height/2-25,200,50);
                przyciskOK.addActionListener(this);


                if (przycisk) {
                    przyciskOK.setVisible(false);
                    odPoczatku();
                    ktoryGraczGra++;
                    updateScore = true;
                    startTime = System.currentTimeMillis();
                }
            }
            this.scoreBoard.setScoreIfNeeded(this.gracz[ktoryGraczGra]);
            this.scoreBoard.ktoryGraczGra = ktoryGraczGra;
        }
    }

    public void KtoWygrał(){

    }
        //////////////////////////////////

        public void Trafienie () throws FileNotFoundException {
            for (int i = 0; i < this.balls.length; i++) {
                boolean trafienie1 = this.balls[i].trafienie(wielkosc, (int) x, y);
                boolean trafienie2 = false;
                boolean trafienie3 = false;
                if (this.upgrade) {
                    trafienie2 = this.balls[i].trafienie(wielkosc, (int) xD[0], yD[0]);
                    trafienie3 = this.balls[i].trafienie(wielkosc, (int) xD[1], yD[1]);
                }

                if (trafienie1 || trafienie2 || trafienie3) {
                    this.celeTrafione++;
                    this.gracz[ktoryGraczGra].score = this.celeTrafione;
                }
                // System.out.println("cele trafione = " + celeTrafione + " / " +
                // this.balls.length);
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
            SprawdzCele();

        }

        public void strzal () throws FileNotFoundException {
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

        }

        public void ObliczCzas () {
            long b = System.currentTimeMillis();
            long c = (b - startTime) / 1000;
            czasWsek = (int) c;

        }

        public void updateScore () {
            //wynik = iloscPilek/czas*10

            double wynikk = ((double) this.balls.length / czasWsek) * 10; // *czas * 10;
            System.out.println("wynikk" + wynikk);
            double wynik = Math.round(wynikk*100.0)/100.0; // obetnij wynik do 2 miejsc po przecinku!!

            gracz[ktoryGraczGra].score = wynik;
            System.out.println(wynik);
            updateScore = false;
        }

        @Override
        public Dimension getPreferredSize () {

            return new Dimension(Width, Height);
        }

        @Override
        public void mouseClicked (MouseEvent e){

        }

        @Override
        public void mousePressed (MouseEvent e){

            x = e.getX();
            y = e.getY();
            if (y > procay) {
                rysKulke = true;
                rysCieciwe = true;
                dragging = true;
                szybkoscPoruszaniaY = 0;
                szybkoscPoruszaniaX = 0;
                strzelaj = false;
            } else {
                rysKulke = false;
                rysCieciwe = false;
                dragging = false;
            }
        }

        @Override
        public void mouseReleased (MouseEvent e){

            x = e.getX();
            y = e.getY();
            if (y > procay) {
                dragging = false;
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

        }

        @Override
        public void mouseEntered (MouseEvent e){

        }

        @Override
        public void mouseExited (MouseEvent e){

        }

        @Override
        public void mouseDragged (MouseEvent e){

            x = e.getX();
            y = e.getY();

            if (y > procay) {
                dragging = true;
                repaint();
            } else {
                dragging = false;
            }

        }

        @Override
        public void mouseMoved (MouseEvent e){
            // System.out.println("MOVED!!"); //sprawdza czy działa event listener
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        przycisk = true;
        if(e.getSource() == noweCele){
            try {
                odPoczatku();
                ktoryGraczGra = 1;
                this.scoreBoard.ktoryGraczGra = ktoryGraczGra;
                this.iloscCeli = Integer.parseInt(JOptionPane.showInputDialog("Ilość celi"));
                this.odPoczatku();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}

    class KeyListener extends JFrame implements java.awt.event.KeyListener {

        Dzialo dzialo; // Klasa Dzialo o nazwie dzialo

        public KeyListener(Dzialo dzialo) {
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


    public class main {
        public static void main(String[] args) throws InterruptedException, FileNotFoundException {

            int iloscCeli = Integer.parseInt(JOptionPane.showInputDialog("Ilość celi"));

            Dzialo dzialo = new Dzialo(iloscCeli);
            KeyListener obwod = new KeyListener(dzialo);

            obwod.setVisible(true);
            obwod.add(dzialo);
            obwod.pack();
            obwod.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            dzialo.IloscGraczy();
            dzialo.Players();

            while (true) {
                dzialo.strzal();
                Thread.sleep(50);
            }
        }

        //praca na komponentach
        //przejscia pomiedzy komponentami
        //jak oczytywac z checkboxow rozwijanych mani
        //uleposzycz to i buttony

    }

