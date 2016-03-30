package engine.graphics.memory;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VertexArrayObject {

	private int handle;
	
	private ArrayList<Buffer> buffers;
	
	public VertexArrayObject() {
		handle = GL30.glGenVertexArrays();
		buffers = new ArrayList<Buffer>();
	}
	
	public void addArrayBuffer(int attribIndex, ByteBuffer data, int size, int type) {
		Buffer buffer = new Buffer(Buffer.ARRAY_BUFFER);
		
		buffer.bind();
		buffer.bufferData(data, Buffer.STATIC_DRAW);
		buffer.vertexAttributePointer(attribIndex, size, type);
		buffer.unbind();
		
		buffers.add(buffer);
		enableAttribute(attribIndex);
	}
	
	public void addArrayBuffer(int attribIndex, FloatBuffer data, int size, int type) {
		Buffer buffer = new Buffer(Buffer.ARRAY_BUFFER);
		
		buffer.bind();
		buffer.bufferData(data, Buffer.STATIC_DRAW);
		buffer.vertexAttributePointer(attribIndex, size, type);
		buffer.unbind();
		
		buffers.add(buffer);
		enableAttribute(attribIndex);
	}
	
	public void addArrayBuffer(int attribIndex, DoubleBuffer data, int size, int type) {
		Buffer buffer = new Buffer(Buffer.ARRAY_BUFFER);
		
		buffer.bind();
		buffer.bufferData(data, Buffer.STATIC_DRAW);
		buffer.vertexAttributePointer(attribIndex, size, type);
		buffer.unbind();
		
		buffers.add(buffer);
		enableAttribute(attribIndex);
	}
	
	public void addArrayBuffer(int attribIndex, ShortBuffer data, int size, int type) {
		Buffer buffer = new Buffer(Buffer.ARRAY_BUFFER);
		
		buffer.bind();
		buffer.bufferData(data, Buffer.STATIC_DRAW);
		buffer.vertexAttributePointer(attribIndex, size, type);
		buffer.unbind();
		
		buffers.add(buffer);
		enableAttribute(attribIndex);
	}
	
	public void addArrayBuffer(int attribIndex, IntBuffer data, int size, int type) {
		Buffer buffer = new Buffer(Buffer.ARRAY_BUFFER);
		
		buffer.bind();
		buffer.bufferData(data, Buffer.STATIC_DRAW);
		buffer.vertexAttributePointer(attribIndex, size, type);
		buffer.unbind();
		
		buffers.add(buffer);
		enableAttribute(attribIndex);
	}
	
	public void addArrayBuffer(Buffer buffer) {
		buffers.add(buffer);
	}
	
	public void enableAttribute(int index) {
		GL20.glEnableVertexAttribArray(index);
	}
	
	public void disableAttribute(int index) {
		GL20.glDisableVertexAttribArray(index);
	}
	
	public void bind() {
		GL30.glBindVertexArray(handle);
	}
	
	public void unbind() {
		GL30.glBindVertexArray(0);
	}
	
	public void destroy() {
		
		for (Buffer buffer : buffers) {
			buffer.destroy();
		}
		
		GL30.glDeleteVertexArrays(handle);
	}
	
}
