package ID3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ID3Node {

	private ID3Node parent;
	private int level;
	private boolean leaf = true;
	private Boolean edible;
	private String reasonForCutoff;
	private Class attribute;
	
	private HashMap <Object, ID3Node> children = new HashMap <Object, ID3Node>();
	
	public ID3Node(ID3Node n, int level){
		this.parent = n;
		this.level = level;
	}

	
	public void printNode(){
		
		for(int i = 0; i < level; i++){
			System.out.print("  ");
		}
		
//		System.out.println("node "+this+" children="+children.size()+" level="+level+" resonForCutoff="+reasonForCutoff+" edible="+edible);
		System.out.print("node "+this);
		System.out.print(" children="+children.size());
		System.out.print(" level="+level);
		System.out.print(" resonForCutoff="+reasonForCutoff);
			
		if(!leaf){
			System.out.print(" att="+attribute.getName());
		}else{
			System.out.print(" edible="+edible);
		}
		
		System.out.println();
		
		for(ID3Node c : children.values()){
			c.printNode();
		}

	}
	
	public ID3Node getParent(){
		return parent;
	}
	
	public HashMap <Object, ID3Node> getChildren(){
		return children;
	}
	
	public void addChild(Object obj,ID3Node child){
		System.out.println("child added");
		children.put(obj, child);
	}
	
	public boolean isLeaf(){
		return leaf;
	}
	
	public void notLeaf(){
		this.leaf = false;
	}
	
	public boolean isEdible(){
		return edible;
	}
	
	public void nodeIsEdible(){
		edible = true;
	}
	
	public void nodeIsNotEdible(){
		edible = false;
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
	
	public void setAttribute(Class selectedAttribute){
		this.attribute = selectedAttribute;
	}
	
	public Class getAttribute(){
		return attribute;
	}
	
	public ID3Node getChild(Object attributeValue){
		return children.get(attributeValue);
	}
}
