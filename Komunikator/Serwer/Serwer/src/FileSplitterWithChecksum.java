import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class FileSplitterWithChecksum {
    private String lol = "C:\\Users\\mateu\\Videos\\4K Video Downloader+\\Java Full Course for free ☕.mp4";
    private String inputVideoPath;// = lol;//"C:\\Users\\mateu\\Videos\\4K Video Downloader+\\Java Swing event handling.mp4";
    private String outputDirectory;// = "C:\\Users\\mateu\\OneDrive\\Pulpit\\Projekty\\Komunikator\\klient\\Client\\Pliki\\";
    private int numberOfParts = 6;
    public ArrayList<String> partFilesName = new ArrayList<>();

    FileSplitterWithChecksum(String inputVideoPath) {
        this.inputVideoPath = inputVideoPath;
        outputDirectory = "C:\\Users\\mateu\\OneDrive\\Pulpit\\Projekty\\Komunikator\\klient\\Client\\Pliki\\";
    }

    public void SplitFile(){
        try {
            splitVideo(inputVideoPath, outputDirectory, numberOfParts);
            calculateAndPrintChecksums(outputDirectory);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void splitVideo(String inputVideoPath, String outputDirectory, int parts) throws IOException, NoSuchAlgorithmException {
        try (FileInputStream fis = new FileInputStream(inputVideoPath)) {
            File outputDir = new File(outputDirectory);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }

            long fileSize = new File(inputVideoPath).length();
            long partSize = fileSize / parts;

            byte[] buffer = new byte[1024];
            int bytesRead;

            for (int i = 1; i <= parts; i++) {
                String outputFilePath = outputDirectory + "part" + i + ".mp4";
                try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                    long bytesWritten = 0;

                    while (bytesWritten < partSize && (bytesRead = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                        bytesWritten += bytesRead;
                    }

                    // Oblicz sumę kontrolną dla każdej części i zapisz do pliku
                    String checksum = calculateChecksum(new File(outputFilePath));
                    writeChecksumToFile(outputFilePath, checksum);
                }
                System.out.println("część " + i + " gotowa");
            }
        }
    }

    private String calculateChecksum(File file) throws IOException, NoSuchAlgorithmException {
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }

            byte[] digest = md.digest();
            StringBuilder result = new StringBuilder();

            for (byte b : digest) {
                result.append(String.format("%02x", b));
            }

            return result.toString();
        }
    }

    private void writeChecksumToFile(String filePath, String checksum) throws IOException {
        try (FileWriter writer = new FileWriter(filePath + ".md5")) {
            writer.write(checksum);
        }
    }

    private void calculateAndPrintChecksums(String outputDirectory) throws IOException, NoSuchAlgorithmException {
        File dir = new File(outputDirectory);
        File[] files = dir.listFiles();


        if (files != null) {
            for (File file : files) {
                partFilesName.add(file.getName());
                if (file.getName().endsWith(".mp4")) {
                    String checksum = calculateChecksum(file);
                    System.out.println(file.getName() + ": " + checksum);
                }
            }
        }
        System.out.println(partFilesName);
    }
}
