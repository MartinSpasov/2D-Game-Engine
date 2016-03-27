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
		// FIXME considers x and y to be the top left corner of the origin
		return (getX() < rect.getX() + rect.getWidth() && getX() + width > rect.getX() && getY() < rect.getY() + rect.getHeight() && getY() + height > rect.getY());
	}
	
	public boolean contains(float x, float y) {
		float topLeftX = getX() - (width / 2.0f);
		float topLeftY = getY() + (height / 2.0f);
		
		return ((x >= topLeftX && x <= topLeftX + width) && (y <= topLeftY && y >= topLeftY - height));
	}
	
	@Override
	public String toString() {
		return "x: " + getX() + " y: " + getY() + " width: " + width + " height: " + height;
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
