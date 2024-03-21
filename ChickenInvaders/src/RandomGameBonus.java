import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RandomGameBonus {



    ImageIcon bonusIcon = new ImageIcon("src/ICONS/bonusIcon.png");

    int x;
    int y;
    boolean goDown = false;
    boolean bonusActive = false;

    boolean nieLosujWiecej = false;
    RandomGameBonus(){

        generateRandomPosition();
    }

    private void generateRandomPosition(){
        Random random = new Random();
           x = random.nextInt(500);
           y = -20;
    }


    public void drawBonus(Graphics2D g2d, Panel panel){

           this.bonusIcon.paintIcon(panel, g2d, x, y);
           if (goDown){
               y += 2;
           }
       }


    public void Generate() {

        Random rand = new Random();

        if (rand.nextInt(500) == 25 ){
            goDown = true;
            nieLosujWiecej = true;
        }


    }

    public void LosowanieBonusu(Graphics2D g2d, Panel panel) {
            Generate();
            if (!panel.bonusActivated) {
                drawBonus(g2d, panel);
            }
    }

    public int chooseBonus(){
        Random rand = new Random();
        int wyloSowana = rand.nextInt(2);
        System.out.println(wyloSowana);
        return wyloSowana;
    }
}
