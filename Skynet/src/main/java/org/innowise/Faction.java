package org.innowise;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;

/**
 * Represents a faction that collects robot parts from the factory storage
 * and builds robots. Each faction runs in its own thread and synchronizes
 * with other factions and the factory using cyclic barriers.
 *
 * The faction participates in a daily cycle:
 * 1. Waits for factory to finish production (barrierDayEnd)
 * 2. Collects up to 5 parts from storage
 * 3. Builds robots from available parts
 * 4. Waits for other factions to finish (barrierNightEnd)
 *
 * @see Factory
 * @see Part
 * @see CyclicBarrier
 */
public class Faction implements Runnable {
    private final String name;
    private final BlockingQueue<Part> storage;
    private final Map<Part, Integer> inventory = new EnumMap<>(Part.class);
    private final CyclicBarrier barrierDayEnd;
    private final CyclicBarrier barrierNightEnd;
    private final int days;
    private final Random random = new Random();

    private int robotsBuilt = 0;

    /**
     * Constructs a new Faction with the specified parameters.
     *
     * @param name the name of the faction
     * @param storage the shared blocking queue for accessing factory parts
     * @param barrierDayEnd barrier for synchronizing after factory production
     * @param barrierNightEnd barrier for synchronizing after night collection
     * @param days the total number of days to run the simulation
     */
    public Faction(String name, BlockingQueue<Part> storage,
                   CyclicBarrier barrierDayEnd, CyclicBarrier barrierNightEnd,
                   int days) {
        this.name = name;
        this.storage = storage;
        this.barrierDayEnd = barrierDayEnd;
        this.barrierNightEnd = barrierNightEnd;
        this.days = days;
        for (Part p : Part.values()) inventory.put(p, 0);
    }

    /**
     * Returns the name of this faction.
     *
     * @return the faction name
     */
    public String getName() { return name; }

    /**
     * Returns the number of robots built by this faction.
     *
     * @return the number of robots built
     */
    public int getRobotsBuilt() { return robotsBuilt; }

    /**
     * Main execution method for the faction thread.
     * Runs for the specified number of days, participating in the daily cycle:
     * - Waits for factory production to complete
     * - Collects parts from storage
     * - Builds robots from available parts
     * - Waits for other factions to complete their night cycle
     *
     * @throws Exception if interrupted or barrier await fails
     */
    @Override
    public void run() {
        try {
            for (int day = 1; day <= days; day++) {
                barrierDayEnd.await();

                List<Part> grabbed = new ArrayList<>();

                Part part;
                while ((part = storage.poll()) != null) {
                    grabbed.add(part);
                    inventory.merge(part, 1, Integer::sum);
                }

                int builtToday = buildRobots();

                System.out.println("Day " + day + " (night): " + name +
                        " grabbed " + grabbed +
                        ", built robots today: " + builtToday +
                        ", total robots: " + robotsBuilt);

                barrierNightEnd.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to build as many robots as possible from available parts.
     * Each robot requires: 1 head, 1 torso, 2 hands, and 2 feet.
     * Continues building until insufficient parts remain.
     *
     * @return the number of robots built in this operation
     */
    private int buildRobots() {
        int count = 0;
        while (inventory.get(Part.HEAD) >= 1 &&
                inventory.get(Part.TORSO) >= 1 &&
                inventory.get(Part.HAND) >= 2 &&
                inventory.get(Part.FEET) >= 2) {

            inventory.put(Part.HEAD, inventory.get(Part.HEAD) - 1);
            inventory.put(Part.TORSO, inventory.get(Part.TORSO) - 1);
            inventory.put(Part.HAND, inventory.get(Part.HAND) - 2);
            inventory.put(Part.FEET, inventory.get(Part.FEET) - 2);

            robotsBuilt++;
            count++;
        }
        return count;
    }
}