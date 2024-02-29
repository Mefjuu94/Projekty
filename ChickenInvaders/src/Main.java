import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JFrame ramka = new JFrame("Chicken Invaders");
        Panel panel = new Panel();
        ramka.add(panel);
        //ramka.setIconImage();
        ramka.setVisible(true);
        ramka.setResizable(false);
        ramka.setDefaultCloseOperation(3);
        ramka.pack();

        while(true) {
            panel.move();
            Thread.sleep(20L);
        }
    }
}

