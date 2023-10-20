package Shapes;

public class ZShape extends Shape {
    public ZShape() {
        super();
        this.tab = new int[][]{
                {0,0,0,0 },
                {1,1,0,0 },
                {0,1,1,0 },
                {0,0,0,0 }};
    }
}