package engine.test;

import java.util.ArrayList;

import engine.Game;
import engine.Scene;
import engine.Tile;
import engine.TiledScene;
import engine.graphics.Background;
import engine.graphics.Color;
import engine.graphics.Texture;
import engine.graphics.text.Font;
import engine.graphics.ui.widget.ImageBox;
import engine.input.Input;
import engine.input.KeyListener;
import engine.math.Vector2f;
import engine.physics.geometry.Rectangle;
import engine.resource.Resources;

public class TestGame extends Game implements KeyListener {
	
	// Resources
	private Font font;
	private Texture charTex;
	private Texture levelTiles;
	private Texture uiPanelTex;
	
	// Collision
	private ArrayList<Rectangle> level1Colliders;
	private ArrayList<Rectangle> level2Colliders;
	private boolean showColliders;
	
	// Level
	private boolean switchedLevels;
	private TiledScene level2;
	
	
	private Player player;
	private Vector2f respawnLoc;
	
	private ImageBox uiPanel;
	
	public static void main(String[] args) {
		new TestGame().run();
	}
	
	public TestGame() {
		super("Test", 500, 500);
	}

	@Override
	public void init() {
		getInput().registerKeyListener(this);
		level1Colliders = new ArrayList<Rectangle>();
		level2Colliders = new ArrayList<Rectangle>();
		
		// Load resources
		font = Resources.loadFont("font2.png", "font2.fnt", 19, 32, getWindow(), 0);
		charTex = Resources.loadArrayTexture("player_walk.png", 2, 4, Texture.NEAREST_NEIGHBOR, 0);
		levelTiles = Resources.loadArrayTexture("tiles.png", 20, 15, Texture.NEAREST_NEIGHBOR, 0);
		uiPanelTex = Resources.loadTexture("Panel_Yellow.png", Texture.NEAREST_NEIGHBOR, 0);
		
		// Load levels
		ArrayList<Tile> tiles = Resources.loadLevelTMX("map.tmx");	
		setScene(new TiledScene(tiles, levelTiles));
		getScene().setGravity(Scene.G);
		
		ArrayList<Tile> tiles2 = Resources.loadLevelTMX("map2.tmx");
		level2 = new TiledScene(tiles2, levelTiles);
		level2.setGravity(Scene.G);
		
		Background bg = new Background(Resources.loadTexture("bg.png", Texture.LINEAR, 0));
		getScene().addBackground(bg);
		level2.addBackground(bg);
		
		player = new Player(charTex, this);
		player.getTransform().translate(0.5f, -10, 0);
		respawnLoc = new Vector2f(0.5f, -10);
		getScene().addObject(player);
		


		initColliders();
		for (Rectangle col : level1Colliders) {
			getCollisionSystem().addStaticCollider(col);
		}

		
		
		// User interface
		//statsBox = new Box(new Rectangle(1,1), new Color(64,64,64,85));
		uiPanel = new ImageBox(new Rectangle(0,-1 + (1 / (320/31.0f)),2,2 / (320 / 31.0f)), uiPanelTex);
		getUserInterface().addWidget(uiPanel);
		

		
		// ################# Camera Controls ##############################
		//GameObject cameraObject = new GameObject();
		//CameraController cameraControl = new CameraController(cameraObject);
		//getInput().registerKeyListener(cameraControl);
		//cameraObject.addComponent(cameraControl);
		//getScene().addObject(cameraObject);
		
		//getRenderSystem().getCamera().setTransform(cameraObject.getTransform());
		//cameraObject.getTransform().translate(0, -10, 3);
	}
	
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		getSoundSystem().checkError(Game.logger);
		//System.out.println("USED MEMORY: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024)) + " MB");
		//getWindow().setTitle("FPS: " + getFps());
	}
	
	// Temporary override
	@Override
	public void destroy() {
		font.destroy();
		charTex.destroy();
		levelTiles.destroy();
		uiPanelTex.destroy();
		super.destroy();
	}
	
	// Temporary override
	@Override
	public void render() {
		super.render();

		if (showColliders) {
			renderColliders();
		}

	}

	@Override
	public void onKey(long window, int key, int scancode, int action, int mods) {
		if (key == Input.KEY_F2 && action == Input.PRESS) {
			showColliders = !showColliders;
		}
		if (key == Input.KEY_F1 && action == Input.PRESS) {
			uiPanel.setVisible(!uiPanel.isVisible());
		}
		if (key == Input.KEY_ESCAPE && action == Input.PRESS) {
			shutdown();
		}
		if (key == Input.KEY_R && action == Input.PRESS) {
			player.getTransform().setXPos(respawnLoc.x);
			player.getTransform().setYPos(respawnLoc.y);
			player.getTransform().setZPos(0);
		}
		if (key == Input.KEY_KP_0 && action == Input.PRESS && !switchedLevels) {
			getCollisionSystem().clearStaticColliders();
			
			CameraComponent cam = player.getComponent(CameraComponent.class);
			cam.setTrackY(true);
			cam.setYOffset(1.5f);

			player.getTransform().setYPos(-26);
			player.getTransform().setXPos(6);
			respawnLoc.x = 6;
			respawnLoc.y = -26;
			level2.addObject(player);
			setScene(level2);
			switchedLevels = true;
			
			for (Rectangle col : level2Colliders) {
				getCollisionSystem().addStaticCollider(col);
			}
		}
	}
	
	private void renderColliders() {
		getRenderSystem().renderRectangle((Rectangle)player.getCollider().getShape(), Color.MAGENTA, false);
		if (switchedLevels) {
			for (Rectangle col : level2Colliders) {
				getRenderSystem().renderRectangle(col, Color.RED, false);
			}
		}
		else {
			for (Rectangle col : level1Colliders) {
				getRenderSystem().renderRectangle(col, Color.RED, false);
			}
		}
	}
	
	private void initColliders() {
		level1Colliders.add(new Rectangle(14f, -12.5f, 29, 2));
		level1Colliders.add(new Rectangle(11,-11,5,1));
		level1Colliders.add(new Rectangle(15,-10,1,1));
		level1Colliders.add(new Rectangle(18,-10,1,1));
		level1Colliders.add(new Rectangle(22,-9,5,1));
		
		level2Colliders.add(new Rectangle(11,-28.5f,15,2));
		level2Colliders.add(new Rectangle(1.5f, -27, 4,5));
		level2Colliders.add(new Rectangle(19.5f,-28,2,3));
		level2Colliders.add(new Rectangle(25.5f, -27, 10,3));
		level2Colliders.add(new Rectangle(32,-26,1,1));
		level2Colliders.add(new Rectangle(34.5f,-27,2,3));
		level2Colliders.add(new Rectangle(40,-28.5f,9,2));
		level2Colliders.add(new Rectangle(24.5f,-25,2,1));
		level2Colliders.add(new Rectangle(30,-21,3,5));
		level2Colliders.add(new Rectangle(33,-22,3,3));

	}
	
	


}
