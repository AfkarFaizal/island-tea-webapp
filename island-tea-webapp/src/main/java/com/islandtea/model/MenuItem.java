package com.islandtea.model;

import java.util.Objects;

/**
 * A single menu item. Implements Comparable&lt;MenuItem&gt; by price so
 * that lists of items can be sorted (merge sort) and searched
 * (binary search) purely by price - see {@code datastructures.SortUtils}.
 */
public class MenuItem implements Comparable<MenuItem> {

    private final String id;
    private final String name;
    private final String description;
    private final double priceRs;
    private final MenuCategory category;
    private final boolean signature;

    public MenuItem(String id, String name, String description, double priceRs,
                     MenuCategory category, boolean signature) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priceRs = priceRs;
        this.category = category;
        this.signature = signature;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPriceRs() { return priceRs; }
    public MenuCategory getCategory() { return category; }
    public boolean isSignature() { return signature; }

    @Override
    public int compareTo(MenuItem other) {
        return Double.compare(this.priceRs, other.priceRs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem menuItem)) return false;
        return id.equals(menuItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
