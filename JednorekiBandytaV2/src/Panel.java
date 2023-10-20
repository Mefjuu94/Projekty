import com.google.gson.Gson;

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


public class Panel extends JPanel implements MouseMotionListener, MouseListener {

    Images images = new Images();
    boolean sortuj = false;

    Buttons button = new Buttons();
    Wajcha wajcha = new Wajcha();
    Plus plus = new Plus();
    int multi = plus.multi;
    boolean[] jakaWygrana = new boolean[5];

    boolean zapblokujPociagniecie = false;

    //sttawka
    //int dialog = Integer.parseInt(JOptionPane.showInputDialog("podaj kwote jaką wnosisz"));
    int dialog = 100;
    //int stawka = Integer.parseInt(JOptionPane.showInputDialog("podaj ile chcesz postawić"));
    int stawka = 10;
    int basicStawka = stawka;
    int stawkaPlus = basicStawka * 3;
    boolean oblicz;
    int obl = 0;

    boolean LOSE;
    boolean WIN;

    //do automatycznej Gry//

    boolean zakonczLosowanie = false;
    int counterRepeat = 19000;
    boolean firstGame = false;
    boolean wynik = false;
    boolean POTWORZ = button.AutoRoll;
    ///


    ///zapis
    int saveCounter = 0;
    LocalTime time = LocalTime.now();
    int minuta = time.getMinute();
    int godzina = time.getHour();
    int sekunda = time.getSecond();
    String dzien = String.valueOf(LocalDate.now());

    String TimeWriteHour = String.valueOf(godzina);
    String TimeWrite = String.valueOf(minuta);
    String TimeWriteSec = String.valueOf(sekunda);

    FileWriter writer = new FileWriter(dzien + "_" + TimeWriteHour + "_" + TimeWrite + ".txt");
    ////

    ///JSON i konwertowanie do JSON////
    FileWriter Json = new FileWriter("wynik" + dzien + "_" + TimeWriteHour + "_" + TimeWrite + "_" + TimeWriteSec + ".json" );
    Gson gson = new Gson();
    ObjectToJson obj;
    String toJson = "";
    //////////////////////////////////


