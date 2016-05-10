package engine.graphics.ui.widget;

import engine.graphics.Color;
import engine.graphics.RenderSystem;
import engine.physics.geometry.Rectangle;

public class Box extends Widget {

	public Box(Rectangle bounds, Color color) {
		super(bounds, color);
		
	}

	@Override
	public void render(RenderSystem renderer) {
		renderer.renderUIRectangle(getBounds(), getBackgroundColor());
	}

	@Override
	public void onClick() {
		
	}

}
