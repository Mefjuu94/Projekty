

import Figura.Figura;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    public final int WIDTH = 1000;
    public final int HEIGTH = 800;

    GameBoard gb;
    //Block block;
    Figura figura;


    Panel() {

        this.setBackground(Color.darkGray);
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


        try {
            figura.rysujFigure(g2d, this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        gb.draw(g2d);


        repaint();
    }


}
