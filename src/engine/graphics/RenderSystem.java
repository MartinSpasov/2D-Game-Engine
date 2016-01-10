package engine.graphics;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.system.MemoryUtil;

import engine.Game;
import engine.Tile;
import engine.console.Logger;
import engine.graphics.shader.ShaderProgram;
import engine.graphics.text.Font;
import engine.math.Matrix4f;
import engine.object.GameObject;
import engine.object.Transform;
import engine.object.component.AnimatorComponent;
import engine.object.component.SpriteComponent;
import engine.physics.geometry.Rectangle;
import engine.resource.Resources;

public class RenderSystem {
	
	public static final float[] PLANE_VERTS = new float[]{-0.5f,0.5f,0.0f, -0.5f,-0.5f,0.0f, 0.5f,0.5f,0.f, 0.5f,0.5f,0.0f, -0.5f,-0.5f,0.0f, 0.5f,-0.5f, 0.0f};
	public static final float[] PLANE_UV = new float[]{0.0f,0.0f,0.0f,1.0f,1.0f,0.0f,1.0f,0.0f,0.0f,1.0f,1.0f,1.0f};

	public static final float[] PLANE_VERTS_TOP_LEFT = new float[]{
			0.0f,0.0f,0.0f, 
			0.0f,-1.0f,0.0f, 
			1.0f,0.0f,0.0f, 
			1.0f,0.0f,0.0f, 
			0.0f,-1.0f,0.0f, 
			1.0f,-1.0f, 0.0f};
	
	public static final int MAX_BATCH_SIZE = 10000;
	
	private GLCapabilities capabilities;
	private GLDebugMessageCallback debugCallback;
	
	private Camera camera;
	
	private ShaderProgram instanceShaderProgram;
	private ShaderProgram textShaderProgram;
	private ShaderProgram spriteShaderProgram;
	private ShaderProgram animSpriteShaderProgram;
	private ShaderProgram uiShaderProgram;
	private ShaderProgram tileShaderProgram;
	private ShaderProgram bgShaderProgram;
	
	private Mesh flatPlane;
	
	private HashMap<Texture, MeshBatch> batches;
	private ArrayList<AnimatorComponent> animators;
	
	private ArrayList<Background> backgrounds;
	
	public RenderSystem(Camera camera) {
		this.camera = camera;
		capabilities = GL.createCapabilities();
		
		debugCallback = new GLDebugMessageCallback() {
			
			@Override
			public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
				String decodedMessage = MemoryUtil.memDecodeUTF8(MemoryUtil.memByteBuffer(message, length));
				String decodedSeverity;
				
				switch(severity) {
				case GL43.GL_DEBUG_SEVERITY_LOW:
					decodedSeverity = "LOW";
					break;
				case GL43.GL_DEBUG_SEVERITY_MEDIUM:
					decodedSeverity = "MEDIUM";
					break;
				case GL43.GL_DEBUG_SEVERITY_HIGH:
					decodedSeverity = "HIGH";
					break;
				case GL43.GL_DEBUG_SEVERITY_NOTIFICATION:
					decodedSeverity = "NOTIFY";
					break;
				default:
					decodedSeverity = "";
				}
				
				Game.logger.log("[" + decodedSeverity +  "] " + decodedMessage);
			}
			
		};
		GL43.glDebugMessageCallback(debugCallback, 0);
		GL43.glDebugMessageControl(GL11.GL_DONT_CARE, GL11.GL_DONT_CARE, GL11.GL_DONT_CARE, 0, BufferUtils.createByteBuffer(0), true);
		
		instanceShaderProgram = new ShaderProgram(Resources.loadText("shader/instance_vert.shader"), Resources.loadText("shader/instance_frag.shader"));
		textShaderProgram = new ShaderProgram(Resources.loadText("shader/text_vert.shader"), Resources.loadText("shader/text_frag.shader"));
		spriteShaderProgram = new ShaderProgram(Resources.loadText("shader/default_vert.shader"), Resources.loadText("shader/sprite_frag.shader"));
		animSpriteShaderProgram = new ShaderProgram(Resources.loadText("shader/default_vert.shader"), Resources.loadText("shader/animsprite_frag.shader"));
		uiShaderProgram = new ShaderProgram(Resources.loadText("shader/ui_vert.shader"), Resources.loadText("shader/ui_frag.shader"));
		tileShaderProgram = new ShaderProgram(Resources.loadText("shader/tile_vert.shader"), Resources.loadText("shader/tile_frag.shader"));
		bgShaderProgram = new ShaderProgram(Resources.loadText("shader/bg_vert.shader"), Resources.loadText("shader/bg_frag.shader"));
		
		flatPlane = new Mesh(Game.toBuffer(PLANE_VERTS), Game.toBuffer(PLANE_UV));
		
