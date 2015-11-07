package engine.math;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Matrix4f {
	
	public float m00;
	public float m01;
	public float m02;
	public float m03;
	public float m10;
	public float m11;
	public float m12;
	public float m13;
	public float m20;
	public float m21;
	public float m22;
	public float m23;
	public float m30;
	public float m31;
	public float m32;
	public float m33;
	
	public Matrix4f() {
		m00 = 1.0f;
		m11 = 1.0f;
		m22 = 1.0f;
		m33 = 1.0f;
	}
	
	public Matrix4f(Matrix4f m) {
		m00 = m.m00;
		m01 = m.m01;
		m02 = m.m02;
		m03 = m.m03;
		m10 = m.m10;
		m11 = m.m11;
		m12 = m.m12;
		m13 = m.m13;
		m20 = m.m20;
		m21 = m.m21;
		m22 = m.m22;
		m23 = m.m23;
		m30 = m.m30;
		m31 = m.m31;
		m32 = m.m32;
		m33 = m.m33;
	}
	
	public Matrix4f(float[] entries) {
		// TODO handle out of bounds
		m00 = entries[0];
		m01 = entries[1];
		m02 = entries[2];
		m03 = entries[3];
		m10 = entries[4];
		m11 = entries[5];
		m12 = entries[6];
		m13 = entries[7];
		m20 = entries[8];
		m21 = entries[9];
		m22 = entries[10];
		m23 = entries[11];
		m30 = entries[12];
		m31 = entries[13];
		m32 = entries[14];
		m33 = entries[15];
	}
	
	public Matrix4f multiply(Matrix4f m) {
		Matrix4f matrix = new Matrix4f(new float[]{
				m00 * m.m00 + m01 * m.m10 + m02 * m.m20 + m03 * m.m30,
				m00 * m.m01 + m01 * m.m11 + m02 * m.m21 + m03 * m.m31, 
				m00 * m.m02 + m01 * m.m12 + m02 * m.m22 + m03 * m.m32, 
				m00 * m.m03 + m01 * m.m13 + m02 * m.m23 + m03 * m.m33, // first row
				m10 * m.m00 + m11 * m.m10 + m12 * m.m20 + m13 * m.m30,
				m10 * m.m01 + m11 * m.m11 + m12 * m.m21 + m13 * m.m31, 
				m10 * m.m02 + m11 * m.m12 + m12 * m.m22 + m13 * m.m32, 
				m10 * m.m03 + m11 * m.m13 + m12 * m.m23 + m13 * m.m33, // second row
				m20 * m.m00 + m21 * m.m10 + m22 * m.m20 + m23 * m.m30,
				m20 * m.m01 + m21 * m.m11 + m22 * m.m21 + m23 * m.m31, 
				m20 * m.m02 + m21 * m.m12 + m22 * m.m22 + m23 * m.m32, 
				m20 * m.m03 + m21 * m.m13 + m22 * m.m23 + m23 * m.m33, // third row
				m30 * m.m00 + m31 * m.m10 + m32 * m.m20 + m33 * m.m30,
				m30 * m.m01 + m31 * m.m11 + m32 * m.m21 + m33 * m.m31, 
				m30 * m.m02 + m31 * m.m12 + m32 * m.m22 + m33 * m.m32, 
				m30 * m.m03 + m31 * m.m13 + m32 * m.m23 + m33 * m.m33});
		return matrix;
	}
	
	public Matrix4f multiply(float value) {
		Matrix4f matrix = new Matrix4f(new float[]{
				m00 * value, m01 * value, m02 * value, m03 * value,
				m10 * value, m11 * value, m12 * value, m13 * value,
				m20 * value, m21 * value, m22 * value, m23 * value,
				m30 * value, m31 * value, m32 * value, m33 * value,
		});
		
		return matrix;
	}
	
	public Matrix4f transpose() {
		Matrix4f matrix = new Matrix4f(new float[]{
				m00, m10, m20, m30,
				m01, m11, m21, m31,
				m02, m12, m22, m32,
				m03, m13, m23, m33});
		return matrix;
	}
	
	public float det() {
		float d1 = (m11 * m22 * m33) + (m12 * m23 * m31) + (m13 * m21 * m32) - (m11 * m23 * m32) - (m12 * m21 * m33) - (m13 * m22 * m31);
		float d2 = (m10 * m22 * m33) + (m12 * m23 * m30) + (m13 * m20 * m32) - (m13 * m22 * m30) - (m10 * m23 * m32) - (m12 * m20 * m33);
		float d3 = (m10 * m21 * m33) + (m11 * m23 * m30) + (m13 * m20 * m31) - (m13 * m21 * m30) - (m10 * m23 * m31) - (m11 * m20 * m33);
		float d4 = (m10 * m21 * m32) + (m11 * m22 * m30) + (m12 * m20 * m31) - (m12 * m21 * m30) - (m10 * m22 * m31) - (m11 * m20 * m32);
		
		return (m00 * d1) - (m01 * d2) + (m02 * d3) - (m03 * d4);
	}
	
	public Matrix4f inverse() {
		
		float c1 = (m11 * m22 * m33) + (m12 * m23 * m31) + (m13 * m21 * m32) - (m11 * m23 * m32) - (m12 * m21 * m33) - (m13 * m22 * m31);
		float c2 = -1 * ((m10 * m22 * m33) + (m12 * m23 * m30) + (m13 * m20 * m32) - (m13 * m22 * m30) - (m10 * m23 * m32) - (m12 * m20 * m33));
		float c3 = (m10 * m21 * m33) + (m11 * m23 * m30) + (m13 * m20 * m31) - (m13 * m21 * m30) - (m10 * m23 * m31) - (m11 * m20 * m33);
		float c4 = -1 * ((m10 * m21 * m32) + (m11 * m22 * m30) + (m12 * m20 * m31) - (m12 * m21 * m30) - (m10 * m22 * m31) - (m11 * m20 * m32));
		float c5 = -1 * ((m01 * m22 * m33) + (m02 * m23 * m31) + (m03 * m21 * m32) - (m03 * m22 * m31) - (m01 * m23 * m32) - (m02 * m21 * m33));
		float c6 = (m00 * m22 * m33) + (m02 * m23 * m30) + (m03 * m20 * m32) - (m03 * m22 * m30) - (m00 * m23 * m32) - (m02 * m20 * m33);
		float c7 = -1 * ((m00 * m21 * m33) + (m01 * m23 * m30) + (m03 * m20 * m31) - (m03 * m21 * m30) - (m00 * m23 * m31) - (m01 * m20 * m33));
		float c8 = (m00 * m21 * m32) + (m01 * m22 * m30) + (m02 * m20 * m31) - (m02 * m21 * m30) - (m00 * m22 * m31) - (m01 * m20 * m32);
		float c9 = (m01 * m12 * m33) + (m02 * m13 * m31) + (m03 * m11 * m32) - (m03 * m12 * m31) - (m01 * m13 * m32) - (m02 * m11 * m33);
		float c10 = -1 * ((m00 * m12 * m33) + (m02 * m13 * m30) + (m03 * m10 * m32) - (m03 * m12 * m30) - (m00 * m13 * m32) - (m02 * m10 * m33));
		float c11 = (m00 * m11 * m33) + (m01 * m13 * m30) + (m03 * m10 * m31) - (m03 * m11 * m30) - (m00 * m13 * m31) - (m01 * m10 * m33);
		float c12 = -1 * ((m00 * m11 * m32) + (m01 * m12 * m30) + (m02 * m10 * m31) - (m02 * m11 * m30) - (m00 * m12 * m31) - (m01 * m10 * m32));
		float c13 = -1 * ((m01 * m12 * m23) + (m02 * m13 * m21) + (m03 * m11 * m22) - (m03 * m12 * m21) - (m01 * m13 * m22) - (m02 * m11 * m23));
		float c14 = (m00 * m12 * m23) + (m02 * m13 * m20) + (m03 * m10 * m22) - (m03 * m12 * m20) - (m00 * m13 * m22) - (m02 * m10 * m23);
		float c15 = -1 * ((m00 * m11 * m23) + (m01 * m13 * m20) + (m03 * m10 * m21) - (m03 * m11 * m20) - (m00 * m13 * m21) - (m01 * m10 * m23));
		float c16 = (m00 * m11 * m22) + (m01 * m12 * m20) + (m02 * m10 * m21) - (m02 * m11 * m20) - (m00 * m12 * m21) - (m01 * m10 * m22);
		
		float det = (m00 * c1) - (m01 * c2 * -1) + (m02 * c3) - (m03 * c4 * -1);
		
		// TODO CHECK IF INVERTIBLE
		
		Matrix4f matrix = new Matrix4f(new float[] {
				c1 / det, c5 / det, c9 / det, c13 / det,
				c2 / det, c6 / det, c10 / det, c14 / det,
				c3 / det, c7 / det, c11 / det, c15 / det,
				c4 / det , c8 / det, c12 / det, c16 / det});
		
		return matrix;
	}
	
	public FloatBuffer toBuffer() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		buffer.put(m00);
		buffer.put(m10);
		buffer.put(m20);
		buffer.put(m30);
		buffer.put(m01);
		buffer.put(m11);
		buffer.put(m21);
		buffer.put(m31);
		buffer.put(m02);
		buffer.put(m12);
		buffer.put(m22);
		buffer.put(m32);
		buffer.put(m03);
		buffer.put(m13);
		buffer.put(m23);
		buffer.put(m33);
		buffer.flip();
		return buffer;
	}
	
	public float[] toArray() {
		float[] array = new float[16];
		array[0] = m00;
		array[1] = m01;
		array[2] = m02;
		array[3] = m03;
		array[4] = m10;
		array[5] = m11;
		array[6] = m12;
		array[7] = m13;
		array[8] = m20;
		array[9] = m21;
		array[10] = m22;
		array[11] = m23;
		array[12] = m30;
		array[13] = m31;
		array[14] = m32;
		array[15] = m33;
		
		return array;
	}
	
	@Override
	public String toString() {
		String string = "[[" +
	m00 + "\t" + m01 + "\t" + m02 + "\t" + m03 + "]\n" +
	"[" + m10 + "\t" + m11 + "\t" + m12 + "\t" + m13 + "]\n" +
	"[" + m20 + "\t" + m21 + "\t" + m22 + "\t" + m23 + "]\n" +
	"[" + m30 + "\t" + m31 + "\t" + m32 + "\t" + m33 + "]]";
		
		return string;
	}
	
	public boolean equals(Matrix4f matrix, double epsilon) {
		float[] array1 = toArray();
		float[] array2 = matrix.toArray();
		
		for (int i = 0; i < array1.length; i++) {
			if (Math.abs(array1[i] - array2[i]) > epsilon) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Matrix4f) {
			Matrix4f matrix = (Matrix4f) o;
			return equals(matrix, 0.0005);
		}
		else {
			return false;
		}
	}

	public static Matrix4f perspective(float l, float r, float t, float b, float n, float f) {
		// FIXME Something is not correct with this matrix
		return new Matrix4f(new float[]{
				(2 * n) / (r - l),0,(r + l) / (r - l),0,
				0,(2 * n) / (t - b),(t + b) / (t - b),0,
				0,0,(-1 * (f + n))/(f - n),(-2 * f * n)/(f - n),
				0,0,-1,0
		});
	}
	
	public static Matrix4f orthographic(float l, float r, float t, float b, float n, float f) {
		
		return new Matrix4f(new float[]{
				1 / r, 0, 0, 0,
				0, 1 / t, 0, 0,
				0, 0, -2/(f - n),-1 * ((f + n)/(f - n)),
				0,0,0,1	
		});
	}
	
	public static Matrix4f translation(float dx, float dy, float dz) {
		return new Matrix4f(new float[]{
				1,0,0,dx,
				0,1,0,dy,
				0,0,1,dz,
				0,0,0,1});
	}
	
	public static Matrix4f scale(float sx, float sy, float sz) {
		return new Matrix4f(new float[]{
				sx,0,0,0,
				0,sy,0,0,
				0,0,sz,0,
				0,0,0,1});
	}
	
	public static Matrix4f rotationX(float angle) {
		return new Matrix4f(new float[] {
				1,0,0,0,
				0,(float) Math.cos(angle),(float)(-1 * Math.sin(angle)),0,
				0,(float) Math.sin(angle),(float) Math.cos(angle),0,
				0,0,0,1});
	}
	
	public static Matrix4f rotationY(float angle) {
		return new Matrix4f(new float[] {
				(float) Math.cos(angle),0,(float) (-1 * Math.sin(angle)),0,
				0,1,0,0,
				(float) Math.sin(angle),0,(float) Math.cos(angle),0,
				0,0,0,1});
	}
	
	public static Matrix4f rotationZ(float angle) {
		return new Matrix4f(new float[] {
				(float) Math.cos(angle),(float) (-1 * Math.sin(angle)),0,0,
				(float) Math.sin(angle),(float) Math.cos(angle),0,0,
				0,0,1,0,
				0,0,0,1});
	}
}