package ID3.niels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domain.buildorder.BuildOrder;
import domain.cost.CostMap;

import analyser.Action.ActionType;
import analyser.Player;
import analyser.Player.Race;

public class ID3 {
	
	DecisionTree tree;
	
	public void generateDecisionTree(List<BuildOrder> trainingTuples){
		
		List<ActionType> actions = new ArrayList<ActionType>(CostMap.costs.keySet());

		actions.add(null); // Null action = game over
		
		ID3Node root = induceDecisionTree(trainingTuples, actions, 0, null);
		tree = new DecisionTree(root);
		
	}
	
	private ID3Node induceDecisionTree(List<BuildOrder> buildOrders, List<ActionType> actions, int depth, ID3Node parent) {
		
		ID3Node node = new ID3Node(parent);
		node.setBuildOrders(buildOrders);
		
		// If all won or lost, stop heres
		if (sameClass(buildOrders)){
			node.setWon(buildOrders.get(0).win);
			return node;
		}
			
		// Get next action
		node.setChildren(new HashMap<ActionType, ID3Node>());
		for(ActionType action : actions){
			
			List<BuildOrder> subset = createSubset(buildOrders, action, depth);
			
			if(!subset.isEmpty()){
				ID3Node child = null;
				if (action == null)
					child = new ID3Node(node, subset);
				else
					child = induceDecisionTree(subset, actions, depth+1, node);
				node.getChildren().put(action, child);
			}
			
		}
	
		return node;
	}

	private List<BuildOrder> createSubset(List<BuildOrder> buildOrders, ActionType action, int depth) {
		
		List<BuildOrder> subset = new ArrayList<BuildOrder>();
		
		for(BuildOrder b : buildOrders)
			if (action == null && b.builds.size() <= depth)
				subset.add(b);
			else if (action != null && b.builds.size() > depth && b.builds.get(depth).action == action)
				subset.add(b);
		
		return subset;
	}
	
	private boolean sameClass(List<BuildOrder> buildOrders) {
		Boolean label = null;
		for(BuildOrder b : buildOrders){
			if (label == null)
				label = b.win;
			else if (label != b.win)
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

	public boolean wins(int depth, BuildOrder buildOrder) {
		
		ID3Stats.searches++;
		
		return tree.predictWin(depth, buildOrder);
		
	}
	
}
