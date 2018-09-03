import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* Request Tester. 
* Overview:请求类的测试
* @author <Authors name> 
* @since <pre>六月 4, 2018</pre> 
* @version 1.0 
*/ 
public class RequestTest {
	private String str;
	private String InOut; //电梯内|电梯外
	private String direct; //向上|向下
	private long time; //请求时间
	private double exetime; //运行时间
	private int location; //当前位置
@Before
public void before() throws Exception {
	str = "(FR,1,UP,0)";
	InOut = "FR";
	direct = "DOWN";
	time = 0;
	exetime = 1.0;
	location = 1;
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
	Request req = new Request(str);
	Assert.assertEquals(req.repOK(),true);
}

/** 
* 
* Method: changeValueSort(String InOut, String direct, long time, int location, String printstr) 
* 
*/ 
@Test
public void testChangeValueSort() throws Exception {
//TODO: Test goes here...
	Request req = new Request(str);
	req.changeValueSort(InOut,direct,time,location,str);
} 

/** 
* 
* Method: changeValueQueue(String InOut, String direct, double time, int location, String printstr) 
* 
*/ 
@Test
public void testChangeValueQueue() throws Exception { 
//TODO: Test goes here...
	Request req = new Request(str);
	req.changeValueQueue(InOut,direct,time,location,str);
} 

/** 
* 
* Method: RidSpace() 
* 
*/ 
@Test
public void testRidSpace() throws Exception { 
//TODO: Test goes here...
	str = "(FR,1,UP,0)";
	Request req = new Request(str);
	req.RidSpace();
} 

/** 
* 
* Method: FormatJudge() 
* 
*/ 
@Test
public void testFormatJudge() throws Exception { 
//TODO: Test goes here...
	str = "(FR,1,UP,0)";
	Request req = new Request(str);
	Assert.assertEquals(req.FormatJudge(),true);
	str = "(SR,1,UP,0)";
	req = new Request(str);
	Assert.assertEquals(req.FormatJudge(),false);
	str = "((FR,1,UP,0)";
	req = new Request(str);
	Assert.assertEquals(req.FormatJudge(),false);
} 

/** 
* 
* Method: get_info() 
* 
*/ 
@Test
public void testGet_info() throws Exception { 
//TODO: Test goes here...
	str = "(FR,1,UP,1000000000000)";
	Request req = new Request(str);
	Assert.assertEquals(req.get_info(),false);
	str = "(FR,10,UP,0)";
	req = new Request(str);
	Assert.assertEquals(req.get_info(),false);
	str = "(FR,1,DOWN,0)";
	req = new Request(str);
	Assert.assertEquals(req.get_info(),false);
	str = "(FR,-1,DOWN,0)";
	req = new Request(str);
	Assert.assertEquals(req.get_info(),false);
	str = "(FR,11,DOWN,0)";
	req = new Request(str);
	Assert.assertEquals(req.get_info(),false);
	str = "(FR,1,UP,4294967296)";
	req = new Request(str);
	Assert.assertEquals(req.get_info(),false);
	str = "(ER,6,1)";
	req = new Request(str);
	Assert.assertEquals(req.get_info(),true);

} 

/** 
* 
* Method: get_InOut() 
* 
*/ 
@Test
public void testGet_InOut() throws Exception { 
//TODO: Test goes here...
	Request req = new Request(str);
	req.changeValueQueue(InOut,direct,time,location,str);
	Assert.assertTrue(req.get_InOut().equals(InOut));
} 

/** 
* 
* Method: get_direct() 
* 
*/ 
@Test
public void testGet_direct() throws Exception { 
//TODO: Test goes here...
	Request req = new Request(str);
	req.changeValueQueue(InOut,direct,time,location,str);
	Assert.assertTrue(req.get_direct().equals(direct));
} 

/** 
* 
* Method: get_time() 
* 
*/ 
@Test
public void testGet_time() throws Exception { 
//TODO: Test goes here...
	Request req = new Request(str);
	req.changeValueQueue(InOut,direct,time,location,str);
	Assert.assertTrue(req.get_time() == time);
}

/** 
* 
* Method: get_exetime() 
* 
*/ 
@Test
public void testGet_exetime() throws Exception { 
//TODO: Test goes here...
	Request req = new Request(str);
	req.changeValueQueue(InOut,direct,time,location,str);
	//对于浮点数的的比较需要进行特殊处理
	//Assert.assertTrue(req.get_exetime() == exetime);
	Assert.assertTrue(Double.doubleToLongBits(req.get_exetime()) == Double.doubleToLongBits(time));
} 

/** 
* 
* Method: get_location() 
* 
*/ 
@Test
public void testGet_location() throws Exception { 
//TODO: Test goes here...
	Request req = new Request(str);
	req.changeValueQueue(InOut,direct,time,location,str);
	Assert.assertTrue(req.get_location() == location);
} 

/** 
* 
* Method: get_printstr() 
* 
*/ 
@Test
public void testGet_printstr() throws Exception { 
//TODO: Test goes here...
	Request req = new Request(str);
	req.changeValueQueue(InOut,direct,time,location,str);
	//Assert.assertTrue(req.get_time() == time);
	Assert.assertEquals(req.get_printstr(),str);
} 


} 
