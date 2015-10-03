package engine.graphics;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL15;

import engine.math.Matrix4f;
import engine.object.GameObject;

public class MeshBatch {

	private Mesh mesh;
	private Texture texture;
	
	private ArrayList<Matrix4f> modelMatrices;
	
	public MeshBatch(Mesh mesh, Texture texture) {
		this.mesh = mesh;
		this.texture = texture;
		modelMatrices = new ArrayList<Matrix4f>();
	}
	
	public MeshBatch(GameObject[] objects, Mesh mesh, Texture texture) {
		this(mesh, texture);
		
		for (int i = 0; i < objects.length; i++) {
			addToBatch(objects[i]);
		}
	}
	
	public void addToBatch(GameObject object) {
		// TODO Handle object with wrong mesh
		
		modelMatrices.add(object.getTransform().toMatrix());
		
	}
	
	public void loadBatch(Camera camera) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, mesh.getMvpMatrixBufferId());
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
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public int size() {
		return modelMatrices.size();
	}
}
