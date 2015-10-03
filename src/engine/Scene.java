package engine;

import java.util.ArrayList;

import engine.graphics.RenderEngine;
import engine.object.GameObject;

public class Scene {

	private RenderEngine renderer;
	private ArrayList<GameObject> objects;
	
	public Scene(RenderEngine renderer) {
		this.renderer = renderer;
		objects = new ArrayList<GameObject>();
	}
	
	public void tick() {
		
	}
	
	public void render() {
		for (GameObject object : objects) {
			renderer.addToRender(object);
		}
	}
	
	public void addObject(GameObject object) {
		objects.add(object);
	}
}
