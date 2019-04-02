package engine.managers;

import java.util.ArrayList;
import java.util.Random;
import engine.classes.Background;
import engine.Engine;

public class BackgroundManager {

  private Background bg1 = new Background("engine/res/bg3.png", 0);
  private Background bg2 = new Background("engine/res/bg3.png", 1);
  private Background bg3 = new Background("engine/res/bg3.png", 2);
  private Background bg4 = new Background("engine/res/bg3.png", 3);
  private ArrayList<Background> bgList = new ArrayList<>();
  private int onDisplay = -1, next = -1;

  public BackgroundManager() {
    
    Engine.getRoot().getChildren().addAll(bg1.getIV(), bg2.getIV(), bg3.getIV(), bg4.getIV());
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
      bgList.get(onDisplay).getIV().setY(-bgList.get(onDisplay).getIV().getFitHeight() + 800);
    }
    else 
      bgList.get(next).getIV().setY(-bgList.get(next).getIV().getFitHeight());
  }

  public void update() {
    try {
      if (bgList.get(onDisplay).getIV().getY() + 5 <= 0)
        bgList.get(onDisplay).getIV().setY(bgList.get(onDisplay).getIV().getY() + 5);
      else if (bgList.get(onDisplay).getIV().getY() + 5 <= Engine.getScene().getHeight()) {
        bgList.get(onDisplay).getIV().setY(bgList.get(onDisplay).getIV().getY() + 5);
        bgList.get(next).getIV().setY(bgList.get(next).getIV().getY() + 5);
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
