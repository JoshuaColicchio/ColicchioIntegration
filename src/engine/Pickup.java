package engine;

import engine.utils.PickupManager.pickupType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pickup {
  
  private Circle pickupIcon;
  private pickupType type;
  private boolean marked = false;
  
  public Pickup(double x, double y, pickupType typ) {
    type = typ;
    pickupIcon = new Circle(50);
    pickupIcon.setFill(getColorFromType());
    pickupIcon.setCenterX(x);
    pickupIcon.setCenterY(y);
  }
  
  public Circle getIcon() {
    return pickupIcon;
  }
  
  public pickupType getType() {
    return type;
  }
  
  public void mark() {
    marked = true;
  }
  
  public boolean marked() {
    return marked;
  }

  public Color getColorFromType() {
    switch (type) {
      case HEALTH:
        return Color.RED;
      case DMG:
        return Color.DARKORANGE;
      case FIRERATE:
        return Color.CORNFLOWERBLUE;
      default:
        return Color.BLACK;
    }
  }
}
