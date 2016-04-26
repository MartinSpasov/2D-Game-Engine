package engine.input;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import engine.Window;

public class Input {

	public static final int RELEASE = GLFW.GLFW_RELEASE;
	public static final int PRESS = GLFW.GLFW_PRESS;
	public static final int REPEAT = GLFW.GLFW_REPEAT;
	
	public static final int KEY_UNKNOWN = GLFW.GLFW_KEY_UNKNOWN;
	
	public static final int	KEY_SPACE = GLFW.GLFW_KEY_SPACE;
	public static final int	KEY_APOSTROPHE = GLFW.GLFW_KEY_APOSTROPHE;
	public static final int	KEY_COMMA = GLFW.GLFW_KEY_COMMA;
	public static final int	KEY_MINUS = GLFW.GLFW_KEY_MINUS;
	public static final int	KEY_PERIOD = GLFW.GLFW_KEY_PERIOD;
	public static final int	KEY_SLASH = GLFW.GLFW_KEY_SLASH;
	public static final int	KEY_0 = GLFW.GLFW_KEY_0;
	public static final int	KEY_1 = GLFW.GLFW_KEY_1;
	public static final int	KEY_2 = GLFW.GLFW_KEY_2;
	public static final int	KEY_3 = GLFW.GLFW_KEY_3;
	public static final int	KEY_4 = GLFW.GLFW_KEY_4;
	public static final int	KEY_5 = GLFW.GLFW_KEY_5;
	public static final int	KEY_6 = GLFW.GLFW_KEY_6;
	public static final int	KEY_7 = GLFW.GLFW_KEY_7;
	public static final int	KEY_8 = GLFW.GLFW_KEY_8;
	public static final int	KEY_9 = GLFW.GLFW_KEY_9;
	public static final int	KEY_SEMICOLON = GLFW.GLFW_KEY_SEMICOLON;
	public static final int	KEY_EQUAL = GLFW.GLFW_KEY_EQUAL;
	public static final int	KEY_A = GLFW.GLFW_KEY_A;
	public static final int	KEY_B = GLFW.GLFW_KEY_B;
	public static final int	KEY_C = GLFW.GLFW_KEY_C;
	public static final int	KEY_D = GLFW.GLFW_KEY_D;
	public static final int	KEY_E = GLFW.GLFW_KEY_E;
	public static final int	KEY_F = GLFW.GLFW_KEY_F;
	public static final int	KEY_G = GLFW.GLFW_KEY_G;
	public static final int	KEY_H = GLFW.GLFW_KEY_H;
	public static final int	KEY_I = GLFW.GLFW_KEY_I;
	public static final int	KEY_J = GLFW.GLFW_KEY_J;
	public static final int	KEY_K = GLFW.GLFW_KEY_K;
	public static final int	KEY_L = GLFW.GLFW_KEY_L;
	public static final int	KEY_M = GLFW.GLFW_KEY_M;
	public static final int	KEY_N = GLFW.GLFW_KEY_N;
	public static final int	KEY_O = GLFW.GLFW_KEY_O;
	public static final int	KEY_P = GLFW.GLFW_KEY_P;
	public static final int	KEY_Q = GLFW.GLFW_KEY_Q;
	public static final int	KEY_R = GLFW.GLFW_KEY_R;
	public static final int	KEY_S = GLFW.GLFW_KEY_S;
	public static final int	KEY_T = GLFW.GLFW_KEY_T;
	public static final int	KEY_U = GLFW.GLFW_KEY_U;
	public static final int	KEY_V = GLFW.GLFW_KEY_V;
	public static final int	KEY_W= GLFW. GLFW_KEY_W;
	public static final int	KEY_X = GLFW.GLFW_KEY_X;
	public static final int	KEY_Y = GLFW.GLFW_KEY_Y;
	public static final int	KEY_Z = GLFW.GLFW_KEY_Z;
	public static final int	KEY_LEFT_BRACKET = GLFW.GLFW_KEY_LEFT_BRACKET;
	public static final int	KEY_BACKSLASH = GLFW.GLFW_KEY_BACKSLASH;
	public static final int	KEY_RIGHT_BRACKET = GLFW.GLFW_KEY_RIGHT_BRACKET;
	public static final int	KEY_GRAVE_ACCENT = GLFW.GLFW_KEY_GRAVE_ACCENT;
	public static final int	KEY_WORLD_1 = GLFW.GLFW_KEY_WORLD_1;
	public static final int	KEY_WORLD_2 = GLFW.GLFW_KEY_WORLD_2;

