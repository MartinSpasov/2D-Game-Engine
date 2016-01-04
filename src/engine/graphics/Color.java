package engine.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Color {

	public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color RED = new Color(1.0f, 0.0f, 0.0f, 1.0f);
	public static final Color GREEN = new Color(0.0f, 1.0f, 0.0f, 1.0f);
	public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f, 1.0f);
	
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

	public boolean equals(Color color, double epsilon) {
		double deltaR = Math.abs(color.r - r);
		double deltaG = Math.abs(color.g - g);
		double deltaB = Math.abs(color.b - b);
		double deltaA = Math.abs(color.a - a);
		
		return (deltaR < epsilon && deltaG < epsilon && deltaB < epsilon && deltaA < epsilon);
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(a);
		result = prime * result + Float.floatToIntBits(b);
		result = prime * result + Float.floatToIntBits(g);
		result = prime * result + Float.floatToIntBits(r);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Color) {
			Color color = (Color) o;
			return equals(color, 0.005);
		}
		else {
			return false;
		}
	}
	
	
}
