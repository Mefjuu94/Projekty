import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Coins {

    int coinsSpeed = 2;
    ImageIcon coinIcon = new ImageIcon("src/ICONS/smallCoin-unscreen.gif");
    int x, y;

    public Coins(int x, int y){
        this.x = x;
        this.y = y;
    }



    public void paint(Graphics2D g2d, JPanel panel) {

        this.coinIcon.paintIcon(panel, g2d, this.x, this.y);

    }

    public void update(Coins c) {
        y = y + coinsSpeed;
    }
}
