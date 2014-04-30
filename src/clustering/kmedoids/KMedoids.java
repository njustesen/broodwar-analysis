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

		// Initialize: randomly select k of the n data points as the medoids
		List<ClusterPoint> medoids = selectKRandom(points);

		// Associate each data point to the closest medoid. ("closest" here is defined using any valid distance metric, most commonly Euclidean distance, Manhattan distance or Minkowski distance)
                List<KMedoidCluster> clusters = new ArrayList<KMedoidCluster>(clusterToMedoids(points, medoids));

                boolean again = true;

		while (again) {
                        again = false;

                        // Set the current best options
                        List<ClusterPoint> bestMedoids = medoids;
                        List<KMedoidCluster> bestClusters = clusters;
			double lowestCost = cost(bestClusters);

			for (ClusterPoint medoid : medoids) {
				for (ClusterPoint point : points) {
                                        // Already in the current medoids, so skip it
					if (medoids.contains(point))
						continue;
                                        // Swap current medoid and the current point
					List<ClusterPoint> newMedoids = swap(medoids, medoid, point);
                                        // Generate the new clusters
					List<KMedoidCluster> newClusters = new ArrayList<KMedoidCluster>(clusterToMedoids(points, newMedoids));
                                        // Compute the cost of the new clusters
					double cost = cost(newClusters);
                                        // Keep it if its cost the lowest
					if (cost < lowestCost) {
						lowestCost = cost;
						bestMedoids = newMedoids;
                                                bestClusters = newClusters;
                                                // Have change so keep going
                                                again = true;
					}
				}
			}

                        clusters = bestClusters;
                        medoids = bestMedoids;
		}

		return clusters;

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
		List<ClusterPoint> newMedoids = new ArrayList<ClusterPoint>(medoids);
                newMedoids.set(newMedoids.indexOf(medoid), point);

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
