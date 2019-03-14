package utils;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import main.Main;
import utils.Enums.*;

/*
 * Joshua Colicchio
 * 
 * The BuildPanel method in the Main class was getting a little heavy, so I decided to move the
 * entire functionality over the BuildPanel method into its own class and use the methods in this
 * class to build the new panels and set the main window frame
 * 
 */

public class PanelFactory {

  private static Main mainInstance; // Store a reference to the instance of the main class
  private static EventDriver eventDriverInstance; // Stores a reference to the eventDriver
  private static JFrame frameInstance; // Stores a reference to the frame being displayed

  public static void updateInstance(Main mInstance, EventDriver eInstance, JFrame frame) {
    mainInstance = mInstance;
    eventDriverInstance = eInstance;
    frameInstance = frame;
  }

  // Pre-built menus below

  public static void buildAndDisplayMainMenu() {
    // This is used when the class calling this method doesn't have a reference to
    // the main window,
    // so this method will pass it instead
    buildAndDisplayMainMenu(frameInstance);
  }

  public static void buildAndDisplayDataTypesMenu() {
    // This is used when the class calling this method doesn't have a reference to
    // the main window,
    // so this method will pass it instead

    // BuildAndDisplayDataTypesMenu is a method call, and frameInstance is the
    // argument
    buildAndDisplayDataTypesMenu(frameInstance);
  }

