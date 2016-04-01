package engine.graphics.text;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import engine.graphics.memory.Buffer;

public class Text {

	private Buffer indexBuffer;
	
	private int numChars;
	
	public Text(String text, Font font) {
		numChars = text.length();
		indexBuffer = new Buffer(Buffer.ARRAY_BUFFER);
		indexBuffer.bind();
		
		IntBuffer buff = BufferUtils.createIntBuffer(numChars);
		for (int i = 0; i < numChars; i++) {
			buff.put(font.getCharacterMapping(text.charAt(i)));
		}
		buff.flip();
		
		indexBuffer.bufferData(buff, Buffer.STATIC_DRAW);
		indexBuffer.unbind();
	}
	
	public Buffer getIndexBuffer() {
		return indexBuffer;
	}
	
	public int getNumChars() {
		return numChars;
	}
	
	public void destroy() {
		indexBuffer.destroy();
	}
}
