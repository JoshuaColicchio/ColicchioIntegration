package utils;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import main.Main;
import utils.Enums.*;

/*
 * Joshua Colicchio
 * 
 * I wanted to have the program driven by button presses and mouse clicks, so I decided to have one
 * class be the primary driver for all key/mouse based events rather than clutter the other class
 * files with a listener here, a listener there etc. In the current stage, the program only ever
 * needs to listen for keypress events, but I'd like to do something with the mouse for the next
 * PSI.
 * 
 */

public class EventDriver {

  // I'm using this field to store what the event listener should be listening for right now
  private Events listenFor;
  private KeyboardFocusManager manager;
  private Main mainInstance;
  private boolean bIsRandLineRunning = false;

  public EventDriver(Main main, Events listenTo) {
    mainInstance = main;
    listenFor = listenTo;
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    manager.addKeyEventDispatcher(new EventDispatcher());
  }

  public void BeginListeningTo(Events listenTo) {
    if (listenFor == Events.BOTH) // Listener is already listening to both types, so ignore
      return;
    else if (listenFor == Events.NONE) // Listener not listening to anything, assign
      listenFor = listenTo;
    else if (listenFor != listenTo) // Ensure we aren't already listening to the source
      listenFor = Events.BOTH; // If we aren't, then we want both
  }

  public void StopListeningTo(Events listenTo) {
    if (listenFor == Events.BOTH)
      listenFor = (listenTo == Events.KEYPRESS) ? Events.MOUSE : Events.KEYPRESS;
    else
      listenFor = Events.NONE;
  }

  public void StopListeningAll() {
    listenFor = Events.NONE;
  }

  public void RunningRandLine() {
    bIsRandLineRunning = true;
  }

  public void HandleEvent(Events event) {
    switch (event) {
      case KEYPRESS:
        if (bIsRandLineRunning) {
          mainInstance.stop();
          PanelFactory.BuildAndDisplayMainMenu();
        }
        break;
      default:
        break;
    }
  }

  private class EventDispatcher implements KeyEventDispatcher {
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
      if (e.getID() == KeyEvent.KEY_PRESSED) {
        if (listenFor != Events.KEYPRESS && listenFor != Events.BOTH)
          return false;

        HandleEvent(Events.KEYPRESS);
      } else if (e.getID() == KeyEvent.KEY_RELEASED) {
        return false;
      } else if (e.getID() == KeyEvent.KEY_TYPED) {
        return false;
      }
      return false;
    }
  }

}
