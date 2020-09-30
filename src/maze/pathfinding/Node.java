package maze.pathfinding;

import maze.Block;

import java.awt.*;
import java.util.Objects;

public class Node implements Comparable<Node> {
    private final Point pos;
    private Block block;
    private double gCost = Double.MAX_VALUE;
    private double hCost = Double.MAX_VALUE;
    private double fCost;
    private Node parent;


    public Node(Point pos) {
        this.pos = pos;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public double getgCost() {
        return gCost;
    }

    public void setgCost(double gCost) {
        this.gCost = gCost;
        fCost = this.gCost + this.hCost;
    }

    public double gethCost() {
        return hCost;
    }

    public void sethCost(double hCost) {
        this.hCost = hCost;
        fCost = this.gCost + this.hCost;
    }

    public double getfCost() {
        return fCost;
    }

    public void setfCost(double fCost) {
        this.fCost = fCost;
    }

    public Point getPos() {
        return pos;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

    public boolean isTraversable() {
        return !block.equals(Block.WALL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return pos.equals(node.pos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos);
    }

    @Override
    public int compareTo(Node node) {
        int result = Double.compare(fCost, node.fCost);
        if (result == 0) {
            result = Double.compare(hCost, node.hCost);
        }
        if (result == 0) {
            return Double.compare(gCost, node.gCost);
        }
        return result;
    }
}
