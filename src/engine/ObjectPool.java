package engine;

public abstract class ObjectPool<E extends Poolable> {
	
	public static final int MAX_POOL_SIZE = 1000;
	
	private Poolable[] objects;
	
	public ObjectPool() {
		this(MAX_POOL_SIZE);
	}
	
	public ObjectPool(int size) {
		objects = new Poolable[size];
		fill();
	}
	
	private void grow() {
		Poolable[] newPool = new Poolable[objects.length * 2];
		for (int i = 0; i < objects.length; i++) {
			newPool[i] = objects[i];
		}
		for (int i = objects.length; i < newPool.length; i++) {
			newPool[i] = allocateObject();
			newPool[i].kill();
		}
		objects = newPool;
	}
	
	private void fill() {
		for (int i = 0; i < objects.length; i++) {
			objects[i] = allocateObject();
			objects[i].kill();
		}
	}
	
	protected abstract E allocateObject();
	
	@SuppressWarnings("unchecked")
	public E create() {
		for (int i = 0; i < objects.length; i++) {
			if (objects[i].isDead()) {
				objects[i].resurect();
				return (E) objects[i];
			}
		}
		int index = objects.length;
		grow();
		objects[index].resurect();
		return (E) objects[index];
	}
	
	public int capacity() {
		return objects.length;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (int i = 0; i < objects.length; i++) {
			if (objects[i].isDead()) {
				sb.append('X');
			}
			else {
				sb.append('O');
			}
			if (i != objects.length - 1)
				sb.append(", ");
		}
		sb.append(']');
		return sb.toString();
	}
}
