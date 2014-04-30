package analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import json.MatchDecoder;

import clustering.ClusterPoint;
import clustering.kmedoids.KMedoidCluster;
import clustering.kmedoids.KMedoids;

import analyser.Match;
import analyser.Player;
import analyser.Player.Race;

public class StrategyClustering {
	
	public static void main(String[] args) {
		List<Match> decodedMatches = null;
		try {
			decodedMatches = new MatchDecoder().decode();
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<KMedoidCluster> clusters = new StrategyClustering(Race.Terran).findStrategies(decodedMatches);
		System.out.println(clusters);
	}

	private Race race;

	public StrategyClustering(Race race) {
		super();
		this.race = race;
	}
	
	public List<KMedoidCluster> findStrategies(List<Match> matches){
		
		List<ClusterPoint> points = new ArrayList<ClusterPoint>();
		
		for(Match match : matches){
			for(Player player : match.players){
				if (player.race == race){
					points.add(player);
				}
			}
			if (points.size() >= 30)
				break;
		}
		
		return new KMedoids(4).cluster(points);
		
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}
	
}
