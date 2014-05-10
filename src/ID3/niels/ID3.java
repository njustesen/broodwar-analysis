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
		
		// If all won or lost, stop heres
		if (sameClass(players)){
			node.setWon(players.get(0).win);
			return node;
		}
			
		// Player with no more actions should stop here
		List<Player> continued = new ArrayList<Player>();
		for(Player p : players){
			if (p.getActions().size() < depth){
				if (p.win)	
					node.wins++;
				else
					node.wins--;
			} else {
				continued.add(p);
			}
		}
		
		// Get next action
		node.setChildren(new HashMap<ActionType, ID3Node>());
		for(ActionType action : actions){
			
			List<Player> subset = createSubset(continued, action, depth);
			
			if(!subset.isEmpty()){
				ID3Node child = induceDecisionTree(subset, actions, depth+1);
				node.getChildren().put(action, child);
			}
			
		}
	
		return node;
	}

	private List<Player> createSubset(List<Player> players, ActionType action, int depth) {
		
		List<Player> subset = new ArrayList<Player>();
		
		for(Player p : players)
			if (p.getActions().size() > depth && p.actions.get(depth).actionType == action)
				subset.add(p);
		
		return subset;
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

	public DecisionTree getTree() {
		return tree;
	}

	public void setTree(DecisionTree tree) {
		this.tree = tree;
	}

	public boolean wins(int depth, Player player) {
		
		ID3Stats.searches++;
		
		return tree.predictWin(depth, player);
		
	}
	
}
