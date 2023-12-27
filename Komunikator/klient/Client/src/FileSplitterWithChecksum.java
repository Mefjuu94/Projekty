import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class FileSplitterWithChecksum {
    private String inputVideoPath;
    private String outputDirectory;
    private int numberOfParts = 6;
    public ArrayList<String> partFilesName = new ArrayList<>();

    // Pobierz bieżącą ścieżkę katalogu, w którym uruchomiona jest aplikacja
    String biezacyKatalog = System.getProperty("user.dir") + "\\";

    FileSplitterWithChecksum(String inputVideoPath) {
        this.inputVideoPath = inputVideoPath;
        outputDirectory = biezacyKatalog + "Pliki\\";
    }

    public void SplitFile(){
        String extension = inputVideoPath.substring(inputVideoPath.length()-4);
        try {
            splitVideo(inputVideoPath, outputDirectory, numberOfParts,extension);
            calculateAndPrintChecksums(outputDirectory);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void splitVideo(String inputVideoPath, String outputDirectory, int parts,String extension) throws IOException, NoSuchAlgorithmException {
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
                String outputFilePath = outputDirectory + "part" + i + extension;
                try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                    partFilesName.add("part" + i + extension);
                    partFilesName.add("part" + i + extension + ".md5");
                    long bytesWritten = 0;

                    while (bytesWritten < partSize && (bytesRead = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                        bytesWritten += bytesRead;
                    }

                    // Oblicz sumę kontrolną dla każdej części i zapisz do pliku
                    String checksum = calculateChecksum(new File(outputFilePath));
                    writeChecksumToFile(outputFilePath, checksum);
                }

                //System.out.println("część " + i + " gotowa");
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
                if (file.getName().endsWith(".mp4")) {
                    String checksum = calculateChecksum(file);
                    System.out.println(file.getName() + ": " + checksum);
                }
            }
        }
    }
}
