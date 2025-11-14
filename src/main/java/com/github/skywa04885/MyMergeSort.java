package com.github.skywa04885;

import java.util.Arrays;

/**
 * My implementation of the merge sort algorithm.
 */
public final class MyMergeSort implements MySorter {
    private static final MyMergeSort INSTANCE = new MyMergeSort();

    public static MyMergeSort getInstance() {
        return INSTANCE;
    }

    private MyMergeSort() {
    }

    @Override
    public <T extends Comparable<T>> T[] sort(final T[] unsorted) {
        final T[] array = Arrays.copyOf(unsorted, unsorted.length);

        return array;
    }
}
