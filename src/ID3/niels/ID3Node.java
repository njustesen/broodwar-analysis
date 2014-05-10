package ID3.niels;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import analyser.Action.ActionType;
import analyser.Player;

public class ID3Node {

	private List<Player> players;
	private Map<ActionType, ID3Node> children;
	private Boolean won;
	public int wins;

	public ID3Node() {
		super();
		wins = 0;
	}

	public ID3Node(List<Player> players) {
		super();
		this.players = players;
		this.won = null;
	}

	public double value() {
		
		if (won != null)
			return won ? players.size() : 0;
		
		int wins = 0;
		for(ID3Node node : children.values())
			wins += node.recursiveWins();
		
		if (wins==0)
			return 0;
		
		return (double)wins/(double)players.size();
	}

	private int recursiveWins() {

		if (won != null)
			return won ? players.size() : 0;
		
		int w = wins;
		for(ID3Node node : children.values())
			w += node.recursiveWins();
		
		if (w==0)
			return w;
		
		return w;
		
	}

	
	public void trim(ID3Node root, double minSupport) {
		
		List<ID3Node> removed = new ArrayList<ID3Node>();
		
		for(ID3Node child : children.values()){
			double support = (double)players.size() / (double)root.players.size();
			if(support >= minSupport)
				child.trim(root, minSupport);
			else
				removed.add(child);
		}
		
		for(ActionType action : children.keySet())
			if(removed.contains(children.get(action)))
				children.remove(action);
		
	}
	
	public void print(int level, Object value) {
		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";

		String m = String.valueOf((players == null ? "null" : players.size()));
		String v = value == null ? "null" : ((Enum)value).name();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(3);
		str += "<node action='" + v + "' players='" + players.size() + "' value='" + df.format(value()) + "'>";
		
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

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
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
		String str = "\n";
		for(int i = 0; i < level; i++)
			str += "\t";

		String m = String.valueOf((players == null ? "null" : players.size()));
		String v = value == null ? "null" : ((Enum)value).name();
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(3);
		str += "<node action='" + v + "' players='" + players.size() + "' value='" + df.format(value()) + "'>";
		
		if (children != null){
			for(Object obj : children.keySet()){
				//str += "\t" + obj.getClass().getName();
				str += children.get(obj).toString(level+1, obj);
			}
		}

		str += "\n";
		for(int i = 0; i < level; i++){
			str += "\t";
		}
		str += "</node>";
		
		return str;
		
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
		if (type != null && children.containsKey(type)){
			return children.get(type).predictWin(depth+1, maxDepth, actions);
		}
		
		// Return average
		ID3Stats.depth+=depth;
		return value() >= 0.5;
		
	}

}