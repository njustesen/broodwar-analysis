package domain.naming;

import java.util.ArrayList;
import java.util.List;

import analyser.Action;
import analyser.Action.ActionType;
import analyser.Player.Race;

public class BuildOrderNamer {

	public static String name(List<Action> actions, String sep){
		
		if (actions.isEmpty())
			return "None";
		
		if (actions.get(0).race == Race.Protoss.ordinal())
			return protossName(actions, sep);
		if (actions.get(0).race == Race.Terran.ordinal())
			return terranName(actions, sep);
		if (actions.get(0).race == Race.Zerg.ordinal())
			return zergName(actions, sep);
		
		return null;
		
	}

	private static String zergName(List<Action> actions, String sep) {
		
		List<String> lines = new ArrayList<String>();
		
		int workers = 0;
		for(Action action : actions){
			if (action.actionType == ActionType.Drone)
				workers++;
			else
				lines.add(workers + " " + action.actionType.name());
		}
		
		String all = "";
		for(String line : lines){
			all += line;
			if (lines.indexOf(line) != lines.size()-1){
				all += sep;
			}
		}
		
		return all;
	}

	private static String terranName(List<Action> actions, String sep) {
		
		List<String> lines = new ArrayList<String>();
		
		int workers = 0;
		for(Action action : actions){
			if (action.actionType == ActionType.SCV)
				workers++;
			else
				lines.add(workers + " " + action.actionType.name());
		}
		
		String all = "";
		for(String line : lines){
			all += line;
			if (lines.indexOf(line) != lines.size()-1){
				all += sep;
			}
		}
		return all;
	}

	private static String protossName(List<Action> actions, String sep) {
		
		List<String> lines = new ArrayList<String>();
		
		int workers = 0;
		for(Action action : actions){
			if (action.actionType == ActionType.Probe)
				workers++;
			else
				lines.add(workers + " " + action.actionType.name());
		}
		
		String all = "";
		for(String line : lines){
			all += line;
			if (lines.indexOf(line) != lines.size()-1){
				all += sep;
			}
		}
		return all;
		
	}
	
}
