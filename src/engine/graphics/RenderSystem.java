package engine.graphics;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.system.MemoryUtil;

import engine.Game;
import engine.graphics.shader.ShaderProgram;
import engine.graphics.text.Font;
import engine.graphics.text.Text;
import engine.math.Matrix4f;
import engine.object.Transform;
import engine.physics.geometry.Rectangle;
import engine.resource.Resources;

public class RenderSystem {
	
	public static final float[] PLANE_VERTS = new float[]{-0.5f,0.5f,0.0f, -0.5f,-0.5f,0.0f, 0.5f,0.5f,0.0f, 0.5f,0.5f,0.0f, -0.5f,-0.5f,0.0f, 0.5f,-0.5f, 0.0f};
	public static final float[] PLANE_UV = new float[]{0.0f,0.0f,0.0f,1.0f,1.0f,0.0f,1.0f,0.0f,0.0f,1.0f,1.0f,1.0f};

	public static final float[] RECTANGLE_VERTS = new float[]{
			-0.5f,0.5f,0.5f,0.5f,
			0.5f,0.5f,0.5f,-0.5f,
			0.5f,-0.5f,-0.5f,-0.5f,
			-0.5f,-0.5f,-0.5f,0.5f
	};
	
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
	private ShaderProgram bgShaderProgram;
	private ShaderProgram rectangleProgram;
	
	private Mesh flatPlane;
	
	private int debugRectangleVAO;
	private int debugRectangleVertexBuffer;
	
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
					decodedSeverity = "?";
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
		bgShaderProgram = new ShaderProgram(Resources.loadText("shader/bg_vert.shader"), Resources.loadText("shader/bg_frag.shader"));
		rectangleProgram = new ShaderProgram(Resources.loadText("shader/rectangle_vert.shader"), Resources.loadText("shader/rectangle_frag.shader"));
		
		flatPlane = new Mesh(Game.toBuffer(PLANE_VERTS), Game.toBuffer(PLANE_UV));
		
		debugRectangleVAO = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(debugRectangleVAO);
		
