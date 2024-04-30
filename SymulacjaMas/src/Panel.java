import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.abs;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {

    JFrame ramka;
    Ball ball;
    List<Ball> ballsList = new ArrayList<>();
    List<Square> squareList = new ArrayList<>();
    int x = 10;

    JPanel controlPanel = new JPanel();
    JButton startButton = new JButton("Start");
    boolean startAnimation = false;

    JButton restart = new JButton("restart");

    JPanel leftBall = new JPanel();
    JPanel rightBall = new JPanel();

    int controlPanelWidth = 200;

    Color[] colors = new Color[10];

    JButton setVelocity1 = new JButton("set Velocity1 :");
    JTextField setVelField1 = new JTextField(10);

    JButton setVelocity2 = new JButton("set Velocity2 :");
    JTextField setVelField2 = new JTextField(10);

    JButton setMass1 = new JButton("set Mass 1:");
    JTextField setMassField1 = new JTextField(10);

    JButton setMass2 = new JButton("set Mass 2");
    JTextField setMassField2 = new JTextField(10);

    JButton setBackgroundColor = new JButton("set Background Color");
    int clickBackGround = 0;
    JButton setLeftBallColor = new JButton("change Ball Color");
    int clickLeftColor = 0;
    JButton setRightBallColor = new JButton("change Ball Color");
    int clickRightColor = 0;
    JButton randomRect = new JButton("add Random Rectangle");
    int rectX;
    int rectY;
    int rectWidth;
    boolean createRect;
    int indexOfCatchBall = 0;
    boolean ballCatchActive = false;

    boolean obliczenia = true;
    JPanel RadioPanel = new JPanel();
    JRadioButton radioButton = new JRadioButton("velocity toollTip");
    JRadioButton numberOfBall = new JRadioButton("number of Ball");
    JRadioButton raidusTooltip = new JRadioButton("raidus toolTip");
    JRadioButton clamp_method = new JRadioButton("Clamp method");
    boolean velocityTooltipVisible = true;

    JButton multipleBalls = new JButton("Multiple Balls");
    boolean multipleMode = false;
    int sizeOfMultiple = 3;
    JTextField multipleModeSize = new JTextField(String.valueOf(sizeOfMultiple), 10);


    //wiele piłeczek
    JPanel multipleControlBallPanel = new JPanel();
    JPanel multipleControlSquarePanel = new JPanel();
    ArrayList<JPanel> ballsPanels = new ArrayList<>();
    ArrayList<JPanel> squarePanels = new ArrayList<>();
    Color[] colorsOfBalls;
    JScrollPane controlPanelScroll = new JScrollPane(multipleControlBallPanel);
    JScrollPane controlPanelScrollsquare = new JScrollPane(multipleControlSquarePanel);

    JButton[] setVelocityXBalls;
    JButton[] setVelocityYBalls;
    JTextField[] setVelFieldXBalls;
    JTextField[] setVelFieldYBalls;

    JButton[] setMassBalls;
    JTextField[] setMassFieldBalls;

    JButton[] setColorOfBallsButton;
    int[] clickOfcolors;

    Square square;

    JButton xPosition;
    JTextField XPositionField;
    JButton yPosition;
    JTextField yPositionField;
    JButton squareWidthButton;
    JTextField squareWidth;
    JButton setColorOfsquareButton;


    ArrayList<JButton> setXpositionRect = new ArrayList<>();
    ArrayList<JButton> setYpositionRect = new ArrayList<>();
    ArrayList<JTextField> XPositionFieldSquares = new ArrayList<>();
    ArrayList<JTextField> YPositionFieldSquares = new ArrayList<>();
    ArrayList<JButton> setWidthsquares = new ArrayList<>();
    ArrayList<JTextField> setWidthSquaresFields = new ArrayList<>();
    ArrayList<JButton> setColorOfSquaresButton = new ArrayList<>();

    boolean left, right, top, bottom;
    double closeX;
    double closeY;

    public double getCloseX() {
        return closeX;
    }

    public void setCloseX(double closeX) {
        this.closeX = closeX;
    }

    public double getCloseY() {
        return closeY;
    }

    public void setCloseY(double closeY) {
        this.closeY = closeY;
    }

    boolean closestX;
    boolean closestY;
    JButton przycisk = new JButton("wyswietl coś");

    JTabbedPane tabbedPane = new JTabbedPane();


    Panel(JFrame ramka) {

        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);
        this.ramka = ramka;
        this.addMouseMotionListener(this);

        this.add(controlPanel, BorderLayout.EAST);
        Border controlPanelBorder = new TitledBorder("Control Panel");
        controlPanelScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        controlPanelScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        controlPanel.setPreferredSize(new Dimension(controlPanelWidth, 20));
        controlPanel.setBorder(controlPanelBorder);

        //restart i stop buttons:
        controlPanel.add(startButton);
        startButton.addMouseListener(this);
        controlPanel.add(restart);
        restart.addMouseListener(this);

        controlPanel.add(przycisk);
        przycisk.addMouseListener(this);

        RadioPanel.add(radioButton);
        radioButton.setSelected(true);
        radioButton.addMouseListener(this);

        RadioPanel.add(numberOfBall);
        numberOfBall.setSelected(false);
        numberOfBall.addMouseListener(this);

        RadioPanel.add(raidusTooltip);
        raidusTooltip.setSelected(false);
        raidusTooltip.addMouseListener(this);

        RadioPanel.add(clamp_method);
        clamp_method.setSelected(false);
        clamp_method.addMouseListener(this);

        controlPanel.add(RadioPanel);
        Border RadioBorder = new TitledBorder("Radio Panel");
        RadioPanel.setBorder(RadioBorder);
        RadioPanel.setLayout(new BoxLayout(RadioPanel, BoxLayout.Y_AXIS));

        controlPanel.add(randomRect);
        randomRect.addMouseListener(this);

        controlPanel.add(setBackgroundColor);
        setBackgroundColor.addMouseListener(this);

        controlPanel.add(multipleBalls);
        multipleBalls.setSelected(true);
        multipleBalls.addMouseListener(this);
        controlPanel.add(multipleModeSize);
        multipleModeSize.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep(); // dzwięk!
                    System.out.println("niedozwolony znak!");
                    e.consume();
                }
            }
        });

        //stworznie 2 piłek
        make2BallsMode();
        ///////////////////////////////////////////////////

    }

    private void make2BallsMode() {

        int m1 = 100;
        int m2 = 200;
        double v = 7;

        ballsList.add(new Ball(10, ramka.getHeight() / 2 - (m1 / 2), m1, v, 30, Color.ORANGE, this));
        ballsList.get(0).setRadius(m1 / 2);
        ballsList.add(new Ball(ramka.getWidth() - m2 - controlPanelWidth - 50, ramka.getHeight() / 2 - (m2 / 2), m2, v * -1, 210, Color.pink, this));
        ballsList.get(1).setRadius(m2 / 2);

        controlPanel.add(leftBall);
        leftBall.setBackground(ballsList.get(0).color);
        Border leftballBorder = new TitledBorder("left ball");
        leftBall.setBorder(leftballBorder);
        leftBall.setLayout(new BoxLayout(leftBall, BoxLayout.Y_AXIS));
        controlPanel.add(rightBall);
        Border rightballBorder = new TitledBorder("right ball");
        rightBall.setBorder(rightballBorder);
        rightBall.setLayout(new BoxLayout(rightBall, BoxLayout.Y_AXIS));

        leftBall.add(setVelocity1);
        setVelocity1.addMouseListener(this);
        add(setVelField1);
        //setVelField.setDocument(); // TODO nowy dokument który zwraca w razie kopiowania null
        setVelField1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep(); // dzwięk!
                    System.out.println("niedozwolony znak!");
                    e.consume();
                }
            }
        });
        leftBall.add(setVelField1);

        leftBall.add(setMass1);
        setMass1.addMouseListener(this);
        leftBall.add(setMassField1);
        setMassField1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep(); // dzwięk!
                    System.out.println("niedozwolony znak!");
                    e.consume();
                }
            }
        });
        leftBall.add(setLeftBallColor);
        setLeftBallColor.addMouseListener(this);

