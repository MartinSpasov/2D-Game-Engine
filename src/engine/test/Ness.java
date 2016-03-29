package engine.test;

import engine.graphics.Texture;
import engine.graphics.animation.Animation;
import engine.input.Input;
import engine.object.GameObject;
import engine.object.component.AnimatorComponent;

public class Ness extends GameObject {

	public static final float animDelay = 0.16f;
	
	public Ness(Input input, Texture nessSprite) {
		super();
		
		
		// ####################### Controls ####################################
		NessController controller = new NessController(this);
		input.registerKeyListener(controller);
		addComponent(controller);
		
		
		// ###################### Animations #####################################
		Animation walkDown = new Animation(new int[]{0,1},new float[]{animDelay,animDelay});
		Animation walkRight = new Animation(new int[]{2,3},new float[]{animDelay,animDelay});
		Animation walkUp = new Animation(new int[]{4,5},new float[]{animDelay,animDelay});
		Animation walkLeft = new Animation(new int[]{6,7},new float[]{animDelay,animDelay});
		
		AnimatorComponent animator = new AnimatorComponent(this, "WALK_DOWN", walkDown, nessSprite);
		animator.addAnimation("WALK_RIGHT", walkRight);
		animator.addAnimation("WALK_UP", walkUp);
		animator.addAnimation("WALK_LEFT", walkLeft);
		animator.receiveMessage("PAUSEANIM", true);
		addComponent(animator);
	}
	
}
