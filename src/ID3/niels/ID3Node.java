package ID3.niels;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.buildorder.Build;
import domain.buildorder.BuildOrder;

import analyser.Action.ActionType;
import analyser.Player;

public class ID3Node {

	private List<BuildOrder> buildOrders;
	private Map<ActionType, ID3Node> children;
	private Boolean won;
	public int wins;
	private boolean trimmed;
	public ID3Node parent;

	public ID3Node() {
		super();
		this.wins = 0;
		this.trimmed = false;
		this.buildOrders = new ArrayList<BuildOrder>();
		this.children = new HashMap<ActionType, ID3Node>();
		this.parent = null;
	}
	
	public ID3Node(ID3Node parent, List<BuildOrder> players) {
		super();
		this.wins = 0;
		this.trimmed = false;
		this.buildOrders = players;
		this.children = new HashMap<ActionType, ID3Node>();
		this.parent = parent;
	}

	public ID3Node(ID3Node parent) {
		super();
		this.wins = 0;
		this.trimmed = false;
		this.buildOrders = new ArrayList<BuildOrder>();
		this.children = new HashMap<ActionType, ID3Node>();
		this.parent = parent;
	}

	public double value() {
		
		return (double)wins()/(double)buildOrders.size();
		
	}

	private int wins() {
		int w = 0;
		
		for(BuildOrder buildOrder : buildOrders)
			w += (buildOrder.win ? 1 : 0);
		
		return w;
	}

	private int recursiveWins() {

		if (won != null)
			return won ? buildOrders.size() : 0;
		
		int w = wins;
		for(ID3Node node : children.values())
			w += node.recursiveWins();
		
		return w;
		
	}

	
	public void trim(ID3Node root, double minSupport) {
		
		if(children == null || children.isEmpty())
			return;
		
		for(ID3Node child : children.values()){
			double support = (double)child.buildOrders.size() / (double)root.buildOrders.size();
			if(support >= minSupport){
				child.trim(root, minSupport);
			}else{
				child.trimmed = true;
				removeBuildOrders(child.buildOrders);
			}
		}
		
	}
	
	private void removeBuildOrders(List<BuildOrder> buildOrdersToRemove) {
		
		buildOrders.removeAll(buildOrdersToRemove);
		
		if (parent != null)
			parent.removeBuildOrders(buildOrdersToRemove);
		
	}

	public void print(int level, Object value) {
		
		if (trimmed)
			return;
		
		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";

		String m = String.valueOf((buildOrders == null ? "null" : buildOrders.size()));
		String v = value == null ? "null" : ((Enum)value).name();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(3);
		str += "<node action='" + v + "' players='" + buildOrders.size() + "' value='" + df.format(value()) + "'>";
		
		System.out.println(str);

		if (children != null){
			for(Object obj : children.keySet()){
				//str += "\t" + obj.getClass().getName();
				children.get(obj).print(level+1, obj);
			}
		}

		str = "";
		for(int i = 0; i < level; i++){
			str += "\t";
		}
		System.out.println(str + "</node>");

	}

	public List<BuildOrder> getBuildOrders() {
		return buildOrders;
	}

	public void setBuildOrders(List<BuildOrder> buildOrders) {
		this.buildOrders = buildOrders;
	}

	public Map<ActionType, ID3Node> getChildren() {
		return children;
	}

	public void setChildren(Map<ActionType, ID3Node> children) {
		this.children = children;
	}
	
	public boolean isWon() {
		return won;
	}

	public void setWon(boolean won) {
		this.won = won;
	}
	public String toString(int level, Object value) {
		
		if (trimmed)
			return "";
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("\n");
		for(int i = 0; i < level; i++)
			stringBuilder.append("\t");

		String m = String.valueOf((buildOrders == null ? "null" : buildOrders.size()));
		String a = value == null ? "Game over" : ((Enum)value).name();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(3);
		if (level == 0)
			stringBuilder.append("<node players='" + buildOrders.size() + "' value='" + df.format(value()) + "'>");
		else
			stringBuilder.append("<node action='" + a + "' players='" + buildOrders.size() + "' value='" + df.format(value()) + "'>");
		
		if (children != null)
			for(Object obj : children.keySet())
				stringBuilder.append(children.get(obj).toString(level+1, obj));

		stringBuilder.append("\n");
		for(int i = 0; i < level; i++){
			stringBuilder.append("\t");
		}
		stringBuilder.append("</node>");
		
		return stringBuilder.toString();
		
	}

	public boolean predictWin(int depth, int maxDepth, List<ActionType> actions) {
		
		if (won != null){
			ID3Stats.depth+=depth;
			return won;
		}
		
		ActionType type = null;
		if (depth < actions.size() && depth < maxDepth)
			type = actions.get(depth);
		
		// Go deeper
		if (type != null && children.containsKey(type))
			return children.get(type).predictWin(depth+1, maxDepth, actions);
		
		// Return average
		ID3Stats.depth+=depth;
		return value() >= 0.5;
		
	}

	public List<BuildOrder> common(double min, ActionType action) {
		
		List<BuildOrder> common = new ArrayList<BuildOrder>();
		
		for(ActionType childType : children.keySet()){
			ID3Node child = children.get(childType);
			if (child.buildOrders.size() >= min){
				List<BuildOrder> commonBuildOrders = child.common(min, childType);
				for(BuildOrder commonBuildOrder : commonBuildOrders){
					BuildOrder buildOrder = new BuildOrder();
					if (action != null)
						buildOrder.builds.add(new Build(action, null, 0));
					buildOrder.builds.addAll(commonBuildOrder.builds);
					common.add(buildOrder);
				}
			}
		}
		
		if (common.isEmpty() && buildOrders.size() >= min){
			BuildOrder buildOrder = new BuildOrder();
			if (action != null)
				buildOrder.builds.add(new Build(action, null, 0));
			common.add(buildOrder);
		}
		
		return common;
	}

	public void toFile(BufferedWriter out, int level, Object value) throws IOException {
		
		if (trimmed)
			return;
		
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("\n");
		for(int i = 0; i < level; i++)
			stringBuilder.append("\t");

		String m = String.valueOf((buildOrders == null ? "null" : buildOrders.size()));
		String a = value == null ? "Game over" : ((Enum)value).name();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(3);
		if (level == 0)
			stringBuilder.append("<node players='" + buildOrders.size() + "' value='" + df.format(value()) + "'>");
		else
			stringBuilder.append("<node action='" + a + "' players='" + buildOrders.size() + "' value='" + df.format(value()) + "'>");
		
		out.write(stringBuilder.toString());
		stringBuilder = new StringBuilder();
		
		if (children != null)
			for(Object obj : children.keySet())
				children.get(obj).toFile(out, level+1, obj);

		stringBuilder.append("\n");
		for(int i = 0; i < level; i++){
			stringBuilder.append("\t");
		}
		stringBuilder.append("</node>");
		
		out.write(stringBuilder.toString());
		
	}

}