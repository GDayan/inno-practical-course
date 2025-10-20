package org.innowise;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BattleRunner {
    private final Factory factory;
    private final List<Faction> factions;

    public BattleRunner(Factory factory, List<Faction> factions) {
        this.factory = factory;
        this.factions = factions;
    }

    public void runSimulation(int days) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(factions.size() + 1);

        for (int day = 1; day <= days; day++) {
            System.out.println("\n=== Day " + day + " ===");

            factory.produceParts();

            CountDownLatch nightLatch = new CountDownLatch(factions.size());

            for (Faction faction : factions) {
                executor.submit(() -> {
                    try {
                        faction.run();
                    } finally {
                        nightLatch.countDown();
                    }
                });
            }

            nightLatch.await();

            System.out.println("--- End of Day " + day + " ---");
        }

        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
            executor.shutdownNow();
        }
    }

    public void printResults() {
        if (factions.size() < 2) {
            System.out.println("Not enough factions to determine results.");
            return;
        }

        Faction f1 = factions.get(0);
        Faction f2 = factions.get(1);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== FINAL RESULTS AFTER 100 DAYS ===");
        System.out.println("=".repeat(50));
        System.out.println(f1.getName() + " robots: " + f1.getRobots());
        System.out.println(f2.getName() + " robots: " + f2.getRobots());
        System.out.println("-".repeat(50));

        if (f1.getRobots() > f2.getRobots()) {
            System.out.println(f1.getName() + " wins!");
        } else if (f1.getRobots() < f2.getRobots()) {
            System.out.println(f2.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public List<Faction> getFactions() {
        return factions;
    }
}