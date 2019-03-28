package engine.utils;

import java.util.ArrayList;
import java.util.Random;
import engine.Engine;
import javafx.scene.layout.Pane;

public class BackgroundManager {

  private Background bg1 = new Background("engine/res/bg3.png", 0);
  private Background bg2 = new Background("engine/res/bg3.png", 1);
  private Background bg3 = new Background("engine/res/bg3.png", 2);
  private Background bg4 = new Background("engine/res/bg3.png", 3);
  private ArrayList<Background> bgList = new ArrayList<>();
  private int onDisplay = -1, next = -1;

  public BackgroundManager() {
    
    Engine.getRoot().getChildren().addAll(bg1.iv, bg2.iv, bg3.iv, bg4.iv);
    bgList.add(bg1);
    bgList.add(bg2);
    bgList.add(bg3);
    bgList.add(bg4);
    genNext();
    prepareScreen(true);
  }

  public void genNext() {
    Random r = new Random();
    if (onDisplay == -1) {
      onDisplay = r.nextInt(4);
      System.out.println("First screen to display: " + onDisplay);
    }

    next = onDisplay;
    while (next == onDisplay)
      next = r.nextInt(4);

    System.out.println("DEBUG: next screen to display is: " + next);
    prepareScreen(false);
  }

  public void prepareScreen(boolean isInitial) {
    if (isInitial) {
      bgList.get(onDisplay).iv.setY(-bgList.get(onDisplay).iv.getFitHeight() + 800);
    }
    else 
      bgList.get(next).iv.setY(-bgList.get(next).iv.getFitHeight());
  }

  public void update() {
    try {
      if (bgList.get(onDisplay).iv.getY() + 5 <= 0)
        bgList.get(onDisplay).iv.setY(bgList.get(onDisplay).iv.getY() + 5);
      else if (bgList.get(onDisplay).iv.getY() + 5 <= Engine.getScene().getHeight()) {
        bgList.get(onDisplay).iv.setY(bgList.get(onDisplay).iv.getY() + 5);
        bgList.get(next).iv.setY(bgList.get(next).iv.getY() + 5);
      }
      else {
        bgList.get(onDisplay).sendToStorage();
        onDisplay = next;
        genNext();
      }
    } catch (Exception x) {
        System.out.println(x);
    }
  }

}
