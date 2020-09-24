package maze;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    public static void save(String filepath, String text) throws MazeException {
        try {
            Files.writeString(Path.of(filepath), text);
        } catch (IOException e) {
            throw new MazeException(e.getMessage());
        }
    }

    public static String load(String filepath) throws MazeException {
        String text;
        try {
            text = Files.readString(Path.of(filepath));
        } catch (IOException e) {
            throw new MazeException(e.getMessage());
        }
        return text;
    }
}
