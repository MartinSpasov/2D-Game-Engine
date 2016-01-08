package engine.object.component;

import engine.Game;
import engine.object.GameObject;

public class StateComponent extends ObjectComponent {

	private String currentState;
	
	public StateComponent(GameObject parentObject, String currentState) {
		super(parentObject);
		this.currentState = currentState;
	}
	
	public String getCurrentState() {
		return currentState;
	}
	
	@Override
	public void tick(float delta, Game game) {
		
	}

	@Override
	public <T> void receiveMessage(String message, T param) {
		if (message.equals("STATECHANGE") && param instanceof String) {
			currentState = (String)param;
		}
	}

}
