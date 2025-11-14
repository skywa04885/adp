package com.github.skywa04885;

import java.util.ArrayList;
import java.util.Optional;

/**
 * My own implementation of the priority queue.
 * @param <T> The type of values present in the priority queue.
 */
public class MyPriorityQueue<T> {
    /**
     * A node in the priority queue, basically a box for the value with added priority.
     * @param <T> The type of value that's boxed.
     */
    @SuppressWarnings("ClassCanBeRecord")
    private static class Node<T> {
        private final int priority;
        private final T value;

        /**
         * Construct a new node with the given priority and value.
         * @param priority The priority of the node.
         * @param value The value of the node.
         */
        public Node(final int priority, final T value) {
            this.priority = priority;
            this.value = value;
        }

        /**
         * Get the priority of the node.
         * @return The priority of the node.
         */
        public int getPriority() {
            return priority;
        }

        /**
         * Get the value of the node.
         * @return The value of the node.
         */
        public T getValue() {
            return value;
        }
    }

    /**
     * The array list storing the binary heap.
     */
    private final ArrayList<Node<T>> arrayList = new ArrayList<>();

    /**
     * Enqueue the given value onto the queue assigned the given priority.
     * @param priority The priority assigned to the enqueued value.
     * @param value The value to enqueue with the given priority.
     */
    public void enqueue(final int priority, final T value) {
        // Create the node based on the given priority and value.
        final Node<T> node = new Node<>(priority, value);

        // Get the current size of the array list before the insertion, since that will be the
        //  index at which the node is inserted, then insert it.
        final int nodeIndex = arrayList.size();
        arrayList.add(node);

        // Begin shifting up of the node based on its priority.
        shiftUp(nodeIndex);
    }

    /**
     * Perform the shift up operation on the node with the given index.
     * @param nodeIndex The index of the node that should be shifted up.
     */
    private void shiftUp(final int nodeIndex) {
        // Check if the node is the root, if so, then stop bubbling up, there's nothing for it
        //  to be compared to, otherwise get the node that should be bubbled up.
        if (nodeIndex == 0) return;
        final Node<T> node = arrayList.get(nodeIndex);

        // Get the parent of the node that should be bubbled up.
        final int parentIndex = getParentIndex(nodeIndex);
        final Node<T> parent = arrayList.get(parentIndex);

        // If the priority of the node that should be bubbled up is not lower than the
        //  parent, then stop bubbling up, since the node belongs where it is right now.
        if (node.getPriority() >= parent.getPriority()) return;

        // Swap the node with its parent.
        arrayList.set(parentIndex, node);
        arrayList.set(nodeIndex, parent);

        // Continue the shifting up with the parent index, because that's where the node
        //  has been placed now.
        shiftUp(parentIndex);
    }

    /**
     * Dequeues an element from the priority queue.
     * @return The dequeued element, if there.
     */
    public Optional<T> dequeue() {
        // If the queue is empty, return an empty optional.
        if (arrayList.isEmpty()) return Optional.empty();

        // Get the root node and the last node.
        final Node<T> rootNode = arrayList.getFirst();
        final Node<T> lastNode = arrayList.getLast();

        // Make the last node the new root and remove it from the end of the array.
        arrayList.set(0, lastNode);
        arrayList.removeLast();

        // Shift down the new root if the tree is not empty.
        if (!arrayList.isEmpty()) shiftDown(0);

        // Return the value of the root node, since it's the dequeued element.
        return Optional.of(rootNode.getValue());
    }

    /**
     * Perform the shift down operation on the node with the given index.
     * @param nodeIndex The index of the node that should be shifted down.
     */
    private void shiftDown(final int nodeIndex) {
        // Get the node under question.
        final Node<T> node = arrayList.get(nodeIndex);

        // Get the left child of the node under question.
        final int leftChildIndex = getLeftChildIndex(nodeIndex);
        final Node<T> leftChild = leftChildIndex < arrayList.size() ? arrayList.get(leftChildIndex) : null;

        // Get the right child of the node under question.
        final int rightChildIndex = getRightChildIndex(nodeIndex);
        final Node<T> rightChild = rightChildIndex < arrayList.size() ? arrayList.get(rightChildIndex) : null;

        // If both the left and right children are not present in the structure, simply
        //  return since we're at the bottom of the tree.
        if (leftChild == null && rightChild == null) return;

        // If the right child is null, then we're almost at the deepest level. Hence one final check for
        //  swapping should be done, and when that one is over, just return since there's not anything to
        //  transcend deeper into.
        if (rightChild == null) {
            if (leftChild.getPriority() < node.getPriority()) {
                arrayList.set(leftChildIndex, node);
                arrayList.set(nodeIndex, leftChild);
            }

            return; // Due to the tree being complete, we're now at the bottom, no shifting down possible.
        }

        // At this point, neither the left nor right child can be null.
        assert leftChild != null;

        // If the right child has a lower priority than the left one, swap the node under question
        //   with the right child. Otherwise, swap it with the left one. Then shift down from there.
        if (rightChild.getPriority() < leftChild.getPriority()) {
            // If the node has a lower priority than the right child (which is already the smallest of the two),
            //  then it means that the current node is in the right position, and we shouldn't descent further.
            if (node.getPriority() <= rightChild.getPriority()) {
                return;
            }

            arrayList.set(rightChildIndex, node);
            arrayList.set(nodeIndex, rightChild);

            shiftDown(rightChildIndex);
        } else {
            // If the node has a lower priority that the left child (which is already either the smallest of the two,
            //  unless they're the same), then the current node is in the right position.
            if (node.getPriority() <= leftChild.getPriority()) {
                return;
            }

            arrayList.set(leftChildIndex, node);
            arrayList.set(nodeIndex, leftChild);

            shiftDown(leftChildIndex);
        }
    }

    /**
     * Calculate the index of the parent node that has the given index.
     * @param index The index of the node to get the parent form.
     * @return The index of the parent.
     */
    private static int getParentIndex(final int index) {
        return (index - 1) / 2;
    }

    /**
     * Calculate the index of the left child of the node at the given index.
     * @param index The index of the node to find the left child for.
     * @return The index of the left child.
     */
    private static int getLeftChildIndex(final int index) {
        return 2 * index + 1;
    }

    /**
     * Calculate the index of the right child of the node at the given index.
     * @param index The index of the node to find the right child for.
     * @return The index of the right child.
     */
    private static int getRightChildIndex(final int index) {
        return 2 * index + 2;
    }
}
