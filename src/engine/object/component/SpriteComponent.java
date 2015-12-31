package engine.object.component;

import engine.Game;
import engine.graphics.Texture;
import engine.object.GameObject;

public class SpriteComponent extends ObjectComponent {

	private Texture texture;
	private boolean horizontalFlip;
	private boolean verticalFlip;
	
	public SpriteComponent(GameObject parentObject, Texture texture) {
		super(parentObject);
		this.texture = texture;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public boolean isHorizontallyFlipped() {
		return horizontalFlip;
	}
	
	public boolean isVerticallyFlipped() {
		return verticalFlip;
	}
	
	public void setHorizontalFlip(boolean flipped) {
		horizontalFlip = flipped;
	}
	
	public void setVerticalFlip(boolean flipped) {
		verticalFlip = flipped;
	}

	@Override
	public void tick(float delta, Game game) {
		game.getRenderSystem().addSpriteComponent(this);
	}
}
