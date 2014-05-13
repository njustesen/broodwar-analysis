package domain.buildorder;

import analyser.Action;
import analyser.Action.ActionType;

public class Build {

	public ActionType action;
	public Action.Type type;
	public int time;
	
	public Build(ActionType action, Action.Type type, int time) {
		super();
		this.action = action;
		this.time = time;
		this.type = type;
	}

	@Override
	public String toString() {
		return action.name();
	}
	
}
