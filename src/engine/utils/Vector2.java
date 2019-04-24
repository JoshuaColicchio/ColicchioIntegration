package engine.utils;

/**
 * This is a 'custom' data type used to contain two doubles in a vector format.
 * 
 * @author - Joshua Colicchio
 * @version - 1.0
 */

public class Vector2 {

  private double xValue; // CheckStyle disagrees with the Google Style.
  private double yValue; // CheckStyle disagrees with the Google Style.

  /**
   * Constructor for the Vector2 class.
   * 
   * @param x - First double to store.
   * @param y - Second double to store.
   */
  public Vector2(double x, double y) {
    xValue = x;
    yValue = y;
  }

  /**
   * Update the values of the Vector2.
   * 
   * @param x - New first value.
   * @param y - New second value.
   */
  public void update(double x, double y) {
    xValue = x;
    yValue = y;
  }

  /**
   * Returns the first value in the Vector2.
   * 
   * @return double
   */
  public double getX() {
    return xValue;
  }

  /**
   * Returns the second value in the Vector2.
   * 
   * @return double
   */
  public double getY() {
    return yValue;
  }

}
