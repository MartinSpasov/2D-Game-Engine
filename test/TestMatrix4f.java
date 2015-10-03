import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import engine.math.Matrix4f;

public class TestMatrix4f {
	
	private static Matrix4f m1;
	private static Matrix4f m2;
	private static Matrix4f m3;
	private static Matrix4f m4;
	private static Matrix4f m5;
	private static Matrix4f m6;
	
	
	@BeforeClass
	public static void init() {
		m1 = new Matrix4f();
		m2 = new Matrix4f(new float[]{5,0,0,3,6,0,5,0,7,8,0,0,0,0,0,1});
		m3 = new Matrix4f(new float[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16});
		m4 = new Matrix4f(new float[]{3,0,2,-1,1,2,0,-2,4,0,6,-3,5,0,2,0});
		m5 = new Matrix4f(new float[]{3,2,0,1,4,0,1,2,3,0,2,1,9,2,3,1});
		m6 = new Matrix4f(new float[]{4,0,10,0,0,1,6,8,0,0,4,4,0,0,0,6});
	}

	@Test
	public void testMultiplyMatrix4f() {
		assertEquals(m2.multiply(m1), m2);
		assertEquals(m3.multiply(m4), new Matrix4f(new float[]{37,4,28,-14,89,12,68,-38,141,20,108,-62,193,28,148,-86}));
	}

	@Test
	public void testMultiplyFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testTranspose() {
		fail("Not yet implemented");
	}

	@Test
	public void testDet() {
		assertEquals(1.0, m1.det(), 0.0005);
		assertEquals(-200.0, m2.det(), 0.0005);
		assertEquals(0.0, m3.det(), 0.0005);
		assertEquals(20.0, m4.det(), 0.0005);
		assertEquals(24.0, m5.det(), 0.0005);
		assertEquals(96.0, m6.det(), 0.0005);
	}

	@Test
	public void testInverse() {
		assertEquals(m1.inverse(), new Matrix4f());
		assertEquals(m2.inverse(), new Matrix4f(new float[]{0.2f,0,0,-0.6f,-0.175f,0,0.125f,0.525f,-0.24f,0.2f,0,0.72f,0,0,0,1}));
		assertEquals(m4.inverse(), new Matrix4f(new float[]{0.6f,0,-0.2f,0,-2.5f,0.5f,0.5f,1,-1.5f,0,0.5f,0.5f,-2.2f,0,0.4f,1}));
		assertEquals(m5.inverse(), new Matrix4f(new float[]{-0.25f,0.25f,-0.5f,0.25f,0.666666666f,-0.5f,0.5f,-0.166666666f,0.16666666666f,-0.5f,1,-0.16666666666f,0.416666666f,0.25f,0.5f,-0.4166666666666f}));
		assertEquals(m6.inverse(), new Matrix4f(new float[]{0.25f,0,-0.625f,0.41666666666f,0,1,-1.5f,-0.333333333333f,0,0,0.25f,-0.166666666666f,0,0,0,0.16666666666f}));
	}

}
