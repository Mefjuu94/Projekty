package Figura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Figura implements KeyListener {

    //skonczyć:

    final int boardWidth = 15;
    final int boardHeight = 20;

    Shape figure = Shape.CreateShape();
    Shape nextFigure;
    public int[][] board = new int[boardWidth][boardHeight];

    public boolean pause = false;
    public ImageIcon pauseGame = new ImageIcon("src/Figura/img/PAUSE.png");

    boolean left, right, down, turn, hardDrop;
    //czas / tick do zjezxdzania w dol
    int dropSpeed = 500; // default 500
    int tickSpeed = 25; // co ile będzie się zmniejszać czas
    long trzystaMs = System.currentTimeMillis();

    int cellSize = 30;
    int moveX = 6;
    int moveY = 0;

    public long result = 0;
    public int scoreLineCounter = 0; // default 0
    int lineScoreToLevelUp = 3; // co ileś linie zwiększa szybkość 0 25ms ( tickSpeed )
    public int level = 1; // default 1
    int scoreX = 550;
    public int scoreY = 40;

    public boolean gameOver = false;

    //save

    public boolean save = true;
    FileWriter writer = new FileWriter("Save.txt", true);
    public String nickname = "";
    // load
    boolean load = true;
    Scanner scanner;
    String[][] podium = new String[3][3];
    ImageIcon goldTrophy = new ImageIcon("src/Figura/img/gold.png");
    ImageIcon silverTrophy = new ImageIcon("src/Figura/img/silver.png");
    ImageIcon bronzeTrophy = new ImageIcon("src/Figura/img/bronze.png");

    //nowa gra
    public boolean restartGame = false;

    {
        for (int i = 0; i < podium.length; i++) {
            for (int j = 0; j < podium[0].length; j++) {
                podium[i][j] = "0";
            }
        }
    }


    static Color[] colors = {Color.DARK_GRAY, Color.ORANGE, Color.BLUE, Color.magenta, Color.PINK, Color.GREEN, Color.RED}; // index 0 jest omijany
    //żeby wyświetlił sie kolor na dole tablicy - gdyby został index 0 to by zytało jako puste pole

    public Figura() throws IOException {

        {
            for (int i = 0; i < figure.tab.length; i++) {
                for (int j = 0; j < figure.tab[0].length; j++) {
                    figure.tab[i][j] = 0;
                }
            }
        }
        figure = Shape.CreateShape();
        nextFigure = Shape.CreateShape();
    }

    /// Rysowanie-----------------------

    public void rysujFigure(Graphics2D g2d, JPanel panel) throws InterruptedException, IOException {

        if (!gameOver) {
            if (pause) {
                //figura głowna
                drawMainFigure(g2d);
                //figura następna
                drawNextFigure(g2d);
                //tabica
                drawBoard(g2d);
                pauseGame.paintIcon(panel, g2d, 80, 150);
            } else {
                //figura głowna
                drawMainFigure(g2d);
                //figura następna
                drawNextFigure(g2d);
                //tabica
                drawBoard(g2d);
                //idź w doł
                goDown(g2d);
                //poruszanie
                updateMove();
                gameOver();
            }


            Font font = new Font(Font.SERIF, Font.BOLD, 30);
            g2d.setFont(font);

            g2d.setColor(Color.white);
            g2d.drawString("Score: " + result, scoreX, 350);
            g2d.drawString("Lines: " + scoreLineCounter, scoreX, 350 + scoreY);
            g2d.drawString("Level: " + level, scoreX, 350 + (scoreY * 2));
            g2d.drawString("Speed: " + dropSpeed, scoreX, 350 + (scoreY * 3));

        } else {

            drawBestScores(g2d);
            goldTrophy.paintIcon(panel, g2d, 360, 160);
            silverTrophy.paintIcon(panel, g2d, 360, 260);
            bronzeTrophy.paintIcon(panel, g2d, 360, 350);
        }

        restartGame();

    }

    public void restartGame() {

        if (restartGame) {
            gameOver = false;

            for (int i = 0; i < figure.tab.length; i++) {
                for (int j = 0; j < figure.tab[0].length; j++) {
                    figure.tab[i][j] = 0;
                }
            }

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j] = 0;
                }
            }

            figure = Shape.CreateShape();
            nextFigure = Shape.CreateShape();

            restartGame = false;
            gameOver = false;
            save = true;


            //reset Stats
            scoreLineCounter = 0;
            result = 0;
            dropSpeed = 500;
            level = 1;
        }
    }


    public void drawMainFigure(Graphics2D g2d) {
        for (int i = 0; i < figure.tab.length; i++) {
            for (int j = 0; j < figure.tab[0].length; j++) {
                if (figure.tab[i][j] > 0) {
                    g2d.setColor(colors[figure.color]);
                    //ładuj kolor figury
                    //rysuj figure która jest:  index + ilosc przeunięć[movex] * wielkosc kratki[cellsize] + przesuniecie tablicy
                    // (0+6) * 30 + 50 = poczatkowa wspolrzedna na X 230
                    g2d.fillRect((i + moveX) * cellSize + 50, (j + moveY) * cellSize + 50, cellSize, cellSize);
                }
            }
        }

    }

    public void drawBoard(Graphics2D g2d) { // rysuje figury ktore spadły
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] > 0) {
                    g2d.setColor(colors[board[i][j]]);
                    g2d.fillRect(i * cellSize + 50, j * cellSize + 50, cellSize, cellSize);

                    g2d.setColor(Color.WHITE);
                    g2d.drawRect(i * cellSize + 50, j * cellSize + 50, cellSize, cellSize);
                }
            }
        }
    }


    public void goDown(Graphics2D g2d) { // spadanie klocków i  sprawdzanie kolizjii

        long curentms = System.currentTimeMillis();

        //sprawdzenhie granic
        if (!checkBoundariesX()) {
            repairPosition(15, g2d);
        }

        if (curentms - trzystaMs > dropSpeed) {
            if (canMove(0, 1)) {
                moveY++;
            } else {
                for (int i = 0; i < figure.tab.length; i++) {
                    for (int j = 0; j < figure.tab[0].length; j++) {

                        if (figure.tab[i][j] > 0) {
                            board[i + moveX][j + moveY] = figure.color;
                        }
                    }
                }

                moveY = 0;
                moveX = 6;
                hardDrop = false;

                figure = nextFigure; // następna figura z next
                nextFigure = Shape.CreateShape(); // losowanie next figury
            }
            trzystaMs = System.currentTimeMillis();
        }

        checkArrayGetPoints();

    }

    // sprawdzenie granic-------------- --> zrobic DLA KOLIZI FIGUR PRZY OBRUCENIU!!!!
    public boolean checkBoundariesX() {

        // sprawdzenie prawej storiny dZiała
        for (int j = 0; j < figure.tab[0].length; j++) {

            if (boardWidth < moveX + figure.tab.length) {

                if (figure.tab[figure.tab.length - 1][j] > 0) {
                    return false;
                }
            }
        }

        for (int j = 0; j < figure.tab[0].length; j++) {
            if (moveX < 0) {
                if (figure.tab[0][j] > 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public void repairPosition(int i, Graphics2D g2d) { // reperuje pozycje żeby nie był poza granicą

        //naprawa pozycji
        if (i < i + moveX) {
            moveX -= 1;
        }
        if (moveX < 0) {
            moveX += 1;
        }
        drawMainFigure(g2d);
    }


    public boolean canMove(int x, int y) { // sprawia, ze "widziw figury pod soba

        for (int i = 0; i < figure.tab.length; i++) {
            for (int j = 0; j < figure.tab[0].length; j++) {
                //jak index jest wiekszy od 1
                if (figure.tab[i][j] > 0) {
                    //sprawdz czy nie dotknęło granicy
                    if (i + moveX + x < 0 || i + moveX + x >= boardWidth || (j + moveY + y >= boardHeight || board[i + moveX + x][j + moveY + y] > 0)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void checkArrayGetPoints() {

        int mainCounter = 0;
        int indexOfPoint;
        for (int i = 0; i < board[0].length; i++) {
            int oneLineCounter = 0;
            indexOfPoint = i;
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] > 0) {
                    oneLineCounter++;
                }
            }

            if (oneLineCounter >= 15) {
                mainCounter++;
                // metoda na redukcje
                System.out.println("ilość lini na raz = " + mainCounter + " * 15");
                reduceBecauseOfPoint(indexOfPoint);
            }
        }

        //liczenie unktów + szybkość spadadnia i level
        nextLevelSpeed(mainCounter);
    }

    public void nextLevelSpeed(int mainCounter) {

        if (mainCounter > 0) {
            result += 15L * mainCounter;
        }
    }

    public void reduceBecauseOfPoint(int indexOFPoint) { /// naprawić działą!! :)

        scoreLineCounter++;
        lineScoreToLevelUp--;
        System.out.println(lineScoreToLevelUp);


        for (int i = 0; i < board.length; i++) {
            board[i][indexOFPoint] = 0;
        }
        System.out.println("zredukowałem linię " + indexOFPoint);

        /// tutaj ma byc przesuniecie o linie
//////////////////////////////////////////////////////////////////////
//        for (int j = 0; j < board.length; j++) {
//            for (int i = indexOFPoint; i > 1 ; i--) {
//                board[j][i] = board[j][i- 1];
//            }
//        }    ten działający fragment można zapisać jako [ patrz niżej]
/////////////////////////////////////////////////////////////////////////
        for (int[] ints : board) {
            if (indexOFPoint - 1 >= 0) System.arraycopy(ints, 1, ints, 2, indexOFPoint - 1);
        }

        if (lineScoreToLevelUp == 0) {
            System.out.println("level UP!");
            level++;
            dropSpeed -= tickSpeed;
            System.out.println("szybkość tiku = " + dropSpeed + " ms");
            lineScoreToLevelUp = 3;
        }

    }

    public void drawNextFigure(Graphics2D g2d) {


        for (int i = 0; i < nextFigure.tab.length; i++) {
            for (int j = 0; j < nextFigure.tab[0].length; j++) {
                if (nextFigure.tab[i][j] > 0) {
                    g2d.setColor(colors[nextFigure.color]);
                    g2d.fillRect(530 + (i * cellSize), 100 + (j * cellSize), cellSize, cellSize);
                }

            }
        }
    }


    /// aktualizacja ruchow

    public void updateMove() {

        if (right && canMove(1, 0)) {
            moveX++;
            right = false;
        }

        if (left && canMove(-1, 0)) {
            moveX--;
            left = false;
        }

        if (turn && canMove(0, 1) && turn && canMove(-1, 1)) {
            figure.rotate();
            turn = false;
        }

        if (down && canMove(0, 1)) {
            moveY++;
            down = false;
        }

        if (hardDrop && canMove(0, 1)) {
            moveY++;
        }

    }

    public void gameOver() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][1] > 0) {
                    gameOver = true;
                }
            }
        }
    }

    public void saveScore(JPanel panel) throws IOException {

        if (result > 0 && save) {

            nickname = JOptionPane.showInputDialog(panel, "Game Over", 0);
            System.out.println("jOptionPane");

            writer.close();
            save = false;
        }


        if (load) {
            loadScores();
            load = false;
        }
    }

    public void loadScores() throws FileNotFoundException {

        scanner = new Scanner(new File("Save.txt"));


        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] split = line.split(" ");

            int liczba = Integer.parseInt(split[1]);
            System.out.println(liczba + " liczba" + " nazwa " + split[0]);


            for (int i = 0; i < podium.length; i++) {
                int liczbaScore = Integer.parseInt(podium[i][1]);
                //System.out.println(liczbaScore + " liczbaScore");

                if (liczba > liczbaScore) {

                    if (i + 2 < podium.length) {
                        podium[i + 2][0] = podium[i + 1][0]; // jak index + 1 jest mniejszy od dl. tablicy podium
                        podium[i + 2][1] = podium[i + 1][1]; // to przesuń wiersz o jeden do góry
                        podium[i + 2][2] = podium[i + 1][2];
                    }

                    if (i + 1 < podium.length) {
                        podium[i + 1][0] = podium[i][0]; // jak index + 1 jest mniejszy od dl. tablicy podium
                        podium[i + 1][1] = podium[i][1]; // to przesuń wiersz o jeden do góry
                        podium[i + 1][2] = podium[i][2];
                    }
                    podium[i][0] = split[0];
                    podium[i][1] = split[1];
                    podium[i][2] = split[3];
                    break;   //  w razie ifa sprzerwij iterację pętli
                }
            }

        }

        scanner.close();

        System.out.println("----------------wypełniona tablica----------");

        for (int i = 0; i < podium.length; i++) {

            for (int j = 0; j < podium[0].length; j++) {
                System.out.print(podium[i][j] + ' ');
            }
            System.out.println();
        }
    }

    public void drawBestScores(Graphics2D g2d) {

        Font font = new Font(Font.SERIF, Font.BOLD, 30);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);

        int xScore = 100;
        int yScore = 200;
        for (int i = 0; i < podium.length; i++) {
            g2d.drawString(i + 1 + ". ", 80, yScore);
            for (int j = 0; j < podium.length - 1; j++) {
                g2d.drawString("  " + podium[i][j] + "    ", xScore, yScore);
                xScore += 150;
            }
            xScore = 100;
            yScore += 100;
        }
    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        //System.out.println(code);

        if (code == 37) {
            this.left = true;
        }

        if (code == 39) {
            this.right = true;
        }

        if (code == 40) {
            this.down = true;
        }

        if (code == 32) {
            this.turn = true;
        }

        if (code == 17) {
            this.hardDrop = true;
        }
        if (code == 27) {
            this.pause = !pause;
            System.out.println(pause);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == 37) {
            this.left = false;
        }

        if (code == 39) {
            this.right = false;
        }

        if (code == 40) {
            this.down = false;
        }

        if (code == 32) {
            this.turn = false;
        }

        if (code == 17) {
            this.hardDrop = false;
        }
    }
}
