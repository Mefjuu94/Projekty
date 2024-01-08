import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageRecordingHandler implements ActionListener {

    private boolean isRecording = false;
    private Thread recordingThread;
    private int frameNumber = 0;
    JFrame frame;
    List<String> imageFiles = new ArrayList<>();
    public Thread BuildingVideoThread;
    JButton buttonStartStop;

    public ImageRecordingHandler(JFrame frame, JButton buttonStartStop) {
        this.frame = frame;
        this.buttonStartStop = buttonStartStop;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            toggleRecording(buttonStartStop);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void toggleRecording(JButton buttonStartStop) throws InterruptedException {
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
            buttonStartStop.setText("Stop recording!");
            while (isRecording) {
                recordFrame(frame);
            }
        });

        recordingThread.start();
    }

    private void stopRecording() throws InterruptedException {
        BuildingVideoThread = new Thread(() -> {
            VideoBuilder videoBuilder = new VideoBuilder(frame.getWidth(),frame.getHeight(),imageFiles,frame);
        });

        isRecording = false;
        buttonStartStop.setText("Start Recording!");
        try {
            recordingThread.join(); // Poczekaj na zakończenie wątku nagrywania
            BuildingVideoThread.start();
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Zakończono nagrywanie.");

    }

    private void recordFrame(JFrame Myframe) {
        JFrame frame = Myframe;
        BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
        frame.paint(image.getGraphics());

        try {
            Thread.sleep(100); // 10 = Nagrywanie co 100 milisekund (10 klatek na sekundę)
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



}
