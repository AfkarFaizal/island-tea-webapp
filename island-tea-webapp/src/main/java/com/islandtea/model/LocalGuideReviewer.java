package com.islandtea.model;

import java.time.LocalDate;

/**
 * A Google "Local Guide" style reviewer - has a review count and photo
 * count, and is trusted more heavily when ranking reviews.
 */
public class LocalGuideReviewer extends Reviewer {

    private final int totalReviewCount;
    private final int totalPhotoCount;

    public LocalGuideReviewer(String name, double rating, String reviewText,
                               LocalDate reviewDate, int totalReviewCount, int totalPhotoCount) {
        super(name, rating, reviewText, reviewDate);
        this.totalReviewCount = totalReviewCount;
        this.totalPhotoCount = totalPhotoCount;
    }

    public int getTotalReviewCount() {
        return totalReviewCount;
    }

    public int getTotalPhotoCount() {
        return totalPhotoCount;
    }

    @Override
    public String getRole() {
        return "Local Guide";
    }

    @Override
    public double getTrustWeight() {
        // More prolific guides get a modest boost, capped at 2x.
        return Math.min(2.0, 1.0 + (totalReviewCount / 500.0));
    }
}