	public static final int KEY_ESCAPE = GLFW.GLFW_KEY_ESCAPE;
	public static final int KEY_ENTER = GLFW.GLFW_KEY_ENTER;
	public static final int KEY_TAB = GLFW.GLFW_KEY_TAB;
	public static final int KEY_BACKSPACE = GLFW.GLFW_KEY_BACKSPACE;
	public static final int KEY_INSERT = GLFW.GLFW_KEY_INSERT;
	public static final int KEY_DELETE = GLFW.GLFW_KEY_DELETE;
	public static final int KEY_RIGHT = GLFW.GLFW_KEY_RIGHT;
	public static final int KEY_LEFT = GLFW.GLFW_KEY_LEFT;
	public static final int KEY_DOWN = GLFW.GLFW_KEY_DOWN;
	public static final int KEY_UP = GLFW.GLFW_KEY_UP;
	public static final int KEY_PAGE_UP = GLFW.GLFW_KEY_PAGE_UP;
	public static final int KEY_PAGE_DOWN = GLFW.GLFW_KEY_PAGE_DOWN;
	public static final int KEY_HOME = GLFW.GLFW_KEY_HOME;
	public static final int KEY_END = GLFW.GLFW_KEY_END;
	public static final int KEY_CAPS_LOCK = GLFW.GLFW_KEY_CAPS_LOCK;
	public static final int KEY_SCROLL_LOCK = GLFW.GLFW_KEY_SCROLL_LOCK;
	public static final int KEY_NUM_LOCK = GLFW.GLFW_KEY_NUM_LOCK;
	public static final int KEY_PRINT_SCREEN = GLFW.GLFW_KEY_PRINT_SCREEN;
	public static final int KEY_PAUSE = GLFW.GLFW_KEY_PAUSE;
	public static final int KEY_F1 = GLFW.GLFW_KEY_F1;
	public static final int KEY_F2 = GLFW.GLFW_KEY_F2;
	public static final int KEY_F3 = GLFW.GLFW_KEY_F3;
	public static final int KEY_F4 = GLFW.GLFW_KEY_F4;
	public static final int KEY_F5 = GLFW.GLFW_KEY_F5;
	public static final int KEY_F6 = GLFW.GLFW_KEY_F6;
	public static final int KEY_F7 = GLFW.GLFW_KEY_F7;
	public static final int KEY_F8 = GLFW.GLFW_KEY_F8;
	public static final int KEY_F9 = GLFW.GLFW_KEY_F9;
	public static final int KEY_F10 = GLFW.GLFW_KEY_F10;
	public static final int KEY_F11 = GLFW.GLFW_KEY_F11;
	public static final int KEY_F12 = GLFW.GLFW_KEY_F12;
	public static final int KEY_F13 = GLFW.GLFW_KEY_F13;
	public static final int KEY_F14 = GLFW.GLFW_KEY_F14;
	public static final int KEY_F15 = GLFW.GLFW_KEY_F15;
	public static final int KEY_F16 = GLFW.GLFW_KEY_F16;
	public static final int KEY_F17 = GLFW.GLFW_KEY_F17;
	public static final int KEY_F18 = GLFW.GLFW_KEY_F18;
	public static final int KEY_F19 = GLFW.GLFW_KEY_F19;
	public static final int KEY_F20 = GLFW.GLFW_KEY_F20;
	public static final int KEY_F21 = GLFW.GLFW_KEY_F21;
	public static final int KEY_F22 = GLFW.GLFW_KEY_F22;
	public static final int KEY_F23 = GLFW.GLFW_KEY_F23;
	public static final int KEY_F24 = GLFW.GLFW_KEY_F24;
	public static final int KEY_F25 = GLFW.GLFW_KEY_F25;
	public static final int KEY_KP_0 = GLFW.GLFW_KEY_KP_0;
	public static final int KEY_KP_1 = GLFW.GLFW_KEY_KP_1;
	public static final int KEY_KP_2 = GLFW.GLFW_KEY_KP_2;
	public static final int KEY_KP_3 = GLFW.GLFW_KEY_KP_3;
	public static final int KEY_KP_4 = GLFW.GLFW_KEY_KP_4;
	public static final int KEY_KP_5 = GLFW.GLFW_KEY_KP_5;
	public static final int KEY_KP_6 = GLFW.GLFW_KEY_KP_6;
	public static final int KEY_KP_7 = GLFW.GLFW_KEY_KP_7;
	public static final int KEY_KP_8 = GLFW.GLFW_KEY_KP_8;
	public static final int KEY_KP_9 = GLFW.GLFW_KEY_KP_9;
	public static final int KEY_KP_DECIMAL = GLFW.GLFW_KEY_KP_DECIMAL;
	public static final int KEY_KP_DIVIDE = GLFW.GLFW_KEY_KP_DIVIDE;
	public static final int KEY_KP_MULTIPLY = GLFW.GLFW_KEY_KP_MULTIPLY;
	public static final int KEY_KP_SUBTRACT = GLFW.GLFW_KEY_KP_SUBTRACT;
	public static final int KEY_KP_ADD = GLFW.GLFW_KEY_KP_ADD;
	public static final int KEY_KP_ENTER = GLFW.GLFW_KEY_KP_ENTER;
	public static final int KEY_KP_EQUAL = GLFW.GLFW_KEY_KP_EQUAL;
	public static final int KEY_LEFT_SHIFT = GLFW.GLFW_KEY_LEFT_SHIFT;
	public static final int KEY_LEFT_CONTROL = GLFW.GLFW_KEY_LEFT_CONTROL;
	public static final int KEY_LEFT_ALT = GLFW.GLFW_KEY_LEFT_ALT;
	public static final int KEY_LEFT_SUPER = GLFW.GLFW_KEY_LEFT_SUPER;
	public static final int KEY_RIGHT_SHIFT = GLFW.GLFW_KEY_RIGHT_SHIFT;
	public static final int KEY_RIGHT_CONTROL = GLFW.GLFW_KEY_RIGHT_CONTROL;
	public static final int KEY_RIGHT_ALT = GLFW.GLFW_KEY_RIGHT_ALT;
	public static final int KEY_RIGHT_SUPER = GLFW.GLFW_KEY_RIGHT_SUPER;
	public static final int KEY_MENU = GLFW.GLFW_KEY_MENU;
	public static final int KEY_LAST = GLFW.GLFW_KEY_LAST;

