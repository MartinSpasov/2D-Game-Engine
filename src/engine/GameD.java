package engine;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import engine.console.Console;
import engine.console.Logger;
import engine.graphics.ArrayTexture;
import engine.graphics.Camera;
import engine.graphics.RenderSystem;
import engine.graphics.Texture;
import engine.graphics.animation.Animation;
import engine.graphics.text.Font;
import engine.input.Input;
import engine.input.KeyListener;
import engine.math.Matrix4f;
import engine.object.GameObject;
import engine.object.component.AnimatorComponent;
import engine.object.component.PlatformerController2D;
import engine.object.component.SpriteComponent;
import engine.object.component.StateComponent;
import engine.resource.Resources;
import engine.sound.SoundSystem;

public class Game implements KeyListener {

	private Window window;
	private RenderSystem renderSystem;
	private Input input;
	private Console console;
	private SoundSystem soundSystem;
	public static Logger logger; // FIXME change this back to private
	//private CollisionSystem collisionSystem;
	
	private Camera testCam;
	
	private Scene scene;
	
	public static final float NEAR_PLANE = 1f;
	public static final float FAR_PLANE = 1000f;
	
	private boolean w;
	private boolean s;
	private boolean a;
	private boolean d;
	private boolean q;
	private boolean e;
	
	private int fps;
	
	public static final float SPEED = 3f;
	
	public static final int MAX_INSTANCE_COUNT = 10000;
	
	private Font font;
	
	private Texture texture;
	private ArrayTexture texture2;
	
