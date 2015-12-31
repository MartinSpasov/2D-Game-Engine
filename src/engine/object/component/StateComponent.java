package engine.object.component;

import java.util.ArrayList;

import engine.Game;
import engine.object.GameObject;

public class StateComponent extends ObjectComponent {

	private String currentState;
	
	private ArrayList<StateListener> listeners;
	
	public StateComponent(GameObject parentObject, String currentState) {
		super(parentObject);
		listeners = new ArrayList<StateListener>();
		this.currentState = currentState;
	}
	
	public void changeState(String state) {
		currentState = state;
		broadcastState();
	}
	
	public void broadcastState() {
		for (StateListener listener : listeners) {
			listener.receiveStateChange(currentState);
		}
	}
	
	public void registerListener(StateListener listener) {
		listeners.add(listener);
	}

	@Override
	public void tick(float delta, Game game) {
		
	}

	// TODO add way of removing listeners

}
