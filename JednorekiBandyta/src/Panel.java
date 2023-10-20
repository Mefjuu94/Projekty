import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {

    ImageIcon rollTwo = new ImageIcon("src/image/upgrade2png.png");

    ImageIcon jeden = new ImageIcon("src/image/1.jpg");
    ImageIcon dwa = new ImageIcon("src/image/2.jpg");
    ImageIcon trzy = new ImageIcon("src/image/3.jpg");
    ImageIcon cztery = new ImageIcon("src/image/4.jpg");
    ImageIcon piec = new ImageIcon("src/image/5.jpg");
    ImageIcon szesc = new ImageIcon("src/image/6.jpg");
    ImageIcon siedem = new ImageIcon("src/image/7.jpg");
    ImageIcon osiem = new ImageIcon("src/image/8.jpg");
    ImageIcon dziewiec = new ImageIcon("src/image/9.jpg");
    ImageIcon dziesiec = new ImageIcon("src/image/10.jpg");
    ImageIcon jedenasie = new ImageIcon("src/image/11.jpg");
    ImageIcon dwanascie = new ImageIcon("src/image/12.jpg");
    ImageIcon trzynascie = new ImageIcon("src/image/13.jpg");
    ImageIcon czternascie = new ImageIcon("src/image/14.jpg");
    ImageIcon pietnascie = new ImageIcon("src/image/15.jpg");
    ImageIcon szesnascie = new ImageIcon("src/image/16.jpg");
    int CounterNapisow = 0;

    ImageIcon[] all = {jeden, dwa, trzy, cztery, piec, szesc, siedem, osiem, dziewiec, dziesiec, jedenasie, dwanascie, trzynascie,
            czternascie, pietnascie, szesnascie};
    String[] obrazy = {"wiśnia", "śliwka", "dzwon", "koniczyna", "cytryna", "pieniądz", "bar", "jabłko", "serce", "winogrona",
            "diament", "pomarańcza", "podkowa", " siódemka", "znak Zapytania", " arbuz"};


    ImageIcon[] rollFirst = new ImageIcon[15];
    int xFirst;
    double[] yFirst = new double[15];
    ImageIcon[] rollSecond = new ImageIcon[15];
    int xSecond;
    double[] ySecond = new double[15];
    ImageIcon[] rollThrid = new ImageIcon[15];
    int xthrid;
    double[] yThrid = new double[15];
    int ods = 115;

    int ballsize = 80;
    double xKulka = 780;
    double yKulka = 345;

    boolean poczatek = true;
    boolean ROLL = false;
    boolean STOP = false;

    //int dialog = Integer.parseInt(JOptionPane.showInputDialog("podaj kwote jaką wnosisz"));
    int dialog = 100;
    //int stawka = Integer.parseInt(JOptionPane.showInputDialog("podaj ile chcesz postawić"));
    int stawka = 10;
    int basicStawka = stawka;
    int stawkaPlus = basicStawka * 3;

    boolean oblicz = false; // do obliczania kasy
    int counteroblicz = 0; // do obliczania kasy

    //do wygranych i wylosowanych obrazków
    ImageIcon[][] wyLOSowane = new ImageIcon[3][3];
    ImageIcon[][] wygraneZPlusem = new ImageIcon[3][3];


    // do rozpoczęcia
    boolean firstGame = false;
    boolean wajchaPociagnieta = false;


    //do wyniku gry
    boolean wynik = false;
    boolean WIN = false;
    boolean LOSE = false;
    boolean sprawdzWynik = false;
    int counter = 0;

    double speed = 0.05;

    int mozliwosci = 3;

    //do zarysowania  mnożnika z wygranej!!!
    boolean[] jakaWygrana = new boolean[5];

    String[] szansa = {"Wieksza szansa!! (moneyx3)", "Mniejsza Szansa ( money x1)"};
    JButton Plus = new JButton();
    boolean PLUSIK = false;
    int multi = 0; // mnożnik przy losowaniu Z PLUSEM

    int counterRepeat = 0;
    boolean POTWORZ = true;
    int GamesCOUNTER = 0;
    boolean zakonczLosowanie = false;

    LocalTime time = LocalTime.now();
    int minuta = time.getMinute();
    int godzina = time.getHour();
    String dzien = String.valueOf(LocalDate.now());

    String TimeWriteHour = String.valueOf(godzina);
    String TimeWrite = String.valueOf(minuta);


    FileWriter writer = new FileWriter(dzien + "_" + TimeWriteHour + "_" + TimeWrite + ".txt");
    int ileLosowan = 2;


    Panel() throws IOException {

        this.add(Plus);
        Plus.addMouseListener(this);

        addMouseListener(this);
        addMouseMotionListener(this);


        sort();
        xFirst = 133;
        xSecond = 301;
        xthrid = 462;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.lightGray);


        //przycisk
        Plus.setLayout(null);
        Plus.setBounds(730, 107, 200, 80);
        if (PLUSIK) {
            Plus.setText(szansa[1]);
            stawka = stawkaPlus;
        } else {
            Plus.setText(szansa[0]);
            stawka = basicStawka;
        }


        //pociągnięta wajcha
        wajcha();

        rysuj(g2d);// osuniecie w y o dlugosc obrazka za każdą pętlą

        metoda();

        //przemieszczenie taśmy
        RysujTasmeNaNowo(yFirst);
        RysujTasmeNaNowo(ySecond);
        RysujTasmeNaNowo(yThrid);

        //MASZYNA LOSUJĄCA
        rollTwo.paintIcon(this, g2d, 0, 0);


        // kluka > wajcha

        g2d.setStroke(new BasicStroke(10));
        g2d.drawRect(728, 106, 203, 83);

        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(15));
        g2d.fillOval((int) xKulka, (int) yKulka, ballsize, ballsize);
        g2d.drawLine(603, 666, (int) (xKulka + (ballsize / 2)), (int) (yKulka + (ballsize / 2)));

        Font f = new Font("serif", Font.PLAIN, 30);
        g2d.setColor(Color.red);
        g2d.setFont(f);

        // podświetl które pola są brane pod uwagę

        KtorePodUwage(g2d, PLUSIK);

        //sprawdzenie wyniku

        try {
            SprawdzWynikdoWyswietlenia();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //obliczanie stawki

        Stawka();

        //// kominukat o przegranej!!! oraz wpisanie kwoty do stawiania
        komunikat(g2d);

        //KASA
        g2d.drawString("Twoje Pieniądze: " + dialog, 250, 900);
        g2d.drawString("Stawka: " + stawka, 750, 900);


        if (POTWORZ & speed == 0) {
            counterRepeat++;
            try {
                repeat();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (POTWORZ & speed > 0 ) {
            counterRepeat++;
            try {
                repeat();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    //////////////////////////////////////////////////////////////////////////////////////////////////

    public void sort() {

        Random random = new Random();

        if (!zakonczLosowanie) {
            for (int i = 0; i < rollFirst.length; i++) {
                rollFirst[i] = all[random.nextInt(mozliwosci)];
                yFirst[i] = 530 - (ods * i);
            }
            for (int i = 0; i < rollSecond.length; i++) {
                rollSecond[i] = all[random.nextInt(mozliwosci)];
                ySecond[i] = 530 - (ods * i);
            }
            for (int i = 0; i < rollThrid.length; i++) {
                rollThrid[i] = all[random.nextInt(mozliwosci)];
                yThrid[i] = 530 - (ods * i);
            }
            System.out.println("losowanie");
            System.out.println(counterRepeat);
        }
    }


    public void metoda() {


    }

    public void rysuj(Graphics2D g2d) {

        for (int i = 0; i < rollFirst.length; i++) {
            rollFirst[i].paintIcon(this, g2d, xFirst, (int) yFirst[i]);
            rollSecond[i].paintIcon(this, g2d, xSecond, (int) ySecond[i]);
            rollThrid[i].paintIcon(this, g2d, xthrid, (int) yThrid[i]);

            if (ROLL && !zakonczLosowanie) {

                Arrays.fill(jakaWygrana, false);
                wynik = false;
                WIN = false;
                LOSE = false;

                poczatek = false;
                yFirst[i] += 0.3; // 0.3
                ySecond[i] += 0.3;
                yThrid[i] += 0.3;
                STOP = false;
                counter = 0;
                speed = 0.05;
            }

            boolean malaSzybkos = false;
            if (STOP) {

                if (yFirst[0] <= 1215) {
                    yFirst[i] += speed; // 0.3
                    ySecond[i] += speed;
                    yThrid[i] += speed;

                    counteroblicz = 0;


                } else if (yFirst[0] > 1350 && yFirst[0] <= 1675 ) {
                    yFirst[i] += speed; // 0.3
                    ySecond[i] += speed;
                    yThrid[i] += speed;

                    counteroblicz = 0;


                } else{
                    speed = 0;

                    stopUj(yFirst, i, rollFirst, 0);

                    stopUj(ySecond, i, rollSecond, 1);

                    stopUj(yThrid, i, rollThrid, 2);
                    malaSzybkos = false;

                    if (speed == 0 && counteroblicz == 0) {
                        sprawdzWynik = true;
                    }
                }

            }
            malaSzybkos = false;


        }
        repaint();
    }


    //=================zatrzymaj
    public void stopUj(double[] yFirst, int i, ImageIcon[] rollFirst, int j) {

        if (yFirst[i] > 275 && yFirst[i] < 395) {
           // System.out.println(rollFirst[i] + " pierwszy wiersz");
            wygraneZPlusem[j][0] = rollFirst[i];
            //System.out.println(j);
        }

        if (yFirst[i] >= 410 && yFirst[i] < 510) {
           // System.out.println(rollFirst[i] + " drugi wiersz");
            wygraneZPlusem[j][1] = rollFirst[i];
            // System.out.println(j);
        }

        if (yFirst[i] > 520 && yFirst[i] < 620) {
            //System.out.println(rollFirst[i] + " trzeci wiersz");
            wygraneZPlusem[j][2] = rollFirst[i];
            // System.out.println(j);
        }

    }

    public void SprawdzWynikdoWyswietlenia() throws IOException {
        if (sprawdzWynik) {
            System.out.println(sprawdzWynik);
            wynik();
            if (wynik) {
                oblicz = true;
            }
        }
    }

    public void Stawka() {

        if (oblicz) {
            System.out.println("oblicz");
            if (LOSE) {
                dialog = dialog - stawka;
                WIN = false;
            } else if (WIN) {
                dialog = dialog + stawka;
                LOSE = false;

            }
            oblicz = false;
            wynik = false;
            counteroblicz++;
            sprawdzWynik = false;
        }
    }

    public void komunikat(Graphics2D g2d) {

        if (WIN) {
            g2d.drawString("Wygrałeś!!!", 300, 850);
        } else if (LOSE) {
            g2d.drawString("Przegrałeś!!!", 300, 850);
        }
        g2d.setColor(Color.BLACK);

        if (dialog < 1) {

            dialog = 1; // zatrzymanie dialogu zeby się nie powielał

            int komunikat = JOptionPane.showConfirmDialog(this, "Przegrałeś Wszystkie pieniądze!!!", "koniec gry", JOptionPane.OK_OPTION);
            System.out.println(komunikat);

            dialog = Integer.parseInt(JOptionPane.showInputDialog("podaj kwote jaką wnosisz"));
            if (PLUSIK) {
                stawkaPlus = Integer.parseInt(JOptionPane.showInputDialog("podaj ile chcesz postawić"));
                basicStawka = stawkaPlus / 3;
            } else {
                basicStawka = Integer.parseInt(JOptionPane.showInputDialog("podaj ile chcesz postawić"));
                stawkaPlus = basicStawka * 3;
            }


        }
    }

    private void wynik(){

        multi = 0;
        /// poprawić multiplicator - nie nalicza ani nie widzi mnoożnika
        wynikPLUS(PLUSIK,wygraneZPlusem,jakaWygrana);

        // jak mnożnik jest większy od 0 (jak tafiłem chociaż raz 3 w  rzędzie) to wygrałem x mnożnik

        System.out.println(multi + " multi plus");

        if (PLUSIK) {
            if (multi > 0) {
                System.out.println(multi + " mnożnik");
                WIN = true;
                stawka = stawkaPlus * multi;
            } else {
                LOSE = true;
            }
        }


        // wygrana bez plusa
        if (!PLUSIK) {
            if (wygraneZPlusem[0][1].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][1])) {
                System.out.println("Wygrana w POZIOMIE!!!");
                jakaWygrana[4] = true;
                WIN = true;
            } else {
                LOSE = true;
            }
        }

        // wyświetlenie wyniku
        wynik = true;
    }

    public void RysujTasmeNaNowo(double[] yFirst) {

        if (yFirst[0] > 1780) {
            for (int i = 0; i < rollFirst.length; i++) {
                yFirst[i] = 530 - (ods * i);
            }
        }
    }


    public void wajcha() {

        if (xKulka > 500 && wajchaPociagnieta) {
            ROLL = !ROLL;
            STOP = !STOP;
            wajchaPociagnieta = false;
        }

        if (ROLL) {
            Arrays.fill(jakaWygrana, false);
            wynik = false;
            WIN = false;
            LOSE = false;
            counter = 0;
            CounterNapisow = 0;
        }

        if (xKulka > 780) {
            xKulka = xKulka - 0.1;
        }
        if (yKulka > 345) {
            yKulka = yKulka - 0.2;
        }
    }

    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    public void repeat() throws IOException {

        if (GamesCOUNTER < ileLosowan) {   ///na 3 losowania ( 2 )
            // System.out.println(counterRepeat); -- liczenie "czasu"
            if (counterRepeat >= 13000 && STOP && !zakonczLosowanie) {

                System.out.println("zaczynanie od nowa counterRepeat = " + counterRepeat);
                ROLL = true;

                firstGame = true;
                wynik = false;
                oblicz = false;

                counterRepeat = 0;
                GamesCOUNTER++;
                zapis(rollFirst);
            }
            if (counterRepeat >= 3000 && !STOP && !ROLL && !zakonczLosowanie) {
                System.out.println("zaczynanie w ogóle= " + counterRepeat);
                ROLL = true;

                Arrays.fill(jakaWygrana, false);
                wynik = false;
                WIN = false;
                LOSE = false;
                counter = 0;
                CounterNapisow = 0;

                counterRepeat = 0;
            }
            if (counterRepeat >= 5000 && ROLL && !STOP && !zakonczLosowanie) {
                System.out.println("zwalnianie STOP counterRepeat = " + counterRepeat);
                System.out.println(yFirst[0]);
                ROLL = false;
                STOP = true;
                counterRepeat = 0;
                speed = 0.05;
            }

        } else if (GamesCOUNTER == ileLosowan){


            writer.close();
            zakonczLosowanie = true;
        }



    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == Plus) {
            PLUSIK = !PLUSIK;
            System.out.println("PLUSIK = " + PLUSIK);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        System.out.println(e.getX());
        System.out.println(e.getY());
        sort();
        xKulka = e.getX() - ballsize / 2;
        yKulka = e.getY() - ballsize / 2;

        if (yKulka < 380 || xKulka < 775) {
            yKulka = 380;
            xKulka = 780;
        } else {

            firstGame = true;
            wajchaPociagnieta = true;
            wynik = false;
            oblicz = false;
            repaint();
        }

    }

    //////////////////////----------SPRAWDZENIE OPRAZ KTRÓRE AKTYWENE------------------------
    public static void KtorePodUwage(Graphics2D g2d, boolean PLUSIK){

        if (PLUSIK){
            g2d.setStroke(new BasicStroke(8));
            g2d.setColor(Color.red);

            g2d.drawRect(133,300,100,100);
            g2d.drawRect(133,415,100,100);
            g2d.drawRect(133,530,100,100);

            g2d.drawRect(301,415,100,100);

            g2d.drawRect(462,300,100,100);
            g2d.drawRect(462,415,100,100);
            g2d.drawRect(462,530,100,100);

        }else {
            g2d.setStroke(new BasicStroke(8));
            g2d.setColor(Color.red);
            g2d.drawRect(133,415,100,100);
            g2d.drawRect(301,415,100,100);
            g2d.drawRect(462,415,100,100);

        }
    }

    public int wynikPLUS(boolean PLUSIK, ImageIcon[][] wygraneZPlusem, boolean[] jakaWygrana){



        //pion 1
        if (wygraneZPlusem[0][0].equals(wygraneZPlusem[0][1]) && wygraneZPlusem[0][1].equals(wygraneZPlusem[0][2])){
            System.out.println("Wygrana w LEWYM PIonie!!!");
            multi++;
            jakaWygrana[0] = true;
        }

        //pion 2
        if (wygraneZPlusem[2][0].equals(wygraneZPlusem[2][1]) && wygraneZPlusem[2][1].equals(wygraneZPlusem[2][2])){
            System.out.println("Wygrana w PRAWYM  PIonie!!!");
            multi++;
            jakaWygrana[1] = true;
        }

        //skos 1

        if (wygraneZPlusem[0][0].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][2])){
            System.out.println("Wygrana w SKOSIE z lewej do prawej!!!");
            multi++;
            jakaWygrana[2] = true;
        }

        //skos 2

        if (wygraneZPlusem[0][2].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][0])){
            System.out.println("Wygrana w SKOSIE z PRAWEJ DO LERWEJ!!!");
            multi++;
            jakaWygrana[3] = true;
        }

        if (wygraneZPlusem[0][1].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][1])){
            System.out.println("Wygrana w POZIOMIE!!!");
            multi++;
            jakaWygrana[4] = true;
        }

        System.out.println(multi+ " multi");

        return multi;
    }
