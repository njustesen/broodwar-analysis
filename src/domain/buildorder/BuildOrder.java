package domain.buildorder;

import java.util.ArrayList;
import java.util.List;

import clustering.ClusterPoint;
import clustering.distance.DistanceManager;

import analyser.Action;
import analyser.Player;
import analyser.Player.Race;

public class BuildOrder implements ClusterPoint {

	public Race race;
	public List<Build> builds;
	public boolean win;
	public Player player;
	
	public BuildOrder(Race race, List<Action> actions, boolean win) {
		super();
		this.race = race;
		this.win = win;
		this.builds = new ArrayList<Build>();
		for(Action action : actions){
			builds.add(new Build(action.actionType, action.type, action.frames));
		}
	}
	
	public BuildOrder(Player player) {
		super();
		this.player = player;
		this.race = player.race;
		this.win = player.win;
		this.builds = new ArrayList<Build>();
		for(Action action : player.actions)
			builds.add(new Build(action.actionType, action.type, action.frames));
	}
	
	public BuildOrder() {
		super();
		this.race = null;
		this.win = false;
		this.builds = new ArrayList<Build>();
	}

	public void filter(boolean units, boolean buildings, boolean research, boolean upgrades){
		
		List<Build> removed = new ArrayList<Build>();
		
		for(Build build : builds){
			if (!units && build.type == Action.Type.Unit)
				removed.add(build);
			if (!buildings && build.type == Action.Type.Building)
				removed.add(build);
			if (!research && build.type == Action.Type.Research)
				removed.add(build);
			if (!upgrades && build.type == Action.Type.Upgrade)
				removed.add(build);
		}
		
		for(Build build : removed){
			builds.remove(build);
		}
		
	}
	
	public String toString() {
		List<Build> b = new ArrayList<Build>();
		for(int i = 0; i < builds.size(); i++)
			b.add(builds.get(i));
		
		return b.toString();
	}

	public String toString(int maxLength) {
		List<Build> b = new ArrayList<Build>();
		for(int i = 0; i < Math.min(maxLength, builds.size()); i++)
			b.add(builds.get(i));
		
		return b.toString();
	}

	@Override
	public double distance(ClusterPoint other) {
		
		return DistanceManager.distance(this, ((BuildOrder)other));
		
	}
	
}
