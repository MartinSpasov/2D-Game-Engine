package engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;

import engine.graphics.shader.ShaderProgram;
import engine.graphics.text.Font;
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
	
	//private ShaderProgram defaultShaderProgram;
	private ShaderProgram instanceShaderProgram;
	//private ShaderProgram textShaderProgram;
	
	public RenderEngine(Camera camera) {
		this.camera = camera;
		instanceShaderProgram = new ShaderProgram(Resources.loadText("instance_vert.shader"), Resources.loadText("instance_frag.shader"), new String[]{"diffuseTexture"});
		//textShaderProgram = new ShaderProgram(Resources.loadText("text_vert.shader"), Resources.loadText("text_frag.shader"));
		System.out.println(GL11.glGetString(GL11.GL_VERSION));
		GL11.glClearColor(0.0f, 51/255.0f, 153/255.0f, 1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		//GL11.glEnable(GL11.GL_BLEND);
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		
			
		//GL20.glUseProgram(defaultShaderProgram.getProgramId());
		
		// Texture setup
		//texUniformLoc = GL20.glGetUniformLocation(defaultShaderProgram.getProgramId(), "mainTexture");
		//GL20.glUniform1i(texUniformLoc, 0); // Sets the texture unit for this sampler
	
	}
	
	public void render(MeshBatch batch) {
		GL20.glUseProgram(instanceShaderProgram.getProgramId());
		
		GL30.glBindVertexArray(batch.getMesh().getVaoId());
		
		batch.loadBatch(camera);
		
		// Diffuse Texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, batch.getTexture().getTextureId());
		GL20.glUniform1i(instanceShaderProgram.getUniforms()[0].getLocation(), 0);
		
		//GL20.glEnableVertexAttribArray(0);
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES, 0, batch.getMesh().getNumVertices(), batch.size());
		
	}
	
	public void renderText(String text, Font font, float x, float y) {
		//GL20.glUseProgram(textShaderProgram.getProgramId());
		
	}
	
}
