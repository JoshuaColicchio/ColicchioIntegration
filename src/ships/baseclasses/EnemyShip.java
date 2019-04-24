package ships.baseclasses;

import engine.Engine;
import engine.classes.Bullet;
import javafx.scene.image.ImageView;

// Joshua Colicchio

/*
 * This class serves as the base for all enemy ships spawned in game.
 * It contains the methods and fields that are shared between all enemies
 * 
 * Java Inheritance is when a class is built off of another parent class
 * Such as Enemy1.java inherits the functionality of EnemyShip.java, which
 * means that Enemy1 is a subclass of EnemyShip. The benefit of inheritance
 * is that you don't have to rewrite the same method in a number of different
 * classes. You can place all shared fields and methods in a parent class, and
 * all classes that need those methods can inherit from the parent.
 */

public class EnemyShip extends GameShip {
  
  private int weaponCount = 0;
  private int weaponType = 0;
  private boolean markedForRemoval = false;
  private long damageTimer = -1;

  public EnemyShip(String img, double hlth) {
    super(img, hlth);
  }
  
  public EnemyShip(String img, double hlth, int weapons, int weaponType) {
    super(img, hlth);
    weaponCount = weapons;
    this.weaponType = weaponType;
  }

  public boolean markForRemoval() {
    return markedForRemoval;
  }

  public ImageView getImageView() {
    return super.iv;
  }
  
  public void onSpawn() {
    if (super.iv.getX() <= super.iv.getFitWidth() / 2)
      super.iv.setX(super.iv.getFitWidth() / 2);
    else if (super.iv.getX() >= Engine.getScene().getWidth() - (super.iv.getFitWidth() / 2))
      super.iv.setX(Engine.getScene().getWidth() - (super.iv.getFitWidth() / 2));
  }
  
  // Inherited methods
  @Override
  public void fire() {
    for (int i = 0; i < weaponCount; i++) {
      float spacer = (float)1 / (2*weaponCount) + (float)i/weaponCount;
      Bullet bullet = new Bullet(5, 10, 5, weaponType,
          super.iv.getX() + spacer * super.iv.getFitWidth(),
          super.iv.getY() + 3 * super.iv.getFitHeight() / 4, false);
      Engine.getGameLoop().registerBullet(bullet);
    }
  }

  @Override
  public void onTakeDamage(double damage) {
    health -= damage;
    if (health <= 0)
      onDestroyed();
    else
      damageTimer = System.nanoTime();
  }

  @Override
  public void onDestroyed() {
    markedForRemoval = true;
    Engine.getGameLoop().spawnPickup(iv.getX(), iv.getY());
  }
  
  @Override
  public void update(long now) {
    super.iv.setY(super.iv.getY() + 5);
    if (super.iv.getY() > Engine.getScene().getHeight()) 
      markedForRemoval = true;
    
    if (damageTimer + 1000000000 > now)
      super.iv.setOpacity((super.iv.getOpacity() == 0.5) ? 1 : 0.5);
    else if (damageTimer + 1000000000 < now)
      super.iv.setOpacity(1);
  }
}
