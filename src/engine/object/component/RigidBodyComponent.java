package engine.object.component;

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

	@Override
	public void tick(float delta) {
		applyForce(new Vector3f(0,G * mass,0));
		
		acceleration = netForce.divide(mass);
		
		netForce.x = 0;
		netForce.y = 0;
		netForce.z = 0;
		
		Vector3f vF = velocity.add(acceleration.multiply(delta));
		Vector3f displacement = velocity.add(vF).divide(2.0f).multiply(delta);
		
		getParentObject().getTransform().translate(displacement.x, displacement.y, displacement.z);
		velocity = vF;
		
		
		// Inertia for flat plane
		int height = 1;
		int width = 1;
		float inertia = mass * ((height * height) + (width * width)) / 12.0f;
		angularAcceleration = netTorque / inertia;
		
		netTorque = 0;
		float avF = (angularVelocity + (angularAcceleration * delta));
		float aDisplacement = ((angularVelocity + avF) / 2.0f) * delta;
		angularVelocity = avF;
		
		getParentObject().getTransform().setZRot(getParentObject().getTransform().getZRot() + aDisplacement);
		//System.out.println(angularAcceleration);
	}
	
	public void applyForce(Vector3f force) {
		netForce = netForce.add(force);
	}
	
	public void applyTorque(float force) {
		netTorque += force;
	}

}
