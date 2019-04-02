package ships;

import engine.classes.Bullet;
import engine.Engine;
import engine.classes.Pickup;
import javafx.scene.image.ImageView;

public class Player extends GameShip {

  private static long rateOfFire = 30;
  private long damageTimer = -1;
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
    
    if (damageTimer + 1000000000 > now)
      super.iv.setOpacity((super.iv.getOpacity() == 0.5) ? 1 : 0.5);
    else if (damageTimer + 1000000000 < now)
      super.iv.setOpacity(1);
  }

  @Override
  public void fire() {
    if (System.nanoTime() < lastBulletShot + (rateOfFire * (1_000_000_000 / 60)))
      return;
    
    lastBulletShot = System.nanoTime();

    Bullet b = new Bullet(5 + dmgBuff, -5, 5, 0, ship.getX() + ship.getFitWidth() / 4,
        ship.getY() + ship.getFitHeight() / 4, true);
    Engine.getGameLoop().registerBullet(b);
    b = new Bullet(5, -5, 5, 0, ship.getX() + 3 * ship.getFitWidth() / 4,
        ship.getY() + ship.getFitHeight() / 4, true);
    Engine.getGameLoop().registerBullet(b);
  }

  @Override
  public void onTakeDamage(double dmg) {
    health -= dmg;
    if (health <= 0)
      onDestroyed();
    else
      damageTimer = System.nanoTime();
  }

  @Override
  public void onPickup(Pickup p) {
    switch (p.getType()) {
      case 0:
        health += 10;
        break;
      case 1:
        dmgBuff += 5;
        break;
      case 2:
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
