package engine.graphics.shader;

import org.lwjgl.opengl.GL20;

public class Uniform {
	
	private String name;
	private int location;
	
	public Uniform(String name, ShaderProgram program) {
		this.name = name;
		location = GL20.glGetUniformLocation(program.getProgramId(), name);
	}
	
	public String getName() {
		return name;
	}
	
	public int getLocation() {
		return location;
	}
}
