import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;

public class MENU implements MouseListener {

    ImageIcon logo = new ImageIcon("src/ICONS/logo1.png");

    boolean play = false;
    int y = 800;
    boolean menu = false;


    public void Draw(Graphics2D g2d, JPanel panel, JButton start, JButton exit,JButton load,JButton save) {

            if (y > -240) {
                y -= 1;
            } else {
                y = -240;
            }
            logo.paintIcon(panel, g2d, 0, y);

            if (y == -240) {
                mainMenu(start,exit,load,save);
            }

    }


    public MENU(){

    }

    public void pause(Graphics2D g2d,JPanel panel,JButton exit,JButton load,JButton save){

        if (Panel.State == Panel.STATE.Pause) {
            g2d.setColor(Color.darkGray);
            g2d.fillRect(150, 150, 500, 500);

            load.setBounds(300, 250, 150, 50);
            load.setSize(200, 76);
            load.setVisible(true);
            load.addMouseListener(this);

            save.setBounds(300, 350, 150, 50);
            save.setSize(200, 76);
            save.setVisible(true);
            save.addMouseListener(this);
            
            exit.setBounds(300, 450, 150, 50);
            exit.setSize(200, 76);
            exit.setVisible(true);
            exit.addMouseListener(this);
        }
    }

    public void mainMenu(JButton start, JButton exit,JButton load, JButton save){

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
                load.setVisible(false);
                save.setVisible(false);
            }
        });


        load.setBounds(300,450,150,50);
        load.setSize(200,76);
        load.setVisible(true);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });




        ////dokonczyc save button!!!!!!
        save.setBounds(300, 550,150,50);
        save.setSize(200,76);
        save.setVisible(true);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                e.getSource();
                String zapis = JOptionPane.showInputDialog("nazwij zapis");

                if(e.getSource() == save){
                    try {
                        FileWriter writer = new FileWriter(zapis + ".txt");


                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }



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

    public void save(JButton exit,JButton load,JButton save){


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
