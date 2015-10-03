package engine.graphics;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;

import engine.math.Matrix4f;
import engine.object.GameObject;
import engine.resource.Resources;

public class RenderEngine {
	
	public static final float[] PLANE_VERTS = new float[]{-0.5f,0.5f,0.0f, -0.5f,-0.5f,0.0f, 0.5f,0.5f,0.f, 0.5f,0.5f,0.0f, -0.5f,-0.5f,0.0f, 0.5f,-0.5f, 0.0f};
	public static final float[] PLANE_COLOR = new float[]{
			1.0f,0.0f,0.0f,
			0.0f,1.0f,0.0f,
			0.0f,0.0f,1.0f,
			1.0f,0.0f,0.0f,
			0.0f,1.0f,0.0f,
			0.0f,0.0f,1.0f};
	public static final float[] PLANE_UV = new float[]{
			0.0f,0.0f,
			0.0f,1.0f,
			1.0f,0.0f,
			1.0f,0.0f,
			0.0f,1.0f,
			1.0f,1.0f};
	private Camera camera;
	private int vertShaderId;
	private int fragShaderId;
	private int defaultProgramId;
	
	private int texUniformLoc;
	private int modelMatrixUniformLoc;
	
	private ArrayList<Matrix4f> matrices;
	
	public RenderEngine(Camera camera) {
		this.camera = camera;
		matrices = new ArrayList<Matrix4f>();
		
		System.out.println(GL11.glGetString(GL11.GL_VERSION));
		GL11.glClearColor(0.0f, 51/255.0f, 153/255.0f, 1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		//GL11.glEnable(GL11.GL_BLEND);
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		
		
		// TODO handle shader compilation error
		vertShaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertShaderId, Resources.loadText("vert.shader"));
		GL20.glCompileShader(vertShaderId);
		int status = GL20.glGetShaderi(vertShaderId, GL20.GL_COMPILE_STATUS);
		System.out.println(status);
		System.out.println(GL11.GL_FALSE);
		System.out.println(GL20.glGetShaderInfoLog(vertShaderId));
		
		fragShaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragShaderId, Resources.loadText("frag.shader"));
		GL20.glCompileShader(fragShaderId);
		System.out.println(GL20.glGetShaderInfoLog(fragShaderId));
		
		defaultProgramId = GL20.glCreateProgram();
		GL20.glAttachShader(defaultProgramId, vertShaderId);
		GL20.glAttachShader(defaultProgramId, fragShaderId);
		GL20.glLinkProgram(defaultProgramId);
		
		GL20.glUseProgram(defaultProgramId);
		
		// Texture setup
		texUniformLoc = GL20.glGetUniformLocation(defaultProgramId, "mainTexture");
		GL20.glUniform1i(texUniformLoc, 0); // Sets the texture unit for this sampler
	
		modelMatrixUniformLoc = GL20.glGetUniformLocation(defaultProgramId, "modelMatrix");
	}
	
	public void renderObject(GameObject object) {
		GL20.glUseProgram(defaultProgramId);
		
		Matrix4f finalM = camera.getProjectionMatrix().multiply(camera.getWorldMatrix().multiply(object.getTransform().toMatrix()));
		GL20.glUniformMatrix4fv(modelMatrixUniformLoc, false, finalM.toBuffer());
		
		GL30.glBindVertexArray(object.getMesh().getVaoId());
		
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, object.getTexture().getId());
		//GL20.glUniform1i(texUniformLoc, 0);
		//GL20.glEnableVertexAttribArray(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, object.getMesh().getNumVertices());
	}
	
	public void render(Mesh mesh, Texture texture) {
		GL20.glUseProgram(defaultProgramId);
		
		GL30.glBindVertexArray(mesh.getVaoId());
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, mesh.modelMatrixufferId);
		FloatBuffer buffer = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY).asFloatBuffer();
		//buffer.clear();
		//System.out.println(buffer.capacity());
		for (int i = 0; i < matrices.size(); i++) {
			buffer.put(matrices.get(i).m00);
			buffer.put(matrices.get(i).m10);
			buffer.put(matrices.get(i).m20);
			buffer.put(matrices.get(i).m30);
			buffer.put(matrices.get(i).m01);
			buffer.put(matrices.get(i).m11);
			buffer.put(matrices.get(i).m21);
			buffer.put(matrices.get(i).m31);
			buffer.put(matrices.get(i).m02);
			buffer.put(matrices.get(i).m12);
			buffer.put(matrices.get(i).m22);
			buffer.put(matrices.get(i).m32);
			buffer.put(matrices.get(i).m03);
			buffer.put(matrices.get(i).m13);
			buffer.put(matrices.get(i).m23);
			buffer.put(matrices.get(i).m33);
		}
		//matrices.clear();
		GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
		//GL20.glUniform1i(texUniformLoc, 0);
		//GL20.glEnableVertexAttribArray(0);
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES, 0, mesh.getNumVertices(), matrices.size());
		matrices.clear();
	}
	
	public void addToRender(GameObject object) {
		Matrix4f finalM = camera.getProjectionMatrix().multiply(camera.getWorldMatrix().multiply(object.getTransform().toMatrix()));
		matrices.add(finalM);
	}
	
	public void renderText(String text) {
		
	}
	
}
