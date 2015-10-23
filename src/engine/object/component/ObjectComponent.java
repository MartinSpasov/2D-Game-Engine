package engine.object.component;

import engine.object.GameObject;

abstract public class ObjectComponent {

	private GameObject parentObject;
	
	public ObjectComponent(GameObject parentObject) {
		this.parentObject = parentObject;
	}
	
	public GameObject getParentObject() {
		return parentObject;
	}
	
	public abstract void tick(float delta);
}
