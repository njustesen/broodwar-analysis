package kmedoids;

import java.util.ArrayList;
import java.util.List;

import clustering.kmedoids.KMedoidCluster;
import clustering.kmedoids.KMedoidPoint;
import clustering.kmedoids.KMedoids;

public class KMedoidsTest {

	
	public static void main(String[] args) {
		
		List<KMedoidPoint> points = new ArrayList<KMedoidPoint>();
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
		 
		List<KMedoidCluster> clusters = new KMedoids(3).cluster(points);
		
		for(KMedoidCluster cluster : clusters){
			System.out.println("Cluster");
			for(KMedoidPoint point : cluster.getMembers()){
				System.out.println(((Point2D)point));
			}
		}
		
	}

}