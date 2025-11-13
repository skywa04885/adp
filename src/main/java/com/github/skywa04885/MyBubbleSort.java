package com.github.skywa04885;

import java.util.Arrays;

/**
 * My implementation of the bubble sort algorithm.
 */
public final class MyBubbleSort implements MySortAlgorithm {
    private static final MyBubbleSort INSTANCE = new MyBubbleSort();

    public static MyBubbleSort getInstance() {
        return INSTANCE;
    }

    private MyBubbleSort() {
    }

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] unsorted) {
        final T[] array = Arrays.copyOf(unsorted, unsorted.length);

        return array;
    }
}
