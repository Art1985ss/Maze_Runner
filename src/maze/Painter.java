package maze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Painter extends Canvas implements Runnable {
    private static final int SIZE = 8;
    private final BufferedImage pass;
    private final BufferedImage wall;
    private final BufferedImage path;
    private final JFrame frame;
    private BufferStrategy bs;
    private Maze maze;
    private boolean showPath = false;
    private Graphics g;

    public Painter(Maze maze) {
        this.maze = maze;
        this.setPreferredSize(new Dimension(maze.getWidth() * SIZE, maze.getHeight() * SIZE));
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("./Maze Runner/task/res/textures/skins.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        pass = image.getSubimage(0, 0, SIZE, SIZE);
        wall = image.getSubimage(SIZE, 0, SIZE, SIZE);
        path = image.getSubimage(SIZE * 2, 0, SIZE, SIZE);

        frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        this.createBufferStrategy(3);
    }

    public boolean isShowPath() {
        return showPath;
    }

    public void setShowPath(boolean showPath) {
        this.showPath = showPath;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
        this.setPreferredSize(new Dimension(maze.getWidth(), maze.getHeight()));
        this.frame.pack();
    }

    public void showMaze() {
        bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            System.out.println("Creating buffer");
            return;
        }
        g = bs.getDrawGraphics();
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                BufferedImage img = pass;
                Block block = maze.getBlock(x, y);
                if (block.equals(Block.PATH)) {
                    if (showPath) {
                        img = path;
                    }
                } else if (block.equals(Block.WALL)) {
                    img = wall;
                }
                g.drawImage(img, x * SIZE, y * SIZE, null);
            }
        }
        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        while (true)
            showMaze();
    }
}
