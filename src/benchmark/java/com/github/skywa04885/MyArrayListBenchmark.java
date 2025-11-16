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
public class MyArrayListBenchmark {
    private static final int INDEX_OF_LIST_SIZE = 1000;
    private static final int REMOVE_LIST_SIZE = 1000;
    private static final int CLEAR_LIST_SIZE = 1000;

    @Benchmark
    public void constructWithDefaultCapacity(final Blackhole blackhole) {
        blackhole.consume(new MyArrayList<>(Integer.class));
    }

    @Benchmark
    public void constructWithCustomCapacity(final Blackhole blackhole) {
        blackhole.consume(new MyArrayList<>(Integer.class, 100));
    }

    @State(Scope.Thread)
    public static class AddWithoutGrowingState {
        private final MyArrayList<Integer> list = new MyArrayList<>(Integer.class);

        @TearDown(Level.Invocation)
        public void tearDown() {
            list.clear();
        }
    }

    @Benchmark
    public void addWithoutGrowing(final AddWithoutGrowingState state) {
        state.list.add(1);
    }

    @State(Scope.Thread)
    public static class AddWithGrowState {
        private MyArrayList<Integer> list;

        @Setup(Level.Invocation)
        public void setup() {
            list =  new MyArrayList<>(Integer.class, 10);;
            list.addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        }
    }

    @Benchmark
    public void addWithGrow(final AddWithGrowState state) {
        state.list.add(11);
    }

    @State(Scope.Benchmark)
    public static class IndexOfState {
        private final MyArrayList<Integer> list = new MyArrayList<>(Integer.class, INDEX_OF_LIST_SIZE);

        public IndexOfState() {
            for (int i = 0; i < INDEX_OF_LIST_SIZE; ++i) {
                list.add(i);
            }
        }
    }

    @Benchmark
    public void indexOfAtStart(final IndexOfState state, final Blackhole blackhole) {
        blackhole.consume(state.list.indexOf(0));
    }

    @Benchmark
    public void indexOfAtEnd(final IndexOfState state, final Blackhole blackhole) {
        blackhole.consume(state.list.indexOf(INDEX_OF_LIST_SIZE - 1));
    }

    @State(Scope.Thread)
    public static class RemoveState {
        private MyArrayList<Integer> list;

        @Setup(Level.Invocation)
        public void setup() {
            list =  new MyArrayList<>(Integer.class, REMOVE_LIST_SIZE);;
            for (int i = 0; i < REMOVE_LIST_SIZE; ++i) {
                list.add(i);
            }
        }
    }

    @Benchmark
    public void removeAtStart(final RemoveState state, final Blackhole blackhole) {
        blackhole.consume(state.list.remove(0));
    }

    @Benchmark
    public void removeAtEnd(final RemoveState state, final Blackhole blackhole) {
        blackhole.consume(state.list.remove(REMOVE_LIST_SIZE - 1));
    }

    @State(Scope.Thread)
    public static class ClearState {
        private MyArrayList<Integer> list;

        @Setup(Level.Invocation)
        public void setup() {
            list =  new MyArrayList<>(Integer.class, CLEAR_LIST_SIZE);;
            for (int i = 0; i < CLEAR_LIST_SIZE; ++i) {
                list.add(i);
            }
        }
    }

    @Benchmark
    public void clear(final ClearState state) {
        state.list.clear();
    }

    public static void main(final String[] args) throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(MyArrayListBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(options).run();
    }
}
