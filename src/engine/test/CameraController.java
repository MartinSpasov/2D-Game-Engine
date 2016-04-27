package engine.test;

import org.lwjgl.glfw.GLFW;

import engine.Game;
import engine.input.Input;
import engine.input.KeyListener;
import engine.object.GameObject;
import engine.object.component.ObjectComponent;

public class CameraController extends ObjectComponent implements KeyListener {

	private boolean w;
	private boolean s;
	private boolean a;
	private boolean d;
	private boolean q;
	private boolean e;
	private boolean ctrl;
	private boolean shift;
	
	public static final float SPEED = 3f;
	
	public CameraController(GameObject parentObject) {
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
		if (shift) {
			getParentObject().getTransform().translate(0, SPEED * delta, 0);
		}
	}
	
	@Override
	public void onKey(long window, int key, int scancode, int action, int mods) {
		if (key == Input.KEY_W) {
			if (action == Input.PRESS) {
				w = true;
			}
			else if (action == Input.RELEASE) {
				w = false;
			}
		}
		
		if (key == Input.KEY_S) {
			if (action == Input.PRESS) {
				s = true;
			}
			else if (action == Input.RELEASE) {
				s = false;
			}
		}
		
		if (key == Input.KEY_A) {
			if (action == Input.PRESS) {
				a = true;
			}
			else if (action == Input.RELEASE) {
				a = false;
			}
		}
		
		if (key == Input.KEY_D) {
			if (action == Input.PRESS) {
				d = true;
			}
			else if (action == Input.RELEASE) {
				d = false;
			}
		}
		if (key == Input.KEY_Q) {
			if (action == Input.PRESS) {
				q = true;
			}
			else if (action == Input.RELEASE) {
				q = false;
			}
		}
		if (key == Input.KEY_E) {
			if (action == Input.PRESS) {
				e = true;
			}
			else if (action == Input.RELEASE) {
				e = false;
			}
		}
		if (key == Input.KEY_LEFT_CONTROL) {
			if (action == GLFW.GLFW_PRESS) {
				ctrl = true;
			}
			else if (action == Input.RELEASE) {
				ctrl = false;
			}
		}
		if (key == Input.KEY_LEFT_SHIFT) {
			if (action == GLFW.GLFW_PRESS) {
				shift = true;
			}
			else if (action == Input.RELEASE) {
				shift = false;
			}
		}

	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		// TODO Auto-generated method stub
		
	}

}
