package analyser;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;

public class Action
{

  private static final Map<String, Integer> RESEARCH_MAP = new HashMap<String, Integer>();
  static {
    RESEARCH_MAP.put("Stim Pack", 0);
    RESEARCH_MAP.put("Lockdown", 1);
    RESEARCH_MAP.put("EMP Shockwave", 2);
    RESEARCH_MAP.put("Spider Mines", 3);
    RESEARCH_MAP.put("Siege Tank", 4);
    RESEARCH_MAP.put("Irradiate", 5);
    RESEARCH_MAP.put("Yamato Gun", 6);
    RESEARCH_MAP.put("Cloacking Field (Wraith)", 7);
    RESEARCH_MAP.put("Personal Cloacking (Ghost)", 8);
    RESEARCH_MAP.put("Burrow", 9);
    RESEARCH_MAP.put("Spawn Broodling", 10);
    RESEARCH_MAP.put("Plague", 11);
    RESEARCH_MAP.put("Consume", 12);
    RESEARCH_MAP.put("Ensnare", 13);
    RESEARCH_MAP.put("Psionic Storm", 14);
    RESEARCH_MAP.put("Hallucination", 15);
    RESEARCH_MAP.put("Recall", 16);
    RESEARCH_MAP.put("Statis Field", 17);
    RESEARCH_MAP.put("Restoration", 18);
    RESEARCH_MAP.put("Distruption Web", 19);
    RESEARCH_MAP.put("Mind control", 20);
    RESEARCH_MAP.put("Optical Flare", 21);
    RESEARCH_MAP.put("Maelstorm", 22);
    RESEARCH_MAP.put("Lurker Aspect", 23);
  }

  private static final Map<String, Integer> UPGRADE_MAP = new HashMap<String, Integer>();
  static {
    UPGRADE_MAP.put("Terran Infantry Armor", 1);
    UPGRADE_MAP.put("Terran Vehicle Plating", 2);
    UPGRADE_MAP.put("Terran Ship Plating", 3);
    UPGRADE_MAP.put("Zerg Carapace", 4);
    UPGRADE_MAP.put("Zerg Flyer Carapace", 5);
    UPGRADE_MAP.put("Protoss Ground Armor", 6);
    UPGRADE_MAP.put("Protoss Air Armor", 7);
    UPGRADE_MAP.put("Terran Infantry Weapons", 8);
    UPGRADE_MAP.put("Terran Vehicle Weapons", 9);
    UPGRADE_MAP.put("Terran Ship Weapons", 10);
    UPGRADE_MAP.put("Zerg Melee Attacks", 11);
    UPGRADE_MAP.put("Zerg Missile Attacks", 12);
    UPGRADE_MAP.put("Zerg Flyer Attacks", 13);
    UPGRADE_MAP.put("Protoss Ground Weapons", 14);
    UPGRADE_MAP.put("Protoss Air Weapons", 15);
    UPGRADE_MAP.put("Protoss Plasma Shields", 16);
    UPGRADE_MAP.put("U-238 Shells (Marine Range)", 17);
    UPGRADE_MAP.put("Ion Thrusters (Vulture Speed)", 18);
    UPGRADE_MAP.put("Titan Reactor (Science Vessel Energy)", 19);
    UPGRADE_MAP.put("Ocular Implants (Ghost Sight)", 20);
    UPGRADE_MAP.put("Moebius Reactor (Ghost Energy)", 21);
    UPGRADE_MAP.put("Apollo Reactor (Wraith Energy)", 22);
    UPGRADE_MAP.put("Colossus Reactor (Battle Cruiser Energy)", 23);
    UPGRADE_MAP.put("Ventral Sacs (Overlord Transport)", 24);
    UPGRADE_MAP.put("Antennae (Overlord Sight)", 25);
    UPGRADE_MAP.put("Pneumatized Carapace (Overlord Speed)", 26);
    UPGRADE_MAP.put("Metabolic Boost (Zergling Speed)", 27);
    UPGRADE_MAP.put("Adrenal Glands (Zergling Attack)", 28);
    UPGRADE_MAP.put("Muscular Augments (Hydralisk Speed)", 29);
    UPGRADE_MAP.put("Grooved Spines (Hydralisk Range)", 30);
    UPGRADE_MAP.put("Gamete Meiosis (Queen Energy)", 31);
    UPGRADE_MAP.put("Defiler Energy", 32);
    UPGRADE_MAP.put("Singularity Charge (Dragoon Range)", 33);
    UPGRADE_MAP.put("Leg Enhancement (Zealot Speed)", 34);
    UPGRADE_MAP.put("Scarab Damage", 35);
    UPGRADE_MAP.put("Reaver Capacity", 36);
    UPGRADE_MAP.put("Gravitic Drive (Shuttle Speed)", 37);
    UPGRADE_MAP.put("Sensor Array (Observer Sight)", 38);
    UPGRADE_MAP.put("Gravitic Booster (Observer Speed)", 39);
    UPGRADE_MAP.put("Khaydarin Amulet (Templar Energy)", 40);
    UPGRADE_MAP.put("Apial Sensors (Scout Sight)", 41);
    UPGRADE_MAP.put("Gravitic Thrusters (Scout Speed)", 42);
    UPGRADE_MAP.put("Carrier Capacity", 43);
    UPGRADE_MAP.put("Khaydarin Core (Arbiter Energy)", 44);
    UPGRADE_MAP.put("Argus Jewel (Corsair Energy)", 45);
    UPGRADE_MAP.put("Argus Talisman (Dark Archon Energy)", 46);
    UPGRADE_MAP.put("Caduceus Reactor (Medic Energy)", 47);
    UPGRADE_MAP.put("Chitinous Plating (Ultralisk Armor)", 48);
    UPGRADE_MAP.put("Anabolic Synthesis (Ultralisk Speed)", 49);
    UPGRADE_MAP.put("Charon Boosters (Goliath Range)", 50);
  }

