package clustering.kmedoids;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clustering.ClusterPoint;

public class KMedoids {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private int k;
	
	public KMedoids(int k){
		this.k = k;
	}
	
	public List<KMedoidCluster> cluster(List<ClusterPoint> points){
		
		// 1. 	Initialize: randomly select k of the n data points as the medoids
		List<ClusterPoint> medoids = selectKRandom(points);
		
		// 2. 	Associate each data point to the closest medoid. ("closest" here is defined using any valid distance metric, most commonly Euclidean distance, Manhattan distance or Minkowski distance)
		List<KMedoidCluster> clusters = null;
		while(true){
		
			// Assign to clusters
			clusters = new ArrayList<KMedoidCluster>(clusterToMedoids(points, medoids));
			
			// Test new configurations
			List<ClusterPoint> bestConfig = new ArrayList<ClusterPoint>();
			medoids.clear();
			for(KMedoidCluster cluster : clusters)
				medoids.add(cluster.getMedoid());
			bestConfig = medoids;
			double lowestCost = cost(clusters);
			for(ClusterPoint medoid : medoids){
				for(ClusterPoint point : points){
					if (medoids.contains(point))
						continue;
					List<ClusterPoint> swap = swap(medoids, medoid, point);
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

	private boolean same(List<ClusterPoint> A, List<ClusterPoint> B) {
		
		for(ClusterPoint a : A){
			if (a != B.get(A.indexOf(a))){
				return false;
			}
		}
		
		return true;
	}

	private double cost(List<KMedoidCluster> clusters) {
		
		double cost = 0;
		for(KMedoidCluster cluster : clusters){
			for(ClusterPoint point : cluster.getMembers()){
				cost += point.distance(cluster.getMedoid());
			}
		}
		
		return cost;
	}


	private List<ClusterPoint> swap(List<ClusterPoint> medoids, ClusterPoint medoid, ClusterPoint point) {
		List<ClusterPoint> newMedoids = new ArrayList<ClusterPoint>();
		for(ClusterPoint m : medoids){
			if (m != medoid)
				newMedoids.add(m);
			else
				newMedoids.add(point);
		}
		return newMedoids;
	}

	private Collection<KMedoidCluster> clusterToMedoids(List<ClusterPoint> points, List<ClusterPoint> medoids) {
		
		// Create clusters with medoids
		Map<ClusterPoint, KMedoidCluster> medoidClusters = new HashMap<ClusterPoint, KMedoidCluster>();
		for(ClusterPoint medoid : medoids){
			medoidClusters.put(medoid, new KMedoidCluster(medoid));
			//medoidClusters.get(medoid).getMembers().add(medoid);
		}

		// Assign to clusters
		for(ClusterPoint point : points){
			ClusterPoint closestMedoid = null;
			double closestDistance = Double.MAX_VALUE;
			for(ClusterPoint medoid : medoids){
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

	private List<ClusterPoint> selectKRandom(List<ClusterPoint> ClusterPoints) {
		List<ClusterPoint> medoids = new ArrayList<ClusterPoint>();
		while(medoids.size() < k){
			int idx = (int) (Math.random() * ClusterPoints.size());
			if(!medoids.contains(ClusterPoints.get(idx)))
				medoids.add(ClusterPoints.get(idx));
		}
		return medoids;
	}

}
