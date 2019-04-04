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

public class Engine extends Application {

  private final int DEF_WIDTH = 600, DEF_HEIGHT = 800;
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
    launch(args);
  }

  public static Pane getRoot() {
    return root;
  }

  public static Scene getScene() {
    return root.getScene();
  }

  public static GameState getGameState() {
    return gState;
  }

  public static void setGameState(GameState gs) {
    gState = gs;
  }

  public static GameLogic getGameLoop() {
    return logicLoop;
  }
  
  public static InputHandler getInput() {
    return input;
  }
  
  public static long getStartTime() {
    return startTime;
  }
  
  public static void disableText() {
    textDisplay.setOpacity(0);
  }
  
  public static void enableText(String text) {
    textDisplay.setText(text);
    textDisplay.setOpacity(1);
  }
  
  public static double getDifficulty() {
    return difficultyHealth;
  }
  
  public static void displayDifficultyOption() {
    difficulty = new TextInputDialog("1(hard) - 100(easy)");
    difficulty.setTitle("Please select a difficulty");
    difficulty.setHeaderText("Input your starting health (1-100)");
    difficulty.setContentText("Starting health: ");
    
    Optional<String> result = difficulty.showAndWait();
    if (!result.isPresent())
      displayDifficultyOption();
    else {
      result.ifPresent(e -> {
        try {
          difficultyHealth = Integer.parseInt(e);
          if (difficultyHealth < 1 || difficultyHealth > 100) {
            displayDifficultyOption();
          } else {
            startTime = System.nanoTime();
            logicLoop.startGame();
          }
        } catch (Exception ex) {
          displayDifficultyOption();
        }
      });
    }
  }
  
  public static void initScoreDisplay() {
    scoreDisplay.setFont(new Font("Comic Sans", 24));
    scoreDisplay.setTextFill(Color.WHITE);
    scoreDisplay.setLayoutX(0);
    scoreDisplay.setLayoutY(0);
    root.getChildren().add(scoreDisplay);
  }
  
  public static void updateScore(String txt) {
    scoreDisplay.setText(txt);
  }

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

  public String sumOfArray(int[] arr) {
    int total = 0;
    String arrAsString = "";
    for (int i : arr) {
      arrAsString = arrAsString + " " + i;
      total += i;
    }

    return "The sum of the items in [" + arrAsString + "] is " + total;
  }

  public String findCoordsInArray(int[][] arr, int valueToFind) {
    String arrAsString = "{ ";
    int x = -1, y = -1;
    for (int i = 0; i < arr.length; i++) {
      arrAsString += (i != 0) ? ", {" : "{";
      for (int j = 0; j < arr[i].length; j++) {
        arrAsString += (j + 1 < arr[i].length) ? arr[i][j] + ", " : arr[i][j] + "}";
        if (arr[i][j] == valueToFind) {
          x = i;
          y = j;
        }
      }
    }
    arrAsString += " }";
    return "The coordinates of " + valueToFind + " in the two-dimensional array " + arrAsString
        + " are (" + x + ", " + y + ").";
  }

  @Override
  public void start(Stage s) {    
    startTime = System.nanoTime();
    gState = GameState.LOADING;
    Random r = new Random(); // only used for PSI 3 requirement

    int[] arr1d = {r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt(), r.nextInt()};
    System.out.println(findSmallestInArray(arr1d));
    System.out.println(sumOfArray(arr1d));

    int[][] arr2d;
    arr2d = new int[][] {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}};
    System.out.println(findCoordsInArray(arr2d, 6));

    s.setTitle("Colicchio Integration");
    Scene scene = new Scene(new BorderPane(root), DEF_WIDTH, DEF_HEIGHT);
    
    input = new InputHandler();
    logicLoop = new GameLogic();
    
    textDisplay.setFont(new Font("Arial", 48));
    textDisplay.setTextFill(Color.WHITE);
    textDisplay.setText("Loading...");
    root.getChildren().add(root.getChildren().size(), textDisplay);
    textDisplay.setLayoutX(DEF_WIDTH / 2 - 200);
    textDisplay.setLayoutY(DEF_HEIGHT / 8);
    
    s.setScene(scene);
    s.show();
    displayDifficultyOption();
  }
}
