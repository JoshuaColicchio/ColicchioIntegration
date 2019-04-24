package engine.classes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This is the base class that all bullets are built off of.
 * 
 * @author - Joshua Colicchio
 * @version - 1.0
 */

public class Bullet {

  private Circle circle;
  private double baseDmg = 5;
  private double speed = 0;
  private int type = 0;
  private boolean markedForRemoval = false;
  private boolean friendly = false;

  /**
   * Constructor for the Bullet class.
   * 
   * @param dmg - How much damage the bullet should cause on collision.
   * @param spd - Speed the bullet should travel at.
   * @param size - Size of the bullet.
   * @param typ - Type of the bullet (0-2).
   * @param xPos - X position to spawn the bullet at.
   * @param yPos - Y position to spawn the bullet at.
   * @param friend - Whether this bullet was fired by the player or an enemy.
   */
  public Bullet(double dmg, double spd, int size, int typ, double xPos, double yPos,
      boolean friend) {
    baseDmg = dmg;
    type = typ;
    speed = spd;
    friendly = friend;
    circle = new Circle(size);
    circle.setFill(getColFromType());
    circle.setCenterX(xPos);
    circle.setCenterY(yPos);
  }

  /**
   * Returns the color of the bullet depending on its' type.
   * 
   * @return javafx.scene.paint.Color
   */
  public Color getColFromType() {
    if (type == 0)
      return Color.CYAN;
    if (type == 1)
      return Color.RED;
    return Color.GREEN;
  }

  /**
   * Returns whether or not the bullet will hurt the player.
   * 
   * @return boolean
   */
  public boolean getFriendly() {
    return friendly;
  }

  /**
   * Returns the damage to be inflicted on collision.
   * 
   * @return double
   */
  public double getDamage() {
    return baseDmg;
  }

  /**
   * Returns the circle object that represents the bullet.
   * 
   * @return javafx.scene.shape.Circle
   */
  public Circle circle() {
    return circle;
  }

  /**
   * Returns the speed of the bullet.
   * 
   * @return double
   */
  public double getSpeed() {
    return speed;
  }

  /**
   * Marks the bullet for removal. Called when the bullet collides with a ship, or travels off
   * screen.
   */
  public void mark() {
    markedForRemoval = true;
  }

  /**
   * Returns whether or not the bullet needs to be cleaned up.
   * 
   * @return boolean
   */
  public boolean marked() {
    return markedForRemoval;
  }
}
