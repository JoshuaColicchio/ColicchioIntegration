package ships.player;

import engine.classes.Bullet;
import engine.Engine;
import engine.Engine.GameState;
import engine.classes.Pickup;
import javafx.scene.image.ImageView;
import ships.baseclasses.GameShip;

/**
 * This is the base class for the player ship.
 * 
 * @author - Joshua Colicchio
 * @version - 1.0
 */

public class Player extends GameShip {

  private static long rateOfFire = 30;
  private static long baseROF = 30;
  private long damageTimer = -1;
  private double dmgBuff = 0;
  private long lastBulletShot = -1;
  private ImageView ship;

  /**
   * Constructor for the player ship using the default ship image.
   */
  public Player() {
    super("ships/res/player.png", Engine.getDifficulty(), rateOfFire);
    ship = super.iv;
    ship.setFitHeight(80);
    ship.setFitWidth(80);
    Engine.getRoot().getChildren().add(ship);
    ship.setY(Engine.getScene().getHeight() - ship.getFitHeight());
  }

  /**
   * Constructor for the player ship using an alternate ship image.
   * 
   * @param customImg - File path of the alternate ship image to use.
   */
  public Player(String customImg) {
    super(customImg, rateOfFire, 100);
    ship = iv;
    ship.setFitHeight(80);
    ship.setFitWidth(80);
    Engine.getRoot().getChildren().add(ship);
    ship.setY(Engine.getScene().getHeight() - ship.getFitHeight());
  }

  /**
   * Returns the ImageView of the player ship.
   * 
   * @return javafx.scene.image.ImageView
   */
  public ImageView getShip() {
    return ship;
  }

  /**
   * Sets a new ImageView for the player ship.
   * 
   * @param newShip - New ImageView.
   */
  public void setShip(ImageView newShip) {
    ship = newShip;
  }

  /**
   * Sets the health of the player ship.
   * 
   * @param hlth - New health.
   */
  public void setHealth(double hlth) {
    health = hlth;
  }

  /**
   * Returns the player ships current health.
   * 
   * @return double
   */
  public double getHealth() {
    return health;
  }

  /**
   * Method called when the player ship collides with a pickup. Applies the pickup buff to the
   * player.
   * 
   * @param pickup - Pickup object that has been collided with.
   */
  public void onPickup(Pickup pickup) {
    switch (pickup.getType()) {
      case 0:
        health = (health + 10 > Engine.getDifficulty()) ? Engine.getDifficulty() : health + 10;
        break;
      case 1:
        dmgBuff += 5;
        break;
      case 2:
        rateOfFire = 3 * rateOfFire / 4;
        break;
    }
    pickup.mark();
  }

  @Override
  public void update(long now) {
    try {
      ship.setX((Engine.getInput().getMousePos().X <= (ship.getFitWidth() / 2)) ? 0
          : (Engine.getInput().getMousePos().X >= Engine.getScene().getWidth()
              - (ship.getFitWidth() / 2)) ? Engine.getScene().getWidth() - (ship.getFitWidth())
                  : Engine.getInput().getMousePos().X - ship.getFitWidth() / 2);

      if (damageTimer + 1000000000 > now)
        super.iv.setOpacity((super.iv.getOpacity() == 0.5) ? 1 : 0.5);
      else if (damageTimer + 1000000000 < now)
        super.iv.setOpacity(1);

      if (rateOfFire > baseROF)
        rateOfFire--;

    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  @Override
  public void fire() {
    if (System.nanoTime() < lastBulletShot + (rateOfFire * (1_000_000_000 / 60)))
      return;

    lastBulletShot = System.nanoTime();

    Bullet bullet = new Bullet(5 + dmgBuff, -5, 5, 0, ship.getX() + ship.getFitWidth() / 4,
        ship.getY() + ship.getFitHeight() / 4, true);
    Engine.getGameLoop().registerBullet(bullet);
    bullet = new Bullet(5 + dmgBuff, -5, 5, 0, ship.getX() + 3 * ship.getFitWidth() / 4,
        ship.getY() + ship.getFitHeight() / 4, true);
    Engine.getGameLoop().registerBullet(bullet);
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
    Engine.setGameState(GameState.GAMEOVER);
  }

}
