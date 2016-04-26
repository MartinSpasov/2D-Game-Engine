package engine.object.component;

import engine.Game;
import engine.math.Vector2f;
import engine.object.GameObject;
import engine.object.Transform;
import engine.physics.collision.Collision;
import engine.physics.collision.CollisionResponse;
import engine.physics.geometry.Shape;

public class ColliderComponent extends ObjectComponent implements CollisionResponse {

	private Shape shape;
	private Vector2f direction;
	private CollisionResponse response;
	
	public ColliderComponent(GameObject parentObject, Shape shape) {
		super(parentObject);
		this.shape = shape;
		direction = new Vector2f();
		response = this;
	}

	@Override
	public void tick(float delta, Game game) {
		Transform t = getParentObject().getTransform();
		
		shape.setX(t.getXPos());
		shape.setY(t.getYPos());
		
		game.getCollisionSystem().addCollider(this);
	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		if (message.equals("DIRECTION") && param instanceof Vector2f) {
			direction = (Vector2f)param;
		}
		else if (message.equals("COLLISION") && param instanceof Collision) {
			response.intersectResponse((Collision)param);
		}
	}
	
	public void setDirection(Vector2f direction) {
		this.direction = direction;
	}
	
	public Vector2f getDirection() {
		return direction;
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public void setCollisionResponse(CollisionResponse response) {
		this.response = response;
	}

	@Override
	public void intersectResponse(Collision collision) {
		// Default response
		getParentObject().getTransform().translate(-collision.getDirection().x, -collision.getDirection().y, 0);
	}

}
