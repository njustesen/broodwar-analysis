package clustering.kmedoids;

import clustering.ClusterPoint;

public class KMedoidPoint {

	ClusterPoint point;
	KMedoidPoint medoid;
	double distance;
	
	public KMedoidPoint(ClusterPoint point) {
		super();
		this.point = point;
	}

	public double distance(KMedoidPoint other) {
		if (other == medoid)
			return distance;
		medoid = other;
		return (distance = other.point.distance(point));
	}

	@Override
	public String toString() {
		return point.toString();
	}

	
	
}