	public static final int MOD_SHIFT = GLFW.GLFW_MOD_SHIFT;
	public static final int MOD_CONTROL = GLFW.GLFW_MOD_CONTROL;
	public static final int MOD_ALT = GLFW.GLFW_MOD_ALT;
	public static final int MOD_SUPER = GLFW.GLFW_MOD_SUPER;

	public static final int MOUSE_BUTTON_1 = GLFW.GLFW_MOUSE_BUTTON_1;
	public static final int MOUSE_BUTTON_2 = GLFW.GLFW_MOUSE_BUTTON_2;
	public static final int MOUSE_BUTTON_3 = GLFW.GLFW_MOUSE_BUTTON_3;
	public static final int MOUSE_BUTTON_4 = GLFW.GLFW_MOUSE_BUTTON_4;
	public static final int MOUSE_BUTTON_5 = GLFW.GLFW_MOUSE_BUTTON_5;
	public static final int MOUSE_BUTTON_6 = GLFW.GLFW_MOUSE_BUTTON_6;
	public static final int MOUSE_BUTTON_7 = GLFW.GLFW_MOUSE_BUTTON_7;
	public static final int MOUSE_BUTTON_8 = GLFW.GLFW_MOUSE_BUTTON_8;
	public static final int MOUSE_BUTTON_LAST = GLFW.GLFW_MOUSE_BUTTON_LAST;
	public static final int MOUSE_BUTTON_LEFT = GLFW.GLFW_MOUSE_BUTTON_LEFT;
	public static final int MOUSE_BUTTON_RIGHT = GLFW.GLFW_MOUSE_BUTTON_RIGHT;
	public static final int MOUSE_BUTTON_MIDDLE = GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

