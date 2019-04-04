package engine;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import engine.Engine.GameState;
import engine.utils.Vector2;
import javafx.scene.input.KeyCode;

// Joshua Colicchio
// This class handles user inputs (key press, mouse movements / clicks)

public class InputHandler {
  
  private Vector2 mousePos, pausedMouse;
  
  public InputHandler() {
    Engine.getRoot().setOnMouseMoved(e -> {
      mousePos = new Vector2(e.getSceneX(), e.getSceneY());
    });

    Engine.getRoot().setOnMouseDragged(e -> {
      mousePos = new Vector2(e.getSceneX(), e.getSceneY());
      Engine.getGameLoop().playerFiring(true);
    });

    Engine.getRoot().setOnMouseDragReleased(e -> {
      Engine.getGameLoop().playerFiring(false);
    });

    Engine.getRoot().setOnMousePressed(e -> {
      Engine.getGameLoop().playerFiring(true);
    });

    Engine.getRoot().setOnMouseReleased(e -> {
      Engine.getGameLoop().playerFiring(false);
    });
    
    Engine.getRoot().setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE || e.getCode() == KeyCode.SPACE) {
        if (Engine.getGameState() == GameState.PAUSED) {
          Engine.setGameState(GameState.RUNNING);
          try {
            Robot robot = new Robot();
            robot.mouseMove((int) pausedMouse.X, (int) pausedMouse.Y);
          } catch (AWTException ex) {
            System.out.println(ex);
          }
        } else if (Engine.getGameState() == GameState.RUNNING) {
          Engine.setGameState(GameState.PAUSED);
          pausedMouse = new Vector2(MouseInfo.getPointerInfo().getLocation().x,
              MouseInfo.getPointerInfo().getLocation().y);
          Engine.getGameLoop().pauseGame();
        } else if (Engine.getGameState() == GameState.GAMEOVER) {
          Engine.displayDifficultyOption();
        }
      } else if (Engine.getGameState() == GameState.GAMEOVER) {
        Engine.displayDifficultyOption();
      }
    });
  }
  
  public Vector2 getMousePos() {
    return mousePos;
  }
}
