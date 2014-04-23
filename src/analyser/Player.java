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

  public boolean win;
  public Race race;
  public String name;
  public List<Action> actions;
  public int actionNumber;
  public int actionsPerMin;

  public Player(String name, boolean win, byte race, int actionNumber, int actionsPerMin, List<parser.bwhf.model.Action> actions)
  {
    this.name = name;
    this.win = win;
    this.race = Race.class.getEnumConstants()[race];
    this.actionNumber = actionNumber;
    this.actionsPerMin = actionsPerMin;
    this.actions = new ArrayList<Action>();

    for (parser.bwhf.model.Action action : actions)
    {
      switch (action.actionNameIndex)
      {
        case 0x0c:
        case 0x1f:
        case 0x23:
        case 0x30:
        case 0x32:
          this.actions.add(new Action(action, race));
          break;
        default:
          break;
      }
    }
  }
}
