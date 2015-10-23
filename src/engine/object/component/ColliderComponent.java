package engine.object.component;

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
	public void tick(float delta) {
		Transform t = getParentObject().getTransform();
		
		shape.setX(t.getXPos());
		shape.setY(t.getYPos());
	}

}
