package com.islandtea.service;

import com.islandtea.model.Cafe;
import org.springframework.stereotype.Component;

/**
 * Holds the single Cafe instance for the app.
 * <p>
 * Spring already manages this class as a singleton bean, which is the
 * idiomatic Spring way to implement the Singleton pattern (rather than a
 * private-constructor-plus-static-getInstance()) - one bean, one shared
 * cafe record, injected wherever it's needed.
 */
@Component
public class CafeInfoProvider {

    private Cafe cafe;

    public Cafe getCafe() {
        if (cafe == null) {
            throw new IllegalStateException("Cafe has not been initialised yet");
        }
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }
}
