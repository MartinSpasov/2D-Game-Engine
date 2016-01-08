package engine.graphics.ui.component;

import java.util.ArrayList;

import engine.Game;
import engine.graphics.Color;
import engine.graphics.text.Font;
import engine.physics.geometry.Rectangle;

public class Button extends UserInterfaceComponent {

	private String text;
	private Font font;
	private Color textColor;
	
	private ArrayList<ButtonListener> listeners;
	
	public Button(Rectangle bounds, String text, Font font) {
		super(bounds);
		this.text = text;
		this.font = font;
		textColor = Color.BLACK;
		listeners = new ArrayList<ButtonListener>();
	}
	
	public void registerButtonListener(ButtonListener listener) {
		listeners.add(listener);
	}
	
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
	@Override
	public void tick(float delta, Game game) {
		game.getRenderSystem().render(getBounds(), getBackgroundColor());
		
		float fontX = getBounds().getX() + (getBounds().getWidth() / 2.0f);
		float fontY = getBounds().getY() - (getBounds().getHeight() / 2.0f);
		
		game.getRenderSystem().renderText(text, font, getBounds().getX(), getBounds().getY(), textColor);
	}

	@Override
	public void onClick() {
		for (ButtonListener listener : listeners) {
			listener.onPress(this);
		}
	}

}
