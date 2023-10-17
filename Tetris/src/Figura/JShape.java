package Figura;

public class JShape extends Shape{

    public JShape(){
        super();
        this.tab = new int[][]{
                {0,0,0,0},
                {0,0,1,0},
                {0,0,1,0},
                {0,1,1,0},
        };
    }
}
