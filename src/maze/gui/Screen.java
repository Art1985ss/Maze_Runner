package maze.gui;

import maze.Block;
import maze.Maze;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Screen extends Canvas implements Runnable {
    private static final int SPRITE_SIZE = 8;
    private int width;
    private int height;
    private Maze maze;
    private boolean showPath = false;
    private boolean showAlgorithm = false;

    public Screen() {
        width = SPRITE_SIZE * 101;
        height = SPRITE_SIZE * 101;
        this.setPreferredSize(new Dimension(width, height));
    }

    public void setShowPath(boolean showPath) {
        this.showPath = showPath;
    }

    public void setShowAlgorithm(boolean showAlgorithm) {
        this.showAlgorithm = showAlgorithm;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
        width = maze.getWidth() * SPRITE_SIZE;
        height = maze.getHeight() * SPRITE_SIZE;
        this.setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            BufferStrategy bufferStrategy = getBufferStrategy();
            if (maze == null) {
                continue;
            }
            if (bufferStrategy == null) {
                createBufferStrategy(3);
                continue;
            }
            Graphics graphics = bufferStrategy.getDrawGraphics();
            for (int x = 0; x < maze.getWidth(); x++) {
                for (int y = 0; y < maze.getHeight(); y++) {
                    Block block = maze.getBlock(x, y);
                    if (block.equals(Block.PATH)) {
                        if (!showPath) {
                            block = Block.PASS;
                        }
                    }
                    if (block.equals(Block.CHECKED)) {
                        if (!showAlgorithm) {
                            block = Block.PASS;
                        }
                    }
                    graphics.drawImage(block.getSprite(), x * SPRITE_SIZE, y * SPRITE_SIZE, null);
                }
            }
            bufferStrategy.show();
            graphics.dispose();
        }
    }
}
