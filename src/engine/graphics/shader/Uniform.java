package engine.graphics.shader;

public class Uniform {
	
	private String name;
	
	private int location;
	private int size;
	private int type;
	
	public Uniform(String name, int location, int size, int type) {
		this.name = name;
		this.location = location;
		this.size = size;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLocation() {
		return location;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