    Panel() throws IOException {
        images.Sortuj();

        addMouseMotionListener(this);
        addMouseListener(this);

        this.add(button.mutliChance);
        button.mutliChance.addMouseListener(this);
        button.mutliChance.setVisible(true);


        this.add(button.autoRoll);
        button.autoRoll.addMouseListener(this);
        button.autoRoll.setVisible(true);


        this.add(button.stopRoll);
        button.stopRoll.addMouseListener(this);
        button.stopRoll.setVisible(true);

        this.add(button.Roll);
        button.Roll.setVisible(true);

        this.add(button.zapis);
        button.zapis.setVisible(true);
        button.zapis.setSelected(false);

        images.xFirst = 133;
        images.xSecond = 301;
        images.xthrid = 462;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        //rysowanie taśmy
        if (button.ROLL) {
            images.Roll(g2d, this);
            obl = 0;
            multi = 0;
            if (sortuj){
                images.Sortuj();
                sortuj = false;
            }
            saveCounter = 0;
        } else if (button.STOP) {
            images.Stop(this,g2d);
            sortuj = true;
        }
        //rysowanie maszyny ( musi być na taśmie)
        images.RysujMaszyne(this,g2d);

        //rysowanie buttonow
        button.mutliChance.setBounds(150,100,200,80);
        button.autoRoll.setBounds(850,850,100,80);
        button.stopRoll.setBounds(850,150,100,80);
        button.Roll.setBounds(850,50,100,80);
        button.zapis.setBounds(850,750,100,80);

        g2d.drawRect(750,500,2,2);

        wajcha.RysujWajche(g2d);
        powrotWajcha();
        plus.KtorePodUwage(g2d,button.multi);

        //stopowanie taśmy

        Font f = new Font("serif", Font.PLAIN, 30);
        g2d.setColor(Color.red);
        g2d.setFont(f);

        if (images.speed == 0 && images.sprawdzWynik){
            plus.wynikPLUS(button.multi, images.wygraneZPlusem,jakaWygrana);
            multi = plus.multi;
            if (saveCounter == 0 && !POTWORZ) {
                try {
                    isSaveSelected();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            oblicz = true;
            wynik();
        }
        // kasa
        if (button.multi){
            stawka = stawkaPlus;
        }else {
            stawka = basicStawka;
        }
        Stawka(); // obliczanie stawki

        komunikat(g2d); // jak przegrasz to się wyswietli

        g2d.drawString("Twoje Pieniądze: " + dialog, 250, 900);
        g2d.drawString("Stawka: " + stawka, 650, 900);
        if (multi > 1){
            g2d.drawString("Stawke X " + multi + " !", 300, 790);
        }

        ifAutoButton();

        //powtórzenie
        if (POTWORZ ) {
            counterRepeat++;
            try {
                repeat();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        //rysowanie ikony Butotona
        if (button.GamesCOUNTER == 0 && POTWORZ){
            button.autoRoll.setIcon(button.autoRol3);
        } else if (button.GamesCOUNTER == 1 && POTWORZ) {
            button.autoRoll.setIcon(button.autoRol2);
        } else if (button.GamesCOUNTER > 1 && POTWORZ) {
            button.autoRoll.setIcon(button.autoRol1);
        } else
            button.autoRoll.setIcon(button.autoRol);
    }

    public void metoda(){

    }

    public void Stawka() {

        if (oblicz) {
            //System.out.println("oblicz");
            if (LOSE) {
                if (obl < 1) {
                    dialog = dialog - stawka;
                    obl ++;
                }
                WIN = false;
            } else if (WIN) {
                if (obl < 1) {
                    if (button.multi){
                        stawka = stawka * multi;
                    }
                    System.out.println("obliczyłem wynik stawka = " + stawka);
                    dialog = dialog + stawka;
                    obl ++;
                }
                LOSE = false;

            }
            oblicz = false;
            images.sprawdzWynik = false;
        }
    }

    public void wynik(){

        if (button.multi && oblicz) {
            if (multi > 0) {
                LOSE = false;
                WIN = true;
                stawka = stawka * multi;
            } else {
                LOSE = true;
            }
        }

        // wygrana bez plusa
        if (!button.multi && oblicz) {
            if (images.wygraneZPlusem[0][1].equals(images.wygraneZPlusem[1][1]) && images.wygraneZPlusem[1][1].equals(images.wygraneZPlusem[2][1])) {
                System.out.println("Wygrana w POZIOMIE!!!");
                jakaWygrana[4] = true;
                LOSE = false;
                WIN = true;
            } else {
                LOSE = true;
            }
        }
    }

    public void komunikat(Graphics2D g2d) {

        if (WIN) {
            g2d.drawString("Wygrałeś!!!", 300, 750);
        } else if (LOSE) {
            g2d.drawString("Przegrałeś!!!", 300, 750);
        }
        g2d.setColor(Color.BLACK);

        if (dialog < 1) {

            dialog = 1; // zatrzymanie dialogu zeby się nie powielał

            int komunikat = JOptionPane.showConfirmDialog(this, "Przegrałeś Wszystkie pieniądze!!!", "koniec gry", JOptionPane.OK_OPTION);
            System.out.println(komunikat);

            dialog = Integer.parseInt(JOptionPane.showInputDialog("podaj kwote jaką wnosisz"));
            if (button.multi) {
                stawkaPlus = Integer.parseInt(JOptionPane.showInputDialog("podaj ile chcesz postawić"));
                basicStawka = stawkaPlus / 3;
            } else {
                basicStawka = Integer.parseInt(JOptionPane.showInputDialog("podaj ile chcesz postawić"));
                stawkaPlus = basicStawka * 3;
            }


        }
    }

    public void powrotWajcha() {

        if (wajcha.xKulka > 750 && wajcha.yKulka > 500 && !zapblokujPociagniecie) {
            if (button.ROLL){
                button.STOP = true;
                button.ROLL = false;
            }else {
                button.ROLL = true;
            }
            System.out.println("pociągnięte!!");
            zapblokujPociagniecie = true;
            //System.out.println(zapblokujPociagniecie);
        }



        if (wajcha.xKulka > 680) {
            wajcha.xKulka = (wajcha.xKulka - 0.1);

        }
        if (wajcha.yKulka > 345) {
            wajcha.yKulka = (wajcha.yKulka - 0.2);

        }

        if (wajcha.yKulka < 345 && wajcha.xKulka < 680 && zapblokujPociagniecie) {
            zapblokujPociagniecie = false;
            //System.out.println(zapblokujPociagniecie);

        }

        repaint();
    }
    public Dimension getPreferredSize() {
        return new Dimension(1000, 1000);

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        wajcha.xKulka = e.getX() - wajcha.ballsize / 2;
        wajcha.yKulka = e.getY() - wajcha.ballsize / 2;

        if (wajcha.yKulka < 380 || wajcha.xKulka < 675) {
            wajcha.yKulka = 380;
            wajcha.xKulka = 680;
        }
        repaint();
    }

    public void repeat() throws IOException {

        ///ZROBIC CHECKBOXA NA ZAPIS BO WYWALA BLAD PRZY ZAPISYWANBIU

        if (button.GamesCOUNTER < button.ileLosowan && button.AutoRoll) {   ///na 3 losowania ( 2 )
             //System.out.println(counterRepeat); //-- liczenie "czasu"
            if (counterRepeat >= 8000 && button.STOP && !zakonczLosowanie) {

                System.out.println("zaczynanie od nowa counterRepeat = " + counterRepeat);
                images.Sortuj();
                button.ROLL = true;
                button.STOP = false;

                Arrays.fill(jakaWygrana, false);
                wynik = false;
                WIN = false;
                LOSE = false;

                firstGame = true;
                oblicz = false;

                counterRepeat = 0;
                button.GamesCOUNTER++;
                if (button.zapis.isSelected()) {
                    zapis(images.rollFirst);
                }
            }
            if (counterRepeat >= 3000 && !button.STOP && !button.ROLL && !zakonczLosowanie && button.AutoRoll) {
                System.out.println("zaczynanie w ogóle= " + counterRepeat);
                images.Sortuj();
                button.ROLL = true;

                Arrays.fill(jakaWygrana, false);
                wynik = false;
                WIN = false;
                LOSE = false;
//                counter = 0;
//                CounterNapisow = 0;

                counterRepeat = 0;
            }
            if (counterRepeat >= 5000 && button.ROLL && !button.STOP && !zakonczLosowanie && button.AutoRoll) {
                System.out.println("zwalnianie STOP counterRepeat = " + counterRepeat);
                System.out.println(images.yFirst[0]);
                button.ROLL = false;
                button.STOP = true;
                counterRepeat = 0;
                images.speed = 0.05;
            }

        } else if (button.GamesCOUNTER == button.ileLosowan || !button.AutoRoll && images.speed == 0 ){
            if (button.zapis.isSelected()) {
                writer.close();
                Json.close();
            }
            zakonczLosowanie = true;
            button.ROLL = false;
            button.STOP = true;
            POTWORZ = false;
            button.AutoRoll = false;
        }

        if (counterRepeat > 20000){
            counterRepeat = 0;
            button.STOP  = true;
            zakonczLosowanie = false;
        }

    }

    public void zapis(ImageIcon[] rollFirst) throws IOException {

        plus.JakaWygrana(button.multi, images.wygraneZPlusem, jakaWygrana);

        ///////JSON
        String tasma1;

        String tasma2;

        String tasma3;

        tasma1 = Arrays.toString(images.rollFirst);
        tasma2 = Arrays.toString(images.rollSecond);
        tasma3 = Arrays.toString(images.rollThrid);

        obj = new ObjectToJson(tasma1, tasma2, tasma3, jakaWygrana,
                stawka,dialog);
        toJson = gson.toJson(obj);
        System.out.println(toJson);

        gson.toJson(obj,Json);
        Json.close();

        saveCounter++;
        ////////////////////////JSON

        writer.write("Losowanie numer: " + button.GamesCOUNTER + "\n");

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
        writer.write(images.wygraneZPlusem[0][0] + " || " + images.wygraneZPlusem[1][0] + " || " + images.wygraneZPlusem[2][0] + "\n"
                + images.wygraneZPlusem[0][1] + " || " + images.wygraneZPlusem[1][1] + " || " + images.wygraneZPlusem[2][1] + "\n"
                + images.wygraneZPlusem[0][2] + " || " + images.wygraneZPlusem[1][2] + " || " + images.wygraneZPlusem[2][2] + "\n");
        writer.write("obrazy które się zgadzały: :" + "\n");

        //lewy pion, prawy pion, z lewej do prawej, z preawej do LEWEJ, POZIOM
        if (jakaWygrana[0]) {
            writer.write("Wygrana w LEwym pionie  " + images.wygraneZPlusem[0][0] + "\n");
        } else if (jakaWygrana[1]) {
            writer.write("Wygrana w PRAWYM pionie  " + images.wygraneZPlusem[2][2] + "\n");
        } else if (jakaWygrana[2]) {
            writer.write("Wygrana na skos z LEWEJ DO PRAWEJ " + images.wygraneZPlusem[0][0] + "\n");
        } else if (jakaWygrana[3]) {
            writer.write("Wygrana na skos z PRAWEJ DO LEWEJ " + images.wygraneZPlusem[0][2] + "\n");
        } else if (jakaWygrana[4]) {
            writer.write("Wygania POZIOMIE  " + images.wygraneZPlusem[1][1] + "\n");
        }
        boolean zadnawygrana = jakaWygrana[0] || jakaWygrana[1] || jakaWygrana[2] ||
                jakaWygrana[3] || jakaWygrana[4];

        if (!zadnawygrana) {
            writer.write("Nie było żadnej wygranej" + "\n");
        }
        if (button.multi){
            writer.write("STAWKA Z PLUSEM (3xpodstawa) x mnożnik  =" + basicStawka * 3 + " * " + multi + "\n");
        }else {
            writer.write("Stawka = " + stawka + "\n");
        }
        writer.write("Stan konta = " + dialog + "\n");


        writer.write("--------------------------" + "\n");

    }

    private void ifAutoButton(){
        POTWORZ = button.AutoRoll;
    }

    private void isSaveSelected() throws IOException {
        if (button.zapis.isSelected()) {
            zapis(images.rollFirst);
        }
    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        System.out.println(e.getX() + " x");
        System.out.println(e.getY() + " Y");

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
