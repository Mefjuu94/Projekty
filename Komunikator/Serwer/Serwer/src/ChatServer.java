import javax.swing.*;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ChatServer {
    static List<ClientHandler> clients = new ArrayList<>();


    public static void main(String[] args) {

        JFrame frame = new JFrame("serwer");
        JPanel panel = new JPanel();

        frame.add(panel);
        frame.setBounds(300, 300, 300, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

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
            //System.out.println("wysłano video do " + client.username);
        }
    }

}

class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private InputStream in;
    private OutputStream out;
    FileOutputStream fileOutputStream;
    int plikiOdebrane = 0;

    boolean zatrzymajPliki = false;
    boolean zatrzymajimg = false;
    boolean zatrzymajwiadomosc = false;

    ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
            writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
            in = new DataInputStream(socket.getInputStream());
            out = clientSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String username;

    @Override
    public void run() {
        try {
            username = reader.readLine();
            System.out.println(username + " dołączył do czatu.");

            while (!zatrzymajwiadomosc) {
                String message = reader.readLine();
                if (message == null) {
                    zatrzymajwiadomosc = true;
                }
                if (message.equals("disconnect")) {
                    clientSocket.close();
                }

                if (message.startsWith("[IMAGE]")) {
                    ChatServer.broadcastMessage(username + ": ");
                    // Przesłano obrazek
                    ChatServer.broadcastImage(message.substring(7));
                    // utnij znacznik obrazka i reszte wyslij do klientów z tym samym dopiskiem

                }

                if (message.startsWith("[VID]")) {
                    zatrzymajPliki = false;
                    String filename = "";
                    System.out.println("odbieram wideo..");

                    ChatServer.broadcastVideo("[VID]");
                    System.out.println("WYSŁĄŁEM ZNACZNIK VIDEO");

                    String user = reader.readLine();
                    System.out.println("kto wysyła: " + user);
                    ChatServer.broadcastVideo(user);

                    filename = reader.readLine();

                    System.out.println("odbieram i przekazuje nazwę pliku: " + filename);
                    ChatServer.broadcastVideo(filename);


                    int counter = 0;

                    ArrayList<String> pliki = new ArrayList<>();

                    while (!zatrzymajPliki) {
                        String nameOfFile = reader.readLine();
                        System.out.println(nameOfFile);
                        pliki.add(nameOfFile);
                        //do wysyłki
                        for (ClientHandler client : ChatServer.clients) {
                            client.out.write(nameOfFile.getBytes());
                            client.out.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                            client.out.flush(); // wysyła w "pakiecie" wwszystko po kolei
                        }

                        String length = reader.readLine();
                        int lenghtOfFile = Integer.parseInt(length);
                        for (ClientHandler client : ChatServer.clients) {
                            client.out.write(length.getBytes());
                            client.out.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                            client.out.flush(); // wysyła w "pakiecie" wwszystko po kolei
                        }

                        String daneS = reader.readLine();
                        byte[] dane = Base64.getDecoder().decode(daneS);

                        String encodedData = Base64.getEncoder().encodeToString(dane);
                        byte[] data = encodedData.getBytes();

                        // Wysyłanie zakodowanych danych do serwera
                        for (ClientHandler client : ChatServer.clients) {
                            client.out.write(data);
                            client.out.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                            client.out.flush(); // wysyła w "pakiecie" wwszystko po kolei
                        }

                        byte[] b = dane;
                        //System.out.println(reader.readLine());
                        // Odczytaj i zapisz plik na dysku

                        fileOutputStream = new FileOutputStream(nameOfFile);
                        fileOutputStream.write(b, 0, lenghtOfFile);
                        fileOutputStream.close();

                        System.out.println("zapisałem na dysku plik o nazwie: " + nameOfFile +
                                " o długości: " + lenghtOfFile);

                        counter += 1;
                        if (counter == 12) {
                            //tutaj ponownie trzeba wysłać pliki do klientów
                            FileMergerWithChecksumVerification fm = new FileMergerWithChecksumVerification();
                            fm.scalPliki(filename);
                            System.out.println("scaliłem pliki");

                            /// usunięcie plików cząstkowych ze scalenia
                            DeleteFiles deleteFiles = new DeleteFiles();
                            for (String s : pliki) {

                                String pathFromFilesName = s.toString();
                                System.out.println(pathFromFilesName);
                                deleteFiles.DeleteFile(pathFromFilesName);
                            }
                            //usunięcie głownego pliku scalonego
                            deleteFiles.DeleteFile(fm.mergedFilePath);

                            zatrzymajPliki = true;
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
            for (ClientHandler client : ChatServer.clients) {
                if (!client.isAlive()){
                    ChatServer.clients.remove(this);
                    System.out.println(username + " opuścił czat.");
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.out.println(username + " has left the Chat");
            ChatServer.clients.remove(this);
            System.out.println(username + " opuścił czat.");
            System.out.println("pozostało: " + ChatServer.clients.size() + " klientów");
            try {
                clientSocket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

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
