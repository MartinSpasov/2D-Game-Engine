package engine.physics.collision;

import java.util.ArrayList;

import engine.math.Vector2f;
import engine.object.component.ColliderComponent;
import engine.physics.geometry.Rectangle;
import engine.physics.geometry.Shape;

public class CollisionSystem {

	private ArrayList<ColliderComponent> objects;
	private ArrayList<Shape> staticColliders;
	
	public CollisionSystem() {
		objects = new ArrayList<ColliderComponent>();
		staticColliders = new ArrayList<Shape>();
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
				component.resetPosition();
				if (Shape.intersects(component.getShape(), shape)) {
					// TODO im assuming these are rectangles for now
					
					// Generate manifold for two rectangles
					Rectangle rA = (Rectangle) component.getShape();
					Rectangle rB = (Rectangle) shape;
					
					
					
					//System.out.println(component.getDirection());
					//System.out.println(component.getParentObject().getTransform().getYPos());
					component.getParentObject().broadcastMessage("COLLISION", generateManifold(rA, rB));
				}
			}
		}
		objects.clear();
	}
	
	public Manifold generateManifold(Rectangle rA, Rectangle rB) {
		
		Vector2f direction = new Vector2f();
		float depth = 0;
		
		// Calculate distance between rectangles
		Vector2f dist = new Vector2f(rB.getX(), rB.getY()).subtract(new Vector2f(rA.getX(), rA.getY()));
		
		float halfWidthA = rA.getWidth() / 2.0f;
		float halfWidthB = rB.getWidth() / 2.0f;
		
		// Calculate overlap in x
		float xOverlap = halfWidthA + halfWidthB - Math.abs(dist.x);
		
		if (xOverlap > 0) {
			
			float halfHeightA = rA.getHeight() / 2.0f;
			float halfHeightB = rB.getHeight() / 2.0f;
			
			// Calculate overlap in y
			float yOverlap = halfHeightA + halfHeightB - Math.abs(dist.y);
			
			
			// TODO these checks arent necessary if I i already know they're overlapping
			if (yOverlap > 0) {
				
				
				if (xOverlap < yOverlap) {
					
					if (dist.x < 0) {
						direction.set(1, 0);
					}
					else {
						direction.set(-1, 0);
					}
					depth = xOverlap;
					
				}
				else {
					if (dist.y < 0) {
						direction.set(0, 1);
					}
					else {
						direction.set(0, -1);
					}
					depth = yOverlap;
				}
				
			}
			
		}
		
		return new Manifold(rA, rB, depth, direction);
	}
}
