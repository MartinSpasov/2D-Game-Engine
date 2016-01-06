package engine.graphics;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {

	public static final int NEAREST_NEIGHBOR = GL11.GL_NEAREST;
	public static final int LINEAR = GL11.GL_LINEAR;
	
	private int textureId;
	
	public Texture(ByteBuffer pixels, int width, int height, int filtering) {
		textureId = GL11.glGenTextures();
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MIN_FILTER, filtering);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MAG_FILTER, filtering);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public int getTextureId() {
		return textureId;
	}
	
	public void destroy() {
		GL11.glDeleteTextures(textureId);
	}
}
