package analyser;

import java.util.Date;
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

  public void addAction(Date time, parser.bwhf.model.Action action)
  {
    actions.add(new Action(time, action));
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
