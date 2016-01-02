package engine.text;

import engine.Game;
import engine.graphics.ArrayTexture;
import engine.graphics.Texture;
import engine.graphics.animation.Animation;
import engine.object.GameObject;
import engine.object.component.AnimatorComponent;
import engine.object.component.PlatformerController2D;
import engine.object.component.SpriteComponent;
import engine.object.component.StateComponent;
import engine.resource.Resources;

public class TestGame extends Game {
	
	private Texture texture;
	private ArrayTexture texture2;

	public static void main(String[] args) {
		new TestGame().run();
	}
	
	public TestGame() {
		super("Test", 500, 500);
	}

	@Override
	public void init() {
		// Texture
		texture = Resources.loadTexture("megaman.png");
				
		for (int i = 0; i < 1000; i++) {
			GameObject obj = new GameObject();
			SpriteComponent comp = new SpriteComponent(obj, texture);
			obj.addComponent(comp);
			obj.getTransform().translate(0, 0, -i);
			getScene().addObject(obj);
		}
				
		GameObject obj = new GameObject();
		obj.getTransform().translate(1,0,-1);
		texture2 = Resources.loadArrayTexture("megaman_sheet.png", 1, 3);
		Animation animWalk = new Animation(new int[]{0,1,2}, new float[]{0.2f,0.2f,0.2f});
		Animation animIdle = new Animation(new int[]{0}, new float[]{0.0f});
				
		AnimatorComponent animComp = new AnimatorComponent(obj, "IDLE", animIdle, texture2);
		animComp.addAnimation("WALKING", animWalk);
				
		StateComponent states = new StateComponent(obj, "IDLE");
		states.registerListener(animComp);
				
		PlatformerController2D control = new PlatformerController2D(obj, states);
		getInput().registerKeyListener(control);
				
		obj.addComponent(animComp);
		obj.addComponent(states);
		obj.addComponent(control);
				
		getScene().addObject(obj);
		
		GameObject cameraObject = new GameObject();
		ControllerComponent cameraControl = new ControllerComponent(cameraObject);
		getInput().registerKeyListener(cameraControl);
		cameraObject.addComponent(cameraControl);
		
		getScene().addObject(cameraObject);
		
		getRenderSystem().getCamera().setTransform(cameraObject.getTransform());
	}
	
	// Temp method
	@Override
	public void destroy() {
		super.destroy();
		texture.destroy();
		texture2.destroy();
	}

}
