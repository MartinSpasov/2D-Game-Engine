package engine.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Color {

	public float r;
	public float g;
	public float b;
	public float a;
	
	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public FloatBuffer toBuffer() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
		buffer.put(r);
		buffer.put(g);
		buffer.put(b);
		buffer.put(a);
		buffer.flip();
		return buffer;
	}
}
