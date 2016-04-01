package engine.test;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import engine.Game;
import engine.Tile;
import engine.TiledScene;
import engine.graphics.Color;
import engine.graphics.Texture;
import engine.graphics.text.Font;
import engine.graphics.text.Text;
import engine.graphics.ui.UserInterface;
import engine.graphics.ui.component.Button;
import engine.graphics.ui.component.ButtonListener;
import engine.input.KeyListener;
import engine.object.GameObject;
import engine.physics.geometry.Rectangle;
import engine.resource.Resources;
import engine.sound.Sound;

public class TestGame extends Game implements KeyListener {
	
	private ArrayList<Tile> tiles;
	private ArrayList<Rectangle> collision;
	
	private Font font;
	
	private Texture earthboundTileset;
	
	private Ness ness;
	private Texture nessSprite;
	
	private Texture test;
	
	private Sound testSound;
	
	private TiledScene sc;
	private Text testT;
	private Text testT2;
	
	private Button testB;
	private UserInterface ui;
	
	private Random rand;

	public static void main(String[] args) {
		new TestGame().run();
	}
	
	public TestGame() {
		super("Test", 500, 500);
	}

	@Override
	public void init() {
		
		GameObject cameraObject = new GameObject();
		ControllerComponent cameraControl = new ControllerComponent(cameraObject);
		getInput().registerKeyListener(cameraControl);
		cameraObject.addComponent(cameraControl);
		
		getScene().addObject(cameraObject);
		
		getRenderSystem().getCamera().setTransform(cameraObject.getTransform());
		cameraObject.getTransform().setZPos(3);

		testSound = Resources.loadSound("attack1.ogg");
		
		nessSprite = Resources.loadArrayTexture("ness.png", 2, 4, Texture.NEAREST_NEIGHBOR);
		ness = new Ness(getInput(), nessSprite);
		getScene().addObject(ness);
		
		earthboundTileset = Resources.loadArrayTexture("earthbound.png", 53, 16, Texture.NEAREST_NEIGHBOR);
		tiles = Resources.loadLevel("map2.png", "map2.txt");

		font = Resources.loadFont("font2.png", "font2.fnt", 19, 32, getWindow());
		
		collision = new ArrayList<Rectangle>();
		collision.add(new Rectangle(0,-5f,1f,800f));
		getCollisionSystem().addStaticCollider(collision.get(0));
		
		getInput().registerKeyListener(this);
		
		test = Resources.loadTexture("megaman.png", Texture.NEAREST_NEIGHBOR);
		//GameObject obj = new GameObject();
		//obj.addComponent(new SpriteComponent(obj, test));
		//getScene().addObject(obj);
		
		sc = new TiledScene(tiles, earthboundTileset);
		testT = new Text("Welcome to Jawa!", font);
		testT2 = new Text("Jawa is Powerful!", font);
		
		ui = new UserInterface(this);
		getInput().registerMouseButtonListener(ui);
		getInput().registerMouseMovementListener(ui);
		rand = new Random();
		testB = new Button(new Rectangle(0.5f,0.2f), "Test", font);
		testB.registerButtonListener(new ButtonListener() {

			@Override
			public void onPress(Button source) {
				switch(rand.nextInt(6)) {
				case 0:
					source.setBackgroundColor(Color.BLUE);
					break;
				case 1:
					source.setBackgroundColor(Color.CYAN);
					break;
				case 2:
					source.setBackgroundColor(Color.GREEN);
					break;
				case 3:
					source.setBackgroundColor(Color.MAGENTA);
					break;
				case 4:
					source.setBackgroundColor(Color.RED);
					break;
				case 5:
					source.setBackgroundColor(Color.YELLOW);
					break;
				default:
					source.setBackgroundColor(Color.WHITE);
				}
			}
			
		});
		ui.addUserInterfaceComponent(testB);
	}
	
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		getSoundSystem().checkError(Game.logger);
	}
	
	// Temporary override
	@Override
	public void destroy() {
		nessSprite.destroy();
		earthboundTileset.destroy();
		font.destroy();
		testSound.destroy();
		test.destroy();
		sc.destroy();
		testT.destroy();
		testT2.destroy();
		super.destroy();
	}
	
	// Temporary override
	@Override
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		sc.render(getRenderSystem().getCamera());
		super.render();
		//getRenderSystem().renderDebugRectangles(tiles, Color.WHITE);
		//getRenderSystem().renderText("FPS: " + getFps(), font, -0.92f, 0.92f, Color.WHITE);
		getRenderSystem().renderText(testT, font, -0.92f, 0.92f, Color.WHITE);
		getRenderSystem().renderText(testT2, font, -0.92f, -0.92f, Color.WHITE);
		ui.tick(16f);
	}

	@Override
	public void onKey(long window, int key, int scancode, int action, int mods) {
		if (key == GLFW.GLFW_KEY_LEFT_ALT && action == GLFW.GLFW_PRESS) {
			getSoundSystem().playSound(testSound);
		}
	}

}
