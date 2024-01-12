import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageRecordingHandler {

    private boolean isRecording = false;
    private Thread recordingThread;
    private int frameNumber = 0;
    JFrame frame;
    List<String> imageFiles = new ArrayList<>();
    public Thread BuildingVideoThread;
    Panel panel;

    String videoname ;
    String pathImeges ;

    public ImageRecordingHandler(JFrame frame, Panel panel,String videoname,String pathImages) {
        this.frame = frame;
        this.videoname = videoname;
        this.panel = panel;
        this.pathImeges = pathImages;
    }

    public void record(boolean isRecording) {
        try {
            toggleRecording(isRecording);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }



    public void toggleRecording(boolean buttonStartStop) throws InterruptedException {
        if (isRecording) {
            stopRecording();
            BuildingVideoThread.join();
            deleteImages(imageFiles);
        } else {
            startRecording();
        }
    }

    private void startRecording() {
        isRecording = true;
        System.out.println("Rozpoczęto nagrywanie.");

        recordingThread = new Thread(() -> {
            while (isRecording) {
                recordFrame(frame);
            }
        });

        recordingThread.start();
    }

    private void stopRecording() {

        Runnable buildVideoThreadRun = () -> {

            isRecording = false;
            try {
                recordingThread.join(); // Poczekaj na zakończenie wątku nagrywania
            } catch (
                    InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Zakończono nagrywanie.");

            try {
                System.out.println(videoname + " przed kodeikiem");
                JCodecPNGtoMP4 codecPNGtoMP4 = new JCodecPNGtoMP4(videoname, pathImeges);
                codecPNGtoMP4.makeVideo();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        };
        BuildingVideoThread = new Thread(buildVideoThreadRun);
        BuildingVideoThread.start();

    }


    private void recordFrame(JFrame Myframe) {
        JFrame frame = Myframe;
        BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        frame.paint(image.getGraphics());

        try {
            Thread.sleep(50); // 10 = Nagrywanie co 100 milisekund (10 klatek na sekundę) - 50 = 20kl/s ->1000/20 = 50
            File outputFile = new File("capture\\frame_" + frameNumber + ".jpg");
            ImageIO.write(image, "jpeg", outputFile);
            frameNumber++;
            imageFiles.add(outputFile.getName());
            frame.repaint();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void deleteImages(List<String> imageFiles) {
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
        JOptionPane.showMessageDialog(frame, "Wideo zostało zapisane.");
    }

}
