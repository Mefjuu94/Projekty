package Figury;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Figura implements KeyListener {

    int HEIGHT;
    int WIDTH;

    int x = 230;
    int y = 180;

    Color c;
    int[] wspolrzedneX = new int[4];
    int[] wspolrzedneY = new int[4];

    //nastepna
    int startNextX = 400;
    int startNextY = 400;
    int[] nextX = new int[4];
    int[] nextY = new int[4];
    int nextLOS;
    int nextTyp;
    Color nexTcolor;
    int nextWIDTH;
    int nextHeight;

    int startTurnX;
    int startTurnY;

    int cellSize = 30;

    int wylosowana;
    boolean przeusniecie = false;
    boolean left,right,down,turn;

    //Lista zapisanych figur które są na dnie

    int[][] TablicaNaDnie = new int[20][15];
    int wypelnienieKolorem; // wypełnia komorke kolorem
    /// kolory nr 1 zoltyy 2 czerw....


    boolean StartGame = true;

    boolean naDnie;

    //figury

    L1 l1 = new L1();
    Rect r1 = new Rect();
    Z z1 = new Z();
    I patyk = new I();
    T t = new T();

    int typ;

    //czas / tick do zjezxdzania w dol oraz rekacji na klawisze
    long currentTimeMilis ;
    long trzystaMs =  System.currentTimeMillis();
    long stoMSkey =  System.currentTimeMillis();


    public Figura(){

        losujFigure();

    }

    public void stworzFigue(int[] wspolrzedneX,int[] wspolrzedneY,Color c, int WIDTH, int HEIGHT, int typ){

        this.wspolrzedneX[0] = wspolrzedneX[0];
        this.wspolrzedneX[1] = wspolrzedneX[1];
        this.wspolrzedneX[2] = wspolrzedneX[2];
        this.wspolrzedneX[3] = wspolrzedneX[3];

        this.wspolrzedneY[0] = wspolrzedneY[0];
        this.wspolrzedneY[1] = wspolrzedneY[1];
        this.wspolrzedneY[2] = wspolrzedneY[2];
        this.wspolrzedneY[3] = wspolrzedneY[3];

        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;

        this.typ = typ;

        this.c = c;

    }

    public void wylosowaniaFigura(int[] nextX,int[] nextY,Color nexTcolorc, int nextWIDTH, int nextHeight, int nextTyp){

        this.nextX[0] = nextX[0];
        this.nextX[1] = nextX[1];
        this.nextX[2] = nextX[2];
        this.nextX[3] = nextX[3];

        this.nextY[0] = nextY[0];
        this.nextY[1] = nextY[1];
        this.nextY[2] = nextY[2];
        this.nextY[3] = nextY[3];

        this.nextWIDTH = nextWIDTH;
        this.nextHeight = nextHeight;

        this.nextTyp = nextTyp;

        this.nexTcolor = nexTcolorc;

    }


    ////////////////// LOSOWANIE FIGURY I WYSWIETLENIE JEJ > PRZEKAZANIE DO TABLIOCY NEXT
    public void losujFigure(){
        Random random = new Random();
        nextLOS = random.nextInt(5);
        //wylosowana = 3;
        switch (nextLOS){
            case 0:
                l1.wylosujTyp();
                wylosowaniaFigura(l1.xL, l1.yL,l1.c,l1.width,l1.height,l1.typ);
                nextTyp = l1.typ;
                if (StartGame) {
                    stworzFigue(l1.xL, l1.yL, l1.c, l1.width, l1.height, l1.typ);
                }
                break;
            case 1:
                //to mie ma typu
                wylosowaniaFigura(r1.xL, r1.yL,r1.c,r1.Width,r1.Height,0);
                nextTyp = 0;
                if (StartGame) {
                    stworzFigue(r1.xL, r1.yL, r1.c, r1.Width, r1.Height, 0);
                }
                break;
            case 2:
                z1.wyLosujTyp();
                wylosowaniaFigura(z1.xL, z1.yL,z1.c,z1.Width,z1.Height, z1.typ);
                nextTyp = z1.typ;
                if (StartGame) {
                    stworzFigue(z1.xL, z1.yL, z1.c, z1.Width, z1.Height, z1.typ);
                }
                break;
            case 3:
                patyk.wylosujTyp();
                wylosowaniaFigura(patyk.xL, patyk.yL,patyk.c,patyk.Width,patyk.Height, patyk.typ);
                nextTyp = patyk.typ;
                if (StartGame) {
                    stworzFigue(patyk.xL, patyk.yL, patyk.c, patyk.Width, patyk.Height, patyk.typ);
                }
                break;
            case 4:
                t.wylosujTyp();
                wylosowaniaFigura(t.xL, t.yL,t.c,t.Width,t.Height,t.typ);
                nextTyp = t.typ;
                if (StartGame) {
                    stworzFigue(t.xL, t.yL, t.c, t.Width, t.Height, t.typ);
                }
                break;
        }

        if (StartGame){
            repeatLosAfterBegining(); // losuj jeszcze raz po rozpoczeciu
        }
    }

    public void repeatLosAfterBegining(){ // losuj jeszcze raz po rozpoczeciu
        Random random = new Random();
        nextLOS = random.nextInt(5);
        switch (nextLOS){
            case 0:
                l1.wylosujTyp();
                wylosowaniaFigura(l1.xL, l1.yL,l1.c,l1.width,l1.height,l1.typ);
                break;
            case 1:
                //to mie ma typu
                wylosowaniaFigura(r1.xL, r1.yL,r1.c,r1.Width,r1.Height,0);
                break;
            case 2:
                z1.wyLosujTyp();
                wylosowaniaFigura(z1.xL, z1.yL,z1.c,z1.Width,z1.Height, z1.typ);
                break;
            case 3:
                patyk.wylosujTyp();
                wylosowaniaFigura(patyk.xL, patyk.yL,patyk.c,patyk.Width,patyk.Height, patyk.typ);
                break;
            case 4:
                t.wylosujTyp();
                wylosowaniaFigura(t.xL, t.yL,t.c,t.Width,t.Height,t.typ);
                break;
        }

        StartGame = false;
    }

    public void rysujFigure(Graphics2D g2d) throws InterruptedException {

        currentTimeMilis = System.currentTimeMillis();

        g2d.setColor(c);

        g2d.fillRect(wspolrzedneX[0],wspolrzedneY[0],cellSize,cellSize);
        g2d.fillRect(wspolrzedneX[1],wspolrzedneY[1],cellSize,cellSize);
        g2d.fillRect(wspolrzedneX[2],wspolrzedneY[2],cellSize,cellSize);
        g2d.fillRect(wspolrzedneX[3],wspolrzedneY[3],cellSize,cellSize);


        update();

        startTurnX = wspolrzedneX[0];
        startTurnY = wspolrzedneY[0];

        //Panel Następnej
        g2d.setColor(nexTcolor);

        g2d.fillRect(nextX[0] + startNextX,nextY[0] + startNextY,cellSize,cellSize);
        g2d.fillRect(nextX[1] + startNextX,nextY[1] + startNextY,cellSize,cellSize);
        g2d.fillRect(nextX[2] + startNextX,nextY[2] + startNextY,cellSize,cellSize);
        g2d.fillRect(nextX[3] + startNextX,nextY[3] + startNextY,cellSize,cellSize);

        ///
        //rysowanie kloców na dnie

        for (int i = 0; i < TablicaNaDnie.length ; i++) {

            System.out.println(i + " i");
            for (int j = 0; j < TablicaNaDnie[0].length; j++) {
            g2d.fillRect(TablicaNaDnie[i][j],TablicaNaDnie[i][j],cellSize,cellSize);
                System.out.println(j + " j");
            }
        }


        //szykbosc przesuwania przy nasciniecu na klawisz
        if (currentTimeMilis - stoMSkey > 130){
            przeusniecie = true;
            stoMSkey = currentTimeMilis;
        }else {
            przeusniecie = false;
        }

        // szybkosc przesuwania w dol
        if (currentTimeMilis - trzystaMs > 400) {
            wDol();
            trzystaMs = currentTimeMilis;
        }


        dodajDolisty();   /// <<---ZROBIĆ !!!!

    }

    public void GetNext(){ // weź wcześniej wylsoowaną figure i daj na planszę

        for (int i = 0; i < 4; i++) {
            wspolrzedneX[i] = nextX[i];
            wspolrzedneY[i] = nextY[i];

            HEIGHT = nextHeight;
            WIDTH = nextWIDTH;
            c = nexTcolor;
            typ = nextTyp;
            wylosowana = nextLOS;

        }
       // NASTEPNA FIGURA WYLSOOWANA!!!

    }

    public void update(){
        if (right && przeusniecie && wspolrzedneX[0] < 500 - (cellSize * WIDTH )){
            for (int i = 0; i < 4; i++) {
                wspolrzedneX[i] = wspolrzedneX[i] + cellSize;
            }
        }

        if (left && przeusniecie && wspolrzedneX[0] > 50){
            for (int i = 0; i < 4; i++) {
                wspolrzedneX[i] = wspolrzedneX[i] - cellSize;
            }
        }
        if (down && przeusniecie && wspolrzedneY[0] < 750 - (HEIGHT * cellSize)){
            for (int i = 0; i < 4; i++) {
                wspolrzedneY[i] = wspolrzedneY[i] + cellSize;
            }
        }


        // obrót i granice planszy X i Y
        if (turn){
            System.out.println(typ + " typ");
            System.out.println(wylosowana + " wylosowana w turn");
            switch (wylosowana){
                case 0:
                    typ += 1;
                    l1.turnFigure(typ,wspolrzedneX,wspolrzedneY,startTurnX,startTurnY);
                    if (typ > l1.iloscPozycji -1){
                        typ = 0;
                        l1.turnFigure(typ,wspolrzedneX,wspolrzedneY,startTurnX,startTurnY);
                    }
                    WIDTH = l1.width;
                    HEIGHT = l1.height;
                    break;
                case 1:
                    //to mie ma typu
                    break;
                case 2:
                    typ += 1;
                    z1.turnFigure(typ,wspolrzedneX,wspolrzedneY,startTurnX,startTurnY);
                    if (typ > z1.iloscPozycji){
                        typ = 0;
                        z1.turnFigure(typ,wspolrzedneX,wspolrzedneY,startTurnX,startTurnY);
                    }
                    WIDTH = z1.Width;
                    HEIGHT = z1.Height;
                    break;
                case 3:
                    typ += 1;
                    patyk.turnFigure(typ,wspolrzedneX,wspolrzedneY,startTurnX,startTurnY);
                    if (typ > patyk.iloscPozycji){
                        typ = 0;
                        patyk.turnFigure(typ,wspolrzedneX,wspolrzedneY,startTurnX,startTurnY);
                    }
                    WIDTH = patyk.Width;
                    HEIGHT = patyk.Height;
                    break;
                case 4:
                    typ += 1;
                    t.turnFigure(typ,wspolrzedneX,wspolrzedneY,startTurnX,startTurnY);
                    if (typ > t.iloscPozycji){
                        typ = 0;
                        t.turnFigure(typ,wspolrzedneX,wspolrzedneY,startTurnX,startTurnY);
                    }
                    WIDTH = t.Width;
                    HEIGHT = t.Height;
                    break;
            }
            turn = false;
            System.out.println(typ);
        }

        //System.out.println(WIDTH + " szer");
        //System.out.println(HEIGHT + " wysokosc");
        checkBoundaries();

    }

    public void checkBoundaries(){
        if (wspolrzedneX[0] > 500 - (cellSize * WIDTH )){
            System.out.println(wspolrzedneX[0] + (cellSize * WIDTH ));
            for (int i = 0; i < 4; i++) {
                wspolrzedneX[i] = wspolrzedneX[i] - cellSize;
            }
        }

        if (wspolrzedneX[0] < 50){
            for (int i = 0; i < 4; i++) {
                wspolrzedneX[i] = wspolrzedneX[i] + cellSize;
            }
        }

    }

    public void wDol() {


        if (wspolrzedneY[0] < 750 - (HEIGHT * cellSize)){
            for (int i = 0; i < 4; i++) {
                wspolrzedneY[i] += 30;
                naDnie = false;
            }
        }else {
            naDnie = true;
        }
    }

    /////////////////////////////////////////////////////////


    public void dodajDolisty(){

        if (naDnie){




            for (int i = 0; i < 4 ; i++) {
                TablicaNaDnie[wspolrzedneX[i]][wspolrzedneY[i]] = 1;

            }


            GetNext();/// tutaj ma byc metoda ktora wezmie mi figure z NEXTA

            losujFigure();
            naDnie = false;
        }

    }

    public void Color(Color c){

        if (c == Color.ORANGE){
            wypelnienieKolorem = 0;
        }
        if (c == Color.pink){
            wypelnienieKolorem = 1;
        }
        if (c == Color.blue){
            wypelnienieKolorem = 2;
        }
        if (c == Color.magenta){
            wypelnienieKolorem = 3;
        }
        if (c == Color.red){
            wypelnienieKolorem = 4;
        }

    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        //System.out.println(code);

        if (code == 37){
            this.left = true;
        }

        if (code == 39){
            this.right = true;
        }

        if (code == 40){
            this.down = true;
        }

        if (code == 32){
            this.turn = true;
            System.out.println("obróc!");
        }
        if (code == 38){
//            System.out.println("x "  + xTab[0]);
//            System.out.println("y "  + yTab[0]);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == 37){
            this.left = false;
        }

        if (code == 39){
            this.right = false;
        }

        if (code == 40){
            this.down = false;
        }

        if (code == 32){
            this.turn = false;
        }
    }


}

