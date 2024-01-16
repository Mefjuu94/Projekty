import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Locale;


public class ChatClient {
    public static Socket socket;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(new Locale("pl", "PL"));

        String host = JOptionPane.showInputDialog("Podaj ip Hosta");

        if (host.equals("")) {
            socket = new Socket("localhost", 9999);
            Panel panel = new Panel(socket);
        } else {
            socket = new Socket(host, 9999);
            Panel panel = new Panel(socket);
        }





    }

}
