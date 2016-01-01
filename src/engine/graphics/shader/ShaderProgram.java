package engine.graphics.shader;

import java.nio.IntBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import engine.Game;

public class ShaderProgram {

	private int programId;
	
	private HashMap<String, Uniform> uniforms;
	
	public ShaderProgram(String vertSrc, String fragSrc) {
		// TODO handle shader compilation error
		int vertShaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertShaderId, vertSrc);
		GL20.glCompileShader(vertShaderId);
		
		int status = GL20.glGetShaderi(vertShaderId, GL20.GL_COMPILE_STATUS);
		if (status == GL11.GL_FALSE) {
			Game.logger.log("Shader failed to compile: " + GL20.glGetShaderInfoLog(vertShaderId));
		}
		
		int fragShaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragShaderId, fragSrc);
		GL20.glCompileShader(fragShaderId);
		
		status = GL20.glGetShaderi(fragShaderId, GL20.GL_COMPILE_STATUS);
		if (status == GL11.GL_FALSE) {
			Game.logger.log("Failed to compile shader: " + GL20.glGetShaderInfoLog(fragShaderId));
		}
				
		programId = GL20.glCreateProgram();
		GL20.glAttachShader(programId, vertShaderId);
		GL20.glAttachShader(programId, fragShaderId);
		GL20.glLinkProgram(programId);
		
		status = GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS);
		if (status == GL11.GL_FALSE) {
			Game.logger.log("Failed to link program: " + GL20.glGetProgramInfoLog(programId));
		}
		
		IntBuffer countBuffer = BufferUtils.createIntBuffer(1);
		GL20.glGetProgramiv(programId, GL20.GL_ACTIVE_UNIFORMS, countBuffer);
		int count = countBuffer.get(0);
		
		uniforms = new HashMap<String, Uniform>();
		for (int i = 0; i < count; i++) {
			IntBuffer size = BufferUtils.createIntBuffer(1);
			IntBuffer type = BufferUtils.createIntBuffer(1);
			String name = GL20.glGetActiveUniform(programId, i, size, type);
			int location = GL20.glGetUniformLocation(programId, name);
			uniforms.put(name, new Uniform(name, location, size.get(0), type.get(0)));
		}
		
		// Clean up.
		GL20.glDetachShader(programId, vertShaderId);
		GL20.glDetachShader(programId, fragShaderId);
		GL20.glDeleteShader(vertShaderId);
		GL20.glDeleteShader(fragShaderId);
		
	}
	
	public int getProgramId() {
		return programId;
	}
	
	public Uniform getUniform(String name) {
		// TODO check if uniform actually exists
		return uniforms.get(name);
	}
	
	public void destroy() {
		GL20.glDeleteProgram(programId);
	}
}
