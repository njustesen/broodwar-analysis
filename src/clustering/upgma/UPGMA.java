/**
 * OIRGINAL CODE FROM http://www.itu.dk/~sestoft/bsa.html, http://www.itu.dk/~sestoft/bsa/Match2.java
 */
package clustering.upgma;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import clustering.ClusterPoint;

public class UPGMA {
	int K;						// The number of clusters created so far
	UPCluster[] cluster;		// The nodes (clusters) of the resulting tree
	List<ClusterPoint> input;
	
	public void init(List<ClusterPoint> input){
		this.input = input;
		double[][] ds = createDistanceMatrix(input);
	    int N = ds.length;
	    K = N;
	    if (K==0)
	    	return;
	    cluster = new UPCluster[2*N-1];
	    
	    System.out.println("Creating single-item UP clusters.");
	    for (int i=0; i<N; i++) {
	    	if (i%1000==0)
				System.out.println(i + "/" + N + " UP single-item clusters created.");
	    	cluster[i] = new UPCluster(i, ds[i]);
	    }
	    
	    System.out.println("Pairing UP clusters.");
	    while (K < 2*N-1){
	    	if (K%100==0)
				System.out.println(K + "/" + (2*N-1) + " clusters created.");
	    	findAndJoin();
	    }
	    System.out.println("UPGMA done!");
	}
	
	public UPCluster getRoot()
	{ return cluster[K-1]; }
	  
	public double d(int i, int j) 
	{ return cluster[Math.max(i, j)].dmat[Math.min(i, j)]; }
		
	void findAndJoin() { // Find closest two live clusters and join them
		int mini = -1, minj = -1;
	    double mind = Double.POSITIVE_INFINITY;
	    for (int i=0; i<K; i++) 
	    	if (cluster[i].live())
		for (int j=0; j<i; j++) 
			if (cluster[j].live()) {
				double d = d(i, j);
				if (d < mind) {
					mind = d;
			      	mini = i;
			      	minj = j;
			    }
			}
	    join(mini, minj);
	}
	
	public void join(int i, int j) { // Join i and j to form node K
		//  System.out.println("Joining " + (i+1) + " and " + (j+1) + " to form "    + (K+1) + " at height " + (int)(d(i, j) * 50)/100.0);
		double[] dmat = new double[K];
		for (int m=0; m<K; m++)
		  if (cluster[m].live() && m != i && m != j) 
		dmat[m] = (d(i, m) * cluster[i].card + d(j, m) * cluster[j].card)
		          / (cluster[i].card + cluster[j].card);
		cluster[K] = new UPCluster(K, cluster[i], cluster[j], d(i, j) / 2, dmat);
		cluster[i].kill(); 
		cluster[j].kill();
		K++;
	}
	
	public List<List<ClusterPoint>> getClusters(int clusters){
		if (K==0)
			return null;
		List<UPCluster> up = cutTree(this.getRoot(), clusters);
		List<List<ClusterPoint>> result = new ArrayList<List<ClusterPoint>>();
		for (int i=0; i < up.size(); i++){
			List<ClusterPoint> tmp = new ArrayList<ClusterPoint>();
			for (int p: getLeafs(up.get(i))){
				tmp.add(this.input.get(p-1));
			}
			
			result.add(tmp);
		}
	
		return result;
	}
	
	public List<List<ClusterPoint>> getClustersWithSupport(double support) {
		if (K==0)
			return null;
		List<UPCluster> up = cutTreeWithMinCard(this.getRoot(), (double)getRoot().card * support);
		List<List<ClusterPoint>> result = new ArrayList<List<ClusterPoint>>();
		for (int i=0; i < up.size(); i++){
			List<ClusterPoint> tmp = new ArrayList<ClusterPoint>();
			for (int p: getLeafs(up.get(i))){
				tmp.add(this.input.get(p-1));
			}
			
			result.add(tmp);
		}
	
		return result;
	}
	
	public List<UPCluster> cutTreeWithMinCard(UPCluster c, double min){
		List<UPCluster> up=new ArrayList<UPCluster>();
		
		if (c.card >= min){
			if (c.right != null)
				up.addAll(cutTreeWithMinCard(c.right, min));
			if (c.left != null)
				up.addAll(cutTreeWithMinCard(c.left, min));
			if (up.isEmpty())
				up.add(c);
		}
		
		return up;
	}
	
	public List<Integer> getLeafs(UPCluster c){
		List<Integer> ids = new ArrayList<Integer>();
		if (c.left != null){
			ids.addAll(getLeafs(c.left));
		}
		if (c.right != null){
			ids.addAll(getLeafs(c.right));
		}
		if (c.left == null && c.right == null){
			ids.add(c.lab);
		}
		return ids;
	}
	
	public List<UPCluster> cutTree(UPCluster c, int clusterLimit){
		List<UPCluster> up=new ArrayList<UPCluster>();
		Queue<UPCluster> q=new LinkedList<UPCluster>();
		if (c.card > 0 && c.left != null && c.right != null){
			q.add(c);
			up.add(c);
			while (up.size()<clusterLimit && !q.isEmpty()){
				UPCluster current =q.poll();
				if (current==null){break;}
				if (current.card > 0 && current.left != null && current.right != null){
					up.remove(current);
					up.add(current.left);
					up.add(current.right);
					if (current.left.height>current.right.height){
						q.add(current.left);
						q.add(current.right);
					} else{
						q.add(current.right);
						q.add(current.left);
					}
				}
			}
			
		} else {
			up.add(c);
		}
		return up;
	}
	
	public double[][] createDistanceMatrix(List<ClusterPoint> points){
		double[][] ds = new double[points.size()][];
		System.out.println("Creating distance matrix.");
		if (points.size() > 0){
			ds[0] = new double[]{};
			for (int i=1; i < points.size();i++){
				double[] tmp = new double[i];
				if (i%1000==0)
					System.out.println(i + "/" + points.size() + " points added to distance matrix.");
				for (int j=0;j<i;j++){
					tmp[j] = points.get(i).distance(points.get(j));
				}
				ds[i] = tmp;
			}
		}
		System.out.println("Done creating distance matrix!");
		return ds;
	}
	
	public void printMatrix(double[][] m){
		for (double[] d : m){
			for (double i : d){
				System.out.print(i+" ");
			}
			System.out.println();
		}
	}

}

// UPGMA clusters or trees, built by the UPGMA algorithm
class UPCluster {
	int lab;			// Cluster identifier
	int card;			// The number of sequences in the cluster
	double height;		// The height of the node
	UPCluster left, right;	// Left and right children, or null
	double[] dmat;		// Distances to lower-numbered nodes, or null

	public UPCluster(int lab, double[] dmat) {	// Leaves = single sequences
		this.lab = lab + 1; 
    	card = 1;
    	this.dmat = dmat;
	}

	public UPCluster(int lab, UPCluster left, UPCluster right, double height, 
			double[] dmat) { 
		this.lab = lab + 1; 
	    this.left = left;
	    this.right = right;
	    card = left.card + right.card;
	    this.height = height;
	    this.dmat = dmat;
	}

	public boolean live()
	{ return dmat != null; }

	public void kill() 
	{ dmat = null; }

}
