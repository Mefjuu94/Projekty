

import Figura.Figura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Panel extends JPanel implements MouseListener {

    public final int WIDTH = 700;
    public final int HEIGTH = 700;

    GameBoard gb;
    Figura figura;
    JButton restart = new JButton("New Game");

    Panel() throws IOException {

        this.setBackground(Color.DARK_GRAY);
        gb = new GameBoard();
        figura = new Figura();

        this.add(restart);
        restart.setVisible(false);
        restart.addMouseListener(this);


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
                restart.setVisible(false);
                try {
                    figura.rysujFigure(g2d, this);
                    gb.draw(g2d, this);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                try {
                    figura.saveScore(this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                gb.draw(g2d, this);
                restart.setBounds(530,500,150,40);
                restart.setVisible(true);
                restart.setFocusable(true);
                try {
                    figura.rysujFigure(g2d, this);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }

            }



        repaint();

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println();

        if (e.getSource() == restart){
            System.out.println("klik!");
            figura.gameOver = false;
            figura.restartGame = true;
            System.out.println(figura.gameOver);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
