package engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class Window extends GLFWWindowSizeCallback {
	
	private long id;
	
	private GLFWErrorCallback errorCallback;
	
	private int width;
	private int height;
	
	public Window(String title, int width, int height) {
		this.width = width;
		this.height = height;
		
		errorCallback = GLFWErrorCallback.createPrint(System.err);
		GLFW.glfwSetErrorCallback(errorCallback);
		
		if (GLFW.glfwInit() == -1) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		GLFW.glfwDefaultWindowHints();
		
		// OpenGL debug context
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GL11.GL_TRUE);
		
		id = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		if (id == MemoryUtil.NULL) {
			throw new RuntimeException("Failed to create window");
		}
		
		GLFW.glfwMakeContextCurrent(id);
		//GLContext.createFromCurrent();
		
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(id);
		
		GLFW.glfwSetWindowSizeCallback(id, this);
	}
	
	public long getId() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(id, title);
	}
	
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(id);
	}
	
	public void destroy() {
		errorCallback.release();
		release();
		GLFW.glfwTerminate();
	}

	@Override
	public void invoke(long window, int width, int height) {
		GL11.glViewport(0, 0, width, height);
		this.width = width;
		this.height = height;
	}
}
