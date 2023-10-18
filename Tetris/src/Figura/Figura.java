package Figura;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Figura implements KeyListener {

    final int boardWidth = 15;
    final int boardHeight = 20;

    Shape figure = Shape.CreateShape();
    int[][] board = new int[boardWidth][boardHeight];

    boolean left, right, down, turn, hardDrop;
    //czas / tick do zjezxdzania w dol
    long trzystaMs = System.currentTimeMillis();

    int cellSize = 30;

    int moveX = 6;
    int moveY = 0;
    int yShadow;

    static Color[] colors = {Color.BLACK, Color.ORANGE, Color.BLUE, Color.magenta, Color.PINK, Color.GREEN, Color.RED}; // index 0 jest omijany
    //żeby wyświetlił sie kolor na dole tablicy - gdyby został index 0 to by zytało jako puste pole

    public Figura() {

        {
            for (int i = 0; i < figure.tab.length; i++) {
                for (int j = 0; j < figure.tab[0].length; j++) {
                    figure.tab[i][j] = 0;
                }
            }
        }
        figure = Shape.CreateShape();
        //nextTab = Shape.CreateShape().tab;

    }

    /// Rysowanie-----------------------

    public void rysujFigure(Graphics2D g2d) throws InterruptedException {

        drawMainFigure(g2d);
        drawBoard(g2d);
        //figura głowna

        goDown(g2d);

        updateMove();
    }


    // zrobic  porzadek z rysowaniem figury oraz aktualizacja ruchow (nie mogha byc sprzężone
    public void drawMainFigure(Graphics2D g2d) {
        for (int i = 0; i < figure.tab.length; i++) {
            for (int j = 0; j < figure.tab[0].length; j++) {
                if (figure.tab[i][j] > 0) {
                    g2d.setColor(colors[figure.color]);
                    //ładuj kolor figury
                    //rysuj figure która jest:  index + ilosc przeunięć[movex] * wielkosc kratki[cellsize] + przesuniecie tablicy
                    // (0+6) * 30 + 50 = poczatkowa wspolrzedna na X 230
                    g2d.fillRect((i + moveX) * cellSize + 50, (j + moveY) * cellSize + 150, cellSize, cellSize);
                }
            }
        }

    }

    public void drawBoard(Graphics2D g2d) { // rysuje figury ktore spadły
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] > 0) {
                    g2d.setColor(colors[board[i][j]]);
                    g2d.fillRect(i * cellSize + 50, j * cellSize + 150, cellSize, cellSize); // tu metoda na redukcje oraz punkty
                }
            }
        }
    }


    public void goDown(Graphics2D g2d) {

        long curentms = System.currentTimeMillis();


        //sprawdzenhie granic

        if (!checkBoundariesX()){
            repairPosition(15,g2d);
        }

        if (curentms - trzystaMs > 300) {
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
                //stworzyc niowa figure
                figure = Shape.CreateShape();
            }
            trzystaMs = System.currentTimeMillis();
            //System.out.println(moveY);
        }


    }

    // sprawdzenie granic-------------- --> zrobic DLA KOLIZI FIGUR PRZY OBRUCENIU!!!!
    public boolean checkBoundariesX() {


        // sprawdzenie prawej storiny dZiała
        for (int j = 0; j < figure.tab[0].length; j++) {

            if (boardWidth < moveX + figure.tab.length) {

                if (figure.tab[figure.tab.length - 1][j] > 0) {
                    System.out.println(figure.tab[figure.tab.length - 1][j]);
                    System.out.println("za dużo!! bo " + boardWidth + " < " + moveX + "+" + figure.tab.length);
                    return false;
                }
            }
        }


        for (int j = 0; j < figure.tab[0].length; j++) {
            if (moveX < 0 ) {
                if (figure.tab[0][j] > 0) {
                    System.out.println("za mało, poza ekranem gry!");
                    return false;
                }
            }
        }

        return true;
    }

    public void repairPosition(int i, Graphics2D g2d) {
        System.out.println("naprawiam pozycje!");
        System.out.println("move przed" + moveX);
        if (i < i + moveX) {
            moveX -= 1;
            System.out.println("move po" + moveX);
        }

        if (moveX < 0 ) {
            moveX+=1;
            System.out.println(moveX + " move po");
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

        if (turn) {
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
            System.out.println("obróc!");
        }

        if (code == 17) {
            this.hardDrop = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();
        System.out.println(code);

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
