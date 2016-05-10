package engine.physics.collision.response;

import engine.physics.collision.Manifold;

public interface CollisionResponse {

	public void intersectResponse(Manifold manifold);
}
