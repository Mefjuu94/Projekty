package Shapes;

public class LShape extends Shape {
    public LShape() {
        super();
        this.tab = new int[][]{
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 1, 0}};
    }
}