		animators = new ArrayList<AnimatorComponent>();
		batches = new HashMap<Texture, MeshBatch>();
		backgrounds = new ArrayList<Background>();
		
		GL11.glClearColor(0.0f, 51/255.0f, 153/255.0f, 1.0f);
		//GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL43.glDebugMessageInsert(GL43.GL_DEBUG_SOURCE_APPLICATION, GL43.GL_DEBUG_TYPE_OTHER, 1, GL43.GL_DEBUG_SEVERITY_NOTIFICATION, "This is a test message!");
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
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
	
	public void renderText(String text, Font font, float x, float y, Color color) {
		
		GL20.glUseProgram(textShaderProgram.getProgramId());
		GL30.glBindVertexArray(flatPlane.getVaoId());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, font.getGlyphs().getTextureId());
		
		//float size = 0.1f;
		for (int i = 0; i < text.length(); i++) {
			Transform transform = new Transform(x + (i * font.getGlyphWidth()),y,0);
			transform.setXScale(font.getGlyphWidth());
			transform.setYScale(font.getGlyphHeight());
			//transform.setZScale(size);
			
			GL20.glUniformMatrix4fv(textShaderProgram.getUniform("modelMatrix").getLocation(), false, transform.toMatrix().toBuffer());
			GL20.glUniform1i(textShaderProgram.getUniform("letter").getLocation(), font.getCharacterMapping(text.charAt(i)));
			GL20.glUniform1i(textShaderProgram.getUniform("diffuseTexture").getLocation(), 0);
			GL20.glUniform4fv(textShaderProgram.getUniform("textColor").getLocation(), color.toBuffer());
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
		}
		
	}
	
	public void renderLevel(ArrayList<Tile> tiles, ArrayTexture texture) {
		GL20.glUseProgram(tileShaderProgram.getProgramId());
		GL30.glBindVertexArray(flatPlane.getVaoId());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, texture.getTextureId());
		
		for (Tile tile : tiles) {
			Transform transform = new Transform(tile.getX(), tile.getY(), 0);
			Matrix4f finalMatrix = camera.getProjectionMatrix().multiply(camera.getWorldMatrix().multiply(transform.toMatrix()));
			
			GL20.glUniformMatrix4fv(tileShaderProgram.getUniform("mvpMatrix").getLocation(), false, finalMatrix.toBuffer());
			GL20.glUniform1i(tileShaderProgram.getUniform("index").getLocation(), tile.getIndex());
			GL20.glUniform1i(tileShaderProgram.getUniform("diffuseTexture").getLocation(), 0);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
		}
	}
	
	public void render(Rectangle rect, Color backgroundColor) {
		GL20.glUseProgram(uiShaderProgram.getProgramId());
		
		// Remember to change this calculation when I change the size of the flatplane
		Matrix4f modelMatrix = Matrix4f.translation(rect.getX(), rect.getY(), 0).multiply(Matrix4f.scale(rect.getWidth(), rect.getHeight(), 1f));

		GL30.glBindVertexArray(flatPlane.getVaoId());
		
		GL20.glUniformMatrix4fv(uiShaderProgram.getUniform("modelMatrix").getLocation(), false, modelMatrix.toBuffer());
		GL20.glUniform4fv(uiShaderProgram.getUniform("backgroundColor").getLocation(), backgroundColor.toBuffer());
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
	}
	
	public void renderBackground(Background background) {
		GL20.glUseProgram(bgShaderProgram.getProgramId());
		
		GL30.glBindVertexArray(flatPlane.getVaoId());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, background.getTexture().getTextureId());
		
		GL20.glUniformMatrix4fv(bgShaderProgram.getUniform("modelMatrix").getLocation(), false, Matrix4f.scale(2, 2, 1).toBuffer());
		GL20.glUniform1i(bgShaderProgram.getUniform("diffuseTexture").getLocation(), 0);
		GL20.glUniform1f(bgShaderProgram.getUniform("xOffset").getLocation(), background.getXOffset());
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
	}
	
	public String getOpenGLVersion() {
		return GL11.glGetString(GL11.GL_VERSION);
	}
	
	public GLCapabilities getCapabilities() {
		return capabilities;
	}
	
	@Deprecated
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
		for (Background bg : backgrounds) {
			renderBackground(bg);
		}
		for (Texture sprite : batches.keySet()) {
			MeshBatch batch = batches.get(sprite);
			render(batch);
			batch.clear();
		}
		for (AnimatorComponent animator : animators) {
			render(animator.getParentObject(), animator.getCurrentFrame(), animator.getTexture(), animator.isHorizontallyFlipped());
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
	
	public void addBackground(Background background) {
		backgrounds.add(background);
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
		uiShaderProgram.destroy();
	}
	
	public Camera getCamera() {
		return camera;
	}
	
}
