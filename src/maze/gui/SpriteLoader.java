package maze.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteLoader {


    public static BufferedImage getSprite(Point pos, String filename, int spriteSize) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("./Maze Runner/task/res/textures/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return image.getSubimage(
                pos.x * spriteSize,
                pos.y * spriteSize,
                spriteSize,
                spriteSize);
    }

}
