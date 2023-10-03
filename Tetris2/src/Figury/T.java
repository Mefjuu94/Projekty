package Figury;

import java.awt.*;
import java.util.Random;

public class T {

    int x = 230;
    int y = 180;


    int Width = 3;
    int Height = 2;

    int[] xL = new int[4];
    int[] yL= new int[4];
    Color c = new Color(178,102,255);

    Random random = new Random();
    int iloscPozycji = 3;
    int typ = random.nextInt(iloscPozycji);;

    public T(){

    }

    public void wylosujTyp(){
        typ = random.nextInt(iloscPozycji);
        x = 50+ (30*6);
        y = 180;

        if (typ == 0){
            xL[0] = x;              //     o
            xL[1] = x+30;          //    ooooo
            xL[2] = x + 30;
            xL[3] = x+ 60;

            yL[0] = y + 30;
            yL[1] = y +30;
            yL[2] = y ;
            yL[3] = y + 30;

            Width = 3;
            Height = 2;
        }

        if (typ == 1) {
            xL[0] = x;          //   o
            xL[1] = x;           //  oo
            xL[2] = x+30;        //  o
            xL[3] = x;

            yL[0] = y;
            yL[1] = y+30;
            yL[2] = y +30;
            yL[3] = y + 60;

            Width = 2;
            Height = 3;

        }
        if (typ == 2) {
            xL[0] = x;
            xL[1] = x+30;      //  o o o o o
            xL[2] = x+30;   //      o
            xL[3] = x+ 60;  //

            yL[0] = y;
            yL[1] = y;
            yL[2] = y+30 ;
            yL[3] = y ;

            Width = 3;
            Height = 2;

        }
        if (typ == 3) {

            xL[0] = x;
            xL[1] = x + 30;       //         o
            xL[2] = x + 30;      //      oo o
            xL[3] = x + 30;      //         o

            yL[0] = y + 30;
            yL[1] = y;
            yL[2] = y + 30;
            yL[3] = y + 60;
            Width = 2;
            Height = 2;
        }
    }


    public void narysujTyp(int typ){
        x = 50+ (30*6);
        y = 180;

        if (typ == 0){
            xL[0] = x;              //     o
            xL[1] = x+30;          //    ooooo
            xL[2] = x + 30;
            xL[3] = x+ 60;

            yL[0] = y + 30;
            yL[1] = y +30;
            yL[2] = y ;
            yL[3] = y + 30;

            Width = 3;
            Height = 2;
        }

        if (typ == 1) {
            xL[0] = x;          //   o
            xL[1] = x;           //  oo
            xL[2] = x+30;        //  o
            xL[3] = x;

            yL[0] = y;
            yL[1] = y+30;
            yL[2] = y +30;
            yL[3] = y + 60;

            Width = 2;
            Height = 3;

        }
        if (typ == 2) {
            xL[0] = x;
            xL[1] = x+30;      //  o o o o o
            xL[2] = x+30;   //      o
            xL[3] = x+ 60;  //

            yL[0] = y;
            yL[1] = y;
            yL[2] = y+30 ;
            yL[3] = y ;

            Width = 3;
            Height = 2;

        }
        if (typ == 3) {

            xL[0] = x;
            xL[1] = x + 30;       //         o
            xL[2] = x + 30;      //      oo o
            xL[3] = x + 30;      //         o

            yL[0] = y + 30;
            yL[1] = y;
            yL[2] = y + 30;
            yL[3] = y + 60;
            Width = 2;
            Height = 2;
        }
    }






    public void turnFigure(int typ, int[] xL, int[] yL,int newStartX,int newStartY){
        if (typ == 0){
            xL[0] = newStartX;              //     o
            xL[1] = newStartX+30;          //    ooooo
            xL[2] = newStartX + 30;
            xL[3] = newStartX+ 60;

            yL[0] = newStartY + 30;
            yL[1] = newStartY +30;
            yL[2] = newStartY ;
            yL[3] = newStartY + 30;

            Width = 3;
            Height = 2;
            System.out.println("tryp 0 !!!!");
        }

        if (typ == 1) {
            xL[0] = newStartX;          //   o
            xL[1] = newStartX;           //  oo
            xL[2] = newStartX+30;        //  o
            xL[3] = newStartX;

            yL[0] = newStartY;
            yL[1] = newStartY+30;
            yL[2] = newStartY +30;
            yL[3] = newStartY + 60;

            Width = 2;
            Height = 3;

        }
        if (typ == 2) {
            xL[0] = newStartX;
            xL[1] = newStartX+30;      //  o o o o o
            xL[2] = newStartX+30;   //      o
            xL[3] = newStartX+ 60;  //

            yL[0] = newStartY;
            yL[1] = newStartY;
            yL[2] = newStartY+30 ;
            yL[3] = newStartY ;

            Width = 3;
            Height = 2;

        }
        if (typ == 3) {

            xL[0] = newStartX;
            xL[1] = newStartX + 30;       //         o
            xL[2] = newStartX + 30;      //      oo o
            xL[3] = newStartX + 30;      //         o

            yL[0] = newStartY + 30;
            yL[1] = newStartY;
            yL[2] = newStartY + 30;
            yL[3] = newStartY + 60;
            Width = 2;
            Height = 2;

        }
    }



        //uzupelnic bp bierzse y  od + 30

}