import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

class Planets extends JPanel{

    int width = 1000;
    int heigh = 800;
    int Slonce = 150;
    int xSlonce = (width/2) - (Slonce/2);
    int ySlonce = heigh/2- (Slonce/2);

    int Ziemia = 50;
    int xZiemia = xSlonce + (Slonce/2) + 200;
    int yZiemia  = ySlonce + (Slonce/2);


    int Moon = 20;
    int xMoon = xZiemia + (Ziemia/2) + 50;
    int yMoon = yZiemia + (Ziemia/2);


    int input = Integer.parseInt(JOptionPane.showInputDialog("Wprowadź w jakim czasie ziemia ma okrążyć słońce"));
    double szybkosc;


    double katZiemia = 0;
    double katMoon = 0;
    int counterMoon = 0;

    boolean jest = false;
    boolean slonceZiemia = false;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        getBackground();                // dla czarnego tłą
        setBackground(Color.BLACK);

        g2d.setColor(Color.yellow);
        g2d.fillOval(xSlonce,ySlonce,Slonce,Slonce);
        g2d.setColor(Color.WHITE);
        //LINIA ORBITY Ziemii
        g2d.drawOval(xSlonce - (Slonce/2),ySlonce- (Slonce/2),Slonce + 150,Slonce + 150);

        g2d.setColor(Color.blue);
        g2d.fillOval(xZiemia,yZiemia,Ziemia,Ziemia);

        g2d.setColor(new Color(192,192,192));
        g2d.fillOval(xMoon,yMoon,Moon,Moon);
        //LINIA ORBITY Ksiezyca
        g2d.drawOval(xZiemia - (Ziemia/2),yZiemia- (Ziemia/2),Ziemia + 50,Ziemia + 50);

    }

    public double obl(){
        return szybkosc = 0.1/input ;
    }

    public void Obrot(){

        obl();
        double szybkoscMoon = szybkosc * 360;

        // Ruch okrężny poprzez obl kąta
        // piersza współrzędna to środek okręgu w jakim się kręci (Środek Słońca) dzielona przez PI
        //druga jak wielkie koło ma zataczać
        xZiemia = (int) ((xSlonce + Slonce /Math.PI) + 150  * Math.cos(katZiemia));
        yZiemia = (int) ((ySlonce + Slonce /Math.PI) + 150 * Math.sin(katZiemia));

        xMoon = (int) ((xZiemia + Ziemia /Math.PI) + 50  * Math.cos(katMoon));
        yMoon = (int) ((yZiemia + Ziemia /Math.PI) + 50 * Math.sin(katMoon));


        katZiemia += szybkosc;// Szybkość krażenia Ziemii
        katMoon += szybkoscMoon; // Szybkość krązenia Księżyca
        LocalTime time = LocalTime.now();

        if (yZiemia == ySlonce && xZiemia > xSlonce && !slonceZiemia){
            System.out.println(time);
            System.out.println("Ziemia właśnie okrążyła słońce");
            System.out.println(counterMoon + " Obrotów księżyca wokół słońca!");
            slonceZiemia = true;
            counterMoon = 0;
        }
        if (yZiemia < ySlonce){
            slonceZiemia = false;
        }


        if (yMoon > yZiemia && !jest){
                counterMoon += 1;
                jest = true;
                System.out.println("księżyc okrążył ziemię : " + counterMoon + " razy");
        }
        if (yMoon < yZiemia) {
            jest = false;
        }




        repaint();

    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width,heigh);
    }
}

public class planety {
    public static void main(String[] args) throws InterruptedException {

        JFrame rama = new JFrame("planety");
        Planets planety = new Planets();

        rama.add(planety);
        rama.setVisible(true);
        rama.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        rama.pack();

        while (true){
            planety.Obrot();
            Thread.sleep(10);
        }


    }
}
