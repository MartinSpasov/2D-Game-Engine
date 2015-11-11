package engine.object.component;

import java.util.HashMap;

import engine.graphics.animation.Animation;
import engine.object.GameObject;

public class AnimatorComponent extends ObjectComponent implements StateListener {

	private HashMap<String, Animation> animations;
	private Animation currentAnimation;
	
	public AnimatorComponent(GameObject parentObject, String currentState, Animation currentAnimation) {
		super(parentObject);
		animations = new HashMap<String, Animation>();
		animations.put(currentState, currentAnimation);
		this.currentAnimation = currentAnimation;
	}
	
	public void changeState(String state) {
		currentAnimation = animations.get(state);
		//currentAnimation.reset();
	}
	
	public void addAnimation(String state, Animation animation) {
		// TODO Warn user if they are replacing an animation
		animations.put(state, animation);
	}
	
	public void removeAnimation(String state) {
		animations.remove(state);
	}
	
	public int getCurrentFrame() {
		return currentAnimation.getCurrentFrame();
	}

	@Override
	public void receiveStateChange(String state) {
		changeState(state);
	}
	
	@Override
	public void tick(float delta) {
		currentAnimation.tick(delta);
	}

}
