package ID3;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//constructor
public class ID3Tree {

	private ID3Node root;
	private long timeBuild;
	private static int treeSize;

	
	ArrayList <String> attributes = new ArrayList<String>();
	
	public ID3Tree(List<String[]> examples, boolean infoGain, int analyzeLength){
		Long begin = System.currentTimeMillis();
		this.root = new ID3Node(null, 0, null);
		for(int i = 0; i < analyzeLength; i++){
			attributes.add(((Integer)i).toString());
		}
		generateTree(examples, attributes, root, infoGain);
		calculateWinningChance();
		long end = System.currentTimeMillis();
		timeBuild = end - begin;
	}

	public static void generateTree(List<String[]> examples, List <String> attributes, ID3Node currentNode, boolean infoGain){
		
		//Check if all matches belong to same class (win/lose)
		boolean allWinners = true;
		boolean allLosers = true;
		for(String[] s: examples){
			if(s[s.length-1] == "win"){
				allLosers = false;
			}else if(s[s.length-1] == "loss"){
				allWinners = false;
			}else{
				System.out.println("wrong index - s[s.length] is neither win or loss");
			}
			if(!allWinners && !allLosers){
				break;
			}
		}
				
		
		//There are no remaining attributes for further partitioning
		if(attributes.isEmpty()){
			currentNode.setReasonForCutoff("'ATTRIBUTES EMPTY'");
			int winCount = 0;
			int divider = 0;
			for(String[] s: examples){
				if(s[s.length-1] == "win"){
					winCount++;
				}else if(s[s.length-1] == "loss"){
				
				}else{
					System.out.println("wrong index - s[s.length] is neither win or loss");
				}
				divider++;
			}
			double winningChance = winCount/divider;
			currentNode.setWinningChance(winningChance);
			
			if(winningChance <= 0.5){
				currentNode.nodeIsNotWin();
			}else{
				currentNode.nodeIsWin();
			}
			return;
		}
	
		//all players are winners
		if(allWinners){
			currentNode.setReasonForCutoff("ALL WINNERS");
			currentNode.setWinningChance(1);
			currentNode.nodeIsWin();
			return;
		}
		
		//all players are losers
		if(allLosers){
			currentNode.setReasonForCutoff("ALL LOSERS");
			currentNode.setWinningChance(0);
			currentNode.nodeIsNotWin();
			return;
		}

		String selectedAttribute = null;
		//Select next attribute by infoGain
		if(infoGain){
			//System.out.println("infogain");
			double highestGain = Integer.MIN_VALUE;
			for(String att: attributes){
				double gain = getInfoGain(att, examples);
				//System.out.println("GAIN="+gain);
				if(gain >= highestGain){
	//				System.out.println("new highest GAIN="+gain);
					selectedAttribute = att;
					highestGain = gain;
				}
			}
		}else{
			//Select next attribute by sequence
			selectedAttribute = attributes.get(0);
		}
		currentNode.setAttribute(selectedAttribute);
		String[] unique = getUniqueEntries(selectedAttribute, examples);
		
		for(String s : unique){
			//make sublists
			List <String[]> subExamples = getSubset(examples, selectedAttribute, s);
			
			List<String> subAttributes = getSubset(attributes, selectedAttribute);
			//create new node
			ID3Node child = new ID3Node(currentNode, currentNode.getLevel()+1, s);
			treeSize++;
			currentNode.addChild(s, child);
			currentNode.notLeaf();
			//recursive call	
			generateTree(subExamples, subAttributes, child, infoGain);
		}
		
		return;
	}
	
	//print entire tree in mxl-like format
	public void printTree(){
		System.out.println();
		System.out.println("- -- --- ---- ----- ------ FINAL TREE START ------ ----- ---- --- -- -");
		root.printNode();
		System.out.println("- -- --- ---- ----- ------ FINAL TREE END ------ ----- ---- --- -- -");
	}
	
	//returns the information gain of a given attribute
	public static double getInfoGain(String att, List<String[]> examples){
		double info = getInfo(examples);
		double infoA = getInfoA(att, examples);
		double infoGain = info - infoA;
		return infoGain;
	}
	
	public static double getInfo(List<String[]> examples){

		if(examples.isEmpty()){
			return 1;
		}
		double info = 0;
		double D = examples.size();
		double DjWin = 0;
		double DjLoss = 0;
			
		for(String[] s: examples){
			if(s[s.length-1].equals("win")){
				DjWin++;
			}else if(s[s.length-1].equals("loss")){
				DjLoss++;
			}	
		}
		
		double infoWin = ((DjWin/D) * (Math.log(DjWin/D)/Math.log(2)));
		double infoLoss = ((DjLoss/D) * (Math.log(DjLoss/D)/Math.log(2)));

		if (DjWin == 0)
			infoWin = 0;
		if (DjLoss == 0)
			infoLoss = 0;

		info = -infoWin-infoLoss;

		return info;
	}
	
	public static double getInfoA(String att, List<String[]> examples){
		
		if(examples.isEmpty()){
			return 1;
		}
		
		double infoA = 0;
		List<String[]> subExamples = new ArrayList<String[]>();

		//for each (unique) attribute value
		String[] unique = getUniqueEntries(att, examples);
		
		for(String u : unique){
			
			double D = examples.size();
			double Dj = 0;
			for(String[] s: examples){
				if(s[Integer.parseInt(att)].equals(u)){
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

	//Returns a String-array of unique values
	private static String[] getUniqueEntries(String att, List<String[]> examples){
		
		Set<String> unique = new HashSet<String>();
		
		for(int i = 0; i < examples.size(); i++){
			unique.add(examples.get(i)[Integer.parseInt(att)].toString());
		}		
		String[] array = unique.toArray(new String[0]);
		return array;
	}
	
	//get subset of examples
	private static List<String[]> getSubset(List<String[]> examples, String attribute, String value) {

		ArrayList<String[]> subset = new ArrayList<String[]>();

		for(String[] s : examples){
			if (s[Integer.parseInt(attribute)].equals(value))
				subset.add(s);
		}
		return subset;
	}
	
	//get subset of attributes
	private static List<String> getSubset(List<String> attributes, Object attribute){
		
		List<String> subset = new ArrayList<String>(attributes);
		
		subset.remove(attribute);
		
		return subset;
	}
	
	public ID3Node getRoot(){
		return root;
	}
	
	public long getTimeBuild(){
		return timeBuild;
	}
	
	public void calculateWinningChance(){
		root.calculateWinningChance();
	}
	
	public int getTreeSize(){
		return treeSize;	
	}

}
