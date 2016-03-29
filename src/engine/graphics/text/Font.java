package engine.graphics.text;

import java.util.HashMap;

import engine.graphics.Texture;

public class Font {

	private Texture glyphs;
	private HashMap<Character, Integer> characterMap;
	private int invalidCharacter;
	
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
	
	public void destroy() {
		glyphs.destroy();
	}
}
