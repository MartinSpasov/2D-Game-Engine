package engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder.Format;
import engine.console.Console;
import engine.graphics.Camera;
import engine.graphics.InstancedMesh;
import engine.graphics.RenderEngine;
import engine.graphics.Texture;
import engine.input.Input;
import engine.input.KeyListener;
import engine.math.Matrix4f;
import engine.object.GameObject;
import engine.object.Transform;

public class Game implements KeyListener {

	private Window window;
	private RenderEngine renderer;
	private Input input;
	private Console console;
	
	private GameObject testObject;
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
	
	public static final float SPEED = 3f;
	
	public static final int MAX_INSTANCE_COUNT = 10000;
	//private double counter;
	
	public static InstancedMesh mesh;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
		game.destroy();
	}
	
	public Game() {
		window = new Window(500,500, "Test");
		
		float l = -1;
		float r = 1;
		float t = 1;
		float b = -1;
		float n = NEAR_PLANE;
		float f = FAR_PLANE;
		testCam = new Camera(new Matrix4f(new float[]{
			(2 * n) / (r - l),0,(r + l) / (r - l),0,
			0,(2 * n) / (t - b),(t + b) / (t - b),0,
			0,0,(-1 * (f + n))/(f - n),(-2 * f * n)/(f - n),
			0,0,-1,0	
		}));
		
		renderer = new RenderEngine(testCam);
		input = new Input(window);
		input.addKeyListener(this);
		console = new Console();
		scene = new Scene(renderer);
		
		
		FloatBuffer verts = BufferUtils.createFloatBuffer(RenderEngine.PLANE_VERTS.length);
		for (int i = 0; i < RenderEngine.PLANE_VERTS.length; i++) {
			verts.put(RenderEngine.PLANE_VERTS[i]);
		}
		verts.flip();
		
//		FloatBuffer colors = BufferUtils.createFloatBuffer(RenderEngine.PLANE_COLOR.length);
//		for (int i = 0; i < RenderEngine.PLANE_COLOR.length; i++) {
//			colors.put(RenderEngine.PLANE_COLOR[i]);
//		}
//		colors.flip();
		
		FloatBuffer uvs = BufferUtils.createFloatBuffer(RenderEngine.PLANE_UV.length);
		for (int i = 0; i < RenderEngine.PLANE_UV.length; i++) {
			uvs.put(RenderEngine.PLANE_UV[i]);
		}
		uvs.flip();
		
		mesh = new InstancedMesh(verts, uvs, MAX_INSTANCE_COUNT);
		
		
		// Texture
		de.matthiasmann.twl.utils.PNGDecoder decoder = null;
		ByteBuffer data = null;
		try {
			decoder = new de.matthiasmann.twl.utils.PNGDecoder(new FileInputStream(new File("megaman.png")));
			data = BufferUtils.createByteBuffer(decoder.getHeight() * decoder.getWidth() * 4);
			decoder.decode(data, decoder.getWidth()* 4, Format.RGBA);
			data.flip();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Texture texture = new Texture(data, decoder.getWidth(), decoder.getHeight());
		
		testObject = new GameObject(new Transform(), mesh, texture);
		testObject.getTransform().setZPos(-1);
		scene.addObject(testObject);
		//PNGDecoder decode = new PNGDecoder("megaman.png");
		Random rand = new Random();
		
		for (int i = 2; i <= 10000; i++) {
			GameObject object = new GameObject(new Transform(), mesh, texture);
			object.getTransform().setZPos(-1 * i);
			object.getTransform().setXPos((3 * rand.nextFloat()) - 1.0f);
			object.getTransform().setYPos((3 * rand.nextFloat()) - 1.0f);
			scene.addObject(object);
		}
	}
	
	public void run() {
		int frames = 0;
		double counter = 0;
		double delta = 0;
		double currentTime = 0;
		
		while (GLFW.glfwWindowShouldClose(window.getId()) == GL11.GL_FALSE) {
			currentTime = GLFW.glfwGetTime();
			GLFW.glfwPollEvents();
			tick((float)delta);
			render();
			window.swapBuffers();
			delta = GLFW.glfwGetTime() - currentTime;
			
			counter += delta; 
			if (counter >= 1) {
				counter = 0;
				window.setTitle("fps: " + frames);
				frames = 0;
			} 
			else {
				frames++;
			}
		}
	}
	
	public void tick(float delta) {
		if (w) {
			//testObject.getTransform().translate(0, SPEED * delta, 0);
			testCam.getTransform().translate(0, 0, -SPEED * delta);
		}
		if (s) {
			//testObject.getTransform().translate(0, -SPEED * delta, 0);
			testCam.getTransform().translate(0, 0, SPEED * delta);
		}
		if (a) {
			//testObject.getTransform().translate(-SPEED * delta, 0, 0);
			testCam.getTransform().translate(-SPEED * delta, 0, 0);
		}
		if (d) {
			//testObject.getTransform().translate(SPEED * delta, 0, 0);
			testCam.getTransform().translate(SPEED * delta, 0, 0);
		}
		if (q) {
			//testObject.getTransform().setZRot(testObject.getTransform().getZRot() + (SPEED * delta));
			testCam.getTransform().setZRot(testCam.getTransform().getZRot() + (SPEED * delta));
		}
		if (e) {
			//testObject.getTransform().setZRot(testObject.getTransform().getZRot() - (SPEED * delta));
			testCam.getTransform().setZRot(testCam.getTransform().getZRot() - (SPEED * delta));
		}
		scene.tick();
	}
	
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		scene.render();
	}
	
	public void destroy() {
		window.destroy();
		console.destroy();
		testObject.destroy();
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
}
