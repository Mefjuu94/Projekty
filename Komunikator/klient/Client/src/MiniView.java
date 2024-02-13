import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class MiniView extends JPanel {
    JLabel label;
    EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
    AudioPlayerComponent audioPlayerComponent = new AudioPlayerComponent();

    MiniView() {

    }

    public void displayIMG(String url, JPanel minipanel) throws IOException {

        String base64Image = "";

        if (Desktop.isDesktopSupported()) {
            File file = new File(url);
            byte b[] = Files.readAllBytes(file.toPath());
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

        minipanel.add(mediaPlayerComponent);

        mediaPlayerComponent.setPreferredSize(new Dimension(350,250));
        mediaPlayerComponent.setVisible(true);

        File file = new File(url);
        String mediaPath = file.getAbsolutePath();

        mediaPlayerComponent.audioDeviceChanged(null,null);
        mediaPlayerComponent.mediaPlayer().media().startPaused(mediaPath);
        mediaPlayerComponent.mediaPlayer().controls().play();

    }

    public void stopVideo()  {

        mediaPlayerComponent.mediaPlayer().controls().stop();

    }

    public void prelistenAudio(String url, JPanel minipanel, String name) throws IOException {    ///dokończyć w wolnym czasie
        JPanel audioControlsPane = new JPanel();

        File file = new File(url);
        String mediaPath = file.getAbsolutePath();

        audioPlayerComponent.mediaPlayer().media().startPaused(mediaPath);
        audioPlayerComponent.mediaPlayer().controls().play();
        System.out.println("powinno grać!");

        ImageIcon playingGif = new ImageIcon("resourceFilesImgGif/playGif.gif");
        JLabel playlabel = new JLabel(playingGif);
        playlabel.setText(name);
        playlabel.setVerticalTextPosition(JLabel.TOP);
        playlabel.setHorizontalTextPosition(JLabel.CENTER);

        audioControlsPane.add(playlabel);                       /// ogarnąć wyświetlanie nazwy\
        minipanel.add(audioControlsPane, BorderLayout.SOUTH);

    }

    public void stopAudio()  {
        audioPlayerComponent.mediaPlayer().controls().stop();

    }
}
