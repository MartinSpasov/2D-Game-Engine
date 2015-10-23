import static org.junit.Assert.*;

import org.junit.Test;

import engine.physics.geometry.Rectangle;

public class TestRectangle {

	@Test
	public void testIntersects() {
		Rectangle rect1 = new Rectangle(1,1,10,10);
		Rectangle rect2 = new Rectangle(5,6,2,4);
		Rectangle rect3 = new Rectangle(1,10,19,8);
		Rectangle rect4 = new Rectangle(1,1,2,4);
		
		assertTrue(rect1.intersects(rect2));
		assertFalse(rect2.intersects(rect3));
		assertFalse(rect3.intersects(rect4));
		assertTrue(rect4.intersects(rect1));
		assertFalse(rect4.intersects(rect2));
	}

}
