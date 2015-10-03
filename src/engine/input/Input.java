package engine.input;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import engine.Window;

public class Input {
	
	private ArrayList<KeyListener> keyListeners;
	
	private GLFWKeyCallback keyCallback;
	
	public Input(Window window) {
		keyListeners = new ArrayList<KeyListener>();
		
		keyCallback = new GLFWKeyCallback() {

			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				for (int i = 0; i < keyListeners.size(); i++) {
					keyListeners.get(i).onKey(window, key, scancode, action, mods);
				}
			}
			
		};
		
		GLFW.glfwSetKeyCallback(window.getId(), keyCallback);
	}
	
	public int addKeyListener(KeyListener listener) {
		keyListeners.add(listener);
		return keyListeners.size() - 1;
	}
	
	public void removeKeyListener(int id) {
		keyListeners.remove(id);
	}
}
