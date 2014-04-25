package clustering.editdistance;

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
public class BuildOrderDistance {

	double discountRate;
	boolean units;
	boolean buildings;
	boolean upgrades;
	boolean research;
	
	public BuildOrderDistance(double discountRate, boolean units,
			boolean buildings, boolean upgrades, boolean research) {
		super();
		this.discountRate = discountRate;
		this.units = units;
		this.buildings = buildings;
		this.upgrades = upgrades;
		this.research = research;
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
		int m = s.size();
		int n = t.size();
		
		//declare int d[0..m, 0..n]
		double[][] d = new double[m+1][n+1];		
	  
		//for i from 0 to m
		for(int i = 0; i <= m; i++){
			//d[i, 0] := i // the distance of any first string to an empty second string
			d[i][0] = i;
		}
		
		//for j from 0 to n
		for(int j = 0; j <= m; j++){
			//d[0, j] := j // the distance of any second string to an empty first string
			d[0][j] = j;
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
					//else
					//d[i, j] := minimum
					//(
					//d[i-1, j] + 1,  // a deletion
                    //d[i, j-1] + 1,  // an insertion
                    //d[i-1, j-1] + 1 // a substitution
					//)
					double costA = cost(s.get(i-1));
					double costB = cost(t.get(j-1));
					double deletion = d[i-1][j] + 1;
					deletion = deletion + costA-1;
					double insertion = d[i][j-1] + 1;
					insertion = insertion + costB -1;
					double substitution = d[i-1][j-1] + 1;
					substitution = substitution + avg(costA,costB) -1;
					d[i][j] = deletion;
					if (insertion < d[i][j])
						d[i][j] = insertion;
					if (substitution < d[i][j])
						d[i][j] = substitution;
					
					// Add discount
					d[i][j] = d[i][j] * (discountRate * n/j);
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

	public double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
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
