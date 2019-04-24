package engine.classes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is the base for all background images. The backgrounds are used by the game logic loop
 * to display the infinite scrolling background.
 * 
 * @author - Joshua Colicchio
 * @version - 1.0
 */

public class Background {

  private ImageView iv;
  private int variant;

  /**
   * Constructor for the Background class.
   * 
   * @param img - File path of the image.
   * @param var - Variant number
   */
  public Background(String img, int var) {
    iv = new ImageView(new Image(img));
    variant = var;
    prepScene();
  }

  /**
   * Returns the ImageView of the background.
   * 
   * @return javafx.scene.image.ImageView
   */
  public ImageView getIV() {
    return iv;
  }

  /**
   * This method moves the ImageView off screen to be called upon later.
   */
  public void sendToStorage() {
    iv.setY(-iv.getFitHeight());
  }

  /**
   * This method sets the size of the ImageView, then sets the X position of the image to display
   * the requested variant.
   */
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
  }
}
