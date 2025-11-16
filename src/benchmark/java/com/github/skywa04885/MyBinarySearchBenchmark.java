package com.github.skywa04885;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
public class MyBinarySearchBenchmark {
    @State(Scope.Thread)
    public static class BenchmarkState {
        @Param({"1000", "10000", "100000"})
        public int size;

        public Integer[] array;
        public Integer[] searchValues;
        public Random random;

        @Setup(Level.Trial)
        public void setup() {
            random = new Random(123456);

            final Integer[] a = new Integer[size];
            for (int i = 0; i < size; i++) {
                a[i] = random.nextInt();
            }

            Arrays.sort(a);
            array = a;

            final Integer[] s = new Integer[size];
            for (int i = 0; i < size; i++) {
                s[i] = a[random.nextInt(size)];
            }
            searchValues = s;
        }
    }

    @Benchmark
    public int search(final BenchmarkState state) {
        final int idx = state.random.nextInt(state.size);

        return MyBinarySearch.binarySearch(state.array, state.searchValues[idx]);
    }

    public static void main(final String[] args) throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(MyBinarySearchBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
