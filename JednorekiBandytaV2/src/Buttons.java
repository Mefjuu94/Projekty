import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Buttons implements MouseListener {

    JButton mutliChance = new JButton("MultiChance");

    JButton autoRoll = new JButton("autoRoll");
    ImageIcon autoRol = new ImageIcon("src/image/autoRoll.png");
    ImageIcon autoRol1 = new ImageIcon("src/image/autoRoll1.png");
    ImageIcon autoRol2 = new ImageIcon("src/image/autoRoll2.png");
    ImageIcon autoRol3 = new ImageIcon("src/image/autoRoll3.png");

    JButton stopRoll = new JButton("stopRoll");
    ImageIcon stopRol = new ImageIcon("src/image/stopRol.png");

    JButton Roll = new JButton("Roll");
    ImageIcon startRoll = new ImageIcon("src/image/Start.png");

    JRadioButton zapis = new JRadioButton("save to Json");

    boolean multi = false;

    boolean ROLL = false;
    boolean STOP = false;

    boolean AutoRoll = false;

    Images images;

    int GamesCOUNTER = 0;
    int ileLosowan = 2; // ( 2 = 3 losowania)

    Buttons() {
        mutliChance.addMouseListener(this);


        autoRoll.addMouseListener(this);
        autoRoll.setIcon(autoRol);

        stopRoll.addMouseListener(this);
        stopRoll.setIcon(stopRol);

        Roll.addMouseListener(this);
        Roll.setIcon(startRoll);

        zapis.addMouseListener(this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == mutliChance ){
            System.out.println("Multichance!!!!");
            multi = !multi;
            System.out.println("Multichance " + multi);

        }
        if (e.getSource() == Roll ){
            System.out.println("ROLL");
            ROLL = true;
            STOP = false;
            System.out.println("Roll = " + ROLL);
            System.out.println("Stop = " + STOP);

        }
        if (e.getSource() == stopRoll ){

            System.out.println("StopRoll");
            STOP = true;
            ROLL = false;
            System.out.println("Roll = " + ROLL);
            System.out.println("Stop = " + STOP);
        }
        if (e.getSource() == autoRoll ){

            System.out.println("AUTORoll");
            AutoRoll = !AutoRoll;
            System.out.println("AUTORoll = " + AutoRoll);
            if (AutoRoll) {
                System.out.println("AUTORoll enable");
            }else {
                System.out.println("AUTORoll disable");
                autoRoll.setIcon(autoRol);
                GamesCOUNTER = 0;
            }

        }

        if (zapis.isSelected()){
            System.out.println("save to Json enabled");
        }else {
            System.out.println("save to Json disabled");
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
