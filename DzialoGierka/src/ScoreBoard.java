import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScoreBoard {
    double wynik;
    int Width = 1200;
    int Height = 800;
    String split[];
    String NajlepszyGracz ;
    String NajlepszyWynik ;
    double NajlepszyWynikDouble ;
    Player gracz[];
    public ScoreBoard() throws FileNotFoundException {
        NajlepszyWynik();
    }

    public void NajlepszyWynik() throws FileNotFoundException {
        Scanner score = new Scanner(new File("C:\\Users\\mateu\\OneDrive\\Pulpit\\PLIKI Z LEKCJI\\score.txt"));
        String linijka = "";
        while (score.hasNextLine()) {
            linijka = score.nextLine();
            split = linijka.split(" ");
            NajlepszyGracz = split[0];
            NajlepszyWynik = split[1];
            NajlepszyWynikDouble = Double.parseDouble(split[1]);
        }
    }
    public void setPlayers(Player[] players){
        this.gracz = players;
    }

    public void paint(Graphics2D g2d) {

        int y = Height - 180;

        for (int i = 1; i <= gracz.length; i++) {
            g2d.setColor(Color.black);
            if (gracz.length > 1) {
                g2d.drawString("gracz: " + gracz[i].name + " wynik " + gracz[i].score, 50, y);
                y += 20;
            } else {
                g2d.drawString("gracz: " + gracz[i].name + " wynik " + gracz[i].score, 50, y);
            }
        }
        g2d.drawRect(45, Height - 200, Width / 2 - 300, 150);

        //NAJLEPSZY WYNIK!!
        g2d.drawString("Najlepszy gracz: " + NajlepszyGracz + "   Wynik: " + NajlepszyWynik, 50, Height - 10);

    }
}
