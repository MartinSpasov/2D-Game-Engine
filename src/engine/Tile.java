package engine;

public class Tile {

	private float x;
	private float y;
	private int index;
	
	public Tile(float x, float y, int index){
		this.x = x;
		this.y = y;
		this.index = index;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	public String toString() {
		return "x: " + x + " y: " + y + " " + index;
	}
}
