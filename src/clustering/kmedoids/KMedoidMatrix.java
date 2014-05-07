package clustering.kmedoids;

import java.util.List;

import clustering.ClusterPoint;

public class KMedoidMatrix {

	double[][] matrix;
	
	public KMedoidMatrix(List<KMedoidPoint> points) {

		matrix = new double[points.size()][];
		
		if (points.size() > 0){
			matrix[0] = new double[]{};
			for (int i=1; i < points.size();i++){
				points.get(i).setIndex(i);
				double[] tmp = new double[i];
				for (int j=0;j<i;j++){
					tmp[j] = points.get(i).distance(points.get(j));
				}
				matrix[i] = tmp;
			}
		}
		
	}

	public double distance(KMedoidPoint a, KMedoidPoint b){
		if (a.getIndex() == b.getIndex())
			return 0;
		
		int max = Math.max(a.getIndex(), b.getIndex());
		int min = Math.min(a.getIndex(), b.getIndex());
		return matrix[max][min];
	}
	
}
