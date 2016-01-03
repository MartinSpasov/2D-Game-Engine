package engine.input;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import engine.Window;

public class Input {
	
	public static int CURSOR_NORMAL = GLFW.GLFW_CURSOR_NORMAL;
	public static int CURSOR_DISABLED = GLFW.GLFW_CURSOR_DISABLED;
	public static int CURSOR_HIDDEN = GLFW.GLFW_CURSOR_HIDDEN;
	
	private ArrayList<KeyListener> keyListeners;
	private ArrayList<MouseButtonListener> mouseButtonListeners;
	private ArrayList<MouseMovementListener> mouseMovementListeners;
	private ArrayList<MouseScrollListener> mouseScrollListeners;
	
	private GLFWKeyCallback keyCallback;
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback mouseMovementCallback;
	private GLFWScrollCallback mouseScrollCallback;
	
	private Window window;
	
	public Input(Window window) {
		this.window = window;
		keyListeners = new ArrayList<KeyListener>();
		mouseButtonListeners = new ArrayList<MouseButtonListener>();
		mouseMovementListeners = new ArrayList<MouseMovementListener>();
		mouseScrollListeners = new ArrayList<MouseScrollListener>();
		
		keyCallback = new GLFWKeyCallback() {

			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				for (KeyListener listener : keyListeners) {
					listener.onKey(window, key, scancode, action, mods);
				}
			}
			
		};
		mouseButtonCallback = new GLFWMouseButtonCallback() {

			@Override
			public void invoke(long window, int button, int action, int mods) {
				for (MouseButtonListener listener : mouseButtonListeners) {
					listener.onMouseButton(window, button, action, mods);
				}
			}
			
		};
		mouseMovementCallback = new GLFWCursorPosCallback() {

			@Override
			public void invoke(long window, double xPos, double yPos) {
				for (MouseMovementListener listener : mouseMovementListeners) {
					listener.onMouseMove(window, xPos, yPos);
				}
			}
			
		};
		mouseScrollCallback = new GLFWScrollCallback() {
			
			@Override
			public void invoke(long window, double xOffset, double yOffset) {
				for (MouseScrollListener listener : mouseScrollListeners) {
					listener.onMouseScroll(window, xOffset, yOffset);
				}
			}
		};
		
		GLFW.glfwSetKeyCallback(window.getId(), keyCallback);
		GLFW.glfwSetMouseButtonCallback(window.getId(), mouseButtonCallback);
		GLFW.glfwSetCursorPosCallback(window.getId(), mouseMovementCallback);
		GLFW.glfwSetScrollCallback(window.getId(), mouseScrollCallback);
	}
	
	public void registerKeyListener(KeyListener listener) {
		keyListeners.add(listener);
	}
	
	// TODO Add way of removing listeners	
	public void registerMouseButtonListener(MouseButtonListener listener) {
		mouseButtonListeners.add(listener);
	}
	
	public void registerMouseMovementListener(MouseMovementListener listener) {
		mouseMovementListeners.add(listener);
	}
	
	public void registerMouseScrollListener(MouseScrollListener listener) {
		mouseScrollListeners.add(listener);
	}
	
	public void setCursorMode(int mode) {
		GLFW.glfwSetInputMode(window.getId(), GLFW.GLFW_CURSOR, mode);
	}

	public void destroy() {
		keyCallback.release();
		mouseButtonCallback.release();
		mouseMovementCallback.release();
		mouseScrollCallback.release();
	}
}
