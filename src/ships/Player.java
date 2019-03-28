package ships;

import engine.Bullet;
import engine.Engine;
import engine.Pickup;
import engine.utils.BulletManager;
import javafx.scene.image.ImageView;

public class Player extends GameShip {

  private static long rateOfFire = 30;
  private double dmgBuff = 0;
  private long lastBulletShot = -1;
  private ImageView ship;

  public Player() {
    super("ships/res/player.png", true, rateOfFire, 100);
    ship = super.iv;
    ship.setFitHeight(80);
    ship.setFitWidth(80);
    Engine.getRoot().getChildren().add(ship);
    ship.setY(Engine.getScene().getHeight() - ship.getFitHeight());
  }
  
  public ImageView getShip() {
    return ship;
  }

  @Override
  public void update(long now) {
    ship.setX((Engine.getMouse().X <= (ship.getFitWidth() / 2)) ? 0
        : (Engine.getMouse().X >= Engine.getScene().getWidth() - (ship.getFitWidth() / 2))
            ? Engine.getScene().getWidth() - (ship.getFitWidth())
            : Engine.getMouse().X - ship.getFitWidth() / 2);
  }

  @Override
  public void fire() {
    if (System.nanoTime() < lastBulletShot + (rateOfFire * (1_000_000_000 / 60)))
      return;
    
    lastBulletShot = System.nanoTime();

    Bullet b = new Bullet(5 + dmgBuff, -5, 5, 0, ship.getX() + ship.getFitWidth() / 4,
        ship.getY() + ship.getFitHeight() / 4, true);
    BulletManager.registerBullet(b);
    b = new Bullet(5, -5, 5, 0, ship.getX() + 3 * ship.getFitWidth() / 4,
        ship.getY() + ship.getFitHeight() / 4, true);
    BulletManager.registerBullet(b);
  }

  @Override
  public void onTakeDamage(double dmg) {
    health -= dmg;
    ship.setOpacity(100);
  }

  @Override
  public void onPickup(Pickup p) {
    switch (p.getType()) {
      case HEALTH:
        health += 10;
        break;
      case DMG:
        dmgBuff += 5;
        break;
      case FIRERATE:
        rateOfFire = rateOfFire / 2;
        break;
    }
    p.mark();
  }

  @Override
  public void onSpawn() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onDestroyed() {
    // TODO Auto-generated method stub

  }

}
