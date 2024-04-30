import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ammo {


    int ammoQuantity = 50;
    int ammoLeft = ammoQuantity;
    ImageIcon ammoIcon = new ImageIcon("src/ICONS/ammunition.png");


    int[] x = new int[ammoQuantity];
    int[] y = new int[ammoQuantity];
    boolean[] goDown = new boolean[ammoQuantity];
    int i = 0;

    {
        for (int i = 0; i < ammoQuantity; i++) {
            goDown[i] = false;
        }
    }

    int szerokosc = 800;

    boolean ammoActive = true;


    Ammo(int szerokosc) {
        this.szerokosc = szerokosc;
        generateRandomPosition();
    }

    private void generateRandomPosition() {
        Random random = new Random();

        for (int i = 0; i < ammoQuantity; i++) {

            x[i] = random.nextInt(szerokosc) + 100;
            y[i] = 0;
        }
    }


    public void drawHelp(Graphics2D g2d, Panel panel) {


        for (int i = 0; i < ammoQuantity; i++) {
            this.ammoIcon.paintIcon(panel, g2d, x[i], y[i]);
            if (goDown[i]) {
                y[i] += 2;
            }
        }
    }

    public int Generate() {

        Random rand = new Random();

        if (rand.nextInt(150) == 25 && i < ammoQuantity) {
            System.out.println("AMUNICJA  nadchodzi!!");
            goDown[i] = true;
            i++;
            return ammoLeft = ammoQuantity - i;
        }

        return ammoLeft;

    }

    public void odpalPomocAmmo(Graphics2D g2d, Panel panel) {
        Generate();
        drawHelp(g2d, panel);
    }
}
