package com.islandtea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregate root describing the cafe itself. Built via the classic
 * Builder design pattern ({@link Cafe.Builder}) to keep the constructor
 * from becoming an unreadable wall of positional parameters, and to keep
 * the object immutable once constructed (encapsulation).
 */
public final class Cafe {

    private final String name;
    private final String tagline;
    private final String address;
    private final String plusCode;
    private final String phone;
    private final String priceRange;
    private final double googleRating;
    private final int googleReviewCount;
    private final List<String> features;

    private Cafe(Builder b) {
        this.name = b.name;
        this.tagline = b.tagline;
        this.address = b.address;
        this.plusCode = b.plusCode;
        this.phone = b.phone;
        this.priceRange = b.priceRange;
        this.googleRating = b.googleRating;
        this.googleReviewCount = b.googleReviewCount;
        this.features = Collections.unmodifiableList(new ArrayList<>(b.features));
    }

    public String getName() { return name; }
    public String getTagline() { return tagline; }
    public String getAddress() { return address; }
    public String getPlusCode() { return plusCode; }
    public String getPhone() { return phone; }
    public String getPriceRange() { return priceRange; }
    public double getGoogleRating() { return googleRating; }
    public int getGoogleReviewCount() { return googleReviewCount; }
    public List<String> getFeatures() { return features; }

    public static Builder builder() {
        return new Builder();
    }

    /** Builder pattern: fluent, readable construction of an immutable Cafe. */
    public static class Builder {
        private String name;
        private String tagline;
        private String address;
        private String plusCode;
        private String phone;
        private String priceRange;
        private double googleRating;
        private int googleReviewCount;
        private final List<String> features = new ArrayList<>();

        public Builder name(String name) { this.name = name; return this; }
        public Builder tagline(String tagline) { this.tagline = tagline; return this; }
        public Builder address(String address) { this.address = address; return this; }
        public Builder plusCode(String plusCode) { this.plusCode = plusCode; return this; }
        public Builder phone(String phone) { this.phone = phone; return this; }
        public Builder priceRange(String priceRange) { this.priceRange = priceRange; return this; }
        public Builder googleRating(double r) { this.googleRating = r; return this; }
        public Builder googleReviewCount(int c) { this.googleReviewCount = c; return this; }
        public Builder addFeature(String feature) { this.features.add(feature); return this; }

        public Cafe build() {
            if (name == null || name.isBlank()) {
                throw new IllegalStateException("Cafe name is required");
            }
            return new Cafe(this);
        }
    }
}
