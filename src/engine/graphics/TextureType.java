package engine.graphics;

public enum TextureType {

	DIFFUSE(0),
	NORMAL_MAP(1);
	
	private final int value;
	
	private TextureType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
