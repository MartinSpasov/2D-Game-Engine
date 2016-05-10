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
	
	public Player(Texture walk, Game game) {
		
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
		Animation walkLeft = new Animation(new int[]{4,5,6,7},new float[]{animDelay,animDelay,animDelay,animDelay});
		Animation walkRight = new Animation(new int[]{0,1,2,3},new float[]{animDelay,animDelay,animDelay,animDelay});
		Animation idleRight = new Animation(new int[]{8,9}, new float[]{3f,animDelay});
		Animation idleLeft = new Animation(new int[]{10,11}, new float[]{3f,animDelay});
		Animation jumpRight = new Animation(new int[]{12}, new float[]{animDelay});
		Animation jumpLeft = new Animation(new int[]{13}, new float[]{animDelay});

		AnimatorComponent animator = new AnimatorComponent(this, "IDLE_RIGHT", idleRight, walk);
		animator.addAnimation("WALK_RIGHT", walkRight);
		animator.addAnimation("WALK_LEFT", walkLeft);
		//animator.addAnimation("IDLE_RIGHT", idleRight);
		animator.addAnimation("IDLE_LEFT", idleLeft);
		animator.addAnimation("JUMP_RIGHT", jumpRight);
		animator.addAnimation("JUMP_LEFT", jumpLeft);
		//animator.receiveMessage("PAUSEANIM", true);
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
