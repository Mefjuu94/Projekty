import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.lang.Math.round;

public class ScoreBoard {
    int Width ;
    int Height ;
    String NajlepszyGracz ;
    String NajlepszyWynik ;
    double NajlepszyWynikDouble ;
    Player gracz[];
    int ktoryGraczGra ;

    public ScoreBoard() throws FileNotFoundException {
        WczytajNajlepszyWynik();

    }

    public void WczytajNajlepszyWynik() throws FileNotFoundException {
        Scanner score = new Scanner(new File("C:\\Users\\mateu\\OneDrive\\Pulpit\\PLIKI Z LEKCJI\\score.txt"));
        String linijka = "";
        while (score.hasNextLine()) {
            linijka = score.nextLine();
            String[] split = linijka.split(" ");
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

        if (this.gracz != null) {
            for (int i = 1; i < gracz.length; i++) {
                g2d.setColor(Color.black);

                g2d.drawString("gracz: " + gracz[i].name + " wynik " + gracz[i].score, 50, y);
                y += 20;
            }
            g2d.drawString("który gracz gra: " + gracz[ktoryGraczGra].name, Width - 400, Height - 50); // zrobic
        }
        
        g2d.drawRect(45, Height - 200, Width / 2 - 300, 150);


        //NAJLEPSZY WYNIK!!
        g2d.drawString("Najlepszy gracz: " + NajlepszyGracz + "   Wynik: " + NajlepszyWynik, 50, Height - 10);
    }

    public void setScoreIfNeeded(Player gracz) throws FileNotFoundException {
        if (gracz.score < NajlepszyWynikDouble) {
            return;
        }
        PrintWriter zapis = new PrintWriter("C:\\Users\\mateu\\OneDrive\\Pulpit\\PLIKI Z LEKCJI\\score.txt");
        String graczS = String.valueOf(this.gracz[ktoryGraczGra].name);

        double round = round(gracz.score);
        System.out.println(round + "round");

        String wynikS = Double.toString(round);
        System.out.println("graczS" + graczS);
        System.out.println("WynikS " + wynikS);
        zapis.println(graczS + " " + wynikS);
        zapis.close();
        System.out.println("Twoj wynik to: " + wynikS);
    }
}
