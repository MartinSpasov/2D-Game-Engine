package engine;

import java.util.ArrayList;
import java.util.HashMap;

import engine.graphics.Mesh;
import engine.graphics.MeshBatch;
import engine.graphics.RenderEngine;
import engine.object.GameObject;

public class Scene {

	private RenderEngine renderer;
	
	// TODO PUBLIC FOR TESTING ONLY
	public ArrayList<GameObject> objects;
	
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
			// Should move all this render stuff to RenderEngine
//			if (object.getMesh() instanceof InstancedMesh) {
//				MeshBatch batch = batches.get(object.getMesh());
//			
//				if (batch == null) {
//					batch = new MeshBatch((InstancedMesh)object.getMesh(), object.getTexture());
//					batches.put((InstancedMesh)object.getMesh(), batch);
//				}
//			
//				batch.addToBatch(object.getTransform().toMatrix());
//			}
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
			renderer.render(batches.get(mesh), Game.anim, Game.tex);
			//batches.clear();
		}
		
		//batches.clear();
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
