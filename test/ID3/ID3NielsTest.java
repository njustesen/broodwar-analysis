package ID3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import json.MatchDecoder;

import org.junit.Test;

import domain.cost.CostMap;

import analyser.Action;
import analyser.Match;
import analyser.Player;
import analyser.Player.Race;

import ID3.niels.ID3;

public class ID3NielsTest {

	@Test
    public void protoss() {
		
		List<Match> decodedMatches = null;
		try {
			MatchDecoder.folder = "/Users/njustesen/matches";
			decodedMatches = new MatchDecoder().decode(2, Race.Protoss, Race.Zerg, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Player> players = new ArrayList<Player>();
		
		for(Match match : decodedMatches)
			for(Player player : match.players)
				if (player.race == Race.Protoss)
					players.add(player);
		
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
			System.out.println(player.actions);
		}
		
		ID3 id3 = new ID3();
		id3.generateDecisionTree(players, Race.Protoss);
		
		id3.getTree().print();
		
	}
	
}
