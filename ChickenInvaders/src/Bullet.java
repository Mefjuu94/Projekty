

public class Bullet extends Hero {

    Bullet[] bullets;

    int xShoot;
    int yShoot;
    int turn;
    int xDegree;
    boolean[] obstacles;

    public int bulletSize = 6;

    public Bullet(int xShoot, int yShoot, int turn, int obstacleSize, int xDegree) {
        this.xShoot = xShoot;
        this.yShoot = yShoot;
        this.turn = turn;
        this.xDegree = xDegree ;


        this.obstacles = new boolean[obstacleSize];

    }

    public Bullet(int xShoot, int yShoot, int obstacleSize,int xDegree) {
        this.xShoot = xShoot;
        this.yShoot = yShoot;
        this.turn = 8;
        this.xDegree = xDegree;

        this.obstacles = new boolean[obstacleSize];

    }


}