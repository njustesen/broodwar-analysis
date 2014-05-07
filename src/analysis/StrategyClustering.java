package analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.internal.bind.MapTypeAdapterFactory;

import json.MatchDecoder;

import clustering.ClusterPoint;
import clustering.distance.DISTANCE_FUNCTION;
import clustering.distance.DistanceManager;
import clustering.distance.EditDistance;
import clustering.distance.FirstDistance;
import clustering.kmedoids.KMedoidCluster;
import clustering.kmedoids.KMedoidPoint;
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
			decodedMatches = new MatchDecoder().decode(20000000, Race.Terran, Type.Python);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (1==1)
			return;
		// Distance Manager
		boolean units = true;
		boolean buildings = true;
		boolean upgrades = true;
		boolean research = true;
		boolean cost = true;
		int discount = 5;
		int max = 5;
		int base = 10;
		DistanceManager.editDistance = new EditDistance(units, buildings, upgrades, research, cost, discount, max);
		//DistanceManager.firstDistance = new FirstDistance(units, buildings, upgrades, research, base);
		//DistanceManager.distanceFunction = DISTANCE_FUNCTION.FIRST_DISTANCE;
		DistanceManager.distanceFunction = DISTANCE_FUNCTION.EDIT_DISTANCE;
		
		//List<KMedoidCluster> clusters = new StrategyClustering(Race.Terran, Type.Python).kmedoids(decodedMatches);
		List<List<ClusterPoint>> clusters = new StrategyClustering(Race.Protoss, Type.Python).upgma(decodedMatches);
		/*
		for(KMedoidCluster cluster : clusters){
			System.out.println("-- Cluster -- ");
			for(KMedoidPoint point : cluster.getMembers())
				System.out.println(new BuildOrderNamer().buildings(((Player)point.getPoint()).actions, ", ", 30));
		}
		*/
		
		for(List<ClusterPoint> cluster : clusters){
			System.out.println("-- Cluster -- ");
			for(ClusterPoint point : cluster)
				System.out.println(new BuildOrderNamer().buildings(((Player)point).actions, ", ", 30));
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
		
		return new KMedoids(true).cluster(points, 18);
		
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
