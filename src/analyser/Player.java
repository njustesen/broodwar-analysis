import parser.bwhf.model.Action;

import java.util.List;

public class Player
{
  public enum Race
  {
    Zerg,
    Terran,
    Protoss
  };

  boolean win;
  Race race;
  String name;
  List<Action> actions;
  int actionNumber;
  int actionsPerMin;

  public Player(String name, byte race, int actionNumber, int actionsPerMin)
  {
    this.name = name;
    this.race = Race.class.getEnumConstants()[race];
  }

  public void addAction(parser.bwhf.model.Action action, Date time)
  {
    actions.add(new Action(time, action));
  }
}
