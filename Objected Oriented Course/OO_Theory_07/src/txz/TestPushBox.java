package txz;

import java.io.ByteArrayInputStream;

import org.junit.Test;

import junit.framework.Assert;

public class TestPushBox {

	@Test
	public void testReadmap() throws Exception{
		PushBox pb = new PushBox();
		ByteArrayInputStream in = new ByteArrayInputStream("5 5 0 3 0 0 0 1 0 1 4 0 0 0 1 0 0 1 0 2 0 0 0 0 0 0 0".getBytes());
		System.setIn(in);
		pb.readmap();
	}	
	
	@Test
	public void testCheck() throws Exception{
		PushBox pb = new PushBox();
		PushBox.map[1][1] = 1;
		PushBox.map[2][2] = 0;
		pb.m = 3;
		pb.n = 3;

		Assert.assertFalse(pb.check(1, 1));
		Assert.assertTrue(pb.check(1, 2));
		Assert.assertTrue(pb.check(2, 2));
	}
	
	@Test
	public void testBsf_man(){
		PushBox pb = new PushBox();
		ByteArrayInputStream in = new ByteArrayInputStream("5 5 0 3 0 0 0 1 0 1 4 0 0 0 1 0 0 1 0 2 0 0 0 0 0 0 0".getBytes());
		System.setIn(in);
		pb.readmap();
		pb.bfs();
		
		//new Test
		node n = new node(1,7,2,6,0,null);
		n.bx = 1;
		n.by = 7;
		n.mx = 2;
		n.my = 6;
		Assert.assertFalse(pb.bfs_man(n.mx, n.my, n.bx, n.by, n.bx, n.by));
	}
}
