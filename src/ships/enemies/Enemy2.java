package ships.enemies;

import java.util.Random;
import engine.Engine;
import ships.baseclasses.EnemyShip;

public class Enemy2 extends EnemyShip {

  private final long swayDir;
  private long pausedTime = 0, offset = 0;
  private Random rand = new Random();

  public Enemy2(double x, double y, int healthModifier) {
    super("ships/res/Enemy2.png", 20 + 10 * healthModifier, 2, 2);
    iv.setFitHeight(80);
    iv.setFitWidth(80);
    iv.setRotate(180);
    iv.setX(x);
    iv.setY(y);
    onSpawn();
    swayDir = (rand.nextInt(2) == 0) ? -1 : 1;
  }

  public void setPauseTime(long time) {
    pausedTime = time;
  }

  // Overriding the parent update method in order to add unique movement pattern
  @Override
  public void update(long now) {
    if (pausedTime != 0) {
      offset = now - pausedTime + offset;
      pausedTime = 0;
    }
    double t = (now - offset) / 1000000000.0;
    double x = 250 + swayDir * 128 * Math.cos(t);
    super.iv.setX(x);

    if (rand.nextInt(100) == 28)
      fire();

    super.update(now);
  }
  
  @Override
  public void onDestroyed() {
    Engine.getGameLoop().onEnemyDestroyed(1000);
    super.onDestroyed();
  }
}
