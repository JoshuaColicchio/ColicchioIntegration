package ships.enemies;

import engine.Engine;
import engine.classes.Bullet;
import ships.baseclasses.EnemyShip;

public class Enemy1 extends EnemyShip {

  public double[] weaponLocations;

  public Enemy1(double x, double y) {
    super("ships/res/Enemy1.png", 20, 2, 1);
    iv.setFitHeight(80);
    iv.setFitWidth(80);
    iv.setRotate(180);
    iv.setX(x);
    iv.setY(y);
    onSpawn();

    weaponLocations =
        new double[] {iv.getX() + iv.getFitWidth() / 4, iv.getX() + 3 * iv.getFitWidth() / 4};
  }

  @Override
  public void fire() {
    Bullet b = new Bullet(5, 10, 5, 1, weaponLocations[0],
        super.iv.getY() + 3 * super.iv.getFitHeight() / 4, false);
    Engine.getGameLoop().registerBullet(b);

    b = new Bullet(5, 10, 5, 1, weaponLocations[1],
        super.iv.getY() + 3 * super.iv.getFitHeight() / 4, false);
    Engine.getGameLoop().registerBullet(b);
  }
}
