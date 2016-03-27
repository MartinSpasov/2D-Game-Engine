package engine.object.component;

import org.lwjgl.glfw.GLFW;

import engine.Game;
import engine.input.KeyListener;
import engine.object.GameObject;

public class PlatformerController2D extends ObjectComponent implements KeyListener {

	public static final float SPEED = 3f;

	private boolean left;
	private boolean right;
	private boolean leftShift;
	private boolean rightShift;
	
	public PlatformerController2D(GameObject parentObject) {
		super(parentObject);
	}

	@Override
	public void tick(float delta, Game game) {
		if (left) {
			getParentObject().getTransform().translateLocal(-SPEED * delta, 0, 0);
		}
		if (right) {
			getParentObject().getTransform().translateLocal(SPEED * delta, 0, 0);
		}
		if (leftShift) {
			getParentObject().broadcastMessage("APPLYTORQUE", 50f);
		}
		if (rightShift) {
			getParentObject().broadcastMessage("APPLYTORQUE", -50f);
		}
	}

	@Override
	public void onKey(long window, int key, int scancode, int action, int mods) {
		if (key == GLFW.GLFW_KEY_LEFT) {
			if (action == GLFW.GLFW_PRESS) {
				left = true;
				getParentObject().broadcastMessage("STATECHANGE", "WALKING");
				getParentObject().broadcastMessage("PAUSEANIM", false);
				getParentObject().broadcastMessage("RESETANIM", null);
				getParentObject().broadcastMessage("FLIPSPRITE", true);
			}
			else if (action == GLFW.GLFW_RELEASE) {
				left = false;
				if (!right) {
					getParentObject().broadcastMessage("STATECHANGE", "IDLE");
					getParentObject().broadcastMessage("PAUSEANIM", true);
					getParentObject().broadcastMessage("SETCURRENTFRAME", 0);
				}
			}
		}
		
		if (key == GLFW.GLFW_KEY_RIGHT) {
			if (action == GLFW.GLFW_PRESS) {
				right = true;
				getParentObject().broadcastMessage("STATECHANGE", "WALKING");
				getParentObject().broadcastMessage("PAUSEANIM", false);
				getParentObject().broadcastMessage("RESETANIM", null);
				getParentObject().broadcastMessage("FLIPSPRITE", false);
			}
			else if (action == GLFW.GLFW_RELEASE) {
				right = false;
				if (!left) {
					getParentObject().broadcastMessage("STATECHANGE", "IDLE");
					getParentObject().broadcastMessage("PAUSEANIM", true);
					getParentObject().broadcastMessage("SETCURRENTFRAME", 0);
				}
			}
		}
		if (key == GLFW.GLFW_KEY_LEFT_SHIFT) {
			if (action == GLFW.GLFW_PRESS) {
				leftShift = true;
			}
			else if (action == GLFW.GLFW_RELEASE) {
				leftShift = false;
			}
		}
		if (key == GLFW.GLFW_KEY_RIGHT_SHIFT) {
			if (action == GLFW.GLFW_PRESS) {
				rightShift = true;
			}
			else if (action == GLFW.GLFW_RELEASE) {
				rightShift = false;
			}
		}
	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		if (message.equals("COLLISION")) {
			left = false;
			right = false;
		}
	}


}
