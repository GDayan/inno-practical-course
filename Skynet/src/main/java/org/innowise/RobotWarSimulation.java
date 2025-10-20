package org.innowise;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Main simulation class that coordinates the robot war between factions.
 * Uses cyclic barriers to synchronize the daily cycle between factory and factions.
 *
 * Simulation flow:
 * 1. Factory produces parts during the day
 * 2. All factions simultaneously collect parts at night
 * 3. Process repeats for specified number of days
 * 4. Results are displayed showing which faction built the most robots
 *
 * @see Factory
 * @see Faction
 * @see CyclicBarrier
 */
public class RobotWarSimulation {

    /**
     * Main method that initializes and runs the robot war simulation.
     * Creates synchronization barriers, starts all threads, and displays results.
     *
     * @param args command line arguments (not used)
     * @throws InterruptedException if any thread is interrupted during execution
     */
    public static void main(String[] args) throws InterruptedException {
        final int DAYS = 100;
        BlockingQueue<Part> storage = new LinkedBlockingQueue<>();

        CyclicBarrier barrierDayEnd = new CyclicBarrier(3);
        CyclicBarrier barrierNightEnd = new CyclicBarrier(3);

        Factory factory = new Factory(storage, barrierDayEnd, barrierNightEnd, DAYS);
        Faction world = new Faction("World", storage, barrierDayEnd, barrierNightEnd, DAYS);
        Faction wednesday = new Faction("Wednesday", storage, barrierDayEnd, barrierNightEnd, DAYS);

        Thread factoryThread = new Thread(factory);
        Thread worldThread = new Thread(world);
        Thread wednesdayThread = new Thread(wednesday);

        factoryThread.start();
        worldThread.start();
        wednesdayThread.start();

        factoryThread.join();
        worldThread.join();
        wednesdayThread.join();

        System.out.println("--------------------------------------------------");
        System.out.println("RESULTS AFTER " + DAYS + " DAYS:");
        System.out.println(world.getName() + " built robots: " + world.getRobotsBuilt());
        System.out.println(wednesday.getName() + " built robots: " + wednesday.getRobotsBuilt());

        if (world.getRobotsBuilt() > wednesday.getRobotsBuilt()) {
            System.out.println("WINNER: " + world.getName());
        } else if (world.getRobotsBuilt() < wednesday.getRobotsBuilt()) {
            System.out.println("WINNER: " + wednesday.getName());
        } else {
            System.out.println("It's a TIE!");
        }
    }
}