package analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.internal.bind.MapTypeAdapterFactory;

import json.MatchDecoder;

import clustering.ClusterPoint;
import clustering.kmedoids.KMedoidCluster;
import clustering.kmedoids.KMedoids;
import clustering.upgma.UPGMA;
import domain.naming.BuildOrderNamer;

import analyser.Map.Type;
import analyser.Match;
import analyser.Player;
import analyser.Player.Race;

public class StrategyClustering {
	
	public static void main(String[] args) {
		List<Match> decodedMatches = null;
		try {
			MatchDecoder.folder = "D:/matches/";
			decodedMatches = new MatchDecoder().decode(200, Race.Terran, Type.Python);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//List<KMedoidCluster> clusters = new StrategyClustering(Race.Terran, Type.Python).findStrategies(decodedMatches);
		List<List<ClusterPoint>> clusters = new StrategyClustering(Race.Terran, Type.Python).upgma(decodedMatches);
		for(List<ClusterPoint> cluster : clusters){
			System.out.println("-- Cluster -- ");
			for(ClusterPoint point : cluster)
				System.out.println(new BuildOrderNamer().buildings(((Player)point).actions, ", ", 16));
		}
			
		//System.out.println(clusters);
	}

	private Race race;
	private Type type;

	public StrategyClustering(Race race, Type mapType) {
		super();
		this.race = race;
		this.type = mapType;
	}
	
	public List<KMedoidCluster> kmedoids(List<Match> matches){
		
		List<ClusterPoint> points = new ArrayList<ClusterPoint>();
		
		for(Match match : matches){
			for(Player player : match.players){
				if (player.race == race && match.map.type == type){
					points.add(player);
				}
				
			}
		}
		
		return new KMedoids(6).cluster(points);
		
	}
	
	public List<List<ClusterPoint>> upgma(List<Match> matches){
		
		List<ClusterPoint> points = new ArrayList<ClusterPoint>();
		
		for(Match match : matches){
			for(Player player : match.players){
				if (player.race == race && match.map.type == type){
					points.add(player);
				}
				
			}
		}
		
		UPGMA upgma = new UPGMA();
		upgma.init(points);
		return upgma.getClusters(32);
		
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}
	
}
