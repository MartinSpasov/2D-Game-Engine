package engine.graphics;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;

public class InstancedMesh extends Mesh {
	
	private int mvpMatrixBufferId;
	
	private int maxInstances;

	public InstancedMesh(FloatBuffer vertices, FloatBuffer uvCoords, int maxInstances) {
		super(vertices, uvCoords);
		this.maxInstances = maxInstances;
		
		GL30.glBindVertexArray(getVaoId());
		
		mvpMatrixBufferId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, mvpMatrixBufferId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, maxInstances * 4 * 16, GL15.GL_DYNAMIC_DRAW);
		
		for (int i = 0; i < 4; i++) {
			GL20.glVertexAttribPointer(2 + i, 4, GL11.GL_FLOAT, false, 64, 16 * i);
			GL20.glEnableVertexAttribArray(2 + i);
			GL33.glVertexAttribDivisor(2 + i, 1);
		}
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public int getMvpMatrixBufferId() {
		return mvpMatrixBufferId;
	}
	
	public int getMaxInstances() {
		return maxInstances;
	}
	
	@Override
	public void destroy() {
		GL15.glDeleteBuffers(mvpMatrixBufferId);
		super.destroy();
	}

}
