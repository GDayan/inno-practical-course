package org.innowise;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a factory that produces parts and provides them to factions.
 */
public class Factory {
    private final List<Part> storage = new ArrayList<>();
    private final Random random = new Random();

    /**
     * Produces a random number of parts (0 to 10) and stores them in the factory.
     * The storage is cleared before production. Prints the produced parts.
     */
    public synchronized void produceParts() {
        storage.clear();
        int partsCount = random.nextInt(11); // 0 to 10 parts
        for (int i = 0; i < partsCount; i++) {
            storage.add(Part.values()[random.nextInt(Part.values().length)]);
        }
        System.out.println("Factory produced: " + storage);
    }

    /**
     * Takes up to maxParts parts from the factory storage.
     *
     * @param maxParts the maximum number of parts to take
     * @return a list of parts taken from the factory
     */
    public synchronized List<Part> takeParts(int maxParts) {
        List<Part> taken = new ArrayList<>();
        int count = Math.min(maxParts, storage.size());
        for (int i = 0; i < count; i++) {
            taken.add(storage.remove(0));
        }
        return taken;
    }
}

