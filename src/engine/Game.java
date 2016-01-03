package engine;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import engine.console.Logger;
import engine.graphics.Camera;
import engine.graphics.RenderSystem;
import engine.input.Input;
import engine.math.Matrix4f;
import engine.sound.SoundSystem;

public abstract class Game {

	private RenderSystem renderSystem;
	private Input input;
	private SoundSystem soundSystem;
	private Window window;
	private Scene scene;
	public static Logger logger = new Logger(System.out); // TODO change later
	
	private int fps;
	
	public Game(String title, int width, int height) {
		logger.log("Initializing subsystems.");
		window = new Window(title, width, height);
		
		renderSystem = new RenderSystem(new Camera(Matrix4f.perspective(-1, 1, 1, -1, 1, 1000)));
		logger.log(renderSystem.getOpenGLVersion());
		
		input = new Input(window);
		soundSystem = new SoundSystem();
		
		logger.log("Loading scene.");
		scene = new Scene();
	}
	
	public void run() {
		init();
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
		
		destroy();
	}
	
	public void tick(float delta) {
		scene.tick(delta, this);
	}
	
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		renderSystem.renderAll();
		renderSystem.checkError(logger);
	}
	
	public void destroy() {
		renderSystem.destroy();
		soundSystem.destroy();
		input.destroy();
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
	
	public int getFps() {
		return fps;
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
