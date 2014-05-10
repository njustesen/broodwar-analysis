package ID3.niels;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import domain.cost.CostMap;

import analyser.Action;
import analyser.Action.ActionType;
import analyser.Player;

public class DecisionTree {

	ID3Node root;

	public DecisionTree(ID3Node root) {
		super();
		this.root = root;
	}

	public void trim(double minSupport){
		
		root.trim(root, minSupport);
		
	}

	public void print() {
		root.print(0, null);
	}

	public void saveToFile(String filename) {
		
		try{
			// Create file 
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(root.toString(0, null));
			//Close the output stream
			out.close();
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
	}

	public boolean predictWin(int maxDepth, Player player) {
		
		List<ActionType> actions = new ArrayList<ActionType>();
		
		for(Action action : player.actions)
			actions.add(action.actionType);
		
		return root.predictWin(0, maxDepth, actions);
		
	}

}