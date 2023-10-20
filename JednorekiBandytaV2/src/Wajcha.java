import java.awt.*;

public class Wajcha {

    double xKulka = 680;
    double yKulka = 345;
    int ballsize = 80;


    public void RysujWajche(Graphics2D g2d) {

        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(15));
        g2d.fillOval((int) this.xKulka, (int) this.yKulka,ballsize,ballsize);
        g2d.drawLine(603,666, (int) (this.xKulka +(ballsize /2)), (int) (this.yKulka +(ballsize /2)));
    }

}
