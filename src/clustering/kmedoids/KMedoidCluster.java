package clustering.kmedoids;

import java.util.ArrayList;
import java.util.List;

public class KMedoidCluster {

	private KMedoidPoint medoid;
	private List<KMedoidPoint> members;
	
	public KMedoidCluster(KMedoidPoint medoid, List<KMedoidPoint> members) {
		super();
		this.medoid = medoid;
		this.members = members;
	}
	
	public KMedoidCluster(KMedoidPoint medoid) {
		super();
		this.medoid = medoid;
		this.members = new ArrayList<KMedoidPoint>();
	}

	public KMedoidPoint getMedoid() {
		return medoid;
	}

	public void setMedoid(KMedoidPoint medoid) {
		this.medoid = medoid;
	}

	public List<KMedoidPoint> getMembers() {
		return members;
	}

	public void setMembers(List<KMedoidPoint> members) {
		this.members = members;
	}
	
}
