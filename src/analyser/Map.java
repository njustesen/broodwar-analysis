package analyser;

import parser.bwhf.model.MapData;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Map
{
  public enum Type
  {
    None,
    Andromedal,
    Chupung_Ryeung,
    Colosseum,
    Hunters,
    Archon,
    Loki200,
    Bifrost,
    Detonation,
    Boat,
    Requiem,
    Plain_To_Hill,
    Nostalgia,
    Parallellines,
    Python,
    BlueStorm,
    Destination,
    Longinus,
    Medusa,
    Othello,
    Paranoid_And,
    Return_Of_The_King,
    Tiamat,
    Byzantium,
    Athena,
    Carthage,
    Hannibal,
    Katrina,
    Outsider,
    TauCross,
    Zodiac,
    Match_Point,
    Tears_Of_The_Moon,
    Moon_Glaive,
    Holy_World,
    Enter_The_Dragon,
    Gaia,
    GodsGarden,
    Andromeda,
    Yellow,
    Heart_Break_Ridge,
    Fighting_Spirit,
    RushHour,
    Luna,
    Pelennor,
    JRMemory,
    Incubus,
    Blade_Storm,
    Lost_Temple,
    Big_Game_Hunters
  };

  public String name;
  public Type type;
  public short[] size;
  public int starts;
  public int geysers;
  public int minerals;
  public int startingGeysers;
  public int startingMinerals;


  public Map(String name, short x, short y, MapData map)
  {
    this.type = Type.None;
    this.name = name;
    this.size = new short[]{x, y};
    if (map != null)
    {
      this.starts = map.startLocationList.size();
      this.geysers = map.geyserList.size();
      this.minerals = map.mineralFieldList.size();
      this.startingMinerals = 0;
      this.startingGeysers = 0;

      short[] start = map.mineralFieldList.get(0);
      for (short[] mineral : map.mineralFieldList)
        if (Math.pow(mineral[0] - start[0], 2) + Math.pow(mineral[1] - start[1], 2) < 50000)
          startingMinerals++;
      for (short[] geyser : map.geyserList)
        if (Math.pow(geyser[0] - start[0], 2) + Math.pow(geyser[1] - start[1], 2) < 50000)
          startingGeysers++;

      for (Type type : Type.class.getEnumConstants())
        if (Pattern.compile(type.name(), Pattern.CASE_INSENSITIVE).matcher(name).find())
          this.type = type;
        else if (Pattern.compile(type.name().replaceAll("_", ""), Pattern.CASE_INSENSITIVE).matcher(name).find())
          this.type = type;
        else if (Pattern.compile(type.name().replaceAll("_", " "), Pattern.CASE_INSENSITIVE).matcher(name).find())
          this.type = type;
    }
  }
}