//////////////////////////////////////////////////////

    public void zapis(ImageIcon[] rollFirst) throws IOException {

        writer.write("Losowanie numer: " + GamesCOUNTER + "\n");


        writer.write("taśma " + 1 + "\n");
        String[] wyraz = new String[rollFirst.length];
        for (int i = 0; i < rollFirst.length; i++) {
            wyraz[i] = String.valueOf(rollFirst[i]);
            writer.write(wyraz[i] + "\n");
        }
        writer.write("-------" + "\n");

        writer.write("taśma " + 2 + "\n");

        for (int i = 0; i < rollFirst.length; i++) {
            wyraz[i] = String.valueOf(rollFirst[i]);
            writer.write(wyraz[i] + "\n");
        }
        writer.write("-------" + "\n");

        writer.write("taśma " + 3 + "\n");

        for (int i = 0; i < rollFirst.length; i++) {
            wyraz[i] = String.valueOf(rollFirst[i]);
            writer.write(wyraz[i] + "\n");
        }

        writer.write("-------" + "\n");
        writer.write("Wylosowane obrazy w okienkach:" + "\n");
        writer.write(wygraneZPlusem[0][0] + " || " + wygraneZPlusem[1][0] + " || " + wygraneZPlusem[2][0] + "\n"
                + wygraneZPlusem[0][1] + " || " + wygraneZPlusem[1][1] + " || " + wygraneZPlusem[2][1] + "\n"
                + wygraneZPlusem[0][2] + " || " + wygraneZPlusem[1][2] + " || " + wygraneZPlusem[2][2] + "\n");
        writer.write("obrazy które się zgadzały: :" + "\n");

        //lewy pion, prawy pion, z lewej do prawej, z preawej do LEWEJ, POZIOM
        if (jakaWygrana[0]) {
            writer.write("Wygrana w LEwym pionie  " + wygraneZPlusem[0][0] + "\n");
        } else if (jakaWygrana[1]) {
            writer.write("Wygrana w PRAWYM pionie  " + wygraneZPlusem[2][2] + "\n");
        } else if (jakaWygrana[2]) {
            writer.write("Wygrana na skos z LEWEJ DO PRAWEJ " + wygraneZPlusem[0][0] + "\n");
        } else if (jakaWygrana[3]) {
            writer.write("Wygrana na skos z PRAWEJ DO LEWEJ " + wygraneZPlusem[0][2] + "\n");
        } else if (jakaWygrana[4]) {
            writer.write("Wygania POZIOMIE  " + wyLOSowane[1][1] + "\n");
        }
        boolean zadnawygrana = jakaWygrana[0] || jakaWygrana[1] || jakaWygrana[2] ||
                jakaWygrana[3] || jakaWygrana[4];

        if (!zadnawygrana) {
            writer.write("Nie było żadnej wygranej" + "\n");
        }
        if (PLUSIK){
            writer.write("STAWKA Z PLUSEM (3xpodstawa) x mnożnik  =" + basicStawka * 3 + " * " + multi + "\n");
        }else {
            writer.write("Stawka = " + stawka + "\n");
        }
        writer.write("Stan konta = " + dialog + "\n");


        writer.write("--------------------------" + "\n");

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        xKulka = e.getX() - ballsize / 2;
        yKulka = e.getY() - ballsize / 2;

        if (yKulka < 380 || xKulka < 775) {
            yKulka = 380;
            xKulka = 780;
        }
        wynik = false;
        oblicz = false;


        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public Dimension getPreferredSize() {
        return new Dimension(1000, 1000);

    }
}
