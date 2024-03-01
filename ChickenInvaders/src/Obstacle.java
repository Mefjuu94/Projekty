import java.awt.*;
import java.util.Random;

public class Obstacle {

    int quantity = 3;
    int[] x = new int[quantity];
    int[] y = new int[quantity];


    boolean obstacleActive;
    int[] obstacleSpeed = new int[quantity];
    int[] obstacleWidth = new int[quantity];
    Color[] colors = new Color[quantity];


    Obstacle(){
        for (int i = 0; i < quantity; i++) {
        generateRandomPosition(i);
        }

    }

    public void paintObstacle(Graphics2D g2d){

        g2d.setStroke(new BasicStroke(5));

        for (int i = 0; i < quantity; i++) {
            g2d.setColor(colors[i]);
            g2d.drawLine(x[i],y[i],x[i]+obstacleWidth[i],y[i]);

        }


        moveObstacle();
    }

    public void setQuantity(int liczba){
        quantity = liczba;
        System.out.println("ilość przeszkód ( kładek = " + quantity);

        x = new int[quantity];
        y = new int[quantity];

        obstacleSpeed = new int[quantity];
        obstacleWidth = new int[quantity];
        colors = new Color[quantity];
        for (int i = 0; i < quantity; i++) {
            generateRandomPosition(i);
        }
    }

    private void generateRandomPosition(int i){
        Random random = new Random();

            x[i] = random.nextInt(500) + 75;
            System.out.println(x[i] + " x");
            y[i] = random.nextInt(550) + 70;
            System.out.println(y[i]  + " y");
            obstacleWidth[i] = random.nextInt(50) + 30;
            System.out.println("długość kładki " + i + " = " +  obstacleWidth[i] );
            obstacleSpeed[i] = random.nextInt(4) + 1;
            colors[i] = new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));

    }


    private void moveObstacle() {

        for (int i = 0; i < quantity; i++) {

            if (x[i] > 800 - obstacleWidth[i] || x[i] <= 2) {
                obstacleSpeed[i] = obstacleSpeed[i] * -1;
            }
            x[i] += obstacleSpeed[i];
        }
    }

}
