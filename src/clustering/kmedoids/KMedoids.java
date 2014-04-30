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
		
		List<KMedoidPoint> kPoints = new ArrayList<KMedoidPoint>();
		for(ClusterPoint p : points)
			kPoints.add(new KMedoidPoint(p));

		// Initialize: randomly select k of the n data points as the medoids
		List<KMedoidPoint> medoids = selectKRandom(kPoints);

		// Associate each data point to the closest medoid. ("closest" here is defined using any valid distance metric, most commonly Euclidean distance, Manhattan distance or Minkowski distance)
        List<KMedoidCluster> clusters = new ArrayList<KMedoidCluster>(clusterToMedoids(kPoints, medoids));

        boolean again = true;

		while (again) {
                        again = false;

                        // Set the current best options
                        List<KMedoidPoint> bestMedoids = medoids;
                        List<KMedoidCluster> bestClusters = clusters;
			double lowestCost = cost(bestClusters);

			for (KMedoidPoint medoid : medoids) {
				for (KMedoidPoint point : kPoints) {
                                        // Already in the current medoids, so skip it
					if (medoids.contains(point))
						continue;
                                        // Swap current medoid and the current point
					List<KMedoidPoint> newMedoids = swap(medoids, medoid, point);
                                        // Generate the new clusters
					List<KMedoidCluster> newClusters = new ArrayList<KMedoidCluster>(clusterToMedoids(kPoints, newMedoids));
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
			for(KMedoidPoint point : cluster.getMembers()){
				cost += point.distance(cluster.getMedoid());
			}
		}

		return cost;
	}


	private List<KMedoidPoint> swap(List<KMedoidPoint> medoids, KMedoidPoint medoid, KMedoidPoint point) {
		List<KMedoidPoint> newMedoids = new ArrayList<KMedoidPoint>(medoids);
                newMedoids.set(newMedoids.indexOf(medoid), point);

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

	private List<KMedoidPoint> selectKRandom(List<KMedoidPoint> ClusterPoints) {
		List<KMedoidPoint> medoids = new ArrayList<KMedoidPoint>();
		while(medoids.size() < k){
			int idx = (int) (Math.random() * ClusterPoints.size());
			if(!medoids.contains(ClusterPoints.get(idx)))
				medoids.add(ClusterPoints.get(idx));
		}
		return medoids;
	}

}
