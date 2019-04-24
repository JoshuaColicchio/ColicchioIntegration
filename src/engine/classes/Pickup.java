package engine.classes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This is the base class for all pickups generated during gameplay.
 * 
 * @author - Joshua Colicchio
 * @version - 1.0
 */

public class Pickup {

  private Circle pickupIcon;
  private int type;
  private boolean marked = false;

  /**
   * Constructor for the Pickup class.
   * 
   * @param x - X position to spawn the pickup.
   * @param y - Y position to spawn the pickup.
   * @param typ - Type of the pickup. 0 - Health, 1 - Damage Buff, 2 - Rate of Fire increase.
   */
  public Pickup(double x, double y, int typ) {
    type = typ;
    pickupIcon = new Circle(10);
    pickupIcon.setFill(getColorFromType());
    pickupIcon.setCenterX(x);
    pickupIcon.setCenterY(y);
  }

  /**
   * Returns the circle representing the pickup on screen.
   * 
   * @return javafx.scene.shape.Circle
   */
  public Circle getIcon() {
    return pickupIcon;
  }

  /**
   * Returns the pickup type. 0 - Health, 1 - Damage Buff, 2 - Rate of Fire increase.
   * 
   * @return int
   */
  public int getType() {
    return type;
  }

  /**
   * Marks the pickup for removal.
   */
  public void mark() {
    marked = true;
  }

  /**
   * Returns whether or not the pickup is marked for removal.
   * 
   * @return boolean
   */
  public boolean marked() {
    return marked;
  }

  /**
   * Returns the color to draw the pickup depending on type.
   * @return javafx.scene.paint.Color
   */
  public Color getColorFromType() {
    switch (type) {
      case 0:
        return Color.RED;
      case 1:
        return Color.DARKORANGE;
      case 2:
        return Color.CORNFLOWERBLUE;
      default:
        return Color.BLACK;
    }
  }
}
