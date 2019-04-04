package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import engine.Engine.GameState;
import engine.classes.Background;
import engine.classes.Bullet;
import engine.classes.Pickup;
import javafx.animation.AnimationTimer;
import ships.baseclasses.EnemyShip;
import ships.enemies.Enemy1;
import ships.enemies.Enemy2;
import ships.player.Player;

public class GameLogic {

  // Shared random instance
  private Random rand = new Random();

  // Variables relating to player
  private Player player;
  private boolean playerFiring = false;
  private int playerScore = 0;

  // Variables relating to background scroller
  private Background bg1 = new Background("engine/res/bg3.png", 0);
  private Background bg2 = new Background("engine/res/bg3.png", 1);
  private Background bg3 = new Background("engine/res/bg3.png", 2);
  private Background bg4 = new Background("engine/res/bg3.png", 3);
  private Background currBG, nextBG;

  // Variables relating to enemy management
  private int maxOnScreenEnemies = 5;
  private long lastEnemySpawn = -1, enemySpawnRate = 1_000_000_000;
  private ArrayList<EnemyShip> onScreenEnemies = new ArrayList<>();

  // Variables relating to pickup management
  private int maxOnScreenPickups = 2;
  private long lastPickupSpawn = -1, pickupSpawnRate = 1_000_000_000;
  private boolean canSpawnPickup = true; // true by default
  private ArrayList<Pickup> onScreenPickups = new ArrayList<>();

  // Variables relating to bullet management
  private ArrayList<Bullet> onScreenBullets = new ArrayList<>();

  // Animation Timer
  private AnimationTimer timer;

