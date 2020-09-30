package maze;

import java.util.Scanner;

public class Application {
    private final Scanner scanner = new Scanner(System.in);
    private Maze maze;
    private Painter painter;
    private Thread t;

    public void execute() {
        boolean run = true;
        do {
            System.out.println(createMenu());
            try {
                run = processUserInput();
            } catch (MazeException e) {
                System.out.println(e.getMessage());
            }
        } while (run);
    }

    private String createMenu() {
        StringBuilder sb = new StringBuilder("=== Menu ===\n");
        sb.append("1. Generate a new maze").append("\n");
        sb.append("2. Load a maze").append("\n");
        if (maze != null) {
            sb.append("3. Save the maze").append("\n");
            sb.append("4. Display the maze").append("\n");
            sb.append("5. Find the escape").append("\n");
        }
        sb.append("0. Exit").append("\n");
        return sb.toString();
    }

    private boolean processUserInput() throws MazeException {
        String input = scanner.nextLine();
        switch (input) {
            case "0":
                return false;
            case "1":
                createNewMaze();
                return true;
            case "2":
                loadMaze();
                return true;
            case "3":
                saveMaze();
                return true;
            case "4":
                System.out.println(maze);
                painter.setShowPath(false);
                return true;
            case "5":
                System.out.println(maze.escapeToString());
                painter.setShowPath(true);
                return true;
            default:
                throw new MazeException("Incorrect option. Please try again");
        }
    }

    private void createNewMaze() {
        System.out.println("Enter the size of a new maze");
        try {
            if (t != null)
                t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int size = Integer.parseInt(scanner.nextLine());
        maze = new Maze(size, size);
        painter = new Painter(maze);
        Thread t = new Thread(painter);
        t.start();
        System.out.println(maze);
    }

    private void loadMaze() throws MazeException {
        System.out.println("Enter path to file :");
        String filepath = scanner.nextLine();
        maze = new Maze(FileManager.load(filepath));
    }

    private void saveMaze() throws MazeException {
        System.out.println("Enter path to file :");
        String filepath = scanner.nextLine();
        FileManager.save(filepath, maze.toStringForFile());
    }
}
