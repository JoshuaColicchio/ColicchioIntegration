package ships.baseclasses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is the base for all ships spawned during gameplay, including the player.
 * 
 * @author - Joshua Colicchio
 * @version - 1.0
 *
 */

public abstract class GameShip {

  protected ImageView iv;
  protected final long rateOfFire;
  protected double health;

  /**
   * Constructor for an armed GameShip.
   * 
   * @param img - File path of the GameShip image.
   * @param hlth - Health of the GameShip.
   * @param fireRate - Fire rate of the GameShip weapons.
   */
  public GameShip(String img, double hlth, long fireRate) {
    iv = new ImageView(new Image(img));
    health = hlth;
    rateOfFire = fireRate;
  }

  /**
   * Constructor for an unarmed GameShip.
   * 
   * @param img - File path of the GameShip image.
   * @param hlth - Health of the GameShip.
   */
  public GameShip(String img, double hlth) {
    iv = new ImageView(new Image(img));
    health = hlth;
    rateOfFire = 0;
  }

  /**
   * Method called when the GameShip fires its weapons.
   */
  public abstract void fire();

  /**
   * Method called when the GameShip takes damage.
   * 
   * @param damage - Amount of damage the GameShip has taken.
   */
  public abstract void onTakeDamage(double damage);

  /**
   * Method called when the GameShip is destroyed.
   */
  public abstract void onDestroyed();

  /**
   * Method called every frame to update the status of the GameShip.
   * 
   * @param now - Current time in nanoseconds.
   */
  public abstract void update(long now);

}
