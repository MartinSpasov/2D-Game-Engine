package engine.object.component;

import engine.graphics.RenderSystem;
import engine.object.GameObject;

public abstract class RenderableComponent extends ObjectComponent {

	public RenderableComponent(GameObject parentObject) {
		super(parentObject);
	}

	public abstract void render(RenderSystem renderer);

}
