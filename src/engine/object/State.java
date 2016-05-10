package engine.object;

import java.util.ArrayList;

import engine.Game;

public abstract class State {

	private ArrayList<StateTransition> transitions;
	
	public State() {
		transitions = new ArrayList<StateTransition>();
	}
	
	public void addTransition(StateTransition transition) {
		transitions.add(transition);
	}
	
	public ArrayList<StateTransition> getTransitions() {
		return transitions;
	}
	
	public abstract void onEnter();
	public abstract void onExit();
	public abstract void tick(float delta, Game game);
	
}
