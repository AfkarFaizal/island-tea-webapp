package com.islandtea.model;

import java.util.Objects;

/**
 * Abstract base class representing any human participant in the system.
 * Encapsulates the fields common to every kind of person (currently just
 * reviewers, but the hierarchy leaves room for staff, owners, etc.).
 *
 * Demonstrates: abstraction, encapsulation, Comparable for natural
 * ordering (alphabetical by name).
 */
public abstract class Person implements Comparable<Person> {

    private final String name;

    protected Person(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /** Subclasses must describe what kind of person they are. */
    public abstract String getRole();

    @Override
    public int compareTo(Person other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return name.equalsIgnoreCase(person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
