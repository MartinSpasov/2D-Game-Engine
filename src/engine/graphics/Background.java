package engine.graphics;

public class Background {

	private Texture texture;
	
	private float xOffset;
	
	public Background(Texture texture) {
		this.texture = texture;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public float getXOffset() {
		return xOffset;
	}
	
	public void setXOffset(float xOffset) {
		this.xOffset = xOffset;
	}
	
	public void destroy() {
		texture.destroy();
	}
}
