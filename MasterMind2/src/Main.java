import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        Panel panel = new Panel();

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.setSize(800,800);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

        panel.metoda();

    }
}
