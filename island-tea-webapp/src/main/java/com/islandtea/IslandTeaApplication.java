package com.islandtea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Island Tea Co. & Ceylon Coffee Club web application.
 * <p>
 * This project intentionally favors hand-written, explicit code (over
 * annotation magic like Lombok) so that the OOP structure - inheritance,
 * polymorphism, encapsulation, abstraction - and the DSA components
 * (custom linked list, trie, merge sort, binary search, heap/priority
 * queue, stack) remain visible and easy to follow.
 */
@SpringBootApplication
public class IslandTeaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IslandTeaApplication.class, args);
    }
}