  // The 'public static JFrame BuildAndDisplayMainMenu' portion is the header of a
  // method
  // The (JFrame frame) portion is the parameter
  public static JFrame buildAndDisplayMainMenu(JFrame frame) {

    // Clean frame to make space to build new frame
    frame.getContentPane().removeAll();
    frame.repaint();

    // Set frame layout, create constraints
    frame.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    // Create panel and add to frame
    JPanel panel = new JPanel(new GridBagLayout());
    frame.add(panel, gbc);

    // Create and add title
    setAll(gbc, 0, 0, 3, 1, 0.1, 0.1);
    String titleText = "Welcome to my Integration Project!";
    JLabel title = new JLabel(titleText, JLabel.CENTER);
    title.setOpaque(true);
    panel.add(title, gbc);

    // Add DataTypes Menu button
    setAll(gbc, 0, 1, 1, 1, 0.1, 0.1);
    gbc.fill = GridBagConstraints.NONE;
    JButton button = new JButton("Click here to learn about Java's data types");

    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        buildAndDisplayDataTypesMenu(frame);
        return;
      }
    });

    panel.add(button, gbc);

 // Add random line generator button
    button = new JButton("Random line generator");
    setGridCoords(gbc, 0, 2);
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // Begin rendering random lines
        buildAndDisplayRandomLinesScreen(frame);
        return;
      }
    });
    panel.add(button, gbc);
    
 // Add non-random line generator button
    button = new JButton("Non-random line generator");
    setGridCoords(gbc, 0, 3);
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // Begin rendering random lines
        buildAndDisplayNonRandomLinesScreen(frame);
        return;
      }
    });
    panel.add(button, gbc);

    // Validate the frame so it updates
    frame.validate();

    return frame;
  }

  public static JFrame buildAndDisplayDataTypesMenu(JFrame frame) {

    // Clean frame to make space to build new frame
    frame.getContentPane().removeAll();
    frame.repaint();

    // Set frame layout, create constraints
    frame.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.NORTH;

    // Create panel and add to frame
    JPanel panel = new JPanel(new GridBagLayout());
    frame.add(panel, gbc);

    // String
    setAll(gbc, 0, 0, 3, 1, 0.5, 0.5);
    JLabel textLabel = new JLabel("String");
    setFont(textLabel, "Arial", 15, true);
    panel.add(textLabel, gbc);

    setAll(gbc, 0, 1, 1, 1, 0.5, 0.5);
    textLabel = new JLabel("A String is a collection of chars.");
    setFont(textLabel, "Arial", 12, false);
    panel.add(textLabel, gbc);

    // Int
    setAll(gbc, 0, 3, 3, 1, 0.5, 0.5);
    textLabel = new JLabel("Integer (int)");
    setFont(textLabel, "Arial", 15, true);
    panel.add(textLabel, gbc);

    setAll(gbc, 0, 4, 1, 1, 0.5, 0.5);
    textLabel = new JLabel("An int is a whole number.");
    setFont(textLabel, "Arial", 12, false);
    panel.add(textLabel, gbc);

    // Double
    setAll(gbc, 0, 6, 3, 1, 0.5, 0.5);
    textLabel = new JLabel("Double");
    setFont(textLabel, "Arial", 15, true);
    panel.add(textLabel, gbc);

    setAll(gbc, 0, 7, 1, 1, 0.5, 0.5);
    textLabel = new JLabel("A double is a 64-bit number with a fractional element.");
    setFont(textLabel, "Arial", 12, false);
    panel.add(textLabel, gbc);

    // Float
    setAll(gbc, 0, 9, 3, 1, 0.5, 0.5);
    textLabel = new JLabel("Float");
    setFont(textLabel, "Arial", 15, true);
    panel.add(textLabel, gbc);

    setAll(gbc, 0, 10, 1, 1, 0.5, 0.5);
    textLabel = new JLabel("A float is a 32-bit number with a fractional element.");
    setFont(textLabel, "Arial", 12, false);
    panel.add(textLabel, gbc);

    // Bool
    setAll(gbc, 0, 12, 3, 1, 0.5, 0.5);
    textLabel = new JLabel("Boolean");
    setFont(textLabel, "Arial", 15, true);
    panel.add(textLabel, gbc);

    setAll(gbc, 0, 13, 1, 1, 0.5, 0.5);
    textLabel = new JLabel("A boolean is a true / false statement.");
    setFont(textLabel, "Arial", 12, false);
    panel.add(textLabel, gbc);

    // Back button
    setAll(gbc, 0, 17, 1, 1, 0.5, 0.5);
    JButton button = new JButton("Back");
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        buildAndDisplayMainMenu(frame);
        return;
      }

    });
    panel.add(button, gbc);

    // Validate the frame so it updates
    frame.validate();

    return frame;
  }

  public static JFrame buildAndDisplayRandomLinesScreen(JFrame frame) {
    eventDriverInstance.beginListeningTo(Events.KEYPRESS);
    eventDriverInstance.runningRandLine();
    mainInstance.setLineStyle(true);
    mainInstance.start();

    return frame;
  }

  public static JFrame buildAndDisplayNonRandomLinesScreen(JFrame frame) {
    eventDriverInstance.beginListeningTo(Events.KEYPRESS);
    eventDriverInstance.runningNonRandLine();
    mainInstance.setLineStyle(false);
    mainInstance.start();

    return frame;
  }

  // Methods to make code cleaner
  public static JLabel setFont(JLabel label, String font) {

    label.setFont(new Font(font, Font.PLAIN, 16));

    return label;
  }
  
  public static JLabel setFont(JLabel label, String font, int size) {

    label.setFont(new Font(font, Font.PLAIN, size));

    return label;
  }
  
  public static JLabel setFont(JLabel label, String font, int size, boolean bBoldText) {

    label.setFont(new Font(font, bBoldText ? Font.BOLD : Font.PLAIN, size));

    return label;
  }

  public static GridBagConstraints setGridCoords(GridBagConstraints gbc, int x, int y) {
    gbc.gridx = x;
    gbc.gridy = y;
    return gbc;
  }

  public static GridBagConstraints setWeights(GridBagConstraints gbc, double x, double y) {
    gbc.weightx = x;
    gbc.weighty = y;
    return gbc;
  }

  public static GridBagConstraints setSize(GridBagConstraints gbc, int width, int height) {
    if (width != -1)
      gbc.gridwidth = width;
    if (height != -1)
      gbc.gridheight = height;
    return gbc;
  }

  public static GridBagConstraints setAll(GridBagConstraints gbc, int gridx, int gridy,
      int gridwidth, int gridheight, double weightx, double weighty) {
    gbc.gridx = gridx;
    gbc.gridy = gridy;
    gbc.gridwidth = gridwidth;
    gbc.gridheight = gridheight;
    gbc.weightx = weightx;
    gbc.weighty = weighty;
    return gbc;
  }
}
