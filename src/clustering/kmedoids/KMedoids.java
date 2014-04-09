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
		
			// Assigne to clusters
			clusters = new ArrayList<KMedoidCluster>(clusterToMedoids(points, medoids));
			
			// Test new configurations
			List<KMedoidPoint> bestConfig = medoids;
			double lowestCost = cost(clusters, medoids);
			for(KMedoidPoint medoid : medoids){
				for(KMedoidPoint KMedoidPoint : medoids){
					if (KMedoidPoint == medoid)
						continue;
					List<KMedoidPoint> swap = swap(medoids, medoid, KMedoidPoint);
					double cost = cost(clusters, swap);
					if (cost < lowestCost){
						lowestCost = cost;
						bestConfig = swap;
					}
				}
			}
			
			// 5	Repeat steps 2 to 4 until there is no change in the medoid.
			if (!same(medoids, bestConfig)){
				medoids = bestConfig;
			} else {
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

	private double cost(List<KMedoidCluster> clusters, List<KMedoidPoint> medoids) {
		
		double cost = 0;
		int i = 0;
		for(KMedoidCluster cluster : clusters){
			KMedoidPoint standInMedoid = medoids.get(i);
			for(KMedoidPoint KMedoidPoint : cluster.members){
				cost += KMedoidPoint.distance(standInMedoid);
			}
		}
		
		return cost;
	}


	private List<KMedoidPoint> swap(List<KMedoidPoint> medoids, KMedoidPoint medoid, KMedoidPoint KMedoidPoint) {
		List<KMedoidPoint> newMedoids = new ArrayList<KMedoidPoint>();
		newMedoids.add(KMedoidPoint);
		for(KMedoidPoint m : medoids){
			if (m != medoid)
				newMedoids.add(m);
		}
		return newMedoids;
	}

	private Collection<KMedoidCluster> clusterToMedoids(List<KMedoidPoint> KMedoidPoints, List<KMedoidPoint> medoids) {
		
		// Create clusters with medoids
		Map<KMedoidPoint, KMedoidCluster> medoidClusters = new HashMap<KMedoidPoint, KMedoidCluster>();
		for(KMedoidPoint medoid : medoids){
			medoidClusters.put(medoid, new KMedoidCluster(medoid));
		}

		// Assign to clusters
		for(KMedoidPoint KMedoidPoint : KMedoidPoints){
			KMedoidPoint closestMedoid = null;
			double closestDistance = Double.MAX_VALUE;
			for(KMedoidPoint medoid : medoids){
				if (KMedoidPoint.distance(medoid) < closestDistance){
					closestMedoid = medoid; 
				}
			}
			medoidClusters.get(closestMedoid).getMembers().add(KMedoidPoint);
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
