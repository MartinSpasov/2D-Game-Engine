package engine.math;

public class Quaternion {

	public float a;
	public float b;
	public float c;
	public float d;
	
	public Quaternion(float a, float b, float c, float d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public Quaternion(float angle, Vector3f axis) {
		this.a = (float)Math.cos(angle / 2.0);
		this.b = (float)(axis.x * Math.sin(angle / 2.0));
		this.c = (float)(axis.y * Math.sin(angle / 2.0));
		this.d = (float)(axis.z * Math.sin(angle / 2.0));
	}
	
	public Quaternion add(Quaternion q) {
		return new Quaternion(a + q.a, b + q.b, c + q.c, d + q.d);
	}
	
	public Quaternion multiply(float real) {
		return new Quaternion(a * real, b * real, c * real, d * real);
	}
	
	public Quaternion divide(float real) {
		return new Quaternion(a / real, b / real, c / real, d / real);
	}

	public Quaternion multiply(Quaternion q) {
		float a2 = (a * q.a) - (b * q.b) - (c * q.c) - (d * q.d);
		float b2 = (a * q.b) + (b * q.a) + (c * q.d) - (d * q.c);
		float c2 = (a * q.c) - (b * q.d) + (c * q.a) + (d * q.b);
		float d2 = (a * q.d) + (b * q.c) - (c * q.b) + (d * q.a);
		
		return new Quaternion(a2, b2, c2, d2);
	}
	
	public Quaternion conjugate() {
		return new Quaternion(a, b * -1, c * -1, d * -1);
	}
	
	public Quaternion inverse() {
		return new Quaternion(a, b, c, d).conjugate().divide(norm() * norm());
	}
	
	public Quaternion normalize() {
		return new Quaternion(a / norm(), b / norm(), c / norm(), d / norm());
	}
	
	public float norm() {
		return (float)Math.sqrt((a * a) + (b * b) + (c * c) + (d * d));
	}
	
	@Override
	public String toString() {
		return (a + " + " + b + "i + " + c + "j + " + d + "k");
	}
}
