import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        JFrame Frame = new JFrame("Wyszukiwarka");
        Panel panel = new Panel();

        Frame.add(panel);
        Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Frame.setSize(650,500);
        Frame.setVisible(true);
        Frame.setResizable(false);



        while (true){
            panel.Metoda();
        }


    }
}
