import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileMergerWithChecksumVerification {
    public String fileName = "";
    public String mergedFilePath = "";
    // Pobierz bieżącą ścieżkę katalogu, w którym uruchomiona jest aplikacja
    String biezacyKatalog = System.getProperty("user.dir") + "\\";

    public String outputDirectory = biezacyKatalog + "Pliki\\";
    FileMergerWithChecksumVerification() {

    }

    public void scalPliki(String fileName){

        int numberOfParts = 6;
        this.fileName = fileName;
        try {
            // Utwórz katalog, jeśli nie istnieje
            File outputDir = new File(outputDirectory);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            String extension = fileName.substring(fileName.length()-4);

            // Sprawdź, czy plik już istnieje, i zmień nazwę, aby uniknąć nadpisania
            mergedFilePath = getUniqueFilename(outputDirectory, fileName);

            mergeFiles(numberOfParts, mergedFilePath,outputDirectory,extension);
            verifyChecksums(outputDirectory,extension);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    private void mergeFiles(int numberOfParts, String mergedFilePath, String outputDirectory,String extension) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(mergedFilePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            for (int i = 1; i <= numberOfParts; i++) {
                String inputFilePath = outputDirectory + "part" + i + extension;
                try (FileInputStream fis = new FileInputStream(inputFilePath)) {
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
            }
        }
    }

    private void verifyChecksums(String outputDirectory,String extension) throws IOException, NoSuchAlgorithmException {
        File dir = new File(outputDirectory);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(extension)) {
                    String checksumFilepath = file.getAbsolutePath() + ".md5";
                    String expectedChecksum = readChecksumFromFile(new File(checksumFilepath));

                    String actualChecksum = calculateChecksum(file);

                    if (!expectedChecksum.equals(actualChecksum)) {
                        throw new IOException("Checksum mismatch for file: " + file.getName());
                    }

                    System.out.println("wsyztsko OK");
                }
            }
        }
    }

    private String readChecksumFromFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
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

    private String getUniqueFilename(String directory, String filename) {
        File file = new File(directory, filename);
        int counter = 1;

        while (file.exists()) {
            String baseName = filename.substring(0, filename.lastIndexOf('.'));
            String extension = filename.substring(filename.lastIndexOf('.'));

            String newFilename = baseName + "_" + counter + extension;
            file = new File(directory, newFilename);
            counter++;
        }

        return file.getAbsolutePath();
    }
}


//
//Ustalenie Ścieżek i Liczby Części:
//
//        W main ustalane są ścieżki do katalogu wyjściowego (outputDirectory), liczba części (numberOfParts), i ścieżka docelowa dla połączonego pliku (mergedFilePath).
//        Utworzenie Katalogu Wyjściowego:
//
//        Sprawdza, czy katalog wyjściowy już istnieje. Jeśli nie, tworzy go.
//        Zabezpieczenie przed Nadpisaniem Pliku Wynikowego:
//
//        Używając metody getUniqueFilename, sprawdza, czy plik docelowy (mergedFilePath) już istnieje. Jeśli tak, dodaje sufiks liczbowy, aby utworzyć unikalną nazwę pliku.
//        Scalenie Plików:
//
//        W metodzie mergeFiles otwiera FileOutputStream do pliku wynikowego (połączonego) i iteruje przez każdą część pliku, odczytując dane i zapisując je do pliku wynikowego.
//        Weryfikacja Sum Kontrolnych:
//
//        W metodzie verifyChecksums przechodzi przez wszystkie pliki w katalogu wyjściowym.
//        Dla każdego pliku z rozszerzeniem .mp4, wczytuje oczekiwaną sumę kontrolną z pliku .md5 i oblicza aktualną sumę kontrolną za pomocą funkcji calculateChecksum.
//        Porównuje obie sumy kontrolne. Jeśli nie są równe, program rzuca wyjątek IOException z informacją o niezgodności sum kontrolnych.
//        Odczyt Sumy Kontrolnej z Pliku:
//
//        W metodzie readChecksumFromFile odczytuje sumę kontrolną z pliku .md5 i zwraca ją jako string.
//        Obliczanie Sumy Kontrolnej:
//
//        W metodzie calculateChecksum oblicza sumę kontrolną pliku za pomocą algorytmu MD5. Odczytuje dane pliku i aktualizuje funkcję skrótu (MessageDigest) iteracyjnie.
//        Tworzenie Unikalnej Nazwy Pliku:
//
//        W metodzie getUniqueFilename dodaje sufiks liczbowy do nazwy pliku, aby uzyskać unikalną nazwę. Iteracyjnie sprawdza, czy plik o danej nazwie już istnieje, i zwiększa licznik, aż znajdzie unikalną nazwę.
//        Obsługa Wyjątków:
//
//        Program obsługuje wyjątki IOException i NoSuchAlgorithmException, wypisując informacje o błędach na standardowe wyjście błędów (e.printStackTrace()).
//        Kod ten łączy pliki, weryfikuje sumy kontrolne i zabezpiecza przed nadpisaniem pliku wynikowego. Mam nadzieję, że to pomaga w zrozumieniu, jak działa ten program. Jeśli masz jakiekolwiek pytania dotyczące konkretnych kroków, śmiało pytaj!
