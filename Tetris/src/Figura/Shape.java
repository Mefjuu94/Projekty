package Figura;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Random;

public abstract class Shape {

    int[][] tab = new int[4][4];
    static Color[] colors = {Color.ORANGE,Color.BLUE,Color.magenta,Color.PINK};
    int color;

    public static Shape CreateShape(){
        Random random = new Random();
        Shape randomShape = new Shape() {};

        int los = random.nextInt(6);
        int randomColor = random.nextInt(colors.length);
        if (los ==0){
            randomShape = new LShape();
        }
        if (los ==1){
            randomShape = new JShape();
        }
        if (los ==2){
            randomShape = new IShape();
        }
        if (los ==3){
            randomShape = new ZShape();
        }
        if (los ==4){
            randomShape = new TShape();
        }
        if (los ==5){
            randomShape = new RShape();
        }

        randomShape.color = randomColor;

        return randomShape;
    }

    public void rotate(){

            final int M = tab.length;
            final int N = tab[0].length;
            int[][] ret = new int[N][M];
            for (int r = 0; r < M; r++) {
                for (int c = 0; c < N; c++) {
                    ret[c][M-1-r] = tab[r][c];
                }
            }
            tab = ret;
        }

}
