package main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;
import javax.swing.*;
import utils.EventDriver;
import utils.PanelFactory;
import utils.Enums.*;

/*
 * Joshua Colicchio
 * 
 * This main file is the driver behind the program. It handles the main JFrame window and dictates
 * when the other classes are called and what's displayed at any given time
 * 
 */

/*
 * A variable in Java is like a box that you can store things in. The type of thing you can store in
 * the box depends on the type of box that you create. Realistically, a variable is a name for a
 * section of memory.
 *
 * Scope in Java is the level of access a variable has / where a variable 'exists'. For example, if
 * you declare a variable inside of a method, that variable is scoped only in that method, not
 * outside of it.
 */
public class Main implements Runnable {

  private JFrame window;
  private EventDriver eventDriver;
  private Thread thread;
  private boolean bRunning = false;

  // The "final" keyword makes it so the program cannot change the value of the variable.
  // So if I tried to set WIDTH = 5 anywhere in this program, it would throw an error
  private final int WIDTH = 720;
  private final int HEIGHT = 600;

  public Main() {
    window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocationRelativeTo(null);
    window.setSize(WIDTH, HEIGHT);
    window.setVisible(true);

    // Create event listener
    eventDriver = new EventDriver(this, Events.NONE);

    // Create and load main menu
    PanelFactory.UpdateInstance(this, eventDriver, window);
    PanelFactory.BuildAndDisplayMainMenu(window);

    thread = new Thread(this);
  }

  public void start() {
    bRunning = true;
    if (thread.getState() == Thread.State.TERMINATED) {
      thread = new Thread(this);
    }

    thread.start();
  }

  public void stop() {
    bRunning = false;
  }

  @Override
  public void run() {
    int frameCounter = 0;
    double resetCounter = 0.0;
    long timeSinceBeganRunning = 0;
    long startTime = System.currentTimeMillis();

    String testStr1 = "Howdy";
    String testStr2 = "Howdy";

    while (bRunning) {
      synchronized (thread) {
        if (!bRunning) {
          try {
            thread.wait();
          } catch (InterruptedException e) {
            System.out.println(e);
          }
        }
      }

      timeSinceBeganRunning = (System.currentTimeMillis() - startTime);

      // By casting a variable, Java attempts to convert the variable into the casted type
      if (timeSinceBeganRunning % 1000 == 0)
        window.setTitle("Colicchio Integration - Random Line Runtime: "
            + (int) (timeSinceBeganRunning / 1000) + " seconds");

      frameCounter++;

      /*
       * When working with strings, the '==' operator doesn't work the way you think it does. The
       * operator compares the locations in memory, not the content of the strings.
       * 
       * The String.equals method compares the characters of two strings The String.compareTo method
       * compares the characters of two strings 'lexographically' which means it compares each
       * character to see which one has a higher value. Returns 0 if they're identical, < 0 if the
       * calling string is lexographically less, and > 0 if the calling string is lexographically
       * greater
       */
      if (Math.floor(resetCounter) >= 10 && testStr1.equals(testStr2)) {
        // Sorry, this is going to spam the heck out of your console. Had to add it for PSI 1
        String specialOutput =
            frameCounter + " frames have occured since last output.\nHow bout that.";
        resetCounter = 0.0;
        frameCounter = 0;
        System.out.println(specialOutput);
      } else {
        resetCounter += 0.001; // Doesn't really do anything, but I couldn't think of a fitting way
                               // to shove a double, or an int, into this program to satisfy PSI 1
      }

      resetCounter = (resetCounter % 1 == 1) ? 1 : resetCounter;

      if (5 < 10 && 10 <= 10)
        render();
    }
  }

  public static void main(String[] args) {
    new Main();
  }

