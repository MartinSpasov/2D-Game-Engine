package engine.graphics;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;

import engine.graphics.memory.Buffer;
import engine.math.Matrix4f;

public class MeshBatch {

	private Mesh mesh;
	private Texture texture;
	
	private int maxSize;
	
	//private int mvpMatrixBufferId;
	private Buffer mvpMatrixBuffer;
	
	private ArrayList<Matrix4f> modelMatrices;
	
	public MeshBatch(Mesh mesh, Texture texture, int maxSize) {
		this.mesh = mesh;
		this.texture = texture;
		this.maxSize = maxSize;
		
		modelMatrices = new ArrayList<Matrix4f>();
		
		mesh.getVertexArrayObject().bind();
		//GL30.glBindVertexArray(mesh.getVaoId());
		
		mvpMatrixBuffer = new Buffer(Buffer.ARRAY_BUFFER);
		
		//GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, mvpMatrixBufferId);
		mvpMatrixBuffer.bind();
		mvpMatrixBuffer.bufferData(maxSize * 4 * 16, Buffer.DYNAMIC_DRAW);
		//GL15.glBufferData(GL15.GL_ARRAY_BUFFER, maxSize * 4 * 16, GL15.GL_DYNAMIC_DRAW);
		
		for (int i = 0; i < 4; i++) {
			GL20.glVertexAttribPointer(2 + i, 4, GL11.GL_FLOAT, false, 64, 16 * i);
			GL20.glEnableVertexAttribArray(2 + i);
			GL33.glVertexAttribDivisor(2 + i, 1);
		}
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public void addToBatch(Matrix4f mat) {
		// TODO Handle object with wrong mesh
		
		modelMatrices.add(mat);
		
	}
	
	public void loadBatch(Camera camera) {
		//GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, mvpMatrixBufferId);
		mvpMatrixBuffer.bind();
		FloatBuffer buffer = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY).asFloatBuffer();
		Matrix4f pvMatrix = camera.getProjectionMatrix().multiply(camera.getWorldMatrix());
		
		for (int i = 0; i < modelMatrices.size(); i++) {
			Matrix4f mvpMatrix = pvMatrix.multiply(modelMatrices.get(i)); 
			buffer.put(mvpMatrix.m00);
			buffer.put(mvpMatrix.m10);
			buffer.put(mvpMatrix.m20);
			buffer.put(mvpMatrix.m30);
			buffer.put(mvpMatrix.m01);
			buffer.put(mvpMatrix.m11);
			buffer.put(mvpMatrix.m21);
			buffer.put(mvpMatrix.m31);
			buffer.put(mvpMatrix.m02);
			buffer.put(mvpMatrix.m12);
			buffer.put(mvpMatrix.m22);
			buffer.put(mvpMatrix.m32);
			buffer.put(mvpMatrix.m03);
			buffer.put(mvpMatrix.m13);
			buffer.put(mvpMatrix.m23);
			buffer.put(mvpMatrix.m33);
		}
		
		GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
		//GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		mvpMatrixBuffer.unbind();
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public int getMaxSize() {
		return maxSize;
	}
	
	public int size() {
		return modelMatrices.size();
	}
	
	public void clear() {
		modelMatrices.clear();
	}
	
	public void destroy() {
		//GL15.glDeleteBuffers(mvpMatrixBufferId);
		mvpMatrixBuffer.destroy();
	}
}
