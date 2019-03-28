package engine;

import java.util.Iterator;
import engine.utils.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ships.Enemy1;
import ships.Player;

public class Engine extends Application {

  public final int DEF_WIDTH = 600, DEF_HEIGHT = 800;  

  private static Pane root = new Pane();
  private static Vector2 mousePos = new Vector2(0, 0);
  
  private boolean playerFiring = false;

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

  @Override
  public void start(Stage s) {
    s.setTitle("Colicchio Integration");
    
    Scene scene = new Scene(new BorderPane(root), DEF_WIDTH, DEF_HEIGHT);

    Rectangle bounds = new Rectangle();
    bounds.widthProperty().bind(scene.widthProperty());
    bounds.heightProperty().bind(scene.heightProperty());

    root.setClip(bounds);
    
    BackgroundManager bgm = new BackgroundManager();
    BulletManager blm = new BulletManager();
    EnemyManager em = new EnemyManager();
    PickupManager pum = new PickupManager();
    
    Player player = new Player();

    root.setOnMouseMoved(e -> {
      mousePos = new Vector2(e.getSceneX(), e.getSceneY());
    });

    root.setOnMouseDragged(e -> {
      mousePos = new Vector2(e.getSceneX(), e.getSceneY());
      playerFiring = true;
    });
    
    root.setOnMouseDragReleased(e -> {
      playerFiring = false;
    });

    root.setOnMousePressed(e -> {
      playerFiring = true;
    });
    
    root.setOnMouseReleased(e -> {
      playerFiring = false;
    });

    AnimationTimer timer = new AnimationTimer() {
      
      @Override
      public void handle(long now) {
        if (playerFiring)
          player.fire();
        
        for(Bullet b : blm.active()) {
          if (b.getFriendly()) { // can only hit enemies
            for (Iterator<Enemy1> it = em.getActive().iterator(); it.hasNext();) {
              Enemy1 enemy = it.next();
              if (b.circle().getBoundsInParent().intersects(enemy.iv().getBoundsInParent())) {
                // cleanup bullet and call onDamage
                b.mark();
                enemy.onTakeDamage(b.getDamage());
              }
            }
          } else { // can only hit player
            if (player.getShip().getBoundsInParent().intersects(b.circle().getBoundsInParent())) {
              b.mark();
              player.onTakeDamage(b.getDamage());
            }
          }
        }
        
        for (Pickup p : pum.getActive()) {
          if (p.getIcon().getBoundsInParent().intersects(player.getShip().getBoundsInParent())) {
            player.onPickup(p);
          }
        }
        
        player.update(now);
        bgm.update();
      }
    };

    s.setScene(scene);
    s.show();

    timer.start();
  }
}
