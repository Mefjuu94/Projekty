import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BallBouncing extends JPanel {

    // Wymiary kwadratu
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;

    // Wymiary piłki
    private static final int BALL_DIAMETER = 20;

    // Prędkość piłki
    private int ballSpeedX = 2;
    private int ballSpeedY = 3;

    // Pozycja piłki
    private int ballX = 400;
    private int ballY = 250;

    // Rysowanie piłki i kwadratu
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    // Aktualizacja pozycji piłki i odbijanie jej od zewnętrznych ścian kwadratu
    public void moveBall() {
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Sprawdzenie czy piłka uderza w zewnętrzne ściany kwadratu
        if(ballX < 0) {
            ballSpeedX = Math.abs(ballSpeedX);
        }
        if(ballX + BALL_DIAMETER > WIDTH) {
            ballSpeedX = -Math.abs(ballSpeedX);
        }
        if(ballY < 0) {
            ballSpeedY = Math.abs(ballSpeedY);
        }
        if(ballY + BALL_DIAMETER > HEIGHT) {
            ballSpeedY = -Math.abs(ballSpeedY);
        }
        repaint();
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Ball Bouncing");
        BallBouncing game = new BallBouncing();
        frame.add(game);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while(true) {
            game.moveBall();
            game.repaint();
            Thread.sleep(10);
        }
    }
}