package engine.physics.geometry;

public class Circle extends Shape {
	
	private float radius;
	
	public Circle(float radius) {
		this(0, 0, radius);
	}
	
	public Circle(float x, float y, float radius) {
		super(x, y);
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public boolean intersects(Circle circle) {
		float dist = (float) Math.sqrt(Math.pow((circle.getX() - getX()), 2) + Math.pow((circle.getY() - getY()), 2));
		
		return (dist < (radius + circle.getRadius()));
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Circle) {
			Circle circle = (Circle) o;
			
			return (circle.getX() == getX() && circle.getY() == getY() && circle.getRadius() == radius);
		}
		else {
			return false;
		}
	}
}
