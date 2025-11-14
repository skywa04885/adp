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

    /**
     * Sort the given unsorted array using merge sort.
     * @param unsorted The array that should be sorted.
     * @return A sorted copy of the unsorted array.
     * @param <T> The type of values in the array that should be sorted.
     */
    @Override
    public <T extends Comparable<T>> T[] sort(final T[] unsorted) {
        final T[] array = Arrays.copyOf(unsorted, unsorted.length);

        mergeSort(array, 0, array.length - 1);

        return array;
    }

    private <T extends Comparable<T>> void mergeSort(
            final T[] array,
            final int left,
            final int right
    ) {
        if (left >= right) {
            return;
        }

        final int mid = (left + right) >>> 1;

        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);

        merge(array, left, mid, right);
    }

    private <T extends Comparable<T>> void merge(
            final T[] array,
            final int left,
            final int mid,
            final int right
    ) {
        final int leftSize = mid - left + 1;
        final int rightSize = right - mid;

        final T[] leftArr = Arrays.copyOfRange(array, left, mid + 1);
        final T[] rightArr = Arrays.copyOfRange(array, mid + 1, right + 1);

        int i = 0, j = 0, k = left;

        while (i < leftSize && j < rightSize) {
            if (leftArr[i].compareTo(rightArr[j]) <= 0) {
                array[k++] = leftArr[i++];
            } else {
                array[k++] = rightArr[j++];
            }
        }

        while (i < leftSize) {
            array[k++] = leftArr[i++];
        }

        while (j < rightSize) {
            array[k++] = rightArr[j++];
        }
    }
}
