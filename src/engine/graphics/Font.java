package engine.graphics;

public class Font {

	private Texture glyphs;
	
	private int rows;
	private int columns;
	
	public Font(Texture glyphs, int rows, int columns) {
		this.glyphs = glyphs;
		this.rows = rows;
		this.columns = columns;
	}
	
	public Texture getGlyphs() {
		return glyphs;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
}
