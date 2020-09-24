package maze.prim;

public class Pair {
    private final Vertex vertex;
    private final Edge edge;

    public Pair(Vertex vertex, Edge edge) {
        this.vertex = vertex;
        this.edge = edge;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Edge getEdge() {
        return edge;
    }
}
