package clustering.kmedoids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KMedoids {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private int k;
	
	public KMedoids(int k){
		this.k = k;
	}
	
	public List<KMedoidCluster> cluster(List<KMedoidPoint> points){
		
		// 1. 	Initialize: randomly select k of the n data points as the medoids
		List<KMedoidPoint> medoids = selectKRandom(points);
		
		// 2. 	Associate each data point to the closest medoid. ("closest" here is defined using any valid distance metric, most commonly Euclidean distance, Manhattan distance or Minkowski distance)
		List<KMedoidCluster> clusters = null;
		while(true){
		
			// Assign to clusters
			clusters = new ArrayList<KMedoidCluster>(clusterToMedoids(points, medoids));
			
			// Test new configurations
			List<KMedoidPoint> bestConfig = new ArrayList<KMedoidPoint>();
			medoids.clear();
			for(KMedoidCluster cluster : clusters)
				medoids.add(cluster.getMedoid());
			bestConfig = medoids;
			double lowestCost = cost(clusters);
			for(KMedoidPoint medoid : medoids){
				for(KMedoidPoint point : points){
					if (medoids.contains(point))
						continue;
					List<KMedoidPoint> swap = swap(medoids, medoid, point);
					clusters = new ArrayList<KMedoidCluster>(clusterToMedoids(points, swap));
					double cost = cost(clusters);
					if (cost < lowestCost){
						lowestCost = cost;
						bestConfig = swap;
					}
				}
			}
			
			// 5	Repeat steps 2 to 4 until there is no change in the medoid.
			if (!same(medoids, bestConfig)){
				medoids = bestConfig;
				System.out.println("\tlowest cost: " + lowestCost);
			} else {
				clusters = new ArrayList<KMedoidCluster>(clusterToMedoids(points, medoids));
				break;
			}
		}
		return clusters;
		
	}

	private boolean same(List<KMedoidPoint> A, List<KMedoidPoint> B) {
		
		for(KMedoidPoint a : A){
			if (a != B.get(A.indexOf(a))){
				return false;
			}
		}
		
		return true;
	}

	private double cost(List<KMedoidCluster> clusters) {
		
		double cost = 0;
		for(KMedoidCluster cluster : clusters){
			for(KMedoidPoint point : cluster.getMembers()){
				cost += point.distance(cluster.getMedoid());
			}
		}
		
		return cost;
	}


	private List<KMedoidPoint> swap(List<KMedoidPoint> medoids, KMedoidPoint medoid, KMedoidPoint point) {
		List<KMedoidPoint> newMedoids = new ArrayList<KMedoidPoint>();
		for(KMedoidPoint m : medoids){
			if (m != medoid)
				newMedoids.add(m);
			else
				newMedoids.add(point);
		}
		return newMedoids;
	}

	private Collection<KMedoidCluster> clusterToMedoids(List<KMedoidPoint> points, List<KMedoidPoint> medoids) {
		
		// Create clusters with medoids
		Map<KMedoidPoint, KMedoidCluster> medoidClusters = new HashMap<KMedoidPoint, KMedoidCluster>();
		for(KMedoidPoint medoid : medoids){
			medoidClusters.put(medoid, new KMedoidCluster(medoid));
			//medoidClusters.get(medoid).getMembers().add(medoid);
		}

		// Assign to clusters
		for(KMedoidPoint point : points){
			KMedoidPoint closestMedoid = null;
			double closestDistance = Double.MAX_VALUE;
			for(KMedoidPoint medoid : medoids){
				double distance = point.distance(medoid);
				if (distance < closestDistance){
					closestMedoid = medoid; 
					closestDistance = distance;
				}
			}
			medoidClusters.get(closestMedoid).getMembers().add(point);
		}
		
		return medoidClusters.values();
		
	}

	private List<KMedoidPoint> selectKRandom(List<KMedoidPoint> KMedoidPoints) {
		List<KMedoidPoint> medoids = new ArrayList<KMedoidPoint>();
		while(medoids.size() < k){
			int idx = (int) (Math.random() * KMedoidPoints.size());
			if(!medoids.contains(KMedoidPoints.get(idx)))
				medoids.add(KMedoidPoints.get(idx));
		}
		return medoids;
	}

}
