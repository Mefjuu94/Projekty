import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Queue;

public class ClientRoom extends Thread{

    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private InputStream in;
    private OutputStream out;
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

    ClientRoom(Socket socket, List clientsName) throws IOException {
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


            username = reader.readLine();
            System.out.println(username + " dołączył do czatu.");


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

//                    messageHistory.add(username + ": ");
//                    messageHistory.add(message);
                    // utnij znacznik obrazka i reszte wyslij do klientów z tym samym dopiskiem

                }



                if (message.startsWith("[VID]")) {
                    zatrzymajPliki = false;
                    String filename = "";
                    System.out.println("odbieram wideo..");

                    //messageHistory.add(username + " send video");


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

                    //messageHistory.add(username + ": " + message);
                    ChatServer.broadcastMessage(username + ": " + message);
                }
            }


            // Po rozłączeniu usuwamy klienta z listy

        } catch (IOException e) {
            System.out.println(username + " has left the Chat");
            sendMessage(username + " has left the Chat");

            //messageHistory.add(username + " has left the Chat");

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
