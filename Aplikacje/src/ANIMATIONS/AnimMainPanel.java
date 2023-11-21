package ANIMATIONS;
import javax.swing.*;

public class AnimMainPanel extends JPanel{

    public JButton[] buttons = new JButton[2];

    public AnimMainPanel() {

        int xButton = 100;
        int yButton = 50;

        for (int i = 0; i < buttons.length ; i++) {
            buttons[i] = new JButton();

            buttons[i].setBounds(xButton,yButton,150,30);
            buttons[i].setVisible(true);

            xButton += 200;
            if (xButton > 650){
                yButton+= 50;
                xButton = 100;
            }

        }

        {
            buttons[0].setText("Bouncing Balls");
            buttons[0].setToolTipText("colorful balls bouncing off the edge of the screen");

            buttons[1].setText("Shield");
            buttons[1].setToolTipText("A shield with the number of circles given in the argument");
        }


    }


}
