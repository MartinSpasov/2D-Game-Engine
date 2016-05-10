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
	
	public static final byte LEFT_BIT = 8;
	public static final byte RIGHT_BIT = 4;
	public static final byte SPACE_BIT = 2;
	public static final byte GROUND_BIT = 1;
	
	private boolean left;
	private boolean right;
	private boolean space;	
	private boolean grounded;
	
	private State currentState;
	
	private State idleL;
	private State idleR;
	private State moveL;
	private State moveR;
	private State airL;
	private State airR;
	
	public PlayerController(GameObject parentObject) {
		super(parentObject);
		
		idleL = new State() {
			@Override
			public void onEnter() {
				getParentObject().broadcastMessage("STATECHANGE", "IDLE_LEFT");
			}

			@Override
			public void onExit() {}
			
			@Override
			public void tick(float delta, Game game) {}
		};

		
		idleR = new State() {

			@Override
			public void onEnter() {
				getParentObject().broadcastMessage("STATECHANGE", "IDLE_RIGHT");
				
			}

			@Override
			public void onExit() {}

			@Override
			public void tick(float delta, Game game) {}
		};
		
		moveL = new State() {

			@Override
			public void onEnter() {
				getParentObject().broadcastMessage("STATECHANGE", "WALK_LEFT");
			}

			@Override
			public void onExit() {}

			@Override
			public void tick(float delta, Game game) {
				getParentObject().getTransform().translate(delta * -SPEED, 0, 0);
			}
			
		};
		
		moveR = new State() {

			@Override
			public void onEnter() {
				getParentObject().broadcastMessage("STATECHANGE", "WALK_RIGHT");
			}

			@Override
			public void onExit() {}

			@Override
			public void tick(float delta, Game game) {
				getParentObject().getTransform().translate(delta * SPEED, 0, 0);
			}
			
		};
		
		airL = new State() {

			@Override
			public void onEnter() {
				getParentObject().broadcastMessage("STATECHANGE", "JUMP_LEFT");
				grounded = false;
			}

			@Override
			public void onExit() {
				
			}

			@Override
			public void tick(float delta, Game game) {
				if (left && !right) {
					getParentObject().getTransform().translate(delta * -SPEED, 0, 0);
				}
			}
			
		};
		
		airR = new State() {

			@Override
			public void onEnter() {
				getParentObject().broadcastMessage("STATECHANGE", "JUMP_RIGHT");
				grounded = false;
			}

			@Override
			public void onExit() {
				
			}

			@Override
			public void tick(float delta, Game game) {
				if (!left && right) {
					getParentObject().getTransform().translate(delta * SPEED, 0, 0);
				}
			}
			
		};
		
		idleL.addTransition(new StateTransition(airL, (byte) 15));
		idleL.addTransition(new StateTransition(airL, (byte) 11));
		idleL.addTransition(new StateTransition(airR, (byte) 7));
		idleL.addTransition(new StateTransition(airL, (byte) 3));
		idleL.addTransition(new StateTransition(moveL, (byte) 9));
		idleL.addTransition(new StateTransition(moveR, (byte) 5));
		
		idleR.addTransition(new StateTransition(airR, (byte) 15));
		idleR.addTransition(new StateTransition(airL, (byte) 11));
		idleR.addTransition(new StateTransition(airR, (byte) 7));
		idleR.addTransition(new StateTransition(airR, (byte) 3));
		idleR.addTransition(new StateTransition(moveL, (byte) 9));
		idleR.addTransition(new StateTransition(moveR, (byte) 5));
		
		moveL.addTransition(new StateTransition(airL, (byte) (SPACE_BIT | GROUND_BIT | LEFT_BIT)));
		moveL.addTransition(new StateTransition(airR, (byte) (SPACE_BIT | GROUND_BIT | RIGHT_BIT)));
		moveL.addTransition(new StateTransition(airL, (byte) (SPACE_BIT | GROUND_BIT)));
		moveL.addTransition(new StateTransition(airL, (byte) (SPACE_BIT | GROUND_BIT | LEFT_BIT | RIGHT_BIT)));
		moveL.addTransition(new StateTransition(moveR, (byte) (GROUND_BIT | RIGHT_BIT)));
		moveL.addTransition(new StateTransition(idleL, (byte) (GROUND_BIT)));
		moveL.addTransition(new StateTransition(idleL, (byte) (GROUND_BIT | RIGHT_BIT | LEFT_BIT)));
		
		moveR.addTransition(new StateTransition(airL, (byte) (SPACE_BIT | GROUND_BIT | LEFT_BIT)));
		moveR.addTransition(new StateTransition(airR, (byte) (SPACE_BIT | GROUND_BIT | RIGHT_BIT)));
		moveR.addTransition(new StateTransition(airR, (byte) (SPACE_BIT | GROUND_BIT)));
		moveR.addTransition(new StateTransition(airR, (byte) (SPACE_BIT | GROUND_BIT | LEFT_BIT | RIGHT_BIT)));
		moveR.addTransition(new StateTransition(moveL, (byte) (GROUND_BIT | LEFT_BIT)));
		moveR.addTransition(new StateTransition(idleR, (byte) (GROUND_BIT)));
		moveR.addTransition(new StateTransition(idleR, (byte) (GROUND_BIT | RIGHT_BIT | LEFT_BIT)));
		
		airL.addTransition(new StateTransition(moveL, (byte)(GROUND_BIT | LEFT_BIT)));
		airL.addTransition(new StateTransition(moveR, (byte)(GROUND_BIT | RIGHT_BIT)));
		airL.addTransition(new StateTransition(idleL, (byte)(GROUND_BIT)));
		airL.addTransition(new StateTransition(idleL, (byte)(GROUND_BIT | LEFT_BIT | RIGHT_BIT)));
		airL.addTransition(new StateTransition(airR, (byte)(SPACE_BIT | RIGHT_BIT)));
		airL.addTransition(new StateTransition(airR, (byte)(RIGHT_BIT)));
		
		airR.addTransition(new StateTransition(moveL, (byte)(GROUND_BIT | LEFT_BIT)));
		airR.addTransition(new StateTransition(moveR, (byte)(GROUND_BIT | RIGHT_BIT)));
		airR.addTransition(new StateTransition(idleR, (byte)(GROUND_BIT)));
		airR.addTransition(new StateTransition(idleR, (byte)(GROUND_BIT | LEFT_BIT | RIGHT_BIT)));
		airR.addTransition(new StateTransition(airL, (byte)(SPACE_BIT | LEFT_BIT)));
		airR.addTransition(new StateTransition(airL, (byte)(LEFT_BIT)));
		
		currentState = idleR;
	}
	
	private void transitionState(byte flag) {
		for (StateTransition transition : currentState.getTransitions()) {
			if (transition.getFlag() == flag) {
				currentState.onExit();
				currentState = transition.getNextState();
				currentState.onEnter();
			}
		}
	}
	
	private byte computeFlags() {
		byte flags = 0;
		
		if (left) {
			flags |= LEFT_BIT;
		}
		if (right) {
			flags |= RIGHT_BIT;
		}
		if (space) {
			flags |= SPACE_BIT;
		}
		if (grounded) {
			flags |= GROUND_BIT;
		}
		
		return flags;
	}

	@Override
	public void tick(float delta, Game game) {
		transitionState(computeFlags());
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
		
		if (key == Input.KEY_SPACE) {
			if (action == Input.PRESS) {
				space = true;
				
				if (grounded) {
					getParentObject().broadcastMessage("APPLYIMPULSE", new Vector3f(0,350,0));
				}
			}
			else if (action == Input.RELEASE) {
				space = false;
			}
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
