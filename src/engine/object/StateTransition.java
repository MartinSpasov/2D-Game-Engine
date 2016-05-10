package engine.object;

public class StateTransition {

	private State nextState;
	private byte flag;
	
	public StateTransition(State nextState, byte flag) {
		this.nextState = nextState;
		this.flag = flag;
	}

	public State getNextState() {
		return nextState;
	}

	public byte getFlag() {
		return flag;
	}
	
}
