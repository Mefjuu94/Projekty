package APPLICATIONS.CHAT.CHAT2USER;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Chat {
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("My Chat2");
        Panel panel = new Panel();

        panel.setLayout(null);

        frame.add(panel);
        frame.setResizable(false);
        frame.setSize(400,300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        while(true){
            panel.getMessage();
        }

    }
}
