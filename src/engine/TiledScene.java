package engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL31;

import engine.graphics.RenderSystem;
import engine.graphics.Texture;
import engine.graphics.memory.Buffer;
import engine.graphics.memory.VertexArrayObject;
import engine.graphics.shader.ShaderProgram;
import engine.math.Matrix4f;
import engine.resource.Resources;

public class TiledScene extends Scene {
	
	private ArrayList<Tile> tiles;
	private Texture tileSheet;
	
	private VertexArrayObject tileVAO;
	private ShaderProgram tileShaderProgram;
	
	public TiledScene(ArrayList<Tile> tiles, Texture tileSheet) {
		this.tiles = tiles;
		this.tileSheet = tileSheet;
		
		tileShaderProgram = new ShaderProgram(Resources.loadText("shader/tile_vert.shader"), Resources.loadText("shader/tile_frag.shader"));
		
		FloatBuffer matrices = BufferUtils.createFloatBuffer(tiles.size() * 16);
		IntBuffer indices = BufferUtils.createIntBuffer(tiles.size());
		
		for (Tile tile : tiles) {
			Matrix4f matrix = Matrix4f.translation(tile.getX(), tile.getY(), 0);
			
			matrices.put(matrix.m00);
			matrices.put(matrix.m10);
			matrices.put(matrix.m20);
			matrices.put(matrix.m30);
			matrices.put(matrix.m01);
			matrices.put(matrix.m11);
			matrices.put(matrix.m21);
			matrices.put(matrix.m31);
			matrices.put(matrix.m02);
			matrices.put(matrix.m12);
			matrices.put(matrix.m22);
			matrices.put(matrix.m32);
			matrices.put(matrix.m03);
			matrices.put(matrix.m13);
			matrices.put(matrix.m23);
			matrices.put(matrix.m33);
			
			indices.put(tile.getIndex());
		}
		
		matrices.flip();
		indices.flip();
		
		tileVAO = new VertexArrayObject();
		
		tileVAO.bind();
		
		tileVAO.addArrayBuffer(0, Game.toBuffer(RenderSystem.PLANE_VERTS), 3, GL11.GL_FLOAT);
		tileVAO.addArrayBuffer(1, Game.toBuffer(RenderSystem.PLANE_UV), 2, GL11.GL_FLOAT);
		
		Buffer matrixBuffer = new Buffer(Buffer.ARRAY_BUFFER);
		Buffer indexBuffer = new Buffer(Buffer.ARRAY_BUFFER);
		
		indexBuffer.bind();
		indexBuffer.bufferData(indices, Buffer.STATIC_DRAW);
		indexBuffer.vertexAttributeIPointer(2, 1, GL11.GL_INT, false, 0, 0);
		indexBuffer.vertexAttributeDivisor(2, 1);
		
		tileVAO.enableAttribute(2);
		tileVAO.addArrayBuffer(indexBuffer);
		
		matrixBuffer.bind();
		matrixBuffer.bufferData(matrices, Buffer.STATIC_DRAW);
		
		for (int i = 0; i < 4; i++) {
			matrixBuffer.vertexAttributePointer(3 + i, 4, GL11.GL_FLOAT, false, 64, 16 * i);
			tileVAO.enableAttribute(3 + i);
			matrixBuffer.vertexAttributeDivisor(3 + i, 1);
		}
		tileVAO.addArrayBuffer(matrixBuffer);
		
		matrixBuffer.unbind();
		tileVAO.unbind();

	}
	
	@Override
	public void render(RenderSystem renderer) {
		GL20.glUseProgram(tileShaderProgram.getProgramId());

		tileVAO.bind();
		tileSheet.bind();
		
		GL20.glUniform1i(tileShaderProgram.getUniform("tilesheet").getLocation(), 0);
		GL20.glUniformMatrix4fv(tileShaderProgram.getUniform("vpMatrix").getLocation(), false, renderer.getCamera().getProjectionMatrix().multiply(renderer.getCamera().getWorldMatrix()).toBuffer());
		
		GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES, 0, RenderSystem.PLANE_VERTS.length, tiles.size());
		
		super.render(renderer);
	}
	
	public VertexArrayObject getTileVAO() {
		return tileVAO;
	}
	
	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	public Texture getTileSheet() {
		return tileSheet;
	}
	
	public void destroy() {
		tileShaderProgram.destroy();
		tileSheet.destroy();
		tileVAO.destroy();
	}

}
