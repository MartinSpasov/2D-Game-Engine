package engine.object;

import engine.math.Matrix4f;

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
	
	public Matrix4f toMatrix() {
		// TODO pre multiply???
		Matrix4f scalePos = new Matrix4f(new float[]{
				xScale,0,0,xPos,
				0,yScale,0,yPos,
				0,0,zScale,zPos,
				0,0,0,1});
		
		Matrix4f xRotMatrix = new Matrix4f(new float[] {
			1,0,0,0,
			0,(float) Math.cos(xRot),(float)(-1 * Math.sin(xRot)),0,
			0,(float) Math.sin(xRot),(float) Math.cos(xRot),0,
			0,0,0,1
		});
		Matrix4f yRotMatrix = new Matrix4f(new float[] {
				(float) Math.cos(yRot),0,(float) (-1 * Math.sin(yRot)),0,
				0,1,0,0,
				(float) Math.sin(yRot),0,(float) Math.cos(yRot),0,
				0,0,0,1
			});
		Matrix4f zRotMatrix = new Matrix4f(new float[] {
				(float) Math.cos(zRot),(float) (-1 * Math.sin(zRot)),0,0,
				(float) Math.sin(zRot),(float) Math.cos(zRot),0,0,
				0,0,1,0,
				0,0,0,1
			});
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
