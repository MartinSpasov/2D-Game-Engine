package engine.physics.collision;

import engine.math.Vector2f;
import engine.physics.geometry.Shape;

public class Collision {

	private Shape collider;
	private Vector2f direction;
	
	public Collision(Shape collider, Vector2f direction) {
		this.collider = collider;
		this.direction = direction;
	}

	public Shape getCollider() {
		return collider;
	}
	
	public Vector2f getDirection() {
		return direction;
	}
	
}
