package engine.graphics.ui.widget;

import engine.graphics.Color;
import engine.graphics.RenderSystem;
import engine.physics.geometry.Rectangle;

public abstract class Widget {

	private Rectangle bounds;
	private Color backgroundColor;
	
	public Widget(Rectangle bounds) {
		this.bounds = bounds;
		backgroundColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public abstract void render(RenderSystem renderer);
	public abstract void onClick();
}