  public GameLogic() {
    // Add all backgrounds to engine root
    Engine.getRoot().getChildren().addAll(bg1.getIV(), bg2.getIV(), bg3.getIV(), bg4.getIV());

    // Initialize default background files
    currBG = indexOfBG(rand.nextInt(4));
    currBG.getIV().setY(-currBG.getIV().getFitHeight() + 800);
    chooseNextBackground();

    // Initialize default pickup variables
    lastPickupSpawn = System.nanoTime();

    // Create player
    player = new Player();

    // Start looping
    timer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        Engine.getRoot().requestFocus();
        if (Engine.getGameState() == GameState.LOADING) {
          if (Engine.getStartTime() + 1000000000 < now)
            startGame();
        } else if (Engine.getGameState() == GameState.RUNNING) {
          Engine.disableText();
          Engine.updateScore("Score: " + playerScore + "\nHealth: " + (int)player.getHealth());
          updateBackground();
          updateBullets();
          updateEnemies(now);
          updatePickups(now);
          player.update(now);
          if (playerFiring)
            player.fire();
        } else if (Engine.getGameState() == GameState.PAUSED) {
          Engine.enableText("Paused");
        } else if (Engine.getGameState() == GameState.GAMEOVER) {
          Engine.enableText("Game over!\nPress any\nbutton to restart");
          stopAnimationTimer();
        }
      }
    };
  }

  // Methods relating to pause system
  public void pauseGame() {
    for (Iterator<EnemyShip> it = onScreenEnemies.iterator(); it.hasNext();) {
      EnemyShip e = it.next();
      if (e.getClass() == Enemy2.class) {
        Enemy2 en = (Enemy2) e;
        en.setPauseTime(System.nanoTime());
      }
    }
  }

  public void cleanup() {
    for (Iterator<EnemyShip> it = onScreenEnemies.iterator(); it.hasNext();) {
      EnemyShip e = it.next();
      Engine.getRoot().getChildren().remove(e.getImageView());
      it.remove();
    }

    for (Iterator<Pickup> it = onScreenPickups.iterator(); it.hasNext();) {
      Pickup p = it.next();
      Engine.getRoot().getChildren().remove(p.getIcon());
      it.remove();
    }

    for (Iterator<Bullet> it = onScreenBullets.iterator(); it.hasNext();) {
      Bullet b = it.next();
      Engine.getRoot().getChildren().remove(b.circle());
      it.remove();
    }
  }

  public void startGame() {
    Engine.disableText();
    Engine.initScoreDisplay();
    player.setHealth(Engine.getDifficulty());
    Engine.setGameState(GameState.RUNNING);
    startAnimationTimer();
  }

  public void gameOver() {
    Engine.setGameState(GameState.GAMEOVER);
  }

  public void stopAnimationTimer() {
    timer.stop();
  }

  public void startAnimationTimer() {
    timer.start();
  }

  // Methods relating to background system
  public void chooseNextBackground() {
    nextBG = currBG;
    while (nextBG == currBG)
      nextBG = indexOfBG(rand.nextInt(4));
  }

  public Background indexOfBG(int ind) {
    switch (ind) {
      case 0:
        return bg1;
      case 1:
        return bg2;
      case 2:
        return bg3;
      case 3:
        return bg4;
      default:
        System.out.println("ERROR: GameLogic attempting to access invalid background");
        return (currBG == bg1) ? bg2 : bg1; // return a default value in case of error
    }
  }

  public void updateBackground() {
    if (Engine.getGameState() == GameState.GAMEOVER)
      return;

    try {
      if (currBG.getIV().getY() + 5 <= 0)
        currBG.getIV().setY(currBG.getIV().getY() + 5);
      else if (currBG.getIV().getY() + 5 <= Engine.getScene().getHeight()) {
        currBG.getIV().setY(currBG.getIV().getY() + 5);
        nextBG.getIV().setY(nextBG.getIV().getY() + 5);
      } else {
        currBG.sendToStorage();
        currBG = nextBG;
        chooseNextBackground();
      }
    } catch (Exception x) {
      System.out.println("ERROR: backgroundLogic: \"" + x + "\"");
    }
  }

  // Methods relating to enemy management
  public void updateEnemies(long now) {
    if (Engine.getGameState() == GameState.GAMEOVER)
      return;

    if (onScreenEnemies.size() < maxOnScreenEnemies && now > lastEnemySpawn + enemySpawnRate * 2) {
      lastEnemySpawn = now;
      EnemyShip e = null;
      switch (rand.nextInt(2)) {
        case 0:
          e = new Enemy1(rand.nextInt((int) Engine.getScene().getWidth()) - 50, -50, playerScore / 5000);
          break;
        case 1:
          e = new Enemy2(rand.nextInt((int) Engine.getScene().getWidth()) - 50, -50, playerScore / 5000);
          break;
      }
      onScreenEnemies.add(e);
      Engine.getRoot().getChildren().add(e.getImageView());
    }
    try {
      for (Iterator<EnemyShip> it = onScreenEnemies.iterator(); it.hasNext();) {
        EnemyShip e = it.next();
        if (e.markForRemoval()) {
          Engine.getRoot().getChildren().remove(e.getImageView());
          it.remove();
        } else
          e.update(now);
      }
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  // Methods relating to pickup management
  public void updatePickups(long now) {
    if (Engine.getGameState() == GameState.GAMEOVER)
      return;

    if (onScreenPickups.size() < maxOnScreenPickups && now > lastPickupSpawn + pickupSpawnRate * 15
        && canSpawnPickup == false) {
      canSpawnPickup = true;
    }

    if (canSpawnPickup && now > lastPickupSpawn + 30 * 1_000_000_000) {
      spawnPickup();
    }
    try {
      for (Iterator<Pickup> it = onScreenPickups.iterator(); it.hasNext();) {
        Pickup p = it.next();
        if (p.marked()) {
          Engine.getRoot().getChildren().remove(p.getIcon());
          it.remove();
        } else {
          p.getIcon().setCenterY(p.getIcon().getCenterY() + 3);
          if (p.getIcon().getBoundsInParent().intersects(player.getShip().getBoundsInParent())) {
            p.mark();
            player.onPickup(p);
          } else if (p.getIcon().getCenterY() > Engine.getScene().getHeight()
              + p.getIcon().getRadius())
            p.mark();
        }
      }
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  public void spawnPickup(double x, double y) {
    int lotto = rand.nextInt(151);
    if (canSpawnPickup && lotto >= 100 && lotto <= 130) {
      Pickup pu = new Pickup(x, y, rand.nextInt(3));
      onScreenPickups.add(pu);
      Engine.getRoot().getChildren().add(pu.getIcon());
      lastPickupSpawn = System.nanoTime();
      canSpawnPickup = false;
    }
  }

  public void spawnPickup() {
    Pickup pu =
        new Pickup(rand.nextInt((int) Engine.getScene().getWidth()) - 50, -50, rand.nextInt(3));
    onScreenPickups.add(pu);
    Engine.getRoot().getChildren().add(pu.getIcon());
    lastPickupSpawn = System.nanoTime();
    canSpawnPickup = false;
  }

  // Methods relating to bullet management
  public void updateBullets() {
    if (Engine.getGameState() == GameState.GAMEOVER)
      return;
    try {
      for (Iterator<Bullet> it = onScreenBullets.iterator(); it.hasNext();) {
        Bullet b = it.next();
        if (b.marked()) {
          Engine.getRoot().getChildren().remove(b.circle());
          it.remove();
        } else {
          if (b.circle().getCenterY() + b.getSpeed() < Engine.getScene().getHeight()
              && b.circle().getCenterY() + b.getSpeed() > 0) { // bullet within bounds
            b.circle().setCenterY(b.circle().getCenterY() + b.getSpeed());

            // check for collision
            if (b.getFriendly()) { // can only hit enemies
              for (Iterator<EnemyShip> t = onScreenEnemies.iterator(); t.hasNext();) {
                EnemyShip e = t.next();
                if (b.circle().getBoundsInParent()
                    .intersects(e.getImageView().getBoundsInParent())) {
                  // bullet hit, cleanup and deal damage
                  b.mark();
                  e.onTakeDamage(b.getDamage());
                }
              }
            } else { // can only hit player
              if (b.circle().getBoundsInParent().intersects(player.getShip().getBoundsInParent())) {
                b.mark();
                player.onTakeDamage(b.getDamage());
              }
            }
          } else {
            b.mark();
          }
        }
      }
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  public void registerBullet(Bullet b) {
    onScreenBullets.add(b);
    Engine.getRoot().getChildren().add(b.circle());
  }

  // Methods related to player management
  public void playerFiring(boolean f) {
    playerFiring = f;
  }

  public void onEnemyDestroyed(int score) {
    playerScore += score;
  }
}
