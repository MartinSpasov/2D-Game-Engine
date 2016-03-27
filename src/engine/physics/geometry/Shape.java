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
			System.out.println(intersects((Rectangle)a, (Rectangle)b));
			return intersects((Rectangle)a, (Rectangle)b);
		}
		else if (a instanceof Circle && b instanceof Circle) {
			return intersects((Circle)a, (Circle)b);
		}
		else {
			return false;
		}
	}
	
	public static boolean intersects(Rectangle a, Rectangle b) {
		return (a.getX() < b.getX() + b.getWidth() && a.getX() + a.getWidth() > b.getX() && a.getY() < b.getY() + b.getHeight() && a.getY() + a.getHeight() > b.getY());
	}
	
	public static boolean intersects(Circle a, Circle b) {
		float dist = (float) Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
		
		return (dist < (a.getRadius() + b.getRadius()));
	}
}
