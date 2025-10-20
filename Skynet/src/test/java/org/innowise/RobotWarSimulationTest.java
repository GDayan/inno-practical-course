package org.innowise;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RobotWarSimulationTest {

    @Test
    void simulationRunsSuccessfully() throws InterruptedException {
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

        int worldRobots = world.getRobotsBuilt();
        int wedRobots = wednesday.getRobotsBuilt();

        System.out.println("World built robots = " + worldRobots);
        System.out.println("Wednesday built robots = " + wedRobots);

        assertTrue(worldRobots >= 0);
        assertTrue(wedRobots >= 0);

        assertTrue(worldRobots + wedRobots >= 0);
    }
}
