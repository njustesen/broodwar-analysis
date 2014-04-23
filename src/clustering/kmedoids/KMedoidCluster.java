package clustering.kmedoids;

import java.util.ArrayList;
import java.util.List;

import clustering.ClusterPoint;

public class KMedoidCluster {

	private ClusterPoint medoid;
	private List<ClusterPoint> members;
	
	public KMedoidCluster(ClusterPoint medoid, List<ClusterPoint> members) {
		super();
		this.medoid = medoid;
		this.members = members;
	}
	
	public KMedoidCluster(ClusterPoint medoid) {
		super();
		this.medoid = medoid;
		this.members = new ArrayList<ClusterPoint>();
	}

	public ClusterPoint getMedoid() {
		return medoid;
	}

	public void setMedoid(ClusterPoint medoid) {
		this.medoid = medoid;
	}

	public List<ClusterPoint> getMembers() {
		return members;
	}

	public void setMembers(List<ClusterPoint> members) {
		this.members = members;
	}
	
}
