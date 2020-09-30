package maze.pathfinding;

import maze.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Pathfinder {
    private final List<Node> open = new ArrayList<>();
    private final List<Node> closed = new ArrayList<>();
    private final Node[][] matrix;
    private Node start;
    private Node end;

    public Pathfinder(Block[][] matrix) {
        this.matrix = new Node[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                Node node = new Node(new Point(row, col));
                node.setBlock(matrix[row][col]);
                this.matrix[row][col] = node;
            }
        }
        setStartEnd();
    }

    public Block[][] findPath() {
        open.add(start);
        while (true) {
            //open.sort(Comparator.reverseOrder());
            Collections.sort(open);
            Node curr = open.get(0);
            open.remove(curr);
            closed.add(curr);
            if (curr == end) {
                break;
            }
            checkNeighbours(curr);
        }
        updatePathBlocks();
        Block[][] blocks = new Block[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                blocks[row][col] = matrix[row][col].getBlock();
            }
        }
        return blocks;
    }

    private void setStartEnd() {
        for (Node[] nodes : matrix) {
            Node node = nodes[0];
            if (node.getBlock() == Block.PASS) {
                start = node;
            }
            node = nodes[matrix[0].length - 1];
            if (node.getBlock() == Block.PASS) {
                end = node;
            }
        }
        start.setgCost(0);
        start.sethCost(start.getPos().distance(end.getPos()));
        end.setgCost(start.getPos().distance(end.getPos()));
        end.sethCost(0);
    }

    private void checkNeighbours(Node node) {
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                if (row == col || row + col == 0) {
                    continue;
                }
                Point p1 = node.getPos();
                Point p2 = new Point(row + p1.x, col + p1.y);
                if (p2.x > matrix.length || p2.y > matrix[0].length || p2.x < 0 || p2.y < 0) {
                    continue;
                }
                Node neighbour = matrix[p2.x][p2.y];
                if (!neighbour.isTraversable() || closed.contains(neighbour)) {
                    continue;
                }
                neighbour.setBlock(Block.CHECKED);
                double gCost = p1.distance(p2) + node.getgCost();
                double hCost = distanceToEnd(neighbour);//end.getPos().distance(p2);
                double fCost = gCost + hCost;
                if (open.contains(neighbour)) {
                    if (neighbour.getfCost() > fCost) {
                        neighbour.setParent(node);
                        neighbour.setgCost(gCost);
                        neighbour.sethCost(hCost);
                        neighbour.setfCost(fCost);
                    }
                } else {
                    neighbour.setParent(node);
                    neighbour.setgCost(gCost);
                    neighbour.sethCost(hCost);
                    neighbour.setfCost(fCost);
                    open.add(neighbour);
                }
            }
        }
    }

    private void updatePathBlocks() {
        end.setBlock(Block.PATH);
        Node parent = end.getParent();
        do {
            parent.setBlock(Block.PATH);
            parent = parent.getParent();
        } while (parent != null);
    }

    private double distanceToEnd(Node node) {
        Point endPoint = end.getPos();
        Point p = node.getPos();
        return Math.abs(endPoint.x - p.x) + Math.abs(endPoint.y - p.y);
    }
}
