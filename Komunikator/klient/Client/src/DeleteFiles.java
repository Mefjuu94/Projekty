import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class DeleteFiles {

    // Ścieżka do pliku, który chcesz usunąć
    private String sciezkaDoPliku = "";


    DeleteFiles() {

    }

    public void DeleteFile(String sciezkaDoPliku){
        this.sciezkaDoPliku = sciezkaDoPliku;

        try {
            // Utwórz obiekt Path na podstawie ścieżki do pliku
            Path sciezka = Path.of(sciezkaDoPliku);

            // Usuń plik
            Files.delete(sciezka);

            System.out.println("Plik został usunięty pomyślnie.");
        } catch (
                IOException e) {
            System.err.println("Plik nie istnieje.");
        }
    }
}
