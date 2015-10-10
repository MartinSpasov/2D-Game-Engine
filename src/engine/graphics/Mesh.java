package engine.graphics;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Mesh {
	
	private int vaoId;
	private int vertBufferId;
	private int uvBufferId;
	
	private int numVertices;

	public Mesh(FloatBuffer vertices, FloatBuffer uvCoords) {
		numVertices = vertices.capacity();
		
		vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);
		
		vertBufferId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertBufferId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		
		uvBufferId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, uvBufferId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, uvCoords, GL15.GL_STATIC_DRAW);
		
		GL20.glEnableVertexAttribArray(1);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public int getNumVertices() {
		return numVertices;
	}
	
	public int getVaoId() {
		return vaoId;
	}
	
	public void destroy() {
		GL15.glDeleteBuffers(vertBufferId);
		GL15.glDeleteBuffers(uvBufferId);
		GL30.glDeleteVertexArrays(vaoId);
	}
}
