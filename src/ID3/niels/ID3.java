package ID3.niels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domain.cost.CostMap;

import analyser.Action.ActionType;
import analyser.Player;
import analyser.Player.Race;

public class ID3 {
	
	DecisionTree tree;
	
	public void generateDecisionTree(List<Player> trainingTuples, Race race){
		
		// Protoss actions
		List<ActionType> actions = new ArrayList<ActionType>(CostMap.costs.keySet());
		
		ID3Node root = induceDecisionTree(trainingTuples, actions, 0);
		tree = new DecisionTree(root);
		
	}
	
	private ID3Node induceDecisionTree(List<Player> players, List<ActionType> actions, int depth) {
		
		ID3Node node = new ID3Node();
		node.setPlayers(players);
		
		if (sameClass(players)){
			node.setWon(players.get(0).win);
			return node;
		}
		
		if (actions.isEmpty()){
			boolean edible = isMajorityWinning(players);
			node.setWon(edible);
			return node;
		}
		
		// Get next action
		node.setChildren(new HashMap<ActionType, ID3Node>());
		for(ActionType action : actions){
			
			List<Player> subset = createSubset(players, action, depth);
			
			if(!subset.isEmpty()){
				ID3Node child = induceDecisionTree(subset, clone(actions), depth+1);
				node.getChildren().put(action, child);
			}
			
		}
	
		return node;
	}

	private List<ActionType> clone(List<ActionType> actions) {
		List<ActionType> clone = new ArrayList<ActionType>();
		for(ActionType obj : actions){
			clone.add(obj);
		}
		return clone;
	}

	private List<Player> createSubset(List<Player> players, ActionType action, int depth) {
		
		List<Player> subset = new ArrayList<Player>();
		
		for(Player p : players){
			if (p.getActions().size() > depth && p.actions.get(depth).actionType == action)
				subset.add(p);
		}
		
		return subset;
	}
	
	private boolean isMajorityWinning(List<Player> players) {
		int e = 0;
		for (Player p : players){
			if (p.win)
				e++;
		}
		return e > players.size() / 2;
	}

	private boolean sameClass(List<Player> players) {
		Boolean label = null;
		for(Player p : players){
			if (label == null)
				label = p.win;
			else if (label != p.win)
				return false;
		}
		return true;
	}

//	public boolean isEdible(Player player){
//		
//		if (tree == null)
//			throw new IllegalStateException("ID3 not set up!");
//		
//		return tree.isEdible(player);
//		
//	}

	public DecisionTree getTree() {
		return tree;
	}

	public void setTree(DecisionTree tree) {
		this.tree = tree;
	}
	
}
