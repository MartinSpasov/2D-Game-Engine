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
import engine.physics.geometry.Rectangle;
import engine.resource.Resources;

public class TestGame extends Game implements KeyListener {
	
	private Font font;
	
	private Rectangle col1;
	private Rectangle col2;
	private Rectangle col3;
	private Rectangle col4;
	private Rectangle col5;
	
	private Player player;
	
	private Texture charWalk;
	private Texture levelTiles;
	
	private Texture uiPanelTex;
	private ImageBox uiPanel;
	
	private boolean showColliders;
	
	public static void main(String[] args) {
		new TestGame().run();
	}
	
	public TestGame() {
		super("Test", 500, 500);
	}

	@Override
	public void init() {

		charWalk = Resources.loadArrayTexture("player_walk.png", 2, 4, Texture.NEAREST_NEIGHBOR, 0);
		levelTiles = Resources.loadArrayTexture("tiles.png", 20, 15, Texture.NEAREST_NEIGHBOR, 0);
		uiPanelTex = Resources.loadTexture("Panel_Yellow.png", Texture.NEAREST_NEIGHBOR, 0);
		
		ArrayList<Tile> tiles = Resources.loadLevelTMX("map.tmx");	
		setScene(new TiledScene(tiles, levelTiles));
		getScene().setGravity(Scene.G);
		
		player = new Player(charWalk, this);
		player.getTransform().translate(0.5f, -10, 0);
		getScene().addObject(player);
		
		// ################# Camera Controls ##############################
		//GameObject cameraObject = new GameObject();
		//CameraController cameraControl = new CameraController(cameraObject);
		//getInput().registerKeyListener(cameraControl);
		//cameraObject.addComponent(cameraControl);
		//getScene().addObject(cameraObject);
		
		//getRenderSystem().getCamera().setTransform(cameraObject.getTransform());
		//cameraObject.getTransform().translate(0, -10, 3);



		font = Resources.loadFont("font2.png", "font2.fnt", 19, 32, getWindow(), 0);

		col1 = new Rectangle(14f, -12.5f, 29, 2);
		col2 = new Rectangle(11,-11,5,1);
		col3 = new Rectangle(15,-10,1,1);
		col4 = new Rectangle(18,-10,1,1);
		col5 = new Rectangle(22,-9,5,1);
		

		getCollisionSystem().addStaticCollider(col1);
		getCollisionSystem().addStaticCollider(col2);
		getCollisionSystem().addStaticCollider(col3);
		getCollisionSystem().addStaticCollider(col4);
		getCollisionSystem().addStaticCollider(col5);
		
		
		getScene().addBackground(new Background(Resources.loadTexture("bg.png", Texture.LINEAR, 0)));
		
		getInput().registerKeyListener(this);
		
		//statsBox = new Box(new Rectangle(1,1), new Color(64,64,64,85));
		uiPanel = new ImageBox(new Rectangle(0,-1 + (1 / (320/31.0f)),2,2 / (320 / 31.0f)), uiPanelTex);
		getUserInterface().addWidget(uiPanel);
	}
	
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		//getCollisionSystem().narrowScan();
		getSoundSystem().checkError(Game.logger);
		//System.out.println("USED MEMORY: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024)) + " MB");
		//getWindow().setTitle("FPS: " + getFps());
	}
	
	// Temporary override
	@Override
	public void destroy() {
		font.destroy();
		charWalk.destroy();
		levelTiles.destroy();
		uiPanelTex.destroy();
		super.destroy();
	}
	
	// Temporary override
	@Override
	public void render() {
		super.render();

		if (showColliders) {
			getRenderSystem().renderRectangle((Rectangle)player.getCollider().getShape(), Color.MAGENTA, false);
			getRenderSystem().renderRectangle(col1, Color.RED, false);
			getRenderSystem().renderRectangle(col2, Color.RED, false);
			getRenderSystem().renderRectangle(col3, Color.RED, false);
			getRenderSystem().renderRectangle(col4, Color.RED, false);
			getRenderSystem().renderRectangle(col5, Color.RED, false);
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
	}


}
