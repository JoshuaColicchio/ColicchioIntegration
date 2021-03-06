package engine;

import engine.Engine.GameState;
import engine.classes.Background;
import engine.classes.Bullet;
import engine.classes.Pickup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javafx.animation.AnimationTimer;
import ships.baseclasses.EnemyShip;
import ships.enemies.Enemy1;
import ships.enemies.Enemy2;
import ships.player.Player;

/**
 * This class handles all game logic, including updating player and enemy positions, determining how
 * to handle events depending on the current GameState, scrolling the background, and handling
 * collisions.
 * 
 * @author - Joshua Colicchio
 * @version - 1.0
 */

public class GameLogic {

  // Shared random instance
  private Random rand = new Random();

  // Variables relating to player
  private Player player;
  private boolean playerFiring = false;
  private boolean cleanedup = false;
  private int playerScore = 0;

  // Variables relating to background
  // Image courtesy of nasa.gov
  private Background bg1 = new Background("engine/res/bg3.png", 0);
  private Background bg2 = new Background("engine/res/bg3.png", 1);
  private Background bg3 = new Background("engine/res/bg3.png", 2);
  private Background bg4 = new Background("engine/res/bg3.png", 3);
  private Background currBG;
  private Background nextBG;

  // Variables relating to enemy management
  private int maxOnScreenEnemies = 5;
  private long lastEnemySpawn = -1;
  private long enemySpawnRate = 1_000_000_000;
  private ArrayList<EnemyShip> onScreenEnemies = new ArrayList<>();

  // Variables relating to pickup management
  private int maxOnScreenPickups = 2;
  private long lastPickupSpawn = -1;
  private long pickupSpawnRate = 1_000_000_000;
  private boolean canSpawnPickup = true; // true by default
  private ArrayList<Pickup> onScreenPickups = new ArrayList<>();

  // Variables relating to bullet management
  private ArrayList<Bullet> onScreenBullets = new ArrayList<>();

  // Animation Timer
  private AnimationTimer timer;

