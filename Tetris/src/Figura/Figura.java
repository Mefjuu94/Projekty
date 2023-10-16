package Figura;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Figura implements KeyListener {

    final int boardWidth = 15;
    final int boardHeight = 20;

    Shape figure = Shape.CreateShape();
    int[][] board = new int[boardWidth][boardHeight];

    boolean left,right,down,turn;
    //czas / tick do zjezxdzania w dol oraz rekacji na klawisze
    long trzystaMs =  System.currentTimeMillis();

    int cellSize = 30;

    int moveX = 6;
    int moveY = 0;
    static Color[] colors = {Color.ORANGE,Color.BLUE,Color.magenta,Color.PINK};

    public Figura(){

        {
            for (int i = 0; i < figure.tab.length; i++) {
                for (int j = 0; j < figure.tab[0].length ; j++) {
                    figure.tab[i][j] = 0;
                }
            }
        }
        figure = Shape.CreateShape();
    }


    /// Rysowanie-----------------------

    public void rysujFigure(Graphics2D g2d) throws InterruptedException {

        drawMainFigure(g2d);
        drawBoard(g2d);
        //figura głowna

        goDown();

        updateMove();
    }


    // zrobic  porzadek z rysowaniem figury oraz aktualizacja ruchow (nie mogha byc sprzężone
    public void drawMainFigure(Graphics2D g2d){
        for (int i = 0; i < figure.tab.length; i++) {
            for (int j = 0; j < figure.tab[0].length; j++) {
                if (figure.tab[i][j] > 0) {
                    g2d.setColor(colors[figure.color]);
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
                    g2d.fillRect(i * cellSize + 50, j * cellSize + 150, cellSize, cellSize);
                }
            }
        }
    }


        public void goDown(){

        long curentms = System.currentTimeMillis();

        if (curentms - trzystaMs > 300) {
            if (canMove(0,1)) {
                moveY++;
            }else {
                for (int i = 0; i < figure.tab.length; i++) {
                    for (int j = 0; j < figure.tab[0].length; j++) {
                        if (figure.tab[i][j] > 0) {
                            board[i + moveX][j + moveY] = figure.color;
                        }
                    }
                }
                moveY = 0;
                //stworzyc niowa figure
                figure = Shape.CreateShape();
            }
            trzystaMs = System.currentTimeMillis();
            System.out.println(moveY);
        }
    }

    public boolean canMove(int x, int y){ // sprawia, ze "widziw figury pod soba


        for (int i = 0; i < figure.tab.length; i++) {
            for (int j = 0; j < figure.tab[0].length; j++) {
                if (figure.tab[i][j] > 0) {
                    if( i + moveX + x < 0 || i + moveX + x >= boardWidth ||  (j + moveY + y >= boardHeight || board[i + moveX + x][j + moveY + y] > 0)  ){
                        return false;
                    }
                }
            }
        }

        return true;
    }



    /// aktualizacja ruchow

    public void updateMove() {
        if (right && canMove(1,0)) {
            moveX++;
            right = false;
        }

        if (left && canMove(-1,0)) {
            moveX --;
            left = false;
        }

        if (turn){
            figure.rotate();
            turn = false;
        }


    }

    // sprawdzenie granic--------------
    public void checkBoundariesOnTurn(){
//        if (wspolrzedneX[0] > 500 - (cellSize * 1 )){
//            System.out.println(wspolrzedneX[0] + (cellSize * 1 ));
//            for (int i = 0; i < 4; i++) {
//                wspolrzedneX[i] = wspolrzedneX[i] - cellSize;
//            }
//        }
//
//        if (wspolrzedneX[0] < 50){
//            for (int i = 0; i < 4; i++) {
//                wspolrzedneX[i] = wspolrzedneX[i] + cellSize;
//            }
//        }

    }


    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();
        //System.out.println(code);

        if (code == 37){
            this.left = true;
        }

        if (code == 39){
            this.right = true;
        }

        if (code == 40){
            this.down = true;
        }

        if (code == 32){
            this.turn = true;
            System.out.println("obróc!");
        }
        if (code == 38){
            System.out.println(code);

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == 37){
            this.left = false;
        }

        if (code == 39){
            this.right = false;
        }

        if (code == 40){
            this.down = false;
        }

        if (code == 32){
            this.turn = false;
        }
    }
}
