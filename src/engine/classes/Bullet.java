package engine.classes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet {
  
  private Circle circle;
  private double baseDmg = 5, speed = 0;
  private int type = 0; 
  private boolean markedForRemoval = false;
  private boolean friendly = false;
  
  public Bullet(double dmg, double spd, int size, int typ, double xPos, double yPos, boolean friend) {
    baseDmg = dmg;
    type = typ;
    speed = spd;
    friendly = friend;
    circle = new Circle(size);
    circle.setFill(getColFromType());
    circle.setCenterX(xPos);
    circle.setCenterY(yPos);
  }
  
  public Color getColFromType() {
    if (type == 0)
      return Color.CYAN;
    if (type == 1)
      return Color.RED;
    return Color.GREEN;
  }
  
  public boolean getFriendly() {
    return friendly;
  }
  
  public double getDamage() {
    return baseDmg;
  }
  
  public Circle circle() {
    return circle;
  }
  
  public double getSpeed() {
    return speed;
  }
  
  public void mark() {
    markedForRemoval = true;
  }
  
  public boolean marked() {
    return markedForRemoval;
  }
}
