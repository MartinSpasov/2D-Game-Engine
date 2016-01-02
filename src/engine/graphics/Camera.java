package engine.graphics;

import engine.math.Matrix4f;
import engine.object.Transform;

public class Camera {
	
	private Transform transform;
	
	private Matrix4f projectionMatrix;
	
	public Camera(Camera camera) {
		this(new Transform(camera.getTransform()), new Matrix4f(camera.getProjectionMatrix()));
	}
	
	public Camera(Matrix4f projectionMatrix) {
		this(new Transform(), projectionMatrix);
	}
	
	public Camera(Transform transform, Matrix4f projectionMatrix) {
		this.transform = transform;
		this.projectionMatrix = projectionMatrix;
	}
	
	public Matrix4f getWorldMatrix() {
		// TODO pre multiply???
		return transform.toMatrix().inverse();
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public void setTransform(Transform transform) {
		this.transform = transform;
	}
}
