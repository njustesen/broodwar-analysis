package clustering.upgma;

import java.util.ArrayList;
import java.util.List;

import clustering.ClusterPoint;
import clustering.kmedoids.KMedoidCluster;
import clustering.kmedoids.KMedoids;
import clustering.kmedoids.Point2D;

public class UpgmaTest {

	
	public static void main(String[] args) {
		
		List<ClusterPoint> points = new ArrayList<ClusterPoint>();
		points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));
		points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));points.add(new Point2D(0,0));
		points.add(new Point2D(1,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(2,0));
		points.add(new Point2D(1,0));
		
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,5));
		points.add(new Point2D(13,6));
		points.add(new Point2D(12,4));
		points.add(new Point2D(13,3));
		
		points.add(new Point2D(22,2));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,3));
		points.add(new Point2D(23,4));
		points.add(new Point2D(22,3));
		 
		UPGMA upgma = new UPGMA();
		upgma.init(points);
		
		List<List<ClusterPoint>> clusters = upgma.getClusters(3);
		
		for(List<ClusterPoint> cluster : clusters){
			System.out.println("Cluster");
			for(ClusterPoint point : cluster){
				System.out.println(((Point2D)point));
			}
		}
		
	}

}
