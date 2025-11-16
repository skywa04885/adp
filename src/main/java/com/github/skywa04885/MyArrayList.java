package com.github.skywa04885;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

/**
 * My implementation of an ArrayList.
 * @param <T> The type of value stored in the ArrayList.
 */
public class MyArrayList<T> implements MyList<T> {
    private final Class<T> valueClass;
    private T[] array;
    private int size;

    /**
     * Construct a new list that has the default capacity and stores values of the given class.
     * @param valueClass The class of values.
     */
    public MyArrayList(final Class<T> valueClass) {
        this(valueClass, 10);
    }

    /**
     * Construct a new list with the given value class and initial capacity.
     * @param valueClass The class of the values.
     * @param initialCapacity The initial capacity.
     */
    public MyArrayList(final Class<T> valueClass, final int initialCapacity) {
        this.valueClass = valueClass;
        this.array = allocate(valueClass, initialCapacity);
        this.size = 0;
    }

    /**
     * Allocate a new array of the given class and capacity.
     * @param valueClass The class of value.
     * @param capacity The capacity to allocate.
     * @return The allocated array of the given capacity.
     * @param <T> The type of value being stored in the array.
     */
    @SuppressWarnings("unchecked")
    private static <T> T[] allocate(final Class<T> valueClass, final int capacity) {
        return (T[]) Array.newInstance(valueClass, capacity);
    }

    /**
     * Check if the list is empty.
     * @return Whether the list is empty.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Add all the given values to the list.
     * @param values The values to add to the list.
     */
    @Override
    @SafeVarargs
    public final void addAll(final T... values) {
        for (final T value : values) {
            add(value);
        }
    }

    /**
     * Get the size of the list.
     * @return The size of the list (number of elements).
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Get the value at the given index from the list.
     * @param index The index to get the value at.
     * @return The found value.
     */
    @Override
    public T get(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }

        return array[index];
    }

    /**
     * Find the index of the given value.
     * @param value The value to find the index for.
     * @return The index of the value, -1 if not found.
     */
    @Override
    public int indexOf(final T value) {
        for (int i = 0; i < size; ++i) {
            if (Objects.equals(array[i], value)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Add the given value to the list, growing if needed.
     * @param value The value to add to the array.
     */
    @Override
    public void add(final T value) {
        // Grow if there is no capacity left.
        if (array.length == size) {
            grow();
        }

        assert array.length > size;

        array[size++] = value;
    }

    /**
     * Remove the given value from the list.
     * @param value The value to remove.
     * @return Whether the value was removed or not.
     */
    @Override
    public boolean remove(final T value) {
        // Find the index of the element that should be removed, if it's not present, simply return.
        final int index = indexOf(value);
        if (index == -1) {
            return false;
        }

        // If the element that is removed is not the last element, then shift the entire array to the left,
        //  (which will override the value, hence null not needed), however, if it's the end, then it has to
        //  be set to null since it's not touched.
        if(index < size - 1) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
            array[size - 1] = null; // Remove the last element after shift.
        } else {
            array[index] = null;
        }

        // Decrease the used size of the array.
        --size;

        // Return true since it was removed.
        return true;
    }

    /**
     * Grow the array.
     */
    private void grow() {
        // The new capacity of the array list should at least be ten, or twice the old capacity if it's not too small'
        final int newCapacity = array.length < 10 ? 10 : array.length + (array.length >> 1);
        final T[] newArray = allocate(valueClass, newCapacity);
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    /**
     * Remove all values from the list.
     */
    @Override
    public void clear() {
        Arrays.fill(array, null);
        size = 0;
    }

    /**
     * Get the string representation of the list.
     * @return The string representation of the list.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("{");

        for (int i = 0; i < size; ++i) {
            builder.append(array[i]);
            if (i < size - 1) {
                builder.append(", ");
            }
        }

        builder.append("}");
        return builder.toString();
    }

    /**
     * Turn the list into an actual array.
     * @param x The type of array it should be turned into.
     * @return The array version of the current list.
     */
    @Override
    public T[] toArray(final T[] x) {
        return Arrays.copyOf(array, size);
    }
}
