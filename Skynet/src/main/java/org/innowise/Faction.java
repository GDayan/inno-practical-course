package org.innowise;

import java.util.*;

/**
 * Represents a faction that collects parts from a factory and assembles robots.
 * Implements {@link Runnable} to allow concurrent execution.
 */
public class Faction implements Runnable {
    private final String name;
    private final Factory factory;
    private final Map<Part, Integer> inventory = new EnumMap<>(Part.class);
    private int robots = 0;

    /**
     * Constructs a new faction with the given name and factory.
     *
     * @param name    the name of the faction
     * @param factory the factory from which the faction will take parts
     */
    public Faction(String name, Factory factory) {
        this.name = name;
        this.factory = factory;
        for (Part p : Part.values()) {
            inventory.put(p, 0);
        }
    }

    /**
     * Returns the number of robots assembled by this faction.
     *
     * @return the number of robots
     */
    public int getRobots() {
        return robots;
    }

    /**
     * Returns the name of this faction.
     *
     * @return the faction name
     */
    public String getName() {
        return name;
    }

    /**
     * Attempts to assemble robots using the parts in the inventory.
     * The number of robots assembled is determined by the minimum count of available parts for each type.
     * Parts used for assembly are removed from the inventory.
     */
    private void assembleRobots() {
        int possibleRobots = Collections.min(inventory.values());
        if (possibleRobots > 0) {
            robots += possibleRobots;
            for (Part p : Part.values()) {
                inventory.put(p, inventory.get(p) - possibleRobots);
            }
        }
    }

    /**
     * Executes the faction's daily actions:
     * takes up to 5 parts from the factory, updates the inventory, and attempts to assemble robots.
     * Prints the parts collected.
     */
    @Override
    public void run() {
        List<Part> parts = factory.takeParts(5);
        for (Part p : parts) {
            inventory.put(p, inventory.get(p) + 1);
        }
        assembleRobots();
        System.out.println(name + " collected: " + parts);
    }
}
