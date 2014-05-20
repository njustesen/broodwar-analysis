package ID3;
import java.util.HashMap;

public class ID3Node {

	private ID3Node parent;
	private int level;
	private boolean leaf = true;
	private Boolean win;
	private String reasonForCutoff;
	private String attribute;
	private String actions;
	private double winningChance;
	
	private HashMap <Object, ID3Node> children = new HashMap <Object, ID3Node>();
	
	//constructor
	public ID3Node(ID3Node n, int level, String actions){
		this.parent = n;
		this.level = level;
		this.actions = actions;
	}

	//Calculates winning chance (between 0 and 1) and
	//triggers every child node
	public void calculateWinningChance(){
		
		if(!leaf){
			winningChance = 0;
			int divider = 0;
			for(ID3Node c : children.values()){
				c.calculateWinningChance();
				winningChance += c.getWinningChance();
				divider++;
			}
			winningChance = winningChance/divider;
		}
	}
	

	//Print Node - triggers every child node
	public void printNode(){
		
		for(int i = 0; i < level; i++){
			System.out.print("  ");
		}
		
		System.out.print("node "+this);
		System.out.print(" children="+children.size());
		System.out.print(" level="+level);
		System.out.print(" resonForCutoff="+reasonForCutoff);
		System.out.print(" gameAction="+actions);
		System.out.print(" winningChance="+winningChance);
		if(!leaf){
			System.out.print(" att="+attribute);
		}else{
			System.out.print(" win="+win);
		}
		
		System.out.println();
		
		for(ID3Node c : children.values()){
			c.printNode();
		}

	}
	
	
	//getters and setters
	public ID3Node getParent(){
		return parent;
	}
	
	public HashMap <Object, ID3Node> getChildren(){
		return children;
	}
	
	public void addChild(Object obj,ID3Node child){
		children.put(obj, child);
	}
	
	public boolean isLeaf(){
		return leaf;
	}
	
	public void notLeaf(){
		this.leaf = false;
	}
	
	public boolean isWin(){
		return win;
	}
	
	public void nodeIsWin(){
		win = true;
	}
	
	public void nodeIsNotWin(){
		win = false;
	}
	
	public int getLevel(){
		return level;
	}
	
	public String getReasonForCutoff(){
		return reasonForCutoff;
	}
	
	public void setReasonForCutoff(String reason){
		reasonForCutoff = reason;
	}
	
	public void setAttribute(String selectedAttribute){
		this.attribute = selectedAttribute;
	}
	
	public String getAttribute(){
		return attribute;
	}
	
	public ID3Node getChild(Object attributeValue){
		return children.get(attributeValue);
	}
	
	public String getActions(){
		return this.actions;
	}
	
	public void setActions(String actions){
		this.actions = actions;
	}
	
	public double getWinningChance(){
		return winningChance;
	}
	
	public void setWinningChance(double winningChance){
		this.winningChance = winningChance;
	}
}
