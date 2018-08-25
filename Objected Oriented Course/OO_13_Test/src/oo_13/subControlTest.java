package oo_13;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
  
public class subControlTest {
	private static subControl s = new subControl();
	@Before
	public void setUp() throws Exception {
		s.setUp();
		System.out.println("New test begin");
	}

	@Test
	/* 测试用例1：
	 * 单一捎带;e.sta==DOWN;楼层请求e.n<=r.n<e.e_n;捎带
	 * 复现bug结果：bug不存在惹
	 */
	public void testCarrySystem1() {
		
		Requeue q = new Requeue();
		Request r1 = new Request();
		r1.outOrder(1, "UP", (long)0, "(FR,1,UP,0)");
		q.add(r1);
		Request r2 = new Request();
		r2.outOrder(7, "UP", (long)0, "(FR,7,UP,0)");
		q.add(r2);
		Request r3 = new Request();
		r3.outOrder(3, "UP", (long)5, "(FR,3,UP,5)");
		q.add(r3);
		Request r4 = new Request();
		r4.outOrder(4, "DOWN", (long)6, "(FR,4,DOWN,6)");
		q.add(r4);
		
		s.command(q,0);
		assertEquals("[(FR,1,UP,0)]/(1,STILL,1.0)",s.getOutput(0));
		assertEquals("[(FR,7,UP,0)]/(7,UP,4.0)",s.getOutput(1));
		assertEquals("[(FR,4,DOWN,6)]/(4,DOWN,6.5)",s.getOutput(2));
		assertEquals("[(FR,3,UP,5)]/(3,DOWN,8.0)",s.getOutput(3));
		System.out.println("Test1 for carrySystem success");
		
	}
	@Test
	/* 测试用例2：
	 * 多个捎带;e.sta==UP;楼层请求全部位于(e.e_n,r.n];全部捎带
	 * 复现bug结果：bug不存在惹
	 */
	public void testCarrySystem2() {
		
		
		Requeue q = new Requeue();
		Request r1 = new Request();
		r1.outOrder(1, "UP", (long)0, "(FR,1,UP,0)");
		q.add(r1);
		Request r2 = new Request();
		r2.outOrder(8, "UP", (long)0, "(FR,8,UP,0)");
		q.add(r2);
		Request r3 = new Request();
		r3.outOrder(4, "UP", (long)2, "(FR,4,UP,2)");
		q.add(r3);
		Request r4 = new Request();
		r4.outOrder(7, "UP", (long)3, "(FR,7,UP,3)");
		q.add(r4);
		Request r5 = new Request();
		r5.inOrder(8, (long)4, "(ER,8,4)");
		q.add(r5);
		
		s.command(q,0);
		assertEquals("[(FR,1,UP,0)]/(1,STILL,1.0)",s.getOutput(0));
		assertEquals("[(FR,4,UP,2)]/(4,UP,2.5)",s.getOutput(1));
		assertEquals("[(FR,7,UP,3)]/(7,UP,5.0)",s.getOutput(2));
		assertEquals("[(FR,8,UP,0)]/(8,UP,6.5)",s.getOutput(3));
		assertEquals("[(ER,8,4)]/(8,UP,6.5)",s.getOutput(4));
		System.out.println("Test2 for carrySystem success");
	}
	
	@Test
	/* 测试用例3：
	 * 多个捎带;e.sta==DOWN;楼层请求全部位于(r.n,e.e_n];全部捎带
	 * bug复现结果：bug不存在惹
	 */
	public void testCarrySystem3() {

		Requeue q = new Requeue();
		Request r1 = new Request();
		r1.outOrder(1, "UP", (long)0, "(FR,1,UP,0)");
		q.add(r1);
		Request r2 = new Request();
		r2.outOrder(10, "DOWN", (long)0, "(FR,10,DOWN,0)");
		q.add(r2);
		Request r3 = new Request();
		r3.outOrder(3, "UP", (long)6, "(FR,3,UP,6)");
		q.add(r3);
		Request r4 = new Request();
		r4.outOrder(7, "DOWN", (long)6, "(FR,7,DOWN,6)");
		q.add(r4);
		Request r5 = new Request();
		r5.outOrder(4, "DOWN", (long)7, "(FR,4,DOWN,7)");
		q.add(r5);
		Request r6 = new Request();
		r6.inOrder(3, (long)8, "(ER,3,8)");
		q.add(r6);
		
		s.command(q,0);
		
		assertEquals("[(FR,1,UP,0)]/(1,STILL,1.0)",s.getOutput(0));
		assertEquals("[(FR,10,DOWN,0)]/(10,UP,5.5)",s.getOutput(1));
		assertEquals("[(FR,7,DOWN,6)]/(7,DOWN,8.0)",s.getOutput(2));
		assertEquals("[(FR,4,DOWN,7)]/(4,DOWN,10.5)",s.getOutput(3));
		assertEquals("[(FR,3,UP,6)]/(3,DOWN,12.0)",s.getOutput(4));
		assertEquals("[(ER,3,8)]/(3,DOWN,12.0)",s.getOutput(5));
		System.out.println("Test3 for carrySystem success");
	}

