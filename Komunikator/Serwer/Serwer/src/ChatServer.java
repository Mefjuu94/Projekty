import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatServer {
    static List<ClientHandler> clients = new ArrayList<>();
    static JButton show = new JButton("show Clients");
    static JButton clearChatHistory = new JButton("Wyczyść historię czatu");
    static List<String> clientsNames = new ArrayList<>();
    static List<String> avatars = new ArrayList<>();
    static List<String> ageOfClients = new ArrayList<>();
    static List<String> localizationOfClients = new ArrayList<>();
    static LocalDateTime ldt = LocalDateTime.now();
    static String time = ldt.toString().substring(0, 10);

    static BufferedWriter writeChatHistory;
    static BufferedReader readChatHistory;
    static String biezacyKatalog = System.getProperty("user.dir");
    static File historyChat = new File("ChatHistory");

    static Queue<String> messageHistory;


    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("serwer");
        JPanel panel = new JPanel();

        frame.add(panel);
        frame.setBounds(300, 300, 300, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pozostało klientów: ");
                for (int i = 0; i < clientsNames.size(); i++) {

                    System.out.println(clientsNames.get(i));
                }
            }
        });
        show.setVisible(true);
        panel.add(show);

        clearChatHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (messageHistory != null){
                    messageHistory.clear();
                    System.out.println("usunięto Historię czatu!");
                }
            }
        });
        clearChatHistory.setVisible(true);
        panel.add(clearChatHistory);


        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Serwer nasłuchuje na porcie 9999...");

            messageHistory = new ConcurrentLinkedQueue<>(); // utwórz linked Queue przed wczytaniem pliku
            loadPrevoiusHistory(historyChat);// wczytgaj plik
            writeChatHistory = new BufferedWriter(new FileWriter("ChatHistory")); // nadpisz

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    for (String message : messageHistory){
                    writeChatHistory.append(message);
                    writeChatHistory.newLine();
                    }

                } catch (RuntimeException | IOException e) {
                    throw new RuntimeException(e);
                }
            }));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nowe połączenie: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, clientsNames, avatars, ageOfClients, localizationOfClients, writeChatHistory, historyChat,readChatHistory,messageHistory);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            writeChatHistory.close();
        }
    }

    static void loadPrevoiusHistory(File Chatfile) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(Chatfile));
        String lineOfHistory = "";
        while ( (lineOfHistory = br.readLine()) != null){
            messageHistory.add(lineOfHistory);
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
    OutputStream out;
    FileOutputStream fileOutputStream;

    List<String> clientsNames = new ArrayList<>();
    List<String> avatars = new ArrayList<>();
    List<String> ageOfClients = new ArrayList<>();
    List<String> localizationOfClients = new ArrayList<>();

    boolean zatrzymajPliki = false;
    boolean zatrzymajwiadomosc = false;

    LocalDateTime ldt = LocalDateTime.now();
    String time = ldt.toString().substring(0, 7);
    BufferedWriter chatHistory;
    BufferedReader readChat;
    File history;

    Queue<String> messageHistory;

    ClientHandler(Socket socket, List clientsName, List avatars, List ageOfClients, List localizationOfClients, BufferedWriter chatHistory,File history, BufferedReader readChat, Queue<String> messageHistory) throws IOException {
        this.clientsNames = clientsName;
        this.clientSocket = socket;
        this.history = history;

        this.avatars = avatars;
        this.ageOfClients = ageOfClients;
        this.localizationOfClients = localizationOfClients;

        this.chatHistory = chatHistory;
        this.readChat = readChat;

        this.messageHistory = messageHistory;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
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
        int indexOfCurrent = 0;
        try {

            sendHistoryToNewUser();

            username = reader.readLine();
            System.out.println(username + " dołączył do czatu.");

            messageHistory.add(username + " dołączył do czatu.");

            System.out.println("ilość klientów: " + clientsNames.size());
            clientsNames.add(username);
            ChatServer.broadcastMessage("c%l%" + clientsNames);

            String line = reader.readLine();
            ;
            String[] split = line.split(";");
            avatars.add(split[0]);
            ChatServer.broadcastMessage("%%av" + avatars);

            ageOfClients.add(split[1]);
            System.out.println(split[1] + " tu wiek");
            ChatServer.broadcastMessage("%%ag" + ageOfClients);

            localizationOfClients.add(split[2]);
            System.out.println(split[2] + " tu lokalizacja");
            ChatServer.broadcastMessage("%%lo" + localizationOfClients);


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

                    messageHistory.add(username + ": ");
                    messageHistory.add(message);
                    // utnij znacznik obrazka i reszte wyslij do klientów z tym samym dopiskiem

                }

                if (message.startsWith("%%PRV;")){
                    System.out.println("PRYWATNA WIADOMOSC!!!");
                    String[] priv = message.split(";");
                    List<String> clients = new ArrayList<>();
                    clients.add(priv[1]);
                    clients.add(priv[2]);
                    String mess = priv[3];
                    System.out.println("cl1 : " + priv[1] + " cl2:" + priv[2]);
                    ChatServer.broadcastMessage("%%PRV;" + priv[1] + ";" + priv[2]);
                    ChatServer.broadcastMessage(mess);

//                    ClientRoom clientRoom = new ClientRoom(clientSocket, clients);
//                    clientRoom.start();

                }

                if (message.startsWith("[VID]")) {
                    zatrzymajPliki = false;
                    String filename = "";
                    System.out.println("odbieram wideo..");

                    messageHistory.add(username + " send video");


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

                    messageHistory.add(username + ": " + message);
                    ChatServer.broadcastMessage(username + ": " + message);
                }
            }


            // Po rozłączeniu usuwamy klienta z listy

        } catch (IOException e) {
            System.out.println(username + " has left the Chat");
            sendMessage(username + " has left the Chat");

            messageHistory.add(username + " has left the Chat");

            for (int i = 0; i < ChatServer.clients.size(); i++) {
                if (clientsNames.get(i).equals(username)) {
                    indexOfCurrent = i;
                    System.out.println("index = " + i);
                }
            }
            ChatServer.clients.remove(this);
            clientsNames.remove(username);

            System.out.println("index of current = " + indexOfCurrent);

            avatars.remove(indexOfCurrent);
            ageOfClients.remove(indexOfCurrent);
            localizationOfClients.remove(indexOfCurrent);
//
            System.out.println(username + " opuścił czat.");
            System.out.println("pozostało: " + ChatServer.clients.size() + " klientów");
            ChatServer.broadcastMessage("c%l%" + clientsNames); // wyślij zauktualizowaną lsitę klientów
            ChatServer.broadcastMessage("%%av" + avatars);
            ChatServer.broadcastMessage("%%ag" + ageOfClients);
            ChatServer.broadcastMessage("%%lo" + localizationOfClients);

            try {
                clientSocket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
    }



    public void getMessageHistory() throws IOException {
        // Zwracamy historię wiadomości jako jeden ciąg znaków, gdzie wiadomości są oddzielone np. nową linią
        for (String message : messageHistory) {
            sendMessage(message);
        }
    }

    // Metoda wywoływana, gdy nowy klient dołącza do czatu
    public void sendHistoryToNewUser() throws IOException {
        // Pobieramy historię wiadomości
        getMessageHistory();
        // Wysyłamy historię do nowego użytkownika (implementacja zależy od logiki aplikacji, np. można to zrealizować poprzez wysłanie wiadomości przez sieć)
        System.out.println("Sending chat history to new user:\n" + history);

    }


    public void Cookies(String username) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("jakisPlik.txt"));

        /// dokonczyc!!!
        boolean breakWhile = false;
        String[] data = new String[3];

        while (br.readLine() != null || breakWhile){
            if (br.readLine().equals(username)){
                data[0] = br.readLine();
                data[1] = br.readLine();
                data[2] = br.readLine();
                breakWhile = true;
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
