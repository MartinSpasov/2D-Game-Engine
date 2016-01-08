package engine.object;

import java.util.ArrayList;

import engine.Game;
import engine.object.component.ObjectComponent;

public class GameObject {

	private static int count = 0; 
	
	private int id;
	
	private Transform transform;
	
	private ArrayList<ObjectComponent> components;

	public GameObject() {
		this(new Transform());
	}
	
	public GameObject(Transform transform) {
		this.transform = transform;
		id = count++;
		components = new ArrayList<ObjectComponent>();
	}
	
	public void tick(float delta, Game game) {
		for (ObjectComponent component : components) {
			component.tick(delta, game);
		}
	}
	
	public void addComponent(ObjectComponent component) {
		components.add(component);
	}
	
	public <T> void broadcastMessage(String message, T param) {
		for (ObjectComponent component : components) {
			component.receiveMessage(message, param);
		}
	}

	public Transform getTransform() {
		return transform;
	}
	
	public int getId() {
		return id;
	}

	
}
