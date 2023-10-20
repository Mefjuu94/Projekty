import javax.swing.*;
import java.awt.*;

public class Plus  {

    int multi = 0;

    public void KtorePodUwage(Graphics2D g2d, boolean PLUSIK){

        if (PLUSIK){
            g2d.setStroke(new BasicStroke(8));
            g2d.setColor(Color.red);

            g2d.drawRect(133,300,100,100);
            g2d.drawRect(133,415,100,100);
            g2d.drawRect(133,530,100,100);

            g2d.drawRect(301,415,100,100);

            g2d.drawRect(462,300,100,100);
            g2d.drawRect(462,415,100,100);
            g2d.drawRect(462,530,100,100);

        }else {
            g2d.setStroke(new BasicStroke(8));
            g2d.setColor(Color.red);
            g2d.drawRect(133,415,100,100);
            g2d.drawRect(301,415,100,100);
            g2d.drawRect(462,415,100,100);

        }
    }

    public int wynikPLUS(boolean PLUSIK, ImageIcon[][] wygraneZPlusem,boolean[] jakaWygrana){

        this.multi = 0;

        if (PLUSIK) {
            //pion 1
            if (wygraneZPlusem[0][0].equals(wygraneZPlusem[0][1]) && wygraneZPlusem[0][1].equals(wygraneZPlusem[0][2])) {
                System.out.println("Wygrana w LEWYM PIonie!!!");
                this.multi++;

                jakaWygrana[0] = true;
            }

            //pion 2
            if (wygraneZPlusem[2][0].equals(wygraneZPlusem[2][1]) && wygraneZPlusem[2][1].equals(wygraneZPlusem[2][2])) {
                System.out.println("Wygrana w PRAWYM  PIonie!!!");
                this.multi++;

                jakaWygrana[1] = true;
            }

            //skos 1

            if (wygraneZPlusem[0][0].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][2])) {
                System.out.println("Wygrana w SKOSIE z lewej do prawej!!!");
                this.multi++;

                jakaWygrana[2] = true;
            }

            //skos 2

            if (wygraneZPlusem[0][2].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][0])) {
                System.out.println("Wygrana w SKOSIE z PRAWEJ DO LERWEJ!!!");
                this.multi++;

                jakaWygrana[3] = true;
            }

            if (wygraneZPlusem[0][1].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][1])) {
                System.out.println("Wygrana w POZIOMIE!!!");
                this.multi++;

                jakaWygrana[4] = true;
            }
        }else {
            if (wygraneZPlusem[0][1] != null) {
                if (wygraneZPlusem[0][1].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][1])) {
                    System.out.println("Wygrana w POZIOMIE!!!");
                    this.multi++;

                    jakaWygrana[4] = true;
                }
            }
        }
        //System.out.println(this.multi+ " multi");
        return this.multi;
    }

    public boolean[] JakaWygrana(boolean PLUSIK, ImageIcon[][] wygraneZPlusem,boolean[] jakaWygrana){
        if (PLUSIK) {
            //pion 1
            if (wygraneZPlusem[0][0].equals(wygraneZPlusem[0][1]) && wygraneZPlusem[0][1].equals(wygraneZPlusem[0][2])) {
                 jakaWygrana[0] = true;
            }

            //pion 2
            if (wygraneZPlusem[2][0].equals(wygraneZPlusem[2][1]) && wygraneZPlusem[2][1].equals(wygraneZPlusem[2][2])) {
                jakaWygrana[1] = true;
            }

            //skos 1

            if (wygraneZPlusem[0][0].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][2])) {
                jakaWygrana[2] = true;
            }

            //skos 2

            if (wygraneZPlusem[0][2].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][0])) {
                jakaWygrana[3] = true;
            }

            if (wygraneZPlusem[0][1].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][1])) {
                jakaWygrana[4] = true;
            }
        }else {
            if (wygraneZPlusem[0][1] != null) {
                if (wygraneZPlusem[0][1].equals(wygraneZPlusem[1][1]) && wygraneZPlusem[1][1].equals(wygraneZPlusem[2][1])) {
                    jakaWygrana[4] = true;
                }
            }
        }
            return jakaWygrana;

    }


}
