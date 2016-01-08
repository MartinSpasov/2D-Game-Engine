package engine.object.component;

import engine.Game;
import engine.object.GameObject;

abstract public class ObjectComponent {

	private GameObject parentObject;
	
	public ObjectComponent(GameObject parentObject) {
		this.parentObject = parentObject;
	}
	
	public GameObject getParentObject() {
		return parentObject;
	}
	
	public abstract void tick(float delta, Game game);
	public abstract <T> void receiveMessage(String message, T param);
}
