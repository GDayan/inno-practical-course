package org.innowise;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Simulation of a robot battle between two factions over 100 days.
 * Each day, the factory produces parts, factions collect parts and assemble robots.
 * Prints the daily production and final results.
 */
public class RobotBattleSimulation {

    /**
     * Main method that runs the simulation.
     *
     * @param args command line arguments (not used)
     * @throws InterruptedException if the thread execution is interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        Factory factory = new Factory();
        Faction world = new Faction("World", factory);
        Faction wednesday = new Faction("Wednesday", factory);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int day = 1; day <= 100; day++) {
            System.out.println("Day " + day);
            factory.produceParts();

            executor.submit(world);
            executor.submit(wednesday);

            Thread.sleep(50);
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("\nResults after 100 days:");
        System.out.println(world.getName() + " robots: " + world.getRobots());
        System.out.println(wednesday.getName() + " robots: " + wednesday.getRobots());

        if (world.getRobots() > wednesday.getRobots()) {
            System.out.println("World wins!");
        } else if (world.getRobots() < wednesday.getRobots()) {
            System.out.println("Wednesday wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}


