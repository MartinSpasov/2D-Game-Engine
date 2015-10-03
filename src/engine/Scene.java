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
	
	public void tick() {
		
	}
	
	public void render() {
		for (GameObject object : objects) {
			// Should move all this render stuff to RenderEngine
			MeshBatch batch = batches.get(object.getMesh());
			
			if (batch == null) {
				batch = new MeshBatch(object.getMesh(), object.getTexture());
				batches.put(object.getMesh(), batch);
			}
			
			batch.addToBatch(object);
		}
		
		for (Mesh mesh : batches.keySet()) {
			renderer.render(batches.get(mesh));
		}
		
		batches.clear();
	}
	
	public void addObject(GameObject object) {
		objects.add(object);
	}
}
