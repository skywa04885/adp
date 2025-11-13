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
        final int n = array.length;

        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    swap(array, j, j + 1);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }

        return array;
    }

    /**
     * Swap two elements in the given array.
     * @param array The array in which the (i)th and (j)th elements should be swapped.
     * @param i The index where the (j)th value should be put.
     * @param j The index where the (i)th value should be put.
     * @param <T> The type of values in the array.
     */
    private static <T> void swap(final T[] array, final int i, final int j) {
        final T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
