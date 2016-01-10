package engine.math;

public class Vector3f {

	public static final Vector3f X_AXIS = new Vector3f(1,0,0);
	public static final Vector3f Y_AXIS = new Vector3f(0,1,0);
	public static final Vector3f Z_AXIS = new Vector3f(0,0,1);
	
	public float x;
	public float y;
	public float z;
	
	public Vector3f() {
		this(0,0,0);
	}
	
	public Vector3f(Vector3f vector) {
		this(vector.x, vector.y, vector.z);
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f add(Vector3f vector) {
		return new Vector3f(x + vector.x, y + vector.y, z + vector.z);
	}
	
	public Vector3f subtract(Vector3f vector) {
		return new Vector3f(x - vector.x, y - vector.y, z - vector.z);
	}
	
	public Vector3f multiply(float scalar) {
		return new Vector3f(x * scalar, y * scalar, z * scalar);
	}
	
	public Vector3f divide(float scalar) {
		return new Vector3f(x / scalar, y / scalar, z * scalar);
	}
	
	public float dot(Vector3f vector) {
		return (x * vector.x) + (y * vector.y) + (z * vector.z);
	}
	
	public Vector3f cross(Vector3f vector) {
		float x2 = ((y * vector.z) - (z * vector.y));
		float y2 = -1 * ((x * vector.z) - (z * vector.x));
		float z2 = ((x * vector.y) - (y * vector.x));
		
		return new Vector3f(x2, y2, z2);
	}
	
	public Vector3f normalize() {
		float norm = norm();
		return new Vector3f(x / norm, y / norm, z / norm);
	}
	
	public Vector3f reverse() {
		return new Vector3f(x * -1, y * -1, z * -1);
	}
	
	public Vector3f rotateX(float angle) {
		float y2 = (float) ((y * Math.cos(angle)) - (z * Math.sin(angle)));
		float z2 = (float) ((y * Math.sin(angle)) + (z * Math.cos(angle)));
		
		return new Vector3f(x, y2, z2);
	}
	
	public Vector3f rotateY(float angle) {
		float x2 = (float) ((x * Math.cos(angle)) - (z * Math.sin(angle)));
		float z2 = (float) ((x * Math.sin(angle)) + (z * Math.cos(angle)));
		
		return new Vector3f(x2, y, z2);
	}
	
	public Vector3f rotateZ(float angle) {
		float x2 = (float) ((x * Math.cos(angle)) - (y * Math.sin(angle)));
		float y2 = (float) ((x * Math.sin(angle)) + (y * Math.cos(angle)));
		
		return new Vector3f(x2, y2, z);
	}
	
	public Vector3f rotate(float angle, Vector3f axis) {
		Quaternion q1 = new Quaternion(angle, axis);
		Quaternion q2 = new Quaternion(0.0f, x, y, z);
		
		Quaternion finalQuat = q1.multiply(q2).multiply(q1.conjugate());
		
		return new Vector3f(finalQuat.b, finalQuat.c, finalQuat.d);
	}
	
	public float norm() {
		return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	}
	
	public boolean equals(Vector3f vector, double epsilon) {
		double deltaX = Math.abs(vector.x - x);
		double deltaY = Math.abs(vector.y - y);
		double deltaZ = Math.abs(vector.z - z);
		
		return (deltaX < epsilon && deltaY < epsilon && deltaZ < epsilon);
	}
	
	@Override
	public String toString() {
		return "<" + x + ", " + y + ", " + z + ">";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Vector3f) {
			Vector3f vector = (Vector3f) obj;
			return equals(vector, 0.005);
		}
		else {
			return false;
		}
	}
	
}