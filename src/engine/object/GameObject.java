package engine.object;

import java.util.ArrayList;

import engine.graphics.Mesh;
import engine.graphics.Texture;
import engine.object.component.ObjectComponent;

public class GameObject {

	private Transform transform;
	
	private ArrayList<ObjectComponent> components;
	
	private Mesh mesh;
	private Texture texture;	
	
	public GameObject(Transform transform, Mesh mesh, Texture texture) {
		this.transform = transform;
		this.mesh = mesh;
		this.texture = texture;
		components = new ArrayList<ObjectComponent>();
	}
	
	public void tick(float delta) {
		for (ObjectComponent component : components) {
			component.tick(delta);
		}
	}
	
	public void addComponent(ObjectComponent component) {
		components.add(component);
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
