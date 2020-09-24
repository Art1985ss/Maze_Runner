package maze;

import java.util.Arrays;

public enum Block {
    PASS(' '),
    WALL('\u2588'),
    PATH('/');

    private final char sign;

    Block(char sign) {
        this.sign = sign;
    }

    public static Block getBySign(char sign) {
        return Arrays.stream(values()).filter(v -> v.sign == sign).findAny()
                .orElseThrow(() -> new MazeException("Invalid sign for block"));
    }

    @Override
    public String toString() {
        return String.format("%s%s", sign, sign);
    }
}
