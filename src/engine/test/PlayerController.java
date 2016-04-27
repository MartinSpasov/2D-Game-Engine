package engine.test;

import engine.Game;
import engine.input.Input;
import engine.input.KeyListener;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.object.GameObject;
import engine.object.component.ObjectComponent;

public class PlayerController extends ObjectComponent implements KeyListener {

	private boolean left;
	private boolean right;
	
	public static final float SPEED = 3f;
	
	private boolean grounded;
	
	
	public PlayerController(GameObject parentObject) {
		super(parentObject);
	}

	@Override
	public void tick(float delta, Game game) {
		// TODO add method to 0 vectors
		Vector2f direction = new Vector2f();
		if (left) {
			getParentObject().getTransform().translate(-SPEED * delta, 0, 0);
			direction.x -= SPEED * delta;
		}
		if (right) {
			getParentObject().getTransform().translate(SPEED * delta, 0, 0);
			direction.x += SPEED * delta;
		}
		//getParentObject().broadcastMessage("DIRECTION", direction);
	}
	
	

	@Override
	public void onKey(long window, int key, int scancode, int action, int mods) {
		if (key == Input.KEY_LEFT) {
			if (action == Input.PRESS) {
				left = true;
				getParentObject().broadcastMessage("STATECHANGE", "WALK_LEFT");
				getParentObject().broadcastMessage("PAUSEANIM", false);
				getParentObject().broadcastMessage("RESETANIM", null);
			}
			else if (action == Input.RELEASE) {
				left = false;
				getParentObject().broadcastMessage("PAUSEANIM", true);
			}
		}
		
		if (key == Input.KEY_RIGHT) {
			if (action == Input.PRESS) {
				right = true;
				getParentObject().broadcastMessage("STATECHANGE", "WALK_RIGHT");
				getParentObject().broadcastMessage("PAUSEANIM", false);
				getParentObject().broadcastMessage("RESETANIM", null);
			}
			else if (action == Input.RELEASE) {
				right = false;
				getParentObject().broadcastMessage("PAUSEANIM", true);
			}
		}
		
		if (key == Input.KEY_SPACE) {
			if (action == Input.PRESS) {
				//getParentObject().broadcastMessage("GROUNDED", false);
				if (grounded) {
					grounded = false;
					getParentObject().broadcastMessage("APPLYIMPULSE", new Vector3f(0,350,0));
				}
			}
		}
	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		if (message.equals("GROUNDED") && param instanceof Boolean) {
			grounded = (boolean)param;
		}
	}

}
