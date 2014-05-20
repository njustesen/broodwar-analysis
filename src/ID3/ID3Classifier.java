package ID3;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import json.MatchDecoder;
import analyser.Action;
import analyser.Match;
import analyser.Player;
import analyser.Map.Type;
import analyser.Player.Race;

public class ID3Classifier {
	
	ID3Tree tree;
	public static int analyzeLength = 10;
	public static boolean infoGain = true;
	public static int maxDepth = 10;
	public static int interval = 1000; // 24 fps (???)
	public static double predictability = 0;
	public static boolean bothPlayers = true;
	private static long timeCheck;
	private static boolean timeInterval = true;
	
	//launch point
	public static void main(String[] args){
		
		try {
			List<Match> matches = new MatchDecoder().decode(15000, Race.Protoss, Race.Terran, Type.Lost_Temple);
			List<Match> trainingData = matches.subList(0, 13000);
			List<Match> testData = matches.subList(13000, 15000);
			
			ID3Classifier id3c = new ID3Classifier(trainingData);
		//	id3c.printTree();
			if(timeInterval){
				id3c.checkMatches(id3c.convertToNewMatchArrays(testData), maxDepth);
			}else{
				id3c.checkMatches(id3c.convertToMatchArrays(testData), maxDepth);
			}
			
		}
		catch (IOException e) {e.printStackTrace();}
		
		
	}
	
	//constructor
	public ID3Classifier(List<Match> trainingData){
		List<String[]> matchArrays = convertToNewMatchArrays(trainingData);
		this.tree = new ID3Tree(matchArrays, infoGain, analyzeLength);
		}
	//secondary constructor
	public ID3Classifier(ID3Tree tree){
		this.tree = tree;	
	}
	
	//Convert List of Match-object to String-arrays containing the data.
	//This will discretize timestamps and append players actions (1000-2000_P1-Pylon_P1-Forge)
	public List<String[]> convertToNewMatchArrays(List<Match> trainingData){
		
		ArrayList<String[]> matches = new ArrayList<String[]>();
		
		System.out.println("trainingdata.size="+trainingData.size());
		
		for(Match m : trainingData){
			
			LinkedHashMap <String, String> map = new LinkedHashMap<String, String>();
			
			Player[] p = m.players;
			if(p == null){
				System.out.println("p is null");
			}

			List<Action> p1a = p[0].actions;
			List<Action> p2a = p[1].actions;
			
			for(int i = 0; i < analyzeLength; i++){
				String key = discretizeNumber(i*interval,0,1000000,interval);
				map.put(key, "NO_BUILD");
			}
			
			List<Action> toBeRemoved = new ArrayList<Action>();
			for(Action a: p1a){
				if(a.type == Action.Type.Unit){
					toBeRemoved.add(a);
				}
			}
			p1a.removeAll(toBeRemoved);
			toBeRemoved.clear();
			
			if(bothPlayers){
				for(Action a: p2a){
					if(a.type == Action.Type.Unit){
						toBeRemoved.add(a);
					}
				}
				p2a.removeAll(toBeRemoved);
				toBeRemoved.clear();
			}
			for(Action a: p1a){
				String key = discretizeNumber(a.frames,0,1000000,interval);
				String newValue = map.get(key);
				if(newValue != null){
					if(newValue.equals("NO_BUILD")){
						newValue = key+" p1_"+a.actionType.toString();
					}else{
						newValue += " p1_"+a.actionType.toString();
					}
					map.put(key, newValue);
				}
			}
			
			if(bothPlayers){
				for(Action a: p2a){
					String key = discretizeNumber(a.frames,0,1000000,interval);
					String newValue = map.get(key);
					if(newValue != null){
						if(newValue.equals("NO_BUILD")){
							newValue = key+" p2_"+a.actionType.toString();
						}else{
							newValue += " p2_"+a.actionType.toString();
						}
						map.put(key, newValue);
					}
				}
			}
			Object[] objectMatch = map.values().toArray();
			String[] finalMatch = new String[analyzeLength+1];
			
			for(int i = 0; i < finalMatch.length; i++){
				if(i < objectMatch.length){	
					finalMatch[i] = (String) objectMatch[i];
				}else{
					finalMatch[i] = "NO_BUILD";
				}
			}
			
			if(p[0].win){
				finalMatch[analyzeLength] = "win";
			}else{
				finalMatch[analyzeLength] = "loss";
			}
			matches.add(finalMatch);
		}
		return matches;
	}
	
	
	//Convert List of Match-object to String-arrays containing the data.
	//The first building is added to first index, second to second and so on..
	public List<String[]> convertToMatchArrays(List<Match> trainingData){
		
		ArrayList<String[]> matches = new ArrayList<String[]>();
		System.out.println("trainingdata.size="+trainingData.size());
		
		for(Match m : trainingData){
			String[] match = new String[analyzeLength+1];
			Player[] p = m.players;
			if(p == null){
				System.out.println("p is null");
			}

			List<Action> p1a = p[0].actions;
			List<Action> p2a = p[1].actions;
			
			List<Action> toBeRemoved = new ArrayList<Action>();
			for(Action a: p1a){
				if(a.type == Action.Type.Unit){
					toBeRemoved.add(a);
				}
			}
			p1a.removeAll(toBeRemoved);
			toBeRemoved.clear();
			for(Action a: p2a){
				if(a.type == Action.Type.Unit){
					toBeRemoved.add(a);
				}
			}
			p2a.removeAll(toBeRemoved);
			
			for(int i = 0; i < analyzeLength; i++){
				String p1Action;
				if(i >= p1a.size()){
					p1Action = "NO_BUILD";
				}else{
					p1Action = p1a.get(i).actionType.toString();
				}
				
				String p2Action;
				if(i >= p2a.size()){
					p2Action = "NO_BUILD";
				}else{
					p2Action = p2a.get(i).actionType.toString();
				}
				
				if(bothPlayers){
					match[i] = p1Action + "_" + p2Action;
				}else{
					match[i] = p1Action;
				}
				
			}
			if(p[0].win){
				match[analyzeLength] = "win";
			}else{
				match[analyzeLength] = "loss";
			}
			
			matches.add(match);
		}
		
		return matches;
	}
	
