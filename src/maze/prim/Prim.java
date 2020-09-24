package maze.prim;

import java.util.List;
import java.util.stream.Collectors;

public class Prim {
    private final List<Vertex> graph;

    public Prim(List<Vertex> graph) {
        this.graph = graph;
    }

    public void execute() {
        if (graph.size() > 0) {
            graph.get(0).setVisited(true);
        }
        while (isDisconnected()) {
            Edge nextMinimum = new Edge(Integer.MAX_VALUE);
            Vertex nextVertex = graph.get(0);
            for (Vertex vertex : graph) {
                if (vertex.isVisited()) {
                    Pair candidate = vertex.nextMinimum();
                    if (candidate.getEdge().getWeight() < nextMinimum.getWeight()) {
                        nextMinimum = candidate.getEdge();
                        nextVertex = candidate.getVertex();
                    }
                }
            }
            nextMinimum.setIncluded(true);
            nextVertex.setVisited(true);
        }
    }

    private boolean isDisconnected() {
        return graph.stream().anyMatch(vertex -> !vertex.isVisited());
    }

    @Override
    public String toString() {
        return graph.stream().map(String::valueOf).collect(Collectors.joining("\n"));
    }
}
