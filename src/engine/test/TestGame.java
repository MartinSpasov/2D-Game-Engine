package engine.test;

import engine.Game;
import engine.graphics.ArrayTexture;
import engine.graphics.Color;
import engine.graphics.Texture;
import engine.graphics.animation.Animation;
import engine.graphics.ui.UserInterface;
import engine.graphics.ui.component.Button;
import engine.graphics.ui.component.ButtonListener;
import engine.object.GameObject;
import engine.object.component.AnimatorComponent;
import engine.object.component.PlatformerController2D;
import engine.object.component.SpriteComponent;
import engine.object.component.StateComponent;
import engine.physics.geometry.Rectangle;
import engine.resource.Resources;

public class TestGame extends Game implements ButtonListener {
	
	private Texture texture;
	private ArrayTexture texture2;
	
	private UserInterface ui;

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
		
		animWalk.setPaused(true);
		AnimatorComponent animComp = new AnimatorComponent(obj, "WALKING", animWalk, texture2);
				
		StateComponent states = new StateComponent(obj, "WALKING");
				
		PlatformerController2D control = new PlatformerController2D(obj, states, animComp);
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
		cameraObject.getTransform().setZPos(3);
		
		ui = new UserInterface(this);
		getInput().registerMouseButtonListener(ui);
		getInput().registerMouseMovementListener(ui);
		
		Button button = new Button(new Rectangle(-0.25f, -0.3f, 0.5f, 0.15f), "Test");
		button.registerButtonListener(this);
		button.setBackgroundColor(red);
		ui.addUserInterfaceComponent(button);
	}
	
	// Temporary override
	@Override
	public void destroy() {
		super.destroy();
		texture.destroy();
		texture2.destroy();
	}
	
	// Temporary override
	@Override
	public void tick(float delta) {
		super.tick(delta);
		getWindow().setTitle("FPS: " + getFps());
	}
	
	@Override
	public void render() {
		super.render();
		ui.tick(0.16f);
	}

	private boolean toggled = false;
	private Color red = new Color(1.0f,0.0f,0.0f,0.0f);
	private Color green = new Color(0.0f,1.0f,0.0f,0.0f);
	
	@Override
	public void onPress(Button button) {
		if (toggled) {
			button.setBackgroundColor(red);
		}
		else {
			button.setBackgroundColor(green);
		}
		toggled = !toggled;
	}

}
