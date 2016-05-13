package engine.test;

import engine.Game;
import engine.graphics.Texture;
import engine.graphics.animation.Animation;
import engine.object.GameObject;
import engine.object.component.AnimatorComponent;
import engine.object.component.ColliderComponent;
import engine.object.component.RigidBodyComponent;
import engine.physics.geometry.Rectangle;

public class Player extends GameObject {
	
	public static final float animDelay = 0.16f;
	
	private ColliderComponent collider;
	
	public Player(Texture walkTex, Game game) {
		
		// ####################### Controls ####################################
		PlayerController controller = new PlayerController(this);
		game.getInput().registerKeyListener(controller);
		addComponent(controller);
		
		// ####################### Camera #######################################
		game.getRenderSystem().getCamera().getTransform().translate(0, -9.5f, 3);
		CameraComponent camera = new CameraComponent(this, game.getRenderSystem().getCamera());
		camera.setTrackY(false);
		//camera.setYOffset(1.25f);
		addComponent(camera);
		
		
		
		// ###################### Animations #####################################
		Animation walk = new Animation(new int[]{0,1,2,3}, new float[]{animDelay,animDelay,animDelay,animDelay});
		Animation idle = new Animation(new int[]{4}, new float[]{animDelay});
		Animation jump = new Animation(new int[]{7}, new float[]{animDelay});
		Animation sleepy = new Animation(new int[]{5,6}, new float[]{3f, animDelay});

		AnimatorComponent animator = new AnimatorComponent(this, "IDLE", idle, walkTex);
		
		animator.addAnimation("WALK", walk);
		animator.addAnimation("JUMP", jump);
		animator.addAnimation("SLEEPY", sleepy);

		
		addComponent(animator);
		
		// ####################### Physics ######################################
		collider = new ColliderComponent(this, new Rectangle(0.45f,1));
		addComponent(collider);
		addComponent(new RigidBodyComponent(this, 65));
		
	}
	
	public ColliderComponent getCollider() {
		return collider;
	}

}
