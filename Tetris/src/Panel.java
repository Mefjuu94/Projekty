

import Figura.Figura;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Panel extends JPanel {

    public final int WIDTH = 700;
    public final int HEIGTH = 700;

    GameBoard gb;
    Figura figura;

    Panel() throws IOException {

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

            try {
                figura.rysujFigure(g2d, this);
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
            gb.draw(g2d, this);


        repaint();

    }


}
