package analyser;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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

  public Player(String name, byte race, int actionNumber, int actionsPerMin, parser.bwhf.model.Action[] actions)
  {
    this.name = name;
    this.race = Race.class.getEnumConstants()[race];
    this.actionNumber = actionNumber;
    this.actionsPerMin = actionsPerMin;
    this.actions = new ArrayList<Action>();
    for (parser.bwhf.model.Action action : actions)
    {
      switch (action.actionNameIndex)
      {
        case 0x0c | 0x1f | 0x23 | 0x30 | 0x32 :
          this.actions.add(new Action(action));
          break;
        default:
          break;
      }
    }
  }
}
