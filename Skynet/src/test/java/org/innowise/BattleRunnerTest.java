package org.innowise;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * Tests for BattleRunner.
 */
public class BattleRunnerTest {

    @Test
    void testBattleRunnerSimulation() throws InterruptedException {
        Factory factory = new Factory();
        Faction world = new Faction("World", factory);
        Faction wednesday = new Faction("Wednesday", factory);

        BattleRunner runner = new BattleRunner(factory, List.of(world, wednesday));

        runner.runSimulation(100);

        for (Faction faction : runner.getFactions()) {
            System.out.println(faction.getName() + " robots: " + faction.getRobots());
            assertTrue(faction.getRobots() >= 0, faction.getName() + " should have >= 0 robots");
        }

        runner.printResults();
    }
}
