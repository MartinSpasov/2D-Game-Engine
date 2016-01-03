package engine.graphics.ui.component;

import java.util.ArrayList;

import engine.Game;
import engine.physics.geometry.Rectangle;

public class Button extends UserInterfaceComponent {

	private String text;
	
	private ArrayList<ButtonListener> listeners;
	
	public Button(Rectangle bounds, String text) {
		super(bounds);
		this.text = text;
		listeners = new ArrayList<ButtonListener>();
	}
	
	public void registerButtonListener(ButtonListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void tick(float delta, Game game) {
		game.getRenderSystem().render(getBounds(), getBackgroundColor());
	}

	@Override
	public void onClick() {
		for (ButtonListener listener : listeners) {
			listener.onPress(this);
		}
	}

}