	public static final int JOYSTICK_1 = GLFW.GLFW_JOYSTICK_1;
	public static final int JOYSTICK_2 = GLFW.GLFW_JOYSTICK_2;
	public static final int JOYSTICK_3 = GLFW.GLFW_JOYSTICK_3;
	public static final int JOYSTICK_4 = GLFW.GLFW_JOYSTICK_4;
	public static final int JOYSTICK_5 = GLFW.GLFW_JOYSTICK_5;
	public static final int JOYSTICK_6 = GLFW.GLFW_JOYSTICK_6;
	public static final int JOYSTICK_7 = GLFW.GLFW_JOYSTICK_7;
	public static final int JOYSTICK_8 = GLFW.GLFW_JOYSTICK_8;
	public static final int JOYSTICK_9 = GLFW.GLFW_JOYSTICK_9;
	public static final int JOYSTICK_10 = GLFW.GLFW_JOYSTICK_10;
	public static final int JOYSTICK_11 = GLFW.GLFW_JOYSTICK_11;
	public static final int JOYSTICK_12 = GLFW.GLFW_JOYSTICK_12;
	public static final int JOYSTICK_13 = GLFW.GLFW_JOYSTICK_13;
	public static final int JOYSTICK_14 = GLFW.GLFW_JOYSTICK_14;
	public static final int JOYSTICK_15 = GLFW.GLFW_JOYSTICK_15;
	public static final int JOYSTICK_16 = GLFW.GLFW_JOYSTICK_16;
	public static final int JOYSTICK_LAST = GLFW.GLFW_JOYSTICK_LAST;

	public static final int CURSOR = GLFW.GLFW_CURSOR;
	public static final int STICKY_KEYS = GLFW.GLFW_STICKY_KEYS;
	public static final int STICKY_MOUSE_BUTTONS = GLFW.GLFW_STICKY_MOUSE_BUTTONS;

	public static final int CURSOR_NORMAL = GLFW.GLFW_CURSOR_NORMAL;
	public static final int CURSOR_HIDDEN = GLFW.GLFW_CURSOR_HIDDEN;
	public static final int CURSOR_DISABLED = GLFW.GLFW_CURSOR_DISABLED;

	public static final int ARROW_CURSOR = GLFW.GLFW_ARROW_CURSOR;
	public static final int IBEAM_CURSOR = GLFW.GLFW_IBEAM_CURSOR;
	public static final int CROSSHAIR_CURSOR = GLFW.GLFW_CROSSHAIR_CURSOR;
	public static final int HAND_CURSOR = GLFW.GLFW_HAND_CURSOR;
	public static final int HRESIZE_CURSOR = GLFW.GLFW_HRESIZE_CURSOR;
	public static final int VRESIZE_CURSOR = GLFW.GLFW_VRESIZE_CURSOR;
	
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
