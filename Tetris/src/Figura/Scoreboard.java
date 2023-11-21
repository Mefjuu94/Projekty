package Figura;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Scoreboard {
    Scanner scanner;
    public String nickname = "";
    String[][] podium = new String[3][3];
    ImageIcon goldTrophy = new ImageIcon("src/Figura/img/gold.png");
    ImageIcon silverTrophy = new ImageIcon("src/Figura/img/silver.png");
    ImageIcon bronzeTrophy = new ImageIcon("src/Figura/img/bronze.png");
    FileWriter writer = new FileWriter("Save.txt", true);

    public Scoreboard() throws IOException {
        for (int i = 0; i < podium.length; i++) {
            for (int j = 0; j < podium[0].length; j++) {
                podium[i][j] = "0";
            }
        }
    }

    public void draw(Graphics2D g2d, JPanel panel) throws InterruptedException, IOException {
        drawBestScores(g2d);
        goldTrophy.paintIcon(panel, g2d, 360, 160);
        silverTrophy.paintIcon(panel, g2d, 360, 260);
        bronzeTrophy.paintIcon(panel, g2d, 360, 350);
    }

    public void drawBestScores(Graphics2D g2d) {

        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);

        int xScore = 100;
        int yScore = 200;
        for (int i = 0; i < podium.length; i++) {
            g2d.drawString(i + 1 + ". ", 80, yScore);
            for (int j = 0; j < podium.length - 1; j++) {
                g2d.drawString("  " + podium[i][j] + "    ", xScore, yScore);
                xScore += 150;
            }
            xScore = 100;
            yScore += 100;
        }
    }

    public void saveScore(JPanel panel, long result, int scoreLineCounter) throws Exception {
        System.out.println("save score " + result);
        if (result > 0 || scoreLineCounter > 0) {

            nickname = JOptionPane.showInputDialog(panel, "Game Over", 0);
            System.out.println(nickname);
            writer.write("\n" + nickname + " " + result + " points: " + scoreLineCounter + " lines");
            writer.close();
        }

        //Gemail działający!!:

        String recipent = JOptionPane.showInputDialog(panel, "Your Email", 0);
        GEmailSender gEmailSender = new GEmailSender();
//
        String text = "wysyłam Ci z mojej własnej aplikacji załącznik!! :)";//"Twój wynik to " + result + " punktów, jeśli nie jesteś pierwszy postaraj się bardziej, Mail testowy o2";
        String subject = "Email z aplikacji";
        String to =  "aniolrav@gmail.com";    //"mateusz94orzel@gmail.com";
        String from = "adameagle@o2.pl";//"qasa21@gmail.com"; "orzel990@vp.pl" Adameagle@o2.pl "testlol@int.pl"
////
        boolean b =  gEmailSender.sendEmail(from,to,subject,text);

        if (b){
            System.out.println("Email is sent succesfully");
        }else {
            System.out.println("Nie udało się wysłać emaila");
        }
    //////////////////

        //simple mail
//        EmailSimple emailSimple = new EmailSimple();
//
//        emailSimple.sendSimpleEmail(from,"Mateusz1",from,to,text,subject);
//////////////////////////simple

        loadScores();

    }

    public void loadScores() throws FileNotFoundException {

        scanner = new Scanner(new File("Save.txt"));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] split = line.split(" ");

            int liczba = Integer.parseInt(split[1]);
            System.out.println(liczba + " liczba" + " nazwa " + split[0]);


            for (int i = 0; i < podium.length; i++) {
                int liczbaScore = Integer.parseInt(podium[i][1]);
                //System.out.println(liczbaScore + " liczbaScore");

                if (liczba > liczbaScore) {

                    if (i + 2 < podium.length) {
                        podium[i + 2][0] = podium[i + 1][0]; // jak index + 1 jest mniejszy od dl. tablicy podium
                        podium[i + 2][1] = podium[i + 1][1]; // to przesuń wiersz o jeden do góry
                        podium[i + 2][2] = podium[i + 1][2];
                    }

                    if (i + 1 < podium.length) {
                        podium[i + 1][0] = podium[i][0]; // jak index + 1 jest mniejszy od dl. tablicy podium
                        podium[i + 1][1] = podium[i][1]; // to przesuń wiersz o jeden do góry
                        podium[i + 1][2] = podium[i][2];
                    }
                    podium[i][0] = split[0];
                    podium[i][1] = split[1];
                    podium[i][2] = split[3];
                    break;   //  w razie ifa sprzerwij iterację pętli
                }
            }

        }

        scanner.close();

        System.out.println("----------------wypełniona tablica----------");

        for (int i = 0; i < podium.length; i++) {

            for (int j = 0; j < podium[0].length; j++) {
                System.out.print(podium[i][j] + ' ');
            }
            System.out.println();
        }
    }
}

