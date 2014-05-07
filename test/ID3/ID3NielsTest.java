package ID3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import json.MatchDecoder;

import org.junit.Test;

import domain.cost.CostMap;

import analyser.Action;
import analyser.Map;
import analyser.Match;
import analyser.Player;
import analyser.Map.Type;
import analyser.Player.Race;

import ID3.niels.ID3;
import ID3.niels.ID3Stats;

public class ID3NielsTest {

	@Test
    public void protoss() {
		
		int n = 1000000;
		Race race = Race.Protoss;
		Race enemy = Race.Zerg;
		//Map.Type map = Map.Type.Andromeda;
		Map.Type map = null;
		int maxDepth = 20;
		
		List<Match> decodedMatches = null;
		try {
			MatchDecoder.folder = "D:/matches/";
			decodedMatches = new MatchDecoder().decode(n, race, map);
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
			List<Action> filtered = new ArrayList<Action>();
			for(Action action : player.actions){
				if (action.type != Action.Type.Unit && CostMap.costs.containsKey(action.actionType)){
					filtered.add(action);
					System.out.print(action.actionType + "\t");
				}
			}
			player.actions = filtered;
			System.out.print("\n");
		}
		
		List<Player> trainingSet = new ArrayList<Player>();
		List<Player> testSet = new ArrayList<Player>();
		
		for(int i = 0; i < players.size(); i++){
			if (i < players.size() / 2)
				trainingSet.add(players.get(i));
			else
				testSet.add(players.get(i));
		}
		
		for(int depth = 1; depth < maxDepth; depth++){
			
			ID3 id3 = new ID3();
			id3.generateDecisionTree(trainingSet, race);
			
			//id3.getTree().print();
			id3.getTree().saveToFile("buildOrders_" + map.name() + "_" + race.name() + "_" + depth + ".xml");
			
			// Test
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
	
}
