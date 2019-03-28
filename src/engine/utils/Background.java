package engine.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Background {

  protected ImageView iv;
  protected Pane root;
  private int variant;
  
  public Background(String img, int var) {
    iv = new ImageView(new Image(img));
    variant = var;
    prepScene();
  }

  public void sendToStorage() {
    // this method moves the imageview off screen to be called upon later
    iv.setY(-100000);
  }

  public void prepScene() {
    iv.setFitHeight(iv.getImage().getHeight() * 3);
    iv.setFitWidth(iv.getImage().getWidth() * 4);
    switch (variant) {
      case 0:
        iv.setX(0);
        break;
      case 1:
        iv.setX(-iv.getImage().getWidth());
        break;
      case 2:
        iv.setX(-iv.getImage().getWidth() * 2);
        break;
      case 3:
        iv.setX(-iv.getImage().getWidth() * 3);
        break;
      default:
        iv.setX(0); // should never happen but better safe than sorry
        break;
    }

    sendToStorage();
    // iv.setY(-iv.getFitHeight());
  }
}