		debugRectangleVertexBuffer = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, debugRectangleVertexBuffer);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Game.toBuffer(RECTANGLE_VERTS), GL15.GL_STATIC_DRAW);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);
		
		//GL11.glClearColor(0.0f, 51/255.0f, 153/255.0f, 1.0f);
		//GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL43.glDebugMessageInsert(GL43.GL_DEBUG_SOURCE_APPLICATION, GL43.GL_DEBUG_TYPE_OTHER, 1, GL43.GL_DEBUG_SEVERITY_NOTIFICATION, "This is a test message!");
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		
			
		//GL20.glUseProgram(defaultShaderProgram.getProgramId());
	}

	public void render(MeshBatch batch) {
		GL20.glUseProgram(instanceShaderProgram.getProgramId());
		
		batch.getMesh().getVertexArrayObject().bind();
		//GL30.glBindVertexArray(batch.getMesh().getVaoId());
		
		
		batch.loadBatch(camera);
		
		// Diffuse Texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, batch.getTexture().getTextureId());
		GL20.glUniform1i(instanceShaderProgram.getUniform("diffuseTexture").getLocation(), 0);
		
		//GL20.glEnableVertexAttribArray(0);
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES, 0, batch.getMesh().getNumVertices(), batch.size());
		
	}

	public void renderAnimationFrame(Transform transform, int frame, Texture tex, boolean horizontalFlip) {
		GL20.glUseProgram(animSpriteShaderProgram.getProgramId());
		
		flatPlane.getVertexArrayObject().bind();
		//GL30.glBindVertexArray(flatPlane.getVaoId());
		
		Matrix4f finalMatrix = camera.getProjectionMatrix().multiply(camera.getWorldMatrix().multiply(transform.toMatrix()));
		
		// Diffuse Texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, tex.getTextureId());

		GL20.glUniformMatrix4fv(animSpriteShaderProgram.getUniform("mvpMatrix").getLocation(), false, finalMatrix.toBuffer());
		GL20.glUniform1i(animSpriteShaderProgram.getUniform("diffuseTextureAtlas").getLocation(), 0);
		GL20.glUniform1i(animSpriteShaderProgram.getUniform("currentFrame").getLocation(), frame);
		GL20.glUniform1i(animSpriteShaderProgram.getUniform("horizontalFlip").getLocation(), (horizontalFlip) ? GL11.GL_TRUE:GL11.GL_FALSE);


		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
	}

	public void renderSprite(Transform transform, Texture texture, boolean hFlip, boolean vFlip) {
		GL20.glUseProgram(spriteShaderProgram.getProgramId());
		
		flatPlane.getVertexArrayObject().bind();
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureId());
		
		Matrix4f finalMatrix = camera.getProjectionMatrix().multiply(camera.getWorldMatrix().multiply(transform.toMatrix()));
		
		GL20.glUniformMatrix4fv(spriteShaderProgram.getUniform("mvpMatrix").getLocation(), false, finalMatrix.toBuffer());
		GL20.glUniform1i(spriteShaderProgram.getUniform("horizontalFlip").getLocation(), (hFlip) ? GL11.GL_TRUE:GL11.GL_FALSE);
		GL20.glUniform1i(spriteShaderProgram.getUniform("verticalFlip").getLocation(), (vFlip) ? GL11.GL_TRUE:GL11.GL_FALSE);
		GL20.glUniform1i(spriteShaderProgram.getUniform("sprite").getLocation(), 0);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
		
	}
	
	public void renderRectangle(Rectangle rect, Color color, boolean filled) {
		GL20.glUseProgram(rectangleProgram.getProgramId());
		
		if (filled) {
			flatPlane.getVertexArrayObject().bind();
		}
		else {
			GL30.glBindVertexArray(debugRectangleVAO);
		}
		
		GL20.glUniform4fv(rectangleProgram.getUniform("rectangleColor").getLocation(), color.toBuffer());
		
		Transform transform = new Transform(rect.getX(), rect.getY(), 0);
		transform.setXScale(rect.getWidth());
		transform.setYScale(rect.getHeight());
		Matrix4f finalMatrix = camera.getProjectionMatrix().multiply(camera.getWorldMatrix().multiply(transform.toMatrix()));
			
		GL20.glUniformMatrix4fv(rectangleProgram.getUniform("mvpMatrix").getLocation(), false, finalMatrix.toBuffer());
		
		if (filled) {
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
		}
		else {
			GL11.glDrawArrays(GL11.GL_LINES, 0, RECTANGLE_VERTS.length);
		}
	}
	
	
	public void renderUIRectangle(Rectangle rect, Color color) {
		GL20.glUseProgram(rectangleProgram.getProgramId());
		
		// Remember to change this calculation when I change the size of the flatplane
		Matrix4f modelMatrix = Matrix4f.translation(rect.getX(), rect.getY(), 0).multiply(Matrix4f.scale(rect.getWidth(), rect.getHeight(), 1f));

		flatPlane.getVertexArrayObject().bind();
		//GL30.glBindVertexArray(flatPlane.getVaoId());
		
		GL20.glUniformMatrix4fv(rectangleProgram.getUniform("mvpMatrix").getLocation(), false, modelMatrix.toBuffer());
		GL20.glUniform4fv(rectangleProgram.getUniform("rectangleColor").getLocation(), color.toBuffer());
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
	}
	
	public void renderBackground(Background background) {
		GL20.glUseProgram(bgShaderProgram.getProgramId());
		
		
		flatPlane.getVertexArrayObject().bind();
		//GL30.glBindVertexArray(flatPlane.getVaoId());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, background.getTexture().getTextureId());
		
		GL20.glUniformMatrix4fv(bgShaderProgram.getUniform("modelMatrix").getLocation(), false, Matrix4f.scale(2, 2, 1).toBuffer());
		GL20.glUniform1i(bgShaderProgram.getUniform("diffuseTexture").getLocation(), 0);
		GL20.glUniform1f(bgShaderProgram.getUniform("xOffset").getLocation(), background.getXOffset());
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, flatPlane.getNumVertices());
	}
	
	public void renderText(Text text, Font font, float x, float y, Color color) {
		GL20.glUseProgram(textShaderProgram.getProgramId());
		
		font.getCharVAO().bind();
		
		text.getIndexBuffer().bind();
		text.getIndexBuffer().vertexAttributeIPointer(2, 1, GL11.GL_INT, false, 0, 0);
		text.getIndexBuffer().vertexAttributeDivisor(2, 1);
		text.getIndexBuffer().unbind();
		
		FloatBuffer modelMatrix = Matrix4f.translation(x, y, 0).multiply(Matrix4f.scale(font.getGlyphWidth(), font.getGlyphHeight(), 1)).toBuffer();
		
		// FIXME remove this when I get home
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		font.getGlyphs().bind();
		
		GL20.glUniformMatrix4fv(textShaderProgram.getUniform("modelMatrix").getLocation(), false, modelMatrix);
		GL20.glUniform1i(textShaderProgram.getUniform("diffuseTexture").getLocation(), 0);
		GL20.glUniform4fv(textShaderProgram.getUniform("textColor").getLocation(), color.toBuffer());
		GL20.glUniform1f(textShaderProgram.getUniform("xOffset").getLocation(), font.getGlyphWidth());
		
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES, 0, RenderSystem.PLANE_VERTS.length, text.getNumChars());
	}
	
	public String getOpenGLVersion() {
		return GL11.glGetString(GL11.GL_VERSION);
	}
	
	public GLCapabilities getCapabilities() {
		return capabilities;
	}
	
	public void setBackgroundColor(float r, float g, float b, float a) {
		GL11.glClearColor(r, g, b, a);
	}
	
	public void destroy() {
		GL20.glUseProgram(0);

		flatPlane.destroy();
		
		GL15.glDeleteBuffers(debugRectangleVertexBuffer);
		GL30.glDeleteVertexArrays(debugRectangleVAO);
		
		instanceShaderProgram.destroy();
		textShaderProgram.destroy();
		spriteShaderProgram.destroy();
		animSpriteShaderProgram.destroy();
		bgShaderProgram.destroy();
		rectangleProgram.destroy();
	}
	
	public Camera getCamera() {
		return camera;
	}
	
}
