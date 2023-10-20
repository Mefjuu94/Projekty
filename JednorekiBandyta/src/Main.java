import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {

        JFrame ramka = new JFrame("Jednoreki Bandyta");
        Panel panel = new Panel();

        ramka.add(panel);
        ramka.setVisible(true);
        ramka.setResizable(false);
        ramka.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ramka.pack();

        while (true){
            panel.metoda();
        }




    }
}
