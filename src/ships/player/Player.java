package ships.player;

import engine.classes.Bullet;
import engine.Engine;
import engine.classes.Pickup;
import javafx.scene.image.ImageView;
import ships.baseclasses.GameShip;

public class Player extends GameShip {

  private static long rateOfFire = 30;
  private long damageTimer = -1;
  private double dmgBuff = 0;
  private long lastBulletShot = -1;
  private ImageView ship;

  public Player() {
    super("ships/res/player.png", Engine.getDifficulty(), rateOfFire);
    ship = super.iv;
    ship.setFitHeight(80);
    ship.setFitWidth(80);
    Engine.getRoot().getChildren().add(ship);
    ship.setY(Engine.getScene().getHeight() - ship.getFitHeight());
  }
  
  public Player(String customImg) {
    super(customImg, rateOfFire, 100);
    ship = iv;
    ship.setFitHeight(80);
    ship.setFitWidth(80);
    Engine.getRoot().getChildren().add(ship);
    ship.setY(Engine.getScene().getHeight() - ship.getFitHeight());
  }
  
  public ImageView getShip() {
    return ship;
  }
  
  public void setShip(ImageView newShip) {
    ship = newShip;
  }
  
  public void setHealth(double hlth) {
    health = hlth;
  }
  
  public void onPickup(Pickup p) {
    switch (p.getType()) {
      case 0:
        health = (health + 10 > Engine.getDifficulty()) ? Engine.getDifficulty() : health + 10;
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
  public void update(long now) {
    ship.setX((Engine.getInput().getMousePos().X <= (ship.getFitWidth() / 2)) ? 0
        : (Engine.getInput().getMousePos().X >= Engine.getScene().getWidth() - (ship.getFitWidth() / 2))
            ? Engine.getScene().getWidth() - (ship.getFitWidth())
            : Engine.getInput().getMousePos().X - ship.getFitWidth() / 2);
    
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
  public void onDestroyed() {
    Engine.getGameLoop().gameOver();
    Engine.getGameLoop().cleanup();
  }

}
