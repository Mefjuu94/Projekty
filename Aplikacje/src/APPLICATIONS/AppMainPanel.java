package APPLICATIONS;

import javax.swing.*;

public class AppMainPanel extends JPanel {

    public JButton[] buttons = new JButton[3];

    public AppMainPanel() {

        int xButton = 100;
        int yButton = 50;

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();

            buttons[i].setBounds(xButton, yButton, 150, 30);
            buttons[i].setVisible(true);

            xButton += 200;
            if (xButton > 650) {
                yButton += 50;
                xButton = 100;
            }

        }

        {
            buttons[0].setText("Searching");
            buttons[0].setToolTipText("Searching files sepecified in scope");

            buttons[1].setText("Email");
            buttons[1].setToolTipText("Can Send and recive email");

            buttons[2].setText("Chat");
            buttons[2].setToolTipText("just simple chat");

        }

    }
}
