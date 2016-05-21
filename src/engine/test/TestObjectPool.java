package engine.test;

import engine.ObjectPool;

public class TestObjectPool extends ObjectPool<TestObject> {

	public TestObjectPool(int size) {
		super(size);
	}
	
	@Override
	protected TestObject allocateObject() {
		return new TestObject();
	}

}
