package engine.object;

import engine.graphics.Mesh;
import engine.graphics.Texture;
import engine.object.component.ColliderComponent;
import engine.object.component.RigidBodyComponent;
import engine.physics.geometry.Shape;

public class GameObject {

	private Transform transform;
	
	private Mesh mesh;
	private Texture texture;
	
	private ColliderComponent collider;
	private RigidBodyComponent rigidBody;
	
	
	public GameObject(Transform transform, Mesh mesh, Texture texture, Shape shape, float mass) {
		this.transform = transform;
		this.mesh = mesh;
		this.texture = texture;
		collider = new ColliderComponent(this, shape);
		rigidBody = new RigidBodyComponent(this, mass);
	}
	
	public void tick(float delta) {
		rigidBody.tick(delta);
		collider.tick(delta);
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
	
	public RigidBodyComponent getRigidBody() {
		return rigidBody;
	}
}
