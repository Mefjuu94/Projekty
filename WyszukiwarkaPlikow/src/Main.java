import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame Frame = new JFrame("Wyszukiwarka");
        Panel panel = new Panel();

        Frame.add(panel);
        Frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Frame.setSize(650,500);
        Frame.setVisible(true);



        while (true){
            panel.Metoda();
        }


    }
}