  /**
   * This method sets the initial values for the GameLogic loop, and then starts the loop.
   */
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
          if (Engine.getStartTime() + 1000000000 < now) {
            startGame();
          }
        } else if (Engine.getGameState() == GameState.RUNNING) {
          Engine.disableText();
          Engine.updateScore("Score: " + playerScore + "\nHealth: " + (int) player.getHealth());
          updateBackground();
          updateBullets();
          updateEnemies(now);
          updatePickups(now);
          player.update(now);
          if (playerFiring) {
            player.fire();
          }
        } else if (Engine.getGameState() == GameState.PAUSED) {
          Engine.enableText("Paused");
        } else if (Engine.getGameState() == GameState.GAMEOVER) {
          if (!cleanedup) {
            cleanup();
          }
          Engine.enableText("Game over!\nPress any\nbutton to restart");
          stopAnimationTimer();
        }
      }
    };
  }

  /**
   * This method is called when the game state is changed to PAUSED. It tells each instance of
   * Enemy2 what time the game was paused at so that the path function can offset itself by the
   * pause time so the path of motion doesn't jerk suddenly.
   */
  public void pauseGame() {
    for (Iterator<EnemyShip> it = onScreenEnemies.iterator(); it.hasNext();) {
      EnemyShip enemy1 = it.next();
      if (enemy1.getClass() == Enemy2.class) {
        Enemy2 enemy2 = (Enemy2) enemy1;
        enemy2.setPauseTime(System.nanoTime());
      }
    }
  }

  /**
   * This method clears all game objects from the screen (Enemies, Pickups, etc.). Called when game
   * state is set to GAMEOVER.
   */
  public void cleanup() {
    for (Iterator<EnemyShip> it = onScreenEnemies.iterator(); it.hasNext();) {
      EnemyShip enemy1 = it.next();
      Engine.getRoot().getChildren().remove(enemy1.getImageView());
      it.remove();
    }

    for (Iterator<Pickup> it = onScreenPickups.iterator(); it.hasNext();) {
      Pickup pickup = it.next();
      Engine.getRoot().getChildren().remove(pickup.getIcon());
      it.remove();
    }

    for (Iterator<Bullet> it = onScreenBullets.iterator(); it.hasNext();) {
      Bullet bullet = it.next();
      Engine.getRoot().getChildren().remove(bullet.circle());
      it.remove();
    }

    cleanedup = true;
  }

  /**
   * Prepares the game to start, sets the game state to RUNNING, then starts the game logic loop.
   */
  public void startGame() {
    Engine.disableText();
    player.setHealth(Engine.getDifficulty());
    Engine.setGameState(GameState.RUNNING);
    cleanedup = false;
    startAnimationTimer();
  }

  /**
   * Stops the game logic loop.
   */
  public void stopAnimationTimer() {
    timer.stop();
  }

  /**
   * Starts the game logic loop.
   */

  public void startAnimationTimer() {
    timer.start();
  }

  // Methods relating to background system
  /**
   * This method randomly chooses a new background image to display.
   */
  public void chooseNextBackground() {
    nextBG = currBG;
    while (nextBG == currBG) {
      nextBG = indexOfBG(rand.nextInt(4));
      continue;
      // The continue statement in a loop ends execution of the current iteration, and jumps to the
      // next loop through
    }
  }

  // Not sure what CheckStyle is mad about here.
  /**
   * This method returns the Background at the provided index in the Background ArrayList.
   * 
   * @param index - Index of requested Background.
   * @return engine.classes.Background
   */
  public Background indexOfBG(int index) {
    switch (index) {
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

  /**
   * This method updates the position of the background, and the next upcoming background. Once the
   * top of the current background reaches the top of the screen, the next background is attached to
   * the top of the screen, and begins to update. When the lower background leaves the screen, it is
   * sent to storage.
   */
  public void updateBackground() {
    if (Engine.getGameState() == GameState.GAMEOVER) {
      return;
    }

    try {
      if (currBG.getIV().getY() + 5 <= 0) {
        currBG.getIV().setY(currBG.getIV().getY() + 5);
      } else if (currBG.getIV().getY() + 5 <= Engine.getScene().getHeight()) {
        currBG.getIV().setY(currBG.getIV().getY() + 5);
        nextBG.getIV().setY(nextBG.getIV().getY() + 5);
      } else {
        currBG.sendToStorage();
        currBG = nextBG;
        chooseNextBackground();
      }
    } catch (Exception ex) {
      System.out.println("ERROR: BackgroundLogic: \"" + ex + "\"");
    }
  }

  // Methods relating to enemy management
  /**
   * Updates position of all on screen enemies, and cleans them up if they are marked for removal.
   * 
   * @param now - The current time in nanoseconds.
   */
  public void updateEnemies(long now) {
    if (Engine.getGameState() == GameState.GAMEOVER) {
      return;
    }

    if (onScreenEnemies.size() < maxOnScreenEnemies && now > lastEnemySpawn + enemySpawnRate * 2) {
      lastEnemySpawn = now;
      EnemyShip enemy = null;
      switch (rand.nextInt(2)) {
        case 0:
          // Casting in Java is when you want the compiler to treat one variable type as another
          // Like below, where I tell Java to treat the Engine.getScene().getWidth() variable, which
          // is a double, as an int.
          enemy = new Enemy1(rand.nextInt((int) Engine.getScene().getWidth()) - 50, -50,
              playerScore / 5000);
          break;
        case 1:
          enemy = new Enemy2(rand.nextInt((int) Engine.getScene().getWidth()) - 50, -50,
              playerScore / 5000);
          break;
        default:
          System.out.println("Somehow, the random got outside of its range!");
          break;
      }
      if (enemy != null) {
        onScreenEnemies.add(enemy);
        Engine.getRoot().getChildren().add(enemy.getImageView());
      }
    }
    try {
      for (Iterator<EnemyShip> it = onScreenEnemies.iterator(); it.hasNext();) {
        EnemyShip enemy = it.next();
        if (enemy.markForRemoval()) {
          Engine.getRoot().getChildren().remove(enemy.getImageView());
          it.remove();
        } else {
          enemy.update(now);
        }
      }
    } catch (Exception ex) {
      // Error expected here during cleanup due to things being deleted while the logic loop is
      // trying to use them, so no need to print the error.
    }
  }

  /**
   * Searches through all on screen enemies until it finds the requested enemy.
   * 
   * @param es - The enemy to find.
   * @return ships.baseclasses.EnemyShip
   */
  public EnemyShip findEnemy(EnemyShip es) {
    EnemyShip enShip = null;
    for (Iterator<EnemyShip> it = onScreenEnemies.iterator(); it.hasNext();) {
      EnemyShip enemy = it.next();
      if (enemy.equals(es)) {
        enShip = enemy;
        break; 
        // If the if statement evaluates as true, the inner code is executed and the loop
        // ends prematurely
      }
    }
    return enShip;
  }

  // Methods relating to pickup management
  /**
   * Updates the position of all on screen pickups, and removes any pickups that have gone off
   * screen. Also handles collision detection with the player.
   * 
   * @param now - The current time in nanoseconds.
   */
  public void updatePickups(long now) {
    if (Engine.getGameState() == GameState.GAMEOVER) {
      return;
    }

    if (onScreenPickups.size() < maxOnScreenPickups && now > lastPickupSpawn + pickupSpawnRate * 15
        && canSpawnPickup == false) {
      canSpawnPickup = true;
    }

    if (canSpawnPickup && now > lastPickupSpawn + 30 * 1_000_000_000) {
      spawnPickup();
    }
    try {
      for (Iterator<Pickup> it = onScreenPickups.iterator(); it.hasNext();) {
        Pickup pickup = it.next();
        if (pickup.marked()) {
          Engine.getRoot().getChildren().remove(pickup.getIcon());
          it.remove();
        } else {
          pickup.getIcon().setCenterY(pickup.getIcon().getCenterY() + 3);
          if (pickup.getIcon().getBoundsInParent()
              .intersects(player.getShip().getBoundsInParent())) {
            pickup.mark();
            player.onPickup(pickup);
          } else if (pickup.getIcon().getCenterY() > Engine.getScene().getHeight()
              + pickup.getIcon().getRadius()) {
            pickup.mark();
          }
        }
      }
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  /**
   * Spawns a pickup with a random effect at the provided X and Y coordinates. Primarily used to
   * spawn pickups on destroyed enemies.
   * 
   * @param xCoord - X Coordinate to spawn the pickup at.
   * @param yCoord - Y Coordinate to spawn the pickup at.
   */
  public void spawnPickup(double xCoord, double yCoord) { 
    // CheckStyle disagrees with the Google Style above.
    int lotto = rand.nextInt(151);
    if (canSpawnPickup && lotto >= 100 && lotto <= 130) {
      Pickup pu = new Pickup(xCoord, yCoord, rand.nextInt(3));
      onScreenPickups.add(pu);
      Engine.getRoot().getChildren().add(pu.getIcon());
      lastPickupSpawn = System.nanoTime();
      canSpawnPickup = false;
    }
  }

  /**
   * Spawns a pickup with a random effect at the top of the screen.
   */
  public void spawnPickup() {
    Pickup pickup =
        new Pickup(rand.nextInt((int) Engine.getScene().getWidth()) - 50, -50, rand.nextInt(3));
    onScreenPickups.add(pickup);
    Engine.getRoot().getChildren().add(pickup.getIcon());
    lastPickupSpawn = System.nanoTime();
    canSpawnPickup = false;
  }

  // Methods relating to bullet management
  /**
   * Updates the position of all on screen bullets, and handles collision detection.
   */
  public void updateBullets() {
    if (Engine.getGameState() == GameState.GAMEOVER) {
      return;
    }
    try {
      for (Iterator<Bullet> it = onScreenBullets.iterator(); it.hasNext();) {
        Bullet bullet = it.next();
        if (bullet.marked()) {
          Engine.getRoot().getChildren().remove(bullet.circle());
          it.remove();
        } else {
          if (bullet.circle().getCenterY() + bullet.getSpeed() < Engine.getScene().getHeight()
              && bullet.circle().getCenterY() + bullet.getSpeed() > 0) { // bullet within bounds
            bullet.circle().setCenterY(bullet.circle().getCenterY() + bullet.getSpeed());

            // check for collision
            if (bullet.getFriendly()) { // can only hit enemies
              for (Iterator<EnemyShip> t = onScreenEnemies.iterator(); t.hasNext();) {
                EnemyShip enemy = t.next();
                if (bullet.circle().getBoundsInParent()
                    .intersects(enemy.getImageView().getBoundsInParent())) {
                  // bullet hit, cleanup and deal damage
                  bullet.mark();
                  enemy.onTakeDamage(bullet.getDamage());
                }
              }
            } else { // can only hit player
              if (bullet.circle().getBoundsInParent()
                  .intersects(player.getShip().getBoundsInParent())) {
                bullet.mark();
                player.onTakeDamage(bullet.getDamage());
              }
            }
          } else {
            bullet.mark();
          }
        }
      }
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

  /**
   * Adds the bullet to the ArrayList of all on screen bullets, and adds it to the root pane.
   * 
   * @param bullet - Bullet to add.
   */
  public void registerBullet(Bullet bullet) {
    onScreenBullets.add(bullet);
    Engine.getRoot().getChildren().add(bullet.circle());
  }

  // Methods related to player management
  /**
   * Updates whether or not the player is currently firing.
   * 
   * @param isFiring - Whether the player is firing or not.
   */
  public void playerFiring(boolean isFiring) {
    playerFiring = isFiring;
  }

  /**
   * Updates the players' score.
   * 
   * @param score - The value to increment the players' score by.
   * @return boolean
   */
  public boolean onEnemyDestroyed(int score) {
    playerScore += score;
    return true;
  }
}
