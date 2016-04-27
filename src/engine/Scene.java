package engine;

import java.util.ArrayList;

import engine.graphics.Background;
import engine.graphics.RenderSystem;
import engine.object.GameObject;

public class Scene {

	public static final float G = 9.807f;
	
	private ArrayList<GameObject> objects;
	private ArrayList<Background> backgrounds;
	
	private float gravity;
	
	public Scene() {
		objects = new ArrayList<GameObject>();
		backgrounds = new ArrayList<Background>();
	}
	
	public void tick(float delta, Game game) {
		for (GameObject object : objects) {
			object.tick(delta, game);
		}
	}
	
	public void render(RenderSystem renderer) {
		renderBackgrounds(renderer);
		renderObjects(renderer);
	}
	
	protected void renderObjects(RenderSystem renderer) {
		for (GameObject object : objects) {
			object.render(renderer);
		}
	}
	
	protected void renderBackgrounds(RenderSystem renderer) {
		for (Background background : backgrounds) {
			renderer.renderBackground(background);
		}
	}

	public void addObject(GameObject object) {
		objects.add(object);
	}
	
	public void addBackground(Background background) {
		backgrounds.add(background);
	}
	
	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	public float getGravity() {
		return gravity;
	}
	
	public void destroy() {
		for (Background background : backgrounds) {
			background.destroy();
		}
	}

}
