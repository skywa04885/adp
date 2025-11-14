package com.github.skywa04885;

public interface MySorter {
    <T extends Comparable<T>> T[] sort(final T[] unsorted);
}
