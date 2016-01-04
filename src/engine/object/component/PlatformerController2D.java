package engine.object.component;

import org.lwjgl.glfw.GLFW;

import engine.Game;
import engine.input.KeyListener;
import engine.object.GameObject;

public class PlatformerController2D extends ObjectComponent implements KeyListener {

	public static final float SPEED = 3f;
	
	private StateComponent stateComponent;
	
	// Temporary addition to flip the sprite
	private AnimatorComponent animatorComponent;

	private boolean left;
	private boolean right;
	
	public PlatformerController2D(GameObject parentObject, StateComponent stateComponent, AnimatorComponent animatorComponent) {
		super(parentObject);
		this.stateComponent = stateComponent;
		this.animatorComponent = animatorComponent;
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
				stateComponent.changeState("WALKING");
				animatorComponent.getCurrentAnimation().setPaused(false);
				animatorComponent.getCurrentAnimation().reset();
				animatorComponent.setHorizontalFlip(true);
			}
			else if (action == GLFW.GLFW_RELEASE) {
				left = false;
				if (!right) {
					stateComponent.changeState("IDLE");
					animatorComponent.getCurrentAnimation().setPaused(true);
					animatorComponent.getCurrentAnimation().setCurrentFrame(0);
				}
			}
		}
		
		if (key == GLFW.GLFW_KEY_RIGHT) {
			if (action == GLFW.GLFW_PRESS) {
				right = true;
				stateComponent.changeState("WALKING");
				animatorComponent.getCurrentAnimation().setPaused(false);
				animatorComponent.getCurrentAnimation().reset();
				animatorComponent.setHorizontalFlip(false);
			}
			else if (action == GLFW.GLFW_RELEASE) {
				right = false;
				if (!left) {
					stateComponent.changeState("IDLE");
					animatorComponent.getCurrentAnimation().setPaused(true);
					animatorComponent.getCurrentAnimation().setCurrentFrame(0);
				}
			}
		}
	}

}
