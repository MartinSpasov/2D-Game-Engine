package engine.object;

import engine.graphics.Mesh;
import engine.graphics.Texture;
import engine.object.component.ColliderComponent;
import engine.physics.geometry.Shape;

public class GameObject {

	private Transform transform;
	
	private Mesh mesh;
	private Texture texture;
	
	private ColliderComponent collider;
	
	public GameObject(Transform transform, Mesh mesh, Texture texture, Shape shape) {
		this.transform = transform;
		this.mesh = mesh;
		this.texture = texture;
		this.collider = new ColliderComponent(this, shape);
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
	
	public ColliderComponent getCollider() {
		return collider;
	}
}
