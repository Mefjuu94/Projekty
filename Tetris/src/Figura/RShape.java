package Figura;

public class RShape extends Shape{
    public RShape(){
        super();
        this.tab = new int[][]{
                {0,0,0,0},
                {0,1,1,0},
                {0,1,1,0},
                {0,0,0,0},
        };
    }
}
