import java.util.Arrays;
import java.util.List;

public class RobotBattleSimulation {
    public static void main(String[] args) throws InterruptedException {
        Factory factory = new Factory();
        Faction world = new Faction("World", factory);
        Faction wednesday = new Faction("Wednesday", factory);

        List<Faction> factions = Arrays.asList(world, wednesday);
        BattleRunner battleRunner = new BattleRunner(factory, factions);

        battleRunner.runSimulation(100);
        battleRunner.printResults();
    }
}