package engine.test;

import org.lwjgl.glfw.GLFW;

import engine.Game;
import engine.input.KeyListener;
import engine.object.GameObject;
import engine.object.component.ObjectComponent;

public class ControllerComponent extends ObjectComponent implements KeyListener {

	private boolean w;
	private boolean s;
	private boolean a;
	private boolean d;
	private boolean q;
	private boolean e;
	private boolean ctrl;
	private boolean space;
	
	public static final float SPEED = 3f;
	
	public ControllerComponent(GameObject parentObject) {
		super(parentObject);
	}

	@Override
	public void tick(float delta, Game game) {
		if (w) {
			getParentObject().getTransform().translate(0, 0, -SPEED * delta);
		}
		if (s) {
			getParentObject().getTransform().translate(0, 0, SPEED * delta);
		}
		if (a) {
			getParentObject().getTransform().translate(-SPEED * delta, 0, 0);
		}
		if (d) {
			getParentObject().getTransform().translate(SPEED * delta, 0, 0);
		}
		if (q) {
			getParentObject().getTransform().setZRot(getParentObject().getTransform().getZRot() + (SPEED * delta));
		}
		if (e) {
			getParentObject().getTransform().setZRot(getParentObject().getTransform().getZRot() - (SPEED * delta));
		}
		if (ctrl) {
			getParentObject().getTransform().translate(0, -SPEED * delta, 0);
		}
		if (space) {
			getParentObject().getTransform().translate(0, SPEED * delta, 0);
		}
	}
	
	@Override
	public void onKey(long window, int key, int scancode, int action, int mods) {
		if (key == GLFW.GLFW_KEY_W) {
			if (action == GLFW.GLFW_PRESS) {
				w = true;
			}
			else if (action == GLFW.GLFW_RELEASE) {
				w = false;
			}
		}
		
		if (key == GLFW.GLFW_KEY_S) {
			if (action == GLFW.GLFW_PRESS) {
				s = true;
			}
			else if (action == GLFW.GLFW_RELEASE) {
				s = false;
			}
		}
		
		if (key == GLFW.GLFW_KEY_A) {
			if (action == GLFW.GLFW_PRESS) {
				a = true;
			}
			else if (action == GLFW.GLFW_RELEASE) {
				a = false;
			}
		}
		
		if (key == GLFW.GLFW_KEY_D) {
			if (action == GLFW.GLFW_PRESS) {
				d = true;
			}
			else if (action == GLFW.GLFW_RELEASE) {
				d = false;
			}
		}
		if (key == GLFW.GLFW_KEY_Q) {
			if (action == GLFW.GLFW_PRESS) {
				q = true;
			}
			else if (action == GLFW.GLFW_RELEASE) {
				q = false;
			}
		}
		if (key == GLFW.GLFW_KEY_E) {
			if (action == GLFW.GLFW_PRESS) {
				e = true;
			}
			else if (action == GLFW.GLFW_RELEASE) {
				e = false;
			}
		}
		if (key == GLFW.GLFW_KEY_LEFT_CONTROL) {
			if (action == GLFW.GLFW_PRESS) {
				ctrl = true;
			}
			else if (action == GLFW.GLFW_RELEASE) {
				ctrl = false;
			}
		}
		if (key == GLFW.GLFW_KEY_SPACE) {
			if (action == GLFW.GLFW_PRESS) {
				space = true;
			}
			else if (action == GLFW.GLFW_RELEASE) {
				space = false;
			}
		}

	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		// TODO Auto-generated method stub
		
	}

}
