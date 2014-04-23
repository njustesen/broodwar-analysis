package clustering.kmedoids;

import clustering.ClusterPoint;


public class Point2D implements ClusterPoint {

	private double x;
	private double y;
	
	public Point2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public double distance(ClusterPoint other) {
		
		if (!(other instanceof Point2D))
			return Double.MAX_VALUE;
		
		Point2D other2D = (Point2D)other;
		
		double x2 = other2D.getX();
		double y2 = other2D.getY();
		
		return Math.sqrt((x-x2)*(x-x2) + (y-y2)*(y-y2));
		
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Point2D [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point2D other = (Point2D) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

}
