import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {

    int WIDTH = 800;
    int HEIGHT = 800;

    Random random = new Random();

    Color[] fourColors = {Color.RED,Color.GREEN,Color.BLUE,Color.BLACK};
    List<Color> randomColors = new ArrayList<>();;
    {
        for (int i = 0; i <fourColors.length ; i++) {
            int los = random.nextInt(fourColors.length);
            randomColors.add(fourColors[los]);
        }
    }
    BallColor ballColor = new BallColor();
    List<BallColor> ballColors = new ArrayList<>();
    List<Color> grayC = new ArrayList<>();
    List<Color> rowColors = new ArrayList<>();


    int x,y;
    int labelsX = 0;
    int labelsY = 700;

    int clickCounter = 0;

    ImageIcon red = new ImageIcon("src/red.png");
    ImageIcon green = new ImageIcon("src/green.png");
    ImageIcon blue = new ImageIcon("src/blue.png");
    ImageIcon black = new ImageIcon("src/black.png");


    JButton label, label1,label2,label3;
    Color c;
    int colCounter = 1;

    int validCounter = 0;
    int row = 0;
    int col = 0;

    {
        labelsX = 220;

                label = new JButton();
                label.addActionListener(this);
                label.setIcon(red);
                label.setBounds(new Rectangle(labelsX, labelsY, 40, 40));
                label.setOpaque(true);
                this.add(label);
                label.setVisible(true);

                labelsX += 60;

                label1 = new JButton();
                label1.addActionListener(this);
                label1.setIcon(green);
                label1.setBounds(new Rectangle(labelsX, labelsY, 40, 40));
                label1.setOpaque(true);
                this.add(label1);
                label1.setVisible(true);

                labelsX += 60;

                label2 = new JButton();
                label2.addActionListener(this);
                label2.setIcon(blue);
                label2.setBounds(new Rectangle(labelsX, labelsY, 40, 40));
                label2.setOpaque(true);
                this.add(label2);
                label2.setVisible(true);

                labelsX += 60;

                label3 = new JButton();
                label3.addActionListener(this);
                label3.setIcon(black);
                label3.setBounds(new Rectangle(labelsX, labelsY, 40, 40));
                label3.setOpaque(true);
                this.add(label3);
                label3.setVisible(true);
            }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.lightGray);
        g2d.fillRect(0,0,800,800);

        losoweGenerowanie(g2d);
        //strzałka
        if(clickCounter == 0){
            this.ballColor.upArrow.paintIcon(this, g2d, 225, 590);
        }
        //do wyełnienia szare
        if (clickCounter==0) {
            ballColor.challenge(g2d,c,row,col);
        }else {
            ballColor.paint(g2d,this,clickCounter,c,randomColors,rowColors,row,col);
        }

        System.out.println(ballColors.size());

        validate(randomColors);
    }

    Panel(){
        this.setLayout(null);
        //this.add();
    }

    public void losoweGenerowanie(Graphics2D g2d){
        x = 220;
        y = 50;
        for (int i = 0; i < randomColors.size(); i++) {

            g2d.setColor(randomColors.get(i));
            g2d.fillOval(x, y, 40, 40);
            x += 60;
            g2d.setColor(Color.BLACK);

            g2d.drawString("Kolory wygenerowane przez kompa", 200,50);
        }
    }

    public void metoda(){

        if (clickCounter % 5 != 0){
            row = clickCounter;
        }
        if (clickCounter % 5 == 0){
            row = 0;
            col++;
        }

        repaint();
    }


    public void validate(List<Color> randomcolors){

        System.out.println(" Validate");

        if (rowColors.size() == 4){

            for (int i = 0; i < 4; i++) {

                Color ball = rowColors.get(i);
                Color random = randomcolors.get(i);

                if (ball != null) {
                    if (ball.equals(random)) {
                        System.out.println("ten sam kolor!!!");
                        validCounter++;
                    }
                }
            }

        }

        for (int i = 0; i < rowColors.size(); i++) {
         rowColors.remove(i);
        }
        System.out.println(rowColors.size() + " rozmiar ");
        if (validCounter == 4){
            System.out.println("Zgadłeś wszystkie kolory!!");
        }


    }



    public Dimension getPreferredSize(){return  new Dimension(WIDTH,HEIGHT);
    }


    @Override
    public void actionPerformed(ActionEvent e) {



        if (e.getSource() == label){
            System.out.println("red");
            clickCounter++;
            c = Color.red;
        }

        if (e.getSource() == label1){
            System.out.println("gren");
            clickCounter++;
            c = Color.green;
        }
        if (e.getSource() == label2){
            System.out.println("blue");
            clickCounter++;
            c = Color.blue;
        }
        if (e.getSource() == label3){
            System.out.println("black");
            clickCounter++;
            colCounter++;
        }

        if (clickCounter == 20){
            clickCounter =0;
        }

        rowColors.add(c);
        System.out.println(rowColors.size() +" rowcloros size"); ///nir dziala, nie zwieksza sie
        repaint();
    }


}
