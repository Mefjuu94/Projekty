

import Figura.Figura;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    public final int WIDTH = 700;
    public final int HEIGTH = 700;

    GameBoard gb;
    Figura figura;

    Panel() {

        this.setBackground(Color.DARK_GRAY);
        gb = new GameBoard();
        figura = new Figura();

        this.addKeyListener(this.figura);
        this.setFocusable(true);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGTH);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        //naniesienie czarnego kwadratu jako tło tablicy
        g2d.setColor(Color.black);
        g2d.fillRect(gb.StartXBoard, gb.startBoardY, gb.boardWidth, gb.boardHeight);

        if (!figura.gameOver) {
            try {
                figura.rysujFigure(g2d, this);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gb.draw(g2d, this);
        } else {
            g2d.setColor(Color.white);
            Font font = new Font(Font.SERIF, Font.BOLD, 50);
            g2d.setFont(font);
            g2d.drawString("Score: " + figura.result, 100, 150);
            g2d.drawString("Lines: " + figura.scoreLineCounter, 100, 250 + figura.scoreY);
            g2d.drawString("Level: " + figura.level, 100, 350 + (figura.scoreY * 2));
        }


        repaint();
    }


}
