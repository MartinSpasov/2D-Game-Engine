package engine.graphics.text;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import engine.Game;
import engine.graphics.RenderSystem;
import engine.graphics.Texture;
import engine.graphics.memory.VertexArrayObject;

public class Font {

	private Texture glyphs;
	private HashMap<Character, Integer> characterMap;
	private int invalidCharacter;
	
	private VertexArrayObject charVAO;
	
	private float glyphWidth;
	private float glyphHeight;
	
	public Font(Texture glyphs, int invalidCharacter, float glyphWidth, float glyphHeight) {
		this(glyphs, invalidCharacter, glyphWidth, glyphHeight, new HashMap<Character, Integer>());
	}
	
	public Font(Texture glyphs, int invalidCharacter, float glyphWidth, float glyphHeight, HashMap<Character, Integer> characterMap) {
		this.glyphs = glyphs;
		this.invalidCharacter = invalidCharacter;
		this.characterMap = characterMap;
		this.glyphWidth = glyphWidth;
		this.glyphHeight = glyphHeight;
		
		this.charVAO = new VertexArrayObject();
		charVAO.bind();
		charVAO.addArrayBuffer(0, Game.toBuffer(RenderSystem.PLANE_VERTS), 3, GL11.GL_FLOAT);
		charVAO.addArrayBuffer(1, Game.toBuffer(RenderSystem.PLANE_UV), 2, GL11.GL_FLOAT);
		charVAO.enableAttribute(2);
		charVAO.unbind();
	}
	
	public Texture getGlyphs() {
		return glyphs;
	}
	
	public void addCharacterMapping(char c, int i) {
		characterMap.put(c, i);
	}
	
	public int getCharacterMapping(char c) {
		Integer i = characterMap.get(c);
		
		if (i == null) {
			return invalidCharacter;
		}
		else {
			return i;
		}
	}
	
	public float getGlyphWidth() {
		return glyphWidth;
	}
	
	public float getGlyphHeight() {
		return glyphHeight;
	}
	
	public VertexArrayObject getCharVAO() {
		return charVAO;
	}
	
	public void destroy() {
		glyphs.destroy();
	}
}
