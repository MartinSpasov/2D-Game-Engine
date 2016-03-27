package engine.object.component;

import engine.Game;
import engine.object.GameObject;
import engine.object.Transform;
import engine.physics.geometry.Shape;

public class ColliderComponent extends ObjectComponent {

	private Shape shape;
	
	public ColliderComponent(GameObject parentObject, Shape shape) {
		super(parentObject);
		this.shape = shape;
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
		if (message.equals("COLLISION") && param instanceof ColliderComponent) {
			ColliderComponent other = (ColliderComponent) param;
			
			float dx = shape.getX() - other.getShape().getX();
			float dy = shape.getY() - other.getShape().getY();
			
			//System.out.println("DX: " + dx + " DY: " + dy);
		}
	}
	
	public Shape getShape() {
		return shape;
	}

}
