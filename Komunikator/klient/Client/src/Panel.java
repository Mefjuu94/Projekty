import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;


public class Panel extends JPanel implements KeyListener{

    getMessage getMessage;
    JEditorPane chatTextPane;
    JTextField messageField;
    Socket socket;
    String username;
    FileSplitterWithChecksum fs;
    String client = "";
    String biezacyKatalog = System.getProperty("user.dir") + "\\";
    boolean gif = false;

    boolean zatrzymajwiadomosc = false;

    HTMLEditorKit editorKit = new HTMLEditorKit();

    int WIDTH = 400;
    int HEIGHT = 300;
    JFrame frame = new JFrame("Chat Client");

    ArrayList <JLabel> labelsImages = new ArrayList<>();
    ArrayList <String> baseImages = new ArrayList<>();
    HTMLDocument htmlDocument;

    LocalDateTime ld = LocalDateTime.now();

    private String STYLESHEET ;

    boolean videoCapture = false;

    Panel(Socket socket) throws IOException {
        this.socket = socket;
        this.createAndShowGUI();
    }

    private void createAndShowGUI() throws IOException {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        chatTextPane = new JEditorPane();
        chatTextPane.setLayout(new BoxLayout(chatTextPane,BoxLayout.PAGE_AXIS));
        chatTextPane.setEditable(false);
        chatTextPane.setContentType("text/html");
        chatTextPane.setEditorKit(editorKit);

        JScrollPane scrollPane = new JScrollPane(chatTextPane);

        messageField = new JTextField();
        JButton sendMessageButton = new JButton("Wyślij Wiadomość");
        JButton sendFileButton = new JButton("Wyślij Załącznik");
        JButton snapshot = new JButton("Take a Screenshot!");
        JButton recordConversation = new JButton("Record Conversation!");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        messageField.addKeyListener(this);
        inputPanel.add(sendMessageButton, BorderLayout.EAST);
        inputPanel.add(sendFileButton, BorderLayout.WEST);

        JPanel optionsPanel = new JPanel();
        optionsPanel.add(snapshot,BorderLayout.NORTH);
        optionsPanel.add(recordConversation,BorderLayout.CENTER);


        // Dodaj listenera do obsługi kliknięcia hiperłącza
        chatTextPane.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                openLink(e.getURL().toString());
            }
        });



        sendMessageButton.addActionListener(e -> sendMessage(messageField.getText()));
        sendFileButton.addActionListener(e -> sendFile());
        snapshot.addActionListener(e -> {
            try {
                takeSnapshot();
            } catch (IOException | AWTException ex) {
                throw new RuntimeException(ex);
            }
        });

        recordConversation.addActionListener( new ImageRecordingHandler(frame,recordConversation));

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(optionsPanel,BorderLayout.NORTH);

        this.username = JOptionPane.showInputDialog("Podaj swoją nazwę użytkownika:");
        frame.setTitle("Chat Client - " + username);

        System.out.println(biezacyKatalog);

        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
                                       @Override
                                       public void componentResized(ComponentEvent e) {
                                           super.componentResized(e);

                                           int size = (frame.getWidth() + frame.getHeight()) / 50;

                                           if (size < 15) {
                                               size = 15;
                                           }
                                           String fontSize = String.valueOf(size);

                                           htmlDocument = (HTMLDocument) chatTextPane.getDocument();
                                           Font font = new Font("Serif", Font.BOLD, size);
                                           int imagesize = (frame.getWidth()+frame.getHeight())/7;

                                           STYLESHEET = "body { margin-top: 0; margin-bottom: 0; margin-left: 0; margin-right: 0;"
                                                   + " font-family: %s; font-size: %dpt;  }"
                                                   + "a, p, li { margin-top: 0; margin-bottom: 0; margin-left: 0;"
                                                   + " margin-right: 0; font-family: %s; font-size: %dpt;  }"
                                                   + "img {max-width: 100%%; height: auto; max-height: %dpt; }";

                                           // Dodaj dane obrazów base64 do listy
                                           // Ustawianie preferowanego rozmiaru JLabel (rozmiar obrazka)
                                           for (int i = 0; i < labelsImages.size(); i++) {

                                               // Dekodowanie Base64 do obrazka
                                               ImageIcon imageIcon = new ImageIcon(Base64.getDecoder().decode(baseImages.get(i)));

                                               // Dostosowanie obrazka do żądanych rozmiarów
                                               Image scaledImage = imageIcon.getImage().getScaledInstance(imagesize, imagesize,Image.SCALE_FAST);
                                               ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

                                               labelsImages.get(i).setPreferredSize(new Dimension(imagesize, imagesize));
                                               labelsImages.get(i).setIcon(scaledImageIcon);
                                           }
                                           // większ rozmiar czcionki i obrazów
                                           setHtmlFont(htmlDocument, font,imagesize);
                                           chatTextPane.setCaretPosition(htmlDocument.getLength());
                                       }
                                   }
        );

        getMessage = new getMessage(socket,this);
    }


    private void takeSnapshot() throws IOException, AWTException {
        String DataTime = ld.toString().substring(0,16);
        String formatedDataTime =DataTime.replace(":","_");
        System.out.println(formatedDataTime);

        try {
            Robot robot = new Robot();
            Container panel = frame.getContentPane();
            Point pos = panel.getLocationOnScreen();
            Rectangle bounds = panel.getBounds();
            bounds.x = pos.x;
            bounds.y = pos.y;
            bounds.x -= 1;
            bounds.y -= 1;
            bounds.width += 2;
            bounds.height += 2;
            BufferedImage snapShot = robot.createScreenCapture(bounds);
            ImageIO.write(snapShot, "png", new File("Screenshots\\Snap" + formatedDataTime + ".png"));
            System.out.println("Screenshot Captured!");
        } catch (Exception ex) {
            System.out.println("capture screenshot failed");
            ex.printStackTrace();
        }

    }

    ///zmiania czcionki
    private void setHtmlFont(HTMLDocument doc, Font font, int imageMaxHeight) {
        String stylesheet = String.format(STYLESHEET, font.getName(), font.getSize(), font.getName(), font.getSize(), imageMaxHeight);

        try {
            doc.getStyleSheet().loadRules(new StringReader(stylesheet), null);
        } catch (IOException e) {
            // Obsługa błędu
            throw new IllegalStateException(e);
        }
    }


    //stworzenie linku
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


    //wyslij tekstową wiadomosc
    private void sendMessage(String message) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
            writer.println(message);
            messageField.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //wyślij plik
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

    private void usunCzesci(boolean wyslano) {

        if (wyslano) {
            DeleteFiles deleteFiles = new DeleteFiles();
            for (int i = 0; i < fs.partFilesName.size(); i++) {
                deleteFiles.DeleteFile(fs.partFilesName.get(i));
                System.out.println("czesc: " + fs.partFilesName.get(i) + " usunięta");
            }
            System.out.println(">>>>>>>>>>>>>usunąłem stworzone części!!!<<<<<<<<<<<<<<<<<");
        }

    }


    public void PartFiles(String selectedfile) {

        fs = new FileSplitterWithChecksum(selectedfile);
        fs.SplitFile();
    }

    private void sendGIF(byte[] source) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(source); //base 64 koduje plik obrazu jpg,png,gif do stringa
        //przekształca tablicwe bajtów w stringa

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("[IMAGE]".getBytes()); //wysłanie sygnału, że przesyła obraz
        outputStream.write(base64Image.getBytes()); // wysłanie zakodowanego obrazu w stringu
        outputStream.write("\r\n".getBytes()); //sygnał o końcu kodu - znak przejscia do poczatku wierszxa
        outputStream.flush(); // wysyła w "pakiecie" wwszystko po kolei
    }

    private void checkIfmessagehaveEmoji(String message) {

        ///zaskoczenie szok
        if (message.contains(":o") || message.contains(":O")) {
            Path src = Path.of("gifs/yoyo and cici monkey-emojigg-pack/8191-yoyomonkey-scared.gif");
            gif = true;
            byte[] imgSource = new byte[0];
            try {
                imgSource = Files.readAllBytes(src);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                sendGIF(imgSource);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            messageField.setText("");
        } else {
            gif = false;
        }

        // kisss
        if (message.contains(":*") || message.contains(";*")) {
            Path src = Path.of("gifs/yoyo and cici monkey-emojigg-pack/4470-yoyomonkey-kiss.gif");
            gif = true;
            byte[] imgSource = new byte[0];
            try {
                imgSource = Files.readAllBytes(src);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                sendGIF(imgSource);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            messageField.setText("");
        } else {
            gif = false;
        }

        //smile
        if (message.contains(":d") || message.contains(";D") || message.contains(":D") || message.contains(";d")) {
            Path src = Path.of("gifs/yoyo and cici monkey-emojigg-pack/9369-yoyomonkey-thumbsup.gif");
            gif = true;
            byte[] imgSource = new byte[0];
            try {
                imgSource = Files.readAllBytes(src);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                sendGIF(imgSource);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            messageField.setText("");
        } else {
            gif = false;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {


        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

            if (messageField.getText().length() > 0) {
                String message = messageField.getText();

                checkIfmessagehaveEmoji(message);
                if (!gif) {
                    sendMessage(messageField.getText());
                } else {
                    System.out.println("write something to send message!");
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                // System.out.println("testKEY");


            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
