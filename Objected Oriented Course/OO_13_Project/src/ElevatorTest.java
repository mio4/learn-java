

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;

/** 
* Elevator Tester. 
* Overview：对于电梯类的测试
* @author <Authors name> 
* @since <pre>六月 4, 2018</pre> 
* @version 1.0 
*/ 
public class ElevatorTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}


/**
 *
 * Method: repOK()
 */
@Test
public void testRepOK(){
	Elevator e = new Elevator();
	Assert.assertEquals(e.repOK(),true);

}


/** 
* 
* Method: get_String(List<String> screen_out) 
* 
*/ 
@Test
public void testGet_String() throws Exception { 
//TODO: Test goes here...
	Elevator e = new Elevator();
	ArrayList<String> tmp = new ArrayList<String>();
	Assert.assertEquals(e.get_String(tmp),true);
} 

/** 
* 
* Method: toString() 
* 
*/ 
@Test
public void testToString() throws Exception { 
//TODO: Test goes here...
	Elevator e = new Elevator();
	ArrayList<String> tmp = new ArrayList<String>();
	tmp.add("testString1");
	tmp.add("testString2");
	e.get_String(tmp);
	Assert.assertEquals(e.toString(),null);
} 


} 
