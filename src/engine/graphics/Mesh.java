package engine.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import engine.graphics.memory.VertexArrayObject;

public class Mesh {
	
	private VertexArrayObject vao;
	
	private int numVertices;

	public Mesh(FloatBuffer vertices, FloatBuffer uvCoords) {
		numVertices = vertices.capacity() / 3; // FIXME At the moment this assumes that 3 values will be provided for each vertex
		
		vao = new VertexArrayObject();
		
		vao.bind();
		
		vao.addArrayBuffer(0, vertices, 3, GL11.GL_FLOAT);
		vao.addArrayBuffer(1, uvCoords, 2, GL11.GL_FLOAT);
		
		vao.unbind();
	}
	
	public int getNumVertices() {
		return numVertices;
	}
	
	public VertexArrayObject getVertexArrayObject() {
		return vao;
	}
	
	public void destroy() {
		vao.destroy();
	}
}
