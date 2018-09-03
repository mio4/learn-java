package oo_13;
 
import static org.junit.Assert.*;

import org.junit.Test;

public class ElevatorTest {
	private static Elevator e = new Elevator();
	
	
	@Test
	public void testSetAndGet() {
		e.setFloor(1);
		assertEquals(1,e.getFloorNow());
		e.setFloor(10);
		assertEquals(10,e.getFloorNow());
		e.setFloor(5);
		assertEquals(5,e.getFloorNow());
	}
	
	@Test
	public void testtoString() {
		e.setFloor(5);
		assertEquals("("+e.getFloorNow()+"STILL)",e.toString());
	}

}
