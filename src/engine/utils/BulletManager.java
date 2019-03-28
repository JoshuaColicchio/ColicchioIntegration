package engine.utils;

import java.util.ArrayList;
import java.util.Iterator;
import engine.Bullet;
import engine.Engine;
import javafx.animation.AnimationTimer;

public class BulletManager {

  private static ArrayList<Bullet> activeBullets = new ArrayList<>();

  public BulletManager() {
    new AnimationTimer() {

      @Override
      public void handle(long now) {
        for (Iterator<Bullet> it = activeBullets.iterator(); it.hasNext();) {
          Bullet b = it.next();
          if (b.marked()) {
            Engine.getRoot().getChildren().remove(b.circle());
            it.remove();
            continue;
          } else {
            if (b.circle().getCenterY() + b.getSpeed() < Engine.getScene().getHeight()
                && b.circle().getCenterY() + b.getSpeed() > 0) 
              b.circle().setCenterY(b.circle().getCenterY() + b.getSpeed());
            else 
              b.mark();
          }
        }
      }
    }.start();;
  }

  public static void registerBullet(Bullet b) {
    activeBullets.add(b);
    Engine.getRoot().getChildren().add(b.circle());
  }
  
  public ArrayList<Bullet> active() {
    return activeBullets;
  }
}
