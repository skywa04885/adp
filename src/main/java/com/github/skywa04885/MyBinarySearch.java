package com.github.skywa04885;

public final class MyBinarySearch {
    private MyBinarySearch() {
    }

    public static <T extends Comparable<T>> int binarySearch(
            final T[] array,
            final T value
    ) {
        int low = 0;
        int high = array.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;

            int comparison = array[mid].compareTo(value);

            if (comparison == 0) {
                return mid;
            } else if (comparison > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }
}
