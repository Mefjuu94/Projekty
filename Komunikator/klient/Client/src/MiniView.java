import com.google.common.io.Files;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

public class MiniView {
    JLabel label;
    MiniView() {

    }

    public void displayIMG(String url, JPanel minipanel) throws IOException {

        String base64Image = "";

        if (Desktop.isDesktopSupported()) {
            File file = new File(url);
            byte b[] = Files.toByteArray(file);
            base64Image = Base64.getEncoder().encodeToString(b);
        }


        // Dekodowanie Base64 do obrazka
        ImageIcon imageIcon = new ImageIcon(Base64.getDecoder().decode(base64Image));

        // Dostosowanie obrazka do żądanych rozmiarów
        Image scaledImage = imageIcon.getImage().getScaledInstance(350, 250, Image.SCALE_FAST);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        // Tworzenie JLabel jako kontenera dla obrazka
        label = new JLabel(scaledImageIcon);
        minipanel.add(label);


        // Ustawianie preferowanego rozmiaru JLabel (rozmiar obrazka)
        label.setPreferredSize(new Dimension(350, 250));

    }

    public void displayVideo(String url, JPanel minipanel) throws IOException {

        String base64Image = "";

        if (Desktop.isDesktopSupported()) {
            File file = new File(url);
            byte b[] = Files.toByteArray(file);
            base64Image = Base64.getEncoder().encodeToString(b);
        }


        // Dekodowanie Base64 do obrazka
        ImageIcon imageIcon = new ImageIcon(Base64.getDecoder().decode(base64Image));

        // Dostosowanie obrazka do żądanych rozmiarów
        Image scaledImage = imageIcon.getImage().getScaledInstance(350, 250, Image.SCALE_FAST);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        // Tworzenie JLabel jako kontenera dla obrazka
        label = new JLabel(scaledImageIcon);
        minipanel.add(label);


        // Ustawianie preferowanego rozmiaru JLabel (rozmiar obrazka)
        label.setPreferredSize(new Dimension(350, 250));

    }


}
