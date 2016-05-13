package engine.test;

import engine.Game;
import engine.input.Input;
import engine.input.KeyListener;
import engine.math.Vector3f;
import engine.object.GameObject;
import engine.object.State;
import engine.object.StateTransition;
import engine.object.component.ObjectComponent;

public class PlayerController extends ObjectComponent implements KeyListener {
	
	public static final float SPEED = 4f;
	
	private boolean left;
	private boolean right;
	private boolean grounded;
	private boolean jumping;
	
	private State currentState;
	
	private State idle;
	private State move;
	private State air;
	
	private int direction;
	private boolean flipped;
	
	private float sleepyCounter;
	private boolean sleepy;
	
	public PlayerController(GameObject parentObject) {
		super(parentObject);
		
		idle = new State() {

			@Override
			public void onEnter() {
				getParentObject().broadcastMessage("STATECHANGE", "IDLE");
				sleepy = false;
			}

			@Override
			public void onExit() {}

			@Override
			public void tick(float delta, Game game) {
				if (!sleepy) {
					sleepyCounter += delta;
					
					if (sleepyCounter >= 5) {
						sleepyCounter = 0;
						sleepy = true;
						getParentObject().broadcastMessage("STATECHANGE", "SLEEPY");
					}
				}
			}
			
		};
		
		move = new State() {

			@Override
			public void onEnter() {
				getParentObject().broadcastMessage("STATECHANGE", "WALK");
			}

			@Override
			public void onExit() {}

			@Override
			public void tick(float delta, Game game) {
				direction = 0;
				
				if (left)
					direction--;
				if (right)
					direction++;
				
				getParentObject().getTransform().translate(delta * SPEED * direction, 0, 0);
				
				if (direction < 0 && !flipped) {
					getParentObject().broadcastMessage("FLIPSPRITE", true);
					flipped = true;
				}
				if (direction > 0 && flipped) {
					getParentObject().broadcastMessage("FLIPSPRITE", false);
					flipped = false;
				}

			}
			
		};
		
		air  = new State() {

			@Override
			public void onEnter() {
				getParentObject().broadcastMessage("STATECHANGE", "JUMP");
				grounded = false;
			}

			@Override
			public void onExit() {
				jumping = false;
			}

			@Override
			public void tick(float delta, Game game) {
				direction = 0;
				
				if (left)
					direction--;
				if (right)
					direction++;
				
				getParentObject().getTransform().translate(delta * SPEED * direction, 0, 0);
				
				if (direction < 0 && !flipped) {
					getParentObject().broadcastMessage("FLIPSPRITE", true);
					flipped = true;
				}
				if (direction > 0 && flipped) {
					getParentObject().broadcastMessage("FLIPSPRITE", false);
					flipped = false;
				}
			}
			
		};
		
		idle.addTransition(new StateTransition(move, () -> (!jumping && (left ^ right))));
		idle.addTransition(new StateTransition(air, () -> (jumping)));
		
		move.addTransition(new StateTransition(idle, () -> (!jumping && !(left ^ right))));
		move.addTransition(new StateTransition(air, () -> (jumping)));
		
		air.addTransition(new StateTransition(idle, () -> (grounded && !(left ^ right))));
		air.addTransition(new StateTransition(move, () -> (grounded && (left || right))));
		
		currentState = idle;

	}
		
	private void transitionState() {
		for (StateTransition transition : currentState.getTransitions()) {
			if (transition.shouldTransition()) {
				currentState.onExit();
				currentState = transition.getNextState();
				currentState.onEnter();
				break;
			}
		}
	}

	@Override
	public void tick(float delta, Game game) {
		transitionState();
		currentState.tick(delta, game);
	}
	
	

	@Override
	public void onKey(long window, int key, int scancode, int action, int mods) {
		if (key == Input.KEY_LEFT) {
			if (action == Input.PRESS) {
				left = true;
			}
			else if (action == Input.RELEASE) {
				left = false;				
			}
		}
		
		if (key == Input.KEY_RIGHT) {
			if (action == Input.PRESS) {
				right = true;
			}
			else if (action == Input.RELEASE) {
				right = false;
			}
		}
		
		if (key == Input.KEY_SPACE && action == Input.PRESS && grounded) {
			jumping = true;
			getParentObject().broadcastMessage("APPLYIMPULSE", new Vector3f(0,350,0));
		}
		
		if (key == Input.KEY_R && action == Input.PRESS) {
			getParentObject().getTransform().setXPos(0.5f);
			getParentObject().getTransform().setYPos(-10);
			getParentObject().getTransform().setZPos(0);
		}
	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		if (message.equals("GROUNDED") && param instanceof Boolean) {
			grounded = (boolean)param;
		}
	}

}
