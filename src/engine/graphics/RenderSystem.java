package engine.graphics;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GLCapabilities;

import engine.Game;
import engine.console.Logger;
import engine.graphics.shader.ShaderProgram;
import engine.graphics.text.Font;
import engine.math.Matrix4f;
import engine.object.GameObject;
import engine.object.Transform;
import engine.object.component.AnimatorComponent;
import engine.object.component.SpriteComponent;
import engine.resource.Resources;

public class RenderSystem {
	
	public static final float[] PLANE_VERTS = new float[]{-0.5f,0.5f,0.0f, -0.5f,-0.5f,0.0f, 0.5f,0.5f,0.f, 0.5f,0.5f,0.0f, -0.5f,-0.5f,0.0f, 0.5f,-0.5f, 0.0f};
	public static final float[] PLANE_UV = new float[]{0.0f,0.0f,0.0f,1.0f,1.0f,0.0f,1.0f,0.0f,0.0f,1.0f,1.0f,1.0f};
	public static final int MAX_BATCH_SIZE = 10000;
	
	private GLCapabilities capabilities;
	
	private Camera camera;
	
	private ShaderProgram instanceShaderProgram;
	private ShaderProgram textShaderProgram;
	private ShaderProgram spriteShaderProgram;
	private ShaderProgram animSpriteShaderProgram;
	
	private Mesh flatPlane;
	
	private HashMap<Texture, MeshBatch> batches;
	private ArrayList<AnimatorComponent> animators;
	
	public RenderSystem(Camera camera) {
		this.camera = camera;
		capabilities = GL.createCapabilities();
		instanceShaderProgram = new ShaderProgram(Resources.loadText("instance_vert.shader"), Resources.loadText("instance_frag.shader"));
		textShaderProgram = new ShaderProgram(Resources.loadText("text_vert.shader"), Resources.loadText("text_frag.shader"));
		spriteShaderProgram = new ShaderProgram(Resources.loadText("default_vert.shader"), Resources.loadText("sprite_frag.shader"));
		animSpriteShaderProgram = new ShaderProgram(Resources.loadText("default_vert.shader"), Resources.loadText("animsprite_frag.shader"));
		
		flatPlane = new Mesh(Game.toBuffer(PLANE_VERTS), Game.toBuffer(PLANE_UV));
		
		animators = new ArrayList<AnimatorComponent>();
		batches = new HashMap<Texture, MeshBatch>();
		
		GL11.glClearColor(0.0f, 51/255.0f, 153/255.0f, 1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		//GL11.glEnable(GL11.GL_BLEND);
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		
			
		//GL20.glUseProgram(defaultShaderProgram.getProgramId());
	}

	public void render(MeshBatch batch) {
		GL20.glUseProgram(instanceShaderProgram.getProgramId());
		
		GL30.glBindVertexArray(batch.getMesh().getVaoId());
		
		
		batch.loadBatch(camera);
		
		// Diffuse Texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, batch.getTexture().getTextureId());
		GL20.glUniform1i(instanceShaderProgram.getUniform("diffuseTexture").getLocation(), 0);
		
		//GL20.glEnableVertexAttribArray(0);
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES, 0, batch.getMesh().getNumVertices(), batch.size());
		
	}

	public void render(GameObject object, int frame, ArrayTexture tex, boolean horizontalFlip) {
		GL20.glUseProgram(animSpriteShaderProgram.getProgramId());
		
		GL30.glBindVertexArray(flatPlane.getVaoId());
		
		Matrix4f finalMatrix = camera.getProjectionMatrix().multiply(camera.getWorldMatrix().multiply(object.getTransform().toMatrix()));
		
		// Diffuse Texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, tex.getTextureId());

		GL20.glUniformMatrix4fv(animSpriteShaderProgram.getUniform("mvpMatrix").getLocation(), false, finalMatrix.toBuffer());
		GL20.glUniform1i(animSpriteShaderProgram.getUniform("diffuseTextureAtlas").getLocation(), 0);
		GL20.glUniform1i(animSpriteShaderProgram.getUniform("currentFrame").getLocation(), frame);
		GL20.glUniform1i(animSpriteShaderProgram.getUniform("horizontalFlip").getLocation(), (horizontalFlip) ? GL11.GL_TRUE:GL11.GL_FALSE);


		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
	}

	public void render(SpriteComponent component) {
		GL20.glUseProgram(spriteShaderProgram.getProgramId());
		GL30.glBindVertexArray(flatPlane.getVaoId());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, component.getTexture().getTextureId());
		
		Matrix4f finalMatrix = camera.getProjectionMatrix().multiply(camera.getWorldMatrix().multiply(component.getParentObject().getTransform().toMatrix()));
		