	//check a collection of matches
	public void checkMatches(List <String[]> testData, int depth){
		double count = 0;
		double succes = 0;
		int dismissed = 0;
		long begin = System.currentTimeMillis();
		for(String[] s: testData){
			if(isMatchPredictable(s, depth)){
				count++;
				boolean win = isMatchWin(s, depth);
				if(s[s.length-1].equals("win") && win){
					succes++;
				}else if(s[s.length-1].equals("loss") && !win){
					succes++;
				}else if(s[s.length-1].equals("loss") && win){
	//				System.out.println(" wrong classification - guessed win, is loss");
				}else if(s[s.length-1].equals("win") && !win){
	//				System.out.println(" wrong classification - guessed loss, is win");
				}
			}else{
				dismissed++;
			}
		}
		long end = System.currentTimeMillis();
		timeCheck = end - begin;
		System.out.println("tested "+count+" matches  rateOfSucces = "+(succes/count)*100 +"% "+dismissed+" matches dismissed. timeCheck="+timeCheck+" ms");
		System.out.println("tree size = "+tree.getTreeSize()+"  Tree buid time = "+tree.getTimeBuild()+"(ms)  Time to check test data = "+this.timeCheck+"(ms)");
	}
	
	//returns the winning chance of the node associated with the given match
	public boolean isMatchWin(String[] s, int depth){
		if(tree != null){
			ID3Node node = tree.getRoot();
			int depthCount = 0;
			while(!node.isLeaf() && depthCount < depth){
				String attributeString = s[Integer.parseInt(node.getAttribute())];
			
				ID3Node child = node.getChild(attributeString);
				if(child == null){
					break;
				}else{
					node = child;
				}
				depthCount++;
				
			}
			double winningChance = node.getWinningChance();
			if(winningChance > 0.5){
				return true;
			}else{
				return false;
			}
		}
		System.out.println("UNABLE TO OBTAIN MATCH RESULTS");
		return false;
	}
	
	//returns if a match is predictable according to the predictability-factor
	public boolean isMatchPredictable(String[] s, int depth){
		if(predictability == 0){
			return true;
		}
		if(tree != null){
			ID3Node node = tree.getRoot();
			int depthCount = 0;
			while(!node.isLeaf() && depthCount < depth){
				String attributeString = s[Integer.parseInt(node.getAttribute())];
//				System.out.println("attributeString="+attributeString+" att="+Integer.parseInt(node.getAttribute()));
				
				ID3Node child = node.getChild(attributeString);
				if(child == null){
					break;
				}else{
					node = child;
				}
//				System.out.println("node="+node);
				depthCount++;
				
			}
			double winningChance = node.getWinningChance();
//			System.out.println("winningChance="+winningChance);
			if(winningChance > 0.5+predictability || winningChance < 0.5-predictability){
				return true;
			}else{
				return false;
			}
		}
		System.out.println("UNABLE TO OBTAIN IF MATCH IS PREDICTABLE");
		return false;
	}

	//assigns numeric values to a string, denoting a given interval
	public static String discretizeNumber(double number, double min, double max, double interval){

		BigDecimal BDnumber = BigDecimal.valueOf(number);
		BigDecimal BDmin = BigDecimal.valueOf(min);
		BigDecimal BDmax = BigDecimal.valueOf(max);
		BigDecimal BDinterval = BigDecimal.valueOf(interval);
		
		for(BigDecimal key = BDmin; key.compareTo(BDmax.subtract(BDinterval)) <= 0; key = key.add(BDinterval)){
			BigDecimal from = key;
			BigDecimal to = key.add(BDinterval);
			
			if((BDnumber.compareTo(from) == 1 || BDnumber.compareTo(from) == 0) && (BDnumber.compareTo(to) == -1 || BDnumber.compareTo(to) == 0)){
				return from.toString()+" - "+to.toString();
			}
		}
		System.out.println("discretize error");
		return null;
	}
	
	//print the entire tree in an XML-like format
	public void printTree(){
		this.tree.printTree();
	}
	
	//re-calculates all winning chances for the tree
	public void calculateWinningChance(){
		this.tree.calculateWinningChance();
	}
}

