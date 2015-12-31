package engine;

import java.util.ArrayList;

import engine.object.GameObject;

public class Scene {

	private ArrayList<GameObject> objects;
	
	public Scene() {
		objects = new ArrayList<GameObject>();
	}
	
	public void tick(float delta, Game game) {
		for (GameObject object : objects) {
			object.tick(delta, game);
		}
	}
	
	public void addObject(GameObject object) {
		objects.add(object);
	}

}
