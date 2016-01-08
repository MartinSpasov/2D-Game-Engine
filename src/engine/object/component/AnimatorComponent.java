package engine.object.component;

import java.util.HashMap;

import engine.Game;
import engine.graphics.ArrayTexture;
import engine.graphics.animation.Animation;
import engine.object.GameObject;

public class AnimatorComponent extends ObjectComponent {
	
	private ArrayTexture texture;
	private HashMap<String, Animation> animations;
	private Animation currentAnimation;
	private boolean horizontalFlip;
	
	public AnimatorComponent(GameObject parentObject, String currentState, Animation currentAnimation, ArrayTexture texture) {
		super(parentObject);
		this.texture = texture;
		animations = new HashMap<String, Animation>();
		animations.put(currentState, currentAnimation);
		this.currentAnimation = currentAnimation;
	}
	
	public void changeState(String state) {
		currentAnimation = animations.get(state);
		currentAnimation.reset();
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
	
	public ArrayTexture getTexture() {
		return texture;
	}
	
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}
	
	public boolean isHorizontallyFlipped() {
		return horizontalFlip;
	}
	
	public void setHorizontalFlip(boolean horizontalFlip) {
		this.horizontalFlip = horizontalFlip;
	}

	@Override
	public void tick(float delta, Game game) {
		currentAnimation.tick(delta);
		game.getRenderSystem().addAnimatorComponent(this);
	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		if (message.equals("STATECHANGE") && param instanceof String) {
			if (animations.containsKey((String)param)) {
				changeState((String)param);
			}
		}
		else if (message.equals("PAUSEANIM") && param instanceof Boolean) {
			currentAnimation.setPaused((boolean)param);
		}
		else if (message.equals("RESETANIM")) {
			currentAnimation.reset();
		}
		else if (message.equals("FLIPSPRITE") && param instanceof Boolean) {
			horizontalFlip = (boolean)param;
		}
		else if (message.equals("SETCURRENTFRAME") && param instanceof Integer) {
			currentAnimation.setCurrentFrame(0);
		}
	}


}
