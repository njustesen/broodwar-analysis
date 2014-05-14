package analysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
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
import domain.buildorder.BuildOrder;
import domain.buildorder.BuildOrderFactory;
import domain.naming.BuildOrderNamer;

import ID3.niels.ID3;
import analyser.Map.Type;
import analyser.Map;
import analyser.Match;
import analyser.Player;
import analyser.Player.Race;

public class StrategyClustering {
	
	private static final String KMEDOIDS_FOLDER = "buildorders-kmedoid/";
	private static final String UPGMA_FOLDER = "buildorders-upgma/";
	private static final int MAX_LENGTH = 8;
	private static final boolean UNITS = false;
	private static final boolean BUILDINGS = true;
	private static final boolean RESEARCH = true;
	private static final boolean UPGRADES = true;
	private static final double SUPPORT = 0.10;
	
	public static void main(String[] args) {
		
		clearFolder();
		/*
		for(Map.Type map : Map.Type.values()){
			if (map != Map.Type.None)
				searchOnMap(map);
		}
		 */
		searchOnMap(Map.Type.Fighting_Spirit);
		
		searchOnMap(null);
		
		//System.out.println(clusters);
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
		if (matches.isEmpty()){
			if (map != null)
				System.out.println("No matches on " + map.name());
			else
				System.out.println("No matches");
			return;
		}
		
		for(Race race : races){
			if (race==null)
				continue;
			
			for(Race enemy : races){
				
				List<BuildOrder> buildOrders = BuildOrderFactory.instantiate(matches, race, enemy);
				
				System.out.println("Analysing " + matchupString(race, enemy) + " on " + map.name());
				
				int discount = 5;
				int max = 5;
				int base = 10;
				boolean cost = true;
				
				DistanceManager.editDistance = new EditDistance(UNITS, BUILDINGS, UPGRADES, RESEARCH, cost, discount, max);
				//DistanceManager.firstDistance = new FirstDistance(units, buildings, upgrades, research, base);
				//DistanceManager.distanceFunction = DISTANCE_FUNCTION.FIRST_DISTANCE;
				DistanceManager.distanceFunction = DISTANCE_FUNCTION.EDIT_DISTANCE;
				/*
				List<KMedoidCluster> clusters = new StrategyClustering(race, enemy, map).kmedoids(buildOrders);
				
				// Remove clusters with low support
				List<KMedoidCluster> removed = new ArrayList<KMedoidCluster>();
				for(KMedoidCluster cluster : clusters){
					if (cluster.getMembers().size() < buildOrders.size() * SUPPORT)
						removed.add(cluster);
				}
				for(KMedoidCluster cluster : removed)
					clusters.remove(cluster);
				
				saveKmedoidClusters(clusters, race, enemy, map);
				*/
				List<List<ClusterPoint>> clusters = new StrategyClustering(race, enemy, map).upgma(buildOrders);
				saveUpgmaClusters(clusters, race, enemy, map);
				
			}
		}
	}
	
	private static void saveKmedoidClusters(List<KMedoidCluster> clusters, Race race, Race enemy, Map.Type map) {
		
		String content = "";
		
		for(KMedoidCluster cluster : clusters){
			BuildOrder buildOrder = (BuildOrder)cluster.getMedoid().getPoint();
			content += buildOrder.toString(MAX_LENGTH);
			double wins = 0;
			for(KMedoidPoint point : cluster.getMembers()){
				if (((BuildOrder)point.getPoint()).win)
					wins++;
			}
			double value = wins / (double) cluster.getMembers().size();
			double actions = 0;
			for(KMedoidPoint point : cluster.getMembers())
				actions += ((BuildOrder)point.getPoint()).player.actionNumber;
			actions = actions / (double)cluster.getMembers().size();
			DecimalFormat df = new DecimalFormat( "0.###" );
			content += " (" + cluster.getMembers().size() + ") {" + df.format(actions) + "} -> " + df.format(value) + "\n";
		}
		
		String races = matchupString(race, enemy);
		
		String filename = races + ".xml";
		
		String folder = "";
		
		if (map != null){
			folder = map.name().toLowerCase();
			File directory = new File(KMEDOIDS_FOLDER + folder);
			if (!directory.exists())
				directory.mkdir();
			
			folder += "/";
		}
		
		try{
			// Create file 
			FileWriter fstream = new FileWriter(KMEDOIDS_FOLDER + folder + filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			//Close the output stream
			out.close();
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
	}
	
	private static void saveUpgmaClusters(List<List<ClusterPoint>> clusters, Race race, Race enemy, Type map) {
		
		String content = "";
		
		for(List<ClusterPoint> cluster : clusters){
			List<KMedoidCluster> kmedoid = new KMedoids(true).cluster(cluster, 1);
			BuildOrder buildOrder = (BuildOrder)kmedoid.get(0).getMedoid().getPoint();
			content += buildOrder.toString(MAX_LENGTH);
			double wins = 0;
			for(ClusterPoint point : cluster){
				if (((BuildOrder)point).win)
					wins++;
			}
			double value = wins / (double) cluster.size();
			double actions = 0;
			for(ClusterPoint point : cluster)
				actions += ((BuildOrder)point).player.actionNumber;
			actions = actions / (double)cluster.size();
			DecimalFormat df = new DecimalFormat( "0.###" );
			content += " (" + cluster.size() + ") {" + df.format(actions) + "} -> " + df.format(value) + "\n";
		}
		
		String races = matchupString(race, enemy);
		
		String filename = races + ".xml";
		
		String folder = "";
		
		if (map != null){
			folder = map.name().toLowerCase();
			File directory = new File(UPGMA_FOLDER + folder);
			if (!directory.exists())
				directory.mkdir();
			
			folder += "/";
		}
		
		try{
			// Create file 
			FileWriter fstream = new FileWriter(UPGMA_FOLDER + folder + filename);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			//Close the output stream
			out.close();
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private static String matchupString(Race race, Race enemy) {
		
		String matchup = ("" + race.name().charAt(0)).toUpperCase();
		if (enemy != null)
			matchup += "v" + ("" + enemy.name().charAt(0)).toUpperCase();
		
		return matchup;
		
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
	
	public static void clearFolder() {
		
		File directory = new File(KMEDOIDS_FOLDER);
		if (directory.exists())
			for(File file : directory.listFiles())
				file.delete();

		directory = new File(UPGMA_FOLDER);
		if (directory.exists())
			for(File file : directory.listFiles())
				file.delete();
			
	}

	public Race race;
	public Race enemy;
	public Type type;

	public StrategyClustering(Race race, Race enemy, Type mapType) {
		super();
		this.race = race;
		this.enemy = enemy;
		this.type = mapType;
	}
	
	public List<KMedoidCluster> kmedoids(List<BuildOrder> buildOrders){
		
		List<ClusterPoint> points = new ArrayList<ClusterPoint>();
		
		points.addAll(buildOrders);
		
		return new KMedoids(true).cluster(points, 18);
		
	}
	
	public List<List<ClusterPoint>> upgma(List<BuildOrder> buildOrders){
		
		List<ClusterPoint> points = new ArrayList<ClusterPoint>();
		points.addAll(buildOrders);
		
		UPGMA upgma = new UPGMA();
		upgma.init(points);
		
		return upgma.getClustersWithSupport(SUPPORT);
		
	}
	
}
