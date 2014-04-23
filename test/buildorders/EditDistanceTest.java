package buildorders;

import java.util.ArrayList;
import java.util.List;

import analyser.Action;
import analyser.Action.Type;
import analyser.Player;
import analyser.Action.ActionType;
import analyser.Player.Race;
import clustering.editdistance.BuildOrderDistance;

public class EditDistanceTest {

	public static void main(String[] args) {
		
		testEqual();
		
		testOneOff();
		
	}

	private static void testEqual() {
		List<Action> actionsA = new ArrayList<Action>();
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 5, ActionType.Gateway, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 6, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 8, ActionType.Zealot, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsA.size(),111,actionsA);
		
		List<Action> actionsB = new ArrayList<Action>();
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 5, ActionType.Gateway, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 6, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 8, ActionType.Zealot, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsB.size(),111,actionsB);
		
		BuildOrderDistance bod = new BuildOrderDistance(0.0, true, true, true, true);
		double distance = bod.distance(a, b);
		
		System.out.println(distance);
	}

	private static void testOneOff() {
		List<Action> actionsA = new ArrayList<Action>();
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 5, ActionType.Gateway, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 6, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 8, ActionType.Zealot, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsA.size(),111,actionsA);
		
		List<Action> actionsB = new ArrayList<Action>();
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 5, ActionType.Gateway, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 6, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 8, ActionType.Probe, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsB.size(),111,actionsB);
		
		BuildOrderDistance bod = new BuildOrderDistance(0.0, true, true, true, true);
		double distance = bod.distance(a, b);
		
		System.out.println(distance);
	}
	
}
