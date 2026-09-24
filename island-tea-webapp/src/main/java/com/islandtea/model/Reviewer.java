package com.islandtea.model;

import java.time.LocalDate;

/**
 * Abstract Reviewer: a Person who has also left a review. Sits in the
 * middle of the inheritance chain (Person -> Reviewer -> concrete types)
 * and implements {@link Reviewable}, so every concrete reviewer type is
 * polymorphically usable wherever a Reviewable is expected.
 */
public abstract class Reviewer extends Person implements Reviewable {

    private final double rating;
    private final String reviewText;
    private final LocalDate reviewDate;
    private int helpfulVotes;

    protected Reviewer(String name, double rating, String reviewText, LocalDate reviewDate) {
        super(name);
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("rating must be between 0 and 5");
        }
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
        this.helpfulVotes = 0;
    }

    @Override
    public double getRating() {
        return rating;
    }

    public String getRatingStars() {
        return "★".repeat((int) Math.round(rating));
    }

    @Override
    public String getReviewText() {
        return reviewText;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public int getHelpfulVotes() {
        return helpfulVotes;
    }

    public void markHelpful() {
        this.helpfulVotes++;
    }

    /**
     * Every concrete reviewer type defines its own weight in ranking
     * calculations (e.g. a Local Guide's review may be weighted higher).
     * This is a template-method style hook that ReviewService relies on
     * without knowing the concrete subtype - runtime polymorphism.
     */
    public abstract double getTrustWeight();
}
