public class EnemyBullets extends Boos {

    Bullet[] bullets;

    int xShoot;
    int yShoot;

    EnemyBullets(int xShoot, int yShoot,Panel panel){
        super(panel);

        this.xShoot = xShoot;
        this.yShoot = yShoot;
    }


}
