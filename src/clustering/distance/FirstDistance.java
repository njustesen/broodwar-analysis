package clustering.distance;

import java.util.ArrayList;
import java.util.List;

import analyser.Action;
import analyser.Action.Type;
import analyser.Player;

/**
 * Wagner-Fisher algorithm for calculating the edit distance for two build orders.
 *
 * @author Niels
 *
 */
public class FirstDistance {

	boolean units;
	boolean buildings;
	boolean upgrades;
	boolean research;
	private int base;

	public FirstDistance(boolean units, boolean buildings, boolean upgrades, boolean research, int base) {
		super();
		this.units = units;
		this.buildings = buildings;
		this.upgrades = upgrades;
		this.research = research;
		this.base = base;
	}

	public double distance(Player a, Player b){

		List<Action> buildOrderA = buildOrder(a);
		List<Action> buildOrderB = buildOrder(b);

		int d = base;
		for(int i = 0; i < buildOrderA.size(); i++){

			if (buildOrderA.get(i).actionType == buildOrderB.get(i).actionType){
				d--;
			} else {
				break;
			}

		}

		return d;

	}

	private List<Action> buildOrder(Player a) {
		List<Action> actions = new ArrayList<Action>();

		for(Action action : a.getActions()){
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

	public boolean isUnits() {
		return units;
	}

	public void setUnits(boolean units) {
		this.units = units;
	}

	public boolean isBuildings() {
		return buildings;
	}

	public void setBuildings(boolean buildings) {
		this.buildings = buildings;
	}

	public boolean isUpgrades() {
		return upgrades;
	}

	public void setUpgrades(boolean upgrades) {
		this.upgrades = upgrades;
	}

	public boolean isResearch() {
		return research;
	}

	public void setResearch(boolean research) {
		this.research = research;
	}

}
