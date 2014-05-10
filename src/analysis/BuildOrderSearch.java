package analysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import json.MatchDecoder;

import org.junit.Test;

import domain.cost.CostMap;

import analyser.Action;
import analyser.Action.ActionType;
import analyser.Map;
import analyser.Match;
import analyser.Player;
import analyser.Map.Type;
import analyser.Player.Race;

import ID3.niels.DecisionTree;
import ID3.niels.ID3;
import ID3.niels.ID3Node;
import ID3.niels.ID3Stats;

public class BuildOrderSearch {
	
	public static void main(String[] args) {
		
		Race race = Race.Protoss;
		Race enemy = Race.Zerg;
		Map.Type map = Map.Type.Fighting_Spirit;
		int n = 1000000;
		
		ID3 id3 = search(race, enemy, map, n);
		
		//id3.getTree().trim(0.02);
		
		save(id3, race, enemy, map);
		
		//for(int i = 0; i < 20; i++){
			//double support = i / 100.0;
			double support = 0.03;
			System.out.println("Players: " + id3.getTree().getRoot().getPlayers().size());
			System.out.println("Support: " + support);
			List<List<ActionType>> buildorders = id3.getTree().common(support);
			
			save(buildorders, id3, race, enemy, map, support);
		//}
				
	}

	private static int players(List<ActionType> buildorder, DecisionTree decisionTree) {
		
		ID3Node node = decisionTree.getRoot();
		for(ActionType action : buildorder)
			node = node.getChildren().get(action);
		
		return node.getPlayers().size();
		
	}

	private static double value(List<ActionType> buildorder, DecisionTree decisionTree) {
		
		ID3Node node = decisionTree.getRoot();
		int gameOver = 0;
		double gameOverValue = 0;
		for(ActionType action : buildorder){
			if (node.getChildren().containsKey(null)){
				ID3Node gameOverNode = node.getChildren().get(null);
				int g = gameOverNode.getPlayers().size();
				gameOver += g;
				gameOverValue += gameOverNode.value() * g;
			}
			node = node.getChildren().get(action);
		}
		
		double value = node.value() * node.getPlayers().size() + (double)gameOverValue * gameOver;
		value = value / ((double)node.getPlayers().size() + gameOver);
		
		return value;
		
	}

	private static void save(ID3 id3, Race race, Race enemy, Type map) {
		String filename = "";
		String races = race.name();
		if (enemy != null)
			races += "_vs_" + enemy.name();
		
		if (map != null)
			filename = id3.getTree().getRoot().getPlayers().size() + "_" + map.name() + "_" + races + ".xml";
		else
			filename = id3.getTree().getRoot().getPlayers().size() + "_" + races + ".xml";
		
		id3.getTree().saveToFile("buildordertrees/" + filename.toLowerCase());
	}
	
	private static void save(List<List<ActionType>> buildorders, ID3 id3, Race race, Race enemy, Type map, double support) {
		
		String content = "";
		
		for(List<ActionType> buildorder : buildorders){
			double value = value(buildorder, id3.getTree());
			int players = players(buildorder, id3.getTree());
			String val = value + "";
			content += buildorder + " (" + players + ") -> " + val.substring(0, Math.min(5, val.length()));
			content += "\n";
		}
		
		String races = race.name();
		if (enemy != null)
			races += "_vs_" + enemy.name();
		int playersUsed = id3.getTree().getRoot().getPlayers().size();
		
		String filename = map.name() + "_" + races + "_" + support + "_" + playersUsed + ".xml";
		
		try{
			// Create file 
			FileWriter fstream = new FileWriter("buildorders/" + filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			//Close the output stream
			out.close();
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
	}

	public static ID3 search(Race race, Race enemy, Map.Type map, int n) {
		
		List<Match> decodedMatches = null;
		try {
			MatchDecoder.folder = "/home/niels/Documents/broodwar-data/matches/";
			decodedMatches = new MatchDecoder().decode(n, Race.Protoss, Race.Zerg, map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Player> players = new ArrayList<Player>();
		
		for(Match match : decodedMatches){
			boolean enemyRace = false;
			Player player = null;
			for(Player p : match.players){
				if (p.race == race)
					player = p;
				else if (p.race == enemy)
					enemyRace = true;
			}
			if (enemyRace && player != null)
				players.add(player);
		}
		
		// Remove units
		for(Player player : players){
			List<Action> upgradesAndResearch = new ArrayList<Action>();
			List<Action> filtered = new ArrayList<Action>();
			for(Action action : player.actions){
				if (action.type != Action.Type.Unit && CostMap.costs.containsKey(action.actionType)){
					if (action.type == Action.Type.Research || action.type == Action.Type.Research){
						if (!upgradesAndResearch.contains(action)){
							upgradesAndResearch.add(action);
							filtered.add(action);
						}
					} else {
						filtered.add(action);
					}
				}
			}
			player.actions = filtered;
			System.out.print("\n");
			System.out.println(player);
		}
		
		ID3 id3 = new ID3();
		id3.generateDecisionTree(players, race);
		
		return id3;
		
	}
	

    private static void guess(ID3 id3, List<Player> testSet, int depth) {
    	
		int correct = 0;
		int incorrect = 0;
		for(Player player : testSet){
			
			boolean guess = id3.wins(depth, player);
			if (guess == player.win)
				correct += 1;
			else
				incorrect += 1;
			
		}
		
		System.out.println("---------------");
		System.out.println("Max depth:\t" + depth);
		System.out.println("Average depth:\t" + (double)ID3Stats.depth / (double)ID3Stats.searches);
		System.out.println("Correct:\t" + correct);
		System.out.println("Incorrect:\t" + incorrect);
		System.out.println("Success rate:\t" + (double)correct / (double)testSet.size());
		
	}
    
}
