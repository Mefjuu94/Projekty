package ANIMATIONS;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BouncingBalls extends JPanel {

    Random los = new Random();
    int Width = 250;
    int Height = 250;
    int pileczki = 15;
    int[] wielkosc =new int[pileczki];
    int[] x = new int[pileczki];
    int[] y = new int[pileczki];

    int[] SzybX = new int[pileczki];
    int[] SzybY = new int[pileczki];

    Color[] color = new Color[pileczki];

    {
        for (int i = 0; i < wielkosc.length ; i++) {
            wielkosc[i] = los.nextInt(70);
        }

        for (int i = 0; i <x.length ; i++) {
            x[i] = los.nextInt(Width - 100);
        }

        for (int i = 0; i < y.length ; i++) {
            y[i] = los.nextInt(Height - 100);
        }

        for (int i = 0; i < SzybX.length ; i++) {
            SzybX[i] = los.nextInt(7);
        }

        for (int i = 0; i < SzybY.length ; i++) {
            SzybY[i] = los.nextInt(7);
        }

        for (int i = 0; i <pileczki ; i++) {
            color[i] = new Color(los.nextInt(256),los.nextInt(256),los.nextInt(256));
        }
    }

    public void move(){

        for (int i = 0; i < pileczki ; i++) {

            x[i] += SzybX[i];
            y[i] += SzybY[i];

            if (x[i] + wielkosc[i] >= getWidth() || x[i] <= 0) {
                SzybX[i] = -SzybX[i];
                color[i] = new Color(los.nextInt(256),los.nextInt(256),los.nextInt(256));
            }
            if (y[i] + wielkosc[i] >= getHeight() || y[i] <= 0) {
                SzybY[i] = -SzybY[i];
                color[i] = new Color(los.nextInt(256),los.nextInt(256),los.nextInt(256));
            }

            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;


        for (int i = 0; i < pileczki ; i++) {
            g2d.setColor(color[i]);
            g2d.fillOval(x[i],y[i],wielkosc[i],wielkosc[i]);
        }

        move();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        repaint();

    }
}
