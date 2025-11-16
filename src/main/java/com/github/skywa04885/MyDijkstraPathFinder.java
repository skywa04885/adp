package com.github.skywa04885;

import java.util.*;

public class MyDijkstraPathFinder<TVertex> {
    /**
     * Wrapper of a map to represent the table used in Dijkstra's algorithm.
     *
     * @param <TVertex> The type of vertices used.
     */
    private static class Table<TVertex> {
        /**
         * An entry in the table used by Dijkstra's algorithm.
         *
         * @param <TVertex> The type of vertex.
         */
        private static class Entry<TVertex> {
            private final TVertex vertex;
            private int distance;
            private TVertex parent;

            /**
             * Construct a new empty table entry.
             *
             * @param vertex    The vertex for which this entry is.
             * @param <TVertex> The type of vertex.
             * @return The newly constructed empty entry.
             */
            public static <TVertex> Entry<TVertex> empty(final TVertex vertex) {
                return new Entry<>(vertex, Integer.MAX_VALUE, null);
            }

            /**
             * Construct a new table entry with the given distance and parent.
             *
             * @param vertex   The vertex for which this entry is.
             * @param distance The distance.
             * @param parent   The parent.
             */
            public Entry(final TVertex vertex, final int distance, final TVertex parent) {
                this.vertex = vertex;
                this.distance = distance;
                this.parent = parent;
            }

            public TVertex getVertex() {
                return vertex;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public TVertex getParent() {
                return parent;
            }

            public void setParent(TVertex parent) {
                this.parent = parent;
            }
        }

        /**
         * The map containing all the table entries.
         */
        private final Map<TVertex, Entry<TVertex>> tableEntries = new HashMap<>();

        /**
         * Initialize the table for the given graph, creating all empty entries.
         *
         * @param graph The graph for which the table should be initialized.
         */
        public void initializeFor(final MyGraph<TVertex> graph) {
            graph.getVertices().forEach(vertex -> {
                tableEntries.put(vertex, Entry.empty(vertex));
            });
        }

        /**
         * Get the table entry for the given vertex.
         *
         * @param vertex The vertex for which the table entry should be retrieved.
         * @return The table entry if it exists.
         */
        public Optional<Entry<TVertex>> get(final TVertex vertex) {
            return Optional.ofNullable(tableEntries.get(vertex));
        }

        /**
         * Find the open vertex with the shortest distance.
         *
         * @param openVertices The open vertices for which the one with the shortest distance should be found.
         * @return If exists, the open vertex with the shortest distance.
         */
        public Optional<Entry<TVertex>> findOpenVertexWithShortestDistance(final Set<TVertex> openVertices) {
            // Find the entry with the min distance from the open vertices.
            return openVertices.stream()
                    .map(this::get)
                    .flatMap(Optional::stream)
                    .min(Comparator.comparingInt(Entry::getDistance));
        }
    }

    /**
     * The table used by the algorithm.
     */
    private final Table<TVertex> table = new Table<>();

    /**
     * Traverse the entire graph starting with the given origin vertex.
     *
     * @param graph        The graph to traverse.
     * @param originVertex The vertex to start the traversal for.
     */
    public void traverse(
            final MyGraph<TVertex> graph,
            final TVertex originVertex
    ) {
        // Initialize the table for the graph.
        table.initializeFor(graph);

        // Set the distance to the origin vertex to zero.
        table.get(originVertex)
                .orElseThrow(() -> new IllegalArgumentException("Could not find the origin vertex in the table"))
                .setDistance(0);

        // Create the sets containing all the open and closed vertices.
        final Set<TVertex> openVertices = new HashSet<>(graph.getVertices());
        final Set<TVertex> closedVertices = new HashSet<>();

        // Keep iterating as long as here are open nodes.
        while (!openVertices.isEmpty()) {
            // Get the table entry with the shortest distance to it.
            final Table.Entry<TVertex> fromEntry = table
                    .findOpenVertexWithShortestDistance(openVertices)
                    .orElseThrow(() -> new IllegalStateException("Could not find shortest distance entry " +
                            "even though there are still open vertices, should never happen."));


            // Get the current open vertex that we're looking at.
            final TVertex fromVertex = fromEntry.getVertex();

            // Get all the edges attached to the current vertex.
            final Set<MyGraph.Edge<TVertex>> edges = graph
                    .getEdgesOf(fromVertex)
                    .orElseThrow(() -> new IllegalStateException("Could not get set of edges for vertex with " +
                            "shortest distance to it, possibly mutated during run of algorithm"));

            // Iterate over all the edges so they can be handled.
            edges.forEach(edge -> {
                // Get the to-vertex and the weight of the edge.
                final TVertex toVertex = edge.getTo();
                final int weight = edge.getWeight();

                // If the to-vertex is already closed, simply ignore it.
                if (closedVertices.contains(toVertex)) {
                    return;
                }

                // Get the table entry of the to-vertex.
                final Table.Entry<TVertex> toEntry = table
                        .get(toVertex)
                        .orElseThrow(() -> new IllegalStateException("Could not find the table entry of the " +
                                "to-vertex, possibly mutated during operation"));

                // If the distance sum of the current from-entry and the weight of the edge exceeds
                //  the already present distance, then this will not be the next shortest distance, hence do
                //  not update.
                if (fromEntry.getDistance() + weight > toEntry.getDistance()) {
                    return;
                }

                // Since the new distance is shorter than the one already present, make the from-vertex its new
                //  parent to make the optimal path.
                toEntry.setParent(fromVertex);
                toEntry.setDistance(fromEntry.getDistance() + weight);
            });

            // Move the currently visited vertex from the open vertices set to the closed vertices set
            //  to mark it as done.
            openVertices.remove(fromVertex);
            closedVertices.add(fromVertex);
        }
    }
}
