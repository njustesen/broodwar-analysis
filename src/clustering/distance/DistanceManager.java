package clustering.distance;

import analyser.Player;

public class DistanceManager {
	
	public static DISTANCE_FUNCTION distanceFunction;
	public static EditDistance editDistance;
	public static FirstDistance firstDistance;
	
	public static double distance(Player a, Player b) {
		
		if (distanceFunction == DISTANCE_FUNCTION.EDIT_DISTANCE){
			return editDistance.distance(a, b);
		}
		
		if (distanceFunction == DISTANCE_FUNCTION.FIRST_DISTANCE){
			return firstDistance.distance(a, b);
		}
		
		throw new IllegalStateException("No distance function set!");
	}

	
	
}
