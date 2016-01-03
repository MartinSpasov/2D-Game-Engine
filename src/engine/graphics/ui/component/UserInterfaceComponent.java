package engine.graphics.ui.component;

import engine.Game;
import engine.graphics.Color;
import engine.physics.geometry.Rectangle;

public abstract class UserInterfaceComponent {

	private Rectangle bounds;
	private Color backgroundColor;
	
	public UserInterfaceComponent(Rectangle bounds) {
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
	
	public abstract void tick(float delta, Game game);
	public abstract void onClick();
}
