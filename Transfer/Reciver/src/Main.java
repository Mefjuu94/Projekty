import javax.swing.*;

public class Main {

    public static String filesName= "";
    public static void main(String[] args) {

        JFrame frame = new JFrame("reciver");
        Reciver panel = new Reciver();

        //panel.setLayout(new GridLayout(2,0));

        frame.add(panel);
        frame.setSize(400,300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JTextArea textArea= new JTextArea();
        textArea.setBounds(20,20,360,260);
        textArea.setEditable(false);
        textArea.setVisible(true);
        panel.add(textArea);


        while (true){
            panel.reciveFile();
            textArea.append(filesName + "\n");
        }


    }
}
