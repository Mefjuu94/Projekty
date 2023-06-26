import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Panel extends JPanel implements ActionListener {

    int WIDTH = 800;
    int HEIGHT = 800;

    Random random = new Random();

    Color[] fourColors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE};
    List<Color> randomColors = new ArrayList<>();
    ;
    List<Color> rowColors = new ArrayList<>();

    Scanner scanner;
    FileWriter writer;

    boolean hide = true;

    int x, y;
    int labelsX = 0;
    int labelsY = 700;

    int hideX = 220;
    int hideY = 50;

    int clickCounter = 0;

    ImageIcon background = new ImageIcon("src/sniezka.png");
    ImageIcon mainFrame = new ImageIcon("src/mainframe.png");

    ImageIcon red = new ImageIcon("src/red.png");
    ImageIcon green = new ImageIcon("src/green.png");
    ImageIcon blue = new ImageIcon("src/blue.png");
    ImageIcon orange = new ImageIcon("src/orange.png");

    ImageIcon upArrow = new ImageIcon("src/up-arrow.png");
    ImageIcon frame = new ImageIcon("src/frame2.png");
    ImageIcon question = new ImageIcon("src/question.png");




    JButton label, label1, label2, label3, hideColor, zapisz,nowaGra, odczyt;
    Color c;
    int colCounter = 1;
    int rowCounter = 0;
    int loscounter = 0;

    int R;
    int G;
    int B;


    int validCounter = 0;
    int[] validChoices = {0, 0, 0, 0};

    int row = 0;

    int col = 0;

    boolean endGmae = false;
    boolean maluj = false;
    boolean newGame = false;
    boolean load = false;

    Color[][] fullFill = new Color[5][5];



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
        label3.setIcon(orange);
        label3.setBounds(new Rectangle(labelsX, labelsY, 40, 40));
        label3.setOpaque(true);
        this.add(label3);
        label3.setVisible(true);

        hideColor = new JButton("Odkryj");
        hideColor.addActionListener(this);
        hideColor.setBounds(new Rectangle(250, 120, 150, 40));
        hideColor.setOpaque(true);
        this.add(hideColor);
        hideColor.setVisible(true);

        ///////////////////////zapisz nowa gra
        zapisz = new JButton("Zapisz grę");
        zapisz.addActionListener(this);
        zapisz.setBounds(new Rectangle(540, 50, 150, 40));
        zapisz.setOpaque(true);
        this.add(zapisz);
        zapisz.setVisible(true);

        nowaGra = new JButton("Nowa Gra");
        nowaGra.addActionListener(this);
        nowaGra.setBounds(new Rectangle(540, 250, 150, 40));
        nowaGra.setOpaque(true);
        this.add(nowaGra);
        nowaGra.setVisible(true);

        odczyt = new JButton("Wczytaj");
        odczyt.addActionListener(this);
        odczyt.setBounds(new Rectangle(540, 350, 150, 40));
        odczyt.setOpaque(true);
        this.add(odczyt);
        odczyt.setVisible(true);
    }

    MENU menu = new MENU();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        losoweGenerowanie(g2d);

        background.paintIcon(this, g2d, 0, 0);
        mainFrame.paintIcon(this, g2d, 150, 190);
        g2d.setColor(Color.lightGray);

        losoweGenerowanie(g2d);

        //do wyełnienia szare



        if (!load) {
            fillGray(g2d); // DZIALA
            fillFullmethod(g2d, c);  //DZIALA
        } else{
            odczytPoSave(g2d);
            load = false;
        }
        //strzałka
        setActiveArrow(g2d, clickCounter);  // DZIALA
        drawGoodChoices(g2d, rowCounter, validChoices);
        // walidacja

        validate(randomColors, g2d); // dziala



    }

    Panel() {
        this.setLayout(null);
    }

