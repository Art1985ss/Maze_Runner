package maze;

import maze.gui.SpriteLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public enum Block {
    PASS(' ', new Point(0, 0)),
    WALL('\u2588', new Point(1, 0)),
    PATH('/', new Point(2, 0)),
    CHECKED('*', new Point(3,0));

    private final char sign;
    private final BufferedImage sprite;

    Block(char sign, Point spritePos) {
        this.sign = sign;
        this.sprite = SpriteLoader.getSprite(spritePos, "skins.png", 8);
    }

    public static Block getBySign(char sign) {
        return Arrays.stream(values()).filter(v -> v.sign == sign).findAny()
                .orElseThrow(() -> new MazeException("Invalid sign for block"));
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    @Override
    public String toString() {
        return String.format("%s%s", sign, sign);
    }
}
