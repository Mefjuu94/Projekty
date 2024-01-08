import com.xuggle.mediatool.IMediaViewer;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static com.xuggle.xuggler.Global.DEFAULT_TIME_UNIT;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class VideoBuilder {
    VideoBuilder(int width, int height, List<String> imageFiles,JFrame JpanelFrame) {
        // the clock time of the next frame

        long nextFrameTime = 0;

        // video parameters

        final int videoStreamIndex = 0;
        final int videoStreamId = 0;
        final long frameRate = DEFAULT_TIME_UNIT.convert(100, MILLISECONDS);

        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        String localDateTime = ldt.toString();
        String finalLDT = localDateTime.replace(":","_");
        String Filename = "video" + finalLDT.substring(0,16) + ".mov";

        try {

            final IMediaWriter writer = ToolFactory.makeWriter(Filename);

//            writer.addListener(ToolFactory.makeViewer(
//                    IMediaViewer.Mode.VIDEO_ONLY, false,
//                    javax.swing.WindowConstants.EXIT_ON_CLOSE));  pokazanie okna

            writer.addVideoStream(videoStreamIndex, videoStreamId, width, height);

            File dir = new File("capture\\");
            for (File f : dir.listFiles()) {
                BufferedImage frame = ImageIO.read(f);
                writer.encodeVideo(videoStreamIndex, frame, nextFrameTime, DEFAULT_TIME_UNIT);
                nextFrameTime += frameRate;
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        deleteImages(imageFiles,JpanelFrame);
    }

    public void deleteImages(List<String> imageFiles,JFrame frame) {
        System.out.println("Usuwanie zdjęć po zakończeniu nagrywania.");
        for (String imageName : imageFiles) {
            File imageFile = new File("capture", imageName);
            if (imageFile.exists()) {
                if (!imageFile.delete()) {
                    System.out.println("Nie można usunąć: " + imageName);
                }
            }
        }
        System.out.println("zakończono proces usuwania klatek");
        JOptionPane.showMessageDialog(frame,"zapisano wideo w folderze Programu");
    }
}
