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
        case 0x0c:
        case 0x1f:
        case 0x23:
        case 0x30:
        case 0x32:
          this.actions.add(new Action(action));
          break;
        default:
          break;
      }
    }
  }

public boolean isWin() {
	return win;
}

public void setWin(boolean win) {
	this.win = win;
}

public Race getRace() {
	return race;
}

public void setRace(Race race) {
	this.race = race;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public List<Action> getActions() {
	return actions;
}

public void setActions(List<Action> actions) {
	this.actions = actions;
}

public int getActionNumber() {
	return actionNumber;
}

public void setActionNumber(int actionNumber) {
	this.actionNumber = actionNumber;
}

public int getActionsPerMin() {
	return actionsPerMin;
}

public void setActionsPerMin(int actionsPerMin) {
	this.actionsPerMin = actionsPerMin;
}



}
