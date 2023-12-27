import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

public class Panel extends JPanel implements KeyListener {

    private JEditorPane chatTextPane;
    private JTextField messageField;
    private Socket socket;
    private String username;
    FileSplitterWithChecksum fs;
    String client = "";
    public String biezacyKatalog = System.getProperty("user.dir") + "\\";

    Panel(Socket socket) {
        this.socket = socket;
        this.createAndShowGUI();
    }

    private void createAndShowGUI() {

        JFrame frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        //TODO
        // - zrbić na górze przycisk do zmiany nasłuchu portu lub wysyłania ip :)

        chatTextPane = new JEditorPane();
        chatTextPane.setEditable(false);
        chatTextPane.setContentType("text/html");
        chatTextPane.setEditorKit(new HTMLEditorKit());

        // Dodaj listenera do obsługi kliknięcia hiperłącza
        chatTextPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                openLink(e.getURL().toString());
            }
        });


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

        System.out.println(biezacyKatalog);
        this.listen();

        frame.setVisible(true);
    }

    private void openLink(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop is not supported. Cannot open the link.");
        }
    }

    private void listen() {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(),true,StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));

            writer.println(username);

            new Thread(() -> {
                try {

                    ArrayList<String> namesOfFilesToMerge = new ArrayList<>();
                    while (true) {
                        String message = reader.readLine();

                        boolean ifVideo = false;
                        if (message == null) {
                            break;
                        }

                        if (message.startsWith("[VID]")) {
                            ifVideo = true; /// żeby nie dodawało za końcu po przesłaniu pliku linijki z "[vid]"

                            FileMergerWithChecksumVerification fm = new FileMergerWithChecksumVerification();
                            client = message.substring(5,message.length());

                            String user = reader.readLine();
                            System.out.println("od kogo plik? -> " + user);

                            String File = reader.readLine();
                            String FileName = File.replace(" ","_");
                            System.out.println("odbieram plik " + FileName);

                            int numberOfFiles = 0;

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


                                FileOutputStream fileOutputStream = new FileOutputStream(biezacyKatalog + "Pliki\\" + nameOfFile);
                                fileOutputStream.write(b, 0, lenghtOfFile);
                                fileOutputStream.close();

                                System.out.println(" odebrałem i zapisałem na dysku plik o nazwie: " + nameOfFile +
                                        " o długości: " + lenghtOfFile + " bajtów");
                                numberOfFiles += 1;
                                namesOfFilesToMerge.add(nameOfFile);
                                if (numberOfFiles == 12) {

                                    fm.scalPliki(FileName);
                                    System.out.println("scaliłem pliki");
                                    break;
                                }

                            }
                            File file = new File(fm.mergedFilePath);
                            //wypisz na ekran
                            appendToChatTextPane(user + " sent File:");
                            //dodaj do hyperlink
                            appendVideoLink(file,fm.fileName);

                            // usuń zbędne party po scaleniu!
                            System.out.println(">>>>>>>>>>>>>CHCE USUNAC CZESCI PRZYJETE<<<<<<<<<<<<<<<<<");
                            DeleteFiles deleteFiles = new DeleteFiles();
                            for (String pathFromFilesName : namesOfFilesToMerge) {
                                deleteFiles.DeleteFile(biezacyKatalog + "Pliki\\" + pathFromFilesName);
                                System.out.println("ODEBRANA CZESC PLIKU " + pathFromFilesName + " USUNIETA");
                            }
                            System.out.println(">>>>>>>>>>>>>USUNĄŁEM CZESCI PRZYJETE<<<<<<<<<<<<<<<<<");

                        }

                        if (message.startsWith("[IMAGE]")) {
                            System.out.println("img");
                            appendToChatTextPane("sent image: ");
                            appendToChatTextPane("");
                            displayImage(message.substring(7));
                            String blankMessage = reader.readLine(); // żeby nie wyświetlało w formie tekstowej obrazka
                        } else {
                            if (!ifVideo ) {
                                System.out.println("wiadomosc tekstowa..");
                                System.out.println(message);
                                appendToChatTextPane(message.toString());
                            }
                            ifVideo = false;

                        }
                    }
                } catch (IOException ex) {
                    System.out.println("stracono połączenie!");
                    ex.printStackTrace();
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendToChatTextPane(String message) {
        HTMLDocument doc = (HTMLDocument) chatTextPane.getDocument();
        try {
            // Sprawdź, czy treść istnieje w dokumencie
            if (doc.getLength() > 0) {
                // Dodaj nową linię przed nową wiadomością
                doc.insertAfterEnd(doc.getCharacterElement(doc.getLength() - 1), "<br>");
            }

            // Dodaj wiadomość do dokumentu HTML
            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), message);
        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }

        // Scrolluj do dołu
        chatTextPane.setCaretPosition(doc.getLength());

    }

    private void sendMessage(String message) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true,StandardCharsets.UTF_8);
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

                if (selectedFile.getName().endsWith(".mp4") || selectedFile.getName().endsWith(".avi") || selectedFile.getName().endsWith(".mp3")) {

                    boolean wyslao = false;

                    System.out.println("dzielę wideo na części");
                    PartFiles(selectedFile.toString());
                    System.out.println("wysyłam video..");
                    byte[] video = "[VID]".getBytes();

                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(video);

                    outputStream.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                    outputStream.flush(); // wysyła w "pakiecie" wwszystko po kolei

                    outputStream.write(username.getBytes(StandardCharsets.UTF_8));

                    outputStream.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                    outputStream.flush(); // wysyła w "pakiecie" wwszystko po kolei

                    String filname = selectedFile.getName();
                    outputStream.write(filname.getBytes());

                    outputStream.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
                    outputStream.flush(); // wysyła w "pakiecie" wwszystko po kolei

                    String sciezka = biezacyKatalog + "Pliki\\";

                    for (int i = 0; i < fs.partFilesName.size(); i++) {

                        try (FileInputStream fis = new FileInputStream(sciezka + fs.partFilesName.get(i))) {

                            String pathFromFilesName = fs.partFilesName.get(i);
                            Path path = Path.of(sciezka + pathFromFilesName);

                            String name = fs.partFilesName.get(i);
                            System.out.println(">>>>wysyłąm plik podzielony?: " + name);

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
                    wyslao = true;

                    //usuń stworzone częsci
                    usunCzesci(wyslao);
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

    private void usunCzesci(boolean wyslano){

        if (wyslano){
            DeleteFiles deleteFiles = new DeleteFiles();
            for (int i = 0; i < fs.partFilesName.size(); i++) {
                deleteFiles.DeleteFile(fs.partFilesName.get(i));
                System.out.println("czesc: " + fs.partFilesName.get(i) + " usunięta");
            }
            System.out.println(">>>>>>>>>>>>>usunąłem stworzone części!!!<<<<<<<<<<<<<<<<<");
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

    private void appendVideoLink(File filePath, String videoFileName) throws BadLocationException {

        HTMLDocument doc = (HTMLDocument) chatTextPane.getDocument();
        try {
            // Sprawdź, czy treść istnieje w dokumencie
            if (doc.getLength() > 0) {
                // Dodaj nową linię przed nową wiadomością
                doc.insertAfterEnd(doc.getCharacterElement(doc.getLength() - 1), "<br><a href='" + "file://"
                        + filePath.getAbsolutePath() + "'>OTWÓRZ PLIK</a>" + " o nazwie " + videoFileName ); //
            }

            // Dodaj wiadomość do dokumentu HTML
            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()),"");
        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }
    }

    public void PartFiles(String selectedfile) {

        fs = new FileSplitterWithChecksum(selectedfile);
        fs.SplitFile();
    }



    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (messageField.getText().length() > 0) {
                sendMessage(messageField.getText());
            }else {
                System.out.println("write something to send message!");
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
           // System.out.println("testKEY");


        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
