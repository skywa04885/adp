package com.github.skywa04885;

public class MyLinkedList<T> implements MyList<T> {
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

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

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

    @Override
    @SafeVarargs
    public final void addAll(final T... values) {
        for (final T value : values) {
            add(value);
        }
    }

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
