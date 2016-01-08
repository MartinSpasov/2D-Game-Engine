package engine.test;

import java.util.ArrayList;

import engine.Game;
import engine.Tile;
import engine.graphics.ArrayTexture;
import engine.graphics.Color;
import engine.graphics.Texture;
import engine.graphics.animation.Animation;
import engine.graphics.text.Font;
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
	private ArrayTexture mapTexture;
	
	private UserInterface ui;
	
	private ArrayList<Tile> tiles;
	
	private Font font;

	public static void main(String[] args) {
		new TestGame().run();
	}
	
	public TestGame() {
		super("Test", 500, 500);
	}

	@Override
	public void init() {
		// Texture
		texture = Resources.loadTexture("megaman.png", Texture.NEAREST_NEIGHBOR);
				
		for (int i = 0; i < 1000; i++) {
			GameObject obj = new GameObject();
			SpriteComponent comp = new SpriteComponent(obj, texture);
			obj.addComponent(comp);
			obj.getTransform().translate(0, 0, -i);
			getScene().addObject(obj);
		}
				
		GameObject obj = new GameObject();
		obj.getTransform().translate(1,0,-1);
		texture2 = Resources.loadArrayTexture("megaman_sheet.png", 1, 4, Texture.NEAREST_NEIGHBOR);
		Animation animWalk = new Animation(new int[]{1,2,3}, new float[]{0.16f,0.16f,0.16f});
		//Animation animWalk = new Animation(new int[]{1,2,3}, new float[]{1f,1f,1f});
		
		animWalk.setPaused(true);
		animWalk.setCurrentFrame(0);
		AnimatorComponent animComp = new AnimatorComponent(obj, "WALKING", animWalk, texture2);
				
		StateComponent states = new StateComponent(obj, "WALKING");
				
		PlatformerController2D control = new PlatformerController2D(obj);
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
		
		mapTexture = Resources.loadArrayTexture("lava_level.png", 5, 4, Texture.NEAREST_NEIGHBOR);
		tiles = Resources.loadLevel("map.png", "map.txt");

		font = Resources.loadFont("font1.png", "font1.fnt", 19, 32, getWindow());
		
		Button button = new Button(new Rectangle(0.0f, -0.45f, 0.5f, 0.15f), "Test", font);
		button.setTextColor(Color.GREEN);
		button.registerButtonListener(this);
		button.setBackgroundColor(Color.RED);
		ui.addUserInterfaceComponent(button);

	}
	
	// Temporary override
	@Override
	public void destroy() {
		super.destroy();
		texture.destroy();
		texture2.destroy();
		mapTexture.destroy();
		font.destroy();
	}
	
	// Temporary override
	@Override
	public void render() {
		super.render();
		getRenderSystem().renderLevel(tiles, mapTexture);
		ui.tick(0.16f);
		getRenderSystem().renderText("FPS: " + getFps(), font, -0.92f, 0.92f, Color.WHITE);
	}

	private boolean toggled = false;
	
	@Override
	public void onPress(Button button) {
		if (toggled) {
			button.setBackgroundColor(Color.RED);
			button.setTextColor(Color.GREEN);
		}
		else {
			button.setBackgroundColor(Color.GREEN);
			button.setTextColor(Color.RED);
		}
		toggled = !toggled;
	}

}
