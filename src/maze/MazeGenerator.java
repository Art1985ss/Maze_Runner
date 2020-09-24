package maze;

import maze.prim.Edge;
import maze.prim.Prim;
import maze.prim.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeGenerator {
    private final int height;
    private final int width;
    private final int mazeHeight;
    private final int mazeWidth;
    private final Vertex[][] matrix;
    private final Prim prim;

    public MazeGenerator(int height, int width) {
        this.height = (int) Math.ceil((height - 2.0) / 2.0);
        this.width = (int) Math.ceil((width - 2.0) / 2.0);
        this.mazeHeight = height;
        this.mazeWidth = width;
        matrix = new Vertex[this.height][this.width];
        List<Vertex> vertices = new ArrayList<>();
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                Vertex vertex = new Vertex(row * this.width + col);
                matrix[row][col] = vertex;
                vertices.add(vertex);
            }
        }
        prim = new Prim(vertices);
    }

    private void execute() {
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                setEdges(new Point(row, col));
            }
        }
        prim.execute();
    }

    public Block[][] getMaze() {
        execute();
        Vertex[][] vertices = new Vertex[height * 2][width * 2];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                vertices[row * 2][col * 2] = matrix[row][col];
            }
        }
        Block[][] blocks = new Block[vertices.length][vertices[0].length];
        for (int row = 0; row < blocks.length; row++) {
            for (int col = 0; col < blocks[0].length; col++) {
                if (vertices[row][col] == null) {
                    blocks[row][col] = Block.WALL;
                } else {
                    blocks[row][col] = Block.PASS;
                }
            }
        }
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                showConnections(new Point(row, col), blocks);
            }
        }
        blocks = setupBorders(blocks);
        return blocks;
    }

    private Block[][] setupBorders(Block[][] blks) {
        Block[][] blocks = new Block[mazeHeight][mazeWidth];
        for (int row = 0; row < mazeHeight; row++) {
            for (int col = 0; col < mazeWidth; col++) {
                blocks[row][col] = Block.WALL;
            }
        }
        for (int row = 1; row < blks.length; row++) {
            if (blks[0].length - 1 >= 0) System.arraycopy(blks[row - 1], 0, blocks[row], 1, blks[0].length - 1);
        }
        Random random = new Random();
        int bound = mazeHeight % 2 == 0 ? mazeHeight - 3 : mazeHeight - 2;
        int entrance = random.nextInt(bound) + 1;
        int exit = random.nextInt(bound) + 1;
        for (int i = 0; i < 3; i++) {
            if (blocks[entrance][i] == Block.PASS) {
                break;
            }
            blocks[entrance][i] = Block.PASS;
        }
        for (int i = blocks[0].length - 1; i > blocks[0].length - 4; i--) {
            if (blocks[exit][i] == Block.PASS) {
                break;
            }
            blocks[exit][i] = Block.PASS;
        }
        return blocks;
    }

    private void showConnections(Point point, Block[][] blocks) {
        Vertex vertex = matrix[point.x][point.y];
        List<Vertex> connectedVertices = vertex.getConnectedVertices();
        for (Vertex v : connectedVertices) {
            for (int row = -1; row <= 1; row++) {
                for (int col = -1; col <= 1; col++) {
                    if (row == col || row + col == 0) {
                        continue;
                    }
                    Point p = new Point(row + point.x, col + point.y);
                    if (p.x < 0 || p.x > height - 1 || p.y < 0 || p.y > width - 1) {
                        continue;
                    }
                    Vertex v2 = matrix[p.x][p.y];
                    if (v.equals(v2)) {
                        p = new Point(p.x - point.x, p.y - point.y);
                        int r = point.x * 2 + p.x;
                        int c = point.y * 2 + p.y;
                        blocks[r][c] = Block.PASS;
                    }
                }
            }
        }
    }

    private void setEdges(Point point) {
        int maxWeight = 20;
        Random random = new Random();
        Vertex sourceVertex = matrix[point.x][point.y];
        Point p = new Point(point.x - 1, point.y);
        if (p.x >= 0) {
            Vertex vertex = matrix[p.x][p.y];
            Edge edge = new Edge(random.nextInt(maxWeight));
            sourceVertex.addEdge(vertex, edge);
            vertex.addEdge(sourceVertex, edge);
        }
        p = new Point(point.x, point.y - 1);
        if (p.y >= 0) {
            Vertex vertex = matrix[p.x][p.y];
            Edge edge = new Edge(random.nextInt(maxWeight));
            sourceVertex.addEdge(vertex, edge);
            vertex.addEdge(sourceVertex, edge);
        }
    }
}
