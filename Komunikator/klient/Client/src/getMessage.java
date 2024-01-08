import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;

import static java.lang.Integer.parseInt;

public class getMessage {
    PrintWriter writer;
    BufferedReader reader;
    Panel panel;
    int someImage = 0;
    boolean largeImage = false;
    boolean ifVideo = false;
    boolean zatrzymajPliki = false;

    getMessage(Socket socket, Panel Panel) throws IOException {
        writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.panel = Panel;


        listen(panel);

    }

    public void listen(Panel panel) {
        writer.println(panel.username);

        new Thread(() -> {
            try {

                ArrayList<String> namesOfFilesToMerge = new ArrayList<>();
                while (!panel.zatrzymajwiadomosc) {
                    String message = reader.readLine();

                    if (message == null) {
                        panel.zatrzymajwiadomosc = true;
                    }

                    assert message != null;
                    if (message.startsWith("[VID]")) {
                        zatrzymajPliki = false;
                        System.out.println("jest video!!!!!");

                        FileMergerWithChecksumVerification fm = new FileMergerWithChecksumVerification();
                        panel.client = message.substring(5, message.length());

                        String user = reader.readLine();
                        System.out.println("od kogo plik? -> " + user);

                        String File = reader.readLine();
                        String FileName = File.replace(" ", "_");
                        System.out.println("odbieram plik " + FileName.substring(0,5));

                        int numberOfFiles = 0;

                        while (!zatrzymajPliki) {
                            String nameOfFile = reader.readLine();
                            System.out.println(nameOfFile);
                            //do wysyłki

                            String length = reader.readLine();
                            int lenghtOfFile = parseInt(length);

                            String daneS = reader.readLine();
                            byte[] dane = Base64.getDecoder().decode(daneS);

                            byte[] b = dane;
                            // Odczytaj i zapisz plik na dysku

                            FileOutputStream fileOutputStream = new FileOutputStream(panel.biezacyKatalog + "Pliki\\" + nameOfFile);
                            fileOutputStream.write(b, 0, lenghtOfFile);
                            fileOutputStream.close();

                            System.out.println(" odebrałem i zapisałem na dysku plik o nazwie: " + nameOfFile +
                                    " o długości: " + lenghtOfFile + " bajtów");
                            numberOfFiles += 1;
                            namesOfFilesToMerge.add(nameOfFile);
                            if (numberOfFiles == 12) {

                                fm.scalPliki(FileName);
                                System.out.println("scaliłem pliki");
                                zatrzymajPliki = true;
                            }

                        }
                        java.io.File file = new File(fm.mergedFilePath);
                        //wypisz na ekran
                        appendToChatTextPane(user + " sent File:");
                        //dodaj do hyperlink
                        appendVideoLink(file, fm.fileName);

                        // usuń zbędne party po scaleniu!
                        System.out.println(">>>>>>>>>>>>>CHCE USUNAC CZESCI PRZYJETE<<<<<<<<<<<<<<<<<");
                        DeleteFiles deleteFiles = new DeleteFiles();
                        for (String pathFromFilesName : namesOfFilesToMerge) {
                            deleteFiles.DeleteFile(panel.biezacyKatalog + "Pliki\\" + pathFromFilesName);
                             System.out.println("ODEBRANA CZESC PLIKU " + pathFromFilesName + " USUNIETA");
                        }
                        namesOfFilesToMerge.clear();
                        System.out.println(">>>>>>>>>>>>>USUNĄŁEM CZESCI PRZYJETE<<<<<<<<<<<<<<<<<");

                    } else if (message.startsWith("[IMAGE]")) {
                        System.out.println("img");
                        appendToChatTextPane("sent image: ");
                        //appendToChatTextPane("");

                        int imagesize = (panel.frame.getWidth() + panel.frame.getHeight()) / 7;
                        displayImage(message.substring(7), imagesize, imagesize);

                        String blankMessage = reader.readLine(); // żeby nie wyświetlało w formie tekstowej obrazka

                    } else {
                        System.out.println("wiadomosc tekstowa..");
                        System.out.println(message);
                        appendToChatTextPane(message);
                    }
                }
            } catch (IOException ex) {
                System.out.println("stracono połączenie!");
                ex.printStackTrace();
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


    public void displayImage(String base64Image, int width, int height) throws IOException {

        if (getImageSize(base64Image) > 700) {
// Ścieżka do pliku, do którego zapiszemy obraz
            String fileName = "Obraz" + someImage + ".jpg";
            String filePath = "Pliki\\" + fileName;
            someImage++;
            // Zapis obrazu do pliku
            try {
                saveBase64ImageToFile(base64Image, filePath);
                System.out.println("Obraz został pomyślnie zapisany do pliku: " + filePath);
                appendLinkToImage(new File(filePath), fileName);
            } catch (IOException | BadLocationException e) {
                e.printStackTrace();
            }
        } else {
            // Dekodowanie Base64 do obrazka
            ImageIcon imageIcon = new ImageIcon(Base64.getDecoder().decode(base64Image));

            // Dostosowanie obrazka do żądanych rozmiarów
            Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_FAST);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

            // Tworzenie JLabel jako kontenera dla obrazka
            JLabel label = new JLabel(scaledImageIcon);
            panel.labelsImages.add(label);
            panel.baseImages.add(base64Image);

            // Ustawianie preferowanego rozmiaru JLabel (rozmiar obrazka)
            label.setPreferredSize(new Dimension(width, height));

            // Pobieranie dokumentu
            StyledDocument doc = (StyledDocument) panel.chatTextPane.getDocument();

            // Tworzenie atrybutów dla wstawianego obrazka
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setComponent(attrs, label);

            // Dodawanie komponentu JLabel do dokumentu
            try {
                doc.insertString(doc.getLength(), " ", attrs); // Wstawianie spację jako tekst z atrybutami
                doc.insertString(doc.getLength(), "\n", null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }

            panel.chatTextPane.repaint();

            // Ustawianie karetki na końcu dokumentu
            panel.chatTextPane.setCaretPosition(doc.getLength());
            largeImage = false;
        }
    }

    public static void saveBase64ImageToFile(String base64Image, String filePath) throws IOException {
        // Dekodowanie Base64 do bajtów
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Zapis bajtów do pliku
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBytes);
        }
    }

    public int getImageSize(String base64Image) throws IOException {
        // Dekodowanie Base64 do bajtów
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Tworzenie BufferedImage z bajtów
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

        // Pobieranie rozmiaru obrazu z BufferedImage
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        return width + height;
    }


    public void appendToChatTextPane(String message) {
        HTMLDocument doc = (HTMLDocument) panel.chatTextPane.getDocument();
        HTMLEditorKit kit = (HTMLEditorKit) panel.chatTextPane.getEditorKit();

        try {
            kit.insertHTML(doc, doc.getLength(), message, 0, 0, null);
            panel.chatTextPane.setCaretPosition(doc.getLength());
        } catch (IOException | BadLocationException e) {
            e.printStackTrace();
        }
        panel.chatTextPane.setCaretPosition(doc.getLength());
    }


    void appendVideoLink(File filePath, String videoFileName) throws BadLocationException {

        HTMLDocument doc = (HTMLDocument) panel.chatTextPane.getDocument();
        try {
            // Sprawdź, czy treść istnieje w dokumencie
            if (doc.getLength() > 0) {
                // Dodaj nową linię przed nową wiadomością
                doc.insertAfterEnd(doc.getCharacterElement(doc.getLength() - 1), "<br><a href='" + "file://"
                        + filePath.getAbsolutePath() + "'>OTWÓRZ PLIK</a>" + " o nazwie " + videoFileName); //
            }

            // Dodaj wiadomość do dokumentu HTML
            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), "");
        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }
        panel.chatTextPane.setCaretPosition(doc.getLength());
    }

    void appendLinkToImage(File filePath, String FileName) throws BadLocationException {

        HTMLDocument doc = (HTMLDocument) panel.chatTextPane.getDocument();
        try {
            // Sprawdź, czy treść istnieje w dokumencie
            if (doc.getLength() > 0) {
                // Dodaj nową linię przed nową wiadomością
                doc.insertAfterEnd(doc.getCharacterElement(doc.getLength() - 1), "<br><a href='" + "file://"
                        + filePath.getAbsolutePath() + "'>OTWÓRZ PLIK</a>" + " o nazwie " + FileName); //
            }

            // Dodaj wiadomość do dokumentu HTML
            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), "");
        } catch (BadLocationException | IOException e) {
            e.printStackTrace();
        }
        panel.chatTextPane.setCaretPosition(doc.getLength());
    }


}
