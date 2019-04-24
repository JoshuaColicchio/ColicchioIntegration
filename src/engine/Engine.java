package engine;

import java.util.Optional;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This program is the culmination of one semester of learning Java at Florida Gulf Coast University
 * in Introduction to Programming (COP 2006). This class is the main driver (the 'engine') of the
 * program
 * 
 * @author Joshua Colicchio
 * @version 1.0
 */

public class Engine extends Application {

  // The "final" keyword makes it so the program cannot change the value of the variable.
  // So if I tried 'DEF_WIDTH = 5' anywhere in this program, it would throw a compiler error.
  private final int DEF_WIDTH = 600;
  private final int DEF_HEIGHT = 800;
  private static GameLogic logicLoop;
  private static InputHandler input;
  private static Pane root = new Pane();
  private static long startTime;
  private static GameState gState;
  private static Label textDisplay = new Label("");
  private static Label scoreDisplay = new Label("Score: ");
  private static TextInputDialog difficulty;
  private static double difficultyHealth = 100;

  public enum GameState {
    RUNNING, PAUSED, GAMEOVER, LOADING
  };

  public static void main(String[] args) {
    psiCompliance();
    launch(args);
  }

  /**
   * Returns the root pane of the primary stage.
   * 
   * @return javafx.scene.layout.Pane
   */
  public static Pane getRoot() {
    return root;
  }

  /**
   * Returns the scene currently being shown.
   * 
   * @return javafx.scene.Scene
   */
  public static Scene getScene() {
    return root.getScene();
  }

  /**
   * Returns the current game state.
   * 
   * @return engine.Engine.GameStage
   */
  public static GameState getGameState() {
    return gState;
  }

  /**
   * Updates the current Game State.
   * 
   * @param gs State to update to.
   */
  public static void setGameState(GameState gs) {
    gState = gs;
  }

  /**
   * Returns the game logic loop.
   * 
   * @return engine.GameLogic
   */
  public static GameLogic getGameLoop() {
    return logicLoop;
  }

  /**
   * Returns the input handler.
   * 
   * @return engine.InputHandler
   */
  public static InputHandler getInput() {
    return input;
  }

  /**
   * Returns the program start time.
   * 
   * @return long
   */
  public static long getStartTime() {
    return startTime;
  }

  /**
   * Disables the text draw on the screen.
   */
  public static void disableText() {
    textDisplay.setOpacity(0);
  }

  /**
   * Updates the text, then enables the text draw.
   * 
   * @param text Text to be displayed.
   */
  public static void enableText(String text) {
    textDisplay.setText(text);
    textDisplay.setOpacity(1);
  }

  /**
   * Returns the difficulty setting, which is the maximum player health.
   * 
   * @return double
   */
  public static double getDifficulty() {
    return difficultyHealth;
  }

  /**
   * Draws the difficulty select dialog.
   */
  public static void displayDifficultyOption() {
    difficulty = new TextInputDialog("");
    difficulty
        .setTitle("Hello, and welcome to my integration project!\nPlease select a 'difficulty'");
    difficulty.setHeaderText("Hello, and welcome to my project.\nThis is a sort of Galaga clone"
        + " (although very simplified)\nThe goal is to destroy the other ships, "
        + "and to not get yourself destroyed.\nThe more enemies you defeat, the tougher they get!"
        + "\nChoose your difficulty by entering your starting "
        + "health below (whole number from 1 to 100)");
    difficulty.setContentText("Starting health: ");

    Optional<String> result = difficulty.showAndWait();
    if (!result.isPresent())
      displayDifficultyOption();
    else {
      result.ifPresent(event -> {
        try {
          difficultyHealth = Double.parseDouble(event);
          if (difficultyHealth < 1 || difficultyHealth > 100) {
            System.out.println("1");
            displayDifficultyOption();
          } else {
            System.out.println("2");
            startTime = System.nanoTime();
            logicLoop.startGame();
          }
          System.out.println("3");
        } catch (Exception ex) {
          ex.printStackTrace();
          // If user enters anything but a double, this gets called
          displayDifficultyOption();
        }
      });
    }
  }

  /**
   * Prepares and displays player Score and Health.
   */
  public static void initScoreDisplay() {
    scoreDisplay.setFont(new Font("Comic Sans", 24)); // who doesn't love comic sans
    scoreDisplay.setTextFill(Color.WHITE);
    scoreDisplay.setLayoutX(0);
    scoreDisplay.setLayoutY(0);
    root.getChildren().add(scoreDisplay);
  }

  /**
   * Updates player score display.
   * 
   * @param txt New Score to display.
   */
  public static void updateScore(String txt) {
    scoreDisplay.setText(txt);
  }

