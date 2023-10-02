package Figury;

import java.awt.*;
import java.util.Random;

public class L1 {
    int x = 50+ (30*6);
    int y = 180;

    int height;
    int width;

    int xL[] = new int[4];
    int yL[] = new int[4];
    Color c = Color.ORANGE;

    Random random = new Random();
    int iloscPozycji = 4;
    int typ = random.nextInt(iloscPozycji); ;


    public void wylosujTyp(){
        typ = random.nextInt(iloscPozycji);
        x = 50+ (30*6);
        y = 180;
        if (typ == 0){
            xL[0] = x;          //L
            xL[1] = x;
            xL[2] = x;
            xL[3] = x+ 30;

            yL[0] = y;
            yL[1] = y+30;
            yL[2] = y +60;
            yL[3] = y + 60;

            width = 2;
            height = 3;
        }

        if (typ == 1) {
            xL[0] = x;          // _|
            xL[1] = x;
            xL[2] = x;
            xL[3] = x- 30;

            yL[0] = y;
            yL[1] = y+30;
            yL[2] = y +60;
            yL[3] = y + 60;

            width = 2;
            height = 3;

        }
        if (typ == 2) {
            xL[0] = x;
            xL[1] = x;      //  o o o o o
            xL[2] = x+30;   //  o
            xL[3] = x+ 60;  //  o

            yL[0] = y;
            yL[1] = y+30;
            yL[2] = y ;
            yL[3] = y ;

            width = 3;
            height = 2;

        }
        if (typ == 3) {

            xL[0] = x;
            xL[1] = x+30;       //  o   o   o
            xL[2] = x +60;      //         o
            xL[3] = x+ 60;      //         o

            yL[0] = y;
            yL[1] = y;
            yL[2] = y ;
            yL[3] = y + 30;
            width = 3;
            height = 2;
        }
    }


    public void narysujTyp(int typ){
        typ = random.nextInt(iloscPozycji);
        x = 50+ (30*6);
        y = 180;
        if (typ == 0){
            xL[0] = x;          //L
            xL[1] = x;
            xL[2] = x;
            xL[3] = x+ 30;

            yL[0] = y;
            yL[1] = y+30;
            yL[2] = y +60;
            yL[3] = y + 60;

            width = 2;
            height = 3;
        }

        if (typ == 1) {
            xL[0] = x;          // _|
            xL[1] = x;
            xL[2] = x;
            xL[3] = x- 30;

            yL[0] = y;
            yL[1] = y+30;
            yL[2] = y +60;
            yL[3] = y + 60;

            width = 2;
            height = 3;

        }
        if (typ == 2) {
            xL[0] = x;
            xL[1] = x;      //  o o o o o
            xL[2] = x+30;   //  o
            xL[3] = x+ 60;  //  o

            yL[0] = y;
            yL[1] = y+30;
            yL[2] = y ;
            yL[3] = y ;

            width = 3;
            height = 2;

        }
        if (typ == 3) {

            xL[0] = x;
            xL[1] = x+30;       //  o   o   o
            xL[2] = x +60;      //         o
            xL[3] = x+ 60;      //         o

            yL[0] = y;
            yL[1] = y;
            yL[2] = y ;
            yL[3] = y + 30;
            width = 3;
            height = 2;
        }
    }

    public void turnFigure(int typ, int[] xL, int[] yL,int newStartX,int newStartY){
        if (typ == 0){
            xL[0] = newStartX;          //L
            xL[1] = newStartX;
            xL[2] = newStartX;
            xL[3] = newStartX+ 30;

            yL[0] = newStartY;
            yL[1] = newStartY+30;
            yL[2] = newStartY +60;
            yL[3] = newStartY + 60;

            width = 2;
            height = 3;
        }

        if (typ == 1) {
            xL[0] = newStartX;          // _|
            xL[1] = newStartX+30;
            xL[2] = newStartX+30;
            xL[3] = newStartX+ 30;

            yL[0] = newStartY + 60;
            yL[1] = newStartY+30;
            yL[2] = newStartY +60;
            yL[3] = newStartY;

            width = 2;
            height = 3;

        }
        if (typ == 2) {
            xL[0] = newStartX;
            xL[1] = newStartX;      //  o o o o o
            xL[2] = newStartX+30;   //  o
            xL[3] = newStartX+ 60;  //  o

            yL[0] = newStartY;
            yL[1] = newStartY+30;
            yL[2] = newStartY ;
            yL[3] = newStartY ;

            width = 3;
            height = 2;

        }
        if (typ == 3) {

            xL[0] = newStartX;
            xL[1] = newStartX+30;       //  o   o   o
            xL[2] = newStartX +60;      //         o
            xL[3] = newStartX+ 60;      //         o

            yL[0] = newStartY;
            yL[1] = newStartY;
            yL[2] = newStartY ;
            yL[3] = newStartY + 30;
            width = 3;
            height = 2;

        }
    }



    L1(){}

}