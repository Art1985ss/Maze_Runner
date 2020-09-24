package maze;

import maze.pathfinding.Pathfinder;

import java.util.Scanner;

public class Maze {
    private final int height;
    private final int width;

    private Block[][] matrix;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        MazeGenerator mazeGenerator = new MazeGenerator(height, width);
        matrix = mazeGenerator.getMaze();
    }

    public Maze(String string) {
        Scanner scanner = new Scanner(string);
        height = Integer.parseInt(scanner.next());
        width = Integer.parseInt(scanner.next());
        matrix = new Block[height][width];
        scanner.nextLine();
        for (int row = 0; row < height; row++) {
            String line = scanner.nextLine();
            for (int col = 0; col < width; col++) {
                matrix[row][col] = Block.getBySign(line.charAt(col * 2));
            }
        }
    }

    public String escapeToString() {
        Pathfinder pathfinder = new Pathfinder(matrix);
        matrix = pathfinder.findPath();
        return getString(true);
    }

    private String getString(boolean showPath) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Block block = matrix[row][col];
                stringBuilder.append(block.equals(Block.PATH) && !showPath ? Block.PASS : block);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return getString(false);
    }

    public String toStringForFile() {
        return height + "\t" + width + "\n" + toString();
    }

}
