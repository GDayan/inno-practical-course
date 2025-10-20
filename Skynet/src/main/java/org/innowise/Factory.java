package org.innowise;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;

/**
 * Represents a factory that produces robot parts daily.
 * The factory runs in its own thread and synchronizes production
 * with factions using cyclic barriers.
 *
 * Daily cycle:
 * 1. Produces 0-10 random parts and adds them to storage
 * 2. Waits for factions to collect parts (barrierDayEnd)
 * 3. Waits for factions to finish building (barrierNightEnd)
 *
 * @see Faction
 * @see Part
 * @see CyclicBarrier
 */
public class Factory implements Runnable {
    private final BlockingQueue<Part> storage;
    private final CyclicBarrier barrierDayEnd;
    private final CyclicBarrier barrierNightEnd;
    private final int days;
    private final Random random = new Random();

    /**
     * Constructs a new Factory with the specified parameters.
     *
     * @param storage the blocking queue where produced parts are stored
     * @param barrierDayEnd barrier for synchronizing after production
     * @param barrierNightEnd barrier for synchronizing after night cycle
     * @param days the total number of days to run the simulation
     */
    public Factory(BlockingQueue<Part> storage, CyclicBarrier barrierDayEnd,
                   CyclicBarrier barrierNightEnd, int days) {
        this.storage = storage;
        this.barrierDayEnd = barrierDayEnd;
        this.barrierNightEnd = barrierNightEnd;
        this.days = days;
    }

    /**
     * Main execution method for the factory thread.
     * Runs for the specified number of days, producing random parts
     * each day and synchronizing with factions.
     *
     * @throws Exception if interrupted or barrier await fails
     */
    @Override
    public void run() {
        try {
            for (int day = 1; day <= days; day++) {
                int produced = random.nextInt(11);
                List<Part> producedParts = new ArrayList<>();
                for (int i = 0; i < produced; i++) {
                    Part p = Part.values()[random.nextInt(Part.values().length)];
                    storage.put(p);
                    producedParts.add(p);
                }

                System.out.println("Day " + day + " (daytime): Factory produced " + producedParts);

                barrierDayEnd.await();

                barrierNightEnd.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}