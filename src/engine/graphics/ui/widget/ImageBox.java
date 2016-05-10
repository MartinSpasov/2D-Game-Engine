package engine.graphics.ui.widget;

import engine.graphics.RenderSystem;
import engine.graphics.Texture;
import engine.physics.geometry.Rectangle;

public class ImageBox extends Widget {
	
	private Texture image;

	public ImageBox(Rectangle bounds, Texture image) {
		super(bounds);
		this.image = image;
	}

	@Override
	public void render(RenderSystem renderer) {
		renderer.renderUISprite(getBounds(), image, false, false);
	}

	@Override
	public void onClick() {
		
	}

}
