package com.islandtea.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A hand-rolled generic singly linked list, used to hold the cafe's
 * photo/gallery timeline in chronological order.
 * <p>
 * Implemented from scratch (rather than using java.util.LinkedList) to
 * demonstrate node-based structures, pointer manipulation, and iterator
 * design - core DSA fundamentals.
 * <p>
 * Time complexity: addLast O(1) (tail pointer kept), addFirst O(1),
 * get(index) O(n), size O(1).
 */
public class CustomLinkedList<T> implements Iterable<T> {

    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T value) { this.value = value; }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public void addLast(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void addFirst(T value) {
        Node<T> node = new Node<>(value);
        node.next = head;
        head = node;
        if (tail == null) tail = node;
        size++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        Node<T> cur = head;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur.value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) throw new NoSuchElementException();
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}
