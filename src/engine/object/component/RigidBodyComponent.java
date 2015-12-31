package engine.object.component;

import engine.Game;
import engine.math.Vector3f;
import engine.object.GameObject;

public class RigidBodyComponent extends ObjectComponent {

	//public static final float G = -9.807f;
	public static final float G = 0;
	
	private float mass;
	
	private Vector3f netForce;
	private Vector3f acceleration;
	private Vector3f velocity;
	
	private float netTorque;
	private float angularAcceleration;
	private float angularVelocity;
	
	public RigidBodyComponent(GameObject parentObject, float mass) {
		super(parentObject);
		this.mass = mass;
		netForce = new Vector3f();
		acceleration = new Vector3f();
		velocity = new Vector3f();
		//netTorque = new Vector3f();
		//angularAcceleration = new Vector3f();
		//angularVelocity = new Vector3f();
	}

	public void applyForce(Vector3f force) {
		netForce = netForce.add(force);
	}
	
	public void applyTorque(float force) {
		netTorque += force;
	}

	@Override
	public void tick(float delta, Game game) {
		// TODO Auto-generated method stub
		
	}

}
