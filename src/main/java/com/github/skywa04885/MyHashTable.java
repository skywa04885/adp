package com.github.skywa04885;

import java.lang.reflect.Array;
import java.util.Optional;

/**
 * My implementation of a hash table.
 * @param <TKey> The type of key in the hash table.
 * @param <TValue> The type of value in the hash table.
 */
public class MyHashTable<TKey, TValue> {
    private Node<TKey, TValue>[] nodes;
    private int size;

    /**
     * Create a new hash table with the default number of initial buckets.
     */
    public MyHashTable() {
        this(16);
    }

    /**
     * Create a new hash table with the given number of initial buckets.
     * @param buckets The initial number of buckets.
     */
    @SuppressWarnings("unchecked")
    public MyHashTable(final int buckets) {
        this.nodes = (Node<TKey, TValue>[]) Array.newInstance(Node.class, buckets);
        this.size = 0;
    }

    /**
     * A node in the hash table.
     * @param <TKey> The key of the node.
     * @param <TValue> The value of the node.
     */
    private static class Node<TKey, TValue> {
        private final TKey key;
        private TValue value;
        private Node<TKey, TValue> next;

        public Node(final TKey key, final TValue value) {
            this.key = key;
            this.value = value;
        }

        public TKey getKey() {
            return key;
        }

        public TValue getValue() {
            return value;
        }

        public void setValue(TValue value) {
            this.value = value;
        }

        public Node<TKey, TValue> getNext() {
            return next;
        }

        public void setNext(final Node<TKey, TValue> next) {
            this.next = next;
        }
    }

    /**
     * Get the load factor of the hash table.
     *
     * @return The load factor of the hash table.
     */
    public double loadFactor() {
        return (double) size / (double) nodes.length;
    }

    /**
     * Insert the given key/ value pair into the hash table.
     *
     * @param key   The key to insert the value under.
     * @param value The value to insert under the key.
     */
    public void insert(final TKey key, final TValue value) {
        // Insert the key/ value pair into the nodes.
        insert(key, value, nodes);

        // Increment the size to indicate the new insertion.
        ++size;

        // Perform the rehashing.
        rehash();
    }

    /**
     * Get the value associated with the given key.
     *
     * @param key The key to get the value for.
     * @return The optional found value.
     */
    public Optional<TValue> get(final TKey key) {
        // Calculate the hash belonging to the key.
        final int hash = hash(key);

        // Get the node associated with that hash.
        Node<TKey, TValue> node = nodes[hash];

        // If the node is null, then the key is not in the hash table, return empty optional.
        if (node == null) return Optional.empty();

        // Move through the chain, trying to find the exact match for the key.
        while (node != null) {
            // Check if the current node matches the key exactly, if so, return it as the value.
            if (node.getKey().equals(key)) {
                return Optional.of(node.getValue());
            }

            // Since the current key was no match, proceed to the next node in the chain.
            node = node.getNext();
        }

        // Since no exact match was found, return empty optional.
        return Optional.empty();
    }

    /**
     * Delete the entry with the given key from the hash table.
     *
     * @param key The key that should be deleted.
     * @return The value associated with the key, if it was deleted.
     */
    public Optional<TValue> delete(final TKey key) {
        // Calculate the hash belonging to the key.
        final int hash = hash(key);

        // Get the node associated with that hash.
        Node<TKey, TValue> node = nodes[hash];

        // If the node is not found, then there is no entry with the specified key, hence return empty optional.
        if (node == null) return Optional.empty();

        // If the current node (the head) is the one that should be removed, shift the head to it's next value.
        if (node.getKey().equals(key)) {
            nodes[hash] = node.getNext();
            --size; // Decrease the size of the structure.
            return Optional.of(node.getValue());
        }

        // Traverse the chain, trying to find the specific one that should be deleted.
        while (node.getNext() != null) {
            // Get the next node that might be of interest.
            final Node<TKey, TValue> nextNode = node.getNext();

            // If the next node is the one we're looking for, then simply take the next node out of the chain, by
            //  setting the next value of the current node, to the next node of the next node.
            if (nextNode.getKey().equals(key)) {
                node.setNext(nextNode.getNext());
                --size; // Decrease the size of the structure.
                return Optional.of(nextNode.getValue());
            }

            // Traverse to the next node.
            node = nextNode;
        }

        // Since no match was found, return nothing.
        return Optional.empty();
    }

    /**
     * Rehash the table if it's load factor is too high.
     */
    @SuppressWarnings({"unchecked", "ForLoopReplaceableByForEach"})
    private void rehash() {
        // If the load factor is less than 0.75, then rehashing is not needed, simply return.
        if (loadFactor() < 0.75) return;

        // Allocate a new nodes array, having double the size of the last one.
        final Node<TKey, TValue>[] newNodes = (Node<TKey, TValue>[])
                Array.newInstance(Node.class, nodes.length * 2);

        // Copy all the existing key/ value pairs into the new nodes array.
        Node<TKey, TValue> node;
        for (int nodeIndex = 0; nodeIndex < nodes.length; ++nodeIndex) {
            node = nodes[nodeIndex];
            while (node != null) {
                insert(node.getKey(), node.getValue(), newNodes);
                node = node.getNext();
            }
        }

        // Replace the internal nodes with the new nodes.
        nodes = newNodes;
    }

    private int hash(final TKey key) {
        return hash(key, nodes.length);
    }

    /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// Static implementations of the actual logic (need to be separated from instance due to rehashing)
    /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Insert the given value under the given key in the nodes.
     *
     * @param key      The key under which the value needs to be inserted.
     * @param value    The value that should be inserted with the given key.
     * @param nodes    The nodes in which the insertion needs to happen.
     * @param <TKey>   The type of key.
     * @param <TValue> The type of value.
     */
    private static <TKey, TValue> void insert(
            final TKey key,
            final TValue value,
            final Node<TKey, TValue>[] nodes
    ) {
        // Compute the hash of the key that we're inserting.
        final int hash = hash(key, nodes.length);

        // Retrieve the node that is present under the hash (possible start of chain).
        Node<TKey, TValue> node = nodes[hash];

        // Traverse the chain for possible existing instances of the key. If a match is found, then the value
        //  of that node will be replaced with the newly provided value.
        while (node != null) {
            if (node.getKey().equals(key)) {
                node.setValue(value);
                return;
            }
            node = node.getNext();
        }

        // Since no existing value has been replaced, create a new node for the key and make it the new head,
        //  whilst putting it next to the current head (prevents extra chain-traversal).
        final Node<TKey, TValue> newNode = new Node<>(key, value);
        newNode.setNext(nodes[hash]);
        nodes[hash] = newNode;
    }

    /**
     * Calculate the hash of the given key, knowing the given number of buckets.
     *
     * @param key     The key to hash.
     * @param buckets The available buckets.
     * @param <TKey>  The type of key that's being hashed.
     * @return The hash based on the buckets.
     */
    private static <TKey> int hash(
            final TKey key,
            final int buckets
    ) {
        // Math.abs is needed because the hash code can get negative.
        return Math.abs(key.hashCode()) % buckets;
    }
}
