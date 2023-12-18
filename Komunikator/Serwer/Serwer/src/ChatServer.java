import javax.print.attribute.standard.MediaSize;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ChatServer {
    static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Serwer nasłuchuje na porcie 9999...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowe połączenie: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    static void broadcastImage(String imageBase64) {
        for (ClientHandler client : clients) {
            client.sendImage(imageBase64);
            System.out.println("wysłano obraz");
        }
    }

    static void broadcastVideo(String VideoBase64) {
        for (ClientHandler client : clients) {
            client.sendVideo(VideoBase64);
            System.out.println("wysłano video");
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private InputStream in;
    private  OutputStream out;
    FileOutputStream fileOutputStream;
    String name;

    ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    enum STATE{
        VIDEO,
        NORMAL
    }

    STATE state = STATE.NORMAL;
    String username;


    @Override
    public void run() {
        try {
            username = reader.readLine();
            System.out.println(username + " dołączył do czatu.");
            int count = 0;
            int fileCounter = 0;

            while (true) {
                String message = reader.readLine();
                if (message == null) {
                    break;
                }
                if (message.equals("disconnect")) {
                    break;
                }

                if (message.startsWith("[IMAGE]") ) {
                    ChatServer.broadcastMessage(username + ": ");
                    // Przesłano obrazek
                    ChatServer.broadcastImage(message.substring(7));
                    // utnij znacznik obrazka i reszte wyslij do klientów z tym samym dopiskiem

                }

                if (message.startsWith("[VID]")) {

                    System.out.println("odbieram wideo..");
                    ChatServer.broadcastVideo("[VID]");

                    out = clientSocket.getOutputStream();
                    boolean EndOfVideo = false;
                    int counter  = 0;

                    while (!EndOfVideo){
                        String nameOfFile = reader.readLine();
                        System.out.println(nameOfFile);
                        //do wysyłki
                        out.write(nameOfFile.getBytes());
                        out.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                        out.flush(); // wysyła w "pakiecie" wwszystko po kolei

                        String length = reader.readLine();
                        int lenghtOfFile = Integer.parseInt(length);
                        out.write(length.getBytes());
                        out.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                        out.flush(); // wysyła w "pakiecie" wwszystko po kolei

                        String daneS = reader.readLine();
                        byte[] dane = Base64.getDecoder().decode(daneS);

                        String encodedData = Base64.getEncoder().encodeToString(dane);
                        byte[] data = encodedData.getBytes();

                        // Wysyłanie zakodowanych danych do serwera
                        out.write(data);
                        out.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                        out.flush(); // wysyła w "pakiecie" wwszystko po kolei
                        
                        byte[] b = dane;
                        //System.out.println(reader.readLine());
                        // Odczytaj i zapisz plik na dysku

                        fileOutputStream = new FileOutputStream(nameOfFile);
                        fileOutputStream.write(b,0,lenghtOfFile);
                        fileOutputStream.close();

                        System.out.println("zapisałem na dysku plik o nazwie: " + nameOfFile +
                        " o długości: " + lenghtOfFile);

                        counter+=1;
                        if (counter == 12){
                            //tutaj ponownie trzeba wysłać pliki do klientów
                            FileMergerWithChecksumVerification fm = new FileMergerWithChecksumVerification();
                            fm.scalPliki();
                            System.out.println("scaliłem pliki");
                            break;
                        }


                    }



                } else {
                    // Przesłano tekst
                    // Wyświetlanie w konsoli serwera
                    System.out.println(username + ": " + message);

                    // Odsyłamy wiadomość do wszystkich klientów
                    ChatServer.broadcastMessage(username + ": " + message);
                }
            }

            // Po rozłączeniu usuwamy klienta z listy
            ChatServer.clients.remove(this);
            System.out.println(username + " opuścił czat.");
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(username + " has left the Chat");
        }
    }


    void sendMessage(String message) {
        writer.println(message);
    }

    void sendImage(String base64Image) {
        writer.println("[IMAGE]" + base64Image);
    }

    void sendVideo(String base64Video) {
        writer.println(base64Video);
    }
}
