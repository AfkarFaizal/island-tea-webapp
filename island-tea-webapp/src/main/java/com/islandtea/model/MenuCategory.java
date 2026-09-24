package com.islandtea.model;

/** Categories for menu items - used as HashMap keys in MenuService (O(1) grouping). */
public enum MenuCategory {
    CEYLON_TEA("Ceylon Tea"),
    COFFEE("Coffee"),
    BREAKFAST("Breakfast"),
    SNACKS("Snacks & Bites"),
    DESSERTS("Desserts");

    private final String displayName;

    MenuCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
