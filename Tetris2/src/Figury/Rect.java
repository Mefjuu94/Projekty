package Figury;

import java.awt.*;

public class Rect {

    int x = 230;
    int y = 180;


    int Width = 2;
    int Height = 2;

    int[] xL = new int[4];
    int[] yL= new int[4];
    Color c = Color.blue;

    int typ = 0;

    public Rect(){

    }

    {
        xL[0] = x;
        xL[1] = x+30;
        xL[2] = x;
        xL[3] = x+30;

        yL[0] = y;
        yL[1] = y;
        yL[2] = y+30;
        yL[3] = y+30;
    }
}
