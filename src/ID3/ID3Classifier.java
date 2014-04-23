package ID3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import analyser.Action;
import analyser.Match;
import analyser.Player;

import parser.bwhf.model.Replay;

public class ID3Classifier {
	
	ID3Tree tree;
	public static int analyzeLength = 10;
	
	public ID3Classifier(List<Match> trainingData){
		List<String[]> matchArrays = convertToMatchArrays(trainingData);
		this.tree = new ID3Tree(matchArrays);	
	}
	
	public ID3Classifier(ID3Tree tree){
		this.tree = tree;	
	}
	
	public List<String[]> convertToMatchArrays(List<Match> trainingData){
		
		ArrayList<String[]> matches = new ArrayList<String[]>();
		
		for(Match m : trainingData){
			String[] match = new String[analyzeLength+1];
			Player[] p = m.getPlayers();
			List<Action> p1a = p[0].getActions();
			List<Action> p2a = p[1].getActions();
			
			for(int i = 0; i < analyzeLength; i++){
				match[i] = p1a.get(i).toString() + p2a.get(i).toString();
			}
			if(p[0].isWin()){
				match[analyzeLength] = "win";
			}else{
				match[analyzeLength] = "loss";
			}
			
			matches.add(match);
		}
		
		return matches;
	}
	
	
	public void checkMatches(List <String[]> testData){
		double count = 0;
		double succes = 0;
		for(String[] s: testData){
			count++;
			boolean win = isMatchWin(s);
			System.out.print("win = "+win);
			if(s[s.length].equals("win") && win){
				succes++;
				System.out.println(" correct classification - edible");
			}else if(s[s.length].equals("loss") && !win){
				succes++;
				System.out.println(" correct classification - poisonous");
			}else{
				System.out.println(" error");
			}
		}
		System.out.println("tested "+count+" mushrooms  rateOfSucces = "+(succes/count)*100);
	}
	
	public boolean isMatchWin(String[] m){
		if(tree != null){
			ID3Node node = tree.getRoot();
			while(!node.isLeaf()){
				String attributeString = m[Integer.parseInt(node.getAttribute())];
				node = node.getChild(attributeString);
			}
			return node.isWin();
		}
		System.out.println("UNABLE TO OBTAIN IF MUSHROOM IS EDIBLE");
		return false;
	}
}