//    enum STATE{
//        MENU,
//        GAME
//    }
//
//    public static STATE State = STATE.GAME; /// zmienic na GAME zebyh grac


    //-------------------------------------Generowanie losowych kolorów do zghadnięcia
    public void losoweGenerowanie(Graphics2D g2d) {
        x = 220;
        y = 50;

        if (randomColors.size() < 1 || loscounter == 0) {
            for (int i = 0; i < fourColors.length; i++) {
                int los = random.nextInt(fourColors.length);
                randomColors.add(fourColors[los]);
            }
            System.out.println("wygenerowałem nową tablicę!!!!!");
            loscounter = 1;
        }

        hideColors(g2d);
    }


    public void hideColors(Graphics2D g2d) {

        if (!hide) {
            x = 220;
            y = 50;
            for (Color randomColor : randomColors) {

                g2d.setColor(randomColor);
                g2d.fillOval(x, y, 40, 40);
                x += 60;
                g2d.setColor(Color.BLACK);

                g2d.drawString("Kolory wygenerowane przez kompa", 200, 50);
                g2d.setColor(Color.darkGray);

            }
        }
        g2d.setColor(Color.darkGray);

        if (hide) {
            hideX = 220;
            hideY = 50;
            for (int i = 0; i < 4; i++) {
                question.paintIcon(this, g2d, hideX, hideY);
                hideX += 60;
            }
        }


        maluj = true;

        System.out.println(clickCounter);


    }

    //-------------------------------------Wypełnienie szarym
    public void fillGray(Graphics2D g2d) {
        x = 220;
        y = 550;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (fullFill[i][j] == null) {
                    fullFill[i][j] = Color.darkGray;
                    g2d.setColor(Color.darkGray);
                }
                g2d.fillOval(x, y, 40, 40);

                x += 60;
            }
            y -= 80;
            x = 220;
        }
    }

    //-------------------------------------Wypełnianie kololrem
    public void fillFullmethod(Graphics2D g2d, Color c) {

        x = 220;
        y = 550;

        System.out.println(clickCounter);

        if (clickCounter > 0 || row > 0) {

            if (maluj && clickCounter == 0) {
                col = clickCounter;
            } else{
                col = clickCounter - 1;
                fullFill[row][col] = c; // lub New Color R G B
                rowColors.add(c);
        }


            g2d.setColor(fullFill[row][col]); // wypełnianie tablicy konkretnymn kolorem

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {

                    if (fullFill[i][j] == null) {
                        g2d.setColor(Color.darkGray);
                    }

                    g2d.setColor(fullFill[i][j]); // o kolrze w tabeli
                    g2d.fillOval(x, y, 40, 40); // maluj kolko
                    x += 60;
                }
                y -= 80;
                x = 220;
            }
        }


        if (clickCounter == 4) {

            clickCounter = 0;
            row = row + 1;
            // System.out.println("wiersz " + row);
        }


    }

    ////-------------sprawdzenie poprawnosci ---
    public void validate(List<Color> randomcolors, Graphics2D g2d) {


        if (col == 3) {
            for (int i = 0; i < 4; i++) {
                if (rowColors.size() > 0) {
                    Color ball = fullFill[row-1][i];
                    Color random = randomcolors.get(i);

                    if (ball != null) {
                        if (ball.equals(random)) {

                            validCounter++;
                            validChoices[i] = 1; // jest kolor
                        } else {
                            validChoices[i] = 0; // nie ma koloru
                        }
                    }
                }
                //System.out.println(validChoices[i]);
            }
            drawGoodChoices(g2d, rowCounter, validChoices);
            rowColors.removeAll(rowColors);
        }
        //System.out.println("poprawne " + validCounter);


        if (validCounter == 4) {
            System.out.println("Zgadłeś wszystkie kolory!!");
            int dialogButton = JOptionPane.showConfirmDialog(null,
                    "Gratulacje, Zgadłeś wszystkie kolory! Chcesz zagrać jeszcze raz?", "Gratulacje", JOptionPane.YES_NO_OPTION);
            endGmae = true;
            hide = true;
            if (dialogButton == 1) {
                System.exit(0);
            } else if (dialogButton == 0) {
                row = 0;
                validCounter = 0;
                endGmae = false;
                randomColors.removeAll(randomColors);
                g2d.setColor(Color.lightGray); // tło
                g2d.fillRect(0, 0, 800, 800);
                background.paintIcon(this, g2d, 0, 0);
                mainFrame.paintIcon(this, g2d, 150, 190);

                losoweGenerowanie(g2d);

                //do wyełnienia szare
                fillGray(g2d); // DZIALA
                for (int i = 0; i < validChoices.length; i++) {
                    validChoices[i] = 0;  // wyzerowanie tablicy
                }

                //tablica znów jest cała szara
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 4; j++) {
                        fullFill[i][j] = Color.darkGray;
                        g2d.setColor(Color.darkGray);
                    }
                }

                drawGoodChoices(g2d, rowCounter, validChoices); //nie dziala

                fillFullmethod(g2d, c);  //DZIALA
                //strzałka
                setActiveArrow(g2d, clickCounter);  // DZIALA
            }
        } else if (row == 5 || endGmae) {
            int dialogButton = JOptionPane.showConfirmDialog(null,
                    "Wykorzystałeś wszystkie szanse, chcesz zagrać jeszcze raz?", "Uwaga", JOptionPane.YES_NO_OPTION);
            hide = true;
            if (dialogButton == 1) {
                System.exit(0);
            } else if (dialogButton == 0) {
                row = 0;
                randomColors.removeAll(randomColors);
                g2d.setColor(Color.lightGray); // tło
                g2d.fillRect(0, 0, 800, 800);
                background.paintIcon(this, g2d, 0, 0);
                mainFrame.paintIcon(this, g2d, 150, 190);

                losoweGenerowanie(g2d);

                //do wyełnienia szare
                fillGray(g2d); // DZIALA
                for (int i = 0; i < validChoices.length; i++) {
                    validChoices[i] = 0;  // wyzerowanie tablicy
                }

                //tablica znów jest cała szara
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 4; j++) {
                        fullFill[i][j] = Color.darkGray;
                        g2d.setColor(Color.darkGray);
                    }
                }
                drawGoodChoices(g2d, rowCounter, validChoices); //DZIALA

                fillFullmethod(g2d, c);  //DZIALA
                //strzałka
                setActiveArrow(g2d, clickCounter);  // DZIALA
            }
        } else if (newGame) {
            row = 0;
            validCounter = 0;
            clickCounter = 0;
            randomColors.removeAll(randomColors);
            g2d.setColor(Color.lightGray); // tło
            g2d.fillRect(0, 0, 800, 800);
            background.paintIcon(this, g2d, 0, 0);
            mainFrame.paintIcon(this, g2d, 150, 190);

            losoweGenerowanie(g2d);

            //do wyełnienia szare
            fillGray(g2d); // DZIALA
            for (int i = 0; i < validChoices.length; i++) {
                validChoices[i] = 0;  // wyzerowanie tablicy
            }

            //tablica znów jest cała szara
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {
                    fullFill[i][j] = Color.darkGray;
                    g2d.setColor(Color.darkGray);
                }
            }
            drawGoodChoices(g2d, rowCounter, validChoices); //DZIALA

            fillFullmethod(g2d, c);  //DZIALA
            //strzałka
            setActiveArrow(g2d, clickCounter);  // DZIALA
            newGame = false;
        }

        validCounter = 0;


    }

    public void drawGoodChoices(Graphics2D g2d, int rowCounter, int[] validChoices) {

        g2d.setColor(Color.black);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 28));
        g2d.drawString("Twoje Wybory", 520, 500);
        frame.paintIcon(this, g2d, 485, 465);
        g2d.setStroke(new BasicStroke(6));


        int x = 520;
        int y = 540;
        for (int i = 0; i < 4; i++) {
            if (validChoices[i] == 1) {
                g2d.setColor(randomColors.get(i)); // o kolrze w tabeli
                g2d.fillOval(x, y, 30, 30); // maluj kolko
                g2d.setColor(Color.black);
                g2d.drawOval(x, y, 30, 30); // maluj kolko
            } else {
                g2d.setColor(Color.black); // o kolrze w tabeli
                g2d.fillOval(x, y, 30, 30); // maluj kolko
            }
            x += 50;
        }

        x = 220;
    }

    public void metoda() {


    }

    //-------------------------------------Strzałka
    public void setActiveArrow(Graphics2D g2d, int clickCounter) {
        int xActive = 225;
        int yActive = 590 - (80 * row);
        int licznik = 0;


        if (clickCounter > 0) {
            xActive = xActive + (60 * clickCounter);
            licznik = clickCounter / 4;
            if (clickCounter == 4) {
                licznik = 0;
            }
        }
        System.out.println(licznik);

        System.out.println(xActive);

        upArrow.paintIcon(this, g2d, xActive, yActive);
    }


    //////////////////////////////zapisz, odczyt metody

    public void zapis(Color[][] fullFill) throws IOException {
        System.out.println(System.getProperty("user.dir"));


        String saveName = JOptionPane.showInputDialog("Nazwij Save");

        writer = new FileWriter(saveName);
        String kolor = "";
        writer.write( Integer.toString(clickCounter));
        writer.write("\n");
        writer.write( Integer.toString(row));
        writer.write("\n");

        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(randomColors.get(i));
            String randomCol = rand.substring(15);
            System.out.println(randomCol);
            writer.write(randomCol + ";");
        }
        writer.write("\n");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {

                kolor = String.valueOf(fullFill[i][j]);
                String kolor1 = kolor.substring(15);
                writer.write(kolor1 + ";");

                if (fullFill[i][j] == null) {

                }
            }
            writer.write("\n");

        }

        writer.close();
        int dialog = JOptionPane.showConfirmDialog(this,"Zapisano pod nazwą " + saveName,"Informacja",2);
    }

    public void odczyt() throws IOException {

        System.out.println("Odczyt:");


        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setCurrentDirectory(new File("C:\\Users\\mateu\\OneDrive\\Pulpit\\Projekty\\MasterMind2"));
        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            Scanner save = new Scanner(file);

            //pierwsze dwie linijki to odczyt Clickcounter i wiesza
            clickCounter = Integer.parseInt(save.nextLine());
            row = Integer.parseInt(save.nextLine());
            System.out.println("--------odczytanie random kolors---------");

            //kolejne linijki to wycięcie niepotrzebnych znaków i wczytanie "losowej tablicy"
            {
                randomColors.removeAll(randomColors);
            String R, G, B;
            int r = 0;
            int g = 0;
            int b = 0;
            String linijka = save.nextLine();

            String[] split = linijka.split(";");

            for (int i = 0; i < split.length; i++) {
                // System.out.println("dlugosc splitui =" + split.length);

                //dzielenie poszczegolnych kolorów
                String[] SplitRGB = split[i].split("=");
                for (int j = 0; j < SplitRGB.length; j++) {


                    //wycinanie pozostałych znaków żeby zostały tylko liczby do RGB
                    SplitRGB[j] = SplitRGB[j].replaceAll("r", "");
                    SplitRGB[j] = SplitRGB[j].replaceAll("b", "");
                    SplitRGB[j] = SplitRGB[j].replaceAll("g", "");
                    SplitRGB[j] = SplitRGB[j].replaceAll("]", "");
                    SplitRGB[j] = SplitRGB[j].replaceAll(",", "");
                    System.out.println(SplitRGB[j]);

                }
                R = SplitRGB[1];
                G = SplitRGB[2];
                B = SplitRGB[3];

                r = Integer.parseInt(R);
                g = Integer.parseInt(G);
                b = Integer.parseInt(B);
                Color newC = new Color(r, g, b);
                randomColors.add(new Color(r,g,b));
            }}


            System.out.println("--------------odczytanie tablicy----------------");


            //reszta tablicy
            int wiersz = 0;
            while (save.hasNextLine()) {
                int przejscia = 0;
                String R, G, B;
                int r = 0;
                int g = 0;
                int b = 0;
                String linijka = save.nextLine();

                    String[] split = linijka.split(";");

                    for (int i = 0; i < split.length; i++) {
                       // System.out.println("dlugosc splitui =" + split.length);

                        //dzielenie poszczegolnych kolorów
                        String[] SplitRGB = split[i].split("=");
                        for (int j = 0; j < SplitRGB.length; j++) {


                            //wycinanie pozostałych znaków żeby zostały tylko liczby do RGB
                            SplitRGB[j] = SplitRGB[j].replaceAll("r", "");
                            SplitRGB[j] = SplitRGB[j].replaceAll("b", "");
                            SplitRGB[j] = SplitRGB[j].replaceAll("g", "");
                            SplitRGB[j] = SplitRGB[j].replaceAll("]", "");
                            SplitRGB[j] = SplitRGB[j].replaceAll(",", "");
                            //System.out.println(SplitRGB[j]);

                        }
                        R = SplitRGB[1];
                        G = SplitRGB[2];
                        B = SplitRGB[3];

                        r = Integer.parseInt(R);
                        g = Integer.parseInt(G);
                        b = Integer.parseInt(B);
                        Color newC = new Color(r,g,b);

                        //System.out.println(newC +" kulka " + przejscia);
                        przejscia++;
                        //split ma tyle samo indeow co pilek w wierszu
                        fullFill[wiersz][i] = newC; //uzupelnienie tablicy

                    }
                //System.out.println("wiersz " + wiersz);
                    wiersz++;

                //System.out.println();
                } // koniec while


                load = true;
        }

        for (int i = 0; i < 4; i++) {

            System.out.println(randomColors.get(i));
        }
        repaint();
    }

    public void odczytPoSave(Graphics2D g2d){

        x = 220;
        y = 550;

        g2d.setColor(fullFill[row][col]); // wypełnianie tablicy konkretnymn kolorem

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {

                if (fullFill[i][j] == null) {
                    g2d.setColor(Color.darkGray);
                }

                g2d.setColor(fullFill[i][j]); // o kolrze w tabeli
                g2d.fillOval(x, y, 40, 40); // maluj kolko
                x += 60;
            }
            y -= 80;
            x = 220;
        }

        //sprawdzenie
        rowColors.removeAll(rowColors);
        validCounter = 0;
        System.out.println(col + " col");
        System.out.println(row + " row");
        col = clickCounter-1;

        System.out.println();
        System.out.println("rowcolors");


        if (row > 0) {
            for (int i = 0; i < 4; i++) {

                Color ball = fullFill[row - 1][i];
                Color random = randomColors.get(i);

                if (ball.equals(random)) {
                    System.out.println("jest kolor " + i);
                    validCounter++;
                    validChoices[i] = 1; // jest kolor
                } else {
                    validChoices[i] = 0; // nie ma koloru
                    System.out.println("nie ma koloru " + i);
                }

                //System.out.println(validChoices[i]);
                drawGoodChoices(g2d, rowCounter, validChoices);

                validate(randomColors, g2d);
            }
        }


    }


    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == label) {
            System.out.println("red");
            clickCounter++;
            c = fourColors[0];
        }

        if (e.getSource() == label1) {
            System.out.println("gren");
            clickCounter++;
            c = fourColors[1];
        }
        if (e.getSource() == label2) {
            System.out.println("blue");
            clickCounter++;
            c = fourColors[2];
        }
        if (e.getSource() == label3) {
            System.out.println("Orange");
            clickCounter++;
            c = fourColors[3];
        }


        if (e.getSource() == hideColor) {
            hide = !hide;
        }


        if (c != null) {
            for (int i = 0; i < 3; i++) {

                if (i == 0) {
                    R = c.getRed();
                }
                if (i == 1) {
                    G = c.getGreen();
                }
                if (i == 2) {
                    B = c.getBlue();
                }
            }
            System.out.println();
            repaint();
        }

        if (e.getSource() == zapisz){
            try {
                zapis(fullFill);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (e.getSource() == nowaGra){
            newGame = true;
        }

        if (e.getSource() == odczyt){
            try {
                odczyt();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


    }


}
