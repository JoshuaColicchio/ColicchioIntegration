package ships;

import java.util.Random;
import engine.classes.Bullet;
import engine.Engine;
import engine.classes.Pickup;
import javafx.scene.image.ImageView;

public class Enemy1 extends GameShip {

  private boolean markedForRemoval = false;
  private long damageTimer = -1;

  public Enemy1(double x, double y) {
    super("ships/res/Enemy1.png", false, -1, 20);
    super.iv.setFitHeight(80);
    super.iv.setFitWidth(80);
    super.iv.setRotate(180);
    super.iv.setX(x);
    super.iv.setY(y);
    onSpawn();
  }

  public boolean marked() {
    return markedForRemoval;
  }

  public ImageView iv() {
    return super.iv;
  }

  @Override
  public void fire() {
    Bullet b = new Bullet(5, 10, 5, 1, super.iv.getX() + super.iv.getFitWidth() / 4,
        super.iv.getY() + 3 * super.iv.getFitHeight() / 4, false);
    Engine.getGameLoop().registerBullet(b);
    b = new Bullet(5, 10, 5, 1, super.iv.getX() + 3 * super.iv.getFitWidth() / 4,
        super.iv.getY() + 3 * super.iv.getFitHeight() / 4, false);
    Engine.getGameLoop().registerBullet(b);
  }

  @Override
  public void onTakeDamage(double dmg) {
    health -= dmg;
    if (health <= 0)
      onDestroyed();
    else
      damageTimer = System.nanoTime();
  }

  @Override
  public void onPickup(Pickup p) {}

  @Override
  public void onSpawn() {
    if (super.iv.getX() <= super.iv.getFitWidth() / 2)
      super.iv.setX(super.iv.getFitWidth() / 2);
    else if (super.iv.getX() >= Engine.getScene().getWidth() - (super.iv.getFitWidth() / 2))
      super.iv.setX(Engine.getScene().getWidth() - (super.iv.getFitWidth() / 2));
  }

  @Override
  public void onDestroyed() {
    markedForRemoval = true;
  }

  @Override
  public void update(long now) {
    super.iv.setY(super.iv.getY() + 5);
    if (super.iv.getY() > Engine.getScene().getHeight()) 
      markedForRemoval = true;
    
    if (damageTimer + 1000000000 > now)
      super.iv.setOpacity((super.iv.getOpacity() == 0.5) ? 1 : 0.5);
    else if (damageTimer + 1000000000 < now)
      super.iv.setOpacity(1);

    Random r = new Random();
    if (r.nextInt(100) == 28)
      fire();
  }

}
