import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MENU implements MouseListener {

    ImageIcon logo = new ImageIcon("src/ICONS/logo1.png");

    boolean play = false;
    int y = 800;
    boolean menu = false;


    public void Draw(Graphics2D g2d, JPanel panel, JButton start, JButton exit) {


            if (y > -240) {
                y -= 1;
            } else {
                y = -240;
            }
            logo.paintIcon(panel, g2d, 0, y);

            if (y == -240) {
                mainMenu(start,exit);
            }

    }




    public MENU(){

    }

    public void mainMenu(JButton start, JButton exit){
        start.setBounds(300,350,150,50);
        start.setSize(200,76);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Uwaga grasz!!!");
                play = true;
                Panel.State = Panel.STATE.GAME;
                start.setVisible(false);
                exit.setVisible(false);
            }
        });
        start.setVisible(true);

        exit.setBounds(300,650,150,50);
        exit.setSize(200,76);
        exit.setVisible(true);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }



    @Override
    public void mouseClicked(MouseEvent e) {

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
