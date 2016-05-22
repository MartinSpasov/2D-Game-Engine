package engine.object;

import java.util.ArrayList;

import engine.Game;
import engine.graphics.RenderSystem;
import engine.object.component.ObjectComponent;
import engine.object.component.RenderableComponent;

public class GameObject {

	private Transform transform;
	
	private ArrayList<ObjectComponent> components;
	private ArrayList<RenderableComponent> renderableComponents;

	public GameObject() {
		this(new Transform());
	}
	
	public GameObject(Transform transform) {
		this.transform = transform;
		components = new ArrayList<ObjectComponent>();
		renderableComponents = new ArrayList<RenderableComponent>();
	}
	
	public void tick(float delta, Game game) {
		for (ObjectComponent component : components) {
			component.tick(delta, game);
		}
		for (RenderableComponent component : renderableComponents) {
			component.tick(delta, game);
		}
	}
	
	public void render(RenderSystem renderer) {
		for (RenderableComponent component : renderableComponents) {
			component.render(renderer);
		}
	}
	
	public void addComponent(ObjectComponent component) {
		components.add(component);
	}
	
	public void addComponent(RenderableComponent component) {
		renderableComponents.add(component);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ObjectComponent> T getComponent(Class<T> type) {
		for (ObjectComponent component : components) {
			if (type.isInstance(component)) {
				return (T) component;
			}
		}
		for (RenderableComponent component : renderableComponents) {
			if (type.isInstance(component)) {
				return (T) component;
			}
		}
		return null;
		
	}
	
	public <T> void broadcastMessage(String message, T param) {
		for (ObjectComponent component : components) {
			component.receiveMessage(message, param);
		}
		for (RenderableComponent component : renderableComponents) {
			component.receiveMessage(message, param);
		}
	}

	public Transform getTransform() {
		return transform;
	}

	
}
