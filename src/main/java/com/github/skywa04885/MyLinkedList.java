package com.github.skywa04885;

/**
 * My LinkedList implementation.
 * @param <T> The type of value stored in the linked list.
 */
public class MyLinkedList<T> implements MyList<T> {
    /**
     * Node that is present in the LinkedList.
     * @param <T> The type of value stored in the node.
     */
    private static class Node<T> {
        private Node<T> nextNode;
        private final T value;

        public Node(final T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    /**
     * Check if the linked list is empty.
     * @return Whether the linked list is empty or not.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Get the number of elements in the linked list.
     * @return The number of elements in the linked list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Find the index of the given value in the linked list.
     * @param value The value to find the index for.
     * @return The index of the value, -1 if not found.
     */
    @Override
    public int indexOf(final T value) {
        Node<T> node = head;

        int index = 0;
        while (node != null && !node.value.equals(value)) {
            ++index;
            node = node.nextNode;
        }

        if (node == null) {
            return -1;
        }

        return index;
    }

    /**
     * Get the value at the given index of the linked list.
     * @param index The index to get the value at.
     * @return The value at te index.
     */
    @Override
    public T get(final int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }

        Node<T> node = head;
        for (int i = 0; i < index; ++i) {
            node = node.nextNode;
        }

        return node.value;
    }

    /**
     * Add all given values to the linked list.
     * @param values The values to add to the linked list.
     */
    @Override
    @SafeVarargs
    public final void addAll(final T... values) {
        for (final T value : values) {
            add(value);
        }
    }

    /**
     * Add the given value to the linked list.
     * @param value The value to add to the linked list.
     */
    @Override
    public void add(final T value) {
        if (isEmpty()) {
            assert tail == null && head == null : "If the list is empty, both head and tail must be null";
            head = tail = new Node<>(value);
        } else {
            assert tail != null && head != null : "If the list is not empty, both head and tail must be non-null";
            tail.nextNode = new Node<>(value);
            tail = tail.nextNode;
        }

        ++size;
    }

    /**
     * Remove the given value from the linked list.
     * @param value The value to remove from the linked list.
     * @return Whether the value was removed or not.
     */
    @Override
    public boolean remove(final T value) {
        Node<T> previous = null;
        Node<T> current = head;

        while (current != null && !current.value.equals(value)) {
            previous = current;
            current = current.nextNode;
        }

        if (current == null) {
            return false;
        }

        if (!current.value.equals(value)) {
            return false;
        }

        if (previous != null) {
            previous.nextNode = current.nextNode;
        }

        if (head == current) {
            head = current.nextNode;
        }

        if (tail == current) {
            tail = previous;
        }

        --size;

        return true;
    }

    /**
     * Clear the linked list.
     */
    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Turn the linked list into a string for debugging.
     * @return The string version of the linked list.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("{");

        Node<T> node = head;
        while (node != null) {
            builder.append(node.value);
            if (node.nextNode != null) {
                builder.append(", ");
            }
            node = node.nextNode;
        }

        builder.append("}");

        return builder.toString();
    }

    /**
     * Turn the linked list into an ordinary array.
     * @param x The type of array the list should be turned into.
     * @return The array containing all values in the linked list.
     */
    @Override
    public T[] toArray(final T[] x) {
        @SuppressWarnings("unchecked")
        final T[] array = (T[]) java.lang.reflect.Array.newInstance(
                x.getClass().getComponentType(), size
        );

        int i = 0;

        Node<T> node = head;
        while (node != null) {
            array[i++] = node.value;
            node = node.nextNode;
        }

        return array;
    }
}
