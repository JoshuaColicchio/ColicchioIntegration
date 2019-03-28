package ships;

import engine.Pickup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameShip {
  
  protected ImageView iv;
  protected final boolean hasWeapon;
  protected final long rateOfFire;
  protected double health;
  
  public GameShip(String img, boolean wep, long fireRate, double hlth) {
    iv = new ImageView(new Image(img));
    health = hlth;
    hasWeapon = wep;
    rateOfFire = fireRate;
  }
  
  public abstract void fire();
  public abstract void onTakeDamage(double damage);
  public abstract void onPickup(Pickup pickup);
  public abstract void onSpawn();
  public abstract void onDestroyed();
  public abstract void update(long now);
  
}
