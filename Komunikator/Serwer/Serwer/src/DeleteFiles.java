import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

            // Usuń pli
            Files.delete(sciezka);

            System.out.println("Plik " + sciezkaDoPliku + " został usunięty pomyślnie.");
        } catch (
                NoSuchFileException e) {
            System.err.println("Plik nie istnieje.");
        } catch (
                IOException e) {
            System.err.println("Wystąpił błąd podczas usuwania pliku: " + e.getMessage());
        }
    }
}
