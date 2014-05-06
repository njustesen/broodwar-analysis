package ID3.niels;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import analyser.Action.ActionType;
import analyser.Player;

public class ID3Node {

	private List<Player> players;
	private Map<ActionType, ID3Node> children;
	private Boolean won;

	public ID3Node() {
		super();
	}

	public ID3Node(List<Player> players) {
		super();
		this.players = players;
		this.won = null;
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
	
	public void printClean(String parent, Object value) {
		String str = "";
		String m = String.valueOf((players == null ? "null" : players.size()));
		String v = value == null ? "null" : ((Enum)value).name();
		if (won != null){
			str += parent + "\t" + v + "[" + (won ? 1 : 0) + "]";
		} else if (value != null){
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(4);
			str += parent + "\t" + v + "[" + df.format(recursiveValue()) + "]";
		}
		
		System.out.println(str);

		if (children != null){
			for(Object obj : children.keySet()){
				//str += "\t" + obj.getClass().getName();
				children.get(obj).printClean(str, obj);
			}
		}
	}

	public double recursiveValue() {
		
		if (won != null)
			return won ? 1 : 0;
		
		double wins = 0;
		for(ID3Node node : children.values())
			wins += node.recursiveValue();
		
		if (wins==0)
			return wins;
		
		return wins/children.values().size();
	}

	public void print(int level, Object value) {
		String str = "";
		for(int i = 0; i < level; i++)
			str += "\t";

		String m = String.valueOf((players == null ? "null" : players.size()));
		String v = value == null ? "null" : ((Enum)value).name();
		if (won != null){
			str += "<node value='" + v + " players='" + players.size() + "' win='" + won + "'>";
		} else {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(4);
			str += "<node action='" + v + "' players='" + players.size() + "' win='" + df.format(recursiveValue()) + "'>";
		}
		
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

}