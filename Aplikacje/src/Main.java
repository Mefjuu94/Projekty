import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame ramka = new JFrame("Moje Aplikacje");
        Panel panel = new Panel();

        panel.setBackground(Color.black);
        ramka.setLocation(100,200);

        ramka.add(panel);
        ramka.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ramka.setSize(700,500);

        ramka.setVisible(true);



    }
}
