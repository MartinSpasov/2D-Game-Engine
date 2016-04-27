package engine.object.component;

import engine.Game;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.object.GameObject;
import engine.physics.collision.Manifold;

public class RigidBodyComponent extends ObjectComponent {
	
	private float mass;
	
	private Vector3f netForce;
	private Vector3f acceleration;
	private Vector3f velocity;
	
	private Vector3f momentum;
	
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
	
	public void applyImpulse(Vector3f impulse) {
		velocity = velocity.add(impulse.divide(mass));
		momentum = velocity.multiply(mass);
	}
	
	public void applyTorque(float force) {
		netTorque += force;
	}

	@Override
	public void tick(float delta, Game game) {
		
		// Apply gravity
		applyForce(new Vector3f(0, -game.getScene().getGravity() * mass, 0));
		
		acceleration = netForce.divide(mass);
	
		netForce.x = 0;
		netForce.y = 0;
		netForce.z = 0;
		
		Vector3f vF = velocity.add(acceleration.multiply(delta));
		Vector3f displacement = velocity.add(vF).divide(2.0f).multiply(delta);

		getParentObject().getTransform().translate(displacement.x, displacement.y, displacement.z);
		velocity = vF;
		momentum = velocity.multiply(mass);

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
		
		//getParentObject().broadcastMessage("DIRECTION", new Vector2f(displacement.x, displacement.y));
		
	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		if (message.equals("APPLYFORCE") && param instanceof Vector3f) {
			applyForce((Vector3f)param);
		}
		else if (message.equals("APPLYTORQUE") && param instanceof Float) {
			applyTorque((float)param);
		}
		else if (message.equals("APPLYIMPULSE") && param instanceof Vector3f) {
			applyImpulse((Vector3f)param);
		}
		else if (message.equals("COLLISION") && param instanceof Manifold) {
			
			Vector2f direction = ((Manifold)param).getDirection();
			
			if (direction.equals(Vector2f.Y_AXIS)) {
				acceleration.x = 0;
				acceleration.y = 0;
				acceleration.z = 0;
				velocity.x = 0;
				velocity.y = 0;
				velocity.z = 0;
				getParentObject().broadcastMessage("GROUNDED", true);
			}

		}
	}

}
