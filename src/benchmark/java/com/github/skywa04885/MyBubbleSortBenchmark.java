package com.github.skywa04885;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(2)
public class MyBubbleSortBenchmark {
    @State(Scope.Thread)
    public static class BenchmarkState {
        @Param({"1000", "10000", "50000"})
        int size;

        Integer[] unsorted;

        @Setup(Level.Invocation)
        public void setup() {
            unsorted = generateRandomArray(size);
        }

        private Integer[] generateRandomArray(final int size) {
            final Random random = new Random(12345);
            final Integer[] arr = new Integer[size];

            for (int i = 0; i < size; i++) {
                arr[i] = random.nextInt();
            }

            return arr;
        }
    }

    @Benchmark
    public Integer[] sort(final BenchmarkState state) {
        return MyBubbleSort.getInstance().sort(state.unsorted);
    }

    public static void main(final String[] args) throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(MyBubbleSortBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
