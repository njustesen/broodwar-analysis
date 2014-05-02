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
	
	

	public ClusterPoint getPoint() {
		return point;
	}

	public void setPoint(ClusterPoint point) {
		this.point = point;
	}

	public KMedoidPoint getMedoid() {
		return medoid;
	}

	public void setMedoid(KMedoidPoint medoid) {
		this.medoid = medoid;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return point.toString();
	}

	
	
}
