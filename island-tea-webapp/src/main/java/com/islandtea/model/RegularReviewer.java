package com.islandtea.model;

import java.time.LocalDate;

/** A standard customer review with no Local Guide status. */
public class RegularReviewer extends Reviewer {

    public RegularReviewer(String name, double rating, String reviewText, LocalDate reviewDate) {
        super(name, rating, reviewText, reviewDate);
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    @Override
    public double getTrustWeight() {
        return 1.0;
    }
}
