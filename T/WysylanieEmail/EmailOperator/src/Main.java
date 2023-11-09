import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame ramka = new JFrame("Email operator");
        Panel panel = new Panel();

        panel.setLayout(null);
        ramka.add(panel);
        ramka.setResizable(false);;
        ramka.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ramka.pack();
        ramka.setVisible(true);


    }
}
