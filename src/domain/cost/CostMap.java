package domain.cost;

import java.util.HashMap;
import java.util.Map;

import analyser.Action.ActionType;

public class CostMap {

	public static Map<ActionType, Cost> costs;
	
	static {
		costs = new HashMap<ActionType, Cost>();
		
		// PROTOSS
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
		
		// Terran
		costs.put(ActionType.SCV, new Cost(50,0));
		costs.put(ActionType.Marine, new Cost(50,0));
		costs.put(ActionType.Firebat, new Cost(50,25));
		costs.put(ActionType.Ghost, new Cost(25,75));
		costs.put(ActionType.Medic, new Cost(50,25));
		costs.put(ActionType.Goliath, new Cost(100,50));
		costs.put(ActionType.ScienceVessel, new Cost(100,225));
		costs.put(ActionType.SiegeTank_TankMode, new Cost(150,100));
		costs.put(ActionType.Vulture, new Cost(75,0));
		costs.put(ActionType.Wraith, new Cost(150,100));
		costs.put(ActionType.Dropship, new Cost(100,100));
		costs.put(ActionType.Battlecruiser, new Cost(400,300));
		costs.put(ActionType.Valkyrie, new Cost(250,125));
		
		costs.put(ActionType.CommandCenter, new Cost(400,0));
		costs.put(ActionType.Refinery, new Cost(100,0));
		costs.put(ActionType.Barracks, new Cost(150,0));
		costs.put(ActionType.SupplyDepot, new Cost(100,0));
		costs.put(ActionType.EngineeringBay, new Cost(125,0));
		costs.put(ActionType.MissileTurret, new Cost(75,0));
		costs.put(ActionType.Academy, new Cost(150,0));
		costs.put(ActionType.Bunker, new Cost(100,0));
		costs.put(ActionType.ComSat, new Cost(50,50));
		costs.put(ActionType.NuclearSilo, new Cost(100,100));
		
		costs.put(ActionType.Factory, new Cost(200,100));
		costs.put(ActionType.Starport, new Cost(150,100));
		costs.put(ActionType.Armory, new Cost(100,50));
		costs.put(ActionType.ScienceFacility, new Cost(100,150));
		costs.put(ActionType.MachineShop, new Cost(50,50));
		costs.put(ActionType.ControlTower, new Cost(50,50));
		costs.put(ActionType.PhysicsLab, new Cost(50,50));
		costs.put(ActionType.CovertOps, new Cost(50,50));
		
		costs.put(ActionType.TerranInfantryWeapons, new Cost(100,100));
		costs.put(ActionType.TerranInfantryArmor, new Cost(100,100));
		costs.put(ActionType.U238Shells, new Cost(150,150));
		costs.put(ActionType.StimPack, new Cost(100,100));
		costs.put(ActionType.OcularImplants, new Cost(100,100));
		costs.put(ActionType.MoebiusReactor, new Cost(150,150));
		costs.put(ActionType.Lockdown, new Cost(200,200));
		costs.put(ActionType.PersonalCloacking, new Cost(100,100));
		costs.put(ActionType.IonThrusters, new Cost(100,100));
		costs.put(ActionType.SpiderMines, new Cost(100,100));
		costs.put(ActionType.TerranVehicleWeapons, new Cost(100,100));
		costs.put(ActionType.TerranVehiclePlating, new Cost(100,100));
		costs.put(ActionType.SiegeTankMode, new Cost(150,150));
		costs.put(ActionType.CharonBoosters, new Cost(100,100));
		
		costs.put(ActionType.TerranShipWeapons, new Cost(100,100));
		costs.put(ActionType.TerranShipPlating, new Cost(100,100));
		costs.put(ActionType.ApolloReactor, new Cost(200,200));
		costs.put(ActionType.CloackingField, new Cost(150,150));
		costs.put(ActionType.ColossusReactor, new Cost(150,150));
		costs.put(ActionType.YamatoGun, new Cost(100,100));
		costs.put(ActionType.TitanReactor, new Cost(150,150));
		costs.put(ActionType.EMPShockwave, new Cost(200,200));
		costs.put(ActionType.Irradiate, new Cost(200,200));
		costs.put(ActionType.CaduceusReactor, new Cost(150,150));
		costs.put(ActionType.Restoration, new Cost(100,100));
		costs.put(ActionType.OpticalFlare, new Cost(100,100));
		
		// ZERG
		costs.put(ActionType.Defiler, new Cost(50,150));
		costs.put(ActionType.Drone, new Cost(50,0));
		costs.put(ActionType.Guardian, new Cost(150,200));
		costs.put(ActionType.Hydralisk, new Cost(75,25));
		costs.put(ActionType.Mutalisk, new Cost(100,100));
		costs.put(ActionType.Overlord, new Cost(100,0));
		costs.put(ActionType.Queen, new Cost(100,100));
		costs.put(ActionType.Scourge, new Cost(24,76));
		costs.put(ActionType.Ultralisk, new Cost(200,200));
		costs.put(ActionType.Zergling, new Cost(50,0));
		costs.put(ActionType.Devourer, new Cost(250,150));
		costs.put(ActionType.Lurker, new Cost(200,200));
		
		costs.put(ActionType.Hatchery, new Cost(300,0));
		costs.put(ActionType.Extractor, new Cost(50,0));
		costs.put(ActionType.EvolutionChamber, new Cost(75,0));
		costs.put(ActionType.HydraliskDen, new Cost(100,50));
		costs.put(ActionType.SporeColony, new Cost(50,0));
		costs.put(ActionType.SunkenColony, new Cost(50,0));
		costs.put(ActionType.SpawningPool, new Cost(200,0));
		costs.put(ActionType.CreepColony, new Cost(75,0));
		costs.put(ActionType.Lair, new Cost(150,100));
		costs.put(ActionType.Spire, new Cost(200,150));
		costs.put(ActionType.QueensNest, new Cost(150,100));
		costs.put(ActionType.GreaterSpire, new Cost(100,150));
		costs.put(ActionType.Hive, new Cost(200,150));
		costs.put(ActionType.NydusCanal, new Cost(150,0));
		costs.put(ActionType.DefilerMound, new Cost(100,100));
		costs.put(ActionType.UltraliskCavern, new Cost(150,200));
		
		costs.put(ActionType.Burrow, new Cost(100,100));
		costs.put(ActionType.ZergCarapace, new Cost(150,150));
		costs.put(ActionType.VentralSacs, new Cost(200,200));
		costs.put(ActionType.Antennae, new Cost(150,150));
		costs.put(ActionType.PneumatizedCarapace, new Cost(150,150));
		costs.put(ActionType.ZergFlyerCarapace, new Cost(150,150));
		
		costs.put(ActionType.ZergMeleeAttacks, new Cost(100,100));
		costs.put(ActionType.MetabolicBoost, new Cost(100,100));
		costs.put(ActionType.AdrenalGlands, new Cost(200,200));
		costs.put(ActionType.ZergMissileAttacks, new Cost(100,100));
		costs.put(ActionType.MuscularAugments, new Cost(150,150));
		costs.put(ActionType.GroovedSpines, new Cost(150,150));
		costs.put(ActionType.LurkerAspect, new Cost(200,200));
		costs.put(ActionType.ZergFlyerAttacks, new Cost(100,100));
		costs.put(ActionType.ZergFlyerCarapace, new Cost(150,150));
		
		costs.put(ActionType.GameteMeiosis, new Cost(150,150));
		costs.put(ActionType.Ensnare, new Cost(100,100));
		costs.put(ActionType.SpawnBroodling, new Cost(100,100));
		//costs.put(ActionType., new Cost(100,100));
		costs.put(ActionType.Consume, new Cost(100,100));
		costs.put(ActionType.Plague, new Cost(200,200));
		costs.put(ActionType.AnabolicSynthesis, new Cost(200,200));
		costs.put(ActionType.ChitinousPlating, new Cost(150,150));
		
	}
}
