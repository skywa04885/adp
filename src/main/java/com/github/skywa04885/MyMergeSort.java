package com.github.skywa04885;

import java.util.Arrays;

public class MyMergeSort implements MySortAlgorithm {
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

        final int mid = left + (right - left) / 2;
        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);

    }

    private <T extends Comparable<T>> void merge(
            final T[] array,
            final int left,
            final int mid,
            final int right
    ) {
        final int n1 = mid - left + 1;
        final int n2 = right - mid;


    }
}
