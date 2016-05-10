package engine.graphics.ui.widget;

import engine.graphics.Color;
import engine.graphics.RenderSystem;
import engine.physics.geometry.Rectangle;

public abstract class Widget {

	private Rectangle bounds;
	private Color backgroundColor;
	
	private boolean visible;
	
	public Widget(Rectangle bounds) {
		this(bounds, Color.WHITE);
	}
	
	public Widget(Rectangle bounds, Color backgroundColor) {
		this.bounds = bounds;
		this.backgroundColor = backgroundColor;
		visible = true;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public abstract void render(RenderSystem renderer);
	public abstract void onClick();
}
