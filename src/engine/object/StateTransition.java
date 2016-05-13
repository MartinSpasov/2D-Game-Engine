package engine.object;

public class StateTransition {

	private State nextState;
	private Condition condition;
	
	public StateTransition(State nextState, Condition condition) {
		this.nextState = nextState;
		this.condition = condition;
	}

	public State getNextState() {
		return nextState;
	}

	public boolean shouldTransition() {
		return condition.evaluate();
	}
	
}
