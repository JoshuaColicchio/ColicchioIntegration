package engine.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import engine.Engine;
import engine.Pickup;
import javafx.animation.AnimationTimer;

public class PickupManager {
  private final int maxPickups = 2;
  private final long spawnRate = (long) (1000000000) * 30;
  private long lastSpawn = -1;
  private ArrayList<Pickup> active = new ArrayList<>();

  public static enum pickupType {
    HEALTH, DMG, FIRERATE
  }

  public PickupManager() {
    new AnimationTimer() {

      @Override
      public void handle(long now) {
        if (active.size() < maxPickups && now > lastSpawn + spawnRate) {
          lastSpawn = now;
          Random r = new Random();
          Pickup pickup = new Pickup(r.nextInt((int) Engine.getScene().getWidth()) - 50,
              -(r.nextInt(50) + 50), pickupType.DMG);
          active.add(pickup);
          Engine.getRoot().getChildren().add(pickup.getIcon());
        }

        for (Iterator<Pickup> it = active.iterator(); it.hasNext();) {
          Pickup p = it.next();
          if (p.marked()) {
            Engine.getRoot().getChildren().remove(p.getIcon());
            it.remove();
            continue;
          }

          p.getIcon().setCenterY(p.getIcon().getCenterY() + 3);
          if (p.getIcon().getCenterY() > Engine.getScene().getHeight() + p.getIcon().getRadius())
            p.mark();
        }
      }
    }.start();
  }

  public ArrayList<Pickup> getActive() {
    return active;
  }
}
