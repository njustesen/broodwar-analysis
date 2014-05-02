package buildorders;

import java.util.ArrayList;
import java.util.List;

import analyser.Action;
import analyser.Action.Type;
import analyser.Player;
import analyser.Action.ActionType;
import analyser.Player.Race;
import clustering.distance.EditDistance;
import clustering.distance.FirstDistance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class FirstDistanceTest {
	
	@Test
    public void same() {
		
		List<Action> actionsA = new ArrayList<Action>();
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 6, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 8, ActionType.Dragoon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 9, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 0, ActionType.Zealot, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsA.size(),111,actionsA);
		
		List<Action> actionsB = new ArrayList<Action>();
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 6, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 8, ActionType.Dragoon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 9, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 10, ActionType.Zealot, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsB.size(),111,actionsB);
		
		FirstDistance bod = new FirstDistance(true, true, true, true, 10);
		
		Long start = System.nanoTime();
		Long startMs = System.currentTimeMillis();
		double distance = bod.distance(a, b);
		System.out.println(System.nanoTime() - start);
		System.out.println(System.currentTimeMillis() - startMs);
		assertTrue(bod.distance(a, b) == bod.distance(b, a));
		
		assertTrue(distance == 0.0);
		
    }
	
	@Test
    public void firstOff() {
		
		List<Action> actionsA = new ArrayList<Action>();
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 6, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 8, ActionType.Dragoon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 9, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 0, ActionType.Zealot, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsA.size(),111,actionsA);
		
		List<Action> actionsB = new ArrayList<Action>();
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 6, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 8, ActionType.Dragoon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 9, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 10, ActionType.Zealot, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsB.size(),111,actionsB);
		
		FirstDistance bod = new FirstDistance(true, true, true, true, 10);
		
		Long start = System.nanoTime();
		Long startMs = System.currentTimeMillis();
		double distance = bod.distance(a, b);
		System.out.println(System.nanoTime() - start);
		System.out.println(System.currentTimeMillis() - startMs);
		assertTrue(bod.distance(a, b) == bod.distance(b, a));
		
		assertTrue(distance == 10.0);
		
    }
	
	@Test
    public void secondOff() {
		
		List<Action> actionsA = new ArrayList<Action>();
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 6, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 8, ActionType.Dragoon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 9, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 0, ActionType.Zealot, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsA.size(),111,actionsA);
		
		List<Action> actionsB = new ArrayList<Action>();
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Dragoon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 6, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 8, ActionType.Dragoon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 9, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 10, ActionType.Zealot, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsB.size(),111,actionsB);
		
		FirstDistance bod = new FirstDistance(true, true, true, true, 10);
		
		Long start = System.nanoTime();
		Long startMs = System.currentTimeMillis();
		double distance = bod.distance(a, b);
		System.out.println(System.nanoTime() - start);
		System.out.println(System.currentTimeMillis() - startMs);
		assertTrue(bod.distance(a, b) == bod.distance(b, a));
		
		assertTrue(distance == 9.0);
		
    }

	@Test
    public void tenthOff() {
		
		List<Action> actionsA = new ArrayList<Action>();
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 6, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 8, ActionType.Dragoon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 9, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 0, ActionType.Zealot, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsA.size(),111,actionsA);
		
		List<Action> actionsB = new ArrayList<Action>();
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 6, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 7, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 8, ActionType.Dragoon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 9, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 10, ActionType.Dragoon, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsB.size(),111,actionsB);
		
		FirstDistance bod = new FirstDistance(true, true, true, true, 10);
		
		Long start = System.nanoTime();
		Long startMs = System.currentTimeMillis();
		double distance = bod.distance(a, b);
		System.out.println(System.nanoTime() - start);
		System.out.println(System.currentTimeMillis() - startMs);
		assertTrue(bod.distance(a, b) == bod.distance(b, a));
		
		assertTrue(distance == 1.0);
		
    }
	
}
