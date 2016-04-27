package engine.graphics.ui.widget;

import java.util.ArrayList;

import engine.graphics.Color;
import engine.graphics.RenderSystem;
import engine.graphics.text.Font;
import engine.graphics.text.Text;
import engine.physics.geometry.Rectangle;

public class Button extends Widget {

	private Text text;
	private Font font;
	private Color textColor;
	
	private ArrayList<ButtonListener> listeners;
	
	public Button(Rectangle bounds, String text, Font font) {
		super(bounds);
		this.text = new Text(text, font);
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
	public void render(RenderSystem renderer) {
		renderer.renderUIRectangle(getBounds(), getBackgroundColor());
		renderer.renderText(text, font, getBounds().getX(), getBounds().getY(), textColor);
	}

	@Override
	public void onClick() {
		for (ButtonListener listener : listeners) {
			listener.onPress(this);
		}
	}

}
