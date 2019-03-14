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

  public static void UpdateInstance(Main mInstance, EventDriver eInstance, JFrame frame) {
    mainInstance = mInstance;
    eventDriverInstance = eInstance;
    frameInstance = frame;
  }

  // Pre-built menus below

  public static void BuildAndDisplayMainMenu() {
    // This is used when the class calling this method doesn't have a reference to
    // the main window,
    // so this method will pass it instead
    BuildAndDisplayMainMenu(frameInstance);
  }

  public static void BuildAndDisplayDataTypesMenu() {
    // This is used when the class calling this method doesn't have a reference to
    // the main window,
    // so this method will pass it instead

    // BuildAndDisplayDataTypesMenu is a method call, and frameInstance is the
    // argument
    BuildAndDisplayDataTypesMenu(frameInstance);
  }

  // The 'public static JFrame BuildAndDisplayMainMenu' portion is the header of a
  // method
  // The (JFrame frame) portion is the parameter
  public static JFrame BuildAndDisplayMainMenu(JFrame frame) {

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
    SetAll(gbc, 0, 0, 3, 1, 0.1, 0.1);
    String titleText = "Welcome to my Integration Project!";
    JLabel title = new JLabel(titleText, JLabel.CENTER);
    title.setOpaque(true);
    panel.add(title, gbc);

    // Add DataTypes Menu button
    SetAll(gbc, 0, 1, 1, 1, 0.1, 0.1);
    gbc.fill = GridBagConstraints.NONE;
    JButton button = new JButton("Click here to learn about Java's data types");

    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        BuildAndDisplayDataTypesMenu(frame);
        return;
      }
    });

    panel.add(button, gbc);

    // Add random line generator button
    button = new JButton("Random line generator");
    SetGridCoords(gbc, 0, 2);
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // Begin rendering random lines
        BuildAndDisplayRandomLinesScreen(frame);
        return;
      }
    });
    panel.add(button, gbc);

    // Validate the frame so it updates
    frame.validate();

    return frame;
  }

  public static JFrame BuildAndDisplayDataTypesMenu(JFrame frame) {

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
    SetAll(gbc, 0, 0, 3, 1, 0.5, 0.5);
    JLabel textLabel = new JLabel("String");
    SetFont(textLabel, "Arial", 15, true);
    panel.add(textLabel, gbc);

    SetAll(gbc, 0, 1, 1, 1, 0.5, 0.5);
    textLabel = new JLabel("A String is a collection of chars.");
    SetFont(textLabel, "Arial", 12, false);
    panel.add(textLabel, gbc);

    // Int
    SetAll(gbc, 0, 3, 3, 1, 0.5, 0.5);
    textLabel = new JLabel("Integer (int)");
    SetFont(textLabel, "Arial", 15, true);
    panel.add(textLabel, gbc);

    SetAll(gbc, 0, 4, 1, 1, 0.5, 0.5);
    textLabel = new JLabel("An int is a whole number.");
    SetFont(textLabel, "Arial", 12, false);
    panel.add(textLabel, gbc);

    // Double
    SetAll(gbc, 0, 6, 3, 1, 0.5, 0.5);
    textLabel = new JLabel("Double");
    SetFont(textLabel, "Arial", 15, true);
    panel.add(textLabel, gbc);

    SetAll(gbc, 0, 7, 1, 1, 0.5, 0.5);
    textLabel = new JLabel("A double is a 64-bit number with a fractional element.");
    SetFont(textLabel, "Arial", 12, false);
    panel.add(textLabel, gbc);

    // Float
    SetAll(gbc, 0, 9, 3, 1, 0.5, 0.5);
    textLabel = new JLabel("Float");
    SetFont(textLabel, "Arial", 15, true);
    panel.add(textLabel, gbc);

    SetAll(gbc, 0, 10, 1, 1, 0.5, 0.5);
    textLabel = new JLabel("A float is a 32-bit number with a fractional element.");
    SetFont(textLabel, "Arial", 12, false);
    panel.add(textLabel, gbc);

    // Bool
    SetAll(gbc, 0, 12, 3, 1, 0.5, 0.5);
    textLabel = new JLabel("Boolean");
    SetFont(textLabel, "Arial", 15, true);
    panel.add(textLabel, gbc);

    SetAll(gbc, 0, 13, 1, 1, 0.5, 0.5);
    textLabel = new JLabel("A boolean is a true / false statement.");
    SetFont(textLabel, "Arial", 12, false);
    panel.add(textLabel, gbc);

    // Back button
    SetAll(gbc, 0, 17, 1, 1, 0.5, 0.5);
    JButton button = new JButton("Back");
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        BuildAndDisplayMainMenu(frame);
        return;
      }

    });
    panel.add(button, gbc);

    // Validate the frame so it updates
    frame.validate();

    return frame;
  }

  public static JFrame BuildAndDisplayRandomLinesScreen(JFrame frame) {
    eventDriverInstance.BeginListeningTo(Events.KEYPRESS);
    eventDriverInstance.RunningRandLine();
    mainInstance.start();

    return frame;
  }

  // Methods to make code cleaner
  public static JLabel SetFont(JLabel label, String font) {

    label.setFont(new Font(font, Font.PLAIN, 16));

    return label;
  }
  
  public static JLabel SetFont(JLabel label, String font, int size) {

    label.setFont(new Font(font, Font.PLAIN, size));

    return label;
  }
  
  public static JLabel SetFont(JLabel label, String font, int size, boolean bBoldText) {

    label.setFont(new Font(font, bBoldText ? Font.BOLD : Font.PLAIN, size));

    return label;
  }

  public static GridBagConstraints SetGridCoords(GridBagConstraints gbc, int x, int y) {
    gbc.gridx = x;
    gbc.gridy = y;
    return gbc;
  }

  public static GridBagConstraints SetWeights(GridBagConstraints gbc, double x, double y) {
    gbc.weightx = x;
    gbc.weighty = y;
    return gbc;
  }

  public static GridBagConstraints SetSize(GridBagConstraints gbc, int width, int height) {
    if (width != -1)
      gbc.gridwidth = width;
    if (height != -1)
      gbc.gridheight = height;
    return gbc;
  }

  public static GridBagConstraints SetAll(GridBagConstraints gbc, int gridx, int gridy,
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
