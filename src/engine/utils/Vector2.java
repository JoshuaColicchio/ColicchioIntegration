package engine.utils;

import java.lang.reflect.Method;

/**
 * This is a 'custom' data type used to contain two doubles in a vector format.
 * 
 * @author - Joshua Colicchio
 * @version - 1.0
 */

public class Vector2 {

  private double x;
  private double y;

  private Method xMeth;
  private Method yMeth;

  public double X;
  public double Y;

  /**
   * Constructor for the Vector2 class.
   * 
   * @param x - First double to store.
   * @param y - Second double to store.
   */
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

  /**
   * Update the values of the Vector2.
   * 
   * @param x - New first value.
   * @param y - New second value.
   */
  public void update(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the first value in the Vector2.
   * 
   * @return double
   */
  public double getX() {
    return x;
  }

  /**
   * Returns the second value in the Vector2.
   * 
   * @return double
   */
  public double getY() {
    return y;
  }

}
