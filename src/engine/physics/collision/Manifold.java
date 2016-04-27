package engine.physics.collision;

import engine.math.Vector2f;
import engine.physics.geometry.Shape;

public class Manifold {

	private Shape colA;
	private Shape colB;
	private float depth;
	private Vector2f direction;
	
	public Manifold(Shape colA, Shape colB, float depth, Vector2f direction) {
		this.colA = colA;
		this.colB = colB;
		this.depth = depth;
		this.direction = direction;
	}

	public Shape getColliderA() {
		return colA;
	}
	
	public Shape getColliderB() {
		return colB;
	}
	
	public float getDepth() {
		return depth;
	}
	
	public Vector2f getDirection() {
		return direction;
	}
	
	@Override
	public String toString() {
		return colA.toString() + "\n" + colB.toString() + "\ndirection: " + direction.toString() + "\ndepth: " + depth;
	}
	
}
