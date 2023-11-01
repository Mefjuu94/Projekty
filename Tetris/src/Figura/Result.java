package Figura;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Result {
    private int scoreX = 550;
    private int scoreY = 350;

    public Result() {

    }

    public void draw(Graphics2D g2d, long result, int scoreLineCounter, int level, int dropSpeed) {
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        g2d.setFont(font);

        g2d.setColor(Color.white);
        g2d.drawString("Score: " + result, scoreX, scoreY);
        g2d.drawString("Lines: " + scoreLineCounter, scoreX, scoreY + 40);
        g2d.drawString("Level: " + level, scoreX, scoreY + 80);
        g2d.drawString("Speed: " + dropSpeed, scoreX, scoreY + 120);
    }
}
