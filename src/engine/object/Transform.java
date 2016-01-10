package engine.object;

import engine.math.Matrix4f;
import engine.math.Vector3f;

public class Transform {

	private float xPos;
	private float yPos;
	private float zPos;
	
	private float xRot;
	private float yRot;
	private float zRot;
	
	private float xScale;
	private float yScale;
	private float zScale;
	
	public Transform() {
		this(0.0f, 0.0f, 0.0f);
	}
	
	public Transform(Transform t) {
		this(t.getXPos(), t.getYPos(), t.getZPos(), t.getXRot(), t.getYRot(), t.getZRot(), t.getXScale(), t.getYScale(), t.getZScale());
	}
	
	public Transform(float xPos, float yPos, float zPos) {
		this(xPos, yPos, zPos, 0.0f, 0.0f, 0.0f);
	}
	
	public Transform(float xPos, float yPos, float zPos, float xRot, float yRot, float zRot) {
		this(xPos, yPos, zPos, xRot, yRot, zRot, 1.0f, 1.0f, 1.0f);
	}
	
	public Transform(float xPos, float yPos, float zPos, float xRot, float yRot, float zRot, float xScale, float yScale, float zScale) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.xRot = xRot;
		this.yRot = yRot;
		this.zRot = zRot;
		this.xScale = xScale;
		this.yScale = yScale;
		this.zScale = zScale;
	}
	
	public void translate(float dx, float dy, float dz) {
		xPos += dx;
		yPos += dy;
		zPos += dz;
	}
	
	public void translateLocal(float dx, float dy, float dz) {
		Vector3f displacement = new Vector3f(dx,dy,dz).rotate(xRot, Vector3f.X_AXIS).rotate(yRot, Vector3f.Y_AXIS).rotate(zRot, Vector3f.Z_AXIS);
		translate(displacement.x, displacement.y, displacement.z);
	}
	
	public void scale(float scale) {
		xScale *= scale;
		yScale *= scale;
		zScale *= scale;
	}
	
	public Matrix4f toMatrix() {
		// TODO pre multiply???
		
		Matrix4f scalePos = Matrix4f.translation(xPos, yPos, zPos).multiply(Matrix4f.scale(xScale, yScale, zScale));
		Matrix4f xRotMatrix = Matrix4f.rotationX(xRot);
		Matrix4f yRotMatrix = Matrix4f.rotationY(yRot);
		Matrix4f zRotMatrix = Matrix4f.rotationZ(zRot);
		
		return scalePos.multiply(xRotMatrix).multiply(zRotMatrix).multiply(yRotMatrix);
	}

	public float getXPos() {
		return xPos;
	}

	public float getYPos() {
		return yPos;
	}

	public float getZPos() {
		return zPos;
	}

	public float getXRot() {
		return xRot;
	}

	public float getYRot() {
		return yRot;
	}

	public float getZRot() {
		return zRot;
	}

	public float getXScale() {
		return xScale;
	}

	public float getYScale() {
		return yScale;
	}

	public float getZScale() {
		return zScale;
	}

	public void setXPos(float xPos) {
		this.xPos = xPos;
	}

	public void setYPos(float yPos) {
		this.yPos = yPos;
	}

	public void setZPos(float zPos) {
		this.zPos = zPos;
	}

	public void setXRot(float xRot) {
		this.xRot = xRot;
	}

	public void setYRot(float yRot) {
		this.yRot = yRot;
	}

	public void setZRot(float zRot) {
		this.zRot = zRot;
	}

	public void setXScale(float xScale) {
		this.xScale = xScale;
	}

	public void setYScale(float yScale) {
		this.yScale = yScale;
	}

	public void setZScale(float zScale) {
		this.zScale = zScale;
	}
}
