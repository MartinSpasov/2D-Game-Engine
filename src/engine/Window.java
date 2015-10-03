package engine;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.system.MemoryUtil;

public class Window {
	
	private long id;
	
	private GLFWErrorCallback errorCallback;
	private GLFWWindowSizeCallback sizeCallback;
	
	public Window(int width, int height, String title) {
		
		errorCallback = Callbacks.errorCallbackPrint(System.err);
		GLFW.glfwSetErrorCallback(errorCallback);
		
		if (GLFW.glfwInit() == -1) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		GLFW.glfwDefaultWindowHints();
		id = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
		if (id == MemoryUtil.NULL) {
			throw new RuntimeException("Failed to create window");
		}
		
		GLFW.glfwMakeContextCurrent(id);
		GLContext.createFromCurrent();
		
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(id);
		
		sizeCallback = new GLFWWindowSizeCallback() {
			
			@Override
			public void invoke(long window, int width, int height) {
				GL11.glViewport(0, 0, width, height);
			}
		};
		GLFW.glfwSetWindowSizeCallback(id, sizeCallback);
	}
	
	public long getId() {
		return id;
	}
	
	public void setTitle(String title) {
		GLFW.glfwSetWindowTitle(id, title);
	}
	
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(id);
	}
	
	public void destroy() {
		errorCallback.release();
		sizeCallback.release();
		GLFW.glfwTerminate();
	}
}
