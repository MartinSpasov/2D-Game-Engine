package engine.physics.geometry;

public class Rectangle extends Shape {

	private float width;
	private float height;
	
	private float halfWidth;
	private float halfHeight;
	
	private Point topLeft;
	private Point topRight;
	private Point bottomLeft;
	private Point bottomRight;
	
	public Rectangle(float width, float height) {
		this(0, 0, width, height);
	}
	
	public Rectangle(float x, float y, float width, float height) {
		super(x,y);
		this.width = width;
		this.height = height;
		
		halfWidth = width / 2.0f;
		halfHeight = height / 2.0f;
		
		topLeft = new Point(x - halfWidth, y + halfHeight);
		topRight = new Point(x + halfWidth, y + halfHeight);
		bottomLeft = new Point(x - halfWidth, y - halfHeight);
		bottomRight = new Point(x + halfWidth, y - halfHeight);
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setWidth(float width) {
		this.width = width;
		halfWidth = width / 2.0f;
	}
	
	public void setHeight(float height) {
		this.height = height;
		halfHeight = height / 2.0f;
	}
	
	public boolean intersects(Rectangle rect) {
		// FIXME considers x and y to be the top left corner of the origin
		return (
				topLeft.x < rect.topRight.x && 
				topRight.x > rect.topLeft.x && 
				topLeft.y > rect.bottomLeft.y && 
				bottomLeft.y < rect.topLeft.y
				);
	}
	
	@Override
	public void setX(float x) {
		super.setX(x);
		topLeft.x = x - halfWidth;
		topRight.x = x + halfWidth;
		bottomLeft.x = x - halfWidth;
		bottomRight.x = x + halfWidth;
	}
	
	@Override
	public void setY(float y) {
		super.setY(y);
		topLeft.y = y + halfHeight;
		topRight.y = y + halfHeight;
		bottomLeft.y = y - halfHeight;
		bottomRight.y = y - halfHeight;
	}

	public boolean contains(float x, float y) {
		float topLeftX = getX() - (width / 2.0f);
		float topLeftY = getY() + (height / 2.0f);
		
		return ((x >= topLeftX && x <= topLeftX + width) && (y <= topLeftY && y >= topLeftY - height));
	}
	
	@Override
	public String toString() {
		return ("tl: " + topLeft.toString() + 
				"\ntr: " + topRight.toString() + 
				"\nbl: " + bottomLeft.toString() + 
				"\nbr: " + bottomRight.toString() + 
				"\norigin: (" + getX() + ", " + getY() + ")\nwidth: " + width + " height: " + height);
		//return "x: " + getX() + " y: " + getY() + " width: " + width + " height: " + height;
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
