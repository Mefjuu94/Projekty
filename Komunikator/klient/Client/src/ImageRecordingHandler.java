import com.xuggle.mediatool.event.IFlushEvent;

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
    Thread BuildingVideoThread;
    Panel panel;
    Runnable buildVideoThreadRun;

    String videoname ;
    String pathImeges ;

    public ImageRecordingHandler(JFrame frame, Panel panel,String videoname,String pathImages, Thread buildingVideoThread) {
        this.frame = frame;
        this.videoname = videoname;
        this.panel = panel;
        this.pathImeges = pathImages;
        this.BuildingVideoThread = buildingVideoThread;
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
        } else {
            startRecording();
        }
    }

    private void startRecording() {
        isRecording = true;
        System.out.println("Rozpoczęto nagrywanie.");

        recordingThread = new Thread(() -> {
            while (isRecording) {
                recordFrame();
            }
        });

        recordingThread.start();

    }

    private void stopRecording() {

        buildVideoThreadRun = () -> {

            isRecording = false;
            try {
                recordingThread.join(); // Poczekaj na zakończenie wątku nagrywania
            } catch (
                    InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Zakończono nagrywanie.");

            try {
                JCodecPNGtoMP4 codecPNGtoMP4 = new JCodecPNGtoMP4(videoname, pathImeges);
                codecPNGtoMP4.makeVideo();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            deleteImages(imageFiles);
        };
        BuildingVideoThread = new Thread(buildVideoThreadRun);
        BuildingVideoThread.start();

    }


    private void recordFrame() {
        BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (frame.getWidth() %2 != 0 || frame.getHeight()%2 != 0){
            frameDimension();
        }
        frame.paint(image.getGraphics());
        frame.repaint();

        try {
            Thread.sleep(50); // 10 = Nagrywanie co 100 milisekund (10 klatek na sekundę) - 50 = 20kl/s ->1000/20 = 50
            File outputFile = new File("capture\\frame_" + frameNumber + ".jpg");
            ImageIO.write(image, "jpeg", outputFile);
            frameNumber++;
            imageFiles.add(outputFile.getName());
            frame.repaint(); //-> powodowało artefakty podczas nagrywania

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

    private void frameDimension(){
        if (frame.getHeight() %2 != 0){
            frame.setSize(frame.getWidth(),(frame.getHeight()/2) * 2);
        }
        if (frame.getWidth() %2 != 0){
            frame.setSize((frame.getWidth()/2)*2,frame.getHeight());
        }
        if (frame.getWidth() %2 != 0 || frame.getHeight()%2 != 0){
            frameDimension();
        }
    }


}
