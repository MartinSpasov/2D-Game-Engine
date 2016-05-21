package engine;

public interface Poolable {
	public boolean isDead();
	public void kill();
	public void resurect();
}
