package engine.test;

import engine.Poolable;

public class TestObject implements Poolable {

	private boolean dead;
	
	public void kill() {
		dead = true;
	}
	
	@Override
	public boolean isDead() {
		return dead;
	}

	@Override
	public void resurect() {
		dead = false;
	}

}
