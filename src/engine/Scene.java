package engine;

import java.util.ArrayList;

import engine.graphics.RenderSystem;
import engine.object.GameObject;

public class Scene {

	public static final float G = 9.807f;
	
	private ArrayList<GameObject> objects;
	
	private float gravity;
	
	public Scene() {
		objects = new ArrayList<GameObject>();
	}
	
	public void tick(float delta, Game game) {
		for (GameObject object : objects) {
			object.tick(delta, game);
		}
	}
	
	public void render(RenderSystem renderer) {
		for (GameObject object : objects) {
			object.render(renderer);
		}
	}

	public void addObject(GameObject object) {
		objects.add(object);
	}
	
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	public float getGravity() {
		return gravity;
	}

}
