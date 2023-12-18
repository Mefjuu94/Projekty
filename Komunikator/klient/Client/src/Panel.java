import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class Panel extends JPanel implements KeyListener {

    private JTextPane chatTextPane;
    private JTextField messageField;
    private Socket socket;
    private String username;
    FileSplitterWithChecksum fs;

    Panel(Socket socket){
        this.socket = socket;
        this.createAndShowGUI();

    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        chatTextPane = new JTextPane();
        chatTextPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatTextPane);

        messageField = new JTextField();
        JButton sendMessageButton = new JButton("Wyślij Wiadomość");
        JButton sendFileButton = new JButton("Wyślij Załącznik");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        messageField.addKeyListener(this);
        inputPanel.add(sendMessageButton, BorderLayout.EAST);
        inputPanel.add(sendFileButton, BorderLayout.WEST);

        sendMessageButton.addActionListener(e -> sendMessage(messageField.getText()));
        sendFileButton.addActionListener(e -> sendFile());

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);

        this.username = JOptionPane.showInputDialog("Podaj swoją nazwę użytkownika:");
        frame.setTitle("Chat Client - " + username);

        this.listen();

        frame.setVisible(true);
    }



    private void listen() {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer.println(username);

            new Thread(() -> {
                try {

                    ArrayList<String> namesOfFilesToMerge = new ArrayList<>();
                    while (true) {
                        String message = reader.readLine();
                        System.out.println(message.substring(0,3));
                        if (message == null) {
                            break;
                        }

                        if (message.startsWith("[VID]")) {

                            int numberOfFiles = 0;
                            System.out.println("odbieram wideo..");

                            while (true) {
                                String nameOfFile = reader.readLine();
                                System.out.println(nameOfFile);
                                //do wysyłki

                                String length = reader.readLine();
                                int lenghtOfFile = Integer.parseInt(length);

                                String daneS = reader.readLine();
                                byte[] dane = Base64.getDecoder().decode(daneS);

                                byte[] b = dane;
                                //System.out.println(reader.readLine());
                                // Odczytaj i zapisz plik na dysku

                                FileOutputStream fileOutputStream = new FileOutputStream("recived\\" + nameOfFile);
                                fileOutputStream.write(b,0,lenghtOfFile);
                                fileOutputStream.close();

                                System.out.println("zapisałem na dysku plik o nazwie: " + nameOfFile +
                                        " o długości: " + lenghtOfFile);

                                System.out.println(" odebrałem i zapisałem na dysku plik o nazwie: " + nameOfFile +
                                        " o długości: " + lenghtOfFile + " bajtów");
                                numberOfFiles += 1;
                                namesOfFilesToMerge.add(nameOfFile);
                                if (numberOfFiles == 12){
                                    FileMergerWithChecksumVerification fm = new FileMergerWithChecksumVerification();
                                    fm.scalPliki();
                                    System.out.println("scaliłem pliki");
                                    break;
                                }

                            }
                            //// tutaj kod który ma wyświetlać video
                        }



                        if (message.startsWith("[IMAGE]")) {
                            System.out.println("img");
                            appendToChatTextPane(username);
                            appendToChatTextPane("");
                            displayImage(message.substring(7));

                        }

                         else {
                            appendToChatTextPane(message);
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("stracono połączenie!");
                    ex.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendToChatTextPane(String message) {
        SwingUtilities.invokeLater(() -> {
            Document doc = chatTextPane.getDocument();
            try {
                doc.insertString(doc.getLength(), message + "\n", null);
                chatTextPane.setCaretPosition(doc.getLength());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendMessage(String message) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(message);
            messageField.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile(); // jak wybierze plik to
            try {
                byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());

                if (selectedFile.getName().endsWith(".mp4") || selectedFile.getName().endsWith(".avi") ) {

                    System.out.println("dzielę wideo na części");
                    PartFiles(selectedFile.toString());
                    System.out.println("wysyłam video..");
                    byte[] video = "[VID]".getBytes();

                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(video);

                    outputStream.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                    outputStream.flush(); // wysyła w "pakiecie" wwszystko po kolei

                    for (int i = 0; i < fs.partFilesName.size(); i++) {

                        try(FileInputStream fis = new FileInputStream("C:\\Users\\mateu\\OneDrive\\Pulpit\\Projekty\\Komunikator\\klient\\Client\\Pliki\\" +
                                fs.partFilesName.get(i));){

                            String pathFromFilesName = fs.partFilesName.get(i);
                            Path path = Path.of("C:\\Users\\mateu\\OneDrive\\Pulpit\\Projekty\\Komunikator\\klient\\Client\\Pliki\\" + pathFromFilesName);

                            String name = fs.partFilesName.get(i);
                            System.out.println("wysyłąm plik podzielony?: " + name);

                            //wyślij nazwe pliku

                            outputStream.write(name.getBytes());

                            outputStream.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                            outputStream.flush(); // wysyła w "pakiecie" wwszystko po kolei

                            //wyślij długość pliku

                            long length = fis.readAllBytes().length;
                            String lengthString = Long.toString(length);
                            outputStream.write(lengthString.getBytes());

                            outputStream.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                            outputStream.flush(); // wysyła w "pakiecie" wwszystko po kolei

                            //wyślij dane pliku!!
                            byte[] fileData = Files.readAllBytes(Paths.get(path.toString()));

                            // Zakoduj dane przy użyciu Base64
                            String encodedData = Base64.getEncoder().encodeToString(fileData);
                            byte[] data = encodedData.getBytes();

                            // Wysyłanie zakodowanych danych do serwera
                            outputStream.write(data);

                            outputStream.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                            outputStream.flush(); // wysyła w "pakiecie" wwszystko po kolei


                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }



                }

                if (selectedFile.getName().endsWith(".jpg") || selectedFile.getName().endsWith(".gif") || selectedFile.getName().endsWith(".png")) {
                    String base64Image = Base64.getEncoder().encodeToString(fileBytes); //base 64 koduje plik obrazu jpg,png,gif do stringa
                    //przekształca tablicwe bajtów w stringa

                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("[IMAGE]".getBytes()); //wysłanie sygnału, że przesyła obraz
                    outputStream.write(base64Image.getBytes()); // wysłanie zakodowanego obrazu w stringu
                    outputStream.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                    outputStream.flush(); // wysyła w "pakiecie" wwszystko po kolei
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayImage(String base64Image) {

            ImageIcon imageIcon = new ImageIcon(Base64.getDecoder().decode(base64Image));

            // Tworzymy obrazek jako ikonę
            Icon icon = new ImageIcon(imageIcon.getImage());

            // Pobieramy dokument
            StyledDocument doc = (StyledDocument) chatTextPane.getDocument();

            // Tworzymy ikonę wstawianą jako komponent
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setComponent(attrs, new JLabel(icon));

            // Dodajemy ikonę do dokumentu
            try {
                doc.insertString(doc.getLength(), " ", attrs); // Wstawiamy spację jako tekst z atrybutami
                doc.insertString(doc.getLength(), "\n", null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
    }

    public void PartFiles(String selectedfile){

        fs = new FileSplitterWithChecksum(selectedfile);
        fs.SplitFile();

    }




    ////////////////////////////////////////////////////////////////////////////
    private void displayVideo(String base64Video) throws IOException {

        FileWriter fileWriter = new FileWriter("video.mp4");
        fileWriter.write(base64Video);

        fileWriter.close();

        File file = new File("video.mp4");

        Media media = new Media(new File(String.valueOf(file)).toURI().toString());

        MediaPlayer mediaPlayer = new MediaPlayer(media);

        //Instantiating MediaView class
        MediaView mediaView = new MediaView(mediaPlayer);

        mediaPlayer.setAutoPlay(true);

        //setting group and scene
        Group root = new Group();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root,500,400);
        Stage primaryStage = (Stage) chatTextPane.getDocument();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Playing video");
        primaryStage.show();

        // Pobieramy dokument
        StyledDocument doc = (StyledDocument) chatTextPane.getDocument();

        // Tworzymy ikonę wstawianą jako komponent
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setComponent(attrs, new JLabel());

        // Dodajemy ikonę do dokumentu
        try {
            doc.insertString(doc.getLength(), " ", attrs); // Wstawiamy spację jako tekst z atrybutami
            doc.insertString(doc.getLength(), "\n", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            sendMessage(messageField.getText());
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
