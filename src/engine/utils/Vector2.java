package engine.utils;

//Joshua Colicchio
//This class is a 'custom' data type created for simplification of code in GameLogic

public class Vector2 {
  
  public double X,Y;
  
  public Vector2(double x, double y) {
    X = x;
    Y = y;
  }
  
  public void update(double x, double y) {
    X = x;
    Y = y;
  }
}
