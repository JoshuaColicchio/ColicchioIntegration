package main;

// Joshua Colicchio
// Simple timer class used to keep track of / end the main class render method

public class Timer { // Rename me
  // Maybe use this class to keep track of time running, which makes it relevant

  private long startTime;
  private long endTime;

  public Timer() {
    startTime = System.currentTimeMillis();
    endTime = -1; // Ignored
  }

  public Timer(long specifiedEndTime) {
    startTime = System.currentTimeMillis();
    endTime = specifiedEndTime;
  }

  public void setStartTime(long newStartTime) {
    startTime = newStartTime;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setEndTime(long newEndTime) {
    endTime = newEndTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void update(Main main) {
    if (endTime == -1)
      return; // Immediately stop because the update has no purpose

    if (endTime <= System.currentTimeMillis()) {
      // If we enter here, that means the timer has stopped, so stop the program
      System.out.println("Timer has elapsed");
      main.stop();

      return;
    } else {
      long timeSinceBeganRunning = System.currentTimeMillis() - startTime;
      if (timeSinceBeganRunning % 1000 == 0)
        main.getWindow().setTitle("Colicchio Integration - Random Line Runtime: "
            + (int) (timeSinceBeganRunning / 1000) + " seconds");

    }
  }
}
