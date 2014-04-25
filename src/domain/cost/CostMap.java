package domain.cost;

import java.util.HashMap;
import java.util.Map;

import analyser.Action.ActionType;

public class CostMap {

	public static Map<ActionType, Cost> costs;
	
	static {
		costs = new HashMap<ActionType, Cost>();
		costs.put(ActionType.Probe, new Cost(50,0));
		costs.put(ActionType.Zealot, new Cost(100,0));
		costs.put(ActionType.Dragoon, new Cost(125,50));
		costs.put(ActionType.HighTemplar, new Cost(50,150));
		costs.put(ActionType.DarkTemplar, new Cost(125,100));
		costs.put(ActionType.DarkArchon, new Cost(250,20));
		costs.put(ActionType.Scout, new Cost(275,125));
		costs.put(ActionType.Observer, new Cost(25,75));
		costs.put(ActionType.Shuttle, new Cost(200,0));
		costs.put(ActionType.Reaver, new Cost(200,100));
		costs.put(ActionType.Observer, new Cost(25,75));
		costs.put(ActionType.Corsair, new Cost(150,100));
		costs.put(ActionType.Carrier, new Cost(350,250));
		costs.put(ActionType.Arbiter, new Cost(100,350));
		
		costs.put(ActionType.Nexus, new Cost(400,0));
		costs.put(ActionType.Assimilator, new Cost(75,0));
		costs.put(ActionType.Gateway, new Cost(150,0));
		costs.put(ActionType.Pylon, new Cost(100,0));
		costs.put(ActionType.Forge, new Cost(150,0));
		costs.put(ActionType.PhotonCannon, new Cost(150,0));
		costs.put(ActionType.CyberneticsCore, new Cost(200,0));
		costs.put(ActionType.ShieldBattery, new Cost(100,0));
		
		costs.put(ActionType.RoboticsFacility, new Cost(200,200));
		costs.put(ActionType.Stargate, new Cost(150,150));
		costs.put(ActionType.CitadelofAdun, new Cost(150,100));
		costs.put(ActionType.RoboticsSupportBay, new Cost(150,100));
		costs.put(ActionType.FleetBeacon, new Cost(300,200));
		costs.put(ActionType.TemplarArchives, new Cost(150,200));
		costs.put(ActionType.Observatory, new Cost(50,100));
		costs.put(ActionType.ArbiterTribunal, new Cost(200,150));
		
		costs.put(ActionType.ProtossGroundWeapons, new Cost(100,100));
		costs.put(ActionType.ProtossGroundArmor, new Cost(100,100));
		costs.put(ActionType.ProtossAirWeapons, new Cost(100,100));
		costs.put(ActionType.ProtossAirArmor, new Cost(100,100));
		costs.put(ActionType.ProtossPlasmaShields, new Cost(100,100));
		
		costs.put(ActionType.LegEnhancement, new Cost(150,150));
		costs.put(ActionType.SingularityCharge, new Cost(150,150));
		costs.put(ActionType.KhaydarinAmulet, new Cost(150,150));
		costs.put(ActionType.GraviticDrive, new Cost(200,200));
		costs.put(ActionType.ScarabDamage, new Cost(200,200));
		costs.put(ActionType.SensorArray, new Cost(150,150));
		costs.put(ActionType.GraviticBooster, new Cost(150,150));
		costs.put(ActionType.ApialSensors, new Cost(100,100));
		costs.put(ActionType.GraviticThrusters, new Cost(200,200));
		costs.put(ActionType.Recall, new Cost(150,150));
		costs.put(ActionType.StatisField, new Cost(150,150));
		costs.put(ActionType.KhaydarinCore, new Cost(150,150));
		costs.put(ActionType.ArgusTalisman, new Cost(150,150));
		costs.put(ActionType.Mindcontrol, new Cost(200,200));
		costs.put(ActionType.Maelstorm, new Cost(100,100));
		
	}
}
