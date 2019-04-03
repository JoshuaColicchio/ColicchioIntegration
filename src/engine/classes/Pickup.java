package engine.classes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pickup {
  
  private Circle pickupIcon;
  private int type;
  private boolean marked = false;
  
  public Pickup(double x, double y, int typ) {
    type = typ;
    pickupIcon = new Circle(10);
    pickupIcon.setFill(getColorFromType());
    pickupIcon.setCenterX(x);
    pickupIcon.setCenterY(y);
  }
  
  public Circle getIcon() {
    return pickupIcon;
  }
  
  public int getType() {
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
