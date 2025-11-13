package com.github.skywa04885;

/**
 * Generic interface for a custom list implementation for school.
 * @param <T> The type of value to be stored in the list.
 */
public interface MyList<T> {
    /**
     * Check if the list is empty.
     * @return Whether the list is empty or not.
     */
    boolean isEmpty();

    /**
     * Add all the given values to the list.
     * @param values The values to add to the list.
     */
    @SuppressWarnings("unchecked")
    void addAll(final T... values);

    /**
     * Get the size of the list.
     * @return The size of the list.
     */
    int size();

    /**
     * Get the value at the given index from the list.
     * @param index The index to get the value at.
     * @return The value at the given index.
     */
    T get(final int index);

    /**
     * Find the index of the first occurrence of the given value.
     * @param value The value to find the index of.
     * @return The index of the value, or -1 if not in the list.
     */
    int indexOf(final T value);

    /**
     * Add the given value to the list.
     * @param value The value to add to the list.
     */
    void add(final T value);

    /**
     * Remove the given value from the list.
     * @param value The value to be removed from the list.
     * @return Whether the value was removed or not.
     */
    boolean remove(final T value);

    /**
     * Turn the list into a string.
     * @return The string representation of the list.
     */
    @Override
    String toString();

    /**
     * Turn the list into an array (for testing purposes).
     * @param x An empty array used for reflection.
     * @return The array containing all the values from the list.
     */
    T[] toArray(final T[] x);
}
