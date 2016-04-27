package engine.test;

import engine.graphics.Texture;
import engine.graphics.animation.Animation;
import engine.input.Input;
import engine.object.GameObject;
import engine.object.component.AnimatorComponent;
import engine.object.component.ColliderComponent;
import engine.object.component.RigidBodyComponent;
import engine.physics.geometry.Rectangle;

public class Player extends GameObject {
	
	public static final float animDelay = 0.16f;
	
	private ColliderComponent collider;
	
	public Player(Texture walk, Input input) {
		
		// ####################### Controls ####################################
		PlayerController controller = new PlayerController(this);
		input.registerKeyListener(controller);
		addComponent(controller);
		
		
		
		// ###################### Animations #####################################
		Animation walkLeft = new Animation(new int[]{4,5,6,7},new float[]{animDelay,animDelay,animDelay,animDelay});
		Animation walkRight = new Animation(new int[]{0,1,2,3},new float[]{animDelay,animDelay,animDelay,animDelay});

		AnimatorComponent animator = new AnimatorComponent(this, "WALK_RIGHT", walkRight, walk);
		animator.addAnimation("WALK_RIGHT", walkRight);
		animator.addAnimation("WALK_LEFT", walkLeft);
		animator.receiveMessage("PAUSEANIM", true);
		addComponent(animator);
		
		// ####################### Physics ######################################
		collider = new ColliderComponent(this, new Rectangle(0.75f,1));
		addComponent(collider);
		addComponent(new RigidBodyComponent(this, 65));
		
	}
	
	public ColliderComponent getCollider() {
		return collider;
	}

}
