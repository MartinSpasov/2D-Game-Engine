package engine.test;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import engine.Game;
import engine.Scene;
import engine.Tile;
import engine.TiledScene;
import engine.graphics.Background;
import engine.graphics.Color;
import engine.graphics.Texture;
import engine.graphics.text.Font;
import engine.object.GameObject;
import engine.physics.geometry.Rectangle;
import engine.resource.Resources;

public class TestGame extends Game {
	
	private Font font;
	
	private Rectangle col1;
	
	private Player player;
	
	private Texture charWalk;
	private Texture levelTiles;
	
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
		
		ArrayList<Tile> tiles = Resources.loadLevel("map3.png", "map3.txt");
		setScene(new TiledScene(tiles, levelTiles));
		getScene().setGravity(Scene.G);
		
		player = new Player(charWalk, getInput());
		player.getTransform().translate(0.5f, -10, 0);
		getScene().addObject(player);
		
		// ################# Camera Controls ##############################
		GameObject cameraObject = new GameObject();
		CameraController cameraControl = new CameraController(cameraObject);
		getInput().registerKeyListener(cameraControl);
		cameraObject.addComponent(cameraControl);
		getScene().addObject(cameraObject);
		
		getRenderSystem().getCamera().setTransform(cameraObject.getTransform());
		cameraObject.getTransform().translate(0, -10, 3);



		font = Resources.loadFont("font2.png", "font2.fnt", 19, 32, getWindow(), 0);

		col1 = new Rectangle(14f, -12.5f, 29, 2);
		

		getCollisionSystem().addStaticCollider(col1);
		
		
		getScene().addBackground(new Background(Resources.loadTexture("bg.png", Texture.LINEAR, 0)));

	}
	
	
	@Override
	public void tick(float delta) {
		super.tick(delta);
		getCollisionSystem().narrowScan();
		getSoundSystem().checkError(Game.logger);
		getWindow().setTitle("FPS: " + getFps());
	}
	
	// Temporary override
	@Override
	public void destroy() {
		font.destroy();
		charWalk.destroy();
		levelTiles.destroy();
		super.destroy();
	}
	
	// Temporary override
	@Override
	public void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		super.render();

		getRenderSystem().renderRectangle((Rectangle)player.getCollider().getShape(), Color.MAGENTA, false);
		getRenderSystem().renderRectangle(col1, Color.RED, false);

	}


}
