package engine.object;
import engine.graphics.Mesh;
import engine.graphics.Texture;

public class GameObject {

	private Transform transform;
	
	private Mesh mesh;
	private Texture texture;
	
	public GameObject(Transform transform, Mesh mesh, Texture texture) {
		this.transform = transform;
		this.mesh = mesh;
		this.texture = texture;
	}
	
	public void destroy() {
		mesh.destroy();
		texture.destroy();
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Texture getTexture() {
		return texture;
	}
}
