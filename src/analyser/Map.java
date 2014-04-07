package analyser;

import parser.bwhf.model.MapData;

public class Map
{
  public enum Type
  {
    Andromedal,
    ChupungRyeung,
    Colosseum,
    Hunters,
    Archon,
    Loki200,
    Bifrost,
    Detonation,
    Boat,
    Requiem,
    PlainToHill,
    Nostalgia,
    Parallellines,
    Python,
    BlueStorm,
    Destination,
    Longinus,
    Medusa,
    Othello,
    ParanoidAnd,
    ReturnOfTheKing,
    Tiamat,
    Byzantium,
    Athena,
    Carthage,
    Hannibal,
    Katrina,
    Outsider,
    TauCross,
    Zodiac,
    MatchPoint,
    TearsOfTheMoon,
    MoonGlaive,
    HolyWorld,
    EnterTheDragon,
    Gaia,
    GodsGarden,
    Andromeda,
    Yellow,
    HeartBreakRidge,
    FightingSpirit,
    RushHour,
    Luna,
    Pelennor,
    JRMemory,
    Incubus,
    BladeStorm,
    LostTemple,
    BigGameHunters
  };

  String name;
  Type type;
  short[] size;
  int starts;
  int geysers;
  int minerals;
  int startingGeysers;
  int startingMinerals;


  public Map(String name, MapData map)
  {
    this.name = name;
    this.size = map.tiles;
    this.starts = map.startLocationList.size();
    this.geysers = map.geyserList.size();
    this.minerals = map.mineralFieldList.size();
    this.startingMinerals = 0;
    this.startingGeysers = 0;

    short[] start = map.mineralFieldList.get(0);
    for (short[] mineral : map.mineralFieldList)
      if (Math.pow(mineral[0] - start[0], 2) + Math.pow(mineral[1] - start[1], 2) < 50)
        startingMinerals++;
    for (short[] geyser : map.geyserList)
      if (Math.pow(geyser[0] - start[0], 2) + Math.pow(geyser[1] - start[1], 2) < 50)
        startingGeysers++;

    // TODO: Set the type (regexp on the name maybe ?);
  }
}
