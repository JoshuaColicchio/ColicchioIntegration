package engine;

import engine.utils.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Engine extends Application {

  public final int DEF_WIDTH = 600, DEF_HEIGHT = 800;
  private static GameLogic logicLoop;

  private static Pane root = new Pane();
  private static Vector2 mousePos = new Vector2(0, 0);

  public static void main(String[] args) {
    launch(args);
  }

  public static Vector2 getMouse() {
    return mousePos;
  }

  public static Pane getRoot() {
    return root;
  }

  public static Scene getScene() {
    return root.getScene();
  }

  public static GameLogic getGameLoop() {
    return logicLoop;
  }

  @Override
  public void start(Stage s) {
    s.setTitle("Colicchio Integration");

    Scene scene = new Scene(new BorderPane(root), DEF_WIDTH, DEF_HEIGHT);

    Rectangle bounds = new Rectangle();
    bounds.widthProperty().bind(scene.widthProperty());
    bounds.heightProperty().bind(scene.heightProperty());

    root.setClip(bounds);

    logicLoop = new GameLogic();
    initMouseListener();

    AnimationTimer timer = new AnimationTimer() {

      @Override
      public void handle(long now) {
        logicLoop.update(now);
      }
    };
    timer.start();

    s.setScene(scene);
    s.show();
  }

  public void initMouseListener() {
    root.setOnMouseMoved(e -> {
      mousePos = new Vector2(e.getSceneX(), e.getSceneY());
    });

    root.setOnMouseDragged(e -> {
      mousePos = new Vector2(e.getSceneX(), e.getSceneY());
      logicLoop.playerFiring(true);
    });

    root.setOnMouseDragReleased(e -> {
      logicLoop.playerFiring(false);
    });

    root.setOnMousePressed(e -> {
      logicLoop.playerFiring(true);
    });

    root.setOnMouseReleased(e -> {
      logicLoop.playerFiring(false);
    });
  }
}
