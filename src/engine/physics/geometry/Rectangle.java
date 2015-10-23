package engine.physics.geometry;

public class Rectangle extends Shape {
	
	private float width;
	private float height;
	
	public Rectangle(float width, float height) {
		this(0, 0, width, height);
	}
	
	public Rectangle(float x, float y, float width, float height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public boolean intersects(Rectangle rect) {
		return (getX() < rect.getX() + rect.getWidth() && getX() + width > rect.getX() && getY() < rect.getY() + rect.getHeight() && getY() + height > rect.getY());
	}
	
	@Override
	public boolean equals(Object o) {		
		if (o instanceof Rectangle) {
			Rectangle rect = (Rectangle) o;
			
			return (rect.getX() == getX() && rect.getY() == getY() && rect.getWidth() == width && rect.getHeight() == height);
		}
		else {
			return false;
		}
	}
}
