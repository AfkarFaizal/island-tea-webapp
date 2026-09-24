package com.islandtea.service;

import com.islandtea.datastructures.CustomLinkedList;
import com.islandtea.model.Reviewer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Owns the collection of reviews and review-related analytics.
 * <p>
 * DSA usage:
 * - PriorityQueue (binary heap) to efficiently extract the top-N
 *   highest-rated / most-trusted reviews in O(n log k) rather than a
 *   full O(n log n) sort when only the top few are needed.
 * - CustomLinkedList to hold reviews in chronological (insertion) order
 *   for the "review timeline" view.
 * <p>
 * OOP usage: works entirely against the abstract {@link Reviewer} type
 * and the {@code getTrustWeight()} / {@code getRating()} hooks, so it
 * never needs to know whether a review came from a LocalGuideReviewer or
 * a RegularReviewer - runtime polymorphism in action.
 */
@Service
public class ReviewService {

    private final CustomLinkedList<Reviewer> timeline = new CustomLinkedList<>();

    public void addReview(Reviewer reviewer) {
        timeline.addLast(reviewer);
    }

    public List<Reviewer> getTimeline() {
        List<Reviewer> list = new ArrayList<>();
        for (Reviewer r : timeline) {
            list.add(r);
        }
        return list;
    }

    public double getAverageRating() {
        List<Reviewer> all = getTimeline();
        if (all.isEmpty()) return 0.0;
        double sum = 0;
        for (Reviewer r : all) sum += r.getRating();
        return sum / all.size();
    }

    /**
     * Returns the top-N reviews ranked by (rating * trustWeight) using a
     * min-heap of bounded size N - classic "top-K" heap pattern.
     */
    public List<Reviewer> getTopReviews(int n) {
        Comparator<Reviewer> byWeightedScore =
                Comparator.comparingDouble(r -> r.getRating() * r.getTrustWeight());

        PriorityQueue<Reviewer> minHeap = new PriorityQueue<>(byWeightedScore);
        for (Reviewer r : getTimeline()) {
            minHeap.offer(r);
            if (minHeap.size() > n) {
                minHeap.poll(); // evict the weakest of the current top-N
            }
        }

        List<Reviewer> top = new ArrayList<>(minHeap);
        top.sort(byWeightedScore.reversed());
        return top;
    }
}
