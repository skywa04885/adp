package com.github.skywa04885;

/**
 * My implementation of the binary search algorithm.
 */
public final class MyBinarySearch {
    private MyBinarySearch() {
    }

    /**
     * Perform a binary search on the given array, looking for the specified value, returning it's index if found.
     * @param array The array in which the value should be searched for (must be sorted).
     * @param value The value of which the index should be found.
     * @return The index of the value if found, otherwise -1;
     * @param <T> The type of values in the array.
     */
    public static <T extends Comparable<T>> int binarySearch(
            final T[] array,
            final T value
    ) {
        // Set the current search space to the entire array.
        int low = 0;
        int high = array.length - 1;

        // Stay in the loop as long as there's still a search space.
        while (low < high) {
            // Calculate the middle of the current range (the value we'll check).
            final int mid = low + (high - low) / 2;

            // Compare the current middle to the value we're looking for.
            final int comparison = array[mid].compareTo(value);

            // If the values are equal, return the current middle index, otherwise, reduce the search
            //  range depending on whether or not the value we're looking or is before or after the current middle.
            if (comparison == 0) {
                return mid;
            } else if (comparison > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        // Return -1 because nothing was found.
        return -1;
    }
}
