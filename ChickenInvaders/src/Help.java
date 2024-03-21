import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Help {


    int firstAidQuantity = 25;
    ImageIcon firstAidIcon = new ImageIcon("src/ICONS/firstAid.png");
    int firstAidleft = firstAidQuantity;

    int[] x = new int[firstAidQuantity];
    int[] y = new int[firstAidQuantity];
    boolean[] goDown = new boolean[firstAidQuantity];
    int i = 0;

    {
        for (int i = 0; i < firstAidQuantity; i++) {
            goDown[i] = false;
        }
    }

    int widthOfFirstAid;

    boolean helpActive = true;



    Help(int widthOfFirstAid) {
        this.widthOfFirstAid = widthOfFirstAid;
        generateRandomPosition();
    }

    private void generateRandomPosition() {
        Random random = new Random();

        for (int i = 0; i < firstAidQuantity; i++) {

            x[i] = random.nextInt(widthOfFirstAid - 150);
            y[i] = 0;
        }
    }


    public void drawHelp(Graphics2D g2d, Panel panel) {


        for (int i = 0; i < firstAidQuantity; i++) {
            this.firstAidIcon.paintIcon(panel, g2d, x[i], y[i]);
            if (goDown[i]) {
                y[i] += 2;
            }
        }
    }

    public int Generate() {

        //zrobić!!
        Random rand = new Random();

        if (rand.nextInt(26) == 25 && i < firstAidQuantity) {
            System.out.println("pomoc nadchodzi!!");
            goDown[i] = true;
            i++;
            return firstAidleft = firstAidQuantity - i;
        }

        return firstAidleft;

    }

    public void odpalPomoc(Graphics2D g2d, Panel panel) {
        Generate();
        drawHelp(g2d, panel);
    }
}
