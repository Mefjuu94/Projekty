import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;

public class WahadloPanel extends JPanel implements MouseListener {
    private double dlugosc;
    private double kat;
    private double katPredkosc;
    private double amplituda;
    private double tłumienie;
    private Timer timer;
    int masaX,masaY;
    int masaRadius = 20;

    public WahadloPanel(double dlugosc, double katPredkosc, double amplituda, double tłumienie) {
        this.dlugosc = dlugosc;
        this.kat = kat;
        this.katPredkosc = katPredkosc;
        this.amplituda = amplituda;
        this.tłumienie = tłumienie;
        addMouseListener(this);

        int opoznienie = 10; // opóźnienie w milisekundach (częstość odświeżania)
        timer = new Timer(opoznienie, new ActionListener() {
            private double currentKat = kat;
            private double czas = 0.0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Aktualizacja pozycji wahadła
                double nowyKat = amplituda * Math.exp(-tłumienie * czas) * Math.sin(katPredkosc * czas);
                currentKat = nowyKat;
                setKat(currentKat);
                czas += 0.01; // krok czasowy (można dostosować)
                repaint(); // Odświeżenie panelu
            }
        });
    }

    private void setKat(double value) {
        kat = value;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int x0 = panelWidth / 2;
        int y0 = 0;

        masaRadius = 20;
        masaX = x0 + (int) (dlugosc * Math.sin(kat));
        masaY = y0 + (int) (dlugosc * Math.cos(kat));

        g.drawLine(x0, y0, masaX, masaY); // rysowanie liny wahadła
        g.fillOval(masaX - masaRadius, masaY - masaRadius, 2 * masaRadius, 2 * masaRadius); // rysowanie masy

        // rysowanie punktu zawieszenia
        g.fillOval(x0 - 5, y0 - 5, 10, 10);
    }

    public void startAnimacji() {
        timer.start(); // Rozpoczęcie animacji
    }

    public static void main(String[] args) {
        double dlugoscWahadla = 250.0; // długość wahadła
        //double katWychylenia = Math.toRadians(45); // kąt wychylenia wahadła (45 stopni w radianach)
        double katPredkosc = 2 * Math.PI / 2; // prędkość zmiany kąta (2 * PI / okres)
        double amplituda = Math.PI / 4; // amplituda wychylenia
        double tłumienie = 0.15; // współczynnik tłumienia (można dostosować)

        WahadloPanel panel = new WahadloPanel(dlugoscWahadla, katPredkosc, amplituda, tłumienie);

        JFrame frame = new JFrame("Wahadło Matematyczne");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(panel);
        frame.setVisible(true);

        panel.startAnimacji(); // Rozpoczęcie animacji
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        System.out.println("klick");
        dlugosc = e.getY();
        masaX = e.getX();
        masaY = e.getY();
        kat = Math.atan2(e.getX() - getWidth() / 2, e.getY() - getHeight() / 2);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

/*
    W ruchu wahadła matematycznego, odpowiedzialne za kierunek i prędkość ruchu są zmienne dlugosc, kat i katPredkosc.

        Zmienna dlugosc określa długość wahadła. Jest używana do obliczenia współrzędnych położenia
        masy wahadła w osi X i Y. Wzór dlugosc * Math.sin(kat) jest używany do obliczenia współrzędnej
        X, a wzór dlugosc * Math.cos(kat) jest używany do obliczenia współrzędnej Y. Zmieniając wartość
        dlugosc, możesz kontrolować rozpiętość ruchu wahadła.

        Zmienna kat przechowuje aktualny kąt wychylenia wahadła. Jest używana w równaniu ruchu wahadła do
            obliczenia pozycji masy wahadła w czasie. Równanie ruchu opisuje wychylenie wahadła w postaci
        sinusoidy, a kąt kat wpływa na amplitudę i fazę tej sinusoidy. Modyfikując wartość kat, możesz zmieniać
        początkowy kąt wychylenia i obserwować, jak wahadło porusza się wokół punktu zawieszenia.

        Zmienna katPredkosc określa prędkość zmiany kąta. Jest używana do obliczenia nowego kąta w każdej
        iteracji timera. Wzór amplituda * Math.exp(-tłumienie * czas) * Math.sin(katPredkosc * czas) opisuje
        równanie ruchu wahadła, gdzie czas to czas trwania animacji. Modyfikując wartość katPredkosc, możesz
        zmieniać prędkość zmiany kąta i tym samym wpływać na tempo ruchu wahadła.

        Zmieniając te zmienne, możesz eksperymentować z różnymi aspektami ruchu wahadła, takimi jak długość,
        początkowy kąt wychylenia, prędkość zmiany kąta itp. Dzięki temu możesz obserwować, jak te zmiany
        wpływają na ruch wahadła i jego animację.



        Wzór amplituda * Math.exp(-tłumienie * czas) * Math.sin(katPredkosc * czas)
        opisuje równanie ruchu wahadła matematycznego. Przyjrzyjmy się poszczególnym składnikom:

        Math.sin(katPredkosc * czas): Ten składnik odpowiada za
        sinusoidalne wychylenie wahadła wokół punktu równowagi.
        Zmienna katPredkosc określa prędkość zmiany kąta wahadła.
        Mnożenie przez czas powoduje stopniową zmianę kąta w czasie.

        Math.exp(-tłumienie * czas): Ten składnik odpowiada za tłumienie ruchu wahadła.
        Zmienna tłumienie reprezentuje współczynnik tłumienia, który wpływa na tempo tłumienia drgań.
        Wzrost wartości tłumienie powoduje szybsze wygaszanie ruchu wahadła.

        amplituda: Ten składnik określa amplitudę wychylenia wahadła. Zmienna amplituda
        reprezentuje maksymalne wychylenie wahadła od punktu równowagi. Im większa wartość amplituda,
        tym większe wychylenie wahadła.

        Całe równanie amplituda * Math.exp(-tłumienie * czas) * Math.sin(katPredkosc * czas)
        łączy te składniki w celu opisania ruchu wahadła matematycznego. Działa na zasadzie sinusoidalnego wychylenia,
        które jest stopniowo tłumione w czasie. Dzięki temu równaniu można kontrolować dynamikę ruchu wahadła,
        taką jak amplituda, prędkość zmiany kąta i tłumienie.

 */