	public static boolean flip;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
		game.destroy();
	}
	
	public Game() {
		logger = new Logger(System.out);
		logger.log("Initializing subsystems.");
		window = new Window("Test", 500,500);
		
		//testCam = new Camera(Matrix4f.orthographic(-1, 1, 1, -1, NEAR_PLANE, FAR_PLANE));
		testCam = new Camera(Matrix4f.perspective(-1, 1, 1, -1, NEAR_PLANE, FAR_PLANE));
		testCam.getTransform().translate(0, 0, 2);
		renderSystem = new RenderSystem(testCam);
		soundSystem = new SoundSystem();
		logger.log(renderSystem.getOpenGLVersion());
		input = new Input(window);
		input.registerKeyListener(this);
		console = new Console();
		
		logger.log("Loading scene.");
		scene = new Scene();
		//collisionSystem = new CollisionSystem();
		
		
		// Texture
		texture = Resources.loadTexture("megaman.png");
		
		for (int i = 0; i < 1000; i++) {
			GameObject obj = new GameObject();
			SpriteComponent comp = new SpriteComponent(obj, texture);
			obj.addComponent(comp);
			obj.getTransform().translate(0, 0, -i);
			scene.addObject(obj);
		}
		
		GameObject obj = new GameObject();
		obj.getTransform().translate(1,0,-1);
		texture2 = Resources.loadArrayTexture("megaman_sheet.png", 1, 3);
		Animation animWalk = new Animation(new int[]{0,1,2}, new float[]{0.2f,0.2f,0.2f});
		Animation animIdle = new Animation(new int[]{0}, new float[]{0.0f});
		
		AnimatorComponent animComp = new AnimatorComponent(obj, "IDLE", animIdle, texture2);
		animComp.addAnimation("WALKING", animWalk);
		
		StateComponent states = new StateComponent(obj, "IDLE");
		states.registerListener(animComp);
		
		PlatformerController2D control = new PlatformerController2D(obj, states);
		input.registerKeyListener(control);
		
		obj.addComponent(animComp);
		obj.addComponent(states);
		obj.addComponent(control);
		
		scene.addObject(obj);
		
		font = new Font(Resources.loadArrayTexture("samplefont.png", 14, 16), 0);
		font.addCharacterMapping(' ', 0);
		font.addCharacterMapping('!', 1);
		font.addCharacterMapping('"', 2);
		font.addCharacterMapping('#', 3);
		font.addCharacterMapping('$', 4);
		font.addCharacterMapping('%', 5);
		font.addCharacterMapping('&', 6);
		font.addCharacterMapping('\'', 7);
		font.addCharacterMapping('(', 8);
		font.addCharacterMapping(')', 9);
		font.addCharacterMapping('*', 10);
		font.addCharacterMapping('+', 11);
		font.addCharacterMapping(',', 12);
		font.addCharacterMapping('-', 13);
		font.addCharacterMapping('.', 14);
		font.addCharacterMapping('/', 15);
		font.addCharacterMapping('0', 16);
		font.addCharacterMapping('1', 17);
		font.addCharacterMapping('2', 18);
		font.addCharacterMapping('3', 19);
		font.addCharacterMapping('4', 20);
		font.addCharacterMapping('5', 21);
		font.addCharacterMapping('6', 22);
		font.addCharacterMapping('7', 23);
		font.addCharacterMapping('8', 24);
		font.addCharacterMapping('9', 25);
		font.addCharacterMapping(':', 26);
		font.addCharacterMapping(';', 27);
		font.addCharacterMapping('<', 28);
		font.addCharacterMapping('=', 29);
		font.addCharacterMapping('>', 30);
		font.addCharacterMapping('?', 31);
		font.addCharacterMapping('@', 32);
		font.addCharacterMapping('A', 33);
		font.addCharacterMapping('B', 34);
		font.addCharacterMapping('C', 35);
		font.addCharacterMapping('D', 36);
		font.addCharacterMapping('E', 37);
		font.addCharacterMapping('F', 38);
		font.addCharacterMapping('G', 39);
		font.addCharacterMapping('H', 40);
		font.addCharacterMapping('I', 41);
		font.addCharacterMapping('J', 42);
		font.addCharacterMapping('K', 43);
		font.addCharacterMapping('L', 44);
		font.addCharacterMapping('M', 45);
		font.addCharacterMapping('N', 46);
		font.addCharacterMapping('O', 47);
		font.addCharacterMapping('P', 48);
		font.addCharacterMapping('Q', 49);
		font.addCharacterMapping('R', 50);
		font.addCharacterMapping('S', 51);
		font.addCharacterMapping('T', 52);
		font.addCharacterMapping('U', 53);
		font.addCharacterMapping('V', 54);
		font.addCharacterMapping('W', 55);
		font.addCharacterMapping('X', 56);
		font.addCharacterMapping('Y', 57);
		font.addCharacterMapping('Z', 58);
		font.addCharacterMapping('[', 59);
		font.addCharacterMapping('\\', 60);
		font.addCharacterMapping(']', 61);
		font.addCharacterMapping('^', 62);
		font.addCharacterMapping('_', 63);
		font.addCharacterMapping('`', 64);
		font.addCharacterMapping('a', 65);
		font.addCharacterMapping('b', 66);
		font.addCharacterMapping('c', 67);
		font.addCharacterMapping('d', 68);
		font.addCharacterMapping('e', 69);
		font.addCharacterMapping('f', 70);
		font.addCharacterMapping('g', 71);
		font.addCharacterMapping('h', 72);
		font.addCharacterMapping('i', 73);
		font.addCharacterMapping('j', 74);
		font.addCharacterMapping('k', 75);
		font.addCharacterMapping('l', 76);
		font.addCharacterMapping('m', 77);
		font.addCharacterMapping('n', 78);
		font.addCharacterMapping('o', 79);
		font.addCharacterMapping('p', 80);
		font.addCharacterMapping('q', 81);
		font.addCharacterMapping('r', 82);
		font.addCharacterMapping('s', 83);
		font.addCharacterMapping('t', 84);
		font.addCharacterMapping('u', 85);
		font.addCharacterMapping('v', 86);
		font.addCharacterMapping('w', 87);
		font.addCharacterMapping('x', 88);
		font.addCharacterMapping('y', 89);
		font.addCharacterMapping('z', 90);
		font.addCharacterMapping('{', 91);
		font.addCharacterMapping('|', 92);
		font.addCharacterMapping('}', 93);
		font.addCharacterMapping('~', 94);
	}
	
	public void run() {
		int frames = 0;
		double counter = 0;
		double delta = 0;
		double currentTime = 0;

		logger.log("Entering loop.");
		
		double time1 = 0;
		
		while (GLFW.glfwWindowShouldClose(window.getId()) == GL11.GL_FALSE) {
			currentTime = GLFW.glfwGetTime();
			GLFW.glfwPollEvents();
			
			time1 = GLFW.glfwGetTime();
			
			tick((float)delta);
			
			render();
			
			if (GLFW.glfwGetTime() - time1 > 0.01666) {
				logger.log("ENGINE IS LAGGING!");
			}
			
			window.swapBuffers();
			delta = GLFW.glfwGetTime() - currentTime;
			
			counter += delta; 
			if (counter >= 1) {
				counter = 0;
				fps = frames;
				frames = 0;
			} 
			else {
				frames++;
			}
		}
	}
	
	public void tick(float delta) {
		if (w) {
			testCam.getTransform().translate(0, 0, -SPEED * delta);
		}
		if (s) {
			testCam.getTransform().translate(0, 0, SPEED * delta);
		}
		if (a) {
			testCam.getTransform().translate(-SPEED * delta, 0, 0);
		}
		if (d) {
			testCam.getTransform().translate(SPEED * delta, 0, 0);
		}
		if (q) {
			testCam.getTransform().setZRot(testCam.getTransform().getZRot() + (SPEED * delta));
		}
		if (e) {
			testCam.getTransform().setZRot(testCam.getTransform().getZRot() - (SPEED * delta));
		}
		scene.tick(delta, this);
	}
	
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		renderSystem.renderAll();
		renderSystem.renderText("FPS: " + fps, font, -0.5f, -0.5f);
		renderSystem.checkError(logger);
	}
	
	public void destroy() {
		console.destroy();
		renderSystem.destroy();
		soundSystem.destroy();
		font.destroy();
		input.destroy();
		window.destroy();
		texture.destroy();
		texture2.destroy();
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

	}
	
	public RenderSystem getRenderSystem() {
		return renderSystem;
	}
	
	// TODO remove later
	public static FloatBuffer toBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		for (int i = 0; i < data.length; i++) {
			buffer.put(data[i]);
		}
		buffer.flip();
		
		return buffer;
	}
}