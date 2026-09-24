package com.islandtea.model;

/**
 * Abstraction: any entity capable of producing a customer review must
 * expose a numeric rating and the text of the review. Implemented by
 * {@link Reviewer} and its subclasses to demonstrate interface-based
 * polymorphism (different reviewer types honour the same contract).
 */
public interface Reviewable {

    double getRating();

    String getReviewText();
}
