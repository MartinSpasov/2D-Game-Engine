package engine;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import engine.console.Logger;
import engine.graphics.Camera;
import engine.graphics.RenderSystem;
import engine.graphics.ui.UserInterface;
import engine.input.Input;
import engine.math.Matrix4f;
import engine.physics.collision.CollisionSystem;
import engine.sound.SoundSystem;

public abstract class Game {

	private RenderSystem renderSystem;
	private Input input;
	private SoundSystem soundSystem;
	private Window window;
	private Scene scene;
	private CollisionSystem collisionSystem;
	private UserInterface ui; // TODO maybe add this in Scene instead

	public static Logger logger = new Logger(System.out); // TODO change later
	
	private int fps;
	
	private boolean shutdown;
	
	public Game(String title, int width, int height) {
		Thread.currentThread().setName("Game Loop");
		logger.log("lwjgl " + Version.getVersion());
		logger.log("Initializing subsystems.");
		window = new Window(title, width, height);
		logger.log(window.getGLFWVersion());
		
		//renderSystem = new RenderSystem(new Camera(Matrix4f.perspective(-1, 1, 1, -1, 1, 1000)));
		renderSystem = new RenderSystem(new Camera(Matrix4f.orthographic(-8, 8, 4.5f, -4.5f, 1, 1000))); // 16 x 9 game units
		logger.log(renderSystem.getOpenGLVersion());
		
		input = new Input(window);
		soundSystem = new SoundSystem();
		
		logger.log("Loading scene.");
		scene = new Scene();
		collisionSystem = new CollisionSystem();
		ui = new UserInterface(this);
		input.registerMouseButtonListener(ui);
		input.registerMouseMovementListener(ui);

	}
	
	public void run() {
		init();
		int frames = 0;
		double counter = 0;
		double delta = 0;
		double currentTime = 0;
		
		logger.log("Entering loop.");
		
		//double time1 = 0;
		
		while (GLFW.glfwWindowShouldClose(window.getId()) == GL11.GL_FALSE && !shutdown) {
			
			
			GLFW.glfwPollEvents();
			
			currentTime = GLFW.glfwGetTime();
			//time1 = GLFW.glfwGetTime();
			
			tick((float)delta);
			

			render();


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
		
		destroy();
	}
	
	public void tick(float delta) {
		scene.tick(delta, this);
		collisionSystem.narrowScan();
	}
	
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		scene.render(renderSystem);
		ui.render(renderSystem);
	}
	
	public void destroy() {
		scene.destroy();
		soundSystem.destroy();
		input.destroy();
		renderSystem.destroy();
		window.destroy();
	}
	
	public RenderSystem getRenderSystem() {
		return renderSystem;
	}
	
	public Input getInput() {
		return input;
	}
	
	public SoundSystem getSoundSystem() {
		return soundSystem;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Window getWindow() {
		return window;
	}
	
	public CollisionSystem getCollisionSystem() {
		return collisionSystem;
	}
	
	public UserInterface getUserInterface() {
		return ui;
	}
	
	public int getFps() {
		return fps;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void shutdown() {
		shutdown = true;
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
	
	public abstract void init();
}
