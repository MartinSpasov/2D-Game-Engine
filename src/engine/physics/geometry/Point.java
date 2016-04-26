package engine.physics.geometry;

public class Point {

	public float x;
	public float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public boolean equals(Point point, double epsilon) {
		double deltaX = Math.abs(point.x - x);
		double deltaY = Math.abs(point.y - y);
		
		return (deltaX < epsilon && deltaY < epsilon);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			return equals((Point)obj, 0.005);
		}
		else {
			return false;
		}
	}
	
}
