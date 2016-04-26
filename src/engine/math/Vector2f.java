package engine.math;

public class Vector2f {

	public static final Vector2f X_AXIS = new Vector2f(1,0);
	public static final Vector2f Y_AXIS = new Vector2f(0,1);
	
	public float x;
	public float y;
	
	public Vector2f() {
		this(0,0);
	}
	
	public Vector2f(Vector2f vector) {
		this(vector.x, vector.y);
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f add(Vector2f vector) {
		return new Vector2f(x + vector.x, y + vector.y);
	}
	
	public Vector2f subtract(Vector2f vector) {
		return new Vector2f(x - vector.x, y - vector.y);
	}
	
	public Vector2f multiply(float scalar) {
		return new Vector2f(x * scalar, y * scalar);
	}
	
	public Vector2f divide(float scalar) {
		return new Vector2f(x / scalar, y / scalar);
	}
	
	public float dot(Vector2f vector) {
		return (x * vector.x) + (y * vector.y);
	}
	
	public Vector2f normalize() {
		float norm = norm();
		return new Vector2f(x / norm, y / norm);
	}
	
	public Vector2f reverse() {
		return new Vector2f(x * -1, y * -1);
	}
	
	public float norm() {
		return (float) Math.sqrt((x*x) + (y*y));
	}
	
	public boolean equals(Vector2f vector, double epsilon) {
		double deltaX = Math.abs(vector.x - x);
		double deltaY = Math.abs(vector.y - y);
		
		return (deltaX < epsilon && deltaY < epsilon);
	}
	
	@Override
	public String toString() {
		return "<" + x + ", " + y + ">";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector2f) {
			return equals((Vector2f)obj, 0.005);
		}
		else {
			return false;
		}
	}
}
