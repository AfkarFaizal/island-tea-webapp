package com.islandtea.datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Hand-written generic Merge Sort and Binary Search, used instead of
 * Collections.sort/Collections.binarySearch to keep the DSA reasoning
 * explicit and inspectable.
 */
public final class SortUtils {

    private SortUtils() { }

    /** Merge sort: O(n log n) time, O(n) extra space, stable. */
    public static <T> List<T> mergeSort(List<T> input, Comparator<T> comparator) {
        if (input.size() <= 1) {
            return new ArrayList<>(input);
        }
        int mid = input.size() / 2;
        List<T> left = mergeSort(input.subList(0, mid), comparator);
        List<T> right = mergeSort(input.subList(mid, input.size()), comparator);
        return merge(left, right, comparator);
    }

    private static <T> List<T> merge(List<T> left, List<T> right, Comparator<T> comparator) {
        List<T> merged = new ArrayList<>(left.size() + right.size());
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));
        return merged;
    }

    /**
     * Binary search over a list that MUST already be sorted according to
     * the same comparator. Returns the index of a matching element, or
     * -1 if not found. O(log n) time.
     */
    public static <T> int binarySearch(List<T> sortedInput, T target, Comparator<T> comparator) {
        int low = 0, high = sortedInput.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = comparator.compare(sortedInput.get(mid), target);
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
