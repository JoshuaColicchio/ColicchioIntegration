package inClassActivities;

// Joshua Colicchio
// In class example class

public class Car {
  private String model, make;
  private int year;
  
  // What is a constructor: a method
  // Called automatically
  // No return type
  // The name must be the class name
  public Car(String model, String make, int year) {
    this.model = model;
    this.make = make;
    this.year = year;
  }
  
  public void setModel(String newModel) {
    model = newModel;
  }
  
  public String getModel() {
    return model;
  }
  
  public void setMake(String newMake) {
    make = newMake;
  }
  
  public String getMake() {
    return make;
  }
  
  public void setYear(int newYear) {
    year = newYear;
  }
  
  public int getYear() {
    return year;
  }
}
