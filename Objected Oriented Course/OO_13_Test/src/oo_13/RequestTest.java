package oo_13; 

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RequestTest {
	private static Request r = new Request(); 
	
	@Before
	public void setUp() throws Exception{
		r.inOrder(0, (long)0, " ");
		r.outOrder(0, " ", (long)0, " ");
		System.out.println("test begin for class Request");
	}
	
	@Test
	public void testInOrder() {
		r.inOrder(5, (long)123, "add");
		assertEquals(true,r.getIn());
		assertEquals(5,r.gettofloor());
		assertEquals(123,r.gettime());
		assertEquals("add",r.getR());
		System.out.println("method inOrder() working well");
	}
	
	@Test
	public void testoutOrder() {
		r.outOrder(9, "UP", (long)3366, "banner");
		assertEquals(false,r.getIn());
		assertEquals(9,r.getfloorLoc());
		assertEquals(3366,r.gettime());
		assertEquals(true,r.getUP());
		assertEquals("banner",r.getR());
		System.out.println("method outOrder() working well");
	}
}
