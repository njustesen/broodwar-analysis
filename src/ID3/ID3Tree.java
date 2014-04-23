package ID3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import analyser.Action;
import analyser.Action.ActionType;
import analyser.Match;

import parser.bwhf.model.Replay;

public class ID3Tree {

	private ID3Node root;
	private final int analyzeLength = 10;
	ArrayList <String> attributes = new ArrayList<String>();
	
	public ID3Tree(List<String[]> examples){
		this.root = new ID3Node(null, 0);
		for(int i = 0; i < analyzeLength; i++){
			attributes.add(((Integer)i).toString());
		}
		generateTree(examples, attributes, root);
	}

/*
	public boolean safeToEat(Mushroom m){
		
	}
*/	
	public static void generateTree(List<String[]> examples, List <String> attributes, ID3Node thisNode){
		System.out.println("examples.size = " + examples.size());
		//Check if all shrooms are poisonous/edible
		//All samples for a given node belong to the same class
		boolean allWinners = true;
		boolean allLosers = true;
		for(String[] s: examples){
			if(s[s.length] == "win"){
				allLosers = false;
			}else if(s[s.length] == "loss"){
				allWinners = false;
			}else{
				System.out.println("wrong index - s[s.length] is neither win or loss");
			}
		}
				
		
		//There are no remaining attributes for further partitoning
		if(attributes.isEmpty()){
			thisNode.setReasonForCutoff("'ATTRIBUTES EMPTY'");
			int winCount = 0;
			for(String[] s: examples){
				if(s[s.length] == "win"){
					winCount++;
				}else if(s[s.length] == "loss"){
					winCount--;
				}else{
					System.out.println("wrong index - s[s.length] is neither win or loss");
				}
			}			
			if(winCount < 0 ){
				thisNode.nodeIsNotWin();
			}else{
				thisNode.nodeIsWin();
			}
		}
	
		//all players are winners
		if(allWinners){
			thisNode.setReasonForCutoff("ALL WINNERS");
			//System.out.println("all edible");
			thisNode.nodeIsWin();
			return;
		}
		
		//all players are losers
		if(allLosers){
			thisNode.setReasonForCutoff("ALL LOSERS");
			//System.out.println("all poisonous");
			thisNode.nodeIsNotWin();
			return;
		}
		
		//Select Attribute (random)
		//Random  r = new Random();
		//int index = r.nextInt(attributes.size());
		//Object selectedAttribute = attributes.get(index);
		String selectedAttribute = null;
		double highestGain = Integer.MIN_VALUE;
		for(String att: attributes){
			double gain = getInfoGain(att, examples);
			System.out.println("GAIN="+gain);
			if(gain > highestGain){
				System.out.println("new highest GAIN="+gain);
				selectedAttribute = att;
				highestGain = gain;
			}
		}
		
		thisNode.setAttribute(selectedAttribute);
		
		for(ActionType at : ActionType.values()){
			//make sublists
			List <String[]> subExamples = getSubset(examples, selectedAttribute, at.toString());
			List subAttributes = getSubset(attributes, selectedAttribute);
			//create new node
			ID3Node child = new ID3Node(thisNode, thisNode.getLevel()+1);
			thisNode.addChild(at, child);
			thisNode.notLeaf();
			//recursive call
			generateTree(subExamples, subAttributes, child);
		}
		
		return;
	}
	
	public void printTree(){
		System.out.println();
		System.out.println("- -- --- ---- ----- ------ FINAL TREE START ------ ----- ---- --- -- -");
		root.printNode();
		System.out.println("- -- --- ---- ----- ------ FINAL TREE END ------ ----- ---- --- -- -");
	}
	
	public static double getInfoGain(String att, List<String[]> examples){
		double info = getInfo(examples);
		double infoA = getInfoA(att, examples);
		double infoGain = info - infoA;
		System.out.println("info="+info+" infoA="+infoA+" infoGain="+infoGain);
		return infoGain;
	}
	
	public static double getInfo(List<String[]> examples){
		System.out.println("print TEST "+testInfo());
		if(examples.isEmpty()){
			return 1;
		}
		double info = 0;
	//	System.out.println("examples="+examples);
		double D = examples.size();
		double DjWin = 0;
		double DjLoss = 0;
			
		for(String[] s: examples){
			if(s[s.length].equals("win")){
				DjWin++;
			}else{
				DjLoss++;
			}	
		}
		
		//(Math.log(pi)/Math.log(2)); ??
		double infoWin = ((DjWin/D) * (Math.log(DjWin/D)/Math.log(2)));
		double infoLoss = ((DjLoss/D) * (Math.log(DjLoss/D)/Math.log(2)));
		
		if (DjWin == 0)
			infoWin = 0;
		if (DjLoss == 0)
			infoLoss = 0;

		info = -infoWin-infoLoss;

		return info;
/*		
		double pi = (Dj/D);
		double logpi = Math.log(pi);
		double logpilog2 = (Math.log(pi)/Math.log(2));
		info -= pi*(Math.log(pi)/Math.log(2));
		//System.out.println("getInfo  Dj="+Dj+"  D="+D+"  pi="+pi+"  logpi="+logpi+" logpilog2="+logpilog2+"  info="+info);
		
		return info;
*/
	}
	
	public static double getInfoA(String att, List<String[]> examples){
		
		if(examples.isEmpty()){
			return 1;
		}
		
		double infoA = 0;
		List<String[]> subExamples = new ArrayList<String[]>();

		//for each (unique) attribute value
		String[] unique = getUniqueEntries(att, examples);
		
		for(ActionType at : ActionType.values()){
			
			double D = examples.size();
			double Dj = 0;
			for(String[] s: examples){
				if(s[Integer.parseInt(att)].equals(at)){
					Dj++;
					subExamples.add(s);
				}
			}

			double division = (Dj/D);
			double subEx = getInfo(subExamples);
			double result = division * subEx;
			infoA = infoA + result;
		}

		return infoA;
	}

	private static String[] getUniqueEntries(String att, List<String[]> examples){
		
		Set<String> unique = new HashSet<String>();
		
		for(int i = 0; i < examples.size(); i++){
			unique.add(examples.get(i)[Integer.parseInt(att)]);
		}
		
		return (String[]) unique.toArray();
	}
	
	private static List<String[]> getSubset(List<String[]> examples, String attribute, String value) {

		ArrayList<String[]> subset = new ArrayList<String[]>();

		for(String[] s : examples){
			if (s[Integer.parseInt(attribute)].equals(value))
				subset.add(s);
		}

		return subset;
	}
	
	private static List<String> getSubset(List<String> attributes, Object attribute){
		
		System.out.println("attributes="+attributes);
		System.out.println("size="+attributes.size());
		System.out.println("attribute="+attribute);
		
		List<String> subset = new ArrayList<String>(attributes);
		
		subset.remove(attribute);
		
		return subset;
	}
	
	public ID3Node getRoot(){
		return root;
	}
	
	public static double testInfo(){
		double info = 0;
		double Dj = 7;
		double D = 17;
		//return info -= pi*(Math.log(pi)/Math.log(2));
		double tal = (Dj/D);
		return tal;
	}

}
