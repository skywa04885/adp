import com.github.skywa04885.MyDijkstraPathFinder;
import com.github.skywa04885.MyGraph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("MyDijkstraPathFinder Tests")
class MyDijkstraPathFinderTests {
    @Test
    @DisplayName("Should compute shortest path in a simple linear graph")
    void shouldComputeShortestPathInSimpleLinearGraph() {
        final MyGraph<String> graph = new MyGraph<>();

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);

        final MyDijkstraPathFinder<String> dijkstra = new MyDijkstraPathFinder<>();
        dijkstra.traverse(graph, "A");

        final Optional<List<String>> path = dijkstra.getPath("C");

        assertThat(path).isPresent();
        assertThat(path.get()).containsExactly("A", "B", "C");
    }

    @Test
    @DisplayName("Should pick the cheapest of multiple possible paths")
    void shouldPickCheapestOfMultiplePaths() {
        final MyGraph<String> graph = new MyGraph<>();

        graph.addEdge("A", "B", 10);
        graph.addEdge("A", "C", 1);
        graph.addEdge("C", "B", 1);

        final MyDijkstraPathFinder<String> dijkstra = new MyDijkstraPathFinder<>();
        dijkstra.traverse(graph, "A");

        final Optional<List<String>> path = dijkstra.getPath("B");

        assertThat(path).isPresent();
        assertThat(path.get()).containsExactly("A", "C", "B");
    }

    @Test
    @DisplayName("Should return empty path when destination has no parent (no path found)")
    void shouldReturnEmptyPathWhenDestinationUnreachable() {
        final MyGraph<String> graph = new MyGraph<>();

        graph.addEdge("A", "B", 5);
        graph.addVertex("X"); // unreachable isolated vertex

        final MyDijkstraPathFinder<String> dijkstra = new MyDijkstraPathFinder<>();
        dijkstra.traverse(graph, "A");

        final Optional<List<String>> path = dijkstra.getPath("X");

        assertThat(path).isEmpty();
    }

    @Test
    @DisplayName("Should compute shortest path in graph with cycles")
    void shouldComputeShortestPathInGraphWithCycles() {
        final MyGraph<String> graph = new MyGraph<>();

        graph.addEdge("A", "B", 3);
        graph.addEdge("B", "C", 4);
        graph.addEdge("C", "A", 10); // cycle back
        graph.addEdge("B", "D", 1);
        graph.addEdge("D", "C", 1);

        final MyDijkstraPathFinder<String> dijkstra = new MyDijkstraPathFinder<>();
        dijkstra.traverse(graph, "A");

        final Optional<List<String>> path = dijkstra.getPath("C");

        assertThat(path).isPresent();
        assertThat(path.get()).containsExactly("A", "B", "D", "C");
    }

    @Test
    @DisplayName("Should handle single-vertex graph correctly")
    void shouldHandleSingleVertexGraphCorrectly() {
        final MyGraph<String> graph = new MyGraph<>();
        graph.addVertex("A");

        final MyDijkstraPathFinder<String> dijkstra = new MyDijkstraPathFinder<>();
        dijkstra.traverse(graph, "A");

        final Optional<List<String>> path = dijkstra.getPath("A");

        assertThat(path).isEmpty(); // no parent, no path
    }

    @Test
    @DisplayName("Should throw when origin vertex is not part of the graph")
    void shouldThrowWhenOriginVertexMissing() {
        final MyGraph<String> graph = new MyGraph<>();
        graph.addVertex("A");

        final MyDijkstraPathFinder<String> dijkstra = new MyDijkstraPathFinder<>();

        assertThatThrownBy(() -> dijkstra.traverse(graph, "X"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("origin vertex");
    }

    @Test
    @DisplayName("Should throw when requesting path for unknown vertex")
    void shouldThrowWhenRequestingPathForUnknownVertex() {
        final MyGraph<String> graph = new MyGraph<>();
        graph.addEdge("A", "B", 1);

        final MyDijkstraPathFinder<String> dijkstra = new MyDijkstraPathFinder<>();
        dijkstra.traverse(graph, "A");

        assertThatThrownBy(() -> dijkstra.getPath("Z"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Destination vertex");
    }

    @Test
    @DisplayName("Should ignore non-optimal edges and choose shortest path")
    void shouldIgnoreNonOptimalEdges() {
        final MyGraph<String> graph = new MyGraph<>();

        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 50);
        graph.addEdge("B", "C", 1); // cheaper path A → B → C

        final MyDijkstraPathFinder<String> dijkstra = new MyDijkstraPathFinder<>();
        dijkstra.traverse(graph, "A");

        final Optional<List<String>> path = dijkstra.getPath("C");

        assertThat(path).isPresent();
        assertThat(path.get()).containsExactly("A", "B", "C");
    }

    @Test
    @DisplayName("Should compute correct parent chain for multi-step path")
    void shouldComputeCorrectParentChain() {
        final MyGraph<String> graph = new MyGraph<>();

        graph.addEdge("Start", "A", 2);
        graph.addEdge("A", "B", 2);
        graph.addEdge("B", "Goal", 2);

        final MyDijkstraPathFinder<String> dijkstra = new MyDijkstraPathFinder<>();
        dijkstra.traverse(graph, "Start");

        final Optional<List<String>> path = dijkstra.getPath("Goal");

        assertThat(path).isPresent();
        assertThat(path.get()).containsExactly("Start", "A", "B", "Goal");
    }
}
