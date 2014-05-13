package analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import json.MatchDecoder;

import org.junit.Test;

import domain.buildorder.Build;
import domain.buildorder.BuildOrder;
import domain.buildorder.BuildOrderCleaner;
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

	private static final String BUILD_ORDER_TREES_FOLDER = "buildordertrees/";
	private static final String BUILD_ORDER_FOLDER = "buildorders/";
	private static final boolean UNITS = false;
	private static final boolean BUILDINGS = true;
	private static final boolean RESEARCH = true;
	private static final boolean UPGRADES = true;
	private static final double SUPPORT = 0.03;

	public static void main(String[] args) {
		
		clearFolders();
		for(Map.Type map : Map.Type.values())
			searchOnMap(map);
	
		searchOnMap(Map.Type.Lost_Temple);
		
		//searchOnMap(null);
		
	}
	
	public static void searchOnMap(Map.Type map){
		
		List<Race> races = new ArrayList<Race>();
		races.add(Race.Zerg);
		races.add(Race.Protoss);
		races.add(Race.Terran);
		races.add(null);
		
		if (map != null)
			System.out.println("Analysing matches on " + map.name());
		else
			System.out.println("Analysing matches on all maps");
		
		List<Match> matches = getMatches(map);
		
		for(Race race : races){
			if (race==null)
				continue;
			
			for(Race enemy : races){
				
				//if (race == Race.Zerg && enemy == race){
				
					System.out.println("Analysing " + matchupString(race, enemy) + " on " + map.name());
					ID3 id3 = generateTree(race, enemy, map, matches, Integer.MAX_VALUE);
					if (id3.getTree().getRoot() == null)
						System.out.println("No build orders found on map " + map.name() + " with matchup " + race.name() + " vs " + enemy.name());
					
					saveTree(id3, race, enemy, map);
					List<BuildOrder> buildorders = id3.getTree().common(SUPPORT);
					saveBuildOrders(buildorders, id3, race, enemy, map, SUPPORT);
				//}
				
			}
		}
	}
	
	private static List<Match> getMatches(Type map) {
		List<Match> decodedMatches = null;
		try {
			MatchDecoder.folder = "/home/niels/Documents/broodwar-data/matches/";
			decodedMatches = new MatchDecoder().decode(Integer.MAX_VALUE, null, null, map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return decodedMatches;
	}

	public static ID3 generateTree(Race race, Race enemy, Map.Type map, List<Match> matches, int n) {
		
		if (matches == null){
			try {
				MatchDecoder.folder = "/home/niels/Documents/broodwar-data/matches/";
				matches = new MatchDecoder().decode(Integer.MAX_VALUE, race, enemy, map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		List<Player> players = new ArrayList<Player>();
		
		for(Match match : matches){
			Player player = null;
			Player second = null;
			for(Player p : match.players){
				if (p.race == race && player == null)
					player = p;
				else if (p.race == enemy)
					second = p;
			}
			if (player != null && second != null && player.win == second.win){
				System.out.println("Error on " + match.id + ". Both players " + (player.win ? "win" : "loose)"));
				continue;
			}
			if (player != null && (second != null || enemy == null)){
				players.add(player);
				if (second != null && second.race == race)
					players.add(second);
			}
		}
		
		// Free memory
		matches = null;
		
		List<BuildOrder> buildOrders = new ArrayList<BuildOrder>(); 
		int wins = 0;
		int loss = 0;
		int sum = 0;
		for(Player player : players){
			sum++;
			BuildOrder buildOrder = new BuildOrder(player);
			buildOrder.filter(UNITS, BUILDINGS, RESEARCH, UPGRADES);
			BuildOrderCleaner.clean(buildOrder);
			buildOrders.add(buildOrder);
			if (buildOrder.win)
				wins++;
			else if (!buildOrder.win)
				loss++;
		}
		System.out.println("wins=" + wins);
		System.out.println("loss=" + loss);
		System.out.println("diff=" + Math.abs(wins-loss));
		System.out.println("sum=" + sum);
		
		// Free memory
		players = null;
		
		if (buildOrders.isEmpty())
			return new ID3();
		
		ID3 id3 = new ID3();
		id3.generateDecisionTree(buildOrders);
		
		return id3;
		
	}
	
	private static void saveTree(ID3 id3, Race race, Race enemy, Type map) {
		
		String races = matchupString(race, enemy);
		
		String folder = "";
		
		if (map != null){
			folder = map.name().toLowerCase();
			File directory = new File(BUILD_ORDER_TREES_FOLDER + folder);
			if (!directory.exists())
				directory.mkdir();
			folder += "/";
		}
		
		String filename = races + ".xml";
		
		id3.getTree().saveToFile(BUILD_ORDER_TREES_FOLDER + folder + filename);
	}

	private static void saveBuildOrders(List<BuildOrder> buildOrders, ID3 id3, Race race, Race enemy, Type map, double support) {
		
		String content = "";
		
		for(BuildOrder buildOrder : buildOrders){
			double value = value(buildOrder, id3.getTree());
			int players = players(buildOrder, id3.getTree());
			double actions = actions(buildOrder, id3.getTree());
			DecimalFormat df = new DecimalFormat( "0.###" );
			content += buildOrder + " (" + players + ") {" + df.format(actions) + "} -> " + df.format(value);
			content += "\n";
		}
		
		String races = matchupString(race, enemy);
		
		String filename = races + ".xml";
		
		String folder = "";
		
		if (map != null){
			folder = map.name().toLowerCase();
			File directory = new File(BUILD_ORDER_FOLDER + folder);
			if (!directory.exists())
				directory.mkdir();
			
			folder += "/";
		}
		
		try{
			// Create file 
			FileWriter fstream = new FileWriter(BUILD_ORDER_FOLDER + folder + filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			//Close the output stream
			out.close();
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
	}
	
	private static double actions(BuildOrder buildOrder, DecisionTree tree) {
		
		ID3Node node = tree.getRoot();
		List<BuildOrder> buildOrders = new ArrayList<BuildOrder>();
		
		for(Build build : buildOrder.builds){
			if (node.getChildren().containsKey(null)){
				ID3Node gameOverNode = node.getChildren().get(null);
				buildOrders.addAll(gameOverNode.getBuildOrders());
			}
			node = node.getChildren().get(build.action);
		}
		
		buildOrders.addAll(node.getBuildOrders());
		
		int actions = 0;
		for(BuildOrder b : buildOrders)
			actions += b.player.actionNumber;
		
		return (double)actions / (double)buildOrders.size();
		
	}

	private static int players(BuildOrder buildOrder, DecisionTree decisionTree) {
		
		ID3Node node = decisionTree.getRoot();
		for(Build build : buildOrder.builds)
			node = node.getChildren().get(build.action);
		
		return node.getBuildOrders().size();
		
	}

	private static double value(BuildOrder buildOrder, DecisionTree decisionTree) {
		
		ID3Node node = decisionTree.getRoot();
		List<BuildOrder> buildOrders = new ArrayList<BuildOrder>();
		
		for(Build build : buildOrder.builds){
			if (node.getChildren().containsKey(null)){
				ID3Node gameOverNode = node.getChildren().get(null);
				buildOrders.addAll(gameOverNode.getBuildOrders());
			}
			node = node.getChildren().get(build.action);
		}
		
		buildOrders.addAll(node.getBuildOrders());
		
		int wins = 0;
		for(BuildOrder b : buildOrders)
			wins += (b.win ? 1 : 0);
		
		return (double)wins / (double)buildOrders.size();
		
	}
	
    private static void guess(ID3 id3, List<BuildOrder> testSet, int depth) {
    	
		int correct = 0;
		int incorrect = 0;
		for(BuildOrder buildOrder : testSet){
			
			boolean guess = id3.wins(depth, buildOrder);
			if (guess == buildOrder.win)
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
    
    private static String matchupString(Race race, Race enemy) {
		
		String matchup = ("" + race.name().charAt(0)).toUpperCase();
		if (enemy != null)
			matchup += "v" + ("" + enemy.name().charAt(0)).toUpperCase();
		
		return matchup;
		
	}
    
    private static void clearFolders() {

		File directory = new File(BUILD_ORDER_TREES_FOLDER);
		if (directory.exists())
			for(File file : directory.listFiles())
				file.delete();
		
		directory = new File(BUILD_ORDER_FOLDER);
		if (directory.exists())
			for(File file : directory.listFiles())
				file.delete();
		
	}
    
}
