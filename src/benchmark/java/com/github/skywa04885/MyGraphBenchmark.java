package com.github.skywa04885;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
public class MyGraphBenchmark {
    @State(Scope.Thread)
    public static class BenchmarkState {
        @Param({"1000", "5000", "10000"})
        public int size;

        public MyGraph<Integer> graph;
        public Integer[] vertices;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            graph = new MyGraph<>();
            vertices = new Integer[size];
            random = new Random(123456);

            for (int i = 0; i < size; i++) {
                vertices[i] = i;
            }

            for (final int x : vertices) {
                graph.addVertex(x);
            }
        }
    }

    @Benchmark
    public void addVertex(final BenchmarkState state) {
        final int value = state.random.nextInt();

        state.graph.addVertex(value);
    }

    @Benchmark
    public void addEdge(final BenchmarkState state) {
        final int from = state.vertices[state.random.nextInt(state.size)];
        final int to = state.vertices[state.random.nextInt(state.size)];

        state.graph.addEdge(from, to, 1);
    }

    @Benchmark
    public Optional<Set<MyGraph.Edge<Integer>>> getEdgesOf(final BenchmarkState state) {
        final int vertices = state.vertices[state.random.nextInt(state.size)];

        return state.graph.getEdgesOf(vertices);
    }

    public static void main(final String[] args) throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(MyGraphBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
