package engine.object.component;

import engine.Game;
import engine.math.Vector2f;
import engine.object.GameObject;
import engine.object.Transform;
import engine.physics.collision.Manifold;
import engine.physics.collision.response.CollisionResponse;
import engine.physics.geometry.Shape;

public class ColliderComponent extends ObjectComponent implements CollisionResponse {

	private Shape shape;
	private CollisionResponse response;
	
	public ColliderComponent(GameObject parentObject, Shape shape) {
		super(parentObject);
		this.shape = shape;
		response = this;
	}

	@Override
	public void tick(float delta, Game game) {
		// Necessary?
		resetPosition();
		
		game.getCollisionSystem().addCollider(this);
	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		if (message.equals("COLLISION") && param instanceof Manifold) {
			response.intersectResponse((Manifold)param);
		}
	}
	
	public void resetPosition() {
		Transform t = getParentObject().getTransform();
		
		shape.setX(t.getXPos());
		shape.setY(t.getYPos());
	}

	public Shape getShape() {
		return shape;
	}
	
	public void setCollisionResponse(CollisionResponse response) {
		this.response = response;
	}

	@Override
	public void intersectResponse(Manifold manifold) {
		// Default response
		//getParentObject().getTransform().translate(-collision.getDirection().x, -collision.getDirection().y, 0);
		Vector2f delta = manifold.getDirection().multiply(manifold.getDepth());
		//System.out.println(manifold);
		//System.out.println(delta.y);
		//System.out.println(delta.x);
		getParentObject().getTransform().translate(delta.x, delta.y, 0);

	}

}
