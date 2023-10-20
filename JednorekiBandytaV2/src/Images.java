import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class Images {

    ImageIcon maszyna = new ImageIcon("src/image/MaszynaWyciete.png");

    ImageIcon jeden = new ImageIcon("src/image/1.jpg");
    ImageIcon dwa = new ImageIcon("src/image/2.jpg");
    ImageIcon trzy = new ImageIcon("src/image/3.jpg");
    ImageIcon cztery = new ImageIcon("src/image/4.jpg");
    ImageIcon piec = new ImageIcon("src/image/5.jpg");
    ImageIcon szesc = new ImageIcon("src/image/6.jpg");
    ImageIcon siedem = new ImageIcon("src/image/7.jpg");
    ImageIcon osiem = new ImageIcon("src/image/8.jpg");
    ImageIcon dziewiec = new ImageIcon("src/image/9.jpg");
    ImageIcon dziesiec = new ImageIcon("src/image/10.jpg");
    ImageIcon jedenasie = new ImageIcon("src/image/11.jpg");
    ImageIcon dwanascie = new ImageIcon("src/image/12.jpg");
    ImageIcon trzynascie = new ImageIcon("src/image/13.jpg");
    ImageIcon czternascie = new ImageIcon("src/image/14.jpg");
    ImageIcon pietnascie = new ImageIcon("src/image/15.jpg");
    ImageIcon szesnascie = new ImageIcon("src/image/16.jpg");

    ImageIcon[] all = {jeden, dwa, trzy, cztery, piec, szesc, siedem, osiem, dziewiec, dziesiec, jedenasie, dwanascie, trzynascie,
            czternascie, pietnascie, szesnascie};
    String[] obrazy = {"wiśnia", "śliwka", "dzwon", "koniczyna", "cytryna", "pieniądz", "bar", "jabłko", "serce", "winogrona",
            "diament", "pomarańcza", "podkowa", " siódemka", "znak Zapytania", " arbuz"};

    double speed = 0;
    boolean predkoscStala = true;
    int mozliwosci = 2; // z ilu obrazów ma losować

    ImageIcon[] rollFirst = new ImageIcon[15];
    int xFirst;
    double[] yFirst = new double[15];
    ImageIcon[] rollSecond = new ImageIcon[15];
    int xSecond;
    double[] ySecond = new double[15];
    ImageIcon[] rollThrid = new ImageIcon[15];
    int xthrid;
    double[] yThrid = new double[15];
    int ods = 115;


    boolean sprawdzWynik = false;

    ImageIcon[][] wygraneZPlusem = new ImageIcon[3][3];

    public Images() throws IOException {
    }

    public void RysujMaszyne(JPanel panel,Graphics2D g2d){
        maszyna.paintIcon(panel,g2d,0,0);

    }

    public void Sortuj() {

        Random random = new Random();

            for (int i = 0; i < rollFirst.length; i++) {
                rollFirst[i] = all[random.nextInt(mozliwosci)];
                yFirst[i] = 530 - (ods * i);
            }
            for (int i = 0; i < rollSecond.length; i++) {
                rollSecond[i] = all[random.nextInt(mozliwosci)];
                ySecond[i] = 530 - (ods * i);
            }
            for (int i = 0; i < rollThrid.length; i++) {
                rollThrid[i] = all[random.nextInt(mozliwosci)];
                yThrid[i] = 530 - (ods * i);
            }

    }

    public void RysujTasmeNaNowo(double[] yFirst) {

        if (yFirst[0] > 1780) {
            for (int i = 0; i < rollFirst.length; i++) {
                yFirst[i] = 530 - (ods * i);
            }
        }
    }

    public void Roll(Graphics2D g2d,JPanel panel) {

        for (int i = 0; i < rollFirst.length; i++) {

                //rysuj tylko kiedy znajduje się... obręb masszyny
                if (yFirst[i] > 220 && yFirst[i] < 820) {
                    rollFirst[i].paintIcon(panel, g2d, xFirst, (int) yFirst[i]);
                    rollSecond[i].paintIcon(panel, g2d, xSecond, (int) ySecond[i]);
                    rollThrid[i].paintIcon(panel, g2d, xthrid, (int) yThrid[i]);
                }

                yFirst[i] += 0.3; // 0.3
                ySecond[i] += 0.3;
                yThrid[i] += 0.3;
                RysujTasmeNaNowo(yFirst);
                RysujTasmeNaNowo(ySecond);
                RysujTasmeNaNowo(yThrid);
        }
        predkoscStala = true;
        speed = 0.3;
    }

    public void Stop(JPanel panel,Graphics2D g2d){
        for (int i = 0; i < rollFirst.length; i++) {

            if (yFirst[i] > 220 && yFirst[i] < 820) {
                rollFirst[i].paintIcon(panel, g2d, xFirst, (int) yFirst[i]);
                rollSecond[i].paintIcon(panel, g2d, xSecond, (int) ySecond[i]);
                rollThrid[i].paintIcon(panel, g2d, xthrid, (int) yThrid[i]);
            }
            // formula procetna !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            double kol =  ((yFirst[0] / 1905) * 100);
            double kol2 = 100 - kol;


//            System.out.println(kol2);
//            System.out.println("speed " + speed);

            if (yFirst[0] <= 1905) {
                yFirst[i] += speed;
                ySecond[i] += speed;
                yThrid[i] += speed;
                speed = (kol2 / 100);
                if (kol2 < 3){
                    speed = 0.05;
                    stopUj(yFirst, i, rollFirst, 0);
                    stopUj(ySecond, i, rollSecond, 1);
                    stopUj(yThrid, i, rollThrid, 2);
                }

            } else {
                speed = 0;
                stopUj(yFirst, i, rollFirst, 0);
                stopUj(ySecond, i, rollSecond, 1);
                stopUj(yThrid, i, rollThrid, 2);

                if (speed <= 0.03 ) {
                    sprawdzWynik = true;
                }
            }

        }

    }


    public void stopUj(double[] yFirst, int i, ImageIcon[] rollFirst, int j) {

        if (yFirst[i] > 275 && yFirst[i] < 395) {
            //System.out.println(rollFirst[i] + " pierwszy wiersz");
            wygraneZPlusem[j][0] = rollFirst[i];
            //System.out.println(j);
        }

        if (yFirst[i] >= 400 && yFirst[i] < 515) {
            //System.out.println(rollFirst[i] + " drugi wiersz");
            wygraneZPlusem[j][1] = rollFirst[i];
            // System.out.println(j);
        }

        if (yFirst[i] > 520 && yFirst[i] < 620) {
           // System.out.println(rollFirst[i] + " trzeci wiersz");
            wygraneZPlusem[j][2] = rollFirst[i];
            // System.out.println(j);
        }

    }



}
