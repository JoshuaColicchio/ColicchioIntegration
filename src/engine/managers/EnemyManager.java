package engine.managers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import engine.Engine;
import javafx.animation.AnimationTimer;
import ships.Enemy1;

public class EnemyManager {

  private int maxEnemyCount = 5;
  private long lastSpawn = -1;
  private long spawnRate = (long) (1000000000);
  private ArrayList<Enemy1> allEnemies = new ArrayList<>();
  
  public EnemyManager() {
    new AnimationTimer() {
      
      @Override
      public void handle(long now) {
        for (Iterator<Enemy1> it = allEnemies.iterator(); it.hasNext();) {
          Enemy1 enemy = it.next();
          if (enemy.marked()) {
            Engine.getRoot().getChildren().remove(enemy.iv());
            it.remove();
          }
          else
            enemy.update(now);
        }
        
        if (allEnemies.size() < maxEnemyCount && now > lastSpawn + spawnRate * 5) {
          lastSpawn = now;
          Random r = new Random();
          Enemy1 enemy = new Enemy1(r.nextInt((int)Engine.getScene().getWidth()) - 50, -(r.nextInt(50) + 50));
          allEnemies.add(enemy);
          Engine.getRoot().getChildren().add(enemy.iv());
        }
      }
    }.start();
  }
  
  public ArrayList<Enemy1> getActive() {
    return allEnemies;
  }
}
