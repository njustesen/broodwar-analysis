package analyser;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import analyser.Action.Type;

import clustering.ClusterPoint;
import clustering.distance.DistanceManager;
import clustering.distance.EditDistance;
import clustering.distance.FirstDistance;

public class Player implements ClusterPoint
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

  public Player(String name, boolean win, Race race, int actionNumber, int actionsPerMin, List<Action> actions)
  {
    this.name = name;
    this.win = win;
    this.race = race;
    this.actionNumber = actionNumber;
    this.actionsPerMin = actionsPerMin;
    this.actions = actions;
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

@Override
public String toString() {

	return "Player [win=" + win + ", race=" + race + ", actions=" + selectActions(false, true,true,true)
			+ "]";
}

public List<Action> selectActions(boolean units, boolean buildings, boolean research, boolean upgrades) {
	List<Action> actions = new ArrayList<Action>();

	for(Action action : this.actions){
		if (action.type == Type.Building && buildings)
			actions.add(action);
		if (action.type == Type.Unit && units)
			actions.add(action);
		if (action.type == Type.Research && research)
			actions.add(action);
		if (action.type == Type.Upgrade && upgrades)
			actions.add(action);
	}

	return actions;
}

@Override
public double distance(ClusterPoint other) {

	//return new EditDistance(true, true, true, true, false, 10).distance((Player)other,this,30);
	return DistanceManager.distance(this, ((Player)other));

}

}
