package com.islandtea.datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Trie (prefix tree) mapping lower-cased menu item names to their IDs,
 * powering fast autocomplete/search-as-you-type on the menu page.
 * <p>
 * insert: O(k) where k = word length.
 * search by prefix: O(p + m) where p = prefix length, m = number of
 * matches collected - far better than scanning every item with
 * String.startsWith for a large menu.
 */
public class MenuTrie {

    private static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        List<String> itemIdsEndingHere = new ArrayList<>();
        boolean isWordEnd;
    }

    private final TrieNode root = new TrieNode();

    public void insert(String word, String itemId) {
        TrieNode node = root;
        String lower = word.toLowerCase();
        for (char c : lower.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        node.isWordEnd = true;
        node.itemIdsEndingHere.add(itemId);
    }

    /** Returns item IDs for every inserted word that starts with the given prefix. */
    public List<String> searchByPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode node = root;
        String lower = prefix.toLowerCase();
        for (char c : lower.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return results; // no matches
            }
        }
        collectAll(node, results);
        return results;
    }

    private void collectAll(TrieNode node, List<String> results) {
        if (node.isWordEnd) {
            results.addAll(node.itemIdsEndingHere);
        }
        for (TrieNode child : node.children.values()) {
            collectAll(child, results);
        }
    }
}
