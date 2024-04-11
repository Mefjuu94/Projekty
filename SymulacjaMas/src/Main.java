import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int width = 800;
        int height = 800;

        JFrame frame = new JFrame("sumulka");
        frame.setSize(1500,800);
        Panel panel = new Panel(frame);

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);


        while (true){
            Thread.sleep(20);
            panel.method();
        }



    }
}