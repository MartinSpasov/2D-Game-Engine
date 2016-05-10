package engine.test;

import engine.Game;
import engine.graphics.Camera;
import engine.object.GameObject;
import engine.object.component.ObjectComponent;

public class CameraComponent extends ObjectComponent{

	private Camera camera;
	private float xOffset;
	private float yOffset;
	
	private boolean trackX;
	private boolean trackY;
	
	public CameraComponent(GameObject parentObject, Camera camera) {
		super(parentObject);
		this.camera = camera;
		trackX = true;
		trackY = true;
	}

	@Override
	public void tick(float delta, Game game) {
		if (trackX)
			camera.getTransform().setXPos(xOffset + getParentObject().getTransform().getXPos());
		
		if (trackY)
			camera.getTransform().setYPos(yOffset + getParentObject().getTransform().getYPos());
	}

	public float getXOffset() {
		return xOffset;
	}

	public void setXOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getYOffset() {
		return yOffset;
	}

	public void setYOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	public boolean isTrackingX() {
		return trackX;
	}

	public void setTrackX(boolean trackX) {
		this.trackX = trackX;
	}

	public boolean isTrackingY() {
		return trackY;
	}

	public void setTrackY(boolean trackY) {
		this.trackY = trackY;
	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		
	}

}
