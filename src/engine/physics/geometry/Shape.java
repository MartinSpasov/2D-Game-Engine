package engine.physics.geometry;

public abstract class Shape {
	
	private float x;
	private float y;
	
	public Shape(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public static boolean intersects(Shape a, Shape b) {
		if (a instanceof Rectangle && b instanceof Rectangle) {
			return ((Rectangle)a).intersects((Rectangle)b);
		}
		else {
			return false;
		}
	}

}
