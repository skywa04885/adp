package com.github.skywa04885;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
public class MyPriorityQueueBenchmark {
    @State(Scope.Thread)
    public static class EnqueueState {
        MyPriorityQueue<Integer> queue;

        @Param({"1", "100", "1000", "10000", "100000"})
        public int size;

        @Setup(Level.Invocation)
        public void setup() {
            queue = new MyPriorityQueue<>();

            for (int i = 0; i < size; ++i) {
                queue.enqueue(i % 10, i);
            }
        }
    }

    @Benchmark
    public void enqueue(final EnqueueState state) {
        state.queue.enqueue(5, 1);
    }

    @State(Scope.Thread)
    public static class DequeueState {
        MyPriorityQueue<Integer> queue;

        @Param({"1", "100", "1000", "10000", "100000"})
        public int size;

        @Setup(Level.Invocation)
        public void setup() {
            queue = new MyPriorityQueue<>();
            for (int i = 0; i < size; ++i) {
                queue.enqueue(i % 10, i);
            }
        }
    }

    @Benchmark
    public void benchmarkDequeue(final DequeueState state, final Blackhole blackhole) {
        blackhole.consume(state.queue.dequeue());
    }

    public static void main(final String[] args) throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(MyPriorityQueueBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