		GL20.glUniformMatrix4fv(spriteShaderProgram.getUniform("mvpMatrix").getLocation(), false, finalMatrix.toBuffer());
		GL20.glUniform1i(spriteShaderProgram.getUniform("horizontalFlip").getLocation(), (component.isHorizontallyFlipped()) ? GL11.GL_TRUE:GL11.GL_FALSE);
		GL20.glUniform1i(spriteShaderProgram.getUniform("verticalFlip").getLocation(), (component.isVerticallyFlipped()) ? GL11.GL_TRUE:GL11.GL_FALSE);
		GL20.glUniform1i(spriteShaderProgram.getUniform("sprite").getLocation(), 0);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
		
	}
	
	public void renderText(String text, Font font, float x, float y) {
		
		GL20.glUseProgram(textShaderProgram.getProgramId());
		GL30.glBindVertexArray(flatPlane.getVaoId());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, font.getGlyphs().getTextureId());
		
		float size = 0.1f;
		for (int i = 0; i < text.length(); i++) {
			Transform transform = new Transform(x + (i * size),y,0);
			transform.setXScale(size);
			transform.setYScale(size);
			transform.setZScale(size);
			
			
			GL20.glUniformMatrix4fv(textShaderProgram.getUniform("modelMatrix").getLocation(), false, transform.toMatrix().toBuffer());
			GL20.glUniform1i(textShaderProgram.getUniform("letter").getLocation(), font.getCharacterMapping(text.charAt(i)));
			GL20.glUniform1i(textShaderProgram.getUniform("diffuseTexture").getLocation(), 0);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
		}
		
	}
	
	public String getOpenGLVersion() {
		return GL11.glGetString(GL11.GL_VERSION);
	}
	
	public GLCapabilities getCapabilities() {
		return capabilities;
	}
	
	public boolean checkError(Logger logger) {
		int error = 0;
		
		while ((error = GL11.glGetError()) != GL11.GL_NO_ERROR) {
			switch (error) {
			case GL11.GL_INVALID_ENUM:
				logger.log("An OpenGL error has occured: Invalid enum.");
				break;
			case GL11.GL_INVALID_VALUE:
				logger.log("An OpenGL error has occured: Invalid value.");
				break;
			case GL11.GL_INVALID_OPERATION:
				logger.log("An OpenGL error has occured: Invalid operation.");
				break;
			case GL11.GL_STACK_OVERFLOW:
				logger.log("An OpenGL error has occured: Stack overflow.");
				break;
			case GL11.GL_STACK_UNDERFLOW:
				logger.log("An OpenGL error has occured: Stack underflow.");
				break;
			case GL11.GL_OUT_OF_MEMORY:
				logger.log("An OpenGL error has occured: Out of memory.");
				break;
			case GL30.GL_INVALID_FRAMEBUFFER_OPERATION:
				logger.log("An OpenGL error has occured: Invalid framebuffer operation.");
				break;
			case GL45.GL_CONTEXT_LOST:
				logger.log("An OpenGL error has occured: Context lost.");
				break;
			default:
				logger.log("An OpenGL error has occured: Unknown error.");
			}
		}
		
		return false;
	}
	
	public void renderAll() {
		for (Texture sprite : batches.keySet()) {
			MeshBatch batch = batches.get(sprite);
			render(batch);
			batch.clear();
		}
		for (AnimatorComponent animator : animators) {
			render(animator.getParentObject(), animator.getCurrentFrame(), animator.getTexture(), false);
		}
		animators.clear();
	}
	
	public void addSpriteComponent(SpriteComponent component) {
		MeshBatch batch = batches.get(component.getTexture());
		if (batch == null) {
			batch = new MeshBatch(flatPlane, component.getTexture(), MAX_BATCH_SIZE);
			batches.put(component.getTexture(), batch);
			batch.addToBatch(component.getParentObject().getTransform().toMatrix());
		}
		else {
			batch.addToBatch(component.getParentObject().getTransform().toMatrix());
		}
	}
	
	public void addAnimatorComponent(AnimatorComponent component) {
		animators.add(component);
	}
	
	public void setBackgroundColor(float r, float g, float b, float a) {
		GL11.glClearColor(r, g, b, a);
	}
	
	public void destroy() {
		GL20.glUseProgram(0);
		
		for (Texture sprite : batches.keySet()) {
			batches.get(sprite).destroy();
		}
		
		flatPlane.destroy();
		
		instanceShaderProgram.destroy();
		textShaderProgram.destroy();
		spriteShaderProgram.destroy();
		animSpriteShaderProgram.destroy();
	}
	
	public Camera getCamera() {
		return camera;
	}
	
}
