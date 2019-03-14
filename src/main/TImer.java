package main;

// Joshua Colicchio
// Simple timer class used to keep track of / end the main class render method

public class TImer { // Rename me
  // Maybe use this class to keep track of time running, which makes it relevant

  private long nStartTime;
  private long nEndTime;

  public TImer() {
    nStartTime = System.currentTimeMillis();
    nEndTime = -1; // Ignored
  }

  public TImer(long nSpecifiedEndTime) {
    nStartTime = System.currentTimeMillis();
    nEndTime = nSpecifiedEndTime;
  }

  public void SetStartTime(long newStartTime) {
    nStartTime = newStartTime;
  }

  public long GetStartTime() {
    return nStartTime;
  }

  public void SetEndTime(long newEndTime) {
    nEndTime = newEndTime;
  }

  public long GetEndTime() {
    return nEndTime;
  }

  public void update(Main main) {
    if (nEndTime == -1)
      return; // Immediately stop because the update has no purpose

    if (nEndTime <= System.currentTimeMillis()) {
      // If we enter here, that means the timer has stopped, so stop the program
      System.out.println("Timer has elapsed");
      main.stop();

      return;
    } else {
      long timeSinceBeganRunning = System.currentTimeMillis() - nStartTime;
      if (timeSinceBeganRunning % 1000 == 0)
        main.getWindow().setTitle("Colicchio Integration - Random Line Runtime: "
            + (int) (timeSinceBeganRunning / 1000) + " seconds");

    }
  }
}