  public enum Type
  {
    // upgardes
    TerranInfantryArmor,
    TerranVehiclePlating,
    TerranShipPlating,
    ZergCarapace,
    ZergFlyerCarapace,
    ProtossGroundArmor,
    ProtossAirArmor,
    TerranInfantryWeapons,
    TerranVehicleWeapons,
    TerranShipWeapons,
    ZergMeleeAttacks,
    ZergMissileAttacks,
    ZergFlyerAttacks,
    ProtossGroundWeapons,
    ProtossAirWeapons,
    ProtossPlasmaShields,
    U238Shells,
    IonThrusters,
    TitanReactor,
    OcularImplants,
    MoebiusReactor,
    ApolloReactor,
    ColossusReactor,
    VentralSacs,
    Antennae,
    PneumatizedCarapace,
    MetabolicBoost,
    AdrenalGlands,
    MuscularAugments,
    GroovedSpines,
    GameteMeiosis,
    DefilerEnergy,
    SingularityCharge,
    LegEnhancement,
    ScarabDamage,
    ReaverCapacity,
    GraviticDrive,
    SensorArray,
    GraviticBooster,
    KhaydarinAmulet,
    ApialSensors,
    GraviticThrusters,
    CarrierCapacity,
    KhaydarinCore,
    ArgusJewel,
    ArgusTalisman,
    CaduceusReactor,
    ChitinousPlating,
    AnabolicSynthesis,
    CharonBoosters,

    // researches 0x36
    StimPack,
    Lockdown,
    EMPShockwave,
    SpiderMines,
    SiegeTankMode,
    Irradiate,
    YamatoGun,
    CloackingField,
    PersonalCloacking,
    Burrow,
    SpawnBroodling,
    Plague,
    Consume,
    Ensnare,
    PsionicStorm,
    Hallucination,
    Recall,
    StatisField,
    Restoration,
    DistruptionWeb,
    Mindcontrol,
    OpticalFlare,
    Maelstorm,
    LurkerAspect,

