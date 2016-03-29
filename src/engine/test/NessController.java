package engine.test;

import org.lwjgl.glfw.GLFW;

import engine.Game;
import engine.input.KeyListener;
import engine.object.GameObject;
import engine.object.component.ObjectComponent;

public class NessController extends ObjectComponent implements KeyListener {

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	public static final float SPEED = 3f;
	
	public NessController(GameObject parentObject) {
		super(parentObject);
	}

	@Override
	public void tick(float delta, Game game) {
		if (up) {
			getParentObject().getTransform().translate(0, SPEED * delta, 0);
		}
		if (down) {
			getParentObject().getTransform().translate(0, -SPEED * delta, 0);
		}
		if (left) {
			getParentObject().getTransform().translate(-SPEED * delta, 0, 0);
		}
		if (right) {
			getParentObject().getTransform().translate(SPEED * delta, 0, 0);
		}
	}
	
	@Override
	public void onKey(long window, int key, int scancode, int action, int mods) {
		if (key == GLFW.GLFW_KEY_UP) {
			if (action == GLFW.GLFW_PRESS) {
				up = true;
				getParentObject().broadcastMessage("STATECHANGE", "WALK_UP");
				getParentObject().broadcastMessage("PAUSEANIM", false);
				getParentObject().broadcastMessage("RESETANIM", null);
			}
			else if (action == GLFW.GLFW_RELEASE) {
				up = false;
				getParentObject().broadcastMessage("PAUSEANIM", true);
			}
		}
		
		if (key == GLFW.GLFW_KEY_DOWN) {
			if (action == GLFW.GLFW_PRESS) {
				down = true;
				getParentObject().broadcastMessage("STATECHANGE", "WALK_DOWN");
				getParentObject().broadcastMessage("PAUSEANIM", false);
				getParentObject().broadcastMessage("RESETANIM", null);
			}
			else if (action == GLFW.GLFW_RELEASE) {
				down = false;
				getParentObject().broadcastMessage("PAUSEANIM", true);
			}
		}
		
		if (key == GLFW.GLFW_KEY_LEFT) {
			if (action == GLFW.GLFW_PRESS) {
				left = true;
				getParentObject().broadcastMessage("STATECHANGE", "WALK_LEFT");
				getParentObject().broadcastMessage("PAUSEANIM", false);
				getParentObject().broadcastMessage("RESETANIM", null);
			}
			else if (action == GLFW.GLFW_RELEASE) {
				left = false;
				getParentObject().broadcastMessage("PAUSEANIM", true);
			}
		}
		
		if (key == GLFW.GLFW_KEY_RIGHT) {
			if (action == GLFW.GLFW_PRESS) {
				right = true;
				getParentObject().broadcastMessage("STATECHANGE", "WALK_RIGHT");
				getParentObject().broadcastMessage("PAUSEANIM", false);
				getParentObject().broadcastMessage("RESETANIM", null);
			}
			else if (action == GLFW.GLFW_RELEASE) {
				right = false;
				getParentObject().broadcastMessage("PAUSEANIM", true);
			}
		}
	}

	@Override
	public <T> void receiveMessage(String message, T param) {

	}

}
