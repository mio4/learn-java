import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

/** 
* Floor Tester. 
* Overview : 楼层类的测试
* @author <Authors name> 
* @since <pre>六月 4, 2018</pre> 
* @version 1.0 
*/ 
public class FloorTest {
	private String InOut;
	private String direct;
	private double time;
@Before
public void before() throws Exception {
	InOut = "FR";
	direct = "UP";
	time = 1.0;
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: repOK() 
* 
*/ 
@Test
public void testRepOK() throws Exception { 
//TODO: Test goes here...
	Floor floor = new Floor(3);
	Assert.assertEquals(floor.repOK(),true);
	floor = new Floor(-1);
	Assert.assertEquals(floor.repOK(),false);

} 

/** 
* 
* Method: get_info(String InOut, String direct, double time) 
* 
*/ 
@Test
public void testGet_info() throws Exception { 
//TODO: Test goes here...
	Floor floor = new Floor(3);
	floor.get_info(InOut,direct,time);
} 

/** 
* 
* Method: check(String InOut, String direct, long time) 
* 
*/ 
@Test
public void testCheck() throws Exception { 
//TODO: Test goes here...
	Floor floor = new Floor(3);
	floor.get_info("ER","STILL",2.0);
	floor.get_info("FR","UP",3.0);
	floor.get_info("ER","STILL",4.0);
	Assert.assertEquals(floor.check("ER","STILL",1),false);
	Assert.assertEquals(floor.check("FR","UP",1),false);
	Assert.assertEquals(floor.check("FR","UP",5),true);

}

/** 
* 
* Method: get_floor() 
* 
*/ 
@Test
public void testGet_floor() throws Exception { 
//TODO: Test goes here...
	Floor floor = new Floor(3);
	Assert.assertEquals(floor.get_floor(),3);
} 


} 
