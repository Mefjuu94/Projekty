package Shapes;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Shape {
    int[][] tab = new int[4][4];
    Color color;
    Random random = new Random();

    public Shape() {
        this.color = Color.pink; // randomowy numer
    }

    static Shape createShape() {

        Shape randomShape = new LShape(); // wylosuj





        return randomShape;
    }

    public void rotateLeft() {

    }