package Figury;

import java.awt.*;
import java.util.Random;

public class I {

    int x = 230;
    int y = 180;


    int Width ;
    int Height;

    int[] xL = new int[4];
    int[] yL = new int[4];
    Color c = Color.pink;

    public I() {
    }

    Random random = new Random();
    int iloscPozycji = 2;
    int typ = random.nextInt(iloscPozycji);




    public void wylosujTyp() {
        typ = random.nextInt(iloscPozycji);
        x = 50+ (30*6);
        y = 180;
        if (typ == 0) {
            xL[0] = x;          //  |
            xL[1] = x;
            xL[2] = x;
            xL[3] = x;

            yL[0] = y;
            yL[1] = y + 30;
            yL[2] = y + 60;
            yL[3] = y + 90;

            Width = 1;
            Height = 4;
        }

        if (typ == 1) {
            xL[0] = x;          //  -----------
            xL[1] = x + 30;
            xL[2] = x + 60;
            xL[3] = x + 90;

            yL[0] = y;
            yL[1] = y;
            yL[2] = y;
            yL[3] = y;

            Width = 4;
            Height = 1;
        }
    }


    public void narysujTyp(int typ) {

        x = 50+ (30*6);
        y = 180;
        if (typ == 0) {
            xL[0] = x;          //  |
            xL[1] = x;
            xL[2] = x;
            xL[3] = x;

            yL[0] = y;
            yL[1] = y + 30;
            yL[2] = y + 60;
            yL[3] = y + 90;

            Width = 1;
            Height = 4;
        }

        if (typ == 1) {
            xL[0] = x;          //  -----------
            xL[1] = x + 30;
            xL[2] = x + 60;
            xL[3] = x + 90;

            yL[0] = y;
            yL[1] = y;
            yL[2] = y;
            yL[3] = y;

            Width = 4;
            Height = 1;
        }
    }

    public void turnFigure(int typ, int[] xL, int[] yL,int newStartX,int newStartY){
        if (typ == 0) {
            xL[0] = newStartX;          //  |
            xL[1] = newStartX;
            xL[2] = newStartX;
            xL[3] = newStartX;

            yL[0] = newStartY;
            yL[1] = newStartY + 30;
            yL[2] = newStartY + 60;
            yL[3] = newStartY + 90;

            Width = 1;
            Height = 4;
        }

        if (typ == 1) {
            xL[0] = newStartX;          //  -----------
            xL[1] = newStartX + 30;
            xL[2] = newStartX + 60;
            xL[3] = newStartX + 90;

            yL[0] = newStartY;
            yL[1] = newStartY;
            yL[2] = newStartY;
            yL[3] = newStartY;

            Width = 4;
            Height = 1;
        }
    }
}
