import ANIMATIONS.AnimMainPanel;
import ANIMATIONS.BouncingBalls;
import ANIMATIONS.Shield;
import APPLICATIONS.AppMainPanel;
import GAMES.GamesMainPanel;
import APPLICATIONS.Search.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Panel extends JPanel implements MouseListener {

    ButtonsModules bm = new ButtonsModules();
    AppMainPanel appMainPanel = new AppMainPanel();
    GamesMainPanel gamesMainPanel = new GamesMainPanel();
    AnimMainPanel animMainPanel = new AnimMainPanel();

    JTabbedPane tabbedPane = new JTabbedPane();


    Panel() {
        this.setLayout(null);
//
//        this.add(tabbedPane);
//        tabbedPane.addTab("Aplications",new JLabel("Label1"));
//        tabbedPane.addTab("Games",new JLabel("Label3"));
//        tabbedPane.addTab("Animations",new JLabel("Label2"));
//        tabbedPane.setVisible(true);

        add(bm.app);
        bm.app.addMouseListener(this);

        add(bm.games);
        bm.games.addMouseListener(this);

        add(bm.anim);
        bm.anim.addMouseListener(this);

        this.add(bm.returnToMenu);
        bm.returnToMenu.addMouseListener(this);

    }

    enum STATE {
        MENU,
        APPLICATIONS,
        GAMES,
        ANIMATIONS,
    }

    STATE state = STATE.MENU;


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        if (state == STATE.MENU) {
            bm.anim.setVisible(true);
            bm.app.setVisible(true);
            bm.games.setVisible(true);

            for (int i = 0; i < animMainPanel.buttons.length; i++) {
                animMainPanel.buttons[i].setVisible(false);
            }

            for (int i = 0; i < appMainPanel.buttons.length; i++) {
                appMainPanel.buttons[i].setVisible(false);
            }

//            for (int i = 0; i < gamesMainPanel.buttons.length; i++) {
//                gamesMainPanel.buttons[i].setVisible(false);
//            }
            bm.returnToMenu.setVisible(false);

        } else if (state == STATE.ANIMATIONS) {

            bm.anim.setVisible(false);
            bm.app.setVisible(false);
            bm.games.setVisible(false);
            bm.returnToMenu.setVisible(true);

            for (int i = 0; i < animMainPanel.buttons.length; i++) {
                animMainPanel.buttons[i].setVisible(true);
            }
        } else if (state == STATE.GAMES) {

            bm.returnToMenu.setVisible(true);
            bm.anim.setVisible(false);
            bm.app.setVisible(false);
            bm.games.setVisible(false);

        } else if (state == STATE.APPLICATIONS) {

            bm.returnToMenu.setVisible(true);
            bm.anim.setVisible(false);
            bm.app.setVisible(false);
            bm.games.setVisible(false);

            for (int i = 0; i < appMainPanel.buttons.length; i++) {
                appMainPanel.buttons[i].setVisible(true);
            }
        }




    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        /////////////////////Gry////////////////////////
        if (e.getSource() == bm.games) {
            System.out.println("klik w GRY");
            state = STATE.GAMES;
            System.out.println("state = " + state);

        }

        /////////////////////ANIMAcje//////////////////
        if (e.getSource() == bm.anim) {
            System.out.println("klik w Animacje");
            state = STATE.ANIMATIONS;
            System.out.println("state = " + state);

            for (int i = 0; i < animMainPanel.buttons.length; i++) {
                this.add(animMainPanel.buttons[i]);
                animMainPanel.buttons[i].addMouseListener(this);
            }
        }

        JFrame animationFrame = new JFrame();
        animationFrame.setVisible(false);

        if (e.getSource() == animMainPanel.buttons[0]) {
            BouncingBalls bouncingBalls = new BouncingBalls();
            settingsOfFrame(animationFrame,300,300,bouncingBalls,0);

        }
        if (e.getSource() == animMainPanel.buttons[1]) {
            Shield shield = new Shield();
            settingsOfFrame(animationFrame,300,300,shield,1);

        }
        /////////////////Aplickacje///////////////////////////////
        if (e.getSource() == bm.app) {
            System.out.println("klik w APKI");
            state = STATE.APPLICATIONS;
            System.out.println("state = " + state);

            for (int i = 0; i < appMainPanel.buttons.length; i++) {
                this.add(appMainPanel.buttons[i]);
                appMainPanel.buttons[i].addMouseListener(this);
            }
        }

        JFrame aplicationFrame = new JFrame();
        aplicationFrame.setVisible(false);

        if (e.getSource() == appMainPanel.buttons[0]) {
            APPLICATIONS.Search.Panel wyszukiwarka = new APPLICATIONS.Search.Panel();
            settingsOfFrame(aplicationFrame,650,500,wyszukiwarka,0);
        }
        if (e.getSource() == appMainPanel.buttons[1]) {
            APPLICATIONS.EMAIL.Panel email = new APPLICATIONS.EMAIL.Panel();
            settingsOfFrame(aplicationFrame,500,550,email,1);
        }

        if (e.getSource() == appMainPanel.buttons[2]) {


        }

        //////////////////////////////////////////////////////////////////////////////////

        if (e.getSource() == bm.returnToMenu) {
            state = STATE.MENU;
            System.out.println("state = " + state);
        }

        repaint();
    }

    private void settingsOfFrame(JFrame frame,int Width,int Height,Component content,int indexOfContent){
        frame.setSize(Width,Height);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocation(800, 500);
        frame.add(content);
        frame.setTitle(appMainPanel.buttons[indexOfContent].getText());
        frame.setVisible(true);

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
