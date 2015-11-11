package engine;

import java.util.ArrayList;
import java.util.HashMap;

import engine.graphics.Mesh;
import engine.graphics.MeshBatch;
import engine.graphics.RenderEngine;
import engine.object.GameObject;

public class Scene {

	private RenderEngine renderer;
	
	private ArrayList<GameObject> objects;
	
	private HashMap<Mesh, MeshBatch> batches;
	
	public Scene(RenderEngine renderer) {
		this.renderer = renderer;
		objects = new ArrayList<GameObject>();
		batches = new HashMap<Mesh, MeshBatch>();
	}
	
	public void tick(float delta) {
		for (GameObject object : objects) {
			object.tick(delta);
		}
	}
	
	public void render() {
		for (GameObject object : objects) {
			// Update batches
			MeshBatch batch = batches.get(object.getMesh());
			
			if (batch != null) {
				batch.addToBatch(object.getTransform().toMatrix());
			}
			else {
				renderer.render(object);
			}
		}
		
		for (Mesh mesh : batches.keySet()) {
			renderer.render(batches.get(mesh), Game.animComp.getCurrentFrame(), Game.tex);
		}
		
	}
	
	public void addObject(GameObject object) {
		objects.add(object);
	}
	
	public void addMeshBatch(MeshBatch batch) {
		batches.put(batch.getMesh(), batch);
	}
	
	public void destroy() {
		for (GameObject object : objects) {
			object.destroy();
		}
		for (Mesh mesh : batches.keySet()) {
			batches.get(mesh).destroy();
		}
	}
}
