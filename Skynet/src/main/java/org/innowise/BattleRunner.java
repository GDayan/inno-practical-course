package org.innowise;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Orchestrator for the robot battle simulation.
 * Manages threads for factions and runs the simulation for a given number of days.
 */

public class BattleRunner {
    private final Factory factory;
    private final List<Faction> factions;

    public BattleRunner(Factory factory, List<Faction> factions) {
        this.factory = factory;
        this.factions = factions;
    }

    /**
     * Runs the simulation for a given number of days.
     *
     * @param days number of simulation days
     * @throws InterruptedException if thread execution is interrupted
     */
    public void runSimulation(int days) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(factions.size() + 1);

        for (int day = 1; day <= days; day++) {
            System.out.println("Day " + day);
            factory.produceParts();

            for (Faction faction : factions) {
                executor.submit(faction);
            }

            Thread.sleep(50); // simulate daily pause
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    /**
     * Prints the final results of the battle.
     * Assumes there are exactly 2 factions.
     */
    public void printResults() {
        if (factions.size() < 2) {
            System.out.println("Not enough factions to determine results.");
            return;
        }

        Faction f1 = factions.get(0);
        Faction f2 = factions.get(1);

        System.out.println("\nResults after simulation:");
        System.out.println(f1.getName() + " robots: " + f1.getRobots());
        System.out.println(f2.getName() + " robots: " + f2.getRobots());

        if (f1.getRobots() > f2.getRobots()) {
            System.out.println(f1.getName() + " wins!");
        } else if (f1.getRobots() < f2.getRobots()) {
            System.out.println(f2.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    /**
     * Returns the list of factions participating in the battle.
     *
     * @return list of factions
     */
    public List<Faction> getFactions() {
        return factions;
    }
}
