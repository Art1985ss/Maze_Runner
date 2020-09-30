package maze.gui;

import maze.Block;
import maze.Maze;

import javax.swing.*;
import java.awt.*;

public class MazeFrame extends JFrame {
    private Maze maze;
    private JButton generateButton;
    private final JToggleButton showPathButton;
    private JPanel screenPanel;
    private Screen screen;
    private final JLabel widthLabel;
    private final JSlider widthSlider;
    private final JLabel heightLabel;
    private final JSlider heightSlider;
    private final JCheckBox showAlgorithm;

    public MazeFrame() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        this.getContentPane().setLayout(layout);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        widthLabel = new JLabel("Width : 11");
        panel.add(widthLabel);
        widthSlider = new JSlider();
        widthSlider.setMinimum(11);
        widthSlider.setMajorTickSpacing(10);
        widthSlider.setMaximum(110);
        widthSlider.setValue(11);
        widthSlider.setPaintTicks(true);
        widthSlider.addChangeListener(changeEvent -> widthLabel.setText("Width : " + widthSlider.getValue()));
        panel.add(widthSlider);
        heightLabel = new JLabel("Height : 11");
        panel.add(heightLabel);
        heightSlider = new JSlider();
        heightSlider.setMinimum(11);
        heightSlider.setMajorTickSpacing(10);
        heightSlider.setMaximum(110);
        heightSlider.setValue(11);
        heightSlider.setPaintTicks(true);
        heightSlider.addChangeListener(changeEvent -> heightLabel.setText("Height : " + heightSlider.getValue()));
        panel.add(heightSlider);
        generateButton = new JButton("Generate maze");
        generateButton.addActionListener(actionEvent -> {
            maze = new Maze(heightSlider.getValue(), widthSlider.getValue());
            screen.setMaze(maze);
            screenPanel.updateUI();
            this.pack();
            this.repaint();
        });
        panel.add(generateButton);
        showAlgorithm = new JCheckBox();
        showAlgorithm.setName("Show checked paths");
        showAlgorithm.setSelected(false);
        showAlgorithm.addActionListener(actionEvent -> screen.setShowAlgorithm(showAlgorithm.isSelected()));
        showPathButton = new JToggleButton("Show path");
        showPathButton.setSelected(false);
        showPathButton.addActionListener(actionEvent -> {
            screen.setShowPath(showPathButton.isSelected());
            screen.setShowAlgorithm(showAlgorithm.isSelected());
            maze.escapeToString();
            this.pack();
            this.repaint();
        });
        panel.add(showPathButton);
        panel.add(showAlgorithm);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.1;
        this.getContentPane().add(panel, constraints);
        screenPanel = new JPanel();
        screen = new Screen();
        maze = new Maze(heightSlider.getValue(), widthSlider.getValue());
        screen.setMaze(maze);
        Thread t = new Thread(screen);
        screenPanel.add(screen);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.9;
        this.getContentPane().add(screenPanel);
        this.pack();
        this.setVisible(true);
        t.start();
    }
}
