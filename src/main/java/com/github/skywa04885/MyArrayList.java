package com.github.skywa04885;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

public class MyArrayList<T> implements MyList<T> {
    private final Class<T> valueClass;
    private T[] array;
    private int size;

    public MyArrayList(final Class<T> valueClass) {
        this(valueClass, 10);
    }

    public MyArrayList(final Class<T> valueClass, final int initialCapacity) {
        this.valueClass = valueClass;
        this.array = allocate(valueClass, initialCapacity);
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] allocate(final Class<T> valueClass, final int capacity) {
        return (T[]) Array.newInstance(valueClass, capacity);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @SafeVarargs
    public final void addAll(final T... values) {
        for (final T value : values) {
            add(value);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }

        return array[index];
    }

    @Override
    public int indexOf(final T value) {
        for (int i = 0; i < size; ++i) {
            if (Objects.equals(array[i], value)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void add(final T value) {
        if (array.length == size) {
            grow();
        }

        assert array.length > size;

        array[size++] = value;
    }

    @Override
    public boolean remove(final T value) {
        int index = indexOf(value);
        if (index == -1) {
            return false;
        }

        if(index < size - 1) {
            System.arraycopy(array, index + 1, array, index, size - index - 1);
        }

        --size;

        return true;
    }

    private void grow() {
        // The new capacity of the array list should at least be ten, or twice the old capacity if it's not too small'
        final int newCapacity = array.length < 10 ? 10 : array.length + (array.length >> 1);
        final T[] newArray = allocate(valueClass, newCapacity);
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

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

    @Override
    public T[] toArray(final T[] x) {
        return Arrays.copyOf(array, size);
    }
}
