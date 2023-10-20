import Figura.Figura;

import java.awt.*;


public class GameBoard {

    final int boardWidth = 450;
    final int boardHeight = 600;  //30px -> W = 20 kratek na H = 30

    int startNextX = 630;
    int startNextY = 550;

    public final int cellSize = 30;

    int StartXBoard = 50;
    int startBoardY = 150;



    public void draw(Graphics2D g2d) {

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

        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        g2d.setFont(font);
        g2d.drawString("NEXT:", 650, 530);
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
        g2d.drawRect(630, 550, 120, 120);


    }


}
