import com.github.skywa04885.MyGraph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DisplayName("MyGraph Tests")
class MyGraphTests {
    @Test
    @DisplayName("Should store vertex when adding one")
    void shouldStoreVertexWhenAddingOne() {
        final MyGraph<String> graph = new MyGraph<>();
        graph.addVertex("A");

        assertThat(graph.getVertices())
                .containsExactly("A");
    }

    @Test
    @DisplayName("Should return empty set when no vertices added")
    void shouldReturnEmptySetWhenNoVerticesAdded() {
        final MyGraph<String> graph = new MyGraph<>();
        assertThat(graph.getVertices()).isEmpty();
    }

    @Test
    @DisplayName("Should create vertices when adding an edge between missing vertices")
    void shouldCreateVerticesWhenAddingEdge() {
        final MyGraph<String> graph = new MyGraph<>();
        graph.addEdge("A", "B", 5);

        assertThat(graph.getVertices())
                .containsExactly("A", "B");

        final Optional<Set<MyGraph.Edge<String>>> edges = graph.getEdgesOf("A");

        assertThat(edges).isPresent();
        assertThat(edges.get()).hasSize(1);

        final MyGraph.Edge<String> edge = edges.get().iterator().next();
        assertThat(edge.getWeight()).isEqualTo(5);
        assertThat(edge.getTo()).isEqualTo("B");
    }

    @Test
    @DisplayName("Should return empty Optional for unknown vertex")
    void shouldReturnEmptyOptionalForUnknownVertex() {
        final MyGraph<String> graph = new MyGraph<>();

        assertThat(graph.getEdgesOf("X")).isEmpty();
    }

    @Test
    @DisplayName("Should return empty edge set for a newly added vertex")
    void shouldReturnEmptyEdgeSetForNewVertex() {
        final MyGraph<String> graph = new MyGraph<>();
        graph.addVertex("A");

        final Optional<Set<MyGraph.Edge<String>>> edges = graph.getEdgesOf("A");

        assertThat(edges).isPresent();
        assertThat(edges.get()).isEmpty();
    }

    @Test
    @DisplayName("Should store multiple edges correctly")
    void shouldStoreMultipleEdgesCorrectly() {
        final MyGraph<String> graph = new MyGraph<>();
        graph.addVertex("A");

        graph.addEdge("A", "B", 3);
        graph.addEdge("A", "C", 7);

        final Set<MyGraph.Edge<String>> edges = graph.getEdgesOf("A").orElseThrow();

        assertThat(edges)
                .hasSize(2)
                .extracting(MyGraph.Edge::getTo)
                .containsExactlyInAnyOrder("B", "C");
    }

    @Test
    @DisplayName("Should store duplicate edges only once")
    void shouldStoreDuplicateEdgesOnlyOnce() {
        final MyGraph<String> graph = new MyGraph<>();

        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "B", 5);

        final Set<MyGraph.Edge<String>> edges = graph.getEdgesOf("A").orElseThrow();

        assertThat(edges).hasSize(1);
    }

    @Test
    @DisplayName("Should treat edges with equal fields as equal")
    void shouldTreatEqualEdgesAsEqual() {
        final MyGraph.Edge<String> e1 = new MyGraph.Edge<>(10, "X");
        final MyGraph.Edge<String> e2 = new MyGraph.Edge<>(10, "X");
        final MyGraph.Edge<String> e3 = new MyGraph.Edge<>(20, "X");
        final MyGraph.Edge<String> e4 = new MyGraph.Edge<>(10, "Y");

        assertThat(e1)
                .isEqualTo(e2)
                .hasSameHashCodeAs(e2);

        assertThat(e1).isNotEqualTo(e3);
        assertThat(e1).isNotEqualTo(e4);
    }

    @Test
    @DisplayName("Should maintain independent edge sets for different vertices")
    void shouldMaintainIndependentEdgeSetsForDifferentVertices() {
        final MyGraph<String> graph = new MyGraph<>();

        graph.addEdge("A", "B", 1);
        graph.addEdge("C", "D", 2);

        final Set<MyGraph.Edge<String>> edgesOfA = graph.getEdgesOf("A").orElseThrow();
        final Set<MyGraph.Edge<String>> edgesOfC = graph.getEdgesOf("C").orElseThrow();

        assertThat(edgesOfA)
                .extracting(MyGraph.Edge::getTo)
                .containsExactly("B");

        assertThat(edgesOfC)
                .extracting(MyGraph.Edge::getTo)
                .containsExactly("D");
    }

    @Test
    @DisplayName("Should create edge with correct weight and target")
    void shouldCreateEdgeWithCorrectWeightAndTarget() {
        final MyGraph<String> graph = new MyGraph<>();
        graph.addEdge("A", "X", 42);

        final MyGraph.Edge<String> edge =
                graph.getEdgesOf("A")
                        .orElseThrow()
                        .iterator()
                        .next();

        assertThat(edge.getWeight()).isEqualTo(42);
        assertThat(edge.getTo()).isEqualTo("X");
    }

}
