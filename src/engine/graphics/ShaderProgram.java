package engine.graphics;

import org.lwjgl.opengl.GL20;

public class ShaderProgram {

	private int programId;
	
	public ShaderProgram(String vertSrc, String fragSrc) {
		// TODO handle shader compilation error
		int vertShaderId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertShaderId, vertSrc);
		GL20.glCompileShader(vertShaderId);
		//int status = GL20.glGetShaderi(vertShaderId, GL20.GL_COMPILE_STATUS);
		//System.out.println(status);
		//System.out.println(GL11.GL_FALSE);
		System.out.println(GL20.glGetShaderInfoLog(vertShaderId));
		
		int fragShaderId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragShaderId, fragSrc);
		GL20.glCompileShader(fragShaderId);
		System.out.println(GL20.glGetShaderInfoLog(fragShaderId));
				
		programId = GL20.glCreateProgram();
		GL20.glAttachShader(programId, vertShaderId);
		GL20.glAttachShader(programId, fragShaderId);
		GL20.glLinkProgram(programId);
		
		// Clean up.
		GL20.glDetachShader(programId, vertShaderId);
		GL20.glDetachShader(programId, fragShaderId);
		GL20.glDeleteShader(vertShaderId);
		GL20.glDeleteShader(fragShaderId);
		
	}
	
	public int getProgramId() {
		return programId;
	}
	
	public void destroy() {
		GL20.glDeleteProgram(programId);
	}
}