    // units and buildings 0x56
    Marine,
    Ghost,
    Vulture,
    Goliath,
    GoliathTurret,
    SiegeTank,
    SiegeTankTurret1,
    SCV,
    Wraith,
    ScienceVessel,
    GuiMotang,
    Dropship,
    Battlecruiser,
    SpiderMine,
    NuclearMissile,
    TerranCivilian,
    SarahKerrigan,
    AlanSchezar,
    AlanSchezarTurret,
    JimRaynor1,
    JimRaynor2,
    TomKazansky,
    Magellan,
    EdmundDuke1,
    EdmundDukeTurret1,
    EdmundDuke2,
    EdmundDukeTurret2,
    ArcturusMengsk,
    Hyperion,
    NoradII1,
    TerranSiegeTank,
    SiegeTankTurret2,
    Firebat,
    ScannerSweep,
    Medic,
    Larva,
    Egg,
    Zergling,
    Hydralisk,
    Ultralisk,
    Drone,
    Overlord,
    Mutalisk,
    Guardian,
    Queen,
    Defiler,
    Scourge,
    Torrasque,
    Matriarch,
    InfestedTerran,
    InfestedKerrigan,
    UncleanOne,
    HunterKiller,
    DevouringOne,
    Kukulza1,
    Kukulza2,
    Yggdrasill,
    Valkyrie,
    MutaliskCocoon,
    Corsair,
    DarkTemplar,
    Devourer,
    DarkArchon,
    Probe,
    Zealot,
    Dragoon,
    HighTemplar,
    Archon,
    Shuttle,
    Scout,
    Arbiter,
    Carrier,
    Interceptor,
    ProtossDarkTemplar,
    Zeratul,
    TassadarZeratul,
    Fenix1,
    Fenix2,
    Tassadar,
    Mojo,
    Warbringer,
    Gantrithor,
    Reaver,
    Observer,
    Scarab,
    Danimoth,
    Aldaris,
    Artanis,
    Rhynadon,
    Bengalaas,
    CargoShip,
    MercenaryGunship,
    Scantid,
    Kakaru,
    Ragnasaur,
    Ursadon,
    LurkerEgg,
    Raszagal,
    SamirDuran,
    AlexeiStukov,
    MapRevealer,
    GerardDuGalle,
    Lurker,
    InfestedDuran,
    DisruptionWeb,
    CommandCenter,
    ComSat,
    NuclearSilo,
    SupplyDepot,
    Refinery,
    Barracks,
    Academy,
    Factory,
    Starport,
    ControlTower,
    ScienceFacility,
    CovertOps,
    PhysicsLab,
    MachineShop,
    RepairBay,
    EngineeringBay,
    Armory,
    MissileTurret,
    Bunker,
    NoradII2,
    IonCannon,
    UrajCrystal,
    KhalisCrystal,
    InfestedCC,
    Hatchery,
    Lair,
    Hive,
    NydusCanal,
    HydraliskDen,
    DefilerMound,
    GreaterSpire,
    QueensNest,
    EvolutionChamber,
    UltraliskCavern,
    Spire,
    SpawningPool,
    CreepColony,
    SporeColony,
    UnusedZergBuilding1,
    SunkenColony,
    ZergOvermind,
    Overmind,
    Extractor,
    MatureChrysalis,
    Cerebrate,
    CerebrateDaggoth,
    UnusedZergBuilding2,
    Nexus,
    RoboticsFacility,
    Pylon,
    Assimilator,
    UnusedProtossBuilding1,
    Observatory,
    Gateway,
    UnusedProtossBuilding2,
    PhotonCannon,
    CitadelofAdun,
    CyberneticsCore,
    TemplarArchives,
    Forge,
    Stargate,
    StasisCellPrison,
    FleetBeacon,
    ArbiterTribunal,
    RoboticsSupportBay,
    ShieldBattery,
    KhaydarinCrystalFormation1,
    ProtossTemple,
    XelNagaTemple,
    MineralField1,
    MineralField2,
    MineralField3,
    Cave,
    CaveIn,
    Cantina,
    MiningPlatform,
    IndependentCommandCenter,
    IndependentStarport,
    IndependentJumpGate,
    Ruins,
    KhaydarinCrystalFormation2,
    VespeneGeyser,
    WarpGate,
    PsiDisrupter,
    ZergMarker,
    TerranMarker,
    ProtossMarker,
    ZergBeacon,
    TerranBeacon,
    ProtossBeacon,
    ZergFlagBeacon,
    TerranFlagBeacon,
    ProtossFlagBeacon,
    PowerGenerator,
    OvermindCocoon,
    DarkSwarm,
    FloorMissileTrap,
    FloorHatch,
    LeftUpperLevelDoor,
    RightUpperLevelDoor,
    LeftPitDoor,
    RightPitDoor,
    FloorGunTrap,
    LeftWallMissileTrap,
    LeftWallFlameTrap,
    RightWallMissileTrap,
    RightWallFlameTrap,
    StartLocation,
    Flag,
    YoungChrysalis,
    PsiEmitter,
    DataDisc,
    KhaydarinCrystal,
    MineralClusterType1,
    MineralClusterType2,
    ProtossVespeneGasOrbType1,
    ProtossVespeneGasOrbType2,
    ZergVespeneGasSacType1,
    ZergVespeneGasSacType2,
    TerranVespeneGasTankType1,
    TerranVespeneGasTankType2,
  }

  public int frames;
  public Type type;

  public Action(parser.bwhf.model.Action action)
  {
    this.frames = action.iteration;

    if (action.actionNameIndex == 0x0c) // build
      type = Type.class.getEnumConstants()[0x56 + action.parameterBuildingNameIndex];

    if (action.actionNameIndex == 0x1f || action.actionNameIndex == 0x23) // train
      type = Type.class.getEnumConstants()[0x56 + action.parameterUnitNameIndex];

    if (action.actionNameIndex == 0x30) // reserch
      type = Type.class.getEnumConstants()[0x36 + RESEARCH_MAP.get(action.parameters)];

    if (action.actionNameIndex == 0x32) // upgrade
      type = Type.class.getEnumConstants()[UPGRADE_MAP.get(action.parameters)];
  }


}
