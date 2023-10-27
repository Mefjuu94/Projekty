import Figura.Figura;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class GameBoard {

    final int boardWidth = 450;
    final int boardHeight = 600;  //30px -> W = 20 kratek na H = 30

    int startNextX = 530;
    int startNextY = 100;

    public final int cellSize = 30;

    public int StartXBoard = 50;
    public int startBoardY = 50;



    public GameBoard() throws IOException {
    }

    public void draw(Graphics2D g2d, JPanel panel) {

        g2d.setColor(Color.black);
        int x = StartXBoard;
        int y = startBoardY;

        for (int i = 0; i < boardHeight / 30; i++) {

            for (int j = 0; j < boardWidth / 30; j++) {
                g2d.drawRect(x, y, cellSize, cellSize);
                x = x + cellSize;
            }
            y = y + cellSize;
            x = StartXBoard;
        }


        int nextx = startNextX;
        int nexty = startNextY;

        // mniejsza tablica next

        g2d.setColor(Color.WHITE);
        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        g2d.setFont(font);
        g2d.drawString("NEXT:", 550, 80);
        g2d.setColor(Color.black);
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {
                g2d.drawRect(nextx, nexty, cellSize, cellSize);
                nextx = nextx + cellSize;
            }
            nexty = nexty + cellSize;
            nextx = startNextX;
        }

        g2d.setColor(Color.white);
        g2d.drawRect(StartXBoard, startBoardY, 30 * 15, 30 * 20);

        g2d.setColor(Color.white);
        g2d.drawRect(530, 100, 120, 120);
    }


}