  /**
   * Returns a string containing the value of and index of the smallest value in the provided array.
   * 
   * @param arr The array to search through.
   * @return String
   */
  public String findSmallestInArray(int[] arr) {
    int indexOfSmallest = 0;
    String arrAsString = "" + arr[0];
    for (int i = 1; i < arr.length; i++) {
      arrAsString = arrAsString + " " + arr[i];
      if (arr[i] < arr[indexOfSmallest])
        indexOfSmallest = i;
    }

    return "The smallest value in [" + arrAsString + "] is " + arr[indexOfSmallest] + " | Index: "
        + indexOfSmallest;
  }

  /**
   * Returns a string containing the summation of the provided array.
   * 
   * @param arr The array to search through.
   * @return String
   */
  public String sumOfArray(int[] arr) {
    int total = 0;
    String arrAsString = "";
    for (int i : arr) {
      arrAsString = arrAsString + " " + i;
      total += i;
    }

    return "The sum of the items in [" + arrAsString + "] is " + total;
  }

  /**
   * Returns a string containing the x and y coordinates in the provided two dimensional array.
   * 
   * @param arr The array to search through.
   * @param valueToFind - The value that you want the coordinates of.
   * @return String
   */
  public String findCoordsInArray(int[][] arr, int valueToFind) {
    String arrAsString = "{ ";
    int xCoord = -1;
    int yCoord = -1;
    for (int i = 0; i < arr.length; i++) {
      arrAsString += (i != 0) ? ", {" : "{";
      for (int j = 0; j < arr[i].length; j++) {
        arrAsString += (j + 1 < arr[i].length) ? arr[i][j] + ", " : arr[i][j] + "}";
        if (arr[i][j] == valueToFind) {
          xCoord = i;
          yCoord = j;
        }
      }
    }
    arrAsString += " }";
    return "The coordinates of " + valueToFind + " in the two-dimensional array " + arrAsString
        + " are (" + xCoord + ", " + yCoord + ").";
  }

  /**
   * This is the start method that sets the initial game state and the initial values of the game
   * variables. This also creates the window, the scene, starts the input handler, and starts the
   * logic loop.
   */
  @Override
  public void start(Stage primStage) {
    startTime = System.nanoTime();
    gState = GameState.LOADING;
    Random rando = new Random(); // only used for PSI 3 requirement

    int[] arr1d = {rando.nextInt(), rando.nextInt(), rando.nextInt(), rando.nextInt(),
        rando.nextInt(), rando.nextInt()};
    System.out.println(findSmallestInArray(arr1d));
    System.out.println(sumOfArray(arr1d));

    int[][] arr2d;
    arr2d = new int[][] {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}};
    System.out.println(findCoordsInArray(arr2d, 6));

    primStage.setTitle("Colicchio Integration");
    Scene scene = new Scene(new BorderPane(root), DEF_WIDTH, DEF_HEIGHT);

    input = new InputHandler();
    logicLoop = new GameLogic();

    textDisplay.setFont(new Font("Arial", 48));
    textDisplay.setTextFill(Color.WHITE);
    textDisplay.setText("Loading...");
    root.getChildren().add(root.getChildren().size(), textDisplay);
    textDisplay.setLayoutX(DEF_WIDTH / 2 - 200);
    textDisplay.setLayoutY(DEF_HEIGHT / 8);

    primStage.setScene(scene);
    primStage.show();
    displayDifficultyOption();
    initScoreDisplay();
  }

  /**
   * This method demonstrates certain skills that are required for COP 2006, but were unnecessary
   * for the game itself.
   */
  public static void psiCompliance() {
    String s1 = "Hello";
    String s2 = "Hello";
    boolean b1 = (s1 == s2);
    boolean b2 = (s1.equals(s2));
    int i1 = s1.compareTo(s2);
    System.out.println("Comparing \"" + s1 + "\" and \"" + s2 + "\"");
    System.out.println("Using == " + b1 + "\nUsing .equals " + b2 + "\nUsing compareTo " + i1);

    System.out.println("The value of 5 % 3 is 2, proof: " + (5 % 3));

    long startTime = System.nanoTime();
    do {
      System.out.println("Holding up program execution :)");
    } while (startTime + 5000 > System.nanoTime());
  }
}

/*
 * A variable in Java is like a box that you can store things in. The type of thing you can store in
 * the box depends on the type of box that you create. Realistically, a variable is a name for a
 * section of memory.
 *
 * Scope in Java is the level of access a variable has / where a variable 'exists'. For example, if
 * you declare a variable inside of a method, that variable is scoped only in that method, not
 * outside of it.
 */

/*
 * List of all default data types in java
 * 
 * String - A collection of characters Character (char) - A single character (Ex: a, A, b, B...)
 * Integer (int) - Any whole number Floating Point Number (float) - A decimal number with 32 bit
 * precision Double Precision Floating Point (double) - A decimal number with 64 bit precision
 * Boolean (boolean) - A true or false variable
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

/*
 * Operator precedence is a lot like PEMDAS in math. It dictates which operations are done first
 * when executing code For example, in the code "5 + 2 / 2 * 3" the program will first divide 2 by
 * 2, because multiplication and division have the same precedence, then it will multiply the
 * result, 1, by three, then add five.
 */