  public void render() {
    BufferStrategy bs = window.getBufferStrategy();
    if (bs == null) {
      window.createBufferStrategy(3);
      return;
    }

    Graphics g = bs.getDrawGraphics();
    g.clearRect(0, 0, window.getWidth(), window.getHeight());

    g.setColor(Color.RED);
    Random r = new Random();
    for (int i = 5; i > 0; i--) {

      /*
       * The 'continue' causes the for loop to end execution of the current iteration and skip to
       * the next one. So in the code below, once the for loop reaches i == 3, the if statement
       * triggers and line i == 3 doesn't draw. Though the program renders so quickly you'd have a
       * hard time noticing it.
       */
      if (i == 3)
        continue;

      g.drawLine(r.nextInt(9) * (window.getWidth() / 8) + 5,
          r.nextInt(5) * (window.getHeight() / 4) - 2, r.nextInt(9) * (window.getWidth() / 8),
          r.nextInt(5) * (window.getHeight() / 4));
    }

    /*
     * The above drawLine method call is a good example of operator precedence. Java has rules for
     * which operators are called before the others, such as in real world mathematics we use PEMDAS
     * to dictate what operations happen in what order.
     * 
     * On line 146, I have to put parentheses around 'window.getWidth() / 8', because if I didn't,
     * Java would read the call left to right, and since the * and / operators are on the same level
     * of precedence, it would multiply THEN divide which is the opposite of what I want. On the
     * same line though, I don't have to use parentheses around the '+ 5' because the + operator has
     * a lower precedence than * and /.
     */

    // This is shoved in here for PSI 2, since I don't have much of a use for it yet.
    int sleepTimer = 50;
    do {
      /*
       * Although the do while loop is meant to continue to loop until the while condition is met,
       * using the 'break' statement on line 175 stops the loop prematurely, forcing the program to
       * move on with the rest of the render method.
       */
      if (sleepTimer-- == 5)
        break;
    } while (sleepTimer >= 0);

    g.setColor(Color.BLACK);
    g.drawString("Press any key to exit", window.getWidth() / 4 * 2 - 20,
        window.getHeight() / 8 * 2);

    g.dispose();
    bs.show();
  }


  // "private static void displayMenu" is a header,
  // and "(String response)" is a parameter
  // private static void displayMenu(String response) {
  /*
   * The String.toLowerCase method converts a given string to its lower case counterpart. IE -
   * "Hello World".toLowerCase() will return "hello world"
   */
  /*
   * switch (response.toLowerCase()) { // displayData() below is an example of a method call case
   * "string": displayData(0); break; case "int": displayData(1); break; case "double":
   * displayData(2); break; case "float": displayData(3); break; case "boolean": displayData(4);
   * break; case "random": Random rand = new Random(); displayData(rand.nextInt(5)); break; case
   * "options": displayData(999); break; default: // The String.toUpperCase method converts a given
   * string into its upper case counterpart. // IE - "Hello World".toUpperCase() will return
   * "HELLO WORLD" promptUser("I have no idea what a \"" + response.toUpperCase() + "\" is...");
   * break; } }
   * 
   * private static void displayData(long input) { // By casting the int i as a long, I can assign
   * the value of i to the // long L, and use L for further operations. int switchInput = (int)
   * input; switch (switchInput) { case 0: // string
   * System.out.println("A String is a collection of chars."); break; case 1: // int
   * System.out.println("An int is a whole number."); break; case 2: // double
   * System.out.println("A double is a 64-bit number with a fractional element."); break; case 3: //
   * float System.out.println("A float is a 32-bit number with a fractional element."); break; case
   * 4: // bool System.out.println("A boolean is a true / false statement."); break; case 999: //
   * options System.out.println(
   * "Available options:\n\tString\n\tInt\n\tDouble\n\tFloat\n\tBoolean\n\tOptions"); break; }
   * promptUser(); }
   * 
   * @Override public void keyPressed(KeyEvent arg0) {
   * 
   * }
   * 
   * @Override public void keyReleased(KeyEvent arg0) {}
   * 
   * @Override public void keyTyped(KeyEvent arg0) {}
   */

}

/*
 * A list and description of Java built-in data types within the user interface or in comments Use
 * the four main types of variables (boolean, int, double, String) with appropriate names and define
 * (in English) variable and scope Use final and describe what it means in a comment
 * 
 * When using a Scanner with strings you need to use .nextLine() to move to the new line / clear the
 * buffer when going from numbers to strings
 * 
 * A CALL contains an ARGUMENT. A HEADER contains a Parameter
 * 
 * A Method is a group of statements
 */
