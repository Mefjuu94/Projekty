import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class Panel extends JPanel implements ActionListener, KeyListener {

    int WIDTH = 800;
    int HEIGHT = 800;

    Random random = new Random();

    List<ImageIcon> randomColors = new ArrayList<>();
    List<ImageIcon> rowColors = new ArrayList<>();

    FileWriter writer;

    boolean hide = true;

    int x, y;
    int labelsX = 0;
    int labelsY = 700;

    int hideX = 220;
    int hideY = 50;

    int clickCounter = 0;

    ImageIcon background = new ImageIcon("src/Icons/b1.png");
    ImageIcon mainFrame = new ImageIcon("src/Icons/mainframe.png");

    ImageIcon red = new ImageIcon("src/Icons/mars.png");
    ImageIcon green = new ImageIcon("src/Icons/earth.png");
    ImageIcon blue = new ImageIcon("src/Icons/neptun.png");
    ImageIcon orange = new ImageIcon("src/Icons/sun.png");
    ImageIcon[] fourIcons = {red,green,blue,orange};

    ImageIcon upArrow = new ImageIcon("src/Icons/up-arrow.png");
    ImageIcon frame = new ImageIcon("src/Icons/frame2.png");
    ImageIcon question = new ImageIcon("src/Icons/question.png");
    ImageIcon podium = new ImageIcon("src/Icons/podium.png");

    boolean drawPodium = false;


    JButton label, label1, label2, label3, hideColor, zapisz,nowaGra, odczyt, bestOf;
    ImageIcon c;
    int colCounter = 1;
    int rowCounter = 0;
    int loscounter = 0;


    int validCounter = 0;
    int[] validChoices = {0, 0, 0, 0};

    int row = 0;

    int col = 0;

    boolean endGmae = false;
    boolean maluj = false;
    boolean newGame = false;
    boolean load = false;

    double BasicPoints = 100;
    Long StartTime;
    Long czasGry;
    int czasGryOdczyt = 0;
    File scoreFile = new File("C:\\Users\\mateu\\OneDrive\\Pulpit\\Projekty\\MasterMind2\\Score.txt");
    String[] table = new String[3];
    ImageIcon winner1 = new ImageIcon("src/GIFS/on.gif");
    ImageIcon winner2 = new ImageIcon("src/GIFS/on1.gif");
    ImageIcon winner3 = new ImageIcon("src/GIFS/ona.gif");
    ImageIcon winner4 = new ImageIcon("src/GIFS/ona1.gif");
    ImageIcon[] winners = new ImageIcon[3];


    ImageIcon[][] fullFill = new ImageIcon[5][5];



    {
        labelsX = 220;
        label = new IconButton(red,labelsX,labelsY,this);
        labelsX += 60;
        label1 = new IconButton(green,labelsX,labelsY,this);
        labelsX += 60;
        label2 = new IconButton(blue,labelsX,labelsY,this);
        labelsX += 60;
        label3 = new IconButton(orange,labelsX,labelsY,this);
        //odczyt, zapis, hide

        hideColor = new PrimaryButton("Odkryj",250,120,this);

        zapisz = new PrimaryButton("Zapisz grę",540,50,this);

        nowaGra = new PrimaryButton("Nowa Gra",540,250,this);

        odczyt = new PrimaryButton("Wczytaj",540,350,this);

        bestOf = new PrimaryButton("Galeria Sław",20,650,this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (drawPodium){
            label.setVisible(false);
            label1.setVisible(false);
            label2.setVisible(false);
            label3.setVisible(false);
            hideColor.setVisible(false);
            zapisz.setVisible(false);
            nowaGra.setVisible(false);
            odczyt.setVisible(false);

            bestOf.setBackground(Color.black);
            bestOf.setForeground(Color.WHITE);
            bestOf.setOpaque(true);
            bestOf.setText("Graj dalej");

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0,0,800,800);
            podium.paintIcon(this,g2d,0,300);
            g2d.setColor(Color.BLACK);
            Font font = new Font("moja", Font.BOLD,14);
            g2d.setFont(font);

            //winner2.paintIcon(this,g2d,300,100);
            winners[0].paintIcon(this,g2d,300,100);
            //System.out.println(winners[0]);
            g2d.drawString( table[0],360,360);

            //winner3.paintIcon(this,g2d,20,200);
            winners[1].paintIcon(this,g2d,80,180);
            //System.out.println(winners[1]);
            g2d.drawString( table[1],150,456);

            //winner3.paintIcon(this,g2d,500,200);
            winners[2].paintIcon(this,g2d,500,220);
            //System.out.println(winners[2]);
            g2d.drawString( table[2],560,484);
        }else {

            label.setVisible(true);
            label1.setVisible(true);
            label2.setVisible(true);
            label3.setVisible(true);
            hideColor.setVisible(true);
            zapisz.setVisible(true);
            nowaGra.setVisible(true);
            odczyt.setVisible(true);

            bestOf.setOpaque(false);
            bestOf.setText("Galeria sław -> 'W");

            losoweGenerowanie(g2d);

            background.paintIcon(this, g2d, 0, 0);
            mainFrame.paintIcon(this, g2d, 150, 190);
            g2d.setColor(Color.black);

            losoweGenerowanie(g2d);

            //do wyełnienia szare

            if (!load) {
                fillGray(g2d); // DZIALA
                fillFullmethod(g2d, c);  //DZIALA
            } else {
                try {
                    odczytPoSave(g2d);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                load = false;
                System.out.println(row+ " row");
                System.out.println(col+ " col");
                if (clickCounter == 0 ){
                    col = clickCounter;
                }
                c = fullFill[row][col];
                fillFullmethod(g2d, c);
                StartTime = System.currentTimeMillis();
            }

            //strzałka
            setActiveArrow(g2d, clickCounter);  // DZIALA
            drawGoodChoices(g2d, rowCounter, validChoices);
            // walidacja

            try {
                validate(randomColors, g2d); // dziala
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    Panel() {
        addKeyListener(this);
        setFocusable(true);
        this.setLayout(null);
    }


    //-------------------------------------Generowanie losowych kolorów do zghadnięcia
    public void losoweGenerowanie(Graphics2D g2d) {
        x = 220;
        y = 50;

        if (randomColors.size() < 1 || loscounter == 0) {
            for (int i = 0; i < fourIcons.length; i++) {
                int los = random.nextInt(3);
                randomColors.add(fourIcons[los]);
            }
            System.out.println("wygenerowałem nową tablicę!!!!!");
            StartTime = System.currentTimeMillis();
            System.out.println(StartTime);
            loscounter = 1;
        }

        hideColors(g2d);
    }


    public void hideColors(Graphics2D g2d) {

        if (!hide) {
            x = 220;
            y = 50;
            for (ImageIcon randomColors : randomColors) {

                //g2d.setColor(randomColor);
                //g2d.fillOval(x, y, 40, 40);
                randomColors.paintIcon(this,g2d,x,y);
                x += 60;
                g2d.setColor(Color.BLACK);

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

    }

    //-------------------------------------Wypełnienie szarym
    public void fillGray(Graphics2D g2d) {
        x = 220;
        y = 550;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (fullFill[i][j] == null) {

                    g2d.setColor(Color.darkGray);
                    g2d.fillOval(x, y, 40, 40);
                }
                g2d.fillOval(x, y, 40, 40);

                x += 60;
            }
            y -= 80;
            x = 220;
        }
    }

    //-------------------------------------Wypełnianie kololrem
    public void fillFullmethod(Graphics2D g2d, ImageIcon c) {

        x = 220;
        y = 550;

        //System.out.println(clickCounter);

        if (clickCounter > 0 || row > 0) {

            if (maluj && clickCounter == 0) {
                col = clickCounter;
            } else{
                col = clickCounter - 1;
                fullFill[row][col] = c; // lub New Color R G B
                rowColors.add(c);
        }

            //g2d.setColor(fullFill[row][col]); // wypełnianie tablicy konkretnymn kolorem

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 4; j++) {

                    if (fullFill[i][j] == null) {
                        g2d.setColor(Color.darkGray);
                        g2d.fillOval(x, y, 40, 40); // maluj kolko
                    }else {
                        fullFill[i][j].paintIcon(this, g2d, x, y);
                    }
                    x += 60;
                }
                y -= 80;
                x = 220;
            }
        }

        if (clickCounter == 4) {

            clickCounter = 0;
            row = row + 1;
        }


    }

    ////-------------sprawdzenie poprawnosci ---
    public void validate(List<ImageIcon> randomcolors, Graphics2D g2d) throws IOException {

        if (col == 3) {
            {
                for (int i = 0; i < 4; i++) {
                    if (rowColors.size() > 0) {

                        ImageIcon ball = fullFill[row - 1][i];
                        ImageIcon random = randomcolors.get(i);

                        String planeta = ball.toString();
                        String randomCol = random.toString();


                        if (planeta.equals(randomCol)) {
                            validCounter++;
                            validChoices[i] = 1; // jest kolor
                        } else {
                            validChoices[i] = 0; // nie ma koloru
                        }
                    }
                    //System.out.println(validChoices[i]);
                }
                rowColors.removeAll(rowColors);
                drawGoodChoices(g2d, rowCounter, validChoices);

            }

            if (rowColors.size() == 4) {
                rowColors.removeAll(rowColors);
            }
            //System.out.println("poprawne " + validCounter);


            if (validCounter == 4) {
                System.out.println("Zgadłeś wszystkie kolory!!");

                //czas gry Pubnktacja
                System.out.println(StartTime  + " -- StartTime");
                Long endGame = System.currentTimeMillis();
                System.out.println(endGame  + " -- End Time");
                czasGry = (endGame - StartTime)/1000 + czasGryOdczyt;
                System.out.println("czas Gry wyniósł = " + czasGry);


                int dialogButton = JOptionPane.showConfirmDialog(null,
                        "Gratulacje, Zgadłeś wszystkie kolory! Chcesz zagrać jeszcze raz?", "Gratulacje", JOptionPane.YES_NO_OPTION);
                hide = true;
                BestOf();

                if (dialogButton == 1) {
                    System.exit(0);
                } else if (dialogButton == 0) {
                    c = null;
                    row = 0;
                    validCounter = 0;
                    endGmae = false;
                    randomColors.removeAll(randomColors);
                    System.out.println("wyzerowania tablica randomów");
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
                            fullFill[i][j] = null;
                            g2d.setColor(Color.darkGray);
                        }
                    }

                    drawGoodChoices(g2d, rowCounter, validChoices); //nie dziala

                    fillFullmethod(g2d, c);  //DZIALA
                    //strzałka
                    setActiveArrow(g2d, clickCounter);  // DZIALA
                }
            }

            if (row == 5 || endGmae) {
                System.out.println("row 5 lub endganme");
                int dialogButton = JOptionPane.showConfirmDialog(null,
                        "Wykorzystałeś wszystkie szanse, chcesz zagrać jeszcze raz?", "Uwaga", JOptionPane.YES_NO_OPTION);
                if (dialogButton == 1) {
                    System.exit(0);
                } else if (dialogButton == 0) {
                    c = null;
                    row = 0;
                    clickCounter = 0;
                    randomColors.removeAll(randomColors);
                    System.out.println("wyzerowania tablica randomów");
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
                            fullFill[i][j] = null;
                            g2d.setColor(Color.darkGray);
                        }
                    }
                    fillFullmethod(g2d, c);  //DZIALA
                    //strzałka
                    setActiveArrow(g2d, clickCounter);  // DZIALA
                    repaint();
                }
            }

            validCounter = 0;
        }

        if (newGame) {
            c = null;
            System.out.println("NOWA GRA!!");
            row = 0;
            validCounter = 0;
            clickCounter = 0;
            randomColors.removeAll(randomColors);
            rowColors.removeAll(rowColors);
            System.out.println("wyzerowania tablica randomów");
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
                    fullFill[i][j] = null;
                    g2d.setColor(Color.darkGray);
                }
            }
            drawGoodChoices(g2d, rowCounter, validChoices); //DZIALA

            fillFullmethod(g2d, c);  //DZIALA
            //strzałka
            setActiveArrow(g2d, clickCounter);  // DZIALA
            newGame = false;
            repaint();
        }

    }

    public void drawGoodChoices(Graphics2D g2d, int rowCounter, int[] validChoices) {

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Calibri", Font.PLAIN, 28));
        g2d.drawString("Twoje Wybory", 520, 500);
        frame.paintIcon(this, g2d, 485, 465);
        g2d.setStroke(new BasicStroke(2));


        int x = 520;
        int y = 540;
        for (int i = 0; i < 4; i++) {
            if (validChoices[i] == 1) {
                //g2d.setColor(randomColors.get(i)); // o kolrze w tabeli
                //g2d.fillOval(x, y, 30, 30); // maluj kolko
                randomColors.get(i).paintIcon(this,g2d,x,y);
                g2d.setColor(Color.WHITE);
                g2d.drawOval(x, y, 38, 38); // maluj kolko
            } else {
                g2d.setColor(Color.WHITE); // o kolrze w tabeli
                g2d.fillOval(x, y, 40, 40); // maluj kolko
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
        //System.out.println(licznik);

        //System.out.println(xActive);

        upArrow.paintIcon(this, g2d, xActive, yActive);
    }

    //////////////////////////////zapisz, odczyt metody

    public void zapis(ImageIcon[][] fullFill) throws IOException {
        //System.out.println(System.getProperty("user.dir"));
        Long endGame = System.currentTimeMillis();
        System.out.println(endGame  + " -- End Time");
        czasGry = (endGame - StartTime)/1000;
        String saveName = JOptionPane.showInputDialog("Nazwij Save");




        writer = new FileWriter(saveName);
        String kolor = "";

        writer.write(Long.toString(czasGry));
        writer.write("\n");
        writer.write( Integer.toString(clickCounter));
        writer.write("\n");
        writer.write( Integer.toString(row));
        writer.write("\n");

        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(randomColors.get(i));
            //System.out.println(rand);
            writer.write(rand + ";");
        }
        writer.write("\n");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {

                kolor = String.valueOf(fullFill[i][j]);
                writer.write(kolor + ";");

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
            czasGryOdczyt = Integer.parseInt(save.nextLine());
            clickCounter = Integer.parseInt(save.nextLine());
            row = Integer.parseInt(save.nextLine());
            System.out.println("--------odczytanie random kolors---------");

            //kolejne linijki to wycięcie niepotrzebnych znaków i wczytanie "losowej tablicy"
            {
                randomColors.removeAll(randomColors);

            String linijka = save.nextLine();

            String[] split = linijka.split(";");

            for (int i = 0; i < 4; i++) {
                randomColors.add(new ImageIcon(split[i]));
                System.out.println("randomcolors " + split[i]);
            }}

            System.out.println("--------------odczytanie tablicy----------------");


            //reszta tablicy
            int wiersz = 0;

            while (save.hasNextLine()) {
                int przejscia = 0;

                String linijka = save.nextLine();
                String[] split = linijka.split(";");

                for (int i = 0; i < 4; i++) {

                    fullFill[wiersz][i] = new ImageIcon(split[i]);
                    przejscia++;
                }
                wiersz++;

                } // koniec while

                load = true;
        }

        for (int i = 0; i < 4; i++) {

            System.out.println();
        }
        repaint();
    }

    public void odczytPoSave(Graphics2D g2d) throws IOException {

        x = 220;
        y = 550;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                g2d.setColor(Color.darkGray);
                g2d.fillOval(x,y,40,40);
                if (fullFill[i][j] != null) {
                    fullFill[i][j].paintIcon(this, g2d, x, y);
                }


                x += 60;
            }
            y -= 80;
            x = 220;
        }
        //sprawdzenie

        rowColors.removeAll(rowColors);
        validCounter = 0;

        col = clickCounter-1;


        if (row > 0) {
            for (int i = 0; i < 4; i++) {

                ImageIcon ball = fullFill[row - 1][i];
                System.out.println(ball);
                ImageIcon random = randomColors.get(i);
                System.out.println(random);

                String planeta = ball.toString();
                String randomCol = random.toString();

                if (planeta.equals(randomCol)) {
                    validCounter++;
                    validChoices[i] = 1; // jest kolor
                } else {
                    validChoices[i] = 0; // nie ma koloru
                }
                System.out.println(validChoices[i]);
                drawGoodChoices(g2d, rowCounter, validChoices);

               validate(randomColors, g2d);
            }
        }


    }

    public void BestOf() throws IOException {

        double score = BasicPoints/czasGry;

        Scanner scanner1 = new Scanner(new File("Score.txt"));
        FileWriter Score;
        String NazwaScore = "Score.txt";

        String imie = JOptionPane.showInputDialog("Wpisz swoje Imię");

        String decimalScore = Double.toString(score);
        String decimal = decimalScore.substring(0,4);
        score = Double.parseDouble(decimal);

        String linijka = "";

        String[] lin1 = new String[3];
        int count = 0;
        boolean wiekszy = true;
        int ktoryWynik = 0;

        while (scanner1.hasNextLine()){

            linijka = scanner1.nextLine();
            String sub = linijka.substring(0,4);
            System.out.println(sub);

            double wynik = Double.parseDouble(sub); //wynik to stary, "score" to nowy wynik

            if (score > wynik && wiekszy){
                lin1[count] = score + " " + imie;
                ktoryWynik = count + 1;
                wiekszy = false ;
            }
            else {
                lin1[count] = linijka;
            }
            count++;
        }

        if (ktoryWynik != 0) {
            int dialog = JOptionPane.showConfirmDialog(null, imie + " To " + ktoryWynik + " Wynik w tabeli!", "Wszystko OK", 0);
        }else {
            int dialog = JOptionPane.showConfirmDialog(null, imie + " Jesteś poza podium!", "Wszystko OK", 0);
        }

        for (int i = 0; i < lin1.length; i++) {
            System.out.println(lin1[i]);
        }

        Score = new FileWriter(NazwaScore);
        for (int i = 0; i < 3; i++) {
            Score.write(lin1[i]);
            Score.write("\n");
        }
        Score.close();


    }

    public void readBestOf() throws IOException {

        System.out.println("szukam");

        if (!drawPodium) {
            drawPodium = true;
        } else {
            drawPodium = false;
        }

        //Score.txt
        Scanner scanner2 = new Scanner(new File("Score.txt"));
        int count = 0;

        while (scanner2.hasNextLine()){
            table[count] = scanner2.nextLine();
           count++;
        }

        String onOna = "";
        for (int i = 0; i < table.length ; i++) {
            String linia = table[i];

            int last = linia.length();
            System.out.println(linia);

            if (linia.endsWith("a")){
                System.out.println("linia zawiera a");
                winners[i] = winner3;
                if (winners[0] == winner3 || winners[1] == winner3) {
                    winners[i] = winner4;
                }
            }else {
                System.out.println("linia nie zawiera");
                winners[i] = winner1;
                if (winners[0] == winner1 || winners[2] == winner1) {
                    winners[i] = winner2;
                }
            }

        }


        repaint();

    }


    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == label) {
            System.out.println("mars");
            clickCounter++;
            c = fourIcons[0];
            rowColors.add(c);
            System.out.println("dodałem do tablicy " + c);
            repaint();
        }

        if (e.getSource() == label1) {
            System.out.println("earth");
            clickCounter++;
            c = fourIcons[1];
            rowColors.add(c);
            System.out.println("dodałem do tablicy " + c);
            repaint();
        }
        if (e.getSource() == label2) {
            System.out.println("neptun");
            clickCounter++;
            c = fourIcons[2];
            rowColors.add(c);
            System.out.println("dodałem do tablicy " + c);
            repaint();
        }
        if (e.getSource() == label3) {
            clickCounter++;
            c = fourIcons[3];
            System.out.println("sun");
            System.out.println(c);
            System.out.println(clickCounter);
            rowColors.add(c);
            System.out.println("dodałem do tablicy " + c);
            repaint();
        }


        if (e.getSource() == hideColor) {
            hide = !hide;
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
            System.out.println("przycisk nowa gra");
            newGame = true;
            repaint();
        }

        if (e.getSource() == bestOf ){
            System.out.println("Najlepsze Wyniki");
            ///'/

            try {
                readBestOf();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }

        if (e.getSource() == odczyt){
            c = null;
            try {
                odczyt();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        System.out.println(e.getKeyCode());

        System.out.println("weszło key pressed");


        if (e.getKeyCode() == KeyEvent.VK_W) {

            if (!drawPodium) {

                try {
                    readBestOf();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                drawPodium = true;
                System.out.println("weszło");
                repaint();
            }else {
                drawPodium = false;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
