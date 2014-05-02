package clustering.distance;

import java.util.ArrayList;
import java.util.List;

import domain.cost.CostMap;

import analyser.Action;
import analyser.Action.Type;
import analyser.Player;

/**
 * Wagner–Fisher algorithm for calculating the edit distance for two build orders.
 * 
 * @author Niels
 *
 */
public class EditDistance {

	int discount;
	boolean units;
	boolean buildings;
	boolean upgrades;
	boolean research;
	private boolean cost;
	private int max;
	
	public EditDistance(boolean units,
			boolean buildings, boolean upgrades, boolean research, boolean cost, int discount, int max) {
		super();
		this.discount = discount;
		this.units = units;
		this.buildings = buildings;
		this.upgrades = upgrades;
		this.research = research;
		this.cost = cost;
		this.max = max;
	}
	
	public double distance(Player a, Player b){
		
		List<Action> buildOrderA = buildOrder(a);
		List<Action> buildOrderB = buildOrder(b);
		
		// for all i and j, d[i,j] will hold the Levenshtein distance between
		// the first i characters of s and the first j characters of t;
		// note that d has (m+1)x(n+1) values
		
		// s and t
		List<Action> s = buildOrderA;
		List<Action> t = buildOrderB;
		
		// m and n
		int m = Math.min(max, s.size());
		int n = Math.min(max, t.size());
		
		//declare int d[0..m, 0..n]
		double[][] d = new double[m+1][n+1];		
		d[0][0] = 0;
		
		//for i from 0 to m
		for(int i = 1; i <= m; i++){
			//d[i, 0] := i // the distance of any first string to an empty second string
			//d[i][0] = i;
			d[i][0] = d[i - 1][0] +  cost(s.get(i-1));	// + cost of deletion
		}
		
		//for j from 0 to n
		for(int j = 1; j <= n; j++){
			//d[0, j] := j // the distance of any second string to an empty first string
			//d[0][j] = j;
			d[0][j] = d[0][j - 1] + cost(t.get(j-1));	// + cost of insertion
		}
		
		//for j from 1 to n
		for(int j = 1; j <= n; j++){
		    //for i from 1 to m
			for(int i = 1; i <= m; i++){
		    	
				//if s[i] = t[j] then  
				if (s.get(i-1).actionType == t.get(j-1).actionType){
					// d[i, j] := d[i-1, j-1]       // no operation required
					d[i][j] = d[i-1][j-1];
				} else {
					//double costA = cost(s.get(i-1));
					//double costB = cost(t.get(j-1));
					double deletion = d[i-1][j] + cost(s.get(i-1)); // + cost of deletion
					//deletion = deletion + costA;
					double insertion = d[i][j-1] + cost(t.get(j-1)); // + cost of insertion
					//insertion = insertion + costB;
					double substitution = d[i-1][j-1] + cost(s.get(i-1)) + cost(t.get(j-1)); // + cost of deletion and insertion
					//substitution = substitution + avg(costA, costB);
					d[i][j] = deletion;
					if (insertion < d[i][j])
						d[i][j] = insertion;
					if (substitution < d[i][j])
						d[i][j] = substitution;
					
					// Add discount
					if (discount != 0)
						d[i][j] = d[i][j] * (double)((double)discount/(double)j);
				}
			}
		}
		   
		//return d[m,n]
		return d[m][n];
		
	}

	private double avg(double a, double b) {
		return (a + b) / 2;
	}

	private double cost(Action action) {
		if (!cost){
			return 1;
		}
		if (CostMap.costs.containsKey(action.actionType))
			return  CostMap.costs.get(action.actionType).getMinerals() + 
					CostMap.costs.get(action.actionType).getGas();
		return -1;
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

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
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
