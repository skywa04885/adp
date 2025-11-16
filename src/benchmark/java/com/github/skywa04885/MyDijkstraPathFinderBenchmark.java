package com.github.skywa04885;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
@Measurement(iterations = 8, time = 1)
@Warmup(iterations = 4, time = 1)
public class MyDijkstraPathFinderBenchmark {
    @State(Scope.Thread)
    public static class BenchmarkState {
        @Param({"500", "2000", "5000"})
        public int size;

        @Param({"SPARSE", "MEDIUM", "DENSE"})
        public String density;

        public MyGraph<Integer> graph;
        public Integer[] vertices;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            random = new Random(987654321);
            graph = new MyGraph<>();
            vertices = new Integer[size];

            for (int i = 0; i < size; i++) {
                vertices[i] = i;
            }

            for (final int x : vertices) {
                graph.addVertex(x);
            }

            final int edgesPerNode = switch (density) {
                case "SPARSE" -> 3;
                case "MEDIUM" -> 10;
                default -> 30;
            };

            for (final int from : vertices) {
                for (int j = 0; j < edgesPerNode; j++) {
                    final int to = random.nextInt(size);
                    final int w = random.nextInt(20) + 1;
                    graph.addEdge(from, to, w);
                }
            }
        }
    }

    @Benchmark
    public void traverse(final BenchmarkState state) {
        final int origin = state.vertices[state.random.nextInt(state.size)];

        final MyDijkstraPathFinder<Integer> finder = new MyDijkstraPathFinder<>();

        finder.traverse(state.graph, origin);
    }

    @Benchmark
    public Optional<List<Integer>> getPath(final BenchmarkState state) {
        final int origin = state.vertices[state.random.nextInt(state.size)];
        final int dest = state.vertices[state.random.nextInt(state.size)];

        final MyDijkstraPathFinder<Integer> finder = new MyDijkstraPathFinder<>();

        finder.traverse(state.graph, origin);

        return finder.getPath(dest);
    }

    public static void main(final String[] args) throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(MyDijkstraPathFinderBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
