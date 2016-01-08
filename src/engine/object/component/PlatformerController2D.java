package engine.object.component;

import org.lwjgl.glfw.GLFW;

import engine.Game;
import engine.input.KeyListener;
import engine.object.GameObject;

public class PlatformerController2D extends ObjectComponent implements KeyListener {

	public static final float SPEED = 3f;

	private boolean left;
	private boolean right;
	
	public PlatformerController2D(GameObject parentObject) {
		super(parentObject);
	}

	@Override
	public void tick(float delta, Game game) {
		if (left) {
			getParentObject().getTransform().translate(-SPEED * delta, 0, 0);
		}
		if (right) {
			getParentObject().getTransform().translate(SPEED * delta, 0, 0);
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
	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		// TODO Auto-generated method stub
		
	}


}
