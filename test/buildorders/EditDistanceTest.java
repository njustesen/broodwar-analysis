package buildorders;

import java.util.ArrayList;
import java.util.List;

import analyser.Action;
import analyser.Action.Type;
import analyser.Player;
import analyser.Action.ActionType;
import analyser.Player.Race;
import clustering.editdistance.BuildOrderDistance;
import domain.naming.BuildOrderNamer;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class EditDistanceTest {
	
	@Test
    public void same() {
		
		List<Action> actionsA = new ArrayList<Action>();
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsA.size(),111,actionsA);
		
		List<Action> actionsB = new ArrayList<Action>();
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsB.size(),111,actionsB);
		
		BuildOrderDistance bod = new BuildOrderDistance(true, true, true, true, true, 0);
		
		Long start = System.nanoTime();
		Long startMs = System.currentTimeMillis();
		double distance = bod.distance(a, b, 10);
		System.out.println(System.nanoTime() - start);
		System.out.println(System.currentTimeMillis() - startMs);
		assertTrue(bod.distance(a, b, 10) == bod.distance(b, a, 10));
		
		assertTrue(distance == 0.0);
		
    }
	
	@Test
    public void oneOff() {
		
		List<Action> actionsA = new ArrayList<Action>();
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.Zealot, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsA.size(),111,actionsA);
		
		List<Action> actionsB = new ArrayList<Action>();
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsB.size(),111,actionsB);
		
		BuildOrderDistance bod = new BuildOrderDistance(true, true, true, true, false, 0);
		double distance = bod.distance(a, b, 10);
		
		assertTrue(bod.distance(a, b, 10) == bod.distance(b, a, 10));
		
		assertTrue(distance == 2.0);
		
		
    }
	
	@Test
    public void twoOff() {
		
		List<Action> actionsA = new ArrayList<Action>();
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.Zealot, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsA.size(),111,actionsA);
		
		List<Action> actionsB = new ArrayList<Action>();
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Gateway, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsB.size(),111,actionsB);
		
		BuildOrderDistance bod = new BuildOrderDistance(true, true, true, true, false, 0);
		double distance = bod.distance(a, b, 10);
		
		assertTrue(bod.distance(a, b, 10) == bod.distance(b, a, 10));
		
		assertTrue(distance == 4.0);
		
    }
	
	@Test
    public void allFourOff() {
		
		List<Action> actionsA = new ArrayList<Action>();
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Nexus, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Archon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Pylon, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.ProtossAirArmor, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsA.size(),111,actionsA);
		
		List<Action> actionsB = new ArrayList<Action>();
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Gateway, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Dragoon, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsB.size(),111,actionsB);
		
		BuildOrderDistance bod = new BuildOrderDistance(true, true, true, true, false, 0);
		double distance = bod.distance(a, b, 10);
		
		assertTrue(bod.distance(a, b, 10) == bod.distance(b, a, 10));
		
		assertTrue(distance == 8.0);
		
    }
	
	@Test
    public void skewed() {
		
		List<Action> actionsA = new ArrayList<Action>();
		actionsA.add(new Action(Race.Protoss, 1, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 3, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 5, ActionType.Zealot, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 6, ActionType.Probe, Type.Unit));
		actionsA.add(new Action(Race.Protoss, 7, ActionType.Zealot, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsA.size(),111,actionsA);
		
		List<Action> actionsB = new ArrayList<Action>();
		actionsB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 2, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 3, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 4, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 6, ActionType.Zealot, Type.Unit));
		actionsB.add(new Action(Race.Protoss, 7, ActionType.Probe, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsB.size(),111,actionsB);
		
		BuildOrderDistance bod = new BuildOrderDistance(true, true, true, true, false, 0);
		double distance = bod.distance(a, b, 10);
		
		assertTrue(bod.distance(a, b, 10) == bod.distance(b, a, 10));
		
		assertTrue(distance == 2.0);
		
    }
	
	@Test
    public void discount() {
		
		int discount = 5;
		
		BuildOrderDistance bod = new BuildOrderDistance(true, true, true, true, false, discount);
		
		List<Action> actionsAA = new ArrayList<Action>();
		actionsAA.add(new Action(Race.Protoss, 1, ActionType.Zealot, Type.Unit));
		actionsAA.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		actionsAA.add(new Action(Race.Protoss, 3, ActionType.Probe, Type.Unit));
		actionsAA.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		actionsAA.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsAA.size(),111,actionsAA);
		
		List<Action> actionsAB = new ArrayList<Action>();
		actionsAB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsAB.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		actionsAB.add(new Action(Race.Protoss, 3, ActionType.Probe, Type.Unit));
		actionsAB.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		actionsAB.add(new Action(Race.Protoss, 5, ActionType.Zealot, Type.Unit));
		
		List<Action> actionsBA = new ArrayList<Action>();
		actionsBA.add(new Action(Race.Protoss, 1, ActionType.Dragoon, Type.Unit));
		actionsBA.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		actionsBA.add(new Action(Race.Protoss, 3, ActionType.Probe, Type.Unit));
		actionsBA.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		actionsBA.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsBA.size(),111,actionsBA);
		
		List<Action> actionsBB = new ArrayList<Action>();
		actionsBB.add(new Action(Race.Protoss, 1, ActionType.Probe, Type.Unit));
		actionsBB.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		actionsBB.add(new Action(Race.Protoss, 3, ActionType.Probe, Type.Unit));
		actionsBB.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		actionsBB.add(new Action(Race.Protoss, 5, ActionType.Dragoon, Type.Unit));
		//Player b = new Player("Player b", true, Race.Protoss,actionsBB.size(),111,actionsBB);

		assertTrue(bod.distance(a, b, 10) == bod.distance(b, a, 10));
		
		double distanceBeginning = bod.distance(a, b, 10);
		
		a.setActionNumber(actionsAB.size());
		a.setActions(actionsAB);
		b.setActionNumber(actionsBB.size());
		b.setActions(actionsBB);
		
		double distanceEnd = bod.distance(a, b, 10);
		
		assertTrue(bod.distance(a, b, 10) == bod.distance(b, a, 10));
		
		//assertTrue(distanceBeginning == discount);
		
		//assertTrue(distanceEnd == 1);
		
		assertTrue(distanceBeginning > distanceEnd);
		
    }
	
	@Test
    public void cost() {
		
		BuildOrderDistance bod = new BuildOrderDistance(true, true, true, true, true, 0);
		
		List<Action> actionsAA = new ArrayList<Action>();
		actionsAA.add(new Action(Race.Protoss, 1, ActionType.Zealot, Type.Unit));
		//actionsAA.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		//actionsAA.add(new Action(Race.Protoss, 3, ActionType.Probe, Type.Unit));
		//actionsAA.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		//actionsAA.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		Player a = new Player("Player a", true, Race.Protoss,actionsAA.size(),111,actionsAA);
		
		List<Action> actionsBA = new ArrayList<Action>();
		actionsBA.add(new Action(Race.Protoss, 1, ActionType.Dragoon, Type.Unit));
		//actionsBA.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		//actionsBA.add(new Action(Race.Protoss, 3, ActionType.Probe, Type.Unit));
		//actionsBA.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		//actionsBA.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));
		Player b = new Player("Player b", true, Race.Protoss,actionsBA.size(),111,actionsBA);
		
		List<Action> actionsBB = new ArrayList<Action>();
		actionsBB.add(new Action(Race.Protoss, 1, ActionType.Nexus, Type.Unit));
		//actionsBB.add(new Action(Race.Protoss, 2, ActionType.Probe, Type.Unit));
		//actionsBB.add(new Action(Race.Protoss, 3, ActionType.Probe, Type.Unit));
		//actionsBB.add(new Action(Race.Protoss, 4, ActionType.Probe, Type.Unit));
		//actionsBB.add(new Action(Race.Protoss, 5, ActionType.Probe, Type.Unit));

		assertTrue(bod.distance(a, b, 10) == bod.distance(b, a, 10));
		
		double distanceZealotDragoon = bod.distance(a, b, 10);
		
		b.setActionNumber(actionsBB.size());
		b.setActions(actionsBB);
		
		double distanceZealotNexus = bod.distance(a, b, 10);
		
		assertTrue(bod.distance(a, b, 10) == bod.distance(b, a, 10));
		
		assertTrue(distanceZealotDragoon < distanceZealotNexus);
		
    }
	
}
