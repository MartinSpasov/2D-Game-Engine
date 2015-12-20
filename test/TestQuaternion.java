import static org.junit.Assert.*;

import org.junit.Test;

import engine.math.Quaternion;

public class TestQuaternion {

	@Test
	public void testAdd() {
		Quaternion q1 = new Quaternion(4,7,2,4);
		Quaternion q2 = new Quaternion(97,-42,2,-43);
		
		assertEquals(q1.add(q2), new Quaternion(1,1,1,1));
	}

	@Test
	public void testMultiplyFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testDivide() {
		fail("Not yet implemented");
	}

	@Test
	public void testMultiplyQuaternion() {
		fail("Not yet implemented");
	}

	@Test
	public void testConjugate() {
		fail("Not yet implemented");
	}

	@Test
	public void testInverse() {
		fail("Not yet implemented");
	}

	@Test
	public void testNormalize() {
		fail("Not yet implemented");
	}

	@Test
	public void testNorm() {
		fail("Not yet implemented");
	}

}
