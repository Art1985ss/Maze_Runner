package maze.prim;

public class Edge {
    private final int weight;
    private boolean isIncluded = false;

    public Edge(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isIncluded() {
        return isIncluded;
    }

    public void setIncluded(boolean included) {
        isIncluded = included;
    }

    @Override
    public String toString() {
        return weight + "";
    }
}
