package maze.prim;

import java.util.*;
import java.util.stream.Collectors;

public class Vertex {
    private final int number;
    private final Map<Vertex, Edge> edges = new HashMap<>();
    private boolean isVisited = false;

    public Vertex(int number) {
        this.number = number;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void addEdge(Vertex vertex, Edge edge) {
        edges.put(vertex, edge);
    }

    public Pair nextMinimum() {
        Edge nextMinimum = new Edge(Integer.MAX_VALUE);
        Vertex nextVertex = this;
        for (Map.Entry<Vertex, Edge> pair : edges.entrySet()) {
            if (!pair.getKey().isVisited()) {
                if (!pair.getValue().isIncluded()) {
                    if (pair.getValue().getWeight() < nextMinimum.getWeight()) {
                        nextMinimum = pair.getValue();
                        nextVertex = pair.getKey();
                    }
                }
            }
        }
        return new Pair(nextVertex, nextMinimum);
    }

    public List<Vertex> getConnectedVertices() {
        return edges.entrySet().stream()
                .filter(entry -> entry.getValue().isIncluded()).map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Vertex " + number + " :" + edges.values().stream().filter(Edge::isIncluded).count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return number == vertex.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
