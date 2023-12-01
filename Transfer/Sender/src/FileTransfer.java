import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileTransfer {

    Panel panel;

    String sciezkaPliku = "C:\\Users\\mateu\\Downloads\\Cassius - I Love You So (Original Mix) [HQ]";
    String adresOdbiorcy = "192.168.0.107"; //"192.168.0.106"; mój komp
    String nameOfFile = "";
    int port = 12345;


    FileTransfer(){


    }

    public void send(String sciezkaPliku, String name, String adresOdbiorcy) {
        //this.sciezkaPliku = sciezkaPliku;

        try {
            // Odczyt pliku z nadawcy
            Path sciezka = Paths.get(sciezkaPliku);
            byte[] danePliku = Files.readAllBytes(sciezka);
            byte[] nazwa = name.getBytes();
            System.out.println(Arrays.toString(nazwa));
            int ileDanych = nazwa.length;

            System.out.println(name);

            // Ustanowienie połączenia z odbiorcą
            try (Socket socket = new Socket(adresOdbiorcy, port)) {
                // Uzyskanie strumienia wejściowego i wyjściowego
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();

                // Wysyłanie rozmiaru pliku
                System.out.println(danePliku.length);

                // Wysyłanie danych pliku
                out.write(ileDanych);
                out.write(nazwa);
                System.out.println("nazwa :" + Arrays.toString(nazwa));

                out.write(danePliku);
                System.out.println("dane : " + Arrays.toString(danePliku));

                System.out.println("Plik został pomyślnie przesłany do odbiorcy.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}