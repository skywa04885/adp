package com.github.skywa04885;

import javax.swing.text.html.Option;
import java.util.*;

/**
 * My graph implementation.
 * @param <TVertex> The type of vertex in the graph.
 */
public class MyGraph<TVertex> {
    /**
     * Representation of a weighted edge in the graph.
     * @param <TVertex> The type of vertex.
     */
    public static class Edge<TVertex> {
        private final int weight;
        private final TVertex to;

        /**
         * Construct a new edge that has the given weight and points towards the given to-vertex.
         * @param weight The weight of the edge.
         * @param to The vertex that the edge should point to.
         */
        public Edge(final int weight, final TVertex to) {
            assert weight >= 0; // Dijkstra's algorithm only works with non-negative weights.

            this.weight = weight;
            this.to = to;
        }

        /**
         * Get the weight of the edge.
         * @return The weight of the edge.
         */
        public int getWeight() {
            return weight;
        }

        /**
         * Get the vertex to which the edge is drawn.
         * @return The vertex.
         */
        public TVertex getTo() {
            return to;
        }

        /**
         * Calculate the hash code of the edge, needed for keeping the set.
         * @return The hash code of the edge.
         */
        @Override
        public int hashCode() {
            return Objects.hash(weight, to);
        }

        /**
         * Check if the edge is equal to the given object.
         * @param object The object to compare to.
         * @return Whether the given object equals the current one.
         */
        @Override
        public boolean equals(final Object object) {
            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            final Edge<?> edge = (Edge<?>) object;
            return weight == edge.weight && Objects.equals(to, edge.to);
        }
    }

    /**
     * The registry of vertices together with their edges.
     */
    private final Map<TVertex, Set<Edge<TVertex>>> vertices = new HashMap<>();

    /**
     * Get a set of all vertices.
     * @return The set of all vertices.
     */
    public Set<TVertex> getVertices() {
        return vertices.keySet();
    }

    /**
     * Add the given vertex to the vertices.
     * @param vertex The vertex to add.
     */
    public void addVertex(final TVertex vertex) {
        vertices.computeIfAbsent(vertex, v -> new HashSet<>());
    }

    /**
     * Add an edge with the given weight between the given vertices.
     * @param fromVertex The vertex to draw the edge from.
     * @param toVertex The vertex to draw the edge to.
     * @param weight The weight of the edge.
     */
    public void addEdge(
            final TVertex fromVertex,
            final TVertex toVertex,
            final int weight
    ) {
        vertices.computeIfAbsent(fromVertex, v -> new HashSet<>())
                .add(new Edge<>(weight, toVertex));
    }

    /**
     * Get all the edges of the given vertex.
     * @param vertex The vertex for which we should get the edges.
     * @return The edges for the vertex, if the vertex is in the graph.
     */
    public Optional<Set<Edge<TVertex>>> getEdgesOf(final TVertex vertex) {
        return Optional.ofNullable(vertices.get(vertex));
    }
}
