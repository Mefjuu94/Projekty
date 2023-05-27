public class Bullet extends Hero {
    int xShoot;
    int yShoot;

    public Bullet(int x, int y) {
        this.xShoot = x + 20;
        this.yShoot = y;
    }
}