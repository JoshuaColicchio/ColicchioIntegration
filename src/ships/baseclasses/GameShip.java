package ships.baseclasses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Joshua Colicchio
// This class is the base for all ships spawned during gameplay, including the player
// Enemy ships extend the EnemyShip class, but the EnemyShip class extends this class

public abstract class GameShip {
  
  protected ImageView iv;
  protected final long rateOfFire;
  protected double health;
  
  public GameShip(String img, double hlth, long fireRate) {
    iv = new ImageView(new Image(img));
    health = hlth;
    rateOfFire = fireRate;
  }
  
  public GameShip(String img, double hlth) {
    iv = new ImageView(new Image(img));
    health = hlth;
    rateOfFire = 0;
  }
  
  public abstract void fire();
  public abstract void onTakeDamage(double damage);
  public abstract void onDestroyed();
  public abstract void update(long now);
  
}
