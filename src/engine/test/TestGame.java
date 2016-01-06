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
		
		animWalk.setPaused(true);
		animWalk.setCurrentFrame(0);
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
		
		mapTexture = Resources.loadArrayTexture("lava_level.png", 5, 4, Texture.NEAREST_NEIGHBOR);
		tiles = Resources.loadLevel("map.png", "map.txt");
		
//		font = new Font(Resources.loadArrayTexture("samplefont.png", 14, 16), 0);
//		font.addCharacterMapping(' ', 0);
//		font.addCharacterMapping('!', 1);
//		font.addCharacterMapping('"', 2);
//		font.addCharacterMapping('#', 3);
//		font.addCharacterMapping('$', 4);
//		font.addCharacterMapping('%', 5);
//		font.addCharacterMapping('&', 6);
//		font.addCharacterMapping('\'', 7);
//		font.addCharacterMapping('(', 8);
//		font.addCharacterMapping(')', 9);
//		font.addCharacterMapping('*', 10);
//		font.addCharacterMapping('+', 11);
//		font.addCharacterMapping(',', 12);
//		font.addCharacterMapping('-', 13);
//		font.addCharacterMapping('.', 14);
//		font.addCharacterMapping('/', 15);
//		font.addCharacterMapping('0', 16);
//		font.addCharacterMapping('1', 17);
//		font.addCharacterMapping('2', 18);
//		font.addCharacterMapping('3', 19);
//		font.addCharacterMapping('4', 20);
//		font.addCharacterMapping('5', 21);
//		font.addCharacterMapping('6', 22);
//		font.addCharacterMapping('7', 23);
//		font.addCharacterMapping('8', 24);
//		font.addCharacterMapping('9', 25);
//		font.addCharacterMapping(':', 26);
//		font.addCharacterMapping(';', 27);
//		font.addCharacterMapping('<', 28);
//		font.addCharacterMapping('=', 29);
//		font.addCharacterMapping('>', 30);
//		font.addCharacterMapping('?', 31);
//		font.addCharacterMapping('@', 32);
//		font.addCharacterMapping('A', 33);
//		font.addCharacterMapping('B', 34);
//		font.addCharacterMapping('C', 35);
//		font.addCharacterMapping('D', 36);
//		font.addCharacterMapping('E', 37);
//		font.addCharacterMapping('F', 38);
//		font.addCharacterMapping('G', 39);
//		font.addCharacterMapping('H', 40);
//		font.addCharacterMapping('I', 41);
//		font.addCharacterMapping('J', 42);
//		font.addCharacterMapping('K', 43);
//		font.addCharacterMapping('L', 44);
//		font.addCharacterMapping('M', 45);
//		font.addCharacterMapping('N', 46);
//		font.addCharacterMapping('O', 47);
//		font.addCharacterMapping('P', 48);
//		font.addCharacterMapping('Q', 49);
//		font.addCharacterMapping('R', 50);
//		font.addCharacterMapping('S', 51);
//		font.addCharacterMapping('T', 52);
//		font.addCharacterMapping('U', 53);
//		font.addCharacterMapping('V', 54);
//		font.addCharacterMapping('W', 55);
//		font.addCharacterMapping('X', 56);
//		font.addCharacterMapping('Y', 57);
//		font.addCharacterMapping('Z', 58);
//		font.addCharacterMapping('[', 59);
//		font.addCharacterMapping('\\', 60);
//		font.addCharacterMapping(']', 61);
//		font.addCharacterMapping('^', 62);
//		font.addCharacterMapping('_', 63);
//		font.addCharacterMapping('`', 64);
//		font.addCharacterMapping('a', 65);
//		font.addCharacterMapping('b', 66);
//		font.addCharacterMapping('c', 67);
//		font.addCharacterMapping('d', 68);
//		font.addCharacterMapping('e', 69);
//		font.addCharacterMapping('f', 70);
//		font.addCharacterMapping('g', 71);
//		font.addCharacterMapping('h', 72);
//		font.addCharacterMapping('i', 73);
//		font.addCharacterMapping('j', 74);
//		font.addCharacterMapping('k', 75);
//		font.addCharacterMapping('l', 76);
//		font.addCharacterMapping('m', 77);
//		font.addCharacterMapping('n', 78);
//		font.addCharacterMapping('o', 79);
//		font.addCharacterMapping('p', 80);
//		font.addCharacterMapping('q', 81);
//		font.addCharacterMapping('r', 82);
//		font.addCharacterMapping('s', 83);
//		font.addCharacterMapping('t', 84);
//		font.addCharacterMapping('u', 85);
//		font.addCharacterMapping('v', 86);
//		font.addCharacterMapping('w', 87);
//		font.addCharacterMapping('x', 88);
//		font.addCharacterMapping('y', 89);
//		font.addCharacterMapping('z', 90);
//		font.addCharacterMapping('{', 91);
//		font.addCharacterMapping('|', 92);
//		font.addCharacterMapping('}', 93);
//		font.addCharacterMapping('~', 94);
		
		Button button = new Button(new Rectangle(0.0f, -0.45f, 0.5f, 0.15f), "Test", font);
		button.setTextColor(Color.GREEN);
		button.registerButtonListener(this);
		button.setBackgroundColor(Color.RED);
		ui.addUserInterfaceComponent(button);

		font = Resources.loadFont("font1.png", "font1.fnt", 19, 32, getWindow());
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
