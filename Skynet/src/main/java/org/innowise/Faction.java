package org.innowise;

import java.util.*;

public class Faction implements Runnable {
    private final String name;
    private final Factory factory;
    private final Map<Part, Integer> inventory = new EnumMap<>(Part.class);
    private int robots = 0;
    private static final int MAX_PARTS_PER_NIGHT = 5;

    private static final Map<Part, Integer> ROBOT_REQUIREMENTS = Map.of(
            Part.HEAD, 1,
            Part.TORSO, 1,
            Part.HAND, 2,
            Part.FEET, 2
    );

    public Faction(String name, Factory factory) {
        this.name = name;
        this.factory = factory;
        for (Part p : Part.values()) {
            inventory.put(p, 0);
        }
    }

    public int getRobots() {
        return robots;
    }

    public String getName() {
        return name;
    }

    public Map<Part, Integer> getInventory() {
        return new EnumMap<>(inventory);
    }

    /**
     * Correct robot assembly logic
     */
    private void assembleRobots() {
        int maxPossibleRobots = Integer.MAX_VALUE;

        for (Map.Entry<Part, Integer> requirement : ROBOT_REQUIREMENTS.entrySet()) {
            Part part = requirement.getKey();
            int required = requirement.getValue();
            int available = inventory.get(part);

            maxPossibleRobots = Math.min(maxPossibleRobots, available / required);
        }

        if (maxPossibleRobots > 0) {
            for (Map.Entry<Part, Integer> requirement : ROBOT_REQUIREMENTS.entrySet()) {
                Part part = requirement.getKey();
                int required = requirement.getValue();
                inventory.put(part, inventory.get(part) - (required * maxPossibleRobots));
            }

            robots += maxPossibleRobots;
            System.out.println("name + " built " + maxPossibleRobots + " robots! Total: " + robots);
        }
    }

    /**
     * Calculate which parts are needed most
     */
    private Map<Part, Integer> calculateNeededParts() {
        Map<Part, Integer> neededParts = new EnumMap<>(Part.class);
        int totalPartsToTake = 0;

        for (Part part : Part.values()) {
            int required = ROBOT_REQUIREMENTS.get(part);
            int current = inventory.get(part);

            int deficit = required - (current % required);
            if (deficit == required) deficit = 0;

            if (deficit > 0 && totalPartsToTake < MAX_PARTS_PER_NIGHT) {
                int canTake = Math.min(deficit, MAX_PARTS_PER_NIGHT - totalPartsToTake);
                neededParts.put(part, canTake);
                totalPartsToTake += canTake;
            }
        }

        return neededParts;
    }

    @Override
    public void run() {
        Map<Part, Integer> partsToCollect = calculateNeededParts();
        Map<Part, Integer> collectedParts = factory.takeParts(partsToCollect);

        for (Map.Entry<Part, Integer> entry : collectedParts.entrySet()) {
            Part part = entry.getKey();
            int count = entry.getValue();
            inventory.put(part, inventory.get(part) + count);
        }

        assembleRobots();

        if (!collectedParts.isEmpty()) {
            System.out.println(name + " collected: " + collectedParts + " | Inventory: " + inventory);
        }
    }
}