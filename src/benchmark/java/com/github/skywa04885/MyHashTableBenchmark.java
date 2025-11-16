package com.github.skywa04885;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
public class MyHashTableBenchmark {
    @State(Scope.Thread)
    public static class BenchmarkState {
        @Param({"1000", "10000", "50000"})
        public int operations;

        public MyHashTable<Integer, Integer> table;

        public int[] keys;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            table = new MyHashTable<>();
            random = new Random(123456);

            final int[] k = new int[operations];
            for (int i = 0; i < operations; i++) {
                k[i] = random.nextInt();
            }
            keys = k;

            for (final int key : k) {
                table.insert(key, key * 2);
            }
        }
    }

    @Benchmark
    public void insert(final BenchmarkState s) {
        final int key = s.random.nextInt();
        s.table.insert(key, key * 7);
    }

    @Benchmark
    public Optional<Integer> get(final BenchmarkState s) {
        final int key = s.keys[s.random.nextInt(s.operations)];
        return s.table.get(key);
    }

    @Benchmark
    public Optional<Integer> delete(final BenchmarkState s) {
        final int key = s.keys[s.random.nextInt(s.operations)];
        return s.table.delete(key);
    }

    public static void main(final String[] args) throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(MyHashTableBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
