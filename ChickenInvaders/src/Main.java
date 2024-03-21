import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JFrame ramka = new JFrame("Chicken Invaders");
        Panel panel = new Panel(ramka);
        ramka.add(panel);
        //ramka.setIconImage();
        ramka.setVisible(true);
        ramka.setResizable(true);
        ramka.setDefaultCloseOperation(3);
        ramka.pack();

        while(true) {
            panel.move();
            Thread.sleep(20L);
        }
    }
}

