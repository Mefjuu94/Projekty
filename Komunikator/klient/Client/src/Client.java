import UserSettings.LoginPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.Locale;


public class Client {
    public static Socket socket;
    static String host = "";

    public static void main(String[] args) throws IOException {
        Locale.setDefault(new Locale("pl", "PL"));


        LoginPanel loginPanel = new LoginPanel();

        // Dodanie nasłuchiwania na zdarzenie zamknięcia okna
        loginPanel.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    if (loginPanel.LocalHost != null) {
                        socket = new Socket(loginPanel.LocalHost, 9999);
                        Panel panel = new Panel(socket,loginPanel.User);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,"Cannot Connect to server, try another host ");
                    ex.printStackTrace();
                }
            }
        });


    }



}
