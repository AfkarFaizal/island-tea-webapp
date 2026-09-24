package com.islandtea.service;

import com.islandtea.datastructures.MenuTrie;
import com.islandtea.datastructures.SortUtils;
import com.islandtea.model.MenuCategory;
import com.islandtea.model.MenuItem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Owns the in-memory menu catalogue and all menu-related queries.
 * <p>
 * DSA usage:
 * - HashMap&lt;MenuCategory, List&lt;MenuItem&gt;&gt; for O(1) average
 *   category lookup instead of filtering the full list every request.
 * - MenuTrie for O(prefix length) autocomplete search.
 * - SortUtils.mergeSort / binarySearch for price-based queries.
 * - ArrayDeque used as a Stack to track "recently viewed" items (LIFO).
 */
@Service
public class MenuService {

    private final List<MenuItem> allItems = new ArrayList<>();
    private final Map<MenuCategory, List<MenuItem>> byCategory = new HashMap<>();
    private final MenuTrie searchIndex = new MenuTrie();
    private final Deque<MenuItem> recentlyViewedStack = new ArrayDeque<>();
    private static final int RECENTLY_VIEWED_CAPACITY = 5;

    public void addItem(MenuItem item) {
        allItems.add(item);
        byCategory.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item);
        searchIndex.insert(item.getName(), item.getId());
    }

    public List<MenuItem> getAllItems() {
        return Collections.unmodifiableList(allItems);
    }

    /** O(1) average lookup via HashMap, falls back to an empty list. */
    public List<MenuItem> getItemsByCategory(MenuCategory category) {
        return byCategory.getOrDefault(category, Collections.emptyList());
    }

    public Map<MenuCategory, List<MenuItem>> getMenuGroupedByCategory() {
        return Collections.unmodifiableMap(byCategory);
    }

    /** Items sorted cheapest-first using our own merge sort implementation. */
    public List<MenuItem> getItemsSortedByPrice() {
        return SortUtils.mergeSort(allItems,
            Comparator.comparingDouble(item -> Objects.requireNonNull(item, "Menu item").getPriceRs()));
    }

    /**
     * Finds an item by exact price using binary search over a
     * price-sorted copy of the catalogue - O(n log n) once, O(log n) per
     * lookup thereafter if the sorted list is cached (kept simple here).
     */
    public Optional<MenuItem> findItemByExactPrice(double price) {
        List<MenuItem> sorted = getItemsSortedByPrice();
        MenuItem probe = new MenuItem("__probe__", "", "", price, MenuCategory.SNACKS, false);
        int idx = SortUtils.binarySearch(sorted, probe,
            Comparator.comparingDouble(item -> Objects.requireNonNull(item, "Menu item").getPriceRs()));
        return idx == -1 ? Optional.empty() : Optional.of(sorted.get(idx));
    }

    /** Prefix / autocomplete search backed by the Trie. */
    public List<MenuItem> searchByPrefix(String prefix) {
        if (prefix == null || prefix.isBlank()) return List.of();
        Set<String> ids = new HashSet<>(searchIndex.searchByPrefix(prefix.trim()));
        return allItems.stream().filter(i -> ids.contains(i.getId())).collect(Collectors.toList());
    }

    /** Records a view and keeps only the most recent N (stack/LIFO behaviour). */
    public void recordView(MenuItem item) {
        recentlyViewedStack.push(item);
        while (recentlyViewedStack.size() > RECENTLY_VIEWED_CAPACITY) {
            recentlyViewedStack.removeLast();
        }
    }

    public List<MenuItem> getRecentlyViewed() {
        return new ArrayList<>(recentlyViewedStack);
    }

    public List<MenuItem> getSignatureItems() {
        return allItems.stream()
                .filter(item -> item != null && item.isSignature())
                .collect(Collectors.toList());
    }
}
