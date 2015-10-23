package engine.physics.collision;

import engine.object.GameObject;

public class ObjectPair {
	public GameObject a;
	public GameObject b;
	
	public ObjectPair(GameObject a, GameObject b) {
		this.a = a;
		this.b = b;
	}
		
	@Override
	public boolean equals(Object o) {
		if (o instanceof ObjectPair) {
			ObjectPair pair = (ObjectPair) o;
			return ((a.equals(pair.a) && b.equals(pair.b)) || (a.equals(pair.b) && b.equals(pair.a)));
		}
		else {
			return false;
		}
	}
}	