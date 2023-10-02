package Figury;

import java.awt.*;
import java.util.Random;

public class Z {

    int x = 50+ (30*6);
    int y = 180;
    int Height;
    int Width;

    int xL[] = new int[4];
    int yL[] = new int[4];
    Color c = Color.red;

    Z(){

    }

    Random random = new Random();
    int iloscPozycji = 4;
    int typ = random.nextInt(iloscPozycji);;

    public void wyLosujTyp(){
        typ = random.nextInt(iloscPozycji);
        x = 50+ (30*6);
        y = 180;

        if (typ == 0){
            xL[0] = x;              //     ooo
            xL[1] = x+30;          //        ooo
            xL[2] = x + 30;
            xL[3] = x+ 60;

            yL[0] = y ;
            yL[1] = y ;
            yL[2] = y +30;
            yL[3] = y + 30;

            Width = 3;
            Height = 2;
        }

        if (typ == 1) {
            xL[0] = x;          //   o
            xL[1] = x;           //  ooo
            xL[2] = x +30;        //    o
            xL[3] = x + 30;

            yL[0] = y;
            yL[1] = y+30;
            yL[2] = y +30;
            yL[3] = y + 60;

            Width = 2;
            Height = 3;

        }
        if (typ == 2) {
            xL[0] = x;
            xL[1] = x+30;      //  ooo
            xL[2] = x+30;   //   ooo
            xL[3] = x+ 60;  //

            yL[0] = y+30;
            yL[1] = y + 30;
            yL[2] = y;
            yL[3] = y ;

            Width = 3;
            Height = 1;

        }
        if (typ == 3) {

            xL[0] = x;
            xL[1] = x;            //        o
            xL[2] = x + 30;      //      oo o
            xL[3] = x + 30;      //      o

            yL[0] = y+60;
            yL[1] = y+ 30;
            yL[2] = y + 30;
            yL[3] = y ;
            Width = 2;
            Height = 1;

        }
    }

    public void narysujTyp(int typ){

        x = 50+ (30*6);
        y = 180;

        if (typ == 0){
            xL[0] = x;              //     ooo
            xL[1] = x+30;          //        ooo
            xL[2] = x + 30;
            xL[3] = x+ 60;

            yL[0] = y ;
            yL[1] = y ;
            yL[2] = y +30;
            yL[3] = y + 30;

            Width = 3;
            Height = 2;
        }

        if (typ == 1) {
            xL[0] = x;          //   o
            xL[1] = x;           //  ooo
            xL[2] = x +30;        //    o
            xL[3] = x + 30;

            yL[0] = y;
            yL[1] = y+30;
            yL[2] = y +30;
            yL[3] = y + 60;

            Width = 2;
            Height = 3;

        }
        if (typ == 2) {
            xL[0] = x;
            xL[1] = x+30;      //  ooo
            xL[2] = x+30;   //   ooo
            xL[3] = x+ 60;  //

            yL[0] = y+30;
            yL[1] = y + 30;
            yL[2] = y;
            yL[3] = y ;

            Width = 3;
            Height = 1;

        }
        if (typ == 3) {

            xL[0] = x;
            xL[1] = x;            //        o
            xL[2] = x + 30;      //      oo o
            xL[3] = x + 30;      //      o

            yL[0] = y+60;
            yL[1] = y+ 30;
            yL[2] = y + 30;
            yL[3] = y ;
            Width = 2;
            Height = 1;

        }
    }

    public void turnFigure(int typ, int[] xL, int[] yL,int newStartX,int newStartY){
        if (typ == 0){
            xL[0] = newStartX;              //     ooo
            xL[1] = newStartX+30;          //        ooo
            xL[2] = newStartX + 30;
            xL[3] = newStartX+ 60;

            yL[0] = newStartY ;
            yL[1] = newStartY ;
            yL[2] = newStartY +30;
            yL[3] = newStartY + 30;

            Width = 3;
            Height = 2;
        }

        if (typ == 1) {
            xL[0] = newStartX;          //   o
            xL[1] = newStartX;           //  ooo
            xL[2] = newStartX +30;        //    o
            xL[3] = newStartX + 30;

            yL[0] = newStartY;
            yL[1] = newStartY+30;
            yL[2] = newStartY +30;
            yL[3] = newStartY + 60;

            Width = 2;
            Height = 3;

        }
        if (typ == 2) {
            xL[0] = newStartX;
            xL[1] = newStartX + 30;      //  ooo
            xL[2] = newStartX + 30;   //   ooo
            xL[3] = newStartX + 60;  //

            yL[0] = newStartY + 30;
            yL[1] = newStartY + 30;
            yL[2] = newStartY;
            yL[3] = newStartY ;

            Width = 3;
            Height = 1;

        }
        if (typ == 3) {

            xL[0] = newStartX;
            xL[1] = newStartX;            //        o
            xL[2] = newStartX + 30;      //      oo o
            xL[3] = newStartX + 30;      //      o

            yL[0] = newStartY+60;
            yL[1] = newStartY+ 30;
            yL[2] = newStartY + 30;
            yL[3] = newStartY ;
            Width = 2;
            Height = 1;

        }
    }


}
