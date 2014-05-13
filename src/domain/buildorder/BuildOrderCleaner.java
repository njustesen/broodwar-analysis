package domain.buildorder;

import java.util.ArrayList;
import java.util.List;

import domain.cost.CostMap;

import analyser.Action;
import analyser.Action.ActionType;

public class BuildOrderCleaner {

	private static final List<ActionType> utilities;
	private static final int TIME_BLOCK = 2000;
	static {
		utilities = new ArrayList<ActionType>();
		utilities.add(ActionType.PhotonCannon);
		utilities.add(ActionType.MissileTurret);
		utilities.add(ActionType.CreepColony);
		utilities.add(ActionType.SunkenColony);
		utilities.add(ActionType.SporeColony);
	}

	public static void clean(BuildOrder buildOrder){
		
		List<Build> removed = new ArrayList<Build>();
		
		for(int b = 0; b < buildOrder.builds.size(); b++){
			Build build = buildOrder.builds.get(b);
			if (b+1 < buildOrder.builds.size()){
				Build next = buildOrder.builds.get(b+1);
				
				if (build.action == next.action){
					if (build.type == Action.Type.Upgrade || build.type == Action.Type.Research)
						removed.add(build);
					
					if (build.type == Action.Type.Building){
						double limit = 0;
						if (CostMap.costs.containsKey(build.action)){
							limit = CostMap.costs.get(build.action).getTotal();
						} else {
							//System.out.println("Unknown build " + build.action.name());
							removed.add(build);
						}
						if (utilities.contains(build.action))
							limit = limit / (build.time / TIME_BLOCK);
							
						if (next.time - build.time < limit)
							removed.add(build);
						
					}
				}
			}
		}
		
		for(Build build : removed){
			buildOrder.builds.remove(build);
		}
		
	}
}
