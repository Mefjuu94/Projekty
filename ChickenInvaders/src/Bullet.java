

public class Bullet extends Hero {

    Bullet[] bullets;

    int xShoot;
    int yShoot;
    int turn;
    boolean[] obstacles;

    public int bulletSize = 6;

    public Bullet(int xShoot, int yShoot, int turn, int obstacleSize) {
        this.xShoot = xShoot;
        this.yShoot = yShoot;
        this.turn = turn;

        this.obstacles = new boolean[obstacleSize];

    }


}