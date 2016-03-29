package engine.graphics.memory;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL44;

public class Buffer {

	public static final int ARRAY_BUFFER = GL15.GL_ARRAY_BUFFER;
	public static final int ATOMIC_COUNTER_BUFFER = GL42.GL_ATOMIC_COUNTER_BUFFER;
	public static final int COPY_READ_BUFFER = GL31.GL_COPY_READ_BUFFER;
	public static final int COPY_WRITE_BUFFER = GL31.GL_COPY_WRITE_BUFFER;
	public static final int DISPATCH_INDIRECT_BUFFER = GL43.GL_DISPATCH_INDIRECT_BUFFER;
	public static final int DRAW_INDIRECT_BUFFER = GL40.GL_DRAW_INDIRECT_BUFFER;
	public static final int ELEMENT_ARRAY_BUFFER = GL15.GL_ELEMENT_ARRAY_BUFFER;
	public static final int PIXEL_PACK_BUFFER = GL21.GL_PIXEL_PACK_BUFFER;
	public static final int PIXEL_UNPACK_BUFFER = GL21.GL_PIXEL_UNPACK_BUFFER;
	public static final int QUERY_BUFFER = GL44.GL_QUERY_BUFFER;
	public static final int SHADER_STORAGE_BUFFER = GL43.GL_SHADER_STORAGE_BUFFER;
	public static final int TEXTURE_BUFFER = GL31.GL_TEXTURE_BUFFER;
	public static final int TRANSFORM_FEEDBACK_BUFFER = GL30.GL_TRANSFORM_FEEDBACK_BUFFER;
	public static final int UNIFORM_BUFFER = GL31.GL_UNIFORM_BUFFER;
	
	public static final int STREAM_DRAW = GL15.GL_STREAM_DRAW;
	public static final int STREAM_READ = GL15.GL_STREAM_READ;
	public static final int STREAM_COPY = GL15.GL_STREAM_COPY;
	public static final int STATIC_DRAW = GL15.GL_STATIC_DRAW;
	public static final int STATIC_READ = GL15.GL_STATIC_READ;
	public static final int STATIC_COPY = GL15.GL_STATIC_COPY;
	public static final int DYNAMIC_DRAW = GL15.GL_DYNAMIC_DRAW;
	public static final int DYNAMIC_READ = GL15.GL_DYNAMIC_READ;
	public static final int DYNAMIC_COPY = GL15.GL_DYNAMIC_COPY;
	
	private int handle;
	private int target;
	
	public Buffer(int target) {
		handle = GL15.glGenBuffers();
		this.target = target;
	}
	
	public void bufferData(FloatBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}
	
	public void bufferData(ByteBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}
	
	public void bufferData(DoubleBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}
	
	public void bufferData(ShortBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}
	
	public void bufferData(IntBuffer data, int usage) {
		GL15.glBufferData(target, data, usage);
	}
	
	public void bufferData(long size, int usage) {
		GL15.glBufferData(target, size, usage);
	}
	
	public void vertexAttributePointer(int index, int size, int type) {
		GL20.glVertexAttribPointer(index, size, type, false, 0, 0);
	}
	
	public void vertexAttributePointer(int index, int size, int type, boolean normalized) {
		GL20.glVertexAttribPointer(index, size, type, normalized, 0, 0);
	}
	
	public void vertexAttributePointer(int index, int size, int type, boolean normalized, int stride, int pointer) {
		GL20.glVertexAttribPointer(index, size, type, normalized, stride, pointer);
	}
	
	public void bind() {
		GL15.glBindBuffer(target, handle);
	}
	
	public void unbind() {
		GL15.glBindBuffer(target, 0);
	}
	
	public void destroy() {
		GL15.glDeleteBuffers(handle);
	}
	
}
