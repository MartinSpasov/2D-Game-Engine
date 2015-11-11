package engine.object.component;

import org.lwjgl.glfw.GLFW;

import engine.input.KeyListener;
import engine.object.GameObject;

public class PlatformerController2D extends ObjectComponent implements KeyListener {

	public static final float SPEED = 3f;
	
	private StateComponent stateComponent;

	private boolean left;
	private boolean right;
	
	public PlatformerController2D(GameObject parentObject, StateComponent stateComponent) {
		super(parentObject);
		this.stateComponent = stateComponent;
	}

	@Override
	public void tick(float delta) {
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
				stateComponent.changeState("MOVING");
			}
			else if (action == GLFW.GLFW_RELEASE) {
				left = false;
				stateComponent.changeState("IDLE");
			}
		}
		
		if (key == GLFW.GLFW_KEY_RIGHT) {
			if (action == GLFW.GLFW_PRESS) {
				right = true;
				stateComponent.changeState("MOVING");
			}
			else if (action == GLFW.GLFW_RELEASE) {
				right = false;
				stateComponent.changeState("IDLE");
			}
		}
	}

}
