package engine;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import engine.Engine.GameState;
import engine.utils.Vector2;
import javafx.scene.input.KeyCode;

/**
 * This class listens for player input events, including mouse movements, mouse clicks, and key
 * presses.
 * 
 * @author - Joshua Colicchio
 * @version - 1.0
 */

public class InputHandler {

  private Vector2 mousePos, pausedMouse;

  public InputHandler() {
    Engine.getRoot().setOnMouseMoved(event -> {
      mousePos = new Vector2(event.getSceneX(), event.getSceneY());
    });

    Engine.getRoot().setOnMouseDragged(event -> {
      mousePos = new Vector2(event.getSceneX(), event.getSceneY());
      Engine.getGameLoop().playerFiring(true);
    });

    Engine.getRoot().setOnMouseDragReleased(event -> {
      Engine.getGameLoop().playerFiring(false);
    });

    Engine.getRoot().setOnMousePressed(event -> {
      Engine.getGameLoop().playerFiring(true);
    });

    Engine.getRoot().setOnMouseReleased(event -> {
      Engine.getGameLoop().playerFiring(false);
    });

    Engine.getRoot().setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.SPACE) {
        if (Engine.getGameState() == GameState.PAUSED) {
          Engine.setGameState(GameState.RUNNING);
          try { // Moves mouse to position prior to pausing (avoid exploiting location)
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

  /**
   * Returns the current position of the mouse.
   * 
   * @return engine.utils.Vector2
   */
  public Vector2 getMousePos() {
    return mousePos;
  }
}
