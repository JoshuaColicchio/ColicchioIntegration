package utils;

import java.awt.Color;

// Joshua Colicchio
// Class that acts as a blueprint for the random lines generated by the main class render method

public class RandomLine {

  private int startX, startY, endX, endY;
  private Color drawColor;
  
  public RandomLine(int sX, int sY, int eX, int eY, Color col) {
    startX = sX;
    startY = sY;
    endX = eX;
    endY = eY;
    drawColor = col;
  }
  
  // No setters in this class because I only use this class to store temporary lines
  
  public int getStartX() {
    return startX;
  }
  
  public int getStartY() {
    return startY;
  }
  
  public int getEndX() {
    return endX;
  }
  
  public int getEndY() {
    return endY;
  }
  
  public Color getColor() {
    return drawColor;
  }
}