package engine.graphics.text;

import java.util.HashMap;

import engine.graphics.ArrayTexture;

public class Font {

	private ArrayTexture glyphs;
	private HashMap<Character, Integer> characterMap;
	private int defaultCharacter;
	
	public Font(ArrayTexture glyphs, int defaultCharacter) {
		this.glyphs = glyphs;
		this.defaultCharacter = defaultCharacter;
		characterMap = new HashMap<Character, Integer>();
	}
	
	public ArrayTexture getGlyphs() {
		return glyphs;
	}
	
	public void addCharacterMapping(char c, int i) {
		characterMap.put(c, i);
	}
	
	public int getCharacterMapping(char c) {
		Integer i = characterMap.get(c);
		
		if (i == null) {
			return defaultCharacter;
		}
		else {
			return i;
		}
	}
	
	public void destroy() {
		glyphs.destroy();
	}
}
