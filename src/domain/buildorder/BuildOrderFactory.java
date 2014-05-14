package domain.buildorder;

import java.util.ArrayList;
import java.util.List;

import analyser.Match;
import analyser.Player;
import analyser.Player.Race;

public class BuildOrderFactory {
	
	public static final boolean UNITS = false;
	public static final boolean BUILDINGS = true;
	public static final boolean RESEARCH = true;
	public static final boolean UPGRADES = true;

	public static List<BuildOrder> instantiate(List<Match> matches, Race race, Race enemy) {
		
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
		
		return buildOrders;
	}

	
	
}
