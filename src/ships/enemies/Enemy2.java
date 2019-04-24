package ships.enemies;

import engine.Engine;
import java.util.Random;
import ships.baseclasses.EnemyShip;

// Joshua Colicchio
// This class is the base for the second variant of enemies spawned during gameplay

public class Enemy2 extends EnemyShip {

  private final long swayDir;
  private long pausedTime = 0;
  private long offset = 0;
  private Random rand = new Random();

  /**
   * Constructor for the Enemy2 class.
   * 
   * @param x - X position to spawn the Enemy2 at.
   * @param y - Y position to spawn the Enemy2 at.
   * @param healthModifier - Current health modifier (related to player score).
   */
  public Enemy2(double x, double y, int healthModifier) {
    super("ships/res/enemy2.png", 20 + 10 * healthModifier, 2, 2);
    iv.setFitHeight(80);
    iv.setFitWidth(80);
    iv.setRotate(180);
    iv.setX(x);
    iv.setY(y);
    onSpawn();
    swayDir = (rand.nextInt(2) == 0) ? -1 : 1;
  }

  // Below, public void setPauseTime is a header, and long time is the parameter
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
    double adjustedTime = (now - offset) / 1000000000.0;
    double xCoord = 250 + swayDir * 128 * Math.cos(adjustedTime);
    // CheckStyle disagrees with the Google Style above.
    super.iv.setX(xCoord);

    if (rand.nextInt(100) == 28) {
      fire();
    }
    super.update(now);
  }

  @Override
  public void onDestroyed() {
    // Below, Engine.getGameLoop().onEnemyDestroyed is a method call, and 1000 is the argument
    Engine.getGameLoop().onEnemyDestroyed(1000);
    super.onDestroyed();
  }
}
