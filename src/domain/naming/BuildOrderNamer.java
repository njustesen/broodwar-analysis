package domain.naming;

import java.util.ArrayList;
import java.util.List;

import analyser.Action;
import analyser.Action.ActionType;
import analyser.Action.Type;
import analyser.Player.Race;

public class BuildOrderNamer {
	
	public String name(List<Action> actions, String sep){
		
		if (actions.isEmpty())
			return "None";
		
		List<String> lines = new ArrayList<String>();
		
		int workers = 0;
		for(Action action : actions){
			if (action.actionType == ActionType.Drone || 
					action.actionType == ActionType.Probe ||
					action.actionType == ActionType.SCV )
				workers++;
			else
				lines.add(workers + " " + action.actionType.name());
				
		}
		
		String all = "";
		boolean started = false;
		for(String line : lines){
			if (started)
				all += sep;
			else 
				started = true;
			all += line;
		}
		
		return all;
		
	}
	
	public String buildings(List<Action> actions, String sep, int n){
		
		if (actions.isEmpty())
			return "None";
		
		List<String> lines = new ArrayList<String>();

		for(Action action : actions){
			if (action.type == Type.Building)
				lines.add(action.actionType.name());
			if (lines.size() >= n)
				break;
		}
		
		String all = "";
		boolean started = false;
		for(String line : lines){
			if (started)
				all += sep;
			else 
				started = true;
			all += line;
		}
		
		return all;
		
	}

}
