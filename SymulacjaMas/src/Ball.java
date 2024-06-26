import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class Ball {


    int ballnumber = 0;
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    Color[] colors = new Color[10];

    public double getDistanceToNearest() {
        return distanceToNearest;
    }

    public void setDistanceToNearest(double distanceToNearest) {
        this.distanceToNearest = distanceToNearest;
    }

    double distanceToNearest;

    public int x;
    public int y;
    boolean hitObstacle;

    public boolean[] getHitSquare() {
        return hitSquare;
    }

    public void setHitSquare(boolean[] hitSquare) {
        this.hitSquare = hitSquare;
    }

    public void manyHitSquare(int i){
        this.hitSquare = new boolean[i];
        Arrays.fill(hitSquare, false);
    }

    boolean[] hitSquare = new boolean[9];

    public boolean[] getReflect() {
        return reflect;
    }

    public void setReflect(boolean[] reflect) {
        this.reflect = reflect;
    }

    boolean[] reflect;

    public void setxV(double v) {
        this.vX = v;
    }

    public double getvX() {
        return vX;
    }

    double vX;

    public double getvY() {
        return vY;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    double vY;
    double myV;

    public double getMyV() {
        return myV;
    }

    public void setMyV(double myV) {
        this.myV = myV;
    }

    double kierunek;

    int originalX;
    public int originalY;
    double originalV;

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    int mass;
    Color color;
    Panel panel;

    public int getIndexLastHit() {
        return indexLastHit;
    }

    public void setIndexLastHit(int indexLastHit) {
        this.indexLastHit = indexLastHit;
    }

    private int indexLastHit = 999;


    public double getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    int radius;
    int distanseX;

    public double getDistanseX() {
        return distanseX;
    }

    public void setDistanseX(int distanseX) {
        this.distanseX = distanseX;
    }

    public int getDistanseY() {
        return distanseY;
    }

    public void setDistanseY(int distanseY) {
        this.distanseY = distanseY;
    }

    public double getDISTANSE() {
        return DISTANSE;
    }

    public void setDISTANSE(double DISTANSE) {
        this.DISTANSE = DISTANSE;
    }

    int distanseY;
    double DISTANSE;

    double closeX;
    double closeY;


    Ball(int x, int y, int mass,double vX,double kierunek,Color color, Panel panel){
        this.x = x;
        this.y = y;
        this.originalX = x;
        this.originalY = y;
        this.originalV = vX;
        this.kierunek = kierunek;
        this.mass = mass;
        this.vX = vX;
        this.color = color;
        this.panel = panel;
        hitObstacle = true;

        colors[0] = Color.RED;
        colors[1] = Color.GREEN;
        colors[2] = Color.BLUE;
        colors[3] = Color.YELLOW;
        colors[4] = Color.ORANGE;
        colors[5] = Color.BLACK;
        colors[6] = Color.CYAN;
        colors[7] = Color.GRAY;
        colors[8] = Color.white;
        colors[9] = Color.LIGHT_GRAY;
    }

    public void paintBall(Graphics2D g2d){

        g2d.setColor(Color.black);
        if (panel.getBackground() == Color.BLACK){
            g2d.setColor(Color.WHITE);
        }
        g2d.setStroke(new BasicStroke(4));
        g2d.drawOval(x,y,mass,mass);
        g2d.setColor(color);
        g2d.fillOval(x,y,mass,mass);
        if (panel.startAnimation) {
            x += vX;
            y += vY;
        }
        Font font = new Font("Arial",Font.BOLD,20);
        g2d.setFont(font);
        g2d.setColor(Color.black);
        double vx = vX;
        double vy = vY;
        if (vX < 0){
            vx = vX * -1;
        }
        if (vy < 0){
            vy = vy * -1;
        }

        int yOfString = y - 10;
        int yOfString2 = y - 30;
        if (y < 30){
            yOfString = y + mass + 20;
        }
        if (y < 30){
            yOfString2 = y + mass;
        }

        if (panel.velocityTooltipVisible) {
            if (panel.getBackground() == Color.BLACK){
                g2d.setColor(Color.WHITE);
            }
            g2d.drawString("Vx = " + roundDigit(vx, 2), x + (mass / 2), yOfString);
            g2d.drawString("Vy = " + roundDigit(vy, 2), x + (mass / 2), yOfString2);
        }

        if (panel.numberOfBall.isSelected()){
            g2d.drawString(String.valueOf(ballnumber), x + (mass / 2), y + (mass/2));
        }
        if (panel.raidusTooltip.isSelected()){
            if (mass < 20){
                g2d.drawString("r " + String.valueOf(getRadius()), x -10, y + (mass / 2));
            }else  {
                g2d.drawString("r " + String.valueOf(getRadius()), x + 10, y + (mass / 2));
            }
        }




    }

    private double roundDigit(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }


    Ball(int x, int y, int mass,double vX, double vY,double kierunek,Color color, Panel panel,boolean[] reflect, int ballnumber){
        this.x = x;
        this.y = y;
        this.originalX = x;
        this.originalV = vX;
        this.kierunek = kierunek;
        this.mass = mass;
        this.vX = vX;
        this.vY = vY;
        this.color = color;
        this.panel = panel;
        this.reflect = reflect;
        this.ballnumber = ballnumber;
        hitObstacle = true;


    }

    public void setFalseToReflect(){

        Arrays.fill(reflect, false);

    }


}
