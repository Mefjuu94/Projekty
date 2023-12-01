import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Reciver extends JPanel {

    public String nazwaPliku = "";
    int port = 12345;

    Reciver() {


    }

    public void reciveFile() {

        // Utworzenie serwera oczekującego na połączenie
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serwer oczekuje na połączenie, lub kolejny plik...");

            // Akceptowanie połączenia od klienta
            Socket socket = serverSocket.accept();
            System.out.println("Połączenie nawiązane.");

            // Uzyskanie strumienia wejściowego i wyjściowego
            InputStream in = socket.getInputStream();

            //NAZWA PLIKU TAKA JAK U WYSYŁAJĄCEGO!//
            int nazwa = (in.read());
            byte[] nazwaPlikuWBajtach = in.readNBytes(nazwa);
            nazwaPliku = new String(nazwaPlikuWBajtach, StandardCharsets.UTF_8);

            System.out.println("nazwa pliku = " + nazwaPliku);

            FileOutputStream out = new FileOutputStream(nazwaPliku);
            /////////////////////////////////////////////

            // odczytanie bajtów
            byte[] danePliku = in.readAllBytes();

            // Zapisanie pliku na serwerze
            out.write(danePliku);
            out.close();

            Main.filesName = nazwaPliku;

            System.out.println("Plik został pomyślnie zapisany na serwerze.");
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
