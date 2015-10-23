package engine.physics.collision;

import java.util.HashSet;

import engine.object.GameObject;

public class CollisionEngine {

	private HashSet<ObjectPair> pairs;
	
	public CollisionEngine() {
		pairs = new HashSet<ObjectPair>();
	}
	
	public void narrowScan(GameObject[] objects) {
		for (int i = 0; i < objects.length; i++) {
			for (int j = i + 1; j < objects.length; j++) {
				// TODO if colliding
				if (true) {
					pairs.add(new ObjectPair(objects[i], objects[j]));
					//System.out.println(i);
				}
			}
		}
		System.out.println(pairs.size());
	}
	
}
