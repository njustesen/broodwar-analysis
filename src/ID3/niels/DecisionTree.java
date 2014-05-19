package ID3.niels;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import domain.buildorder.Build;
import domain.buildorder.BuildOrder;
import domain.cost.CostMap;

import analyser.Action;
import analyser.Action.ActionType;
import analyser.Player;

public class DecisionTree {

	private ID3Node root;

	public DecisionTree(ID3Node root) {
		super();
		this.root = root;
	}
	
	public List<BuildOrder> common(double minSupport){
		
		double minBuildOrders = minSupport * root.getBuildOrders().size();
		
		return root.common(minBuildOrders, null);
		
	}
	
	public List<BuildOrder> common(double minSupport, int maxDdepth){
		
		double minBuildOrders = minSupport * root.getBuildOrders().size();
		
		return root.common(minBuildOrders, maxDdepth, 0, null);
		
	}

	public void print() {
		root.print(0, null);
	}

	public void saveToFile(String filename) {
		
		try{
			// Create file 
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			//out.write(root.toString(0, null));
			root.toFile(out, 0, null);
			//Close the output stream
			out.close();
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
	}
	
	public void saveFmtToFile(String filename, int levels) {
		try{
			// Create file 
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);
			//out.write(root.toString(0, null));
			root.toFmt(out, 0, levels, null);
			//Close the output stream
			out.close();
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public boolean predictWin(int maxDepth, BuildOrder buildOrder) {
		
		List<ActionType> actions = new ArrayList<ActionType>();
		
		for(Build build : buildOrder.builds)
			actions.add(build.action);
		
		return root.predictWin(0, maxDepth, actions);
		
	}

	public ID3Node getRoot() {
		return root;
	}

	public void setRoot(ID3Node root) {
		this.root = root;
	}

	

}