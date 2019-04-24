package engine.utils;

import java.lang.reflect.Method;

// Joshua Colicchio
// This class is a 'custom' data type created for simplification of code in GameLogic

public class Vector2 {

  private double x;
  private double y;

  private Method xMeth;
  private Method yMeth;

  public double X;
  public double Y;

  public Vector2(double x, double y) {
    this.x = x;
    this.y = y;

    try { // Allows calling getX and getY with .X and .Y rather than getX()...
      xMeth = this.getClass().getMethod("getX");
      yMeth = this.getClass().getMethod("getY");

      X = (double) xMeth.invoke(this);
      Y = (double) yMeth.invoke(this);
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  public void update(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

}
