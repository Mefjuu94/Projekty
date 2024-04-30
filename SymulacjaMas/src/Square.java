import javax.swing.*;
import java.awt.*;

class Square {
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int x;
    private int y;
    private int width;
    private int height;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private Color color;

    public JButton getSetColorOfsquareButton() {
        return setColorOfsquareButton;
    }

    public void setSetColorOfsquareButton(JButton setColorOfsquareButton) {
        this.setColorOfsquareButton = setColorOfsquareButton;
    }

    JButton setColorOfsquareButton;


    public Square(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}