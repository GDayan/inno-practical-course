package org.innowise;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Factory {
    private final Map<Part, BlockingQueue<Part>> storage = new EnumMap<>(Part.class);
    private final Lock productionLock = new ReentrantLock();
    private final Random random = new Random();
    private static final int MAX_PARTS_PER_DAY = 10;

    public Factory() {
        for (Part part : Part.values()) {
            storage.put(part, new LinkedBlockingQueue<>());
        }
    }

    /**
     * Produces random parts (0-10) and adds them to storage
     */
    public void produceParts() {
        productionLock.lock();
        try {
            for (Part part : Part.values()) {
                storage.get(part).clear();
            }

            int partsCount = random.nextInt(MAX_PARTS_PER_DAY + 1);
            System.out.println("Factory producing " + partsCount + " parts");

            for (int i = 0; i < partsCount; i++) {
                Part randomPart = Part.values()[random.nextInt(Part.values().length)];
                storage.get(randomPart).offer(randomPart);
            }

            printProductionReport();

        } finally {
            productionLock.unlock();
        }
    }

    /**
     * Takes up to maxParts parts from factory
     * Factions can specify which parts they need
     */
    public Map<Part, Integer> takeParts(Map<Part, Integer> requestedParts) {
        Map<Part, Integer> takenParts = new EnumMap<>(Part.class);

        productionLock.lock();
        try {
            for (Map.Entry<Part, Integer> entry : requestedParts.entrySet()) {
                Part partType = entry.getKey();
                int requestedAmount = entry.getValue();
                BlockingQueue<Part> partQueue = storage.get(partType);

                int actualTaken = 0;
                while (actualTaken < requestedAmount && !partQueue.isEmpty()) {
                    Part part = partQueue.poll();
                    if (part != null) {
                        actualTaken++;
                    } else {
                        break;
                    }
                }

                if (actualTaken > 0) {
                    takenParts.put(partType, actualTaken);
                }
            }
        } finally {
            productionLock.unlock();
        }

        return takenParts;
    }

    private void printProductionReport() {
        System.out.print("Produced: ");
        for (Part part : Part.values()) {
            int count = storage.get(part).size();
            if (count > 0) {
                System.out.print(part + ": " + count + " ");
            }
        }
        System.out.println();
    }
}