package engine.graphics;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class ArrayTexture {
	
	private int textureId;
	
	public ArrayTexture(ByteBuffer pixels, int width, int height, int layers) {
		textureId = GL11.glGenTextures();
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, textureId);
		GL12.glTexImage3D(GL30.GL_TEXTURE_2D_ARRAY, 0, GL11.GL_RGBA, width, height, layers, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY,GL11.GL_TEXTURE_MIN_FILTER,GL11.GL_NEAREST);
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY,GL11.GL_TEXTURE_MAG_FILTER,GL11.GL_NEAREST);
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY,GL11.GL_TEXTURE_WRAP_S,GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY,GL11.GL_TEXTURE_WRAP_T,GL12.GL_CLAMP_TO_EDGE);
		
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, 0);

	}
	
	public int getTextureId() {
		return textureId;
	}
	
	public void destroy() {
		GL11.glDeleteTextures(textureId);
	}
	
}
