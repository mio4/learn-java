package oo_13;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
 
public class ControlTest {

	private static Control c = new Control();
	@Before
	public void setUp() {
		System.out.println("Test begin");
		c.setUp();
	}
	
	@Test
	public void testIsSame1() {
		Request r1 = new Request();
		Request r2 = new Request();
		
		r1.inOrder(5, (long)123456, "123456");
		r2.inOrder(5, (long)123456, "123456");
		assertEquals(true,c.isSame(r1, r2));
		System.out.println("test isSame() 1 success");
	}
	
	@Test
	public void testIsSame2() {
		Request r1 = new Request();
		Request r2 = new Request();
		
		r1.outOrder(6, "UP", (long)666, "Doc");
		r2.outOrder(6, "UP", (long)666, "Doc");
		assertEquals(true,c.isSame(r1, r2));
		System.out.println("test isSame() 2 success");
	}
	
	@Test
	public void testIsSame3() {
		Request r1 = new Request();
		Request r2 = new Request();
		
		r1.outOrder(4, "DOWN", (long)666, "Doc");
		r2.outOrder(6, "UP", (long)666, "Doc");
		assertEquals(false,c.isSame(r1, r2));
		System.out.println("test isSame() 3 success");
	}
	
	@Test
	public void testIsSame4() {
		Request r1 = new Request();
		Request r2 = new Request();
		
		r1.inOrder(5, (long)123456, "123456");
		r2.outOrder(6, "UP", (long)666, "Doc");
		assertEquals(false,c.isSame(r1, r2));
		System.out.println("test isSame() 4 success");
	}
	
	@Test
	public void testCommand1() {
		
		Requeue q = new Requeue();
		Request r1 = new Request();
		r1.outOrder(1, "UP", (long)0, "(FR,1,UP,0)");
		q.add(r1);
		Request r2 = new Request();
		r2.outOrder(10, "DOWN", (long)0, "(FR,10,DOWN,0)");
		q.add(r2);
		Request r3 = new Request();
		r3.outOrder(10, "DOWN", (long)3, "(FR,10,DOWN,3)");
		q.add(r3);
		Request r4 = new Request();
		r4.outOrder(10, "DOWN", (long)10, "(FR,10,DOWN,10)");
		q.add(r4);
		Request r5 = new Request();
		r5.outOrder(4, "DOWN", (long)11, "(FR,4,DOWN,11)");
		q.add(r5);
		
		c.command(q);
		
		assertEquals(true,c.isSame(r1, q.visit(0)));
		assertEquals(true,c.isSame(r2, q.visit(1)));
		assertEquals(null,q.visit(2));
		assertEquals(true,c.isSame(r4, q.visit(3)));
		assertEquals(true,c.isSame(r5, q.visit(4)));
		System.out.println("test command() 1 success");
	}
	@Test
	public void testCommand2() {
		
		System.out.println("testCommand2 begin");
		Requeue q = new Requeue();
		Request r1 = new Request();
		r1.outOrder(1, "UP", (long)0, "(FR,1,UP,0)");
		q.add(r1);
		Request r2 = new Request();
		r2.inOrder(10,  (long)0, "(ER,10,0)");
		q.add(r2);
		Request r3 = new Request();
		r3.inOrder(10,  (long)3, "(ER,10,3)");
		q.add(r3);
		Request r4 = new Request();
		r4.inOrder(10,  (long)20, "(ER,10,20)");
		q.add(r4);
		Request r5 = new Request();
		r5.inOrder(4,  (long)21, "(ER,4,21)");
		q.add(r5);
		
		c.command(q);
		assertEquals(true,c.isSame(r1, q.visit(0)));
		assertEquals(true,c.isSame(r2, q.visit(1)));
		assertEquals(null, q.visit(2));
		assertEquals(true,c.isSame(r4, q.visit(3)));
		assertEquals(true,c.isSame(r5, q.visit(4)));
		System.out.println("test command() 2 success");
	}
	
	@Test
	/* 测试用例5：
	 * 用于测试同质请求的判断
	 * 复现bug结果：bug仍然存在
	 */
	public void testCommand3() {
		System.out.println("testCommand3 begin");
		
		Requeue q = new Requeue();
		Request r1 = new Request();
		r1.outOrder(1, "UP", (long)0, "(FR,1,UP,0)");
		q.add(r1);
		Request r2 = new Request();
		r2.inOrder(10,  (long)0, "(ER,10,0)");
		q.add(r2);
		Request r3 = new Request();
		r3.outOrder(4, "UP", (long)0, "(FR,4,UP,0)");
		q.add(r3);
		Request r4 = new Request();
		r4.outOrder(6, "UP", (long)0, "(FR,6,UP,0)");
		q.add(r4);
		Request r5 = new Request();
		r5.inOrder(10,  (long)7, "(ER,10,7)");
		q.add(r5);
		
		c.command(q);
		
		assertEquals(true,c.isSame(r1, q.visit(0)));
		assertEquals(true,c.isSame(r2, q.visit(1)));
		assertEquals(true, c.isSame(r3, q.visit(2)));
		assertEquals(true,c.isSame(r4, q.visit(3)));
		assertEquals(null, q.visit(4));
		System.out.println("233");
		
	}


}