	@Test
	/* 测试用例4：
	 * 副请求时间==主请求开关门完毕时间;不捎带
	 * bug复现结果：bug不存在惹
	 */
	public void testCarrySystem4() {

		Requeue q = new Requeue();
		Request r1 = new Request();
		r1.outOrder(1, "UP", (long)0, "(FR,1,UP,0)");
		q.add(r1);
		Request r2 = new Request();
		r2.outOrder(7, "UP", (long)0, "(FR,7,UP,0)");
		q.add(r2);
		Request r3 = new Request();
		r3.outOrder(4, "DOWN", (long)1, "(FR,4,DOWN,1)");
		q.add(r3);
		
		Request r4 = new Request();
		r4.inOrder(10, (long)4, "(ER,10,4)");
		q.add(r4);
		
		s.command(q,0);
		
		assertEquals("[(FR,1,UP,0)]/(1,STILL,1.0)",s.getOutput(0));
		assertEquals("[(FR,7,UP,0)]/(7,UP,4.0)",s.getOutput(1));
		assertEquals("[(FR,4,DOWN,1)]/(4,DOWN,6.5)",s.getOutput(2));
		assertEquals("[(ER,10,4)]/(10,UP,10.5)",s.getOutput(3));
		
		System.out.println("Test4 for carrySystem success");
	}

	@Test
	/* 测试用例6：
	 * e.sta==UP;不捎带
	 * bug复现结果：bug不存在惹
	 */
	public void testCarrySystem5() {

		Requeue q = new Requeue();
		Request r1 = new Request();
		r1.outOrder(1, "UP", (long)0, "(FR,1,UP,0)");
		q.add(r1);
		Request r2 = new Request();
		r2.inOrder(10, (long)101, "(ER,10,101)");
		q.add(r2);
		Request r3 = new Request();
		r3.outOrder(1, "UP", (long)101, "(FR,1,UP,101)");
		q.add(r3);
		Request r4 = new Request();
		r4.inOrder(10, (long)150, "(ER,10,150)");
		q.add(r4);
		Request r5 = new Request();
		r5.inOrder(3, (long)200, "(ER,3,200)");
		q.add(r5);
		Request r6 = new Request();
		r6.inOrder(1, (long)201, "(ER,1,201)");
		q.add(r6);
		s.command(q,0);
		
		assertEquals("[(FR,1,UP,0)]/(1,STILL,1.0)",s.getOutput(0));
		assertEquals("[(ER,10,101)]/(10,UP,105.5)",s.getOutput(1));
		assertEquals("[(FR,1,UP,101)]/(1,DOWN,111.0)",s.getOutput(2));
		assertEquals("[(ER,10,150)]/(10,UP,154.5)",s.getOutput(3));
		assertEquals("[(ER,3,200)]/(3,DOWN,203.5)",s.getOutput(4));
		assertEquals("[(ER,1,201)]/(1,DOWN,205.5)",s.getOutput(5));
		
		System.out.println("Test5 for carrySystem success");
	}
	@Test
	
	public void testCarrySystem6() {

		Requeue q = new Requeue();
		Request r1 = new Request();
		r1.outOrder(1, "UP", (long)0, "(FR,1,UP,0)");
		q.add(r1);
		Request r2 = new Request();
		r2.inOrder(8, (long)1, "(ER,8,1)");
		q.add(r2);
		Request r3 = new Request();
		r3.outOrder(4, "UP", (long)2, "(FR,4,UP,2)");
		q.add(r3);
		Request r4 = new Request();
		r4.inOrder(4, (long)2, "(ER,4,2)");
		q.add(r4);
		Request r5 = new Request();
		r5.inOrder(5, (long)2, "(ER,5,2)");
		q.add(r5);
		Request r6 = new Request();
		r6.outOrder(5, "UP", (long)2, "(FR,5,UP,2)");
		q.add(r6);
		Request r7 = new Request();
		r7.inOrder(1,  (long)7, "(ER,1,7)");
		q.add(r7);
		Request r8 = new Request();
		r8.inOrder(5, (long)8, "(ER,5,8)");
		q.add(r8);
		Request r9 = new Request();
		r9.outOrder(5, "DOWN", (long)8, "(FR,5,DOWN,8)");
		q.add(r9);
		
		s.command(q,0);
		
		assertEquals("[(FR,1,UP,0)]/(1,STILL,1.0)",s.getOutput(0));
		assertEquals("[(FR,4,UP,2)]/(4,UP,2.5)\n[(ER,4,2)]/(4,UP,2.5)",s.getOutput(1));
		assertEquals("[(ER,5,2)]/(5,UP,4.0)\n[(FR,5,UP,2)]/(5,UP,4.0)",s.getOutput(2));
		assertEquals("[(ER,8,1)]/(8,UP,6.5)",s.getOutput(3));
		assertEquals("[(ER,5,8)]/(5,DOWN,9.0)\n[(FR,5,DOWN,8)]/(5,UP,9.0)",s.getOutput(4));
		assertEquals("[(ER,1,7)]/(1,DOWN,12.0)",s.getOutput(5));
		
		System.out.println("Test6 for carrySystem success");
	}
}
