package engine.graphics;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;

import engine.Game;

public class Texture {
	
	public static final int TEXTURE_1D = GL11.GL_TEXTURE_1D;
	public static final int TEXTURE_2D = GL11.GL_TEXTURE_2D;
	public static final int TEXTURE_3D = GL12.GL_TEXTURE_3D;
	public static final int TEXTURE_1D_ARRAY = GL30.GL_TEXTURE_1D_ARRAY;
	public static final int TEXTURE_2D_ARRAY = GL30.GL_TEXTURE_2D_ARRAY;
	public static final int TEXTURE_RECTANGLE = GL31.GL_TEXTURE_RECTANGLE;
	public static final int TEXTURE_CUBE_MAP = GL13.GL_TEXTURE_CUBE_MAP;
	public static final int TEXTURE_CUBE_MAP_ARRAY = GL40.GL_TEXTURE_CUBE_MAP_ARRAY;
	public static final int TEXTURE_BUFFER = GL31.GL_TEXTURE_BUFFER;
	public static final int TEXTURE_2D_MULTISAMPLE = GL32.GL_TEXTURE_2D_MULTISAMPLE;
	public static final int TEXTURE_2D_MULTISAMPLE_ARRAY = GL32.GL_TEXTURE_2D_MULTISAMPLE_ARRAY;

	public static final int NEAREST_NEIGHBOR = GL11.GL_NEAREST;
	public static final int LINEAR = GL11.GL_LINEAR;
	
	private int handle;
	private int target;
	private int unit;
	
	public Texture(int target, int unit) {
		handle = GL11.glGenTextures();
		this.target = target;
		this.unit = unit;
	}
	
	public void bufferTexture2D(ByteBuffer data, int width, int height, int filtering) {
		if (target == TEXTURE_2D) {
			GL11.glTexImage2D(target, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
			
			GL11.glTexParameteri(target,GL11.GL_TEXTURE_MIN_FILTER, filtering);
			GL11.glTexParameteri(target,GL11.GL_TEXTURE_MAG_FILTER, filtering);
		}
		else {
			Game.logger.log("[ERROR] Texture target not supported by engine.");
		}
	}
	
	public void bufferTexture3D(ByteBuffer data, int width, int height, int layers, int filtering) {
		if (target == TEXTURE_2D_ARRAY) {
			GL12.glTexImage3D(target, 0, GL11.GL_RGBA, width, height, layers, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
			
			GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, filtering);
			GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, filtering);
			GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		}
		else {
			Game.logger.log("[ERROR] Texture target not supported by engine.");
		}
	}
	
	public int getTextureId() {
		return handle;
	}
	
	public void bind() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		GL11.glBindTexture(target, handle);
	}
	
	public void unbind() {
		GL11.glBindTexture(target, 0);
	}
	
	public void destroy() {
		GL11.glDeleteTextures(handle);
	}
}
