package com.github.skywa04885;

public interface MySortAlgorithm {
    <T extends Comparable<T>> T[] sort(final T[] unsorted);
}
