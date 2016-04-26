package engine.test;

import java.util.ArrayList;

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
import engine.object.component.ColliderComponent;
import engine.physics.geometry.Rectangle;
import engine.resource.Resources;
import engine.sound.Sound;

public class TestGame extends Game implements KeyListener {
	
	private ArrayList<Tile> tiles;
	
	private Font font;
	
	private Texture earthboundTileset;
	
	private Ness ness;
	private Texture nessSprite;
	private Rectangle nessCollider;
	
	private Rectangle col1;
	private Rectangle col2;
	private Rectangle col3;
	private Rectangle col4;
	
	private Texture test;
	
	private Sound testSound;
	
	private TiledScene sc;
	private Text testT;
	private Text testT2;

	private UserInterface ui;
	private Button button;
	
	public static void main(String[] args) {
		new TestGame().run();
	}
	
	public TestGame() {
		super("Test", 500, 500);
	}

	@Override
	public void init() {
		
		earthboundTileset = Resources.loadArrayTexture("earthbound.png", 53, 16, Texture.NEAREST_NEIGHBOR, 0);
		tiles = Resources.loadLevel("map2.png", "map2.txt");
		sc = new TiledScene(tiles, earthboundTileset);
		setScene(sc);
		
		GameObject cameraObject = new GameObject();
		ControllerComponent cameraControl = new ControllerComponent(cameraObject);
		getInput().registerKeyListener(cameraControl);
		cameraObject.addComponent(cameraControl);
		
		getScene().addObject(cameraObject);
		
		getRenderSystem().getCamera().setTransform(cameraObject.getTransform());
		cameraObject.getTransform().setZPos(3);

		testSound = Resources.loadSound("attack1.ogg");
		
		nessSprite = Resources.loadArrayTexture("ness.png", 2, 4, Texture.NEAREST_NEIGHBOR, 0);
		ness = new Ness(getInput(), nessSprite);
		ness.getTransform().translate(2, -2, 0);
		getScene().addObject(ness);

		font = Resources.loadFont("font2.png", "font2.fnt", 19, 32, getWindow(), 0);

		
		getInput().registerKeyListener(this);
		
		test = Resources.loadTexture("megaman.png", Texture.NEAREST_NEIGHBOR, 0);
		//GameObject obj = new GameObject();
		//obj.addComponent(new SpriteComponent(obj, test));
		//getScene().addObject(obj);
		
		//sc = new TiledScene(tiles, earthboundTileset);
		testT = new Text("Welcome to Java!", font);
		testT2 = new Text("Java is Powerful!", font);
		
		col1 = new Rectangle(-1, -4.5f, 1, 10);
		col2 = new Rectangle(20, -4.5f, 1, 10);
		col3 = new Rectangle(9.5f, 1, 20, 1);
		col4 = new Rectangle(9.5f, -10, 20, 1);
		
		getCollisionSystem().addStaticCollider(col1);
		getCollisionSystem().addStaticCollider(col2);
		getCollisionSystem().addStaticCollider(col3);
		getCollisionSystem().addStaticCollider(col4);
		
		nessCollider = new Rectangle(ness.getTransform().getXPos(), ness.getTransform().getYPos(),
				ness.getTransform().getXScale(), ness.getTransform().getYScale());
		ness.addComponent(new ColliderComponent(ness, nessCollider));
		
		ui = new UserInterface(this);
		button = new Button(new Rectangle(0,-0.5f,0.5f,0.18f), "lel", font);
		ui.addUserInterfaceComponent(button);
		button.registerButtonListener(new ButtonListener() {
			
			@Override
			public void onPress(Button source) {
				System.out.println("CLICK!");
			}
		});
	}
	
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
//		nessCollider.setX(ness.getTransform().getXPos());
//		nessCollider.setY(ness.getTransform().getYPos());
//		
//		if (nessCollider.intersects(col1) || nessCollider.intersects(col2) || nessCollider.intersects(col3) || nessCollider.intersects(col4)) {
//			//logger.log("INTERSECTING!");
//			ness.controller.intersectResponse();
//		}
		getCollisionSystem().narrowScan();
		getSoundSystem().checkError(Game.logger);
		getWindow().setTitle("FPS: " + getFps());
	}
	
	// Temporary override
	@Override
	public void destroy() {
		nessSprite.destroy();
		//earthboundTileset.destroy();
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
		//sc.render(getRenderSystem().getCamera());
		super.render();
		//getRenderSystem().renderDebugRectangles(tiles, Color.WHITE);
		getRenderSystem().renderRectangle(nessCollider, Color.MAGENTA, false);
		getRenderSystem().renderRectangle(col2, Color.RED, false);
		getRenderSystem().renderRectangle(col1, Color.RED, false);
		getRenderSystem().renderRectangle(col3, Color.RED, false);
		getRenderSystem().renderRectangle(col4, Color.RED, false);
		//getRenderSystem().renderDebugRectangles(getScene().getColliders(), Color.RED);
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