//////////////////RIGHT BAll/////////////////////////

        rightBall.setBackground(ballsList.get(1).color);
        rightBall.add(setVelocity2);
        setVelocity2.addMouseListener(this);
        add(setVelField2);
        //setVelField.setDocument(); // TODO nowy dokument który zwraca w razie kopiowania null
        setVelField2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep(); // dzwięk!
                    System.out.println("niedozwolony znak!");
                    e.consume();
                }
            }
        });
        rightBall.add(setVelField2);

        rightBall.add(setMass2);
        setMass2.addMouseListener(this);
        setMassField2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep(); // dzwięk!
                    System.out.println("niedozwolony znak!");
                    e.consume();
                }
            }
        });
        rightBall.add(setMassField2);

        rightBall.add(setRightBallColor);
        setRightBallColor.addMouseListener(this);

        controlPanelScroll.revalidate();

    }

    private void makeBallList(int numberOfBalls) {

        setVelocityXBalls = new JButton[numberOfBalls];
        setVelocityYBalls = new JButton[numberOfBalls];
        setMassFieldBalls = new JTextField[numberOfBalls];
        colorsOfBalls = new Color[numberOfBalls];
        setVelFieldXBalls = new JTextField[numberOfBalls];
        setVelFieldYBalls = new JTextField[numberOfBalls];

        setMassBalls = new JButton[numberOfBalls];
        setColorOfBallsButton = new JButton[numberOfBalls];
        clickOfcolors = new int[numberOfBalls];

        controlPanelScroll.setPreferredSize(new Dimension(controlPanel.getWidth() - 10, (ramka.getHeight() - 380)));
        multipleControlBallPanel.setLayout(new BoxLayout(multipleControlBallPanel, BoxLayout.Y_AXIS));

        controlPanelScrollsquare.setPreferredSize(new Dimension(controlPanel.getWidth() - 10, (ramka.getHeight() - 380)));
        multipleControlSquarePanel.setLayout(new BoxLayout(multipleControlSquarePanel, BoxLayout.Y_AXIS));


        for (int i = 0; i < numberOfBalls; i++) {

            Random rand = new Random();
            //new ball random position:
            int mass = rand.nextInt(250);
            int x = rand.nextInt(ramka.getWidth() - 250 - mass);
            int y = rand.nextInt(ramka.getHeight() - mass - 100);
            int velocityX = rand.nextInt(5) + 1;
            int velocityY = rand.nextInt(5) + 1;

            boolean[] reflect = new boolean[numberOfBalls];

            while (!repairPosition(mass, x, y)) {
                mass = rand.nextInt(100) + 10;
                x = rand.nextInt(ramka.getWidth() - 230 - mass);
                y = rand.nextInt(ramka.getHeight() - mass - 100);
            }

            ballsList.add(new Ball(x, y, mass, velocityX, velocityY, 30, new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)), this, reflect, i));
            ballsList.get(i).setRadius(mass / 2);
            ballsList.get(i).originalY = y;

            Border panelsBorder = new TitledBorder("Ball: " + i);
            ballsPanels.add(new JPanel());
            ballsPanels.get(i).setBorder(panelsBorder);
            ballsPanels.get(i).setLayout(new BoxLayout(ballsPanels.get(i), BoxLayout.Y_AXIS));

            setVelocityXBalls[i] = new JButton("set Velocity X: " + i);
            setVelFieldXBalls[i] = new JTextField(5);
            setVelocityYBalls[i] = new JButton("set Velocity Y: " + i);
            setVelFieldYBalls[i] = new JTextField(5);
            setMassBalls[i] = new JButton("set mass : " + i);
            setMassFieldBalls[i] = new JTextField(5);

            setColorOfBallsButton[i] = new JButton("change Color of Ball : " + i);

            ballsPanels.get(i).setBackground(ballsList.get(i).color);
            ballsPanels.get(i).add(setVelocityXBalls[i]);
            setVelocityXBalls[i].addMouseListener(this);

            ballsPanels.get(i).add(setVelFieldXBalls[i]);
            //setVelField.setDocument(); // TODO nowy dokument który zwraca w razie kopiowania null
            setVelFieldXBalls[i].addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!((c >= '0') && (c <= '9') ||
                            (c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE))) {
                        getToolkit().beep(); // dzwięk!
                        System.out.println("niedozwolony znak!");
                        e.consume();
                    }
                }
            });

            ballsPanels.get(i).add(setVelocityYBalls[i]);
            setVelocityYBalls[i].addMouseListener(this);

            ballsPanels.get(i).add(setVelFieldYBalls[i]);
            //setVelField.setDocument(); // TODO nowy dokument który zwraca w razie kopiowania null
            setVelFieldYBalls[i].addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!((c >= '0') && (c <= '9') ||
                            (c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE))) {
                        getToolkit().beep(); // dzwięk!
                        System.out.println("niedozwolony znak!");
                        e.consume();
                    }
                }
            });
            ballsPanels.get(i).add(setMassFieldBalls[i]);

            ballsPanels.get(i).add(setMassBalls[i]);
            setMassBalls[i].addMouseListener(this);
            setMassFieldBalls[i].addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!((c >= '0') && (c <= '9') ||
                            (c == KeyEvent.VK_BACK_SPACE) ||
                            (c == KeyEvent.VK_DELETE))) {
                        getToolkit().beep(); // dzwięk!
                        System.out.println("niedozwolony znak!");
                        e.consume();
                    }
                }
            });
            ballsPanels.get(i).add(setMassFieldBalls[i]);

            ballsPanels.get(i).add(setColorOfBallsButton[i]);
            setColorOfBallsButton[i].addMouseListener(this);

            multipleControlBallPanel.add(ballsPanels.get(i));
        }


        //rect square!!!!


        tabbedPane.addTab("balls", controlPanelScroll);
        tabbedPane.addTab("squares", controlPanelScrollsquare);
        controlPanel.add(tabbedPane);

        controlPanel.revalidate();

        controlPanelScroll.setVisible(true);
        controlPanelScrollsquare.setVisible(true);
    }

    private void addSquarePanel(int i) {


        Random rand = new Random();
        //new ball random position:

        Border panelsBorder = new TitledBorder("square: " + i);
        squarePanels.add(new JPanel());
        squarePanels.get(i).setBorder(panelsBorder);
        squarePanels.get(i).setLayout(new BoxLayout(squarePanels.get(i), BoxLayout.Y_AXIS));

        xPosition = new JButton("set X: " + i);
        XPositionField = new JTextField(5);
        yPosition = new JButton("set Y: " + i);
        yPositionField = new JTextField(5);
        squareWidthButton = new JButton("set Width : " + i);
        squareWidth = new JTextField(5);
        setColorOfsquareButton = new JButton("change Color of Rect : " + i);

        setXpositionRect.add(xPosition);
        XPositionFieldSquares.add(XPositionField);
        setYpositionRect.add(yPosition);
        YPositionFieldSquares.add(yPositionField);
        setWidthsquares.add(squareWidthButton);
        setWidthSquaresFields.add(squareWidth);
        setColorOfSquaresButton.add(setColorOfsquareButton);

        squarePanels.get(i).add(xPosition);
        xPosition.addMouseListener(this);
        squarePanels.get(i).add(XPositionField);
        //setVelField.setDocument(); // TODO nowy dokument który zwraca w razie kopiowania null
        XPositionField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep(); // dzwięk!
                    System.out.println("niedozwolony znak!");
                    e.consume();
                }
            }
        });

        squarePanels.get(i).add(yPosition);
        yPosition.addMouseListener(this);

        squarePanels.get(i).add(yPositionField);
        //setVelField.setDocument(); // TODO nowy dokument który zwraca w razie kopiowania null
        yPositionField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep(); // dzwięk!
                    System.out.println("niedozwolony znak!");
                    e.consume();
                }
            }
        });

        squarePanels.get(i).add(squareWidthButton);
        squarePanels.get(i).add(squareWidth);

        squareWidth.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep(); // dzwięk!
                    System.out.println("niedozwolony znak!");
                    e.consume();
                }
            }
        });

        setColorOfsquareButton.addMouseListener(this);
        squareWidthButton.addMouseListener(this);

        squarePanels.get(i).add(setColorOfsquareButton);

        squarePanels.get(i).setBackground(squareList.get(i).getColor());
        multipleControlSquarePanel.add(squarePanels.get(i));


        tabbedPane.revalidate();
        tabbedPane.repaint();
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        if (ballsList != null) {
            for (int k = 0; k < ballsList.size(); k++) {
                ballsList.get(k).paintBall(g2d);


                if (startAnimation) {
                    // detekcja kolizjii
                    if (obliczenia) {
                        CollisionDetect();
                    }
                    reflectFromWalls();
                }

                if (createRect) {

                    for (int j = 0; j < squarePanels.size(); j++) {

                        g2d.setColor(squareList.get(j).getColor());
                        g2d.fillRect(squareList.get(j).getX(), squareList.get(j).getY(), squareList.get(j).getWidth(), squareList.get(j).getWidth());


                        if (clamp_method.isSelected()) {
                            for (int i = 0; i < ballsList.size(); i++) {
                                g2d.setColor(Color.black);
                                g2d.drawLine(ballsList.get(i).x + ballsList.get(i).radius, ballsList.get(i).y + ballsList.get(i).radius, (int) ballsList.get(i).closeX, (int) ballsList.get(i).closeY);
                                g2d.setColor(ballsList.get(i).color);
                                g2d.fillOval((int) ballsList.get(i).closeX - 5, (int) ballsList.get(i).closeY - 5, 10, 10);

                            }
                        }

                        colisionWithRect(j);
                    }
                }
            }
        }


    }

    public void method() {

        repaint();

    }


    private void colisionWithRect(int squareIndex) {

        for (Ball ball : ballsList) {


//            double dXOfBall = ball.x + ball.radius;
//            double dYOfBall = ball.y + ball.radius;
//
//            double dx = (dXOfBall) - (ball.closeX);
//            double dy = (dYOfBall) - (ball.closeY);
//            double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));


//
//
//            ball.DISTANSE = (int) distance;
//





            clamp(ball, squareIndex);


        }
    }


    public void clamp(Ball ball, int i) {
        double dXOfBall = ball.x + ball.radius;
        double dYOfBall = ball.y + ball.radius;

        double DXofCloseX = (ball.x + ball.radius) - ball.closeX;
        double DYofCloseY = (ball.y + ball.radius) - ball.closeY;
        double distance = Math.sqrt(Math.pow(DXofCloseX, 2) + Math.pow(DYofCloseY, 2));

        ball.closeX = dXOfBall;
        ball.closeY = dYOfBall;


        boolean rectangleX = ball.x + ball.mass > squareList.get(i).getX() && ball.x < squareList.get(i).getX() + squareList.get(i).getWidth();
        boolean rectangleY = ball.y + ball.mass > squareList.get(i).getY() && ball.y < squareList.get(i).getY() + squareList.get(i).getWidth();
        boolean rectangleBOX = rectangleX && rectangleY;

        //clamping ani
        if (dXOfBall < squareList.get(i).getX()) {
            ball.closeX = squareList.get(i).getX();
            closestX = true;
            closestY = false;
        }

        if (dXOfBall > squareList.get(i).getX() + squareList.get(i).getWidth()) {
            ball.closeX = squareList.get(i).getX() + squareList.get(i).getWidth();
            closestX = true;
            closestY = false;
        }

        if (dYOfBall < squareList.get(i).getY()) {
            ball.closeY = squareList.get(i).getY();
            closestX = false;
            closestY = true;
        }

        if (dYOfBall > squareList.get(i).getY() + squareList.get(i).getWidth()) {
            ball.closeY = squareList.get(i).getY() + squareList.get(i).getWidth();
            closestX = false;
            closestY = true;
        }

        int middleRectX = squareList.get(i).getX() + (rectWidth / 2);
        int middleRectY = squareList.get(i).getY() + (rectWidth / 2);

        left = ball.x + ball.mass < middleRectX;
        right = ball.x > middleRectX ;
        bottom = ball.y > middleRectY ;
        top = ball.y + ball.mass < middleRectY ;

        if(distance <= ball.radius) {
            System.out.println("pilka w kwadracie");
            squareList.get(i).setColor(Color.red);
            double xPit = Math.sqrt(Math.pow(dXOfBall,2) + Math.pow( middleRectX,2));
            double yPit = Math.sqrt(Math.pow(dYOfBall,2) + Math.pow( middleRectY,2));

            if (left || right){
                System.out.println("left or right");
                ball.vX *= -1;
                ball.hitObstacle = false;
            }else {
                System.out.println("top or bottom");
                ball.vX *= -1;
                ball.hitObstacle = false;
            }

        }else {
            squareList.get(i).setColor(Color.blue);
        }
    }

    private void CollisionDetect() {

        if (!multipleMode) {
            double dx = (ballsList.get(0).x + ballsList.get(0).radius) - (ballsList.get(1).x + ballsList.get(1).radius);
            double dy = (ballsList.get(0).y + ballsList.get(0).radius) - (ballsList.get(1).y + ballsList.get(1).radius);
            double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            ballsList.get(0).setDistanceToNearest(distance);
            ballsList.get(1).setDistanceToNearest(distance);

            if (ballsList.get(0).x + ballsList.get(0).mass >= ballsList.get(1).x && ballsList.get(0).x < ballsList.get(1).x + ballsList.get(1).mass &&
                    ballsList.get(0).y + ballsList.get(0).mass >= ballsList.get(1).y && ballsList.get(0).y < ballsList.get(1).y + ballsList.get(1).mass) {
                System.out.println("detekcja kolizjii!");

                double M = ballsList.get(0).mass + ballsList.get(1).mass;
                double V = (ballsList.get(0).mass * ballsList.get(0).vX + ballsList.get(1).mass * ballsList.get(1).vX) / M;

                // Obliczenie prędkości relatywnej po zderzeniu
                ballsList.get(0).vX = 2 * V - ballsList.get(0).vX;
                ballsList.get(1).vX = 2 * V - ballsList.get(1).vX;

                System.out.println("szybkość lewej piłki: " + ballsList.get(0).vX);
                System.out.println("szybkość prawej piłki: " + ballsList.get(1).vX);
                obliczenia = false;
            }
        } else {


            for (int i = 0; i < ballsList.size(); i++) {

                for (int j = 0; j < ballsList.size(); j++) {

                    double distance;
                    double maxDistance;
                    double dx;
                    double dy;

                    if (i != j) {

                        dx = (ballsList.get(i).x + ballsList.get(i).radius) - (ballsList.get(j).x + ballsList.get(j).radius);
                        dy = (ballsList.get(i).y + ballsList.get(i).radius) - (ballsList.get(j).y + ballsList.get(j).radius);
                        distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                        maxDistance = ballsList.get(i).radius + ballsList.get(j).radius;

                        ballsList.get(i).setDistanceToNearest(distance);

//                        System.out.println(maxDistance + " max dystans bo: " + ballsList.get(i).radius + " " + ballsList.get(j).radius);
//                        System.out.println("dystans teraz: " + distance);


                        if (distance <= maxDistance && !ballsList.get(j).reflect[i]) {
                            // System.out.println("kolizja!" + distance + " " + i + " " + j);

                            boolean[] f = new boolean[ballsList.size()];
                            ballsList.get(i).hitObstacle = true;
                            ballsList.get(i).setIndexLastHit(9999);

                            ballsList.get(i).setFalseToReflect();
                            ballsList.get(j).setFalseToReflect();

                            ballsList.get(i).reflect[j] = true;
                            ballsList.get(j).reflect[i] = true;

                            // Obliczamy jednostkowy wektor normalny
                            double nx = dx / distance;
                            double ny = dy / distance;

                            // Obliczamy wektor prędkości względnej
                            double dvx = ballsList.get(i).vX - ballsList.get(j).vX;
                            double dvy = ballsList.get(i).vY - ballsList.get(j).vY;
                            double dotProduct = dvx * nx + dvy * ny;

                            // Obliczamy siłę odbicia (zmianę prędkości)
                            double force = 2 * dotProduct / (ballsList.get(j).mass + ballsList.get(i).mass);

                            // Obliczamy zmianę prędkości dla obu piłek
                            double deltaVX1 = force * ballsList.get(i).mass * nx;
                            double deltaVY1 = force * ballsList.get(i).mass * ny;
                            double deltaVX2 = -force * ballsList.get(j).mass * nx;
                            double deltaVY2 = -force * ballsList.get(j).mass * ny;

                            // Aktualizujemy prędkości obu piłek
                            ballsList.get(j).vX += deltaVX1;
                            ballsList.get(j).vY += deltaVY1;
                            ballsList.get(i).vX += deltaVX2;
                            ballsList.get(i).vY += deltaVY2;

                        }
                    }

                }
            }

        }
    }

    private void reflectFromWalls() {
        int WIDTH = this.getWidth();
        int HEIGHT = this.getHeight();
        boolean[] f = new boolean[ballsList.size()];

        if (!multipleMode) {
            for (int i = 0; i < ballsList.size(); i++) {

                if (ballsList.get(i).x <= 0 || ballsList.get(i).x + ballsList.get(i).mass >= (WIDTH - 220)) {
                    ballsList.get(i).vX = ballsList.get(i).vX * -1;
                    obliczenia = true;
                    ballsList.get(i).setIndexLastHit(9999);
                }

            }
        } else {
            for (int i = 0; i < ballsList.size(); i++) {
                if (ballsList.get(i).x <= 0 || ballsList.get(i).x + ballsList.get(i).mass >= (WIDTH - 220)) {
                    ballsList.get(i).vX = ballsList.get(i).vX * -1;
                    ballsList.get(i).setFalseToReflect();
                    ballsList.get(i).hitObstacle = true;
                    ballsList.get(i).setIndexLastHit(9999);

                }

                if (ballsList.get(i).y <= 0 || ballsList.get(i).y + ballsList.get(i).mass >= HEIGHT) {
                    ballsList.get(i).vY = ballsList.get(i).vY * -1;
                    ballsList.get(i).setFalseToReflect();
                    ballsList.get(i).setFalseToReflect();
                    ballsList.get(i).hitObstacle = true;
                    ballsList.get(i).setIndexLastHit(9999);

                }
            }

        }


    }

    public static boolean containsOnlyDigits(String str) {
        return str.matches("\\d+");
    }

    private boolean repairPosition(int mass, int x, int y) {

        for (int j = 0; j < ballsList.size(); j++) {

            if (x + mass >= ballsList.get(j).x && x < ballsList.get(j).x + ballsList.get(j).mass &&
                    y + mass >= ballsList.get(j).y && y < ballsList.get(j).y + ballsList.get(j).mass) {

                System.out.println("pozycja powtórzona!");
                return false;
            }
        }

        return true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == startButton) {
            System.out.println("klik start");
            startAnimation = !startAnimation;
            if (startAnimation) {
                startButton.setText("stop");
            } else {
                startButton.setText("start");
            }
        }

        if (e.getSource() == restart) {

            for (int i = 0; i < ballsList.size(); i++) {

            ballsList.get(i).x = ballsList.get(i).originalX;
            ballsList.get(i).y = ballsList.get(i).originalY;
            ballsList.get(i).vX = ballsList.get(i).getMyV();

            startAnimation = false;
            obliczenia = true;
            }
            System.out.println("restart");
            squareList.clear();
            squarePanels.clear();
            startButton.setText("start");
        }

        if (e.getSource() == setVelocity1) {
            String getText = setVelField1.getText();
            if (containsOnlyDigits(getText)) {
                int newVelocity = Integer.parseInt(getText);
                if (newVelocity > 0) {
                    setVelField1.setToolTipText("");
                    setVelocity1.setToolTipText("");
                    ballsList.get(0).setMyV(newVelocity);
                    ballsList.get(0).vX = newVelocity;
                    System.out.println("Changed Velocity = " + newVelocity);
                } else {
                    getToolkit().beep(); // dzwięk!
                    System.out.println("niedozwolona Wartość!");
                    setVelField1.setToolTipText("must be positive number!");
                    setVelocity1.setToolTipText("must be positive number!");
                }
            } else {
                setVelField1.setToolTipText("DIGITS ONLY!");
                setVelocity1.setToolTipText("Digits ONLY!");
            }
        }

        if (e.getSource() == setVelocity2) {
            String getText = setVelField2.getText();
            if (containsOnlyDigits(getText)) {
                int newVelocity = Integer.parseInt(getText);
                if (newVelocity > 0) {
                    setVelocity2.setToolTipText("");
                    setVelField2.setToolTipText("");
                    ballsList.get(1).vX = newVelocity * -1;
                    ballsList.get(1).setMyV(newVelocity * -1);
                    System.out.println("Changed Velocity = " + newVelocity);
                } else {
                    getToolkit().beep(); // dzwięk!
                    System.out.println("niedozwolona Wartość!");
                    setVelField2.setToolTipText("must be positive number!");
                    setVelField2.setToolTipText("must be positive number!");
                }
            } else {
                setVelField2.setToolTipText("DIGITS ONLY!");
                setVelField2.setToolTipText("Digits ONLY!");
            }
        }

        if (e.getSource() == setMass1) {
            int mass = Integer.parseInt(setMassField1.getText());
            ballsList.get(0).setMass(mass);
            ballsList.get(0).setX(10);
            ballsList.get(0).setY(ramka.getHeight() / 2 - (ballsList.get(0).getMass() / 2));
            startAnimation = false;
            obliczenia = true;
            System.out.println("Changed mass of left Ball");
        }

        if (e.getSource() == setMass2) {
            int mass = Integer.parseInt(setMassField2.getText());
            ballsList.get(1).setMass(mass);
            ballsList.get(1).setX(ramka.getWidth() - ballsList.get(1).getMass() - controlPanelWidth - 50);
            ballsList.get(1).setY(ramka.getHeight() / 2 - (ballsList.get(1).getMass() / 2));
            startAnimation = false;
            obliczenia = true;
            System.out.println("Changed mass of right Ball");
        }

        if (e.getSource() == setBackgroundColor) {
            System.out.println("Changed color of Background");
            clickBackGround++;
            if (clickBackGround > 9) {
                clickBackGround = 0;
            }
            if (clickBackGround > 0) {
                Random random = new Random();
                this.setBackground(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            }
        }
        if (e.getSource() == setLeftBallColor) {
            System.out.println("Changed color of left Ball");
            clickLeftColor++;
            if (clickLeftColor > 9) {
                clickLeftColor = 0;
            }
            if (clickLeftColor > 0) {
                ballsList.get(0).color = ballsList.get(0).colors[clickLeftColor];
                leftBall.setBackground(ballsList.get(1).colors[clickLeftColor]);
            }
        }

        if (e.getSource() == setRightBallColor) {
            clickRightColor++;
            if (clickRightColor > 9) {
                clickRightColor = 0;
            }
            if (clickRightColor > 0) {
                ballsList.get(1).color = ballsList.get(1).colors[clickRightColor];
                rightBall.setBackground(ballsList.get(1).colors[clickRightColor]);
            }
            System.out.println("Changed color of right Ball");
        }

        if (e.getSource() == radioButton) {
            if (radioButton.isSelected()) {
                velocityTooltipVisible = true;
                System.out.println("velocity visible = " + velocityTooltipVisible);
            } else {
                velocityTooltipVisible = false;
                System.out.println("velocity visible = " + velocityTooltipVisible);
            }
        }

        if (e.getSource() == multipleBalls) {
            System.out.println("multii");
            multipleMode = !multipleMode;
            String text = multipleModeSize.getText();
            sizeOfMultiple = Integer.parseInt(text);

//            if (sizeOfMultiple < 2) {
//                getToolkit().beep(); // dzwięk!
//                System.out.println("niedozwolona Wartośc!");
//                JOptionPane.showMessageDialog(controlPanel, "Invalid Value! Must be greater than 2!");
//                multipleMode = false;
//            }


            if (multipleMode) {
                controlPanel.remove(leftBall);
                controlPanel.remove(rightBall);

                ballsList.clear();

                makeBallList(sizeOfMultiple);

                multipleBalls.setText("2 Object Mode");
                multipleModeSize.setVisible(false);
            } else {
                multipleBalls.setText("Multi Object Mode");


                for (int i = 0; i < ballsPanels.size(); i++) {
                    ballsPanels.get(i).removeAll();
                    controlPanelScroll.remove(ballsPanels.get(i));
                    tabbedPane.removeAll();
                    tabbedPane.remove(controlPanelScroll);
                    controlPanelScroll.revalidate();
                    controlPanelScroll.repaint();
                }
                ballsList.clear();
                System.out.println("bal panels lenght: " + ballsPanels.size());
                System.out.println("ballList: " + ballsList.size());

                make2BallsMode();
                controlPanel.revalidate();
                controlPanelScroll.setVisible(false);
                multipleModeSize.setVisible(true);

            }
        }


        if (multipleMode) {
            for (int i = 0; i < ballsList.size(); i++) {

                if (e.getSource() == setVelocityXBalls[i]) {
                    if (setVelFieldXBalls[i].getText() == null || Objects.equals(setVelFieldXBalls[i].getText(), "")) {
                        JOptionPane.showMessageDialog(controlPanel, "valuie must be greater than 0!");
                    } else {
                        int V = Integer.parseInt(setVelFieldXBalls[i].getText());
                        if (V >= 0) {
                            ballsList.get(i).setxV(V);
                            System.out.println("Changed velocity of ball : " + i + " = " + V);
                        }
                    }
                }

                if (e.getSource() == setVelocityYBalls[i]) {
                    if (setVelFieldYBalls[i].getText() == null || Objects.equals(setVelFieldYBalls[i].getText(), "")) {
                        JOptionPane.showMessageDialog(controlPanel, "valuie must be greater than 0!");
                    } else {
                        int V = Integer.parseInt(setVelFieldYBalls[i].getText());
                        if (V >= 0) {
                            ballsList.get(i).setvY(V);
                            System.out.println("Changed velocity of ball : " + i + " = " + V);
                        }
                    }
                }


                if (e.getSource() == setMassBalls[i]) {
                    if (setMassFieldBalls[i].getText() == null || Objects.equals(setMassFieldBalls[i].getText(), "")) {
                        JOptionPane.showMessageDialog(controlPanel, "valuie must be greater than 0!");
                    } else {
                        int M = Integer.parseInt(setMassFieldBalls[i].getText());
                        if (M > 0) {
                            ballsList.get(i).setMass(M);
                            ballsList.get(i).setRadius(M / 2);
                            System.out.println("Changed Mass of ball : " + i + " = " + M);
                        }
                    }
                }

                if (e.getSource() == setColorOfBallsButton[i]) {
                    Random random = new Random();
                    Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                    ballsList.get(i).color = color;
                    ballsPanels.get(i).setBackground(color);
                    System.out.println("Changed Color of ball : " + i);

                }


            }
        }

        if (e.getSource() == randomRect) {
            createRandomRect();
        }

        if (e.getSource() == przycisk) {
            for (int i = 0; i < ballsList.size(); i++) {
                System.out.println("dystans piłki nr " + i + " = " + ballsList.get(i).DISTANSE);
                System.out.println("closeX = " + ballsList.get(i).closeX);
                System.out.println("closeY = " + ballsList.get(i).closeY);

            }
        }

        for (int i = 0; i < squarePanels.size(); i++) {

            if (e.getSource() == setXpositionRect.get(i)) {
                System.out.println(i);
                System.out.println(XPositionFieldSquares.get(i).getText());

                if (XPositionFieldSquares.get(i).getText() == null || Objects.equals(XPositionFieldSquares.get(i).getText(), "")) {
                    JOptionPane.showMessageDialog(controlPanel, "valuie must be greater than 0!");
                } else {
                    int X = Integer.parseInt(XPositionFieldSquares.get(i).getText());
                    if (X >= 0) {
                        squareList.get(i).setX(X);
                        System.out.println("Changed X position of Square : " + i + " = " + X);
                    }
                }
            }

            if (e.getSource() == setYpositionRect.get(i)) {
                if (YPositionFieldSquares.get(i).getText() == null || Objects.equals(YPositionFieldSquares.get(i).getText(), "")) {
                    JOptionPane.showMessageDialog(controlPanel, "valuie must be greater than 0!");
                } else {
                    int Y = Integer.parseInt(YPositionFieldSquares.get(i).getText());
                    if (Y >= 0) {
                        squareList.get(i).setY(Y);
                        System.out.println("Changed X position of Square : " + i + " = " + Y);
                    }
                }
            }


            if (e.getSource() == setWidthsquares.get(i)) {
                if (setWidthSquaresFields.get(i).getText() == null || Objects.equals(setWidthSquaresFields.get(i).getText(), "")) {
                    JOptionPane.showMessageDialog(controlPanel, "valuie must be greater than 0!");
                } else {
                    int M = Integer.parseInt(setWidthSquaresFields.get(i).getText());
                    if (M > 0) {
                        squareList.get(i).setWidth(M);
                        System.out.println("Changed size of square : " + i + " = " + M);
                    }
                }
            }

            if (e.getSource() == setColorOfSquaresButton.get(i)) {
                Random random = new Random();
                Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                squareList.get(i).setColor(color);
                squarePanels.get(i).setBackground(color);
                System.out.println("Changed Color of SQUARE : " + i);
            }
            repaint();
        }


    }

    private void createRandomRect() {

        Random rand = new Random();
        //new ball random position:
        rectWidth = rand.nextInt(250);
        rectX = rand.nextInt(ramka.getWidth() - 250 - rectWidth);
        rectY = rand.nextInt(ramka.getHeight() - rectWidth - 100);
        Color color = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));


        while (!repairPosition(rectWidth, rectX, rectY)) {
            rectWidth = rand.nextInt(200) + 10;
            rectX = rand.nextInt(ramka.getWidth() - 230 - rectWidth);
            rectY = rand.nextInt(ramka.getHeight() - rectWidth - 100);
        }

        if (repairPosition(rectWidth, rectX, rectY)) {
            square = new Square(rectX, rectY, rectWidth, rectWidth, color);
            squareList.add(this.square);
            addSquarePanel(squareList.size() - 1);
            createRect = true;
            System.out.println(rectWidth + " wielkosc");
            for (Ball balls : ballsList) {
                balls.manyHitSquare(squareList.size());
            }
        }


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

    @Override
    public void mouseDragged(MouseEvent e) {
        for (int i = 0; i < ballsList.size(); i++) {

            double mouseX = e.getX();
            double mouseY = e.getY();

            if (mouseX > ballsList.get(i).x && mouseX < ballsList.get(i).x + ballsList.get(i).mass &&
                    mouseY > ballsList.get(i).y && mouseY < ballsList.get(i).y + ballsList.get(i).mass) {
                indexOfCatchBall = i;
                ballCatchActive = true;
                ballsList.get(i).x = (int) (mouseX - ballsList.get(i).radius);
                ballsList.get(i).y = (int) (mouseY - ballsList.get(i).radius);
            }

        }

        if (ballCatchActive) {
            double mouseX = e.getX();
            double mouseY = e.getY();
            ballsList.get(indexOfCatchBall).x = (int) (mouseX - ballsList.get(indexOfCatchBall).radius);
            ballsList.get(indexOfCatchBall).y = (int) (mouseY - ballsList.get(indexOfCatchBall).radius);
            ballCatchActive = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


}
