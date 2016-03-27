package engine.physics.collision;

import java.util.ArrayList;
import java.util.HashSet;

import engine.object.GameObject;
import engine.object.component.ColliderComponent;
import engine.physics.geometry.Shape;

public class CollisionSystem {

	private HashSet<ObjectPair> pairs;
	
	private ArrayList<ColliderComponent> objects;
	private ArrayList<Shape> staticColliders;
	
	public CollisionSystem() {
		pairs = new HashSet<ObjectPair>();
		objects = new ArrayList<ColliderComponent>();
		staticColliders = new ArrayList<Shape>();
	}
	
	public void narrowScanOLD(GameObject[] objects) {
//		for (int i = 0; i < objects.length; i++) {
//			for (int j = i + 1; j < objects.length; j++) {
//				// TODO if colliding
//				if (true) {
//					pairs.add(new ObjectPair(objects[i], objects[j]));
//					//System.out.println(i);
//				}
//			}
//		}
	}
	
	public void addCollider(ColliderComponent collider) {
		objects.add(collider);
	}
	
	public void addStaticCollider(Shape shape) {
		staticColliders.add(shape);
	}
	
	public void narrowScan() {
		for (int i = 0; i < objects.size(); i++) {
			for (int j = i + 1; j < objects.size(); j++) {
				ColliderComponent a = objects.get(i);
				ColliderComponent b = objects.get(j);
				if (Shape.intersects(a.getShape(), b.getShape())) {
					a.getParentObject().broadcastMessage("COLLISION", b);
					b.getParentObject().broadcastMessage("COLLISION", a);
				}
			}
			for (int k = 0; k < staticColliders.size(); k++) {
				ColliderComponent component = objects.get(i);
				Shape shape = staticColliders.get(k);
				System.out.println(shape.toString() + " and " + component.getShape());
				if (Shape.intersects(component.getShape(), shape)) {
					System.out.println("PING PING");
					component.getParentObject().broadcastMessage("COLLISION", shape);
				}
			}
		}
		objects.clear();
	}
